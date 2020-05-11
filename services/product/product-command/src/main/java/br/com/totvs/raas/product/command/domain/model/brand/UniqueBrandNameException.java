package br.com.totvs.raas.product.command.domain.model.brand;

import br.com.totvs.raas.core.exception.BusinessException;

public class UniqueBrandNameException extends BusinessException {

    private static final String CODE = "exception.unique_product_name";

    public UniqueBrandNameException(String name) {
        super(CODE, name);
    }

}
