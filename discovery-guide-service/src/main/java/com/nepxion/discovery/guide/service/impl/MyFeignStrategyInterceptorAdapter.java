package com.nepxion.discovery.guide.service.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import feign.RequestTemplate;

import com.nepxion.discovery.plugin.strategy.service.adapter.DefaultFeignStrategyInterceptorAdapter;

// 自定义Feign拦截器中的Header传递
public class MyFeignStrategyInterceptorAdapter extends DefaultFeignStrategyInterceptorAdapter {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("n-d-my-id", "123");
    }
}