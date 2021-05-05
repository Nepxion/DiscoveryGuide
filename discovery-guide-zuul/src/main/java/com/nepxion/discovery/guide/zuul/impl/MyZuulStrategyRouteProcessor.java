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
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.zuul.route.ZuulStrategyRoute;

/*
一个服务映射多个动态路由路径，配置中心界面上推送示例，如下
1. 精简配置
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

2. 完整配置
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

// 把继承类（extends）换成如下任何一个，即可切换配置中心，代码无需任何变动
// 1. NacosProcessor
// 2. ApolloProcessor
// 3. ConsulProcessor
// 4. EtcdProcessor
// 5. ZookeeperProcessor
// 6. RedisProcessor
// Group和DataId自行决定，需要注意
// 1. 对于Nacos配置中心，Group和DataId需要和界面相对应
// 2. 对于其它配置中心，Key的格式为Group-DataId
// 3. 千万不能和蓝绿灰度发布的Group和DataId冲突
public class MyZuulStrategyRouteProcessor extends NacosProcessor {
    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_DYNAMIC_ROUTE_GROUP + ":" + DiscoveryConstant.NEPXION + "}")
    private String group;

    /*@Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_DYNAMIC_ROUTE_GROUP + ":DEFAULT_GROUP}")
    private String group;*/

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
    public String getDescription() {
        return "Zuul dynamic route";
    }

    @Override
    public void callbackConfig(String config) {
        zuulStrategyRoute.updateAll(config);
    }
}