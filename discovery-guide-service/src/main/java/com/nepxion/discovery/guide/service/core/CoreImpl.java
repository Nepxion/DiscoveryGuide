package com.nepxion.discovery.guide.service.core;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;

public class CoreImpl {
    @Autowired
    private PluginAdapter pluginAdapter;

    public String getPluginInfo(String value) {
        return pluginAdapter.getPluginInfo(value);

        // Skywalking下不支持长URL的GET方式的埋点输出，返回值改成OK
        // return "OK";
    }
}