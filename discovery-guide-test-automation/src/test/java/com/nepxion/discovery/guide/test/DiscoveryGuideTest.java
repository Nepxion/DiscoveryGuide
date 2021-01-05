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

    @Value("${gateway.enabled:true}")
    private Boolean gatewayEnabled;

    @Value("${gateway.group}")
    private String gatewayGroup;

    @Value("${gateway.service.id}")
    private String gatewayServiceId;

    @Value("${gateway.test.url}")
    private String gatewayTestUrl;

    @Value("${gateway.inspect.url}")
    private String gatewayInspectUrl;

    @Value("${zuul.enabled:true}")
    private Boolean zuulEnabled;

    @Value("${zuul.group}")
    private String zuulGroup;

    @Value("${zuul.service.id}")
    private String zuulServiceId;

    @Value("${zuul.test.url}")
    private String zuulTestUrl;

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
            if (gatewayEnabled) {
                discoveryGuideTestCases.testNoGray(gatewayGroup, gatewayServiceId, gatewayTestUrl);
                discoveryGuideTestCases.testNoGray(gatewayGroup, gatewayGroup, gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testNoGray(zuulGroup, zuulServiceId, zuulTestUrl);
                discoveryGuideTestCases.testNoGray(zuulGroup, zuulGroup, zuulTestUrl);
            }
        }
    }

    @Test
    public void testEnabledStrategyGray1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testEnabledStrategyGray1(gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testEnabledStrategyGray1(zuulTestUrl);
            }
        }
    }

    @Test
    public void testEnabledStrategyGray2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testEnabledStrategyGray2(gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testEnabledStrategyGray2(zuulTestUrl);
            }
        }
    }

    @Test
    public void testVersionRouteFilter1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testVersionRouteFilter1(gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testVersionRouteFilter1(zuulTestUrl);
            }
        }
    }

    @Test
    public void testVersionRouteFilter2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testVersionRouteFilter2(gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testVersionRouteFilter2(zuulTestUrl);
            }
        }
    }

    @Test
    public void testRegionRouteFilter1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testRegionRouteFilter1(gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testRegionRouteFilter1(zuulTestUrl);
            }
        }
    }

    @Test
    public void testRegionRouteFilter2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testRegionRouteFilter2(gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testRegionRouteFilter2(zuulTestUrl);
            }
        }
    }

    @Test
    public void testAddressRouteFilter1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testAddressRouteFilter1(gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testAddressRouteFilter1(zuulTestUrl);
            }
        }
    }

    @Test
    public void testAddressRouteFilter2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testAddressRouteFilter2(gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testAddressRouteFilter2(zuulTestUrl);
            }
        }
    }

    @Test
    public void testVersionStrategyGray1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testVersionStrategyGray1(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testVersionStrategyGray1(zuulGroup, zuulServiceId, zuulTestUrl);
            }
        }
    }

    @Test
    public void testVersionStrategyGray2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testVersionStrategyGray2(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testVersionStrategyGray2(zuulGroup, zuulServiceId, zuulTestUrl);
            }
        }
    }

    @Test
    public void testVersionStrategyGray3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testVersionStrategyGray3(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testVersionStrategyGray3(zuulGroup, zuulServiceId, zuulTestUrl);
            }
        }
    }

    @Test
    public void testVersionWeightStrategyGray() throws Exception {
        if (weightTestcasesEnabled) {
            for (int i = 0; i < loopTimes; i++) {
                if (gatewayEnabled) {
                    discoveryGuideTestCases.testVersionWeightStrategyGray(gatewayGroup, gatewayServiceId, gatewayTestUrl);
                }
                if (zuulEnabled) {
                    discoveryGuideTestCases.testVersionWeightStrategyGray(zuulGroup, zuulServiceId, zuulTestUrl);
                }
            }
        }
    }

    @Test
    public void testRegionStrategyGray1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testRegionStrategyGray1(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testRegionStrategyGray1(zuulGroup, zuulServiceId, zuulTestUrl);
            }
        }
    }

    @Test
    public void testRegionStrategyGray2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testRegionStrategyGray2(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testRegionStrategyGray2(zuulGroup, zuulServiceId, zuulTestUrl);
            }
        }
    }

    public void testRegionStrategyGray3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testRegionStrategyGray3(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testRegionStrategyGray3(zuulGroup, zuulServiceId, zuulTestUrl);
            }
        }
    }

    @Test
    public void testRegionWeightStrategyGray() throws Exception {
        if (weightTestcasesEnabled) {
            for (int i = 0; i < loopTimes; i++) {
                if (gatewayEnabled) {
                    discoveryGuideTestCases.testRegionWeightStrategyGray(gatewayGroup, gatewayServiceId, gatewayTestUrl);
                }
                if (zuulEnabled) {
                    discoveryGuideTestCases.testRegionWeightStrategyGray(zuulGroup, zuulServiceId, zuulTestUrl);
                }
            }
        }
    }

    @Test
    public void testStrategyCustomizationBlueGreen1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testStrategyCustomizationBlueGreen1(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testStrategyCustomizationBlueGreen1(zuulGroup, zuulServiceId, zuulTestUrl);
            }
        }
    }

    @Test
    public void testStrategyCustomizationBlueGreen2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testStrategyCustomizationBlueGreen2(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testStrategyCustomizationBlueGreen2(zuulGroup, zuulServiceId, zuulTestUrl);
            }
        }
    }

    @Test
    public void testStrategyCustomizationBlueGreen3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testStrategyCustomizationBlueGreen3(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testStrategyCustomizationBlueGreen3(zuulGroup, zuulServiceId, zuulTestUrl);
            }
        }
    }

    @Test
    public void testStrategyCustomizationBlueGreenHeader1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testStrategyCustomizationBlueGreenHeader1(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testStrategyCustomizationBlueGreenHeader1(zuulGroup, zuulServiceId, zuulTestUrl);
            }
        }
    }

    @Test
    public void testStrategyCustomizationBlueGreenHeader2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testStrategyCustomizationBlueGreenHeader2(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testStrategyCustomizationBlueGreenHeader2(zuulGroup, zuulServiceId, zuulTestUrl);
            }
        }
    }

    @Test
    public void testStrategyCustomizationBlueGreenHeader3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testStrategyCustomizationBlueGreenHeader3(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testStrategyCustomizationBlueGreenHeader3(zuulGroup, zuulServiceId, zuulTestUrl);
            }
        }
    }

    @Test
    public void testStrategyCustomizationBlueGreenHeaderParameterCookie() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testStrategyCustomizationBlueGreenHeaderParameterCookie(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testStrategyCustomizationBlueGreenHeaderParameterCookie(zuulGroup, zuulServiceId, zuulTestUrl);
            }
        }
    }

    @Test
    public void testInspectStrategyCustomizationBlueGreenHeader1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testInspectStrategyCustomizationBlueGreenHeader1(gatewayGroup, gatewayServiceId, gatewayInspectUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testInspectStrategyCustomizationBlueGreenHeader1(zuulGroup, zuulServiceId, zuulInspectUrl);
            }
        }
    }

    @Test
    public void testInspectStrategyCustomizationBlueGreenHeader2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testInspectStrategyCustomizationBlueGreenHeader2(gatewayGroup, gatewayServiceId, gatewayInspectUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testInspectStrategyCustomizationBlueGreenHeader2(zuulGroup, zuulServiceId, zuulInspectUrl);
            }
        }
    }

    @Test
    public void testInspectStrategyCustomizationBlueGreenHeader3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testInspectStrategyCustomizationBlueGreenHeader3(gatewayGroup, gatewayServiceId, gatewayInspectUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testInspectStrategyCustomizationBlueGreenHeader3(zuulGroup, zuulServiceId, zuulInspectUrl);
            }
        }
    }

    @Test
    public void testStrategyCustomizationGray1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testStrategyCustomizationGray1(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testStrategyCustomizationGray1(zuulGroup, zuulServiceId, zuulTestUrl);
            }
        }
    }

    @Test
    public void testStrategyCustomizationGray2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testStrategyCustomizationGray2(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testStrategyCustomizationGray2(zuulGroup, zuulServiceId, zuulTestUrl);
            }
        }
    }

    @Test
    public void testStrategyCustomizationGray3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testStrategyCustomizationGray3(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testStrategyCustomizationGray3(zuulGroup, zuulServiceId, zuulTestUrl);
            }
        }
    }

    @Test
    public void testStrategyCustomizationGray4() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testStrategyCustomizationGray4(gatewayGroup, gatewayServiceId, gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testStrategyCustomizationGray4(zuulGroup, zuulServiceId, zuulTestUrl);
            }
        }
    }

    @Test
    public void testVersionRuleGray() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testVersionRuleGray(gatewayGroup, gatewayGroup, gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testVersionRuleGray(zuulGroup, zuulGroup, zuulTestUrl);
            }
        }
    }

    @Test
    public void testRegionRuleGray() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testRegionRuleGray(gatewayGroup, gatewayGroup, gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testRegionRuleGray(zuulGroup, zuulGroup, zuulTestUrl);
            }
        }
    }

    @Test
    public void testVersionWeightRuleGray() throws Exception {
        if (weightTestcasesEnabled) {
            for (int i = 0; i < loopTimes; i++) {
                if (gatewayEnabled) {
                    discoveryGuideTestCases.testVersionWeightRuleGray(gatewayGroup, gatewayGroup, gatewayTestUrl);
                }
                if (zuulEnabled) {
                    discoveryGuideTestCases.testVersionWeightRuleGray(zuulGroup, zuulGroup, zuulTestUrl);
                }
            }
        }
    }

    @Test
    public void testRegionWeightRuleGray() throws Exception {
        if (weightTestcasesEnabled) {
            for (int i = 0; i < loopTimes; i++) {
                if (gatewayEnabled) {
                    discoveryGuideTestCases.testRegionWeightRuleGray(gatewayGroup, gatewayGroup, gatewayTestUrl);
                }
                if (zuulEnabled) {
                    discoveryGuideTestCases.testRegionWeightRuleGray(zuulGroup, zuulGroup, zuulTestUrl);
                }
            }
        }
    }

    @Test
    public void testVersionCompositeRuleGray() throws Exception {
        if (weightTestcasesEnabled) {
            for (int i = 0; i < loopTimes; i++) {
                if (gatewayEnabled) {
                    discoveryGuideTestCases.testVersionCompositeRuleGray(gatewayGroup, gatewayGroup, gatewayTestUrl);
                }
                if (zuulEnabled) {
                    discoveryGuideTestCases.testVersionCompositeRuleGray(zuulGroup, zuulGroup, zuulTestUrl);
                }
            }
        }
    }

    @Test
    public void testBlacklist1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testBlacklist1(gatewayGroup, gatewayGroup, gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testBlacklist1(zuulGroup, zuulGroup, zuulTestUrl);
            }
        }
    }

    @Test
    public void testBlacklist2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testBlacklist2(gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testBlacklist2(zuulTestUrl);
            }
        }
    }

    @Test
    public void testBlacklist3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testBlacklist3(gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testBlacklist3(zuulTestUrl);
            }
        }
    }

    @Test
    public void testEnv() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testEnv(gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testEnv(zuulTestUrl);
            }
        }
    }

    @Test
    public void testSentinelAuthority1() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testSentinelAuthority1(gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testSentinelAuthority1(zuulTestUrl);
            }
        }
    }

    @Test
    public void testSentinelAuthority2() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testSentinelAuthority2(gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testSentinelAuthority2(zuulTestUrl);
            }
        }
    }

    @Test
    public void testSentinelAuthority3() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testSentinelAuthority3(gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testSentinelAuthority3(zuulTestUrl);
            }
        }
    }

    @Test
    public void testZ1NacosDynamicalMetadataUpdated() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testNacosDynamicalMetadataUpdated(gatewayGroup, gatewayGroup, gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testNacosDynamicalMetadataUpdated(zuulGroup, zuulGroup, zuulTestUrl);
            }
        }
    }

    @Test
    public void testZ2NacosDynamicalMetadataDeleted() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testNacosDynamicalMetadataDeleted(gatewayGroup, gatewayGroup, gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testNacosDynamicalMetadataDeleted(zuulGroup, zuulGroup, zuulTestUrl);
            }
        }
    }

    @Test
    public void testZ3NacosDynamicalMetadataRestored() throws Exception {
        for (int i = 0; i < loopTimes; i++) {
            if (gatewayEnabled) {
                discoveryGuideTestCases.testNacosDynamicalMetadataRestored(gatewayGroup, gatewayGroup, gatewayTestUrl);
            }
            if (zuulEnabled) {
                discoveryGuideTestCases.testNacosDynamicalMetadataRestored(zuulGroup, zuulGroup, zuulTestUrl);
            }
        }
    }
}