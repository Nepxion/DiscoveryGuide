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

import com.nepxion.discovery.plugin.test.automation.TestApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TestApplication.class, MyTestConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyTest {
    @Value("${gateway.group}")
    private String gatewayGroup;

    @Value("${gateway.service.id}")
    private String gatewayServiceId;

    @Value("${gateway.test.url}")
    private String gatewayTestUrl;

    @Value("${zuul.group}")
    private String zuulGroup;

    @Value("${zuul.service.id}")
    private String zuulServiceId;

    @Value("${zuul.test.url}")
    private String zuulTestUrl;

    @Autowired
    private MyTestCases myTestCases;

    @Test
    public void testNoGray() throws Exception {
        myTestCases.testNoGray(gatewayTestUrl);
        myTestCases.testNoGray(zuulTestUrl);
    }

    @Test
    public void testVersionGray1() throws Exception {
        myTestCases.testVersionGray1(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        myTestCases.testVersionGray1(zuulGroup, zuulServiceId, zuulTestUrl);
    }

    @Test
    public void testVersionGray2() throws Exception {
        myTestCases.testVersionGray2(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        myTestCases.testVersionGray2(zuulGroup, zuulServiceId, zuulTestUrl);
    }

    @Test
    public void testVersionGray3() throws Exception {
        myTestCases.testVersionGray3(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        myTestCases.testVersionGray3(zuulGroup, zuulServiceId, zuulTestUrl);
    }

    @Test
    public void testRegionGray1() throws Exception {
        myTestCases.testRegionGray1(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        myTestCases.testRegionGray1(zuulGroup, zuulServiceId, zuulTestUrl);
    }

    @Test
    public void testRegionGray2() throws Exception {
        myTestCases.testRegionGray2(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        myTestCases.testRegionGray2(zuulGroup, zuulServiceId, zuulTestUrl);
    }

    public void testRegionGray3() throws Exception {
        myTestCases.testRegionGray3(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        myTestCases.testRegionGray3(zuulGroup, zuulServiceId, zuulTestUrl);
    }
}