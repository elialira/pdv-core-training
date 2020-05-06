package br.com.totvs.raas.core.exception;

import br.com.totvs.raas.core.exception.BusinessException;
import br.com.totvs.raas.core.exception.HasErrors;
import br.com.totvs.raas.core.validator.Violation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.join;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ViolationException extends BusinessException implements HasErrors<Violation> {

    private final static String DELIMITER = ", ";
    private final static String CODE = "exception.violation";

    private final Collection<Violation> violations;

    public ViolationException(String code, Collection<Violation> violations) {
        super(code);
        this.violations = violations;
    }

    public ViolationException(Collection<Violation> violations) {
        super(CODE);
        this.violations = violations;
    }

    @Override
    public Collection<Violation> getErrors() {
        return this.violations;
    }

    @Override
    public String getMessage() {
        return join(DELIMITER, getMessages());
    }

    private List<String> getMessages() {
        return violations.stream()
                .map(Object::toString)
                .collect(Collectors.toList());
    }

}
