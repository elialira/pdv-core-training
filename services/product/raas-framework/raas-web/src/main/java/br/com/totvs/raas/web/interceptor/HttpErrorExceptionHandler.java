package br.com.totvs.raas.web.interceptor;

import br.com.totvs.raas.core.i18.Translator;
import br.com.totvs.raas.web.ApiError;
import br.com.totvs.raas.web.interceptor.translator.ErrorTranslator;
import br.com.totvs.raas.web.interceptor.translator.ThrowableErrorTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class HttpErrorExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private Translator translator;

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                             HttpHeaders headers, HttpStatus status,
                                                             WebRequest request) {
        ErrorTranslator error = new ThrowableErrorTranslator(ex, translator);

        ApiError apiError = ApiError.builder()
                .code(error.toCode())
                .message(error.toMessage())
                .errors(error.getErrors())
                .build();

        return super.handleExceptionInternal(ex, apiError, headers, status, request);
    }

}
