package com.nepxion.discovery.guide.service.middleware;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
public class RocketMQOperation {
    private static final Logger LOG = LoggerFactory.getLogger(RocketMQOperation.class);

    private static final String DESTINATION = "MyDestination";
    private static final String CONSUMER_GROUP = "MyGroup";

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void operate() {
        produce();
    }

    public void produce() {
        String message = "MyMessage";

        rocketMQTemplate.convertAndSend(DESTINATION, message);

        LOG.info("::::: RocketMQ produced, destination={}, message={}", DESTINATION, message);
    }

    @Service
    @RocketMQMessageListener(topic = DESTINATION, consumerGroup = CONSUMER_GROUP)
    public static class Subscriber implements RocketMQListener<String> {
        @Override
        public void onMessage(String message) {
            LOG.info("::::: RocketMQ subscribed, message={}", message);
        }
    }
}