package br.com.totvs.raas.product.commad.test.adapter.messaging;

import br.com.totvs.raas.product.commad.test.ProductQueryTestConstants.*;
import br.com.totvs.raas.product.commad.test.adapter.shared.Identifier;
import br.com.totvs.raas.product.commad.test.steps.VersionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BrandEventProducer {

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private VersionContext versionContext;

    public String create(String name, Boolean activated) {
        String id = Identifier.next();
        Long version = versionContext.next(id);
        Map<String, Object> brand = createBrand(id, name, activated, version);
        eventProducer.send(id, BrandConstants.CREATED, brand);
        return id;
    }

    public void change(String id, String name, Boolean status) {
        Long version = versionContext.getAndNext(id);
        Map<String, Object> brand = createBrand(id, name, status, version);
        eventProducer.send(id, BrandConstants.CHANGED, brand);
    }

    private Map<String, Object> createBrand(String id, String name,
                                            Boolean activated, Long version) {
        Map<String, Object> brand = new HashMap<>();
        brand.put("id", id);
        brand.put("name", name);
        brand.put("activated", activated);
        brand.put("version", version);
        brand.put("tenantId", TenantConstants.ID);
        return brand;
    }
}
