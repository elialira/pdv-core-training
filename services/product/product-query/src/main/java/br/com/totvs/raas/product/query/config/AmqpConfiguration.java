package br.com.totvs.raas.product.query.config;

import com.rabbitmq.client.Channel;
import org.axonframework.extensions.amqp.eventhandling.AMQPMessageConverter;
import org.axonframework.extensions.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfiguration {

    @Value("${amqp.exchange:product.events}")
    private String exchangeName;

    @Value("${amqp.queue:product.query.events}")
    private String queueName;

    @Value("${amqp.dlx.queue:product.query.events.dlx}")
    private String deadLetterQueueName;

    @Bean
    public Exchange exchange() {
        return ExchangeBuilder
                .fanoutExchange(exchangeName)
                .build();
    }

    @Bean
    public Queue queue() {
        return QueueBuilder
                .durable(queueName)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", deadLetterQueueName)
                .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder
                .durable(deadLetterQueueName)
                .build();
    }

    @Bean
    public Binding binding(Exchange exchange, Queue queue) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with("*")
                .noargs();
    }

    @Autowired
    public void configure(AmqpAdmin amqpAdmin, Exchange exchange,
                          Queue deadLetterQueue, Queue queue,
                          Binding binding) {
        amqpAdmin.declareExchange(exchange);
        amqpAdmin.declareQueue(deadLetterQueue);
        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareBinding(binding);
    }

    @Bean
    public SpringAMQPMessageSource inputMessageSource(AMQPMessageConverter messageConverter) {
        return new SpringAMQPMessageSource(messageConverter) {
            @RabbitListener(queues = "${amqp.queue:product.query.events}")
            public void onMessage(Message message, Channel channel) {
                super.onMessage(message, channel);
            }
        };
    }

}
