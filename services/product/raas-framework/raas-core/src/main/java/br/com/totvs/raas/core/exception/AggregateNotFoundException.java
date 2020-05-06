package br.com.totvs.raas.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class AggregateNotFoundException extends BusinessException {

    public AggregateNotFoundException(String code) {
        super(code);
    }

}

