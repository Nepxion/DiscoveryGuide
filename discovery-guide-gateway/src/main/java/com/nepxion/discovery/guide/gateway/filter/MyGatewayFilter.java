package com.nepxion.discovery.guide.gateway.filter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import com.nepxion.discovery.plugin.strategy.gateway.context.GatewayStrategyContext;

public class MyGatewayFilter implements GlobalFilter, Ordered {
    private static final Logger LOG = LoggerFactory.getLogger(MyGatewayFilter.class);

    @Autowired
    private WebClient.Builder webClient;

    @Override
    public int getOrder() {
        return 10000;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String parameter = "MyGatewayFilter";

        return webClient.build().get().uri("http://discovery-guide-service-b/rest/" + parameter).retrieve().bodyToMono(String.class).flatMap(s -> {
            // 异步线程需要复制上下文
            GatewayStrategyContext.getCurrentContext().setExchange(exchange);

            LOG.info("网关上触发WebClient调用，返回值={}", s);

            return chain.filter(exchange);
        });
    }
}