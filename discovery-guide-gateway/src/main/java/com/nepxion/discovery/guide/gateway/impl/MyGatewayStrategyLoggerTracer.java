package com.nepxion.discovery.guide.gateway.impl;

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

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ServerWebExchange;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.gateway.tracer.DefaultGatewayStrategyTracer;

// 自定义调用链和灰度调用链通过MDC输出到日志
public class MyGatewayStrategyLoggerTracer extends DefaultGatewayStrategyTracer {
    private static final Logger LOG = LoggerFactory.getLogger(MyGatewayStrategyLoggerTracer.class);

    @Override
    public void trace(ServerWebExchange exchange) {
        super.trace(exchange);

        // 自定义调用链
        MDC.put("traceid", "traceid=" + strategyContextHolder.getHeader("traceid"));
        MDC.put("spanid", "spanid=" + strategyContextHolder.getHeader("spanid"));
        MDC.put("mobile", "mobile=" + strategyContextHolder.getHeader("mobile"));
        MDC.put("user", "user=" + strategyContextHolder.getHeader("user"));

        // 灰度路由调用链
        MDC.put(DiscoveryConstant.N_D_SERVICE_GROUP, "服务组名=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_GROUP));
        MDC.put(DiscoveryConstant.N_D_SERVICE_TYPE, "服务类型=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_TYPE));
        MDC.put(DiscoveryConstant.N_D_SERVICE_ID, "服务名=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_ID));
        MDC.put(DiscoveryConstant.N_D_SERVICE_ADDRESS, "地址=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_ADDRESS));
        MDC.put(DiscoveryConstant.N_D_SERVICE_VERSION, "版本=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_VERSION));
        MDC.put(DiscoveryConstant.N_D_SERVICE_REGION, "区域=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_REGION));

        LOG.info("全链路灰度调用链输出到日志");

        LOG.info("request={}", exchange.getRequest());
    }

    @Override
    public void release(ServerWebExchange exchange) {
        MDC.clear();

        LOG.info("全链路灰度调用链日志上下文清除");
    }

    // Debug用
    @Override
    public Map<String, String> getDebugTraceMap() {
        Map<String, String> debugTraceMap = new LinkedHashMap<String, String>();
        debugTraceMap.put("traceid", strategyContextHolder.getHeader("traceid"));
        debugTraceMap.put("spanid", strategyContextHolder.getHeader("spanid"));
        debugTraceMap.put("mobile", strategyContextHolder.getHeader("mobile"));
        debugTraceMap.put("user", strategyContextHolder.getHeader("user"));

        return debugTraceMap;
    }
}