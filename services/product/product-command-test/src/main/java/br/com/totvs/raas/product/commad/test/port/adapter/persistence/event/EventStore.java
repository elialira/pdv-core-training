package br.com.totvs.raas.product.commad.test.port.adapter.persistence.event;

import java.util.Map;

public interface EventStore {

    Map<String, Object> findBy(String aggregateId, String type);

    String save(String type, String payloadType, Map<String, Object> payload);

    void deleteAll();

}
