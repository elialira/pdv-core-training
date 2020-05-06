package br.com.totvs.raas.web.interceptor.translator;

import br.com.totvs.raas.core.exception.HasErrors;
import br.com.totvs.raas.core.i18.TranslatedMessage;
import br.com.totvs.raas.core.i18.Translation;
import br.com.totvs.raas.core.i18.Translator;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class ThrowableErrorTranslator implements ErrorTranslator {

    private final Optional<Throwable> cause;
    private final Translator translator;

    public ThrowableErrorTranslator(Throwable cause, Translator translator) {
        this.cause = Optional.ofNullable(cause);
        this.translator = translator;
    }

    public boolean noError() {
        return !cause.isPresent();
    }

    public String toMessage() {
        return cause.filter(Translation.class::isInstance)
                .map(Translation.class::cast)
                .map(translation -> translation.toMessage(translator))
                .or(() -> cause.map(Throwable::getMessage))
                .orElse(null);
    }

    public String toCode() {
        return cause
                .map(Throwable::getClass)
                .map(Class::getSimpleName)
                .map(this::toCode)
                .orElse(null);
    }

    private String toCode(String exceptionName) {
        return "exception." + exceptionName.replace("Exception", "")
                .replaceAll("([a-z])([A-Z])", "$1_$2")
                .toLowerCase();
    }

    @Override
    public Collection<Object> getErrors() {
        return cause.filter(HasErrors.class::isInstance)
                .map(HasErrors.class::cast)
                .map(HasErrors::getErrors)
                .map(this::translation)
                .orElse(null);
    }

    private Collection<Object> translation(Collection<Object> errors) {
        Collection<Object> translatedErrors = errors.stream()
                           .filter(Translation.class::isInstance)
                           .map(Translation.class::cast)
                           .map(this::createTranslatedMessage)
                           .collect(Collectors.toList());
        if (translatedErrors.isEmpty()) {
            return errors;
        }
        return translatedErrors;
    }

    private Object createTranslatedMessage(Translation translation) {
        return new TranslatedMessage(translation,
                translation.toMessage(translator));
    }

}
