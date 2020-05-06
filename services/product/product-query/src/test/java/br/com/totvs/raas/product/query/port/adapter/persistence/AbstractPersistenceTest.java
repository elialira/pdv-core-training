package br.com.totvs.raas.product.query.port.adapter.persistence;

import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.CustomDockerComposeContainer;
import org.testcontainers.containers.DockerComposeFile;
import org.testcontainers.containers.wait.strategy.Wait;

import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.time.Duration;

@SpringBootTest
@Testcontainers
public abstract class AbstractPersistenceTest {

    @Container
    public static CustomDockerComposeContainer containers =
            new CustomDockerComposeContainer(DockerComposeFile.root().parent().dockerCompose())
                    .withServices("rabbitmq", "query-update-db")
                    .waitingFor("query-db",
                            Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(300)))
                    .waitingFor("rabbitmq",
                            Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(300)))
                    .withLocalCompose();

}
