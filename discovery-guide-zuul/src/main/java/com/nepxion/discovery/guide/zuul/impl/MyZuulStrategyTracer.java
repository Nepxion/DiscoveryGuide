package com.nepxion.discovery.guide.zuul.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author Shun Zhang
 * @version 1.0
 */

import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.tag.Tags;

import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableMap;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.tracer.StrategyTracerContext;
import com.nepxion.discovery.plugin.strategy.zuul.tracer.DefaultZuulStrategyTracer;
import com.netflix.zuul.context.RequestContext;

// 自定义调用链和灰度调用链输出到日志和Opentracing
public class MyZuulStrategyTracer extends DefaultZuulStrategyTracer {
    private static final Logger LOG = LoggerFactory.getLogger(MyZuulStrategyTracer.class);

    @Autowired
    private Tracer tracer;

    @Override
    public void trace(RequestContext context) {
        Span span = tracer.buildSpan(DiscoveryConstant.SPAN_NAME).start();
        StrategyTracerContext.getCurrentContext().setContext(span);

        super.trace(context);
        LOG.info("全链路灰度调用链输出到日志");

        span.setTag(Tags.COMPONENT.getKey(), DiscoveryConstant.TAG_COMPONENT_NAME);
        span.setTag(DiscoveryConstant.TRACE_ID, span.context().toTraceId());
        span.setTag(DiscoveryConstant.SPAN_ID, span.context().toSpanId());
        span.setTag(DiscoveryConstant.N_D_SERVICE_GROUP, strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_GROUP));
        span.setTag(DiscoveryConstant.N_D_SERVICE_TYPE, strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_TYPE));
        span.setTag(DiscoveryConstant.N_D_SERVICE_ID, strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_ID));
        span.setTag(DiscoveryConstant.N_D_SERVICE_ADDRESS, strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_ADDRESS));
        span.setTag(DiscoveryConstant.N_D_SERVICE_VERSION, strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_VERSION));
        span.setTag(DiscoveryConstant.N_D_SERVICE_REGION, strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_REGION));
        Map<String, String> customizationMap = getCustomizationMap();
        if (MapUtils.isNotEmpty(customizationMap)) {
            for (Map.Entry<String, String> entry : customizationMap.entrySet()) {
                span.setTag(entry.getKey(), entry.getValue());
            }
        }
        LOG.info("全链路灰度调用链输出到Opentracing");
    }

    @Override
    public void release(RequestContext context) {
        super.release(context);
        LOG.info("全链路灰度调用链日志上下文清除");

        Span span = getContextSpan();
        if (span != null) {
            span.finish();
        }
        StrategyTracerContext.clearCurrentContext();
        LOG.info("全链路灰度调用链Opentracing上下文清除");
    }

    @Override
    public String getTraceId() {
        Span span = getContextSpan();
        if (span != null) {
            return span.context().toTraceId();
        }

        return null;
    }

    @Override
    public String getSpanId() {
        Span span = getContextSpan();
        if (span != null) {
            return span.context().toSpanId();
        }

        return null;
    }

    @Override
    public Map<String, String> getCustomizationMap() {
        return new ImmutableMap.Builder<String, String>()
                .put("mobile", StringUtils.isNotEmpty(strategyContextHolder.getHeader("mobile")) ? strategyContextHolder.getHeader("mobile") : StringUtils.EMPTY)
                .put("user", StringUtils.isNotEmpty(strategyContextHolder.getHeader("user")) ? strategyContextHolder.getHeader("user") : StringUtils.EMPTY)
                .build();
    }

    private Span getContextSpan() {
        return (Span) StrategyTracerContext.getCurrentContext().getContext();
    }
}