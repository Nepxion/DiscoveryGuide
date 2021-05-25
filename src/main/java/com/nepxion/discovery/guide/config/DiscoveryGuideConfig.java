package com.nepxion.discovery.guide.config;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.nepxion.discovery.guide.config.processor.MyConfigProcessor;

@SpringBootApplication
public class DiscoveryGuideConfig {
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryGuideConfig.class, args);
    }

    @Bean
    public MyConfigProcessor myConfigProcessor() {
        return new MyConfigProcessor();
    }
}