package com.nepxion.discovery.guide.service;

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
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
// Only for GraalVM Native Image
@LoadBalancerClients({ @LoadBalancerClient("discovery-guide-service-a"), @LoadBalancerClient("discovery-guide-service-b") })
public class DiscoveryGuideServiceB2 {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "b2");

        new SpringApplicationBuilder(DiscoveryGuideServiceB2.class).run(args);
    }
}