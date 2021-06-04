package com.nepxion.discovery.guide.service.impl;

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

import org.aopalliance.intercept.MethodInvocation;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.service.monitor.ServiceStrategyMonitorAdapter;
import com.nepxion.discovery.plugin.strategy.service.monitor.ServiceStrategyMonitorInterceptor;

// 自定义类方法上入参和出参输出到调用链
// parameterMap格式：
// key为入参名
// value为入参值
public class MyServiceStrategyMonitorAdapter implements ServiceStrategyMonitorAdapter {
    @Override
    public Map<String, String> getCustomizationMap(ServiceStrategyMonitorInterceptor interceptor, MethodInvocation invocation, Map<String, Object> parameterMap, Object returnValue) {
        Map<String, String> customizationMap = new LinkedHashMap<String, String>();
        customizationMap.put(DiscoveryConstant.PARAMETER, parameterMap.toString());
        customizationMap.put(DiscoveryConstant.RETURN, returnValue != null ? returnValue.toString() : null);

        return customizationMap;
    }
}