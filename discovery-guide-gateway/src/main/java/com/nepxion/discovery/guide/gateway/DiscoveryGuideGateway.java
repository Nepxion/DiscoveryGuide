package com.nepxion.discovery.guide.gateway;

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
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import com.nepxion.banner.BannerConstant;
import com.nepxion.discovery.guide.gateway.impl.MyDiscoveryEnabledStrategy;
import com.nepxion.discovery.guide.gateway.impl.MyGatewayStrategyRouteFilter;
import com.nepxion.discovery.guide.gateway.impl.MyStrategyTracerAdapter;
import com.nepxion.discovery.plugin.strategy.adapter.DiscoveryEnabledStrategy;
import com.nepxion.discovery.plugin.strategy.adapter.StrategyTracerAdapter;
import com.nepxion.discovery.plugin.strategy.gateway.filter.GatewayStrategyRouteFilter;

@SpringBootApplication
@EnableDiscoveryClient
public class DiscoveryGuideGateway {
    public static void main(String[] args) {
        // 是否要显示旗标
        System.setProperty(BannerConstant.BANNER_SHOWN, "true");
        // 是否把旗标渲染成彩色
        System.setProperty(BannerConstant.BANNER_SHOWN_ANSI_MODE, "true");

        new SpringApplicationBuilder(DiscoveryGuideGateway.class).run(args);
    }

    // ========== 下面的Bean配置以及impl目录下的类都是高级应用，可以全部删除 ==========
    // 自定义负载均衡的蓝绿灰度策略
    @Bean
    public DiscoveryEnabledStrategy discoveryEnabledStrategy() {
        return new MyDiscoveryEnabledStrategy();
    }

    // 自定义路由过滤的蓝绿灰度策略
    @Bean
    public GatewayStrategyRouteFilter gatewayStrategyRouteFilter() {
        return new MyGatewayStrategyRouteFilter();
    }

    // 自定义调用链上下文参数
    @Bean
    public StrategyTracerAdapter strategyTracerAdapter() {
        return new MyStrategyTracerAdapter();
    }

    // 自定义路由过滤的WebClient调用
    /*@Bean
    public GlobalFilter gatewayFilter() {
        return new MyGatewayFilter();
    }*/

    @Bean
    @LoadBalanced
    public WebClient.Builder webClient() {
       return WebClient.builder();
    }
}