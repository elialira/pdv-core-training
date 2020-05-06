package br.com.totvs.raas.product.commad.test.steps.brand;

import br.com.totvs.raas.core.test.context.Result;

public interface BrandAdapter {

    Result findBy(String id);

    Result findAll();

}
