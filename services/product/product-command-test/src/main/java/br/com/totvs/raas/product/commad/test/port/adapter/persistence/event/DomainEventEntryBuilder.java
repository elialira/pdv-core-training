package br.com.totvs.raas.product.commad.test.port.adapter.persistence.event;

import org.axonframework.common.ReflectionUtils;
import org.axonframework.eventhandling.DomainEventMessage;
import org.axonframework.eventhandling.GenericDomainEventMessage;
import org.axonframework.eventsourcing.eventstore.jpa.DomainEventEntry;
import org.axonframework.messaging.GenericMessage;
import org.axonframework.messaging.Message;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;

import java.time.Instant;
import java.util.Map;
import java.util.stream.StreamSupport;

public class DomainEventEntryBuilder {

    private String id;
    private String type;
    private String payloadType;
    private Long version;
    private Map<String, Object> payload;
    private Serializer serializer;

    public DomainEventEntryBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public DomainEventEntryBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public DomainEventEntryBuilder withPayloadType(String payloadType) {
        this.payloadType = payloadType;
        return this;
    }

    public DomainEventEntryBuilder withVersion(Long version) {
        this.version = version;
        return this;
    }

    public DomainEventEntryBuilder withPayload(Map<String, Object> payload) {
        this.payload = payload;
        return this;
    }

    public DomainEventEntryBuilder withJacksonSerializer() {
        this.serializer = JacksonSerializer.builder().build();
        return this;
    }

    public DomainEventEntry build() {
        DomainEventEntry domainEventEntry = new DomainEventEntry(createDomainEventMessage(), serializer);
        setPayloadType(domainEventEntry);
        return domainEventEntry;
    }

    private void setPayloadType(DomainEventEntry domainEventEntry) {
        StreamSupport.stream(ReflectionUtils.fieldsOf(DomainEventEntry.class).spliterator(), false)
                .filter(field -> field.getName().equalsIgnoreCase("payloadType"))
                .forEach(field -> ReflectionUtils.setFieldValue(field, domainEventEntry, payloadType));
    }

    private DomainEventMessage createDomainEventMessage() {
        return  new GenericDomainEventMessage(type, id, version,
                createMessage(), Instant.now());
    }

    private Message createMessage() {
        return new GenericMessage(payload);
    }

}
