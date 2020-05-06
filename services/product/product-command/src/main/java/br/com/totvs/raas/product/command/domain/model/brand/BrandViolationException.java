package br.com.totvs.raas.product.command.domain.model.brand;

import br.com.totvs.raas.core.exception.ViolationException;
import br.com.totvs.raas.core.validator.Violation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collection;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BrandViolationException extends ViolationException {

    private static final String CODE = "exception.brand_violation";

    public BrandViolationException(Collection<Violation> violations) {
        super(CODE, violations);
    }

}
