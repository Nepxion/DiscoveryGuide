package com.nepxion.discovery.guide.service.impl;


import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.statistic.StatisticSlotCallbackRegistry;


/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zhang shun
 * @version 1.0
 */
public class OpenTracingInitFunc implements InitFunc {


    public OpenTracingInitFunc() {
    }

    @Override
    public void init() throws Exception {
        StatisticSlotCallbackRegistry.addEntryCallback(OpenTracingProcessorSlotEntryCallback.class.getName(),
                new OpenTracingProcessorSlotEntryCallback());
    }

}
