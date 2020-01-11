package com.nepxion.discovery.guide.test;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nepxion.discovery.plugin.test.application.TestApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TestApplication.class, MyTestConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyTest {
    private static final Logger LOG = LoggerFactory.getLogger(MyTest.class);

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

    @Value("${gray.weight.testcases.enabled:true}")
    private Boolean weightTestcasesEnabled;

    @Value("${testcase.loop.times:1}")
    private Integer loopTimes;

    @Autowired
    private MyTestCases myTestCases;

    private static long startTime;

    @BeforeClass
    public static void beforeTest() {
        // 彩色旗标显示设置
        System.setProperty("nepxion.banner.shown.ansi.mode", "true");

        startTime = System.currentTimeMillis();
    }

    @AfterClass
    public static void afterTest() {
        LOG.info("* Finished automation test in {} seconds", (System.currentTimeMillis() - startTime) / 1000);
    }

    @Test
    public void testNoGray() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            myTestCases.testNoGray(gatewayTestUrl);
            myTestCases.testNoGray(zuulTestUrl);
        }
    }

    @Test
    public void testEnabledStrategyGray1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            myTestCases.testEnabledStrategyGray1(gatewayTestUrl);
            myTestCases.testEnabledStrategyGray1(zuulTestUrl);
        }
    }

    @Test
    public void testEnabledStrategyGray2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            myTestCases.testEnabledStrategyGray2(gatewayTestUrl);
            myTestCases.testEnabledStrategyGray2(zuulTestUrl);
        }
    }

    @Test
    public void testVersionStrategyGray1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            myTestCases.testVersionStrategyGray1(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            myTestCases.testVersionStrategyGray1(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    @Test
    public void testVersionStrategyGray2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            myTestCases.testVersionStrategyGray2(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            myTestCases.testVersionStrategyGray2(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    @Test
    public void testVersionStrategyGray3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            myTestCases.testVersionStrategyGray3(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            myTestCases.testVersionStrategyGray3(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    @Test
    public void testVersionWeightStrategyGray() throws Exception {
        if (weightTestcasesEnabled) {
            for (int i = 0; i < loopTimes; i++) {
                myTestCases.testVersionWeightStrategyGray(gatewayGroup, gatewayServiceId, gatewayTestUrl);
                myTestCases.testVersionWeightStrategyGray(zuulGroup, zuulServiceId, zuulTestUrl);
            }
        }
    }

    @Test
    public void testRegionStrategyGray1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            myTestCases.testRegionStrategyGray1(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            myTestCases.testRegionStrategyGray1(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    @Test
    public void testRegionStrategyGray2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            myTestCases.testRegionStrategyGray2(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            myTestCases.testRegionStrategyGray2(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    public void testRegionStrategyGray3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            myTestCases.testRegionStrategyGray3(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            myTestCases.testRegionStrategyGray3(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    @Test
    public void testRegionWeightStrategyGray() throws Exception {
        if (weightTestcasesEnabled) {
            for (int i = 0; i < loopTimes; i++) {
                myTestCases.testRegionWeightStrategyGray(gatewayGroup, gatewayServiceId, gatewayTestUrl);
                myTestCases.testRegionWeightStrategyGray(zuulGroup, zuulServiceId, zuulTestUrl);
            }
        }
    }

    @Test
    public void testStrategyCustomizationBlueGreen1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            myTestCases.testStrategyCustomizationBlueGreen1(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            myTestCases.testStrategyCustomizationBlueGreen1(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    @Test
    public void testStrategyCustomizationBlueGreen2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            myTestCases.testStrategyCustomizationBlueGreen2(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            myTestCases.testStrategyCustomizationBlueGreen2(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    @Test
    public void testStrategyCustomizationBlueGreen3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            myTestCases.testStrategyCustomizationBlueGreen3(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            myTestCases.testStrategyCustomizationBlueGreen3(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    @Test
    public void testStrategyCustomizationGray1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            myTestCases.testStrategyCustomizationGray1(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            myTestCases.testStrategyCustomizationGray1(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    @Test
    public void testStrategyCustomizationGray2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            myTestCases.testStrategyCustomizationGray2(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            myTestCases.testStrategyCustomizationGray2(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    @Test
    public void testStrategyCustomizationGray3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            myTestCases.testStrategyCustomizationGray3(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            myTestCases.testStrategyCustomizationGray3(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    @Test
    public void testStrategyCustomizationGray4() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            myTestCases.testStrategyCustomizationGray4(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            myTestCases.testStrategyCustomizationGray4(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    @Test
    public void testVersionRuleGray() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            myTestCases.testVersionRuleGray(gatewayGroup, gatewayGroup, gatewayTestUrl);
            myTestCases.testVersionRuleGray(zuulGroup, zuulGroup, zuulTestUrl);
        }
    }

    @Test
    public void testRegionRuleGray() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            myTestCases.testRegionRuleGray(gatewayGroup, gatewayGroup, gatewayTestUrl);
            myTestCases.testRegionRuleGray(zuulGroup, zuulGroup, zuulTestUrl);
        }
    }

    @Test
    public void testVersionWeightRuleGray() throws Exception {
        if (weightTestcasesEnabled) {
            for (int i = 0; i < loopTimes; i++) {
                myTestCases.testVersionWeightRuleGray(gatewayGroup, gatewayGroup, gatewayTestUrl);
                myTestCases.testVersionWeightRuleGray(zuulGroup, zuulGroup, zuulTestUrl);
            }
        }
    }

    @Test
    public void testRegionWeightRuleGray() throws Exception {
        if (weightTestcasesEnabled) {
            for (int i = 0; i < loopTimes; i++) {
                myTestCases.testRegionWeightRuleGray(gatewayGroup, gatewayGroup, gatewayTestUrl);
                myTestCases.testRegionWeightRuleGray(zuulGroup, zuulGroup, zuulTestUrl);
            }
        }
    }

    @Test
    public void testVersionCompositeRuleGray() throws Exception {
        if (weightTestcasesEnabled) {
            for (int i = 0; i < loopTimes; i++) {
                myTestCases.testVersionCompositeRuleGray(gatewayGroup, gatewayGroup, gatewayTestUrl);
                myTestCases.testVersionCompositeRuleGray(zuulGroup, zuulGroup, zuulTestUrl);
            }
        }
    }
}