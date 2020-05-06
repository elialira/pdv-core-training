package br.com.totvs.raas.product.commad.test;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features",
        plugin = {"pretty",
                "br.com.totvs.cucumber.ext.listener.ExtentCucumberFormatter:target/report/index.html"})
public class ProductQueryTestApplication {

    private static final int SUCCESSFULLY_COMPLETED = 0;

    public static void main(String[] args) {
        JUnitCore.runClasses(ProductQueryTestApplication.class)
                .getFailures()
                .forEach(System.out::print);

        System.exit(SUCCESSFULLY_COMPLETED);
    }

}