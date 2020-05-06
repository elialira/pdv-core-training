package br.com.totvs.raas.product.query.domain.brand;

import br.com.totvs.raas.core.exception.AggregateNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BrandNotFoundException extends AggregateNotFoundException {

    private static final String CODE = "exception.brand_not_found";

    public BrandNotFoundException() {
        super(CODE);
    }

}
