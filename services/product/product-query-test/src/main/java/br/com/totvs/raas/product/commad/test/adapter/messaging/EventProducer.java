package br.com.totvs.raas.product.commad.test.adapter.messaging;

import br.com.totvs.raas.product.commad.test.ProductQueryTestConstants.MessageConstants;
import br.com.totvs.raas.product.commad.test.adapter.shared.Identifier;
import br.com.totvs.raas.product.commad.test.steps.SequenceContext;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

import static br.com.totvs.raas.product.commad.test.adapter.shared.Mapper.toBytes;

@Component
public class EventProducer {

    @Value("${amqp.exchange:product.events}")
    private String exchangeName;

    @Value("${amqp.routing_key:br.com.totvs.raas.product.common.event}")
    private String routingKey;

    @Autowired
    private SequenceContext sequenceContext;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String id, String type, Map<String, Object> body) {
        Message message = createMessage(id, type, body);
        rabbitTemplate.send(exchangeName, routingKey, message);
    }

    private Message createMessage(String id, String type, Map<String, Object> body) {
        MessageProperties properties = createMessageProperties(id,
                sequenceContext.next(),
                Identifier.next(),
                type);
        return new Message(toBytes(body), properties);
    }

    private MessageProperties createMessageProperties(String id, Long sequence,
                                                      String messageId, String type) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageConstants.JSON_CONTEXT_TYPE);
        messageProperties.setHeader(MessageConstants.AGGREGATE_ID, id);
        messageProperties.setHeader(MessageConstants.AGGREGATE_SEQ, sequence);
        messageProperties.setHeader(MessageConstants.MESSAGE_ID, messageId);
        messageProperties.setHeader(MessageConstants.TYPE, type);
        return messageProperties;
    }

}
