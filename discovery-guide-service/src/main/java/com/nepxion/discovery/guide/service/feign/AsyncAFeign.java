package com.nepxion.discovery.guide.service.feign;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.concurrent.Future;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "discovery-guide-service-a")
public interface AsyncAFeign {
    // @Async注解方式的异步
    @GetMapping(path = "/invoke-async/{value}")
    Future<String> invokeAsync(@PathVariable(value = "value") String value);

    // 单线程方式的异步
    @GetMapping(path = "/invoke-thread/{value}")
    String invokeThread(@PathVariable(value = "value") String value);

    // 线程池方式的异步
    @GetMapping(path = "/invoke-threadpool/{value}")
    String invokeThreadPool(@PathVariable(value = "value") String value);
}