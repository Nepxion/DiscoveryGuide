package com.nepxion.discovery.guide.zuul.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Arrays;
import java.util.List;

import com.nepxion.discovery.common.entity.HeadersInjectorEntity;
import com.nepxion.discovery.common.entity.HeadersInjectorType;
import com.nepxion.discovery.plugin.strategy.injector.StrategyHeadersInjector;

public class MyStrategyHeadersInjector implements StrategyHeadersInjector {
    @Override
    public List<HeadersInjectorEntity> getHeadersInjectorEntityList() {
        return Arrays.asList(
                new HeadersInjectorEntity(HeadersInjectorType.TRACER, Arrays.asList("test1")),
                new HeadersInjectorEntity(HeadersInjectorType.ALL, Arrays.asList("test2")));
    }
}