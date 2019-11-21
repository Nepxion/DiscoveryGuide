package com.nepxion.discovery.guide.service.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.adapter.DiscoveryEnabledStrategy;
import com.nepxion.discovery.plugin.strategy.service.constant.ServiceStrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.context.ServiceStrategyContextHolder;
import com.netflix.loadbalancer.Server;

// 实现了组合策略，版本路由策略+区域路由策略+IP和端口路由策略+自定义策略
public class MyDiscoveryEnabledStrategy implements DiscoveryEnabledStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(MyDiscoveryEnabledStrategy.class);

    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private ServiceStrategyContextHolder serviceStrategyContextHolder;

    @Override
    public boolean apply(Server server) {
        boolean enabled = applyFromHeader(server);
        if (!enabled) {
            return false;
        }

        return applyFromMethod(server);
    }

    // 根据REST调用传来的Header参数（例如：mobile），选取执行调用请求的服务实例
    private boolean applyFromHeader(Server server) {
        String mobile = serviceStrategyContextHolder.getHeader("mobile");
        String serviceId = pluginAdapter.getServerServiceId(server);
        String version = pluginAdapter.getServerVersion(server);
        String region = pluginAdapter.getServerRegion(server);
        String environment = pluginAdapter.getServerEnvironment(server);
        String address = server.getHostPort();

        LOG.info("负载均衡用户定制触发：mobile={}, serviceId={}, version={}, region={}, env={}, address={}", mobile, serviceId, version, region, environment, address);

        if (StringUtils.isNotEmpty(mobile)) {
            // 手机号以移动138开头，路由到1.0版本的服务上
            if (mobile.startsWith("138") && StringUtils.equals(version, "1.0")) {
                return true;
                // 手机号以联通133开头，路由到2.0版本的服务上
            } else if (mobile.startsWith("133") && StringUtils.equals(version, "1.1")) {
                return true;
            } else {
                // 其它情况，直接拒绝请求
                return false;
            }
        }

        return true;
    }

    // 根据RPC调用传来的方法参数（例如接口名、方法名、参数名或参数值等），选取执行调用请求的服务实例
    // 本示例只作用在discovery-guide-service-a服务上
    @SuppressWarnings("unchecked")
    private boolean applyFromMethod(Server server) {
        Map<String, Object> attributes = serviceStrategyContextHolder.getRpcAttributes();
        String serviceId = pluginAdapter.getServerServiceId(server);
        String version = pluginAdapter.getServerVersion(server);
        String region = pluginAdapter.getServerRegion(server);
        String environment = pluginAdapter.getServerEnvironment(server);
        String address = server.getHostPort();

        LOG.info("负载均衡用户定制触发：attributes={}, serviceId={}, version={}, region={}, env={}, address={}", attributes, serviceId, version, region, environment, address);

        if (attributes.containsKey(ServiceStrategyConstant.PARAMETER_MAP)) {
            Map<String, Object> parameterMap = (Map<String, Object>) attributes.get(ServiceStrategyConstant.PARAMETER_MAP);
            String value = parameterMap.get("value").toString();
            if (StringUtils.isNotEmpty(value)) {
                // 输入值包含dev，路由到dev区域的服务上
                if (value.contains("dev") && StringUtils.equals(region, "dev")) {
                    return true;
                    // 输入值包含qa，路由到qa区域的服务上
                } else if (value.contains("qa") && StringUtils.equals(region, "qa")) {
                    return true;
                } else {
                    // 其它情况，直接通过请求
                    return true;
                }
            }
        }

        return true;
    }
}