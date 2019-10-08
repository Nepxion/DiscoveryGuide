package com.nepxion.discovery.guide.service.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

import com.nepxion.discovery.plugin.strategy.service.adapter.DefaultRestTemplateStrategyInterceptorAdapter;

// 自定义RestTemplate拦截器中的Header传递
public class MyRestTemplateStrategyInterceptorAdapter extends DefaultRestTemplateStrategyInterceptorAdapter {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        headers.add("n-d-my-id", "456");

        return execution.execute(request, body);
    }
}