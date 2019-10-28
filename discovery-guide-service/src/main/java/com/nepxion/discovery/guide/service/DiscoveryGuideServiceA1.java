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
import org.springframework.context.annotation.Bean;

import com.nepxion.discovery.guide.service.impl.MyDiscoveryEnabledStrategy;
import com.nepxion.discovery.guide.service.impl.MyFeignStrategyInterceptorAdapter;
import com.nepxion.discovery.guide.service.impl.MyRestTemplateStrategyInterceptorAdapter;
import com.nepxion.discovery.guide.service.impl.MyServiceSentinelRequestOriginAdapter;
import com.nepxion.discovery.guide.service.impl.MyStrategyTracerAdapter;
import com.nepxion.discovery.plugin.strategy.adapter.DiscoveryEnabledStrategy;
import com.nepxion.discovery.plugin.strategy.adapter.StrategyTracerAdapter;
import com.nepxion.discovery.plugin.strategy.service.adapter.FeignStrategyInterceptorAdapter;
import com.nepxion.discovery.plugin.strategy.service.adapter.RestTemplateStrategyInterceptorAdapter;
import com.nepxion.discovery.plugin.strategy.service.sentinel.adapter.ServiceSentinelRequestOriginAdapter;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class DiscoveryGuideServiceA1 {
    public static void main(String[] args) {
        System.setProperty("nepxion.banner.shown.ansi.mode", "true");
        System.setProperty("spring.profiles.active", "a1");

        new SpringApplicationBuilder(DiscoveryGuideServiceA1.class).run(args);
    }

    // 自定义负载均衡的灰度策略
    @Bean
    public DiscoveryEnabledStrategy discoveryEnabledStrategy() {
        return new MyDiscoveryEnabledStrategy();
    }

    // 自定义灰度路由策略
    /*@Bean
    public ServiceStrategyRouteFilter serviceStrategyRouteFilter() {
        return new MyServiceStrategyRouteFilter();
    }*/

    // 自定义Feign拦截器中的Header传递
    @Bean
    public FeignStrategyInterceptorAdapter feignStrategyInterceptorAdapter() {
        return new MyFeignStrategyInterceptorAdapter();
    }

    // 自定义RestTemplate拦截器中的Header传递
    @Bean
    public RestTemplateStrategyInterceptorAdapter restTemplateStrategyInterceptorAdapter() {
        return new MyRestTemplateStrategyInterceptorAdapter();
    }

    // 自定义组合式熔断
    @Bean
    public ServiceSentinelRequestOriginAdapter ServiceSentinelRequestOriginAdapter() {
        return new MyServiceSentinelRequestOriginAdapter();
    }

    // 自定义调用链上下文参数
    @Bean
    public StrategyTracerAdapter strategyTracerAdapter() {
        return new MyStrategyTracerAdapter();
    }
}