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
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nepxion.banner.BannerConstant;
import com.nepxion.discovery.plugin.test.automation.application.TestApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TestApplication.class, DiscoveryGuideTestConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DiscoveryGuideTest {
    private static final Logger LOG = LoggerFactory.getLogger(DiscoveryGuideTest.class);

    @Value("${gateway.group}")
    private String gatewayGroup;

    @Value("${gateway.service.id}")
    private String gatewayServiceId;

    @Value("${gateway.test.url}")
    private String gatewayTestUrl;

    @Value("${gateway.route0.test.url}")
    private String gatewayTestRoute0Url;

    @Value("${gateway.route1.test.url}")
    private String gatewayTestRoute1Url;

    @Value("${gateway.route2.test.url}")
    private String gatewayTestRoute2Url;

    @Value("${gateway.inspect.url}")
    private String gatewayInspectUrl;

    @Value("${zuul.group}")
    private String zuulGroup;

    @Value("${zuul.service.id}")
    private String zuulServiceId;

    @Value("${zuul.test.url}")
    private String zuulTestUrl;

    @Value("${zuul.route0.test.url}")
    private String zuulTestRoute0Url;

    @Value("${zuul.route1.test.url}")
    private String zuulTestRoute1Url;

    @Value("${zuul.route2.test.url}")
    private String zuulTestRoute2Url;

    @Value("${zuul.inspect.url}")
    private String zuulInspectUrl;

    @Value("${gray.weight.testcases.enabled:true}")
    private Boolean weightTestcasesEnabled;

    @Value("${testcase.loop.times:1}")
    private Integer loopTimes;

    @Autowired
    private DiscoveryGuideTestCases discoveryGuideTestCases;

    private static long startTime;

    @BeforeClass
    public static void beforeTest() {
        // 是否要显示旗标
        System.setProperty(BannerConstant.BANNER_SHOWN, "true");
        // 是否把旗标渲染成彩色
        System.setProperty(BannerConstant.BANNER_SHOWN_ANSI_MODE, "true");

        startTime = System.currentTimeMillis();
    }

    @AfterClass
    public static void afterTest() {
        LOG.info("* Finished automation test in {} seconds", (System.currentTimeMillis() - startTime) / 1000);
    }

    @Test
    public void testANoGray() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testNoGray(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            discoveryGuideTestCases.testNoGray(zuulGroup, zuulServiceId, zuulTestUrl);
            discoveryGuideTestCases.testNoGray(gatewayGroup, gatewayGroup, gatewayTestUrl);
            discoveryGuideTestCases.testNoGray(zuulGroup, zuulGroup, zuulTestUrl);
        }
    }

    @Test
    public void testEnabledStrategyGray1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testEnabledStrategyGray1(gatewayTestUrl);
            discoveryGuideTestCases.testEnabledStrategyGray1(zuulTestUrl);
        }
    }

    @Test
    public void testEnabledStrategyGray2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testEnabledStrategyGray2(gatewayTestUrl);
            discoveryGuideTestCases.testEnabledStrategyGray2(zuulTestUrl);
        }
    }

    @Test
    public void testVersionRouteFilter1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testVersionRouteFilter1(gatewayTestUrl);
            discoveryGuideTestCases.testVersionRouteFilter1(zuulTestUrl);
        }
    }

    @Test
    public void testVersionRouteFilter2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testVersionRouteFilter2(gatewayTestUrl);
            discoveryGuideTestCases.testVersionRouteFilter2(zuulTestUrl);
        }
    }

    @Test
    public void testRegionRouteFilter1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testRegionRouteFilter1(gatewayTestUrl);
            discoveryGuideTestCases.testRegionRouteFilter1(zuulTestUrl);
        }
    }

    @Test
    public void testRegionRouteFilter2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testRegionRouteFilter2(gatewayTestUrl);
            discoveryGuideTestCases.testRegionRouteFilter2(zuulTestUrl);
        }
    }

    @Test
    public void testAddressRouteFilter1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testAddressRouteFilter1(gatewayTestUrl);
            discoveryGuideTestCases.testAddressRouteFilter1(zuulTestUrl);
        }
    }

    @Test
    public void testAddressRouteFilter2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testAddressRouteFilter2(gatewayTestUrl);
            discoveryGuideTestCases.testAddressRouteFilter2(zuulTestUrl);
        }
    }

    @Test
    public void testVersionStrategyGray1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testVersionStrategyGray1(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            discoveryGuideTestCases.testVersionStrategyGray1(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    @Test
    public void testVersionStrategyGray2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testVersionStrategyGray2(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            discoveryGuideTestCases.testVersionStrategyGray2(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    @Test
    public void testVersionStrategyGray3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testVersionStrategyGray3(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            discoveryGuideTestCases.testVersionStrategyGray3(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    @Test
    public void testVersionWeightStrategyGray() throws Exception {
        if (weightTestcasesEnabled) {
            for (int i = 0; i < loopTimes; i++) {
                discoveryGuideTestCases.testVersionWeightStrategyGray(gatewayGroup, gatewayServiceId, gatewayTestUrl);
                discoveryGuideTestCases.testVersionWeightStrategyGray(zuulGroup, zuulServiceId, zuulTestUrl);
            }
        }
    }

    @Test
    public void testRegionStrategyGray1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testRegionStrategyGray1(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            discoveryGuideTestCases.testRegionStrategyGray1(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    @Test
    public void testRegionStrategyGray2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testRegionStrategyGray2(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            discoveryGuideTestCases.testRegionStrategyGray2(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    public void testRegionStrategyGray3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testRegionStrategyGray3(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            discoveryGuideTestCases.testRegionStrategyGray3(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    @Test
    public void testRegionWeightStrategyGray() throws Exception {
        if (weightTestcasesEnabled) {
            for (int i = 0; i < loopTimes; i++) {
                discoveryGuideTestCases.testRegionWeightStrategyGray(gatewayGroup, gatewayServiceId, gatewayTestUrl);
                discoveryGuideTestCases.testRegionWeightStrategyGray(zuulGroup, zuulServiceId, zuulTestUrl);
            }
        }
    }

    @Test
    public void testStrategyCustomizationBlueGreen1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyCustomizationBlueGreen1(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            discoveryGuideTestCases.testStrategyCustomizationBlueGreen1(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    @Test
    public void testStrategyCustomizationBlueGreen2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyCustomizationBlueGreen2(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            discoveryGuideTestCases.testStrategyCustomizationBlueGreen2(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    @Test
    public void testStrategyCustomizationBlueGreen3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyCustomizationBlueGreen3(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            discoveryGuideTestCases.testStrategyCustomizationBlueGreen3(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    @Test
    public void testStrategyCustomizationBlueGreenHeader1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyCustomizationBlueGreenHeader1(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            discoveryGuideTestCases.testStrategyCustomizationBlueGreenHeader1(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    @Test
    public void testStrategyCustomizationBlueGreenHeader2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyCustomizationBlueGreenHeader2(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            discoveryGuideTestCases.testStrategyCustomizationBlueGreenHeader2(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    @Test
    public void testStrategyCustomizationBlueGreenHeader3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyCustomizationBlueGreenHeader3(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            discoveryGuideTestCases.testStrategyCustomizationBlueGreenHeader3(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    @Test
    public void testStrategyCustomizationBlueGreenHeaderParameterCookie() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyCustomizationBlueGreenHeaderParameterCookie(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            discoveryGuideTestCases.testStrategyCustomizationBlueGreenHeaderParameterCookie(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    @Test
    public void testInspectStrategyCustomizationBlueGreenHeader1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testInspectStrategyCustomizationBlueGreenHeader1(gatewayGroup, gatewayServiceId, gatewayInspectUrl);
            discoveryGuideTestCases.testInspectStrategyCustomizationBlueGreenHeader1(zuulGroup, zuulServiceId, zuulInspectUrl);
        }
    }

    @Test
    public void testInspectStrategyCustomizationBlueGreenHeader2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testInspectStrategyCustomizationBlueGreenHeader2(gatewayGroup, gatewayServiceId, gatewayInspectUrl);
            discoveryGuideTestCases.testInspectStrategyCustomizationBlueGreenHeader2(zuulGroup, zuulServiceId, zuulInspectUrl);
        }
    }

    @Test
    public void testInspectStrategyCustomizationBlueGreenHeader3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testInspectStrategyCustomizationBlueGreenHeader3(gatewayGroup, gatewayServiceId, gatewayInspectUrl);
            discoveryGuideTestCases.testInspectStrategyCustomizationBlueGreenHeader3(zuulGroup, zuulServiceId, zuulInspectUrl);
        }
    }

    @Test
    public void testStrategyCustomizationGray1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyCustomizationGray1(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            discoveryGuideTestCases.testStrategyCustomizationGray1(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    @Test
    public void testStrategyCustomizationGray2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyCustomizationGray2(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            discoveryGuideTestCases.testStrategyCustomizationGray2(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    @Test
    public void testStrategyCustomizationGray3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyCustomizationGray3(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            discoveryGuideTestCases.testStrategyCustomizationGray3(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    @Test
    public void testStrategyCustomizationGray4() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyCustomizationGray4(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            discoveryGuideTestCases.testStrategyCustomizationGray4(zuulGroup, zuulServiceId, zuulTestUrl);
        }
    }

    @Test
    public void testVersionRuleGray() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testVersionRuleGray(gatewayGroup, gatewayGroup, gatewayTestUrl);
            discoveryGuideTestCases.testVersionRuleGray(zuulGroup, zuulGroup, zuulTestUrl);
        }
    }

    @Test
    public void testRegionRuleGray() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testRegionRuleGray(gatewayGroup, gatewayGroup, gatewayTestUrl);
            discoveryGuideTestCases.testRegionRuleGray(zuulGroup, zuulGroup, zuulTestUrl);
        }
    }

    @Test
    public void testVersionWeightRuleGray() throws Exception {
        if (weightTestcasesEnabled) {
            for (int i = 0; i < loopTimes; i++) {
                discoveryGuideTestCases.testVersionWeightRuleGray(gatewayGroup, gatewayGroup, gatewayTestUrl);
                discoveryGuideTestCases.testVersionWeightRuleGray(zuulGroup, zuulGroup, zuulTestUrl);
            }
        }
    }

    @Test
    public void testRegionWeightRuleGray() throws Exception {
        if (weightTestcasesEnabled) {
            for (int i = 0; i < loopTimes; i++) {
                discoveryGuideTestCases.testRegionWeightRuleGray(gatewayGroup, gatewayGroup, gatewayTestUrl);
                discoveryGuideTestCases.testRegionWeightRuleGray(zuulGroup, zuulGroup, zuulTestUrl);
            }
        }
    }

    @Test
    public void testVersionCompositeRuleGray() throws Exception {
        if (weightTestcasesEnabled) {
            for (int i = 0; i < loopTimes; i++) {
                discoveryGuideTestCases.testVersionCompositeRuleGray(gatewayGroup, gatewayGroup, gatewayTestUrl);
                discoveryGuideTestCases.testVersionCompositeRuleGray(zuulGroup, zuulGroup, zuulTestUrl);
            }
        }
    }

    @Test
    public void testBlacklist1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testBlacklist1(gatewayGroup, gatewayGroup, gatewayTestUrl);
            discoveryGuideTestCases.testBlacklist1(zuulGroup, zuulGroup, zuulTestUrl);
        }
    }

    @Test
    public void testBlacklist2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testBlacklist2(gatewayTestUrl);
            discoveryGuideTestCases.testBlacklist2(zuulTestUrl);
        }
    }

    @Test
    public void testBlacklist3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testBlacklist3(gatewayTestUrl);
            discoveryGuideTestCases.testBlacklist3(zuulTestUrl);
        }
    }

    @Test
    public void testEnv() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testEnv(gatewayTestUrl);
            discoveryGuideTestCases.testEnv(zuulTestUrl);
        }
    }

    @Test
    public void testSentinelAuthority1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testSentinelAuthority1(gatewayTestUrl);
            discoveryGuideTestCases.testSentinelAuthority1(zuulTestUrl);
        }
    }

    @Test
    public void testSentinelAuthority2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testSentinelAuthority2(gatewayTestUrl);
            discoveryGuideTestCases.testSentinelAuthority2(zuulTestUrl);
        }
    }

    @Test
    public void testSentinelAuthority3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testSentinelAuthority3(gatewayTestUrl);
            discoveryGuideTestCases.testSentinelAuthority3(zuulTestUrl);
        }
    }

    @Test
    public void testDynamicRoute() throws Exception {
        discoveryGuideTestCases.testDynamicRoute1("nepxion", gatewayServiceId, new String[] {gatewayTestRoute0Url, gatewayTestRoute1Url, gatewayTestRoute2Url}, "dynamic-route-gateway-default.json");
        discoveryGuideTestCases.testDynamicRoute2("nepxion", gatewayServiceId, new String[] {gatewayTestRoute0Url, gatewayTestRoute1Url, gatewayTestRoute2Url}, "dynamic-route-gateway.json", "dynamic-route-gateway-default.json");

        discoveryGuideTestCases.testDynamicRoute1("nepxion", zuulServiceId, new String[] {zuulTestRoute0Url, zuulTestRoute1Url, zuulTestRoute2Url}, "dynamic-route-zuul-default.json");
        discoveryGuideTestCases.testDynamicRoute2("nepxion", zuulServiceId, new String[] {zuulTestRoute0Url, zuulTestRoute1Url, zuulTestRoute2Url}, "dynamic-route-zuul.json", "dynamic-route-zuul-default.json");
    }

    /*@Test
    public void testZ1NacosDynamicalMetadataUpdated() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testNacosDynamicalMetadataUpdated(gatewayGroup, gatewayGroup, gatewayTestUrl);
            discoveryGuideTestCases.testNacosDynamicalMetadataUpdated(zuulGroup, zuulGroup, zuulTestUrl);
        }
    }

    @Test
    public void testZ2NacosDynamicalMetadataDeleted() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testNacosDynamicalMetadataDeleted(gatewayGroup, gatewayGroup, gatewayTestUrl);
            discoveryGuideTestCases.testNacosDynamicalMetadataDeleted(zuulGroup, zuulGroup, zuulTestUrl);
        }
    }

    @Test
    public void testZ3NacosDynamicalMetadataRestored() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testNacosDynamicalMetadataRestored(gatewayGroup, gatewayGroup, gatewayTestUrl);
            discoveryGuideTestCases.testNacosDynamicalMetadataRestored(zuulGroup, zuulGroup, zuulTestUrl);
        }
    }*/
}