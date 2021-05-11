package com.nepxion.discovery.guide.service.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import javax.servlet.http.HttpServletRequest;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.sentinel.limiter.adapter.DefaultSentinelStrategyRequestOriginAdapter;

// 自定义版本号+地域名，实现组合式熔断
public class MySentinelStrategyRequestOriginAdapter extends DefaultSentinelStrategyRequestOriginAdapter {
    @Override
    public String parseOrigin(HttpServletRequest request) {
        String version = request.getHeader(DiscoveryConstant.N_D_SERVICE_VERSION);
        String location = request.getHeader("location");

        return version + "&" + location;
    }
}