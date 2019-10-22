package com.nepxion.discovery.guide.service.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.tag.Tags;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nepxion.discovery.plugin.strategy.service.tracer.DefaultServiceStrategyTracer;
import com.nepxion.discovery.plugin.strategy.service.tracer.ServiceStrategyTracerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

// 自定义调用链和灰度调用链输出到Zipkin
public class MyServiceStrategyZipkinTracer extends DefaultServiceStrategyTracer {
    private static final Logger LOG = LoggerFactory.getLogger(MyServiceStrategyZipkinTracer.class);

    @Autowired
    private Tracer tracer;

    private Tracer.SpanBuilder spanBuilder;

    private Span span;

    @Override
    public void trace(ServiceStrategyTracerInterceptor interceptor, MethodInvocation invocation) {
        super.trace(interceptor, invocation);

        Method method = invocation.getMethod();
        String methodName = interceptor.getMethodName(invocation);
        String className = method.getDeclaringClass().getName();
        Tracer.SpanBuilder spanBuilder = tracer.buildSpan(methodName);
        spanBuilder.withTag(Tags.COMPONENT.getKey(),"discovery");
        spanBuilder.withTag("methodName",methodName);
        spanBuilder.withTag("className",className);
        Span span = spanBuilder.start();
        span.setTag(DiscoveryConstant.N_D_SERVICE_GROUP, "服务组名=" + pluginAdapter.getGroup());
        span.setTag(DiscoveryConstant.N_D_SERVICE_TYPE, "服务类型=" + pluginAdapter.getServiceType());
        span.setTag(DiscoveryConstant.N_D_SERVICE_ID, "服务名=" + pluginAdapter.getServiceId());
        span.setTag(DiscoveryConstant.N_D_SERVICE_ADDRESS, "地址=" + pluginAdapter.getHost() + ":" + pluginAdapter.getPort());
        span.setTag(DiscoveryConstant.N_D_SERVICE_VERSION, "版本=" + pluginAdapter.getVersion());
        span.setTag(DiscoveryConstant.N_D_SERVICE_REGION, "区域=" + pluginAdapter.getRegion());
        LOG.info("全链路灰度调用链输出到Zipkin");
    }

    @Override
    public void error(ServiceStrategyTracerInterceptor interceptor, MethodInvocation invocation, Throwable e) {
        Map<String,Object> exceptionMap = new HashMap<>();
        exceptionMap.put("event",Tags.ERROR.getKey());
        exceptionMap.put("error.object",e);
        span.log(exceptionMap);
        LOG.info("全链路灰度调用链异常输出到Zipkin");
    }

    @Override
    public void release(ServiceStrategyTracerInterceptor interceptor, MethodInvocation invocation) {
        super.release(interceptor, invocation);
        if (span != null && tracer != null){
            span.finish();
        }
    }
}