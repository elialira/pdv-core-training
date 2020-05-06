package br.com.totvs.raas.product.commad.test.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.Nullable;

import java.util.Optional;
import java.util.Properties;

public class YamlPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource resource) {
        Properties propertiesFromYaml = loadYamlProperties(resource.getResource());

        profileResource(resource)
                .map(this::loadYamlProperties)
                .ifPresent(propertiesFromYaml::putAll);

        String sourceName = name != null ? name : resource.getResource().getFilename();

        return new PropertiesPropertySource(sourceName, propertiesFromYaml);
    }

    private Properties loadYamlProperties(Resource resource) {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(resource);
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    private Optional<ClassPathResource> profileResource(EncodedResource resource) {
        String resourceName = resource.getResource().getFilename();
        return activeProfiles()
                .stream()
                .map(profile -> resourceName.replaceFirst("\\.yml", String.format("-%s.yml", profile)))
                .map(ClassPathResource::new)
                .filter(Resource::exists)
                .findFirst();
    }

    private Optional<String[]> activeProfiles() {
        return Optional.ofNullable(System.getProperty("spring.profiles.active"))
                .map(profiles -> profiles.split(","));
    }

}
