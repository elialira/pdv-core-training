package br.com.totvs.raas.product.commad.test.port.adapter.messaging;

import br.com.totvs.raas.product.commad.test.port.adapter.shared.Mapper;
import br.com.totvs.raas.product.commad.test.steps.event.EventMessage;
import br.com.totvs.raas.product.commad.test.steps.event.EventMessageContext;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class EventListener {

    interface Constants {
        String AGGREGATE_ID = "axon-message-aggregate-id";
        String TYPE = "axon-message-type";
        String VERSION = "axon-message-aggregate-seq";
    }

    @Autowired
    private EventMessageContext context;

    @RabbitListener(queues = "${amqp.queue:test.product.events}")
    public void receive(Message message) {
        context.add(EventMessage.builder()
                .aggregateId(getAggregateId(message))
                .type(getType(message))
                .version(getVersion(message))
                .payload(getBody(message))
                .build());
    }

    private Map<String,Object> getBody(Message message) {
        return Optional.of(message)
                .map(Message::getBody)
                .map(Mapper::toMap)
                .orElse(null);
    }

    private String getAggregateId(Message message) {
        return toString(getHeader(message, Constants.AGGREGATE_ID));
    }

    private String getType(Message message) {
        return toString(getHeader(message, Constants.TYPE));
    }

    private String toString(Optional<Object> value) {
        return value.filter(String.class::isInstance)
                .map(String.class::cast)
                .orElse(null);
    }

    private Long getVersion(Message message) {
        return toLong(getHeader(message, Constants.VERSION));
    }

    private Long toLong(Optional<Object> value) {
        return value.filter(Long.class::isInstance)
                .map (Long.class::cast)
                .orElse(null);
    }

    private Optional<Object> getHeader(Message message, String key) {
        return Optional.of(message)
                .map(Message::getMessageProperties)
                .map(MessageProperties::getHeaders)
                .map(headers -> headers.get(key));
    }

}
