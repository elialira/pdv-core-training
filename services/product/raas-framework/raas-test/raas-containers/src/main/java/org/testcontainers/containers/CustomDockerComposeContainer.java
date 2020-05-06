package org.testcontainers.containers;


import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.containers.wait.strategy.WaitAllStrategy;
import org.testcontainers.containers.wait.strategy.WaitStrategy;
import org.testcontainers.lifecycle.Startable;
import org.testcontainers.shaded.com.google.common.base.Preconditions;
import org.testcontainers.shaded.com.google.common.base.Strings;
import org.testcontainers.utility.Base58;
import org.testcontainers.utility.LogUtils;
import org.testcontainers.utility.ResourceReaper;

import java.io.File;
import java.time.Duration;
import java.util.AbstractMap.SimpleEntry;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * Container which launches Docker Compose, for the purposes of launching a defined set of containers.
 */
@Slf4j
public class CustomDockerComposeContainer<SELF extends CustomDockerComposeContainer<SELF>> extends FailureDetectingExternalResource implements Startable {

    /**
     * Random identifier which will become part of spawned containers names, so we can shut them down
     */
    private final String identifier;
    private final List<File> composeFiles;
    private Set<ParsedDockerComposeFile> parsedComposeFiles;
    private final Map<String, Integer> scalingPreferences = new HashMap<>();
    private DockerClient dockerClient;
    private boolean localCompose;
    private boolean pull = true;
    private boolean build = false;
    private boolean tailChildContainers;

    private String project;

    private final AtomicInteger nextAmbassadorPort = new AtomicInteger(2000);
    private final Map<String, Map<Integer, Integer>> ambassadorPortMappings = new ConcurrentHashMap<>();
    private final Map<String, ComposeServiceWaitStrategyTarget> serviceInstanceMap = new ConcurrentHashMap<>();
    private final Map<String, WaitAllStrategy> waitStrategyMap = new ConcurrentHashMap<>();
    private final SocatContainer ambassadorContainer = new SocatContainer();
    private final Map<String, List<Consumer<OutputFrame>>> logConsumers = new ConcurrentHashMap<>();

    private static final Object MUTEX = new Object();

    private List<String> scaldedServices = new ArrayList<>();
    private Set<String> services = new HashSet<>();

    /**
     * Properties that should be passed through to all Compose and ambassador containers (not
     * necessarily to containers that are spawned by Compose itself)
     */
    private Map<String, String> env = new HashMap<>();
    private RemoveImages removeImages;

    @Deprecated
    public CustomDockerComposeContainer(File composeFile, String identifier) {
        this(identifier, composeFile);
    }

    public CustomDockerComposeContainer(File... composeFiles) {
        this(Arrays.asList(composeFiles));
    }

    public CustomDockerComposeContainer(List<File> composeFiles) {
        this(Base58.randomString(6).toLowerCase(), composeFiles);
    }

    public CustomDockerComposeContainer(String identifier, File... composeFiles) {
        this(identifier, Arrays.asList(composeFiles));
    }

    public CustomDockerComposeContainer(String identifier, List<File> composeFiles) {

        this.composeFiles = composeFiles;
        this.parsedComposeFiles = composeFiles.stream().map(ParsedDockerComposeFile::new).collect(Collectors.toSet());

        // Use a unique identifier so that containers created for this compose environment can be identified
        this.identifier = identifier;
        this.project = randomProjectId();

        this.dockerClient = DockerClientFactory.instance().client();
    }


    @Override
    @Deprecated
    public Statement apply(Statement base, Description description) {
        return super.apply(base, description);
    }

    @Override
    @Deprecated
    public void starting(Description description) {
        start();
    }

    @Override
    @Deprecated
    protected void succeeded(Description description) {
    }

    @Override
    @Deprecated
    protected void failed(Throwable e, Description description) {
    }

    @Override
    @Deprecated
    public void finished(Description description) {
        stop();
    }

    @Override
    public void start() {
        synchronized (MUTEX) {
            registerContainersForShutdown();
            if (pull) {
                try {
                    pullImages();
                } catch (ContainerLaunchException e) {
                    log.warn("Exception while pulling images, using local images if available", e);
                }
            }
            createServices();
            startAmbassadorContainers();
            waitUntilServiceStarted();
        }
    }

