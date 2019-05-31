package com.nepxion.discovery.gray.zuul;

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
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.nepxion.discovery.plugin.strategy.zuul.filter.ZuulStrategyRouteFilter;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class DiscoveryGrayZuul {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DiscoveryGrayZuul.class).run(args);
    }

    @Bean
    public ZuulStrategyRouteFilter zuulStrategyRouteFilter() {
        return new ZuulStrategyRouteFilter();
    }

    @Bean
    public DiscoveryGrayZuulEnabledStrategy zuulEnabledStrategy() {
        return new DiscoveryGrayZuulEnabledStrategy();
    }
}