package com.nepxion.discovery.guide.zuul;

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
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.nepxion.discovery.guide.zuul.impl.MyDiscoveryEnabledStrategy;
import com.nepxion.discovery.guide.zuul.impl.MyZuulStrategyTracer;
import com.nepxion.discovery.plugin.strategy.adapter.DiscoveryEnabledStrategy;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.zuul.tracer.ZuulStrategyTracer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class DiscoveryGuideZuul {
    public static void main(String[] args) {
        System.setProperty("nepxion.banner.shown.ansi.mode", "true");

        new SpringApplicationBuilder(DiscoveryGuideZuul.class).run(args);
    }

    @Bean
    public DiscoveryEnabledStrategy discoveryEnabledStrategy() {
        return new MyDiscoveryEnabledStrategy();
    }

    /*@Bean
    public ZuulStrategyRouteFilter zuulStrategyRouteFilter() {
        return new MyZuulStrategyRouteFilter();
    }*/

    @Bean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACE_ENABLED, matchIfMissing = false)
    public ZuulStrategyTracer zuulStrategyTracer() {
        return new MyZuulStrategyTracer();
    }
}