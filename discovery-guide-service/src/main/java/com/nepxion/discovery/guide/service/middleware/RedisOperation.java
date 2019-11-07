package com.nepxion.discovery.guide.service.middleware;

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
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisOperation {
    private static final Logger LOG = LoggerFactory.getLogger(RedisOperation.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void invokeRedis() {
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();

        String group = "MyGroup";
        String dataId = "MyDataId";
        String message = "MyMessage";

        hashOperations.put(group, dataId, message);
        stringRedisTemplate.convertAndSend(group + "-" + dataId, message);

        LOG.info("Redis publish, group={}, dataId={}, message={}", group, dataId, message);

        String result = hashOperations.get(group, dataId);

        LOG.info("Redis get, group={}, dataId={}, result={}", group, dataId, result);
    }
}