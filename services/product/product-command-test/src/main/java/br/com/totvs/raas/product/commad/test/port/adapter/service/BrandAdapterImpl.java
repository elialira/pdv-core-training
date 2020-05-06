package br.com.totvs.raas.product.commad.test.port.adapter.service;

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
    public Result save(String name, Boolean activated) {
        Map<String, Object> brand = createBrand(name, activated);

        return post(brand);
    }

    @Override
    public Result change(String id, String name, Boolean activated) {
        Map<String, Object> brand = createBrand(name, activated);

        return put(id, brand);
    }

    @Override
    public Result delete(String id) {
        return super.delete(id);
    }

    private Map<String, Object> createBrand(String name, Boolean activated) {
        Map<String, Object> brand = new HashMap<>();
        brand.put("name", name);
        brand.put("activated", activated);
        return brand;
    }
}
