package com.nepxion.discovery.guide.service.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nepxion.discovery.plugin.strategy.service.tracer.DefaultServiceStrategyTracer;
import com.nepxion.discovery.plugin.strategy.service.tracer.ServiceStrategyTracerInterceptor;

// 自定义调用链和灰度调用链输出到Zipkin
public class MyServiceStrategyZipkinTracer extends DefaultServiceStrategyTracer {
    private static final Logger LOG = LoggerFactory.getLogger(MyServiceStrategyZipkinTracer.class);

    @Override
    public void trace(ServiceStrategyTracerInterceptor interceptor, MethodInvocation invocation) {
        super.trace(interceptor, invocation);

        // 待实现

        LOG.info("全链路灰度调用链输出到Zipkin");
    }

    @Override
    public void error(ServiceStrategyTracerInterceptor interceptor, MethodInvocation invocation, Throwable e) {
        // 待实现

        LOG.info("全链路灰度调用链异常输出到Zipkin");
    }
}