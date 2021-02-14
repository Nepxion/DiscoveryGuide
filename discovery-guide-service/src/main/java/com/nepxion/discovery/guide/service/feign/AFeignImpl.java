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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.guide.service.core.CoreImpl;
import com.nepxion.discovery.guide.service.middleware.MiddlewareOperation;
import com.nepxion.discovery.guide.service.permission.Permission;
import com.nepxion.discovery.plugin.strategy.monitor.StrategyMonitorContext;

@RestController
// @Permission(name = "AFeign", label = "AFeign label", description = "AFeign description")
@ConditionalOnProperty(name = DiscoveryConstant.SPRING_APPLICATION_NAME, havingValue = "discovery-guide-service-a")
public class AFeignImpl extends CoreImpl implements AFeign {
    private static final Logger LOG = LoggerFactory.getLogger(AFeignImpl.class);

    @Autowired
    private BFeign bFeign;

    @Autowired
    private StrategyMonitorContext strategyMonitorContext;

    @Autowired
    private MiddlewareOperation middlewareOperation;

    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    @Override
    @SentinelResource(value = "sentinel-resource", blockHandler = "handleBlock", fallback = "handleFallback")
    @Permission(name = "AFeign invoke", label = "AFeign invoke label", description = "AFeign invoke description")
    public String invoke(@PathVariable(value = "value") String value) {
        value = doInvoke(value);

        return value;
    }

    @Override
    @SentinelResource(value = "sentinel-resource", blockHandler = "handleBlock", fallback = "handleFallback")
    @Async
    public Future<String> invokeAsync(@PathVariable(value = "value") String value) {
        try {
            value = doInvoke(value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        AsyncResult<String> result = new AsyncResult<String>(value);
        try {
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    @SentinelResource(value = "sentinel-resource", blockHandler = "handleBlock", fallback = "handleFallback")
    public String invokeThread(@PathVariable(value = "value") String value) {
        Runnable runnable = createRunnable(value);

        new Thread(runnable).start();

        return "Invoke Thread";
    }

    @Override
    @SentinelResource(value = "sentinel-resource", blockHandler = "handleBlock", fallback = "handleFallback")
    public String invokeThreadPool(String value) {
        Runnable runnable = createRunnable(value);

        cachedThreadPool.execute(runnable);

        return "Invoke ThreadPool";
    }

    private Runnable createRunnable(String value) {
        Runnable invokeRunnable = new Runnable() {
            @Override
            public void run() {
                doInvoke(value);
            }
        };
        TracedRunnable tracedRunnable = new TracedRunnable(invokeRunnable, GlobalTracer.get());

        return tracedRunnable;
    }

    private String doInvoke(String value) {
        value = getPluginInfo(value);
        value = bFeign.invoke(value);

        LOG.info("调用路径：{}", value);

        doTrace(value);

        doOperate();

        return value;
    }

    private void doTrace(String value) {
        LOG.info("获取TraceId={}, SpanId={}", strategyMonitorContext.getTraceId(), strategyMonitorContext.getSpanId());

        Span invokeSpan = GlobalTracer.get().buildSpan("自定义调用埋点").start();
        // 如果没有子Span就不需要下面的代码
        // GlobalTracer.get().activateSpan(invokeSpan);
        invokeSpan.setTag("自定义参数", "这是我自定义的参数");
        invokeSpan.finish();

        if (value.contains("gateway") || value.contains("zuul")) {
            Span errorSpan = GlobalTracer.get().buildSpan("自定义异常埋点").start();
            // 如果没有子Span就不需要下面的代码
            // GlobalTracer.get().activateSpan(errorSpan);
            Map<String, Object> customizationMap = new HashMap<String, Object>();
            customizationMap.put("自定义参数", "这是我自定义的参数");
            customizationMap.put(DiscoveryConstant.EVENT, Tags.ERROR.getKey());
            customizationMap.put(DiscoveryConstant.ERROR_OBJECT, new IllegalArgumentException("我认为入参包含'gateway'或'zuul'，是一个错误的参数，那么就做异常埋点处理"));
            errorSpan.log(customizationMap);
            errorSpan.finish();
        }
    }

    private void doOperate() {
        middlewareOperation.operate();
    }

    public String handleBlock(String value, BlockException e) {
        return value + "-> A server sentinel block, cause=" + e.getClass().getName() + ", rule=" + e.getRule() + ", limitApp=" + e.getRuleLimitApp();
    }

    public String handleFallback(String value) {
        return value + "-> A server sentinel fallback";
    }
}