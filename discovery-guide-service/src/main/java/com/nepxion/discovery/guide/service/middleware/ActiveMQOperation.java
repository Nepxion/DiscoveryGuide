package com.nepxion.discovery.guide.service.middleware;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;

@Configuration
public class ActiveMQOperation {
    private static final Logger LOG = LoggerFactory.getLogger(ActiveMQOperation.class);

    private static final String DESTINATION = "MyDestination";

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void operate() {
        produce();
    }

    public void produce() {
        String message = "MyMessage";

        jmsMessagingTemplate.convertAndSend(DESTINATION, message);

        LOG.info("::::: ActiveMQ produced, destination={}, message={}", DESTINATION, message);
    }

    @JmsListener(destination = DESTINATION)
    public void subscribe(String message) {
        LOG.info("::::: ActiveMQ subscribed, message={}", message);
    }
}