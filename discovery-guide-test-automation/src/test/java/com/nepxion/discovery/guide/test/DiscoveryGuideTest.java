package com.nepxion.discovery.guide.test;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.nepxion.banner.BannerConstant;
import com.nepxion.discovery.plugin.test.automation.application.TestApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { TestApplication.class, DiscoveryGuideTestConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
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

    @Value("${gray.weight.testcases.enabled:true}")
    private Boolean weightTestcasesEnabled;

    @Value("${testcase.loop.times:1}")
    private Integer loopTimes;

    @Autowired
    private DiscoveryGuideTestCases discoveryGuideTestCases;

    private static long startTime;

    @BeforeAll
    public static void beforeTest() {
        // 是否要显示旗标
        System.setProperty(BannerConstant.BANNER_SHOWN, "true");
        // 是否把旗标渲染成彩色
        System.setProperty(BannerConstant.BANNER_SHOWN_ANSI_MODE, "true");

        startTime = System.currentTimeMillis();
    }

    @AfterAll
    public static void afterTest() {
        LOG.info("* Finished automation test in {} seconds", (System.currentTimeMillis() - startTime) / 1000);
    }

    @Test
    public void testANoGray() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testNoGray(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            discoveryGuideTestCases.testNoGray(gatewayGroup, gatewayGroup, gatewayTestUrl);
        }
    }

    @Test
    public void testEnabledStrategyGray1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testEnabledStrategyGray1(gatewayTestUrl);
        }
    }

    @Test
    public void testEnabledStrategyGray2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testEnabledStrategyGray2(gatewayTestUrl);
        }
    }

    @Test
    public void testVersionRouteFilter1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testVersionRouteFilter1(gatewayTestUrl);
        }
    }

    @Test
    public void testVersionRouteFilter2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testVersionRouteFilter2(gatewayTestUrl);
        }
    }

    @Test
    public void testRegionRouteFilter1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testRegionRouteFilter1(gatewayTestUrl);
        }
    }

    @Test
    public void testRegionRouteFilter2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testRegionRouteFilter2(gatewayTestUrl);
        }
    }

    @Test
    public void testAddressRouteFilter1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testAddressRouteFilter1(gatewayTestUrl);
        }
    }

    @Test
    public void testAddressRouteFilter2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testAddressRouteFilter2(gatewayTestUrl);
        }
    }

    @Test
    public void testVersionStrategyGray1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testVersionStrategyGray1(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        }
    }

    @Test
    public void testVersionStrategyGray2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testVersionStrategyGray2(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        }
    }

    @Test
    public void testVersionStrategyGray3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testVersionStrategyGray3(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        }
    }

    @Test
    public void testVersionWeightStrategyGray() throws Exception {
        if (weightTestcasesEnabled) {
            for (int i = 0; i < loopTimes; i++) {
                discoveryGuideTestCases.testVersionWeightStrategyGray(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            }
        }
    }

    @Test
    public void testRegionStrategyGray1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testRegionStrategyGray1(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        }
    }

    @Test
    public void testRegionStrategyGray2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testRegionStrategyGray2(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        }
    }

    public void testRegionStrategyGray3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testRegionStrategyGray3(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        }
    }

    @Test
    public void testRegionWeightStrategyGray() throws Exception {
        if (weightTestcasesEnabled) {
            for (int i = 0; i < loopTimes; i++) {
                discoveryGuideTestCases.testRegionWeightStrategyGray(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            }
        }
    }

    @Test
    public void testStrategyBlueGreen1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyBlueGreen1(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        }
    }

    @Test
    public void testStrategyBlueGreen2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyBlueGreen2(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        }
    }

    @Test
    public void testStrategyBlueGreen3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyBlueGreen3(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        }
    }

    @Test
    public void testStrategyBlueGreenHeader1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyBlueGreenHeader1(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        }
    }

    @Test
    public void testStrategyBlueGreenHeader2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyBlueGreenHeader2(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        }
    }

    @Test
    public void testStrategyBlueGreenHeader3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyBlueGreenHeader3(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        }
    }

    @Test
    public void testStrategyBlueGreenHeaderParameterCookie() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyBlueGreenHeaderParameterCookie(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        }
    }

    @Test
    public void testStrategyAll1Test1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyAll1(gatewayGroup, gatewayServiceId, gatewayTestUrl, "0");
        }
    }

    @Test
    public void testStrategyAll1Test2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyAll1(gatewayGroup, gatewayServiceId, gatewayTestUrl, "1");
        }
    }

    @Test
    public void testStrategyAll2Test1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyAll2(gatewayGroup, gatewayServiceId, gatewayTestUrl, "2");
        }
    }

   @Test
    public void testStrategyAll2Test2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyAll2(gatewayGroup, gatewayServiceId, gatewayTestUrl, "3");
        }
    }

   @Test
   public void testStrategyAll2Test3() throws Exception {
       for (int i = 0; i < loopTimes; i++) {
           discoveryGuideTestCases.testStrategyAll2(gatewayGroup, gatewayServiceId, gatewayTestUrl, null);
       }
   }

    @Test
    public void testInspectStrategyBlueGreenHeader1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testInspectStrategyBlueGreenHeader1(gatewayGroup, gatewayServiceId, gatewayInspectUrl);
        }
    }

    @Test
    public void testInspectStrategyBlueGreenHeader2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testInspectStrategyBlueGreenHeader2(gatewayGroup, gatewayServiceId, gatewayInspectUrl);
        }
    }

    @Test
    public void testInspectStrategyBlueGreenHeader3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testInspectStrategyBlueGreenHeader3(gatewayGroup, gatewayServiceId, gatewayInspectUrl);
        }
    }

    @Test
    public void testStrategyGray1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyGray1(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        }
    }

    @Test
    public void testStrategyGray2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyGray2(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        }
    }

    @Test
    public void testStrategyGray3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyGray3(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        }
    }

    @Test
    public void testStrategyGray4() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyGray4(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        }
    }

    @Test
    public void testVersionRuleGray() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testVersionRuleGray(gatewayGroup, gatewayGroup, gatewayTestUrl);
        }
    }

    @Test
    public void testRegionRuleGray() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testRegionRuleGray(gatewayGroup, gatewayGroup, gatewayTestUrl);
        }
    }

    @Test
    public void testVersionWeightRuleGray() throws Exception {
        if (weightTestcasesEnabled) {
            for (int i = 0; i < loopTimes; i++) {
                discoveryGuideTestCases.testVersionWeightRuleGray(gatewayGroup, gatewayGroup, gatewayTestUrl);
            }
        }
    }

    @Test
    public void testRegionWeightRuleGray() throws Exception {
        if (weightTestcasesEnabled) {
            for (int i = 0; i < loopTimes; i++) {
                discoveryGuideTestCases.testRegionWeightRuleGray(gatewayGroup, gatewayGroup, gatewayTestUrl);
            }
        }
    }

    @Test
    public void testVersionCompositeRuleGray() throws Exception {
        if (weightTestcasesEnabled) {
            for (int i = 0; i < loopTimes; i++) {
                discoveryGuideTestCases.testVersionCompositeRuleGray(gatewayGroup, gatewayGroup, gatewayTestUrl);
            }
        }
    }

    @Test
    public void testBlacklist1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testBlacklist1(gatewayGroup, gatewayGroup, gatewayTestUrl);
        }
    }

    @Test
    public void testBlacklist2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testBlacklist2(gatewayTestUrl);
        }
    }

    @Test
    public void testBlacklist3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testBlacklist3(gatewayTestUrl);
        }
    }

    @Test
    public void testEnv() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testEnv(gatewayTestUrl);
        }
    }

    @Test
    public void testSentinelAuthority1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testSentinelAuthority1(gatewayGroup, gatewayTestUrl);
        }
    }

    @Test
    public void testSentinelAuthority2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testSentinelAuthority2(gatewayGroup, gatewayTestUrl);
        }
    }

    @Test
    public void testSentinelAuthority3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testSentinelAuthority3(gatewayGroup, gatewayTestUrl);
        }
    }

    @Test
    public void testDynamicRoute() throws Exception {
        discoveryGuideTestCases.testDynamicRoute1(gatewayGroup, gatewayServiceId + "-dynamic-route", new String[] {gatewayTestRoute0Url, gatewayTestRoute1Url, gatewayTestRoute2Url}, "dynamic-route-gateway-default.json", "dynamic-route-gateway-default.json");
        discoveryGuideTestCases.testDynamicRoute2(gatewayGroup, gatewayServiceId + "-dynamic-route", new String[] {gatewayTestRoute0Url, gatewayTestRoute1Url, gatewayTestRoute2Url}, "dynamic-route-gateway.json", "dynamic-route-gateway-default.json");
    }

    /*@Test
    public void testZ1NacosDynamicalMetadataUpdated() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testNacosDynamicalMetadataUpdated(gatewayGroup, gatewayGroup, gatewayTestUrl);
        }
    }

    @Test
    public void testZ2NacosDynamicalMetadataDeleted() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testNacosDynamicalMetadataDeleted(gatewayGroup, gatewayGroup, gatewayTestUrl);
        }
    }

    @Test
    public void testZ3NacosDynamicalMetadataRestored() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testNacosDynamicalMetadataRestored(gatewayGroup, gatewayGroup, gatewayTestUrl);
        }
    }*/
}