    private void pullImages() {
        // Pull images using our docker client rather than compose itself,
        // (a) as a workaround for https://github.com/docker/compose/issues/5854, which prevents authenticated image pulls being possible when credential helpers are in use
        // (b) so that credential helper-based auth still works when compose is running from within a container
        parsedComposeFiles.stream()
                .flatMap(it -> it.getServiceImageNames().stream())
                .forEach(imageName -> {
                    try {
                        DockerClientFactory.instance().checkAndPullImage(dockerClient, imageName);
                    } catch (Exception e) {
                        log.warn("Failed to pull image '{}'. Exception message was {}", imageName, e.getMessage());
                    }
                });
    }

    public SELF withServices(@NonNull String... services) {
        this.services = new HashSet<>(Arrays.asList(services));
        return self();
    }

    public SELF withScale(@NonNull String... services) {
        this.scaldedServices = Arrays.asList(services);
        return self();
    }

    private void createServices() {
        // Apply scaling
        final String servicesWithScalingSettings = Stream.concat(scaldedServices.stream(), scalingPreferences.keySet().stream())
                .map(service -> "--scale " + service + "=" + scalingPreferences.getOrDefault(service, 1))
                .collect(joining(" "));

        final String services = String.join(" ", this.services);

        String flags = "-d";

        if (build) {
            flags += " --build";
        }

        // Run the docker-compose container, which starts up the services
        if(Strings.isNullOrEmpty(servicesWithScalingSettings)) {
            runWithCompose("up " + flags + " " + services);
        } else {
            runWithCompose("up " + flags  + " " + servicesWithScalingSettings + " " + services);
        }
    }

    private void waitUntilServiceStarted() {
        listChildContainers().forEach(this::createServiceInstance);
        serviceInstanceMap.forEach(this::waitUntilServiceStarted);
    }

    private void createServiceInstance(Container container) {
        String serviceName = getServiceNameFromContainer(container);
        final ComposeServiceWaitStrategyTarget containerInstance = new ComposeServiceWaitStrategyTarget(container,
                ambassadorContainer, ambassadorPortMappings.getOrDefault(serviceName, new HashMap<>()));

        String containerId = containerInstance.getContainerId();
        if (tailChildContainers) {
            followLogs(containerId, new Slf4jLogConsumer(log).withPrefix(container.getNames()[0]));
        }
        //follow logs using registered consumers for this service
        logConsumers.getOrDefault(serviceName, Collections.emptyList()).forEach(consumer -> followLogs(containerId, consumer));
        serviceInstanceMap.putIfAbsent(serviceName, containerInstance);
    }

    private void waitUntilServiceStarted(String serviceName, ComposeServiceWaitStrategyTarget serviceInstance) {
        final WaitAllStrategy waitAllStrategy = waitStrategyMap.get(serviceName);
        if(waitAllStrategy != null) {
            waitAllStrategy.waitUntilReady(serviceInstance);
        }
    }

    private String getServiceNameFromContainer(Container container) {
        final String containerName = container.getLabels().get("com.docker.compose.service");
        final String containerNumber = container.getLabels().get("com.docker.compose.container-number");
        return String.format("%s_%s", containerName, containerNumber);
    }

    private void runWithCompose(String cmd) {
        Preconditions.checkNotNull(composeFiles);
        Preconditions.checkArgument(!composeFiles.isEmpty(), "No docker compose file have been provided");

        createDockerCompose()
                .withCommand(cmd)
                .withEnv(env)
                .invoke();
    }

    private DockerCompose createDockerCompose() {
        if (localCompose) {
            return new LocalDockerCompose(composeFiles, project);
        }
        return new ContainerisedDockerCompose(composeFiles, project);
    }

    private void registerContainersForShutdown() {
        ResourceReaper.instance().registerFilterForCleanup(Arrays.asList(
                new SimpleEntry<>("label", "com.docker.compose.project=" + project)
        ));
    }

    private List<Container> listChildContainers() {
        return dockerClient.listContainersCmd()
                .withShowAll(true)
                .exec().stream()
                .filter(container -> Arrays.stream(container.getNames()).anyMatch(name ->
                        name.startsWith("/" + project)))
                .collect(toList());
    }

    private void startAmbassadorContainers() {
        if (!ambassadorPortMappings.isEmpty()) {
            ambassadorContainer.start();
        }
    }

