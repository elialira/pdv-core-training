package org.testcontainers.containers;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class DockerComposeFile extends File {

    private DockerComposeFile(@NotNull String pathname) {
        super(pathname);
    }

    private DockerComposeFile(String parent, @NotNull String child) {
        super(parent, child);
    }

    public static DockerComposeFile root() {
        return new DockerComposeFile("");
    }

    public DockerComposeFile parent() {
        return new DockerComposeFile(getAbsoluteFile().getParent());
    }

    public DockerComposeFile dockerCompose() {
        return new DockerComposeFile(this.getAbsolutePath(), "docker-compose.yml");
    }

    public DockerComposeFile dockerCompose(String profile) {
        return new DockerComposeFile(this.getAbsolutePath(), "docker-compose-" + profile + ".yml");
    }

}
