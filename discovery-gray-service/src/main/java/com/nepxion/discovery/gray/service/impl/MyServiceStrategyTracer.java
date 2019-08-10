package com.nepxion.discovery.gray.service.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.aopalliance.intercept.MethodInvocation;
import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.service.tracer.DefaultServiceStrategyTracer;
import com.nepxion.discovery.plugin.strategy.service.tracer.ServiceStrategyTracerInterceptor;

public class MyServiceStrategyTracer extends DefaultServiceStrategyTracer {
    private static final Logger LOG = LoggerFactory.getLogger(MyServiceStrategyTracer.class);

    @Override
    public void trace(ServiceStrategyTracerInterceptor interceptor, MethodInvocation invocation) {
        super.trace(interceptor, invocation);

        // 输出到日志
        MDC.put("traceid", "traceid=" + strategyContextHolder.getHeader("traceid"));
        MDC.put("spanid", "spanid=" + strategyContextHolder.getHeader("spanid"));
        MDC.put(DiscoveryConstant.N_D_SERVICE_GROUP, "服务组名=" + pluginAdapter.getGroup());
        MDC.put(DiscoveryConstant.N_D_SERVICE_TYPE, "服务类型=" + pluginAdapter.getServiceType());
        MDC.put(DiscoveryConstant.N_D_SERVICE_ID, "服务名=" + pluginAdapter.getServiceId());
        MDC.put(DiscoveryConstant.N_D_SERVICE_ADDRESS, "地址=" + pluginAdapter.getHost() + ":" + pluginAdapter.getPort());
        MDC.put(DiscoveryConstant.N_D_SERVICE_VERSION, "版本=" + pluginAdapter.getVersion());
        MDC.put(DiscoveryConstant.N_D_SERVICE_REGION, "区域=" + pluginAdapter.getRegion());

        LOG.info("全链路灰度调用链输出");

        String methodName = interceptor.getMethodName(invocation);
        LOG.info("methodName={}", methodName);

        Object[] arguments = interceptor.getArguments(invocation);
        for (int i = 0; i < arguments.length; i++) {
            LOG.info("arguments[{}]={}", i, arguments[i].toString());
        }
    }

    @Override
    public void release(ServiceStrategyTracerInterceptor interceptor, MethodInvocation invocation) {
        MDC.clear();

        LOG.info("全链路灰度调用链清除");
    }
}