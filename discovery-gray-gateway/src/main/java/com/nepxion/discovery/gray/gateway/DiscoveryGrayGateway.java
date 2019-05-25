package com.nepxion.discovery.gray.gateway;

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
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.nepxion.discovery.gray.gateway.filter.GrayGatewayFilter;

@SpringBootApplication
@EnableDiscoveryClient
public class DiscoveryGrayGateway {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DiscoveryGrayGateway.class).run(args);
    }

    @Bean
    public GrayGatewayFilter grayGatewayFilter() {
        return new GrayGatewayFilter();
    }
}