    @Override
    public void stop() {
        synchronized (MUTEX) {
            try {
                // shut down the ambassador container
                ambassadorContainer.stop();

                // Kill the services using docker-compose
                String cmd = "down -v";
                if (removeImages != null) {
                    cmd += " --rmi " + removeImages.dockerRemoveImagesType();
                }
                runWithCompose(cmd);

            } finally {
                project = randomProjectId();
            }
        }
    }

    public SELF withExposedService(String serviceName, int servicePort) {
        return withExposedService(serviceName, servicePort, Wait.defaultWaitStrategy());
    }

    public CustomDockerComposeContainer withExposedService(String serviceName, int instance, int servicePort) {
        return withExposedService(serviceName + "_" + instance, servicePort);
    }

    public CustomDockerComposeContainer withExposedService(String serviceName, int instance, int servicePort, WaitStrategy waitStrategy) {
        return withExposedService(serviceName + "_" + instance, servicePort, waitStrategy);
    }

    public SELF withExposedService(String serviceName, int servicePort, @NonNull WaitStrategy waitStrategy) {

        String serviceInstanceName = getServiceInstanceName(serviceName);

        /*
         * For every service/port pair that needs to be exposed, we register a target on an 'ambassador container'.
         *
         * The ambassador container's role is to link (within the Docker network) to one of the
         * compose services, and proxy TCP network I/O out to a port that the ambassador container
         * exposes.
         *
         * This avoids the need for the docker compose file to explicitly expose ports on all the
         * services.
         *
         * {@link GenericContainer} should ensure that the ambassador container is on the same network
         * as the rest of the compose environment.
         */

        // Ambassador container will be started together after docker compose has started
        int ambassadorPort = nextAmbassadorPort.getAndIncrement();
        ambassadorPortMappings.computeIfAbsent(serviceInstanceName, __ -> new ConcurrentHashMap<>()).put(servicePort, ambassadorPort);
        ambassadorContainer.withTarget(ambassadorPort, serviceInstanceName, servicePort);
        ambassadorContainer.addLink(new FutureContainer(this.project + "_" + serviceInstanceName), serviceInstanceName);
        addWaitStrategy(serviceInstanceName, waitStrategy);
        return self();
    }

    private String getServiceInstanceName(String serviceName) {
        String serviceInstanceName = serviceName;
        if (!serviceInstanceName.matches(".*_[0-9]+")) {
            serviceInstanceName += "_1"; // implicit first instance of this service
        }
        return serviceInstanceName;
    }

    /*
     * can have multiple wait strategies for a single container, e.g. if waiting on several ports
     * if no wait strategy is defined, the WaitAllStrategy will return immediately.
     * The WaitAllStrategy uses an long timeout, because timeouts should be handled by the inner strategies.
     */
    private void addWaitStrategy(String serviceInstanceName, @NonNull WaitStrategy waitStrategy) {
        final WaitAllStrategy waitAllStrategy = waitStrategyMap.computeIfAbsent(serviceInstanceName, __ ->
                new WaitAllStrategy(WaitAllStrategy.Mode.WITH_MAXIMUM_OUTER_TIMEOUT).withStartupTimeout(Duration.ofMinutes(30)));
        waitAllStrategy.withStrategy(waitStrategy);
    }

    /**
     Specify the {@link WaitStrategy} to use to determine if the container is ready.
     *
     * @see org.testcontainers.containers.wait.strategy.Wait#defaultWaitStrategy()
     * @param serviceName the name of the service to wait for
     * @param waitStrategy the WaitStrategy to use
     * @return this
     */
    public SELF waitingFor(String serviceName, @NonNull WaitStrategy waitStrategy) {
        String serviceInstanceName = getServiceInstanceName(serviceName);
        addWaitStrategy(serviceInstanceName, waitStrategy);
        return self();
    }

    /**
     * Get the host (e.g. IP address or hostname) that an exposed service can be found at, from the host machine
     * (i.e. should be the machine that's running this Java process).
     * <p>
     * The service must have been declared using DockerComposeContainer#withExposedService.
     *
     * @param serviceName the name of the service as set in the docker-compose.yml file.
     * @param servicePort the port exposed by the service container.
     * @return a host IP address or hostname that can be used for accessing the service container.
     */
    public String getServiceHost(String serviceName, Integer servicePort) {
        return ambassadorContainer.getContainerIpAddress();
    }

