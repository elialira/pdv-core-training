package br.com.totvs.raas.product.commad.test.port.adapter.persistence;

import br.com.totvs.raas.product.commad.test.ProductCommandTestConstants.*;
import br.com.totvs.raas.product.commad.test.port.adapter.persistence.event.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class BrandEventRepository {

    @Autowired
    private EventStore eventStore;

    @Autowired
    private BrandRepository brandRepository;

    public String save(String name, Boolean status) {
        Map<String, Object> brand = createBrand(name, status);
        brandRepository.save((String) brand.get("id"),(String) brand.get("name"),(String) brand.get("tenantId"));
        return eventStore.save(BrandConstants.TYPE, BrandConstants.CREATED, brand);
    }

    public Map<String, Object> findCreatedBy(String aggregateId) {
        return eventStore.findBy(aggregateId, BrandConstants.CREATED);
    }

    public Map<String, Object> findChangedBy(String aggregateId) {
        return eventStore.findBy(aggregateId, BrandConstants.CHANGED);
    }

    private Map<String, Object> createBrand(String name, Boolean activated) {
        Map<String, Object> brand = new HashMap<>();
        brand.put("id",  UUID.randomUUID().toString());
        brand.put("name", name);
        brand.put("activated", activated);
        brand.put("tenantId", TenantConstants.ID);
        return brand;
    }

}
