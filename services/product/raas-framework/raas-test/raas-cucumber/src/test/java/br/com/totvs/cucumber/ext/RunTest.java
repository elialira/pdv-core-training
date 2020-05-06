package br.com.totvs.cucumber.ext;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/run"},
        glue = {"br.com.totvs.cucumber.ext"},
        plugin = {"br.com.totvs.cucumber.ext.listener.ExtentCucumberFormatter:target/report/index.html"}
)
public class RunTest {

    @AfterClass
    public static void setup() {
//        Reporter.loadXMLConfig(new File("src/main/resources/extent-config.xml"));
//        Reporter.setSystemInfo("user", System.getProperty("user.name"));
//        Reporter.setSystemInfo("os", "Mac OSX");
//        Reporter.setTestRunnerOutput("Sample test runner output message");
    }


}
