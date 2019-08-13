package com.nepxion.discovery.gray.test;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import com.nepxion.discovery.gray.test.config.ConfigOperation;

@SpringBootApplication
public class DiscoveryGrayTestApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DiscoveryGrayTestApplication.class).run(args);
    }

    @Bean
    public ConfigOperation configOperation() {
        return new ConfigOperation();
    }
}