package br.com.totvs.raas.product.commad.test.steps.brand;

import br.com.totvs.raas.product.commad.test.ProductCommandTestConstants.BrandConstants;
import br.com.totvs.raas.product.commad.test.steps.event.EventMessageContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BrandContext {

    @Autowired
    private EventMessageContext context;

    public Map<String, Object> getCreated(String aggregateId) {
        return context.findBy(aggregateId, BrandConstants.CREATED);
    }

    public Map<String, Object> getChanged(String aggregateId) {
        return context.findBy(aggregateId, BrandConstants.CHANGED);
    }

    public Map<String, Object> getDeleted(String aggregateId) {
        return context.findBy(aggregateId, BrandConstants.DELETED);
    }
}
