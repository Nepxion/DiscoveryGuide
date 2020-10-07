package com.nepxion.discovery.guide.gateway.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.plugin.strategy.gateway.filter.DefaultGatewayStrategyRouteFilter;

// 适用于A/B Testing或者更根据某业务参数决定灰度路由路径。可以结合配置中心分别配置A/B两条路径，可以动态改变并通知
// 当Header中传来的用户为张三，执行一条路由路径；为李四，执行另一条路由路径
public class MyGatewayStrategyRouteFilter extends DefaultGatewayStrategyRouteFilter {
    private static final Logger LOG = LoggerFactory.getLogger(MyGatewayStrategyRouteFilter.class);

    private static final String DEFAULT_A_ROUTE_VERSION = "{\"discovery-guide-service-a\":\"1.0\", \"discovery-guide-service-b\":\"1.1\"}";
    private static final String DEFAULT_B_ROUTE_VERSION = "{\"discovery-guide-service-a\":\"1.1\", \"discovery-guide-service-b\":\"1.0\"}";
    private static final String DEFAULT_A_ROUTE_REGION = "{\"discovery-guide-service-a\":\"dev\", \"discovery-guide-service-b\":\"qa\"}";
    private static final String DEFAULT_B_ROUTE_REGION = "{\"discovery-guide-service-a\":\"qa\", \"discovery-guide-service-b\":\"dev\"}";

    @Value("${a.route.version:" + DEFAULT_A_ROUTE_VERSION + "}")
    private String aRouteVersion;

    @Value("${b.route.version:" + DEFAULT_B_ROUTE_VERSION + "}")
    private String bRouteVersion;

    @Value("${a.route.region:" + DEFAULT_A_ROUTE_REGION + "}")
    private String aRouteRegion;

    @Value("${b.route.region:" + DEFAULT_B_ROUTE_REGION + "}")
    private String bRouteRegion;

    // 自定义根据Header全链路版本匹配
    @Override
    public String getRouteVersion() {
        String user = strategyContextHolder.getHeader("user");

        LOG.info("自定义根据Header全链路版本匹配, Header user={}", user);

        if (StringUtils.equals(user, "zhangsan")) {
            LOG.info("执行全链路版本路由={}", aRouteVersion);

            return aRouteVersion;
        } else if (StringUtils.equals(user, "lisi")) {
            LOG.info("执行全链路版本路由={}", bRouteVersion);

            return bRouteVersion;
        }

        return super.getRouteVersion();
    }

    // 自定义根据Parameter全链路区域匹配
    @Override
    public String getRouteRegion() {
        String user = strategyContextHolder.getParameter("user");

        LOG.info("自定义根据Parameter全链路区域匹配, Parameter user={}", user);

        if (StringUtils.equals(user, "zhangsan")) {
            LOG.info("执行全链路区域路由={}", aRouteRegion);

            return aRouteRegion;
        } else if (StringUtils.equals(user, "lisi")) {
            LOG.info("执行全链路区域路由={}", bRouteRegion);

            return bRouteRegion;
        }

        return super.getRouteRegion();
    }

    // 自定义全链路版本权重
    /*@Override
    public String getRouteVersion() {
        LOG.info("自定义全链路版本权重");

        List<Pair<String, Double>> weightList = new ArrayList<Pair<String, Double>>();
        weightList.add(new ImmutablePair<String, Double>(aRouteVersion, 30D));
        weightList.add(new ImmutablePair<String, Double>(bRouteVersion, 70D));
        MapWeightRandom<String, Double> weightRandom = new MapWeightRandom<String, Double>(weightList);

        return weightRandom.random();
    }*/
}