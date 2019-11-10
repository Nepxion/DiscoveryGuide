package com.nepxion.discovery.guide.service.middleware;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Ankeway
 * @version 1.0
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQOperation {
    private static final Logger LOG = LoggerFactory.getLogger(RabbitMQOperation.class);

    private static final String EXCHANGE = "MyGroup";
    private static final String ROUTINGKEY = "MyDataId";

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Bean
    public Queue queue() {
        return new Queue(ROUTINGKEY);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding bindingExchangeMessages(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY);
    }

    public void operate() {
        produce();
    }

    public void produce() {
        String message = "MyMessage";

        amqpTemplate.convertAndSend(EXCHANGE, ROUTINGKEY, message);

        LOG.info("::::: RabbitMQ produced, exchange={}, routingKey={}, message={}", EXCHANGE, ROUTINGKEY, message);
    }

    @RabbitListener(queues = ROUTINGKEY)
    public void subscribe(String message) {
        LOG.info("::::: RabbitMQ subscribed, message={}", message);
    }
}