package com.nepxion.discovery.gray.zuul.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;

import com.nepxion.discovery.plugin.strategy.zuul.filter.ZuulStrategyRouteFilter;

// 当Header中传来的用户为张三，执行一条路由路径；李四，执行另一条路由路径
public class MyRouteFilter extends ZuulStrategyRouteFilter {
    @Override
    protected String getRouteVersion() {
        String user = strategyContextHolder.getHeader("user");

        if (StringUtils.equals(user, "zhangsan")) {
            return "{\"discovery-gray-service-a\":\"1.0\", \"discovery-gray-service-b\":\"1.1\"}";
        } else if (StringUtils.equals(user, "lisi")) {
            return "{\"discovery-gray-service-a\":\"1.1\", \"discovery-gray-service-b\":\"1.0\"}";
        }

        return super.getRouteVersion();
    }
}