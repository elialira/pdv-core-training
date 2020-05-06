package br.com.totvs.raas.core.exception;

import br.com.totvs.raas.core.i18.Translation;
import br.com.totvs.raas.core.i18.Translator;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ToString
@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public abstract class BusinessException extends RuntimeException implements Translation {

    @Getter
    private final String code;
    private final Object[] arguments;

    public BusinessException(String code, Object... arguments) {
        this.code = code;
        this.arguments = arguments;
    }

    @Override
    public String toMessage(Translator translator) {
        return translator.translate(code, arguments);
    }

}
