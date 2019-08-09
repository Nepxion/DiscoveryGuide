package com.nepxion.discovery.gray.zuul.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.zuul.tracer.DefaultZuulStrategyTracer;
import com.netflix.zuul.context.RequestContext;

public class MyZuulStrategyTracer extends DefaultZuulStrategyTracer {
    private static final Logger LOG = LoggerFactory.getLogger(MyZuulStrategyTracer.class);

    @Override
    public void trace(RequestContext context) {
        super.trace(context);

        System.out.println("request=" + context.getRequest());
        System.out.println("mobile=" + strategyContextHolder.getHeader("mobile"));

        // 输出到日志
        MDC.put(DiscoveryConstant.N_D_SERVICE_GROUP, strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_GROUP));
        MDC.put(DiscoveryConstant.N_D_SERVICE_TYPE, strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_TYPE));
        MDC.put(DiscoveryConstant.N_D_SERVICE_ID, strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_ID));
        MDC.put(DiscoveryConstant.N_D_SERVICE_ADDRESS, strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_ADDRESS));
        MDC.put(DiscoveryConstant.N_D_SERVICE_VERSION, strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_VERSION));
        MDC.put(DiscoveryConstant.N_D_SERVICE_REGION, strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_REGION));

        LOG.info("调用链输出");

        // release方法的调用视业务系统的日志使用方式而定，可以自行在后面自定义的Filter中释放。需要注意，不能有线程切换
        release(context);
    }

    @Override
    public void release(RequestContext context) {
        MDC.clear();
    }
}