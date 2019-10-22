package com.nepxion.discovery.guide.service.impl;

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
import io.opentracing.tag.Tags;

import java.util.HashMap;
import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.service.tracer.DefaultServiceStrategyTracer;
import com.nepxion.discovery.plugin.strategy.service.tracer.ServiceStrategyTracerInterceptor;

// 自定义调用链和灰度调用链输出到Zipkin
public class MyServiceStrategyZipkinTracer extends DefaultServiceStrategyTracer {
    private static final Logger LOG = LoggerFactory.getLogger(MyServiceStrategyZipkinTracer.class);

    @Autowired
    private Tracer tracer;

    private Span span;

    @Override
    public void trace(ServiceStrategyTracerInterceptor interceptor, MethodInvocation invocation) {
        super.trace(interceptor, invocation);

        Tracer.SpanBuilder spanBuilder = tracer.buildSpan(DiscoveryConstant.SERVICE_TYPE);
        span = spanBuilder.start();

        // 自定义调用链
        span.setTag(Tags.COMPONENT.getKey(), DiscoveryConstant.DISCOVERY_NAME);
        span.setTag("class name", interceptor.getMethod(invocation).getDeclaringClass().getName());
        span.setTag("method name", interceptor.getMethodName(invocation));
        span.setTag("mobile", strategyContextHolder.getHeader("mobile"));
        span.setTag("user", strategyContextHolder.getHeader("user"));

        // 灰度路由调用链
        span.setTag(DiscoveryConstant.N_D_SERVICE_GROUP, pluginAdapter.getGroup());
        span.setTag(DiscoveryConstant.N_D_SERVICE_TYPE, pluginAdapter.getServiceType());
        span.setTag(DiscoveryConstant.N_D_SERVICE_ID, pluginAdapter.getServiceId());
        span.setTag(DiscoveryConstant.N_D_SERVICE_ADDRESS, pluginAdapter.getHost() + ":" + pluginAdapter.getPort());
        span.setTag(DiscoveryConstant.N_D_SERVICE_VERSION, pluginAdapter.getVersion());
        span.setTag(DiscoveryConstant.N_D_SERVICE_REGION, pluginAdapter.getRegion());

        LOG.info("全链路灰度调用链输出到Zipkin");
    }

    @Override
    public void error(ServiceStrategyTracerInterceptor interceptor, MethodInvocation invocation, Throwable e) {
        Map<String, Object> exceptionMap = new HashMap<String, Object>();
        exceptionMap.put("event", Tags.ERROR.getKey());
        exceptionMap.put("error.object", e);

        span.log(exceptionMap);

        LOG.info("全链路灰度调用链异常输出到Zipkin");
    }

    @Override
    public void release(ServiceStrategyTracerInterceptor interceptor, MethodInvocation invocation) {
        if (tracer != null && span != null) {
            span.finish();
        }

        LOG.info("全链路灰度调用链Zipkin上下文清除");
    }
}