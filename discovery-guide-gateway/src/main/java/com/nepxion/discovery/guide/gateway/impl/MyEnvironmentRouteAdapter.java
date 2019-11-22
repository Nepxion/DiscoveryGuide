package com.nepxion.discovery.guide.gateway.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.plugin.framework.adapter.DefaultEnvironmentRouteAdapter;

// 自定义环境路由
public class MyEnvironmentRouteAdapter extends DefaultEnvironmentRouteAdapter {
    @Override
    public boolean isRouteEnabled() {
        return true;
    }

    @Override
    public String getEnvironmentRoute() {
        return "common";
    }
}