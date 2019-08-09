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

        String methodName = interceptor.getMethodName(invocation);
        System.out.println("methodName=" + methodName);

        System.out.println("arguments=");
        Object[] arguments = interceptor.getArguments(invocation);
        for (int i = 0; i < arguments.length; i++) {
            System.out.println("  " + arguments[i].toString());
        }

        // 输出到日志
        MDC.put(DiscoveryConstant.N_D_SERVICE_GROUP, pluginAdapter.getGroup());
        MDC.put(DiscoveryConstant.N_D_SERVICE_TYPE, pluginAdapter.getServiceType());
        MDC.put(DiscoveryConstant.N_D_SERVICE_ID, pluginAdapter.getServiceId());
        MDC.put(DiscoveryConstant.N_D_SERVICE_ADDRESS, pluginAdapter.getHost() + ":" + pluginAdapter.getPort());
        MDC.put(DiscoveryConstant.N_D_SERVICE_VERSION, pluginAdapter.getVersion());
        MDC.put(DiscoveryConstant.N_D_SERVICE_REGION, pluginAdapter.getRegion());

        LOG.info("调用链输出");

        // 不需要执行release方法，内置的AOP会自动执行它
    }

    @Override
    public void release() {
        MDC.clear();

        LOG.info("调用链清除");
    }
}