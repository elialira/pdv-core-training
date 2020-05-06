package br.com.totvs.raas.product.commad.test.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@EnableAutoConfiguration
@EnableCaching
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:application.yml")
@ComponentScan(basePackages = {"br.com.totvs.raas.product.commad.test"})
public class AppConfig {
}
