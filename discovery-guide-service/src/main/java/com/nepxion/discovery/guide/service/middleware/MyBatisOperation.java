package com.nepxion.discovery.guide.service.middleware;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.nepxion.discovery.guide.service.middleware")
public class MyBatisOperation {
    private static final Logger LOG = LoggerFactory.getLogger(MyBatisOperation.class);

    @Autowired
    private MyBatisMapper myBatisMapper;

    public void invokeMyBatis() {
        int count = myBatisMapper.count();

        LOG.info("MyBatis select count={}", count);
    }
}