package com.nepxion.discovery.guide.gateway.impl;

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
import com.nepxion.discovery.plugin.strategy.gateway.route.GatewayStrategyRoute;

/*
一个服务映射多个动态路由路径，配置中心界面上推送示例，如下
① 精简配置
[
    {
        "id": "route0", 
        "uri": "lb://discovery-guide-service-a", 
        "predicates": [
            "Path=/discovery-guide-service-a/**, /x/**,/y/**"
        ], 
        "filters": [
            "StripPrefix=1"
        ]
    }
]

② 完整配置
[
    {
        "id": "route0", 
        "uri": "lb://discovery-guide-service-a", 
        "predicates": [
            "Path=/discovery-guide-service-a/**, /x/**,/y/**"
        ], 
        "filters": [
            "StripPrefix=1"
        ], 
        "order": 0,
        "metadata": {}
    }
]
*/

// 使用Nacos配置中心
public class MyGatewayStrategyRouteTemplate extends NacosTemplate {
    private String group = "DEFAULT_GROUP";
    private String dataId = "gateway-route";

    @Autowired
    private GatewayStrategyRoute gatewayStrategyRoute;

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
        return "Gateway dynamic route";
    }

    @Override
    public void callbackConfig(String config) {
        gatewayStrategyRoute.updateAll(config);
    }
}

// 使用Apollo配置中心
/*public class MyZuulStrategyRouteTemplate extends ApolloTemplate {
    private String dataId = "gateway-route";

    @Autowired
    private GatewayStrategyRoute gatewayStrategyRoute;

    @Override
    public String getKey() {
        return dataId;
    }

    @Override
    public String getConfigType() {
        return "Gateway dynamic route";
    }

    @Override
    public void callbackConfig(String config) {
        gatewayStrategyRoute.updateAll(config);
    }
}*/