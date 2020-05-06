package br.com.totvs.raas.product.commad.test.steps.brand;

import br.com.totvs.raas.core.test.context.Result;

public interface BrandAdapter {

    Result save(String name, Boolean activated);

    Result change(String id, String name, Boolean activated);

    Result delete(String id);
}
