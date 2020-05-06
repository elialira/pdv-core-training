package br.com.totvs.raas.product.commad.test.port.adapter.persistence.event;

import static br.com.totvs.raas.product.commad.test.port.adapter.persistence.event.EventStoreSQL.*;
import br.com.totvs.raas.product.commad.test.port.adapter.shared.Mapper;
import br.com.totvs.raas.product.commad.test.port.adapter.shared.Wait;
import br.com.totvs.raas.product.commad.test.steps.event.AggregateContext;
import org.axonframework.eventsourcing.eventstore.jpa.DomainEventEntry;
import org.axonframework.serialization.SerializedObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Map;
import java.util.Optional;

@Repository
public class EventStoreImpl implements EventStore {

    @Autowired
    private AggregateContext context;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public Map<String, Object> findBy(String aggregateId, String type) {
        Long version = context.getPersisted().next(aggregateId);

        return Wait.notNull(() -> {
            try {
                return to(find(aggregateId, type, version));
            } catch (NoResultException cause) {
                return null;
            }
        });
    }

    private DomainEventEntry find(String aggregateId, String type, Long version) {
        return (DomainEventEntry) entityManager
                    .createQuery(EventStoreSQL.FIND_BY_AGGREGATE_ID_AND_TYPE_AND_TYPE)
                    .setParameter(Param.AGGREGATE_ID, aggregateId)
                    .setParameter(Param.TYPE, type)
                    .setParameter(Param.VERSION, version)
                    .getSingleResult();
    }

    @Transactional
    public void deleteAll() {
        entityManager.createQuery(EventStoreSQL.DELETE)
                .executeUpdate();
    }

    @Transactional
    public String save(String type, String payloadType, Map<String, Object> payload) {
        DomainEventEntry domainEventEntry = new DomainEventEntryBuilder()
                .withId((String) payload.get("id"))
                .withType(type)
                .withPayloadType(payloadType)
                .withPayload(payload)
                .withVersion(context.next((String) payload.get("id")))
                .withJacksonSerializer()
                .build();

        entityManager.persist(domainEventEntry);

        return (String) payload.get("id");
    }

    private Map<String, Object> to(DomainEventEntry event) {
        return Optional.ofNullable(event)
                .map(DomainEventEntry::getPayload)
                .map(SerializedObject::getData)
                .map(Mapper::toMap)
                .orElse(null);
    }

}
