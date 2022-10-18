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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.plugin.strategy.gateway.context.GatewayStrategyContextHolder;
import com.nepxion.discovery.plugin.strategy.gateway.filter.DefaultGatewayStrategyRouteFilter;

// 适用于A/B Testing或者更根据某业务参数决定蓝绿灰度路由路径。可以结合配置中心分别配置A/B两条路径，可以动态改变并通知
// 当Header中传来的用户为张三，执行一条路由路径；为李四，执行另一条路由路径
public class MyGatewayStrategyRouteFilter extends DefaultGatewayStrategyRouteFilter {
    private static final Logger LOG = LoggerFactory.getLogger(MyGatewayStrategyRouteFilter.class);

    private static final String DEFAULT_A_ROUTE_VERSION = "{\"discovery-guide-service-a\":\"1.0\", \"discovery-guide-service-b\":\"1.1\"}";
    private static final String DEFAULT_B_ROUTE_VERSION = "{\"discovery-guide-service-a\":\"1.1\", \"discovery-guide-service-b\":\"1.0\"}";
    private static final String DEFAULT_A_ROUTE_REGION = "{\"discovery-guide-service-a\":\"dev\", \"discovery-guide-service-b\":\"qa\"}";
    private static final String DEFAULT_B_ROUTE_REGION = "{\"discovery-guide-service-a\":\"qa\", \"discovery-guide-service-b\":\"dev\"}";
    private static final String DEFAULT_A_ROUTE_ADDRESS = "{\"discovery-guide-service-a\":\"3001\", \"discovery-guide-service-b\":\"4002\"}";
    private static final String DEFAULT_B_ROUTE_ADDRESS = "{\"discovery-guide-service-a\":\"3002\", \"discovery-guide-service-b\":\"4001\"}";

    @Value("${a.route.version:" + DEFAULT_A_ROUTE_VERSION + "}")
    private String aRouteVersion;

    @Value("${b.route.version:" + DEFAULT_B_ROUTE_VERSION + "}")
    private String bRouteVersion;

    @Value("${a.route.region:" + DEFAULT_A_ROUTE_REGION + "}")
    private String aRouteRegion;

    @Value("${b.route.region:" + DEFAULT_B_ROUTE_REGION + "}")
    private String bRouteRegion;

    @Value("${a.route.address:" + DEFAULT_A_ROUTE_ADDRESS + "}")
    private String aRouteAddress;

    @Value("${b.route.address:" + DEFAULT_B_ROUTE_ADDRESS + "}")
    private String bRouteAddress;

    // 自定义根据Header全链路版本匹配路由
    @Override
    public String getRouteVersion() {
        String user = strategyContextHolder.getHeader("user");

        LOG.info("自定义根据Header全链路版本匹配路由, Header user={}", user);

        if (StringUtils.equals(user, "zhangsan")) {
            LOG.info("执行全链路版本匹配路由={}", aRouteVersion);

            return aRouteVersion;
        } else if (StringUtils.equals(user, "lisi")) {
            LOG.info("执行全链路版本匹配路由={}", bRouteVersion);

            return bRouteVersion;
        }

        return super.getRouteVersion();
    }

    // 自定义根据Parameter全链路区域匹配路由
    @Override
    public String getRouteRegion() {
        String user = strategyContextHolder.getParameter("user");

        LOG.info("自定义根据Parameter全链路区域匹配路由, Parameter user={}", user);

        if (StringUtils.equals(user, "zhangsan")) {
            LOG.info("执行全链路区域匹配路由={}", aRouteRegion);

            return aRouteRegion;
        } else if (StringUtils.equals(user, "lisi")) {
            LOG.info("执行全链路区域匹配路由={}", bRouteRegion);

            return bRouteRegion;
        }

        return super.getRouteRegion();
    }

    // 自定义根据Cookie全链路IP地址和端口匹配路由
    @Override
    public String getRouteAddress() {
        String user = strategyContextHolder.getCookie("user");

        LOG.info("自定义根据Cookie全链路IP地址和端口匹配路由, Cookie user={}", user);

        if (StringUtils.equals(user, "zhangsan")) {
            LOG.info("执行全链路IP地址和端口匹配路由={}", aRouteAddress);

            return aRouteAddress;
        } else if (StringUtils.equals(user, "lisi")) {
            LOG.info("执行全链路IP地址和端口匹配路由={}", bRouteAddress);

            return bRouteAddress;
        }

        return super.getRouteAddress();
    }

    @Autowired
    private GatewayStrategyContextHolder gatewayStrategyContextHolder;

    // 自定义根据域名全链路环境隔离
    @Override
    public String getRouteEnvironment() {
        String host = gatewayStrategyContextHolder.getURI().getHost();
        if (host.contains("nepxion.com")) {
            LOG.info("自定义根据域名全链路环境隔离, URL={}", host);

            String environment = host.substring(0, host.indexOf("."));

            LOG.info("执行全链路环境隔离={}", environment);

            return environment;
        }

        return super.getRouteEnvironment();
    }

    // 把域名前缀转化成蓝绿灰度条件表达式中的驱动参数
    /*@Override
    public Map<String, String> getExternalHeaderMap() {
        String host = gatewayStrategyContextHolder.getURI().getHost();
        String domain = host.substring(0, host.indexOf("."));

        Map<String, String> externalHeaderMap = new HashMap<String, String>();
        externalHeaderMap.put("domain", domain);

        return externalHeaderMap;
    }*/

    // 自定义全链路版本权重路由
    /*@Override
    public String getRouteVersion() {
        LOG.info("自定义全链路版本权重路由");

        List<Pair<String, Integer>> weightList = new ArrayList<Pair<String, Integer>>();
        weightList.add(new ImmutablePair<String, Integer>(aRouteVersion, 30));
        weightList.add(new ImmutablePair<String, Integer>(bRouteVersion, 70));
        MapWeightRandom<String, Integer> weightRandom = new MapWeightRandom<String, Integer>(weightList);

        return weightRandom.random();
    }*/
}