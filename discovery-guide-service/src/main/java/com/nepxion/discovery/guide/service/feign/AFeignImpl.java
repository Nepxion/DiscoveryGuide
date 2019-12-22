package com.nepxion.discovery.guide.service.feign;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import io.opentracing.Span;
import io.opentracing.contrib.concurrent.TracedRunnable;
import io.opentracing.tag.Tags;
import io.opentracing.util.GlobalTracer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.google.common.collect.ImmutableMap;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.guide.service.permission.Permission;

@RestController
// @Permission(name = "AFeign", label = "AFeign label", description = "AFeign description")
@ConditionalOnProperty(name = DiscoveryConstant.SPRING_APPLICATION_NAME, havingValue = "discovery-guide-service-a")
public class AFeignImpl extends AbstractFeignImpl implements AFeign {
    private static final Logger LOG = LoggerFactory.getLogger(AFeignImpl.class);

    @Autowired
    private BFeign bFeign;

    @Override
    @SentinelResource(value = "sentinel-resource", blockHandler = "handleBlock", fallback = "handleFallback")
    @Permission(name = "AFeign invoke", label = "AFeign invoke label", description = "AFeign invoke description")
    public String invoke(@PathVariable(value = "value") String value) {
        value = doInvoke(value);
        value = bFeign.invoke(value);

        Span invokeSpan = GlobalTracer.get().buildSpan("自定义调用埋点").start();
        // 如果没有子Span就不需要下面的代码
        // GlobalTracer.get().activateSpan(invokeSpan);
        invokeSpan.setTag("自定义参数", "这是我自定义的参数");
        invokeSpan.finish();

        if (value.contains("gateway") || value.contains("zuul")) {
            Span errorSpan = GlobalTracer.get().buildSpan("自定义异常埋点").start();
            // 如果没有子Span就不需要下面的代码
            // GlobalTracer.get().activateSpan(errorSpan);
            errorSpan.log(new ImmutableMap.Builder<String, Object>()
                    .put("自定义参数", "这是我自定义的参数")
                    .put(DiscoveryConstant.EVENT, Tags.ERROR.getKey())
                    .put(DiscoveryConstant.ERROR_OBJECT, new IllegalArgumentException("我认为入参包含'gateway'或'zuul'，是一个错误的参数，那么就做异常埋点处理"))
                    .build());
            errorSpan.finish();
        }

        LOG.info("调用路径：{}", value);

        return value;
    }

    @Override
    @SentinelResource(value = "sentinel-resource", blockHandler = "handleBlock", fallback = "handleFallback")
    public String invokeAsync(@PathVariable(value = "value") String value) {
        Runnable invokeRunnable = new Runnable() {
            @Override
            public void run() {
                bFeign.invoke(value);

                LOG.info("异步调用...");
            }
        };
        TracedRunnable tracedRunnable = new TracedRunnable(invokeRunnable, GlobalTracer.get());
        new Thread(tracedRunnable).start();

        return "Invoke Async";
    }

    public String handleBlock(String value, BlockException e) {
        return value + "-> A server sentinel block, cause=" + e.getClass().getName() + ", rule=" + e.getRule() + ", limitApp=" + e.getRuleLimitApp();
    }

    public String handleFallback(String value) {
        return value + "-> A server sentinel fallback";
    }
}