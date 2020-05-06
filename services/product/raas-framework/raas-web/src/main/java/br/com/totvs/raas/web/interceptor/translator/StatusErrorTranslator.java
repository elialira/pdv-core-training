package br.com.totvs.raas.web.interceptor.translator;

import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.stream.Stream;

public class StatusErrorTranslator implements ErrorTranslator {

    private Optional<Integer> status;

    public StatusErrorTranslator(Integer status) {
        this.status = Optional.ofNullable(status);
    }

    @Override
    public boolean noError() {
        return !status.isPresent();
    }

    @Override
    public String toMessage() {
        return status.map(this::toHttpStatus)
                .map(HttpStatus::getReasonPhrase)
                .orElse(null);
    }

    @Override
    public String toCode() {
        return status.map(this::toHttpStatus)
                .map(HttpStatus::name)
                .map(String::toLowerCase)
                .map(statusName -> "exception." + statusName)
                .orElse(null);
    }

    private HttpStatus toHttpStatus(Integer status) {
        return Stream.of(HttpStatus.values())
                .filter(httpStatus -> httpStatus.value() == status)
                .findFirst()
                .orElse(null);
    }

}
