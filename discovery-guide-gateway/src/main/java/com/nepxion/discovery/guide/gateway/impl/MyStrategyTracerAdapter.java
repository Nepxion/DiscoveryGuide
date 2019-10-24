package com.nepxion.discovery.guide.gateway.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.ImmutableMap;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.adapter.DefaultStrategyTracerAdapter;

// 自定义调用链上下文参数的创建
// 对于getTraceId和getSpanId方法，在Opentracing等调用链中间件引入的情况下，由调用链中间件决定，在这里定义不会起作用；在Opentracing等调用链中间件未引入的情况下，在这里定义才有效，下面代码中表示从Http Header中获取，并全链路传递
// 对于getCustomizationMap方法，表示输出到调用链中的定制化业务参数，可以同时输出到日志和Opentracing等调用链中间件，下面代码中表示从Http Header中获取，并全链路传递
public class MyStrategyTracerAdapter extends DefaultStrategyTracerAdapter {
    @Override
    public String getTraceId() {
        return StringUtils.isNotEmpty(strategyContextHolder.getHeader(DiscoveryConstant.TRACE_ID)) ? strategyContextHolder.getHeader(DiscoveryConstant.TRACE_ID) : StringUtils.EMPTY;
    }

    @Override
    public String getSpanId() {
        return StringUtils.isNotEmpty(strategyContextHolder.getHeader(DiscoveryConstant.SPAN_ID)) ? strategyContextHolder.getHeader(DiscoveryConstant.SPAN_ID) : StringUtils.EMPTY;
    }

    @Override
    public Map<String, String> getCustomizationMap() {
        return new ImmutableMap.Builder<String, String>()
                .put("mobile", StringUtils.isNotEmpty(strategyContextHolder.getHeader("mobile")) ? strategyContextHolder.getHeader("mobile") : StringUtils.EMPTY)
                .put("user", StringUtils.isNotEmpty(strategyContextHolder.getHeader("user")) ? strategyContextHolder.getHeader("user") : StringUtils.EMPTY)
                .build();
    }
}