    /**
     * Get the port that an exposed service can be found at, from the host machine
     * (i.e. should be the machine that's running this Java process).
     * <p>
     * The service must have been declared using DockerComposeContainer#withExposedService.
     *
     * @param serviceName the name of the service as set in the docker-compose.yml file.
     * @param servicePort the port exposed by the service container.
     * @return a port that can be used for accessing the service container.
     */
    public Integer getServicePort(String serviceName, Integer servicePort) {
        Map<Integer, Integer> portMap = ambassadorPortMappings.get(getServiceInstanceName(serviceName));

        if (portMap == null) {
            throw new IllegalArgumentException("Could not get a port for '" + serviceName + "'. " +
                    "Testcontainers does not have an exposed port configured for '" + serviceName + "'. "+
                    "To fix, please ensure that the service '" + serviceName + "' has ports exposed using .withExposedService(...)");
        } else {
            return ambassadorContainer.getMappedPort(portMap.get(servicePort));
        }
    }

    public SELF withScaledService(String serviceBaseName, int numInstances) {
        scalingPreferences.put(serviceBaseName, numInstances);

        return self();
    }

    public SELF withEnv(String key, String value) {
        env.put(key, value);
        return self();
    }

    public SELF withEnv(Map<String, String> env) {
        env.forEach(this.env::put);
        return self();
    }

    /**
     * Use a local Docker Compose binary instead of a container.
     *
     * @return this instance, for chaining
     */
    public SELF withLocalCompose() {
        return withLocalCompose(true);
    }

    /**
     * Use a local Docker Compose binary instead of a container.
     *
     * @return this instance, for chaining
     */
    public SELF withLocalCompose(boolean localCompose) {
        this.localCompose = localCompose;
        return self();
    }

    /**
     * Whether to pull images first.
     *
     * @return this instance, for chaining
     */
    public SELF withPull(boolean pull) {
        this.pull = pull;
        return self();
    }

    /**
     * Whether to tail child container logs.
     *
     * @return this instance, for chaining
     */
    public SELF withTailChildContainers(boolean tailChildContainers) {
        this.tailChildContainers = tailChildContainers;
        return self();
    }

    /**
     * Attach an output consumer at container startup, enabling stdout and stderr to be followed, waited on, etc.
     * <p>
     * More than one consumer may be registered.
     *
     * @param serviceName the name of the service as set in the docker-compose.yml file
     * @param consumer consumer that output frames should be sent to
     * @return this instance, for chaining
     */
    public SELF withLogConsumer(String serviceName, Consumer<OutputFrame> consumer) {
        String serviceInstanceName = getServiceInstanceName(serviceName);
        final List<Consumer<OutputFrame>> consumers = this.logConsumers.getOrDefault(serviceInstanceName, new ArrayList<>());
        consumers.add(consumer);
        this.logConsumers.putIfAbsent(serviceInstanceName, consumers);
        return self();
    }

    /**
     * Whether to always build images before starting containers.
     *
     * @return this instance, for chaining
     */
    public SELF withBuild(boolean build) {
        this.build = build;
        return self();
    }

    /**
     * Remove images after containers shutdown.
     *
     * @return this instance, for chaining
     */
    public SELF withRemoveImages(RemoveImages removeImages) {
        this.removeImages = removeImages;
        return self();
    }

    private void followLogs(String containerId, Consumer<OutputFrame> consumer) {
        LogUtils.followOutput(DockerClientFactory.instance().client(), containerId, consumer);
    }

    private SELF self() {
        return (SELF) this;
    }

    private String randomProjectId() {
        return identifier + Base58.randomString(6).toLowerCase();
    }

    public enum RemoveImages {
        /**
         * Remove all images used by any service.
         */
        ALL("all"),

        /**
         * Remove only images that don't have a custom tag set by the `image` field.
         */
        LOCAL("local");

        private final String dockerRemoveImagesType;

        RemoveImages(final String dockerRemoveImagesType) {
            this.dockerRemoveImagesType = dockerRemoveImagesType;
        }

        public String dockerRemoveImagesType() {
            return dockerRemoveImagesType;
        }
    }
}

