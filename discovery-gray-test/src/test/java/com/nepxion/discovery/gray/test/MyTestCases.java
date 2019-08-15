package com.nepxion.discovery.gray.test;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import com.nepxion.discovery.plugin.test.annotation.DTest;
import com.nepxion.discovery.plugin.test.annotation.DTestGray;

public class MyTestCases {
    private static final Logger LOG = LoggerFactory.getLogger(MyTestCases.class);

    @Autowired
    private TestRestTemplate testRestTemplate;

    @DTest
    public void testNoGray(String testUrl) {
        int noRepeatCount = 0;
        List<String> resultList = new ArrayList<String>();
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            if (!resultList.contains(result)) {
                noRepeatCount++;
            }
            resultList.add(result);
        }

        Assert.assertEquals(noRepeatCount, 4);
    }

    @DTest
    public void testEnabledStrategyGray1(String testUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("mobile", "138");

        LOG.info("Header : {}", headers);

        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.exchange(testUrl, HttpMethod.GET, requestEntity, String.class, new HashMap<String, String>()).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            boolean aMatched = false;
            boolean bMatched = false;
            String[] array = result.split("->");
            for (String value : array) {
                if (value.contains("discovery-gray-service-a")) {
                    if (value.contains("[V=1.0]")) {
                        aMatched = true;
                    }
                }
                if (value.contains("discovery-gray-service-b")) {
                    if (value.contains("[V=1.0]")) {
                        bMatched = true;
                    }
                }
            }

            Assert.assertEquals(aMatched && bMatched, true);
        }
    }

    @DTest
    public void testEnabledStrategyGray2(String testUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("mobile", "133");

        LOG.info("Header : {}", headers);

        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.exchange(testUrl, HttpMethod.GET, requestEntity, String.class, new HashMap<String, String>()).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            boolean aMatched = false;
            boolean bMatched = false;
            String[] array = result.split("->");
            for (String value : array) {
                if (value.contains("discovery-gray-service-a")) {
                    if (value.contains("[V=1.1]")) {
                        aMatched = true;
                    }
                }
                if (value.contains("discovery-gray-service-b")) {
                    if (value.contains("[V=1.1]")) {
                        bMatched = true;
                    }
                }
            }

            Assert.assertEquals(aMatched && bMatched, true);
        }
    }

    @DTestGray(group = "#group", serviceId = "#serviceId", path = "test-config-strategy-version-1.xml")
    public void testVersionStrategyGray1(String group, String serviceId, String testUrl) {
        testVersionStrategyGray(testUrl);
    }

    @DTestGray(group = "#group", serviceId = "#serviceId", path = "test-config-strategy-version-2.xml")
    public void testVersionStrategyGray2(String group, String serviceId, String testUrl) {
        testVersionStrategyGray(testUrl);
    }

    @DTestGray(group = "#group", serviceId = "#serviceId", path = "test-config-strategy-version-3.xml")
    public void testVersionStrategyGray3(String group, String serviceId, String testUrl) {
        testVersionStrategyGray(testUrl);
    }

    private void testVersionStrategyGray(String testUrl) {
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            int index = result.indexOf("[V=1.0]");
            int lastIndex = result.lastIndexOf("[V=1.0]");

            Assert.assertNotEquals(index, -1);
            Assert.assertNotEquals(lastIndex, -1);
            Assert.assertNotEquals(index, lastIndex);
        }
    }

    @DTestGray(group = "#group", serviceId = "#serviceId", path = "test-config-strategy-version-weight.xml")
    public void testVersionWeightStrategyGray(String group, String serviceId, String testUrl) {
        int aV0Count = 0;
        int aV1Count = 0;
        int bV0Count = 0;
        int bV1Count = 0;

        int totalCount = 3000;
        int offset = 5;
        int aV0Weight = 90;
        int aV1Weight = 10;
        int bV0Weight = 20;
        int bV1Weight = 80;

        LOG.info("Total count={}", totalCount);
        LOG.info("A service desired : 1.0 version weight={}%, 1.1 version weight={}%", aV0Weight, aV1Weight);
        LOG.info("B service desired : 1.0 version weight={}%, 1.1 version weight={}%", bV0Weight, bV1Weight);
        LOG.info("Weight offset desired={}%", offset);

        for (int i = 0; i < totalCount; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            String[] array = result.split("->");
            for (String value : array) {
                if (value.contains("discovery-gray-service-a")) {
                    if (value.contains("[V=1.0]")) {
                        aV0Count++;
                    }
                    if (value.contains("[V=1.1]")) {
                        aV1Count++;
                    }
                }
                if (value.contains("discovery-gray-service-b")) {
                    if (value.contains("[V=1.0]")) {
                        bV0Count++;
                    }
                    if (value.contains("[V=1.1]")) {
                        bV1Count++;
                    }
                }
            }
        }

        DecimalFormat format = new DecimalFormat("0.0000");
        double aV0Reslut = Double.valueOf(format.format((double) aV0Count * 100 / totalCount));
        double aV1Reslut = Double.valueOf(format.format((double) aV1Count * 100 / totalCount));
        double bV0Reslut = Double.valueOf(format.format((double) bV0Count * 100 / totalCount));
        double bV1Reslut = Double.valueOf(format.format((double) bV1Count * 100 / totalCount));

        LOG.info("Result : A service 1.0 version weight={}%", aV0Reslut);
        LOG.info("Result : A service 1.1 version weight={}%", aV1Reslut);
        LOG.info("Result : B service 1.0 version weight={}%", bV0Reslut);
        LOG.info("Result : B service 1.1 version weight={}%", bV1Reslut);

        Assert.assertEquals(aV0Reslut > aV0Weight - offset && aV0Reslut < aV0Weight + offset, true);
        Assert.assertEquals(aV1Reslut > aV1Weight - offset && aV1Reslut < aV1Weight + offset, true);
        Assert.assertEquals(bV0Reslut > bV0Weight - offset && bV0Reslut < bV0Weight + offset, true);
        Assert.assertEquals(bV1Reslut > bV1Weight - offset && bV1Reslut < bV1Weight + offset, true);
    }

    @DTestGray(group = "#group", serviceId = "#serviceId", path = "test-config-strategy-region-1.xml")
    public void testRegionStrategyGray1(String group, String serviceId, String testUrl) {
        testRegionStrategyGray(testUrl);
    }

    @DTestGray(group = "#group", serviceId = "#serviceId", path = "test-config-strategy-region-2.xml")
    public void testRegionStrategyGray2(String group, String serviceId, String testUrl) {
        testRegionStrategyGray(testUrl);
    }

    @DTestGray(group = "#group", serviceId = "#serviceId", path = "test-config-strategy-region-3.xml")
    public void testRegionStrategyGray3(String group, String serviceId, String testUrl) {
        testRegionStrategyGray(testUrl);
    }

    private void testRegionStrategyGray(String testUrl) {
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            int index = result.indexOf("[R=dev]");
            int lastIndex = result.lastIndexOf("[R=dev]");

            Assert.assertNotEquals(index, -1);
            Assert.assertNotEquals(lastIndex, -1);
            Assert.assertNotEquals(index, lastIndex);
        }
    }

    @DTestGray(group = "#group", serviceId = "#serviceId", path = "test-config-strategy-region-weight.xml")
    public void testRegionWeightStrategyGray(String group, String serviceId, String testUrl) {
        int aDevCount = 0;
        int aQaCount = 0;
        int bDevCount = 0;
        int bQaCount = 0;

        int totalCount = 3000;
        int offset = 5;
        int aDevWeight = 85;
        int aQaWeight = 15;
        int bDevWeight = 85;
        int bQaWeight = 15;

        LOG.info("Total count={}", totalCount);
        LOG.info("A service desired : dev region weight={}%, qa region weight={}%", aDevWeight, aQaWeight);
        LOG.info("B service desired : dev region weight={}%, qa region weight={}%", bDevWeight, bQaWeight);
        LOG.info("Weight offset desired={}%", offset);

        for (int i = 0; i < totalCount; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            String[] array = result.split("->");
            for (String value : array) {
                if (value.contains("discovery-gray-service-a")) {
                    if (value.contains("[R=dev]")) {
                        aDevCount++;
                    }
                    if (value.contains("[R=qa]")) {
                        aQaCount++;
                    }
                }
                if (value.contains("discovery-gray-service-b")) {
                    if (value.contains("[R=dev]")) {
                        bDevCount++;
                    }
                    if (value.contains("[R=qa]")) {
                        bQaCount++;
                    }
                }
            }
        }

        DecimalFormat format = new DecimalFormat("0.0000");
        double aDevReslut = Double.valueOf(format.format((double) aDevCount * 100 / totalCount));
        double aQaReslut = Double.valueOf(format.format((double) aQaCount * 100 / totalCount));
        double bDevReslut = Double.valueOf(format.format((double) bDevCount * 100 / totalCount));
        double bQaReslut = Double.valueOf(format.format((double) bQaCount * 100 / totalCount));

        LOG.info("Result : A service dev region weight={}%", aDevReslut);
        LOG.info("Result : A service qa region weight={}%", aQaReslut);
        LOG.info("Result : B service dev region weight={}%", bDevReslut);
        LOG.info("Result : B service qa region weight={}%", bQaReslut);

        Assert.assertEquals(aDevReslut > aDevWeight - offset && aDevReslut < aDevWeight + offset, true);
        Assert.assertEquals(aQaReslut > aQaWeight - offset && aQaReslut < aQaWeight + offset, true);
        Assert.assertEquals(bDevReslut > bDevWeight - offset && bDevReslut < bDevWeight + offset, true);
        Assert.assertEquals(bQaReslut > bQaWeight - offset && bQaReslut < bQaWeight + offset, true);
    }

    @DTestGray(group = "#group", serviceId = "#serviceId", path = "test-config-strategy-customization.xml")
    public void testStrategyCustomizationGray1(String group, String serviceId, String testUrl) {
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            boolean aMatched = false;
            boolean bMatched = false;
            String[] array = result.split("->");
            for (String value : array) {
                if (value.contains("discovery-gray-service-a")) {
                    if (value.contains("[V=1.0]")) {
                        aMatched = true;
                    }
                }
                if (value.contains("discovery-gray-service-b")) {
                    if (value.contains("[V=1.0]")) {
                        bMatched = true;
                    }
                }
            }

            Assert.assertEquals(aMatched && bMatched, true);
        }
    }

    @DTestGray(group = "#group", serviceId = "#serviceId", path = "test-config-strategy-customization.xml")
    public void testStrategyCustomizationGray2(String group, String serviceId, String testUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("a", "1");

        LOG.info("Header : {}", headers);

        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.exchange(testUrl, HttpMethod.GET, requestEntity, String.class, new HashMap<String, String>()).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            boolean aMatched = false;
            boolean bMatched = false;
            String[] array = result.split("->");
            for (String value : array) {
                if (value.contains("discovery-gray-service-a")) {
                    if (value.contains("[V=1.0]")) {
                        aMatched = true;
                    }
                }
                if (value.contains("discovery-gray-service-b")) {
                    if (value.contains("[V=1.1]")) {
                        bMatched = true;
                    }
                }
            }

            Assert.assertEquals(aMatched && bMatched, true);
        }
    }

    @DTestGray(group = "#group", serviceId = "#serviceId", path = "test-config-strategy-customization.xml")
    public void testStrategyCustomizationGray3(String group, String serviceId, String testUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("a", "1");
        headers.add("b", "2");

        LOG.info("Header : {}", headers);

        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.exchange(testUrl, HttpMethod.GET, requestEntity, String.class, new HashMap<String, String>()).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            boolean aMatched = false;
            boolean bMatched = false;
            String[] array = result.split("->");
            for (String value : array) {
                if (value.contains("discovery-gray-service-a")) {
                    if (value.contains("[V=1.1]")) {
                        aMatched = true;
                    }
                }
                if (value.contains("discovery-gray-service-b")) {
                    if (value.contains("[V=1.1]")) {
                        bMatched = true;
                    }
                }
            }

            Assert.assertEquals(aMatched && bMatched, true);
        }
    }

    @DTestGray(group = "#group", serviceId = "#serviceId", path = "test-config-rule-version.xml")
    public void testVersionRuleGray(String group, String serviceId, String testUrl) {
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            boolean aV0Matched = false;
            boolean bV0Matched = false;
            boolean aV1Matched = false;
            boolean bV1Matched = false;
            String[] array = result.split("->");
            for (String value : array) {
                if (value.contains("discovery-gray-service-a")) {
                    if (value.contains("[V=1.0]")) {
                        aV0Matched = true;
                    }
                    if (value.contains("[V=1.1]")) {
                        aV1Matched = true;
                    }
                }
                if (value.contains("discovery-gray-service-b")) {
                    if (value.contains("[V=1.0]")) {
                        bV0Matched = true;
                    }
                    if (value.contains("[V=1.1]")) {
                        bV1Matched = true;
                    }
                }
            }

            Assert.assertEquals((aV0Matched && bV0Matched) || (aV1Matched && bV1Matched), true);
        }
    }

    @DTestGray(group = "#group", serviceId = "#serviceId", path = "test-config-rule-region.xml")
    public void testRegionRuleGray(String group, String serviceId, String testUrl) {
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            boolean aDevMatched = false;
            boolean bDevMatched = false;
            boolean aQaMatched = false;
            boolean bQaMatched = false;
            String[] array = result.split("->");
            for (String value : array) {
                if (value.contains("discovery-gray-service-a")) {
                    if (value.contains("[R=dev]")) {
                        aDevMatched = true;
                    }
                    if (value.contains("[R=qa]")) {
                        aQaMatched = true;
                    }
                }
                if (value.contains("discovery-gray-service-b")) {
                    if (value.contains("[R=dev]")) {
                        bDevMatched = true;
                    }
                    if (value.contains("[R=qa]")) {
                        bQaMatched = true;
                    }
                }
            }

            Assert.assertEquals((aDevMatched && bDevMatched) || (aQaMatched && bQaMatched), true);
        }
    }

    @DTestGray(group = "#group", serviceId = "#serviceId", path = "test-config-rule-version-weight.xml")
    public void testVersionWeightRuleGray(String group, String serviceId, String testUrl) {
        int aV0Count = 0;
        int aV1Count = 0;
        int bV0Count = 0;
        int bV1Count = 0;

        int totalCount = 3000;
        int offset = 5;
        int aV0Weight = 75;
        int aV1Weight = 25;
        int bV0Weight = 35;
        int bV1Weight = 65;

        LOG.info("Total count={}", totalCount);
        LOG.info("A service desired : 1.0 version weight={}%, 1.1 version weight={}%", aV0Weight, aV1Weight);
        LOG.info("B service desired : 1.0 version weight={}%, 1.1 version weight={}%", bV0Weight, bV1Weight);
        LOG.info("Weight offset desired={}%", offset);

        for (int i = 0; i < totalCount; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            String[] array = result.split("->");
            for (String value : array) {
                if (value.contains("discovery-gray-service-a")) {
                    if (value.contains("[V=1.0]")) {
                        aV0Count++;
                    }
                    if (value.contains("[V=1.1]")) {
                        aV1Count++;
                    }
                }
                if (value.contains("discovery-gray-service-b")) {
                    if (value.contains("[V=1.0]")) {
                        bV0Count++;
                    }
                    if (value.contains("[V=1.1]")) {
                        bV1Count++;
                    }
                }
            }
        }

        DecimalFormat format = new DecimalFormat("0.0000");
        double aV0Reslut = Double.valueOf(format.format((double) aV0Count * 100 / totalCount));
        double aV1Reslut = Double.valueOf(format.format((double) aV1Count * 100 / totalCount));
        double bV0Reslut = Double.valueOf(format.format((double) bV0Count * 100 / totalCount));
        double bV1Reslut = Double.valueOf(format.format((double) bV1Count * 100 / totalCount));

        LOG.info("Result : A service 1.0 version weight={}%", aV0Reslut);
        LOG.info("Result : A service 1.1 version weight={}%", aV1Reslut);
        LOG.info("Result : B service 1.0 version weight={}%", bV0Reslut);
        LOG.info("Result : B service 1.1 version weight={}%", bV1Reslut);

        Assert.assertEquals(aV0Reslut > aV0Weight - offset && aV0Reslut < aV0Weight + offset, true);
        Assert.assertEquals(aV1Reslut > aV1Weight - offset && aV1Reslut < aV1Weight + offset, true);
        Assert.assertEquals(bV0Reslut > bV0Weight - offset && bV0Reslut < bV0Weight + offset, true);
        Assert.assertEquals(bV1Reslut > bV1Weight - offset && bV1Reslut < bV1Weight + offset, true);
    }

    @DTestGray(group = "#group", serviceId = "#serviceId", path = "test-config-rule-region-weight.xml")
    public void testRegionWeightRuleGray(String group, String serviceId, String testUrl) {
        int aDevCount = 0;
        int aQaCount = 0;
        int bDevCount = 0;
        int bQaCount = 0;

        int totalCount = 3000;
        int offset = 5;
        int aDevWeight = 95;
        int aQaWeight = 5;
        int bDevWeight = 95;
        int bQaWeight = 5;

        LOG.info("Total count={}", totalCount);
        LOG.info("A service desired : dev region weight={}%, qa region weight={}%", aDevWeight, aQaWeight);
        LOG.info("B service desired : dev region weight={}%, qa region weight={}%", bDevWeight, bQaWeight);
        LOG.info("Weight offset desired={}%", offset);

        for (int i = 0; i < totalCount; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            String[] array = result.split("->");
            for (String value : array) {
                if (value.contains("discovery-gray-service-a")) {
                    if (value.contains("[R=dev]")) {
                        aDevCount++;
                    }
                    if (value.contains("[R=qa]")) {
                        aQaCount++;
                    }
                }
                if (value.contains("discovery-gray-service-b")) {
                    if (value.contains("[R=dev]")) {
                        bDevCount++;
                    }
                    if (value.contains("[R=qa]")) {
                        bQaCount++;
                    }
                }
            }
        }

        DecimalFormat format = new DecimalFormat("0.0000");
        double aDevReslut = Double.valueOf(format.format((double) aDevCount * 100 / totalCount));
        double aQaReslut = Double.valueOf(format.format((double) aQaCount * 100 / totalCount));
        double bDevReslut = Double.valueOf(format.format((double) bDevCount * 100 / totalCount));
        double bQaReslut = Double.valueOf(format.format((double) bQaCount * 100 / totalCount));

        LOG.info("Result : A service dev region weight={}%", aDevReslut);
        LOG.info("Result : A service qa region weight={}%", aQaReslut);
        LOG.info("Result : B service dev region weight={}%", bDevReslut);
        LOG.info("Result : B service qa region weight={}%", bQaReslut);

        Assert.assertEquals(aDevReslut > aDevWeight - offset && aDevReslut < aDevWeight + offset, true);
        Assert.assertEquals(aQaReslut > aQaWeight - offset && aQaReslut < aQaWeight + offset, true);
        Assert.assertEquals(bDevReslut > bDevWeight - offset && bDevReslut < bDevWeight + offset, true);
        Assert.assertEquals(bQaReslut > bQaWeight - offset && bQaReslut < bQaWeight + offset, true);
    }

    @DTestGray(group = "#group", serviceId = "#serviceId", path = "test-config-rule-version-composite.xml")
    public void testVersionCompositeRuleGray(String group, String serviceId, String testUrl) {
        int aV0Count = 0;
        int aV1Count = 0;

        int totalCount = 3000;
        int offset = 5;
        int aV0Weight = 40;
        int aV1Weight = 60;

        LOG.info("Total count={}", totalCount);
        LOG.info("A service desired : 1.0 version weight={}%, 1.1 version weight={}%", aV0Weight, aV1Weight);
        LOG.info("Weight offset desired={}%", offset);
        LOG.info("Route desired : A Service 1.0 version -> B Service 1.0 version, A Service 1.1 version -> B Service 1.1 version");

        for (int i = 0; i < totalCount; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            boolean aV0Matched = false;
            boolean bV0Matched = false;
            boolean aV1Matched = false;
            boolean bV1Matched = false;
            String[] array = result.split("->");
            for (String value : array) {
                if (value.contains("discovery-gray-service-a")) {
                    if (value.contains("[V=1.0]")) {
                        aV0Count++;
                        aV0Matched = true;
                    }
                    if (value.contains("[V=1.1]")) {
                        aV1Count++;
                        aV1Matched = true;
                    }
                }
                if (value.contains("discovery-gray-service-b")) {
                    if (value.contains("[V=1.0]")) {
                        bV0Matched = true;
                    }
                    if (value.contains("[V=1.1]")) {
                        bV1Matched = true;
                    }
                }
            }

            Assert.assertEquals((aV0Matched && bV0Matched) || (aV1Matched && bV1Matched), true);
        }

        DecimalFormat format = new DecimalFormat("0.0000");
        double aV0Reslut = Double.valueOf(format.format((double) aV0Count * 100 / totalCount));
        double aV1Reslut = Double.valueOf(format.format((double) aV1Count * 100 / totalCount));
        LOG.info("Result : A service 1.0 version weight={}%", aV0Reslut);
        LOG.info("Result : A service 1.1 version weight={}%", aV1Reslut);

        Assert.assertEquals(aV0Reslut > aV0Weight - offset && aV0Reslut < aV0Weight + offset, true);
        Assert.assertEquals(aV1Reslut > aV1Weight - offset && aV1Reslut < aV1Weight + offset, true);
    }
}