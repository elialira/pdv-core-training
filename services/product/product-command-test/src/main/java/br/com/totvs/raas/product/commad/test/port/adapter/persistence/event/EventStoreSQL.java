package br.com.totvs.raas.product.commad.test.port.adapter.persistence.event;

public interface EventStoreSQL {

    String DELETE = "delete from DomainEventEntry";

    String FIND_BY_AGGREGATE_ID_AND_TYPE_AND_TYPE = new StringBuilder("from DomainEventEntry ")
            .append("where aggregateIdentifier = :" + Param.AGGREGATE_ID)
            .append(" and payloadType = :" + Param.TYPE)
            .append(" and sequenceNumber = :" + Param.VERSION)
            .toString();

    interface Param {
        String AGGREGATE_ID = "aggregateId";
        String TYPE = "type";
        String VERSION = "version";
    }

}
