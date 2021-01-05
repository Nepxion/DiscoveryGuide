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
import org.springframework.scheduling.annotation.EnableAsync;

import com.nepxion.banner.BannerConstant;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableAsync
public class DiscoveryGuideServiceA2 {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "a2");

        // 是否要显示旗标
        System.setProperty(BannerConstant.BANNER_SHOWN, "true");
        // 是否把旗标渲染成彩色
        System.setProperty(BannerConstant.BANNER_SHOWN_ANSI_MODE, "true");

        // 阿里巴巴Sentinel Dashboard设置
        /*System.setProperty("project.name", "guide-service-a2");
        System.setProperty("csp.sentinel.dashboard.server", System.getProperty("middleware.host", "localhost") + ":8075");
        System.setProperty("csp.sentinel.api.port", "3002");*/

        new SpringApplicationBuilder(DiscoveryGuideServiceA2.class).run(args);
    }
}