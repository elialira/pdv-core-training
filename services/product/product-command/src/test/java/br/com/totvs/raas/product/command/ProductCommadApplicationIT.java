package br.com.totvs.raas.product.command;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.CustomDockerComposeContainer;
import org.testcontainers.containers.DockerComposeFile;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;

@SpringBootTest
@Testcontainers
public class ProductCommadApplicationIT {

	@Container
	public static CustomDockerComposeContainer containers =
			new CustomDockerComposeContainer(DockerComposeFile.root()
												.parent()
												.dockerCompose())
					.withServices("rac", "command-update-db")
					.waitingFor("rac",
							Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(300)))
					.waitingFor("command-db",
							Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(300)))
					.withLocalCompose();

	@Test
	public void contextLoads() { }

}
