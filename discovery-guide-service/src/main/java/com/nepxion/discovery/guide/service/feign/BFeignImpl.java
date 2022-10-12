package com.nepxion.discovery.guide.service.feign;

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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.guide.service.core.CoreImpl;

@RestController
@ConditionalOnProperty(name = DiscoveryConstant.SPRING_APPLICATION_NAME, havingValue = "discovery-guide-service-b")
public class BFeignImpl extends CoreImpl implements BFeign {
    private static final Logger LOG = LoggerFactory.getLogger(BFeignImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String invoke(@PathVariable(value = "value") String value) {
        value = getPluginInfo(value);
        value = restTemplate.getForEntity("http://discovery-guide-gateway2/discovery-guide-service-d/invoke/" + value, String.class).getBody();

        LOG.info("调用路径：{}", value);

        return value;
    }
}