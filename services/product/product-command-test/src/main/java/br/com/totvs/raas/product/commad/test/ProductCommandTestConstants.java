package br.com.totvs.raas.product.commad.test;

public interface ProductCommandTestConstants {

    interface TenantConstants {
        String ID = "7cfd79b6-8b22-4ff2-a3e5-e5bec0806636";
    }

    interface BrandConstants {
        String TYPE = "Brand";
        String CREATED = "br.com.totvs.raas.product.common.event.BrandCreatedEvent";
        String CHANGED = "br.com.totvs.raas.product.common.event.BrandChangedEvent";
        String DELETED = "br.com.totvs.raas.product.common.event.BrandDeletedEvent";
    }

}
