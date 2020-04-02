package com.nepxion.discovery.guide.service.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.HashMap;
import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;

import com.nepxion.discovery.plugin.strategy.service.monitor.ServiceStrategyMonitorAdapter;
import com.nepxion.discovery.plugin.strategy.service.monitor.ServiceStrategyMonitorInterceptor;

// 自定义服务端接口方法的入参输出到调用链Span上
// parameterMap格式：
// key为入参名
// value为入参值
public class MyServiceStrategyMonitorAdapter implements ServiceStrategyMonitorAdapter {
    @Override
    public Map<String, String> getCustomizationMap(ServiceStrategyMonitorInterceptor interceptor, MethodInvocation invocation, Map<String, Object> parameterMap) {
        Map<String, String> customizationMap = new HashMap<String, String>();
        for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
            String methodName = entry.getKey();
            String argument = entry.getValue().toString();

            customizationMap.put(methodName, argument);
        }

        return customizationMap;
    }
}