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
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class DiscoveryGuideServiceB2 {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "b2");

        // 彩色旗标显示设置
        System.setProperty("nepxion.banner.shown.ansi.mode", "true");

        // 阿里巴巴Sentinel Dashboard设置
        System.setProperty("project.name", "guide-service-b2");
        System.setProperty("csp.sentinel.dashboard.server", System.getProperty("middleware.host", "localhost") + ":8075");
        System.setProperty("csp.sentinel.api.port", "4002");

        new SpringApplicationBuilder(DiscoveryGuideServiceB2.class).run(args);
    }
}