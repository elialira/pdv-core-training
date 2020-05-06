package br.com.totvs.raas.web.interceptor;

import br.com.totvs.raas.core.i18.Translator;
import br.com.totvs.raas.web.ApiError;
import br.com.totvs.raas.web.interceptor.translator.ErrorTranslator;
import br.com.totvs.raas.web.interceptor.translator.StatusErrorTranslator;
import br.com.totvs.raas.web.interceptor.translator.SystemErrorTranslator;
import br.com.totvs.raas.web.interceptor.translator.ThrowableErrorTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.ServletException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static java.util.Objects.nonNull;

@Component
public class HttpErrorAttributes extends DefaultErrorAttributes {

    private static final String STATUS_CODE = "javax.servlet.error.status_code";

    @Autowired
    private Translator translator;

    @Override
    public Map<String, Object> getErrorAttributes(
            WebRequest webRequest, boolean includeStackTrace) {
        ApiError error = createError(webRequest);

        return error.toMap();
    }

    private ApiError createError(WebRequest webRequest) {
        ErrorTranslator error = createThrowableError(webRequest);
        if (error.noError()) {
            error = createStatusError(webRequest);
        }
        if (error.noError()) {
            error = createSystemError();
        }
        return ApiError.builder()
                .code(error.toCode())
                .message(error.toMessage())
                .errors(error.getErrors())
                .build();
    }

    public ErrorTranslator createThrowableError(WebRequest webRequest) {
        Throwable cause = super.getError(webRequest);
        if (nonNull(cause)) {
            while ((cause instanceof ExecutionException || cause instanceof ServletException)
                    && nonNull(cause.getCause())) {
                cause = cause.getCause();
            }
        }
        return new ThrowableErrorTranslator(cause, translator);
    }

    private ErrorTranslator createStatusError(WebRequest request) {
        Integer status = (Integer) request.getAttribute(STATUS_CODE,
                                                        RequestAttributes.SCOPE_REQUEST);

        return new StatusErrorTranslator(status);
    }

    private ErrorTranslator createSystemError() {
        return new SystemErrorTranslator();
    }

}
