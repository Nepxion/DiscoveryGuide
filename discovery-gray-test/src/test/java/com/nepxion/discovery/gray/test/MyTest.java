package com.nepxion.discovery.gray.test;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TestApplication.class, MyTestConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyTest {
    @Value("${gateway.url}")
    private String gatewayUrl;

    @Value("${zuul.url}")
    private String zuulUrl;

    @Value("${gateway.test.url}")
    private String gatewayTestUrl;

    @Value("${zuul.test.url}")
    private String zuulTestUrl;

    @Autowired
    private MyTestCases myTestCases;

    @Test
    public void testNoGray() throws Exception {
        myTestCases.testNoGray(gatewayUrl, gatewayTestUrl);
        myTestCases.testNoGray(zuulUrl, zuulTestUrl);
    }

    @Test
    public void testVersionGray() throws Exception {
        myTestCases.testVersionGray(gatewayUrl, gatewayTestUrl);
        myTestCases.testVersionGray(zuulUrl, zuulTestUrl);
    }

    @Test
    public void testRegionGray() throws Exception {
        myTestCases.testRegionGray(gatewayUrl, gatewayTestUrl);
        myTestCases.testRegionGray(zuulUrl, zuulTestUrl);
    }
}