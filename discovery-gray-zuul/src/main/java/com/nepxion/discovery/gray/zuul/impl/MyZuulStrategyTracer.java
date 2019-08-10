package com.nepxion.discovery.gray.zuul.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
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

        // 自定义调用链
        Map<String, String> customerTraceMap = getCustomerTraceMap();
        if (MapUtils.isNotEmpty(customerTraceMap)) {
            for (Map.Entry<String, String> entry : customerTraceMap.entrySet()) {
                MDC.put(entry.getKey(), entry.getKey() + "=" + entry.getValue());
            }
        }

        // 灰度路由调用链
        MDC.put(DiscoveryConstant.N_D_SERVICE_GROUP, "服务组名=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_GROUP));
        MDC.put(DiscoveryConstant.N_D_SERVICE_TYPE, "服务类型=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_TYPE));
        MDC.put(DiscoveryConstant.N_D_SERVICE_ID, "服务名=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_ID));
        MDC.put(DiscoveryConstant.N_D_SERVICE_ADDRESS, "地址=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_ADDRESS));
        MDC.put(DiscoveryConstant.N_D_SERVICE_VERSION, "版本=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_VERSION));
        MDC.put(DiscoveryConstant.N_D_SERVICE_REGION, "区域=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_REGION));

        LOG.info("全链路灰度调用链输出");

        LOG.info("request={}", context.getRequest());
    }

    @Override
    public void release(RequestContext context) {
        MDC.clear();

        LOG.info("全链路灰度调用链清除");
    }

    @Override
    public Map<String, String> getCustomerTraceMap() {
        Map<String, String> customerTraceMap = new LinkedHashMap<String, String>();
        customerTraceMap.put("traceid", strategyContextHolder.getHeader("traceid"));
        customerTraceMap.put("spanid", strategyContextHolder.getHeader("spanid"));
        customerTraceMap.put("mobile", strategyContextHolder.getHeader("mobile"));

        return customerTraceMap;
    }
}