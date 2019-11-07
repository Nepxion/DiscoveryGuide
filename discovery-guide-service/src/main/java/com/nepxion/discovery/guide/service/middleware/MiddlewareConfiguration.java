package com.nepxion.discovery.guide.service.middleware;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.nepxion.discovery.guide.service.middleware")
public class MiddlewareConfiguration {
    @Bean
    public MongoDBOperation mongoDBOperation() {
        return new MongoDBOperation();
    }

    @Bean
    public MyBatisOperation myBatisOperation() {
        return new MyBatisOperation();
    }

    @Bean
    public RabbitMQOperation rabbitMQOperation() {
        return new RabbitMQOperation();
    }

    @Bean
    public RedisOperation redisOperation() {
        return new RedisOperation();
    }

    @Bean
    public RocketMQOperation rocketMQOperation() {
        return new RocketMQOperation();
    }
}