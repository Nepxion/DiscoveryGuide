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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.nepxion.discovery.gray.gateway.impl.MyDiscoveryEnabledStrategy;
import com.nepxion.discovery.gray.gateway.impl.MyGatewayStrategyTracer;
import com.nepxion.discovery.plugin.strategy.adapter.DiscoveryEnabledStrategy;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.gateway.constant.GatewayStrategyConstant;
import com.nepxion.discovery.plugin.strategy.gateway.filter.CustomizationGatewayStrategyRouteFilter;
import com.nepxion.discovery.plugin.strategy.gateway.filter.GatewayStrategyRouteFilter;
import com.nepxion.discovery.plugin.strategy.gateway.tracer.GatewayStrategyTracer;

@SpringBootApplication
@EnableDiscoveryClient
public class DiscoveryGrayGateway {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DiscoveryGrayGateway.class).run(args);
    }

    @Bean
    public DiscoveryEnabledStrategy discoveryEnabledStrategy() {
        return new MyDiscoveryEnabledStrategy();
    }

    @Bean
    @ConditionalOnProperty(value = GatewayStrategyConstant.SPRING_APPLICATION_STRATEGY_GATEWAY_ROUTE_FILTER_ENABLED, matchIfMissing = true)
    public GatewayStrategyRouteFilter gatewayStrategyRouteFilter() {
        // return new MyRouteFilter();
        return new CustomizationGatewayStrategyRouteFilter();
    }

    @Bean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACE_ENABLED, matchIfMissing = false)
    public GatewayStrategyTracer gatewayStrategyTracer() {
        return new MyGatewayStrategyTracer();
    }
}