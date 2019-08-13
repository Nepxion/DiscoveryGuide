package com.nepxion.discovery.gray.test.core;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.discovery.gray.test.operation.TestOperation;

@Configuration
public class TestConfiguration {
    @Bean
    public TestOperation testOperation() {
        return new TestOperation();
    }
}
