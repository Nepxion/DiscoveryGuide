package com.nepxion.discovery.guide.gateway.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.contrib.tracerresolver.TracerResolver;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMap;
import io.opentracing.tag.Tags;
import reactor.core.publisher.Mono;

import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;

// Opentracing不支持Webflux，该方案采用来自下面的链接
// https://github.com/opentracing-contrib/java-spring-jaeger/issues/62
@ConditionalOnProperty(value = "opentracing.jaeger.enabled", havingValue = "true")
@Configuration
public class MyGatewayStrategyTracerConfiguration {
    private Logger log = LoggerFactory.getLogger(MyGatewayStrategyTracerConfiguration.class);

    public MyGatewayStrategyTracerConfiguration() {
        log.info("Initiating tracing configuration...");
    }

    @Bean
    @Order(-1)
    public GlobalFilter tracingFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpRequest.Builder requestBuilder = request.mutate();

            Span span = null;

            try {
                Tracer tracer = TracerResolver.resolveTracer();

                // TODO: This is not right, we should create the span based on the downstream request
                span = tracer.buildSpan("FORWARD")
                        .withTag(Tags.SPAN_KIND.getKey(), Tags.SPAN_KIND_CLIENT)
                        .withTag(Tags.HTTP_METHOD.getKey(), request.getMethod().name())
                        .withTag(Tags.HTTP_URL.getKey(), request.getPath().toString())
                        .asChildOf(tracer.activeSpan())
                        .start();

                tracer.inject(span.context(), Format.Builtin.HTTP_HEADERS, new RequestBuilderCarrier(requestBuilder));
            } catch (Exception e) {
                log.warn("Tracing not initiated", e);
            }

            Span finalSpan = span;

            return chain.filter(exchange.mutate()
                    .request(requestBuilder.build())
                    .build()).then(Mono.fromRunnable(() -> {
                        ServerHttpResponse response = exchange.getResponse();

                        if (finalSpan != null) {
                            finalSpan.setTag(Tags.HTTP_STATUS, response.getStatusCode().value());
                            finalSpan.finish();
                        }
                    }));
        };
    }

    public static class RequestBuilderCarrier implements TextMap {
        private final ServerHttpRequest.Builder requestBuilder;

        RequestBuilderCarrier(ServerHttpRequest.Builder requestBuilder) {
            this.requestBuilder = requestBuilder;
        }

        @Override
        public Iterator<Map.Entry<String, String>> iterator() {
            throw new UnsupportedOperationException("carrier is write-only");
        }

        @Override
        public void put(String key, String value) {
            requestBuilder.headers(headers -> headers.add(key, value));
        }
    }
}