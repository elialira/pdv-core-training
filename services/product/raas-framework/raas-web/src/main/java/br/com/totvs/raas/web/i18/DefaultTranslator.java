package br.com.totvs.raas.web.i18;

import br.com.totvs.raas.core.i18.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class DefaultTranslator implements Translator {

    private static ResourceBundleMessageSource messageSource;

    @Autowired
    public DefaultTranslator(ResourceBundleMessageSource messageSource) {
        DefaultTranslator.messageSource = messageSource;
    }

    @Override
    public String translate(String code, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, locale);
    }

}
