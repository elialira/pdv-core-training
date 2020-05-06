package br.com.totvs.raas.web.autoconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Configuration
@ComponentScan("br.com.totvs.raas.web")
public class WebMvcConfiguration extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {

    private List<Locale> LOCALES = Arrays.asList(
            new Locale("en"),
            new Locale("pt", "BR"),
            new Locale("es", "ES"));

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        return Optional.ofNullable(request)
                .map(this::getAcceptLanguage)
                .map(this::toLocale)
                .orElse(new Locale("pt", "BR"));
    }

    private String getAcceptLanguage(HttpServletRequest request) {
        return request.getHeader("Accept-Language");
    }

    private Locale toLocale(String language) {
        return Locale.lookup(Locale.LanguageRange.parse(language), LOCALES);
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource resourceBundle = new ResourceBundleMessageSource();
        resourceBundle.setBasename("i18n/messages");
        resourceBundle.setDefaultEncoding("UTF-8");
        resourceBundle.setUseCodeAsDefaultMessage(true);
        return resourceBundle;
    }

}
