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
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.nacos.proccessor.NacosProcessor;
import com.nepxion.discovery.plugin.strategy.zuul.route.ZuulStrategyRoute;

/*
一个服务映射多个动态路由路径，配置中心界面上推送示例，如下
① 精简配置
[
    {
        "id": "route0",
        "serviceId": "discovery-guide-service-a",
        "path": "/discovery-guide-service-a/**"
    },
    {
        "id": "route1",
        "serviceId": "discovery-guide-service-a",
        "path": "/x/**"
    },
    {
        "id": "route2",
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

② 完整配置
[
    {
        "id": "route0",
        "serviceId": "discovery-guide-service-a",
        "path": "/discovery-guide-service-a/**",
        "url": null,
        "stripPrefix": true,
        "retryable": null,
        "sensitiveHeaders": [],
        "customSensitiveHeaders": false
    },
    {
        "id": "route1",
        "serviceId": "discovery-guide-service-a",
        "path": "/x/**",
        "url": null,
        "stripPrefix": true,
        "retryable": null,
        "sensitiveHeaders": [],
        "customSensitiveHeaders": false
    },
    {
        "id": "route2",
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
public class MyZuulStrategyRouteProcessor extends NacosProcessor {
    private String group = "DEFAULT_GROUP";

    @Value("${" + DiscoveryConstant.SPRING_APPLICATION_NAME + "}")
    private String dataId;

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
/*public class MyZuulStrategyRouteProcessor extends ApolloProcessor {
     private String group = "nepxion";

    @Value("${" + DiscoveryConstant.SPRING_APPLICATION_NAME + "}")
    private String dataId;

    @Autowired
    private ZuulStrategyRoute zuulStrategyRoute;

    @Override
    public String getGroup() {
        return group;
    }

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