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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.nepxion.discovery.guide.gateway.impl.MyDiscoveryEnabledStrategy;
import com.nepxion.discovery.guide.gateway.impl.MyGatewayStrategyLoggerTracer;
import com.nepxion.discovery.plugin.strategy.adapter.DiscoveryEnabledStrategy;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.gateway.tracer.GatewayStrategyTracer;

@SpringBootApplication
@EnableDiscoveryClient
public class DiscoveryGuideGateway {
    public static void main(String[] args) {
        System.setProperty("nepxion.banner.shown.ansi.mode", "true");

        new SpringApplicationBuilder(DiscoveryGuideGateway.class).run(args);
    }

    // 自定义负载均衡的灰度策略
    @Bean
    public DiscoveryEnabledStrategy discoveryEnabledStrategy() {
        return new MyDiscoveryEnabledStrategy();
    }

    // 自定义灰度路由策略
    /*@Bean
    public GatewayStrategyRouteFilter gatewayStrategyRouteFilter() {
        return new MyGatewayStrategyRouteFilter();
    }*/

    // 自定义调用链和灰度调用链通过MDC输出到日志
    @Bean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACE_ENABLED, matchIfMissing = false)
    public GatewayStrategyTracer gatewayStrategyLoggerTracer() {
        return new MyGatewayStrategyLoggerTracer();
    }
}