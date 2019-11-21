package com.nepxion.discovery.guide.gateway.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.plugin.framework.adapter.DefaultEnvironmentTransferAdapter;

// 自定义是否要环境切流
public class MyEnvironmentTransferAdapter extends DefaultEnvironmentTransferAdapter {
    @Override
    public boolean isTransferred() {
        return true;
    }
}