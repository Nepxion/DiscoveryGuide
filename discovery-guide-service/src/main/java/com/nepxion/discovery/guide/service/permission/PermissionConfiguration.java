package com.nepxion.discovery.guide.service.permission;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.util.SpringBootUtil;
import com.nepxion.discovery.plugin.strategy.service.constant.ServiceStrategyConstant;

@Configuration
public class PermissionConfiguration {
    @Autowired
    private ConfigurableEnvironment environment;

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Bean
    public PermissionAutoScanProxy permissionAutoScanProxy() {
        String scanPackages = environment.getProperty(ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES);
        if (StringUtils.isEmpty(scanPackages)) {
            scanPackages = SpringBootUtil.convertBasePackages(applicationContext);
        }

        if (StringUtils.isEmpty(scanPackages)) {
            throw new PermissionException(ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES + "'s value can't be empty");
        }

        if (scanPackages.contains(DiscoveryConstant.ENDPOINT_SCAN_PACKAGES)) {
            throw new PermissionException("It can't scan packages for '" + DiscoveryConstant.ENDPOINT_SCAN_PACKAGES + "', please check '" + ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES + "'");
        }

        return new PermissionAutoScanProxy(scanPackages);
    }

    @Bean
    public PermissionInterceptor permissionInterceptor() {
        return new PermissionInterceptor();
    }

    @Bean
    public PermissionPersister permissionPersister() {
        return new PermissionPersister();
    }
}