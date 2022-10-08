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

import com.nepxion.discovery.plugin.strategy.adapter.DefaultDiscoveryEnabledStrategy;
import com.netflix.loadbalancer.Server;

// 实现了组合策略，版本路由策略+区域路由策略+IP地址和端口路由策略+自定义策略
public class MyDiscoveryEnabledStrategy extends DefaultDiscoveryEnabledStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(MyDiscoveryEnabledStrategy.class);

    // 对REST调用传来的Header参数（例如：mobile）做策略
    @Override
    public boolean apply(Server server) {
        String mobile = strategyContextHolder.getHeader("mobile");
        String serviceId = pluginAdapter.getServerServiceId(server);
        String version = pluginAdapter.getServerVersion(server);
        String region = pluginAdapter.getServerRegion(server);
        String environment = pluginAdapter.getServerEnvironment(server);
        String address = server.getHost() + ":" + server.getPort();

        LOG.info("负载均衡用户定制触发：mobile={}, serviceId={}, version={}, region={}, env={}, address={}", mobile, serviceId, version, region, environment, address);

        if (StringUtils.isNotEmpty(mobile)) {
            // 手机号以移动138开头，路由到1.0版本的服务上
            if (mobile.startsWith("138") && StringUtils.equals(version, "1.0")) {
                return true;
                // 手机号以联通133开头，路由到1.1版本的服务上
            } else if (mobile.startsWith("133") && StringUtils.equals(version, "1.1")) {
                return true;
            } else {
                // 其它情况，实例被过滤掉
                return false;
            }
        }

        // 无手机号，实例不被过滤掉
        return true;
    }
}