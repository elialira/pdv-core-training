package org.testcontainers.containers;

import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.startupcheck.IndefiniteWaitOneShotStartupCheckStrategy;
import org.testcontainers.shaded.com.google.common.base.Joiner;
import org.testcontainers.shaded.com.google.common.util.concurrent.Uninterruptibles;
import org.testcontainers.shaded.org.apache.commons.lang.StringUtils;
import org.testcontainers.shaded.org.apache.commons.lang.SystemUtils;
import org.testcontainers.utility.AuditLogger;
import org.testcontainers.utility.MountableFile;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;

public class ContainerisedDockerCompose extends GenericContainer<ContainerisedDockerCompose> implements DockerCompose {

    private static final String DOCKER_SOCKET_PATH = "/var/run/docker.sock";
    public static final char UNIX_PATH_SEPERATOR = ':';

    public ContainerisedDockerCompose(List<File> composeFiles, String identifier) {

        super(TestcontainersConfiguration.getInstance().getDockerComposeContainerImage());
        addEnv(ENV_PROJECT_NAME, identifier);

        final File dockerComposeBaseFile = composeFiles.get(0);
        final String pwd = dockerComposeBaseFile.getAbsoluteFile().getParentFile().getAbsolutePath();
        final String containerPwd = MountableFile.forHostPath(pwd).getFilesystemPath();

        final List<String> absoluteDockerComposeFiles = composeFiles.stream()
                .map(File::getAbsolutePath)
                .map(MountableFile::forHostPath)
                .map(MountableFile::getFilesystemPath)
                .collect(toList());
        final String composeFileEnvVariableValue = Joiner.on(UNIX_PATH_SEPERATOR).join(absoluteDockerComposeFiles); // we always need the UNIX path separator
        logger().debug("Set env COMPOSE_FILE={}", composeFileEnvVariableValue);
        addEnv(ENV_COMPOSE_FILE, composeFileEnvVariableValue);
        addFileSystemBind(pwd, containerPwd, BindMode.READ_ONLY);

        // Ensure that compose can access docker. Since the container is assumed to be running on the same machine
        //  as the docker daemon, just mapping the docker control socket is OK.
        // As there seems to be a problem with mapping to the /var/run directory in certain environments (e.g. CircleCI)
        //  we map the socket file outside of /var/run, as just /docker.sock
        addFileSystemBind(getDockerSocketHostPath(), "/docker.sock", BindMode.READ_WRITE);
        addEnv("DOCKER_HOST", "unix:///docker.sock");
        setStartupCheckStrategy(new IndefiniteWaitOneShotStartupCheckStrategy());
        setWorkingDirectory(containerPwd);
    }

    private String getDockerSocketHostPath() {
        return SystemUtils.IS_OS_WINDOWS
                ? "/" + DOCKER_SOCKET_PATH
                : DOCKER_SOCKET_PATH;
    }

    @Override
    public void invoke() {
        super.start();

        this.followOutput(new Slf4jLogConsumer(logger()));

        // wait for the compose container to stop, which should only happen after it has spawned all the service containers
        logger().info("Docker Compose container is running for command: {}", Joiner.on(" ").join(this.getCommandParts()));
        while (this.isRunning()) {
            logger().trace("Compose container is still running");
            Uninterruptibles.sleepUninterruptibly(100, TimeUnit.MILLISECONDS);
        }
        logger().info("Docker Compose has finished running");

        AuditLogger.doComposeLog(this.getCommandParts(), this.getEnv());

        final Integer exitCode = this.dockerClient.inspectContainerCmd(getContainerId())
                .exec()
                .getState()
                .getExitCode();

        if (exitCode == null || exitCode != 0) {
            throw new ContainerLaunchException(
                    "Containerised Docker Compose exited abnormally with code " +
                            exitCode +
                            " whilst running command: " +
                            StringUtils.join(this.getCommandParts(), ' '));
        }
    }
}