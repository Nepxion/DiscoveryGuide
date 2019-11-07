package com.nepxion.discovery.guide.service.feign;

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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.guide.service.middleware.MongoDBOperation;
import com.nepxion.discovery.guide.service.middleware.MySQLOperation;
import com.nepxion.discovery.guide.service.middleware.RabbitMQOperation;
import com.nepxion.discovery.guide.service.middleware.RedisOperation;
import com.nepxion.discovery.guide.service.middleware.RocketMQOperation;

@RestController
@ConditionalOnProperty(name = DiscoveryConstant.SPRING_APPLICATION_NAME, havingValue = "discovery-guide-service-b")
public class BFeignImpl extends AbstractFeignImpl implements BFeign {
    private static final Logger LOG = LoggerFactory.getLogger(BFeignImpl.class);

    @Override
    @SentinelResource(value = "sentinel-resource", blockHandler = "handleBlock", fallback = "handleFallback")
    public String invoke(@PathVariable(value = "value") String value) {
        value = doInvoke(value);

        LOG.info("调用路径：{}", value);

        return value;
    }

    public String handleBlock(String value, BlockException e) {
        return value + "-> B server sentinel block, cause=" + e.getClass().getName() + ", rule=" + e.getRule() + ", limitApp=" + e.getRuleLimitApp();
    }

    public String handleFallback(String value) {
        return value + "-> B server sentinel fallback";
    }

    @Autowired
    private MongoDBOperation mongoDBOperation;
    
    @Autowired
    private MySQLOperation mySQLOperation;    

    @Autowired
    private RabbitMQOperation rabbitMQOperation;

    @Autowired
    private RedisOperation redisOperation;

    @Autowired
    private RocketMQOperation rocketMQOperation;
}