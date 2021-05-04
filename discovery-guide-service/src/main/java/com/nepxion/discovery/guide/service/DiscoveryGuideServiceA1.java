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
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import com.nepxion.banner.BannerConstant;
import com.nepxion.discovery.guide.service.impl.MyDiscoveryEnabledStrategy;
import com.nepxion.discovery.guide.service.impl.MyServiceSentinelRequestOriginAdapter;
import com.nepxion.discovery.guide.service.impl.MyServiceStrategyMonitorAdapter;
import com.nepxion.discovery.guide.service.impl.MyServiceStrategyRouteFilter;
import com.nepxion.discovery.guide.service.impl.MyStrategyTracerAdapter;
import com.nepxion.discovery.guide.service.impl.MySubscriber;
import com.nepxion.discovery.plugin.strategy.adapter.DiscoveryEnabledStrategy;
import com.nepxion.discovery.plugin.strategy.adapter.StrategyTracerAdapter;
import com.nepxion.discovery.plugin.strategy.service.filter.ServiceStrategyRouteFilter;
import com.nepxion.discovery.plugin.strategy.service.monitor.ServiceStrategyMonitorAdapter;
import com.nepxion.discovery.plugin.strategy.service.sentinel.adapter.ServiceSentinelRequestOriginAdapter;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableAsync
public class DiscoveryGuideServiceA1 {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "a1");

        // 是否要显示旗标
        System.setProperty(BannerConstant.BANNER_SHOWN, "true");
        // 是否把旗标渲染成彩色
        System.setProperty(BannerConstant.BANNER_SHOWN_ANSI_MODE, "true");

        // 阿里巴巴Sentinel Dashboard设置
        /*System.setProperty("project.name", "guide-service-a1");
        System.setProperty("csp.sentinel.dashboard.server", System.getProperty("middleware.host", "localhost") + ":8075");
        System.setProperty("csp.sentinel.api.port", "3001");*/

        new SpringApplicationBuilder(DiscoveryGuideServiceA1.class).run(args);
    }

    // ========== 下面的Bean配置以及impl目录下的类都是高级应用，可以全部删除 ==========
    // 自定义负载均衡的蓝绿灰度策略
    @Bean
    public DiscoveryEnabledStrategy discoveryEnabledStrategy() {
        return new MyDiscoveryEnabledStrategy();
    }

    // 自定义路由过滤的蓝绿灰度策略
    @Bean
    public ServiceStrategyRouteFilter serviceStrategyRouteFilter() {
        return new MyServiceStrategyRouteFilter();
    }

    // 自定义组合式熔断
    @Bean
    public ServiceSentinelRequestOriginAdapter serviceSentinelRequestOriginAdapter() {
        return new MyServiceSentinelRequestOriginAdapter();
    }

    // 自定义服务端接口方法的入参输出到调用链Span上
    @Bean
    public ServiceStrategyMonitorAdapter serviceStrategyMonitorAdapter() {
        return new MyServiceStrategyMonitorAdapter();
    }

    // 自定义调用链上下文参数
    @Bean
    public StrategyTracerAdapter strategyTracerAdapter() {
        return new MyStrategyTracerAdapter();
    }

    // 自定义事件总线订阅
    @Bean
    public MySubscriber mySubscriber() {
        return new MySubscriber();
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}