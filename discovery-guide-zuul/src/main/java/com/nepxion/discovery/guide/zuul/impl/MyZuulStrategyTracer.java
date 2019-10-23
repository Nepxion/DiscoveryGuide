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
        Span span = tracer.buildSpan(DiscoveryConstant.DISCOVERY_TRACER_NAME).start();

        // 全链路灰度调用链输出到日志
        log(span);
        LOG.info("全链路灰度调用链输出到日志");

        // 全链路灰度调用链输出到Opentracing
        span.setTag(Tags.COMPONENT.getKey(), DiscoveryConstant.DISCOVERY_NAME);
        span.setTag("mobile", StringUtils.isNotEmpty(strategyContextHolder.getHeader("mobile")) ? strategyContextHolder.getHeader("mobile") : StringUtils.EMPTY);
        span.setTag("user", StringUtils.isNotEmpty(strategyContextHolder.getHeader("user")) ? strategyContextHolder.getHeader("user") : StringUtils.EMPTY);
        span.setTag(DiscoveryConstant.N_D_SERVICE_GROUP, strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_GROUP));
        span.setTag(DiscoveryConstant.N_D_SERVICE_TYPE, strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_TYPE));
        span.setTag(DiscoveryConstant.N_D_SERVICE_ID, strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_ID));
        span.setTag(DiscoveryConstant.N_D_SERVICE_ADDRESS, strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_ADDRESS));
        span.setTag(DiscoveryConstant.N_D_SERVICE_VERSION, strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_VERSION));
        span.setTag(DiscoveryConstant.N_D_SERVICE_REGION, strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_REGION));
        StrategyTracerContext.getCurrentContext().setContext(span);
        LOG.info("全链路灰度调用链输出到Opentracing");

        super.trace(context);
    }

    @Override
    public void release(RequestContext context) {
        // 全链路灰度调用链日志上下文清除
        MDC.clear();
        LOG.info("全链路灰度调用链日志上下文清除");

        // 全链路灰度调用链Opentracing上下文清除
        Span span = (Span) StrategyTracerContext.getCurrentContext().getContext();
        span.finish();
        StrategyTracerContext.clearCurrentContext();
        LOG.info("全链路灰度调用链Opentracing上下文清除");
    }

    private void log(Span span) {
        // 可以自定义traceid和spanid
        // MDC.put("traceid", "traceid=" + (StringUtils.isNotEmpty(strategyContextHolder.getHeader("traceid")) ? strategyContextHolder.getHeader("traceid") : StringUtils.EMPTY));
        // MDC.put("spanid", "spanid=" + (StringUtils.isNotEmpty(strategyContextHolder.getHeader("spanid")) ? strategyContextHolder.getHeader("spanid") : StringUtils.EMPTY));
        MDC.put("traceid", "traceid=" + span.context().toTraceId());
        MDC.put("spanid", "spanid=" + span.context().toSpanId());
        MDC.put("mobile", "mobile=" + (StringUtils.isNotEmpty(strategyContextHolder.getHeader("mobile")) ? strategyContextHolder.getHeader("mobile") : StringUtils.EMPTY));
        MDC.put("user", "user=" + (StringUtils.isNotEmpty(strategyContextHolder.getHeader("user")) ? strategyContextHolder.getHeader("user") : StringUtils.EMPTY));
        MDC.put(DiscoveryConstant.N_D_SERVICE_GROUP, "服务组名=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_GROUP));
        MDC.put(DiscoveryConstant.N_D_SERVICE_TYPE, "服务类型=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_TYPE));
        MDC.put(DiscoveryConstant.N_D_SERVICE_ID, "服务名=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_ID));
        MDC.put(DiscoveryConstant.N_D_SERVICE_ADDRESS, "地址=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_ADDRESS));
        MDC.put(DiscoveryConstant.N_D_SERVICE_VERSION, "版本=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_VERSION));
        MDC.put(DiscoveryConstant.N_D_SERVICE_REGION, "区域=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_REGION));
    }

    // Debug用
    @Override
    public Map<String, String> getDebugTraceMap() {
        Span span = (Span) StrategyTracerContext.getCurrentContext().getContext();

        return new ImmutableMap.Builder<String, String>()
                // 可以自定义traceid和spanid
                // .put("traceid", StringUtils.isNotEmpty(strategyContextHolder.getHeader("traceid")) ? strategyContextHolder.getHeader("traceid") : StringUtils.EMPTY)
                // .put("spanid", StringUtils.isNotEmpty(strategyContextHolder.getHeader("spanid")) ? strategyContextHolder.getHeader("spanid") : StringUtils.EMPTY)                
                .put("traceid", span.context().toTraceId())
                .put("spanid", span.context().toSpanId())
                .put("mobile", StringUtils.isNotEmpty(strategyContextHolder.getHeader("mobile")) ? strategyContextHolder.getHeader("mobile") : StringUtils.EMPTY)
                .put("user", StringUtils.isNotEmpty(strategyContextHolder.getHeader("user")) ? strategyContextHolder.getHeader("user") : StringUtils.EMPTY)
                .build();
    }
}