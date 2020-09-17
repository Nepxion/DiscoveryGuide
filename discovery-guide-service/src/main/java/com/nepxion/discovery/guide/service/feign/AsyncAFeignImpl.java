package com.nepxion.discovery.guide.service.feign;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import io.opentracing.contrib.concurrent.TracedRunnable;
import io.opentracing.util.GlobalTracer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

@RestController
// @Permission(name = "AFeign", label = "AFeign label", description = "AFeign description")
@ConditionalOnProperty(name = DiscoveryConstant.SPRING_APPLICATION_NAME, havingValue = "discovery-guide-service-a")
public class AsyncAFeignImpl extends CoreImpl implements AsyncAFeign {
    private static final Logger LOG = LoggerFactory.getLogger(AsyncAFeignImpl.class);

    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    @Autowired
    private BFeign bFeign;

    @Override
    @SentinelResource(value = "sentinel-resource", blockHandler = "handleBlock", fallback = "handleFallback")
    @Async
    public String invokeAsync(@PathVariable(value = "value") String value) {
        value = invoke(value);

        AsyncResult<String> result = new AsyncResult<String>(value);
        try {
            value = result.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return "Invoke Async";
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
                invoke(value);
            }
        };
        TracedRunnable tracedRunnable = new TracedRunnable(invokeRunnable, GlobalTracer.get());

        return tracedRunnable;
    }

    private String invoke(String value) {
        value = getPluginInfo(value);
        value = bFeign.invoke(value);

        LOG.info("调用路径：{}", value);

        return value;
    }

    public String handleBlock(String value, BlockException e) {
        return value + "-> A server sentinel block, cause=" + e.getClass().getName() + ", rule=" + e.getRule() + ", limitApp=" + e.getRuleLimitApp();
    }

    public String handleFallback(String value) {
        return value + "-> A server sentinel fallback";
    }
}