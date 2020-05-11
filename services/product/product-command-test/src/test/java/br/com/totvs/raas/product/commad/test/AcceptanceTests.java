package br.com.totvs.raas.product.commad.test;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.testcontainers.containers.CustomDockerComposeContainer;
import org.testcontainers.containers.DockerComposeFile;
import org.testcontainers.containers.wait.strategy.Wait;

import java.time.Duration;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features"/*,
                plugin = "br.com.totvs.cucumber.ext.listener.ExtentCucumberFormatter:target/report/index.html"*/)
public class AcceptanceTests {



//    @ClassRule
//    public static CustomDockerComposeContainer environment =
//            new CustomDockerComposeContainer(DockerComposeFile.root()
//                    .parent()
//                    .dockerCompose())
//                    .withServices("command")
//                    .waitingFor
//                            ("command",
//                            Wait.forListeningPort()
//                                    .withStartupTimeout(Duration.ofSeconds(300)))
//                    .withLocalCompose();

}
