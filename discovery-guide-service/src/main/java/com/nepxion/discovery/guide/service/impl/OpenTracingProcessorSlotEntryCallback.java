package com.nepxion.discovery.guide.service.impl;

import com.alibaba.csp.sentinel.context.Context;
import com.alibaba.csp.sentinel.slotchain.ProcessorSlotEntryCallback;
import com.alibaba.csp.sentinel.slotchain.ResourceWrapper;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import io.opentracing.Span;



/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zhang shun
 * @version 1.0
 */
public class OpenTracingProcessorSlotEntryCallback implements ProcessorSlotEntryCallback {

    @Override
    public void onPass(Context context, ResourceWrapper resourceWrapper, Object o, int i, Object... objects) throws Exception {

    }

    @Override
    public void onBlocked(BlockException e, Context context, ResourceWrapper resourceWrapper, Object o, int count, Object... objects) {
        Span span = SentinelTracer.getTracer().buildSpan("SENTINEL").start();
        span.setTag("contextName",context.getName());
        span.setTag("resourceName",resourceWrapper.getName());
        span.setTag("resourceType",resourceWrapper.getResourceType());
        span.setTag("entryType",resourceWrapper.getEntryType().toString());
        span.setTag("ruleLimitApp",e.getRuleLimitApp());
        span.setTag("rule",e.getRule().toString());
        span.setTag("cause",e.getClass().getName());
        span.setTag("blockException",e.getMessage());
        span.setTag("async",context.isAsync());
        span.setTag("count",count);
        span.setTag("params", JSON.toJSONString(objects));
        span.finish();
    }
}
