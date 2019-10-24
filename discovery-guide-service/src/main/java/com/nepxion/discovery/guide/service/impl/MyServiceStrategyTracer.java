package com.nepxion.discovery.guide.service.impl;

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

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableMap;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.service.tracer.DefaultServiceStrategyTracer;
import com.nepxion.discovery.plugin.strategy.service.tracer.ServiceStrategyTracerInterceptor;
import com.nepxion.discovery.plugin.strategy.tracer.StrategyTracerContext;

// 自定义调用链和灰度调用链输出到日志和Opentracing
public class MyServiceStrategyTracer extends DefaultServiceStrategyTracer {
    private static final Logger LOG = LoggerFactory.getLogger(MyServiceStrategyTracer.class);

    @Autowired
    private Tracer tracer;

    @Override
    public void trace(ServiceStrategyTracerInterceptor interceptor, MethodInvocation invocation) {
        Span span = tracer.buildSpan(DiscoveryConstant.SPAN_NAME).start();
        StrategyTracerContext.getCurrentContext().setContext(span);

        super.trace(interceptor, invocation);
        LOG.info("全链路灰度调用链输出到日志");

        span.setTag(Tags.COMPONENT.getKey(), DiscoveryConstant.TAG_COMPONENT_NAME);
        span.setTag("class", interceptor.getMethod(invocation).getDeclaringClass().getName());
        span.setTag("method", interceptor.getMethodName(invocation));
        span.setTag(DiscoveryConstant.TRACE_ID, span.context().toTraceId());
        span.setTag(DiscoveryConstant.SPAN_ID, span.context().toSpanId());
        span.setTag(DiscoveryConstant.N_D_SERVICE_GROUP, pluginAdapter.getGroup());
        span.setTag(DiscoveryConstant.N_D_SERVICE_TYPE, pluginAdapter.getServiceType());
        span.setTag(DiscoveryConstant.N_D_SERVICE_ID, pluginAdapter.getServiceId());
        span.setTag(DiscoveryConstant.N_D_SERVICE_ADDRESS, pluginAdapter.getHost() + ":" + pluginAdapter.getPort());
        span.setTag(DiscoveryConstant.N_D_SERVICE_VERSION, pluginAdapter.getVersion());
        span.setTag(DiscoveryConstant.N_D_SERVICE_REGION, pluginAdapter.getRegion());
        Map<String, String> customizationMap = getCustomizationMap();
        if (MapUtils.isNotEmpty(customizationMap)) {
            for (Map.Entry<String, String> entry : customizationMap.entrySet()) {
                span.setTag(entry.getKey(), entry.getValue());
            }
        }
        LOG.info("全链路灰度调用链输出到Opentracing");
    }

    @Override
    public void error(ServiceStrategyTracerInterceptor interceptor, MethodInvocation invocation, Throwable e) {
        super.error(interceptor, invocation, e);
        LOG.info("全链路灰度调用链异常输出到日志");

        Span span = getContextSpan();
        if (span != null) {
            span.log(new ImmutableMap.Builder<String, Object>()
                    .put("event", Tags.ERROR.getKey())
                    .put("exception", e)
                    .build());
        }
        LOG.info("全链路灰度调用链异常输出到Opentracing");
    }

    @Override
    public void release(ServiceStrategyTracerInterceptor interceptor, MethodInvocation invocation) {
        super.release(interceptor, invocation);
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