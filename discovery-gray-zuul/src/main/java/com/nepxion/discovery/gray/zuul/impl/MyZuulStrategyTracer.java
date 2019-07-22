package com.nepxion.discovery.gray.zuul.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.plugin.strategy.zuul.tracer.DefaultZuulStrategyTracer;
import com.netflix.zuul.context.RequestContext;

public class MyZuulStrategyTracer extends DefaultZuulStrategyTracer {
    @Override
    public void trace(RequestContext context) {
        super.trace(context);

        System.out.println("request=" + context.getRequest());
        System.out.println("mobile=" + strategyContextHolder.getHeader("mobile"));

        // 输出到日志
    }
}