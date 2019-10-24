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

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.MDC;
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

        log(span);
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
        span.setTag("mobile", StringUtils.isNotEmpty(strategyContextHolder.getHeader("mobile")) ? strategyContextHolder.getHeader("mobile") : StringUtils.EMPTY);
        span.setTag("user", StringUtils.isNotEmpty(strategyContextHolder.getHeader("user")) ? strategyContextHolder.getHeader("user") : StringUtils.EMPTY);
        StrategyTracerContext.getCurrentContext().setContext(span);
        LOG.info("全链路灰度调用链输出到Opentracing");

        super.trace(context);
    }

    @Override
    public void release(RequestContext context) {
        MDC.clear();
        LOG.info("全链路灰度调用链日志上下文清除");

        Span span = (Span) StrategyTracerContext.getCurrentContext().getContext();
        span.finish();
        StrategyTracerContext.clearCurrentContext();
        LOG.info("全链路灰度调用链Opentracing上下文清除");
    }

    private void log(Span span) {
        MDC.put(DiscoveryConstant.TRACE_ID, DiscoveryConstant.TRACE_ID + "=" + span.context().toTraceId());
        MDC.put(DiscoveryConstant.SPAN_ID, DiscoveryConstant.SPAN_ID + "=" + span.context().toSpanId());
        MDC.put(DiscoveryConstant.N_D_SERVICE_GROUP, DiscoveryConstant.N_D_SERVICE_GROUP + "=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_GROUP));
        MDC.put(DiscoveryConstant.N_D_SERVICE_TYPE, DiscoveryConstant.N_D_SERVICE_TYPE + "=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_TYPE));
        MDC.put(DiscoveryConstant.N_D_SERVICE_ID, DiscoveryConstant.N_D_SERVICE_ID + "=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_ID));
        MDC.put(DiscoveryConstant.N_D_SERVICE_ADDRESS, DiscoveryConstant.N_D_SERVICE_ADDRESS + "=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_ADDRESS));
        MDC.put(DiscoveryConstant.N_D_SERVICE_VERSION, DiscoveryConstant.N_D_SERVICE_VERSION + "=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_VERSION));
        MDC.put(DiscoveryConstant.N_D_SERVICE_REGION, DiscoveryConstant.N_D_SERVICE_REGION + "=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_REGION));
        MDC.put("mobile", "mobile=" + (StringUtils.isNotEmpty(strategyContextHolder.getHeader("mobile")) ? strategyContextHolder.getHeader("mobile") : StringUtils.EMPTY));
        MDC.put("user", "user=" + (StringUtils.isNotEmpty(strategyContextHolder.getHeader("user")) ? strategyContextHolder.getHeader("user") : StringUtils.EMPTY));
    }

    @Override
    public Map<String, String> getDebugTraceMap() {
        Span span = (Span) StrategyTracerContext.getCurrentContext().getContext();

        return new ImmutableMap.Builder<String, String>()
                .put(DiscoveryConstant.TRACE_ID, span.context().toTraceId())
                .put(DiscoveryConstant.SPAN_ID, span.context().toSpanId())
                .put("mobile", StringUtils.isNotEmpty(strategyContextHolder.getHeader("mobile")) ? strategyContextHolder.getHeader("mobile") : StringUtils.EMPTY)
                .put("user", StringUtils.isNotEmpty(strategyContextHolder.getHeader("user")) ? strategyContextHolder.getHeader("user") : StringUtils.EMPTY)
                .build();
    }
}