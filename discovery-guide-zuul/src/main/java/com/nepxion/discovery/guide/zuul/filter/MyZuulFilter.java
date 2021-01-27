package com.nepxion.discovery.guide.zuul.filter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.web.client.RestTemplate;

import com.nepxion.discovery.guide.zuul.feign.ZuulFeign;
import com.netflix.zuul.ZuulFilter;

public class MyZuulFilter extends ZuulFilter {
    private static final Logger LOG = LoggerFactory.getLogger(MyZuulFilter.class);

    @Autowired
    private ZuulFeign zuulFeign;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 100;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        try {
            String parameter = "MyZuulFilter";
            String feignValue = zuulFeign.invoke(parameter);
            String restTemplateValue = restTemplate.getForEntity("http://discovery-guide-service-a/rest/" + parameter, String.class).getBody();

            LOG.info("网关上触发Feigin调用，返回值={}", feignValue);
            LOG.info("网关上触发RestTemplate调用，返回值={}", restTemplateValue);
        } catch (Exception e) {
            LOG.info("Invoke failed", e);
        }

        return null;
    }
}