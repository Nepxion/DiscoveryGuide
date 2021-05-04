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
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.nacos.proccessor.NacosProcessor;
import com.nepxion.discovery.plugin.strategy.gateway.route.GatewayStrategyRoute;

/*
一个服务映射多个动态路由路径，配置中心界面上推送示例，如下
1. 精简配置
[
    {
        "id": "route0", 
        "uri": "lb://discovery-guide-service-a", 
        "predicates": [
            "Path=/discovery-guide-service-a/**,/x/**,/y/**"
        ], 
        "filters": [
            "StripPrefix=1"
        ]
    }
]

2. 完整配置
[
    {
        "id": "route0", 
        "uri": "lb://discovery-guide-service-a", 
        "predicates": [
            "Path=/discovery-guide-service-a/**,/x/**,/y/**"
        ], 
        "filters": [
            "StripPrefix=1"
        ], 
        "order": 0,
        "metadata": {}
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
public class MyGatewayStrategyRouteProcessor extends NacosProcessor {
    // private String group = "DEFAULT_GROUP";
    private String group = "nepxion";

    @Value("${" + DiscoveryConstant.SPRING_APPLICATION_NAME + "}")
    private String dataId;

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
    public String getDescription() {
        return "Gateway dynamic route";
    }

    @Override
    public void callbackConfig(String config) {
        gatewayStrategyRoute.updateAll(config);
    }
}