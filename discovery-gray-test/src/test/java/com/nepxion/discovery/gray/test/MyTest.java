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
    public void testVersionStrategyGray1() throws Exception {
        myTestCases.testVersionStrategyGray1(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        myTestCases.testVersionStrategyGray1(zuulGroup, zuulServiceId, zuulTestUrl);
    }

    @Test
    public void testVersionStrategyGray2() throws Exception {
        myTestCases.testVersionStrategyGray2(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        myTestCases.testVersionStrategyGray2(zuulGroup, zuulServiceId, zuulTestUrl);
    }

    @Test
    public void testVersionStrategyGray3() throws Exception {
        myTestCases.testVersionStrategyGray3(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        myTestCases.testVersionStrategyGray3(zuulGroup, zuulServiceId, zuulTestUrl);
    }

    @Test
    public void testVersionWeightStrategyGray() throws Exception {
        myTestCases.testVersionWeightStrategyGray(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        myTestCases.testVersionWeightStrategyGray(zuulGroup, zuulServiceId, zuulTestUrl);
    }

    @Test
    public void testRegionStrategyGray1() throws Exception {
        myTestCases.testRegionStrategyGray1(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        myTestCases.testRegionStrategyGray1(zuulGroup, zuulServiceId, zuulTestUrl);
    }

    @Test
    public void testRegionStrategyGray2() throws Exception {
        myTestCases.testRegionStrategyGray2(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        myTestCases.testRegionStrategyGray2(zuulGroup, zuulServiceId, zuulTestUrl);
    }

    public void testRegionStrategyGray3() throws Exception {
        myTestCases.testRegionStrategyGray3(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        myTestCases.testRegionStrategyGray3(zuulGroup, zuulServiceId, zuulTestUrl);
    }

    @Test
    public void testRegionWeightStrategyGray() throws Exception {
        myTestCases.testRegionWeightStrategyGray(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        myTestCases.testRegionWeightStrategyGray(zuulGroup, zuulServiceId, zuulTestUrl);
    }
}