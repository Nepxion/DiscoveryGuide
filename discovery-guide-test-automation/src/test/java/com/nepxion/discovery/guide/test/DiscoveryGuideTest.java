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
    public void testStrategyCustomizationBlueGreen1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyCustomizationBlueGreen1(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        }
    }

    @Test
    public void testStrategyCustomizationBlueGreen2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyCustomizationBlueGreen2(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        }
    }

    @Test
    public void testStrategyCustomizationBlueGreen3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyCustomizationBlueGreen3(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        }
    }

    @Test
    public void testStrategyCustomizationBlueGreenHeader1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyCustomizationBlueGreenHeader1(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        }
    }

    @Test
    public void testStrategyCustomizationBlueGreenHeader2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyCustomizationBlueGreenHeader2(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        }
    }

    @Test
    public void testStrategyCustomizationBlueGreenHeader3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyCustomizationBlueGreenHeader3(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        }
    }

    @Test
    public void testStrategyCustomizationBlueGreenHeaderParameterCookie() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyCustomizationBlueGreenHeaderParameterCookie(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        }
    }

    @Test
    public void testInspectStrategyCustomizationBlueGreenHeader1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testInspectStrategyCustomizationBlueGreenHeader1(gatewayGroup, gatewayServiceId, gatewayInspectUrl);
        }
    }

    @Test
    public void testInspectStrategyCustomizationBlueGreenHeader2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testInspectStrategyCustomizationBlueGreenHeader2(gatewayGroup, gatewayServiceId, gatewayInspectUrl);
        }
    }

    @Test
    public void testInspectStrategyCustomizationBlueGreenHeader3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testInspectStrategyCustomizationBlueGreenHeader3(gatewayGroup, gatewayServiceId, gatewayInspectUrl);
        }
    }

    @Test
    public void testStrategyCustomizationGray1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyCustomizationGray1(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        }
    }

    @Test
    public void testStrategyCustomizationGray2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyCustomizationGray2(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        }
    }

    @Test
    public void testStrategyCustomizationGray3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyCustomizationGray3(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        }
    }

    @Test
    public void testStrategyCustomizationGray4() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testStrategyCustomizationGray4(gatewayGroup, gatewayServiceId, gatewayTestUrl);
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
            discoveryGuideTestCases.testSentinelAuthority1(gatewayTestUrl);
        }
    }

    @Test
    public void testSentinelAuthority2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testSentinelAuthority2(gatewayTestUrl);
        }
    }

    @Test
    public void testSentinelAuthority3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            discoveryGuideTestCases.testSentinelAuthority3(gatewayTestUrl);
        }
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