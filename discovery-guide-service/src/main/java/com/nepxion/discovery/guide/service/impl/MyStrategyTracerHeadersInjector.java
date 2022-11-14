package com.nepxion.discovery.guide.service.impl;

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

import com.nepxion.discovery.plugin.strategy.injector.StrategyTracerHeadersInjector;

public class MyStrategyTracerHeadersInjector implements StrategyTracerHeadersInjector {
    @Override
    public List<String> getHeaderNames() {
        return Arrays.asList("n-d-xyz");
    }
}