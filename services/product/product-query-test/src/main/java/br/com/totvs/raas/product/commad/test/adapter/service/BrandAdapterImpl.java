package br.com.totvs.raas.product.commad.test.adapter.service;

import br.com.totvs.raas.core.test.context.Result;
import br.com.totvs.raas.product.commad.test.steps.brand.BrandAdapter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BrandAdapterImpl extends AbstractService implements BrandAdapter {

    private static final String BRANDS = "api/brands/";

    public BrandAdapterImpl() {
        super(BRANDS);
    }

    @Override
    public Result findBy(String id) {
        return get(id);
    }

    @Override
    public Result findAll() {
        return getAll();
    }

    private Map<String, Object> createBrand(String name, Boolean activated) {
        Map<String, Object> brand = new HashMap<>();
        brand.put("name", name);
        brand.put("activated", activated);
        return brand;
    }
}
