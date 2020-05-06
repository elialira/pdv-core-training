package br.com.totvs.raas.product.commad.test;

public interface ProductQueryTestConstants {

    int JUST_A = 1;
    int FIRST_ITEM = 0;

    interface TenantConstants {
        String ID = "totvs";
    }

    interface BrandConstants {
        Long VERSION_IS_O = 0L;
        String TYPE = "Brand";
        String CREATED = "br.com.totvs.raas.product.common.event.BrandCreatedEvent";
        String CHANGED = "br.com.totvs.raas.product.common.event.BrandChangedEvent";
        String DELETED = "br.com.totvs.raas.product.common.event.BrandDeletedEvent";
    }

    interface MessageConstants {
        String JSON_CONTEXT_TYPE = "application/json";
        String AGGREGATE_ID = "axon-message-aggregate-id";
        String AGGREGATE_SEQ = "axon-message-aggregate-seq";
        String AGGREGATE_TYPE = "axon-message-aggregate-type";
        String MESSAGE_ID = "axon-message-id";
        String REVISION = "axon-message-revision";
        String TIMESTAMP = "axon-message-timestamp";
        String TYPE = "axon-message-type";
        String CORRELATION_ID = "axon-metadata-correlationId";
        String TRACE_ID = "axon-metadata-traceId";
    }

}
