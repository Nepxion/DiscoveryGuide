package com.nepxion.discovery.guide.zuul.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.nacos.template.NacosTemplate;
import com.nepxion.discovery.plugin.strategy.zuul.route.ZuulStrategyRoute;

/*
一个服务映射多个动态路由路径，配置中心界面上推送示例，如下
① 简单格式
[
    {
        "id": "route0",
        "serviceId": "discovery-guide-service-a",
        "path": "/x/**"
    },
    {
        "id": "route1",
        "serviceId": "discovery-guide-service-a",
        "path": "/y/**"
    }
]
如果希望一个服务只映射一个动态路由路径，则不需要id，可以简化为
[
    {
        "serviceId": "discovery-guide-service-a",
        "path": "/x/**"
    }
]

② 详细格式
[
    {
        "id": "route0",
        "serviceId": "discovery-guide-service-a",
        "path": "/x/**",
        "url": null,
        "stripPrefix": true,
        "retryable": null,
        "sensitiveHeaders": [],
        "customSensitiveHeaders": false
    },
    {
        "id": "route1",
        "serviceId": "discovery-guide-service-a",
        "path": "/y/**",
        "url": null,
        "stripPrefix": true,
        "retryable": null,
        "sensitiveHeaders": [],
        "customSensitiveHeaders": false
    }
]
*/

// 使用Nacos配置中心
public class MyZuulStrategyRouteTemplate extends NacosTemplate {
    private String group = "DEFAULT_GROUP";
    private String dataId = "zuul-route";

    @Autowired
    private ZuulStrategyRoute zuulStrategyRoute;

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public String getDataId() {
        return dataId;
    }

    @Override
    public String getConfigType() {
        return "Zuul dynamic route";
    }

    @Override
    public void callbackConfig(String config) {
        zuulStrategyRoute.updateAll(config);
    }
}

// 使用Apollo配置中心
/*public class MyZuulStrategyRouteTemplate extends ApolloTemplate {
    private String dataId = "zuul-route";

    @Autowired
    private ZuulStrategyRoute zuulStrategyRoute;

    @Override
    public String getKey() {
        return dataId;
    }

    @Override
    public String getConfigType() {
        return "Zuul dynamic route";
    }

    @Override
    public void callbackConfig(String config) {
        zuulStrategyRoute.updateAll(config);
    }
}*/