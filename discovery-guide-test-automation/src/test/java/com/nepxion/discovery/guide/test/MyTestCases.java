package com.nepxion.discovery.guide.test;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import com.nepxion.discovery.plugin.test.annotation.DTest;
import com.nepxion.discovery.plugin.test.annotation.DTestConfig;

public class MyTestCases {
    private static final Logger LOG = LoggerFactory.getLogger(MyTestCases.class);

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Value("${gray.weight.testcase.sample.count:3000}")
    private Integer sampleCount;

    @Value("${gray.weight.testcase.result.offset:5}")
    private Integer resultOffset;

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
                if (value.contains("discovery-guide-service-a")) {
                    if (value.contains("[V=1.0]")) {
                        aMatched = true;
                    }
                }
                if (value.contains("discovery-guide-service-b")) {
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
                if (value.contains("discovery-guide-service-a")) {
                    if (value.contains("[V=1.1]")) {
                        aMatched = true;
                    }
                }
                if (value.contains("discovery-guide-service-b")) {
                    if (value.contains("[V=1.1]")) {
                        bMatched = true;
                    }
                }
            }

            Assert.assertEquals(aMatched && bMatched, true);
        }
    }

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-version-1.xml", resetPath = "gray-default.xml")
    public void testVersionStrategyGray1(String group, String serviceId, String testUrl) {
        testVersionStrategyGray(testUrl);
    }

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-version-2.xml", resetPath = "gray-default.xml")
    public void testVersionStrategyGray2(String group, String serviceId, String testUrl) {
        testVersionStrategyGray(testUrl);
    }

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-version-3.xml", resetPath = "gray-default.xml")
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

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-version-weight.xml", resetPath = "gray-default.xml")
    public void testVersionWeightStrategyGray(String group, String serviceId, String testUrl) {
        int aV0Count = 0;
        int aV1Count = 0;
        int bV0Count = 0;
        int bV1Count = 0;

        int aV0Weight = 90;
        int aV1Weight = 10;
        int bV0Weight = 20;
        int bV1Weight = 80;

        LOG.info("Sample count={}", sampleCount);
        LOG.info("Weight result offset desired={}%", resultOffset);
        LOG.info("A service desired : 1.0 version weight={}%, 1.1 version weight={}%", aV0Weight, aV1Weight);
        LOG.info("B service desired : 1.0 version weight={}%, 1.1 version weight={}%", bV0Weight, bV1Weight);

        for (int i = 0; i < sampleCount; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            String[] array = result.split("->");
            for (String value : array) {
                if (value.contains("discovery-guide-service-a")) {
                    if (value.contains("[V=1.0]")) {
                        aV0Count++;
                    }
                    if (value.contains("[V=1.1]")) {
                        aV1Count++;
                    }
                }
                if (value.contains("discovery-guide-service-b")) {
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
        double aV0Reslut = Double.valueOf(format.format((double) aV0Count * 100 / sampleCount));
        double aV1Reslut = Double.valueOf(format.format((double) aV1Count * 100 / sampleCount));
        double bV0Reslut = Double.valueOf(format.format((double) bV0Count * 100 / sampleCount));
        double bV1Reslut = Double.valueOf(format.format((double) bV1Count * 100 / sampleCount));

        LOG.info("Result : A service 1.0 version weight={}%", aV0Reslut);
        LOG.info("Result : A service 1.1 version weight={}%", aV1Reslut);
        LOG.info("Result : B service 1.0 version weight={}%", bV0Reslut);
        LOG.info("Result : B service 1.1 version weight={}%", bV1Reslut);

        Assert.assertEquals(aV0Reslut > aV0Weight - resultOffset && aV0Reslut < aV0Weight + resultOffset, true);
        Assert.assertEquals(aV1Reslut > aV1Weight - resultOffset && aV1Reslut < aV1Weight + resultOffset, true);
        Assert.assertEquals(bV0Reslut > bV0Weight - resultOffset && bV0Reslut < bV0Weight + resultOffset, true);
        Assert.assertEquals(bV1Reslut > bV1Weight - resultOffset && bV1Reslut < bV1Weight + resultOffset, true);
    }

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-region-1.xml", resetPath = "gray-default.xml")
    public void testRegionStrategyGray1(String group, String serviceId, String testUrl) {
        testRegionStrategyGray(testUrl);
    }

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-region-2.xml", resetPath = "gray-default.xml")
    public void testRegionStrategyGray2(String group, String serviceId, String testUrl) {
        testRegionStrategyGray(testUrl);
    }

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-region-3.xml", resetPath = "gray-default.xml")
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

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-region-weight.xml", resetPath = "gray-default.xml")
    public void testRegionWeightStrategyGray(String group, String serviceId, String testUrl) {
        int aDevCount = 0;
        int aQaCount = 0;
        int bDevCount = 0;
        int bQaCount = 0;

        int aDevWeight = 85;
        int aQaWeight = 15;
        int bDevWeight = 85;
        int bQaWeight = 15;

        LOG.info("Sample count={}", sampleCount);
        LOG.info("Weight result offset desired={}%", resultOffset);
        LOG.info("A service desired : dev region weight={}%, qa region weight={}%", aDevWeight, aQaWeight);
        LOG.info("B service desired : dev region weight={}%, qa region weight={}%", bDevWeight, bQaWeight);

        for (int i = 0; i < sampleCount; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            String[] array = result.split("->");
            for (String value : array) {
                if (value.contains("discovery-guide-service-a")) {
                    if (value.contains("[R=dev]")) {
                        aDevCount++;
                    }
                    if (value.contains("[R=qa]")) {
                        aQaCount++;
                    }
                }
                if (value.contains("discovery-guide-service-b")) {
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
        double aDevReslut = Double.valueOf(format.format((double) aDevCount * 100 / sampleCount));
        double aQaReslut = Double.valueOf(format.format((double) aQaCount * 100 / sampleCount));
        double bDevReslut = Double.valueOf(format.format((double) bDevCount * 100 / sampleCount));
        double bQaReslut = Double.valueOf(format.format((double) bQaCount * 100 / sampleCount));

        LOG.info("Result : A service dev region weight={}%", aDevReslut);
        LOG.info("Result : A service qa region weight={}%", aQaReslut);
        LOG.info("Result : B service dev region weight={}%", bDevReslut);
        LOG.info("Result : B service qa region weight={}%", bQaReslut);

        Assert.assertEquals(aDevReslut > aDevWeight - resultOffset && aDevReslut < aDevWeight + resultOffset, true);
        Assert.assertEquals(aQaReslut > aQaWeight - resultOffset && aQaReslut < aQaWeight + resultOffset, true);
        Assert.assertEquals(bDevReslut > bDevWeight - resultOffset && bDevReslut < bDevWeight + resultOffset, true);
        Assert.assertEquals(bQaReslut > bQaWeight - resultOffset && bQaReslut < bQaWeight + resultOffset, true);
    }

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-customization-blue-green.xml", resetPath = "gray-default.xml")
    public void testStrategyCustomizationBlueGreen1(String group, String serviceId, String testUrl) {
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            boolean aMatched = false;
            boolean bMatched = false;
            String[] array = result.split("->");
            for (String value : array) {
                if (value.contains("discovery-guide-service-a")) {
                    if (value.contains("[V=1.0]")) {
                        aMatched = true;
                    }
                }
                if (value.contains("discovery-guide-service-b")) {
                    if (value.contains("[V=1.0]")) {
                        bMatched = true;
                    }
                }
            }

            Assert.assertEquals(aMatched && bMatched, true);
        }
    }

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-customization-blue-green.xml", resetPath = "gray-default.xml")
    public void testStrategyCustomizationBlueGreen2(String group, String serviceId, String testUrl) {
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
                if (value.contains("discovery-guide-service-a")) {
                    if (value.contains("[V=1.0]")) {
                        aMatched = true;
                    }
                }
                if (value.contains("discovery-guide-service-b")) {
                    if (value.contains("[V=1.1]")) {
                        bMatched = true;
                    }
                }
            }

            Assert.assertEquals(aMatched && bMatched, true);
        }
    }

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-customization-blue-green.xml", resetPath = "gray-default.xml")
    public void testStrategyCustomizationBlueGreen3(String group, String serviceId, String testUrl) {
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
                if (value.contains("discovery-guide-service-a")) {
                    if (value.contains("[V=1.1]")) {
                        aMatched = true;
                    }
                }
                if (value.contains("discovery-guide-service-b")) {
                    if (value.contains("[V=1.1]")) {
                        bMatched = true;
                    }
                }
            }

            Assert.assertEquals(aMatched && bMatched, true);
        }
    }

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-customization-gray-1.xml", resetPath = "gray-default.xml")
    public void testStrategyCustomizationGray1(String group, String serviceId, String testUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("a", "1");

        LOG.info("Header : {}", headers);

        int aV0Count = 0;
        int aV1Count = 0;
        int bV0Count = 0;
        int bV1Count = 0;

        int v0Weight = 95;
        int v1Weight = 5;

        LOG.info("Sample count={}", sampleCount);
        LOG.info("Weight result offset desired={}%", resultOffset);
        LOG.info("All services desired : 1.0 version weight={}%, 1.1 version weight={}%", v0Weight, v1Weight);
        LOG.info("Route desired : A Service 1.0 version -> B Service 1.0 version, A Service 1.1 version -> B Service 1.1 version");

        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        for (int i = 0; i < sampleCount; i++) {
            String result = testRestTemplate.exchange(testUrl, HttpMethod.GET, requestEntity, String.class, new HashMap<String, String>()).getBody();

            boolean aV0Matched = false;
            boolean bV0Matched = false;
            boolean aV1Matched = false;
            boolean bV1Matched = false;
            String[] array = result.split("->");
            for (String value : array) {
                if (value.contains("discovery-guide-service-a")) {
                    if (value.contains("[V=1.0]")) {
                        aV0Count++;
                        aV0Matched = true;
                    }
                    if (value.contains("[V=1.1]")) {
                        aV1Count++;
                        aV1Matched = true;
                    }
                }
                if (value.contains("discovery-guide-service-b")) {
                    if (value.contains("[V=1.0]")) {
                        bV0Count++;
                        bV0Matched = true;
                    }
                    if (value.contains("[V=1.1]")) {
                        bV1Count++;
                        bV1Matched = true;
                    }
                }
            }

            Assert.assertEquals((aV0Matched && bV0Matched) || (aV1Matched && bV1Matched), true);
        }

        DecimalFormat format = new DecimalFormat("0.0000");
        double aV0Reslut = Double.valueOf(format.format((double) aV0Count * 100 / sampleCount));
        double aV1Reslut = Double.valueOf(format.format((double) aV1Count * 100 / sampleCount));
        double bV0Reslut = Double.valueOf(format.format((double) bV0Count * 100 / sampleCount));
        double bV1Reslut = Double.valueOf(format.format((double) bV1Count * 100 / sampleCount));

        LOG.info("Result : A service 1.0 version weight={}%", aV0Reslut);
        LOG.info("Result : A service 1.1 version weight={}%", aV1Reslut);
        LOG.info("Result : B service 1.0 version weight={}%", bV0Reslut);
        LOG.info("Result : B service 1.1 version weight={}%", bV1Reslut);

        Assert.assertEquals(aV0Reslut > v0Weight - resultOffset && aV0Reslut < v0Weight + resultOffset, true);
        Assert.assertEquals(aV1Reslut > v1Weight - resultOffset && aV1Reslut < v1Weight + resultOffset, true);
        Assert.assertEquals(bV0Reslut > v0Weight - resultOffset && bV0Reslut < v0Weight + resultOffset, true);
        Assert.assertEquals(bV1Reslut > v1Weight - resultOffset && bV1Reslut < v1Weight + resultOffset, true);

        Assert.assertEquals(String.valueOf(aV0Reslut), String.valueOf(bV0Reslut));
        Assert.assertEquals(String.valueOf(aV1Reslut), String.valueOf(bV1Reslut));
    }

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-customization-gray-2.xml", resetPath = "gray-default.xml")
    public void testStrategyCustomizationGray2(String group, String serviceId, String testUrl) {
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            boolean aMatched = false;
            boolean bMatched = false;
            String[] array = result.split("->");
            for (String value : array) {
                if (value.contains("discovery-guide-service-a")) {
                    if (value.contains("[V=1.0]")) {
                        aMatched = true;
                    }
                }
                if (value.contains("discovery-guide-service-b")) {
                    if (value.contains("[V=1.0]")) {
                        bMatched = true;
                    }
                }
            }

            Assert.assertEquals(aMatched && bMatched, true);
        }
    }

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-customization-gray-2.xml", resetPath = "gray-default.xml")
    public void testStrategyCustomizationGray3(String group, String serviceId, String testUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("a", "1");

        LOG.info("Header : {}", headers);

        int aV0Count = 0;
        int aV1Count = 0;
        int bV0Count = 0;
        int bV1Count = 0;

        int v0Weight = 10;
        int v1Weight = 90;

        LOG.info("Sample count={}", sampleCount);
        LOG.info("Weight result offset desired={}%", resultOffset);
        LOG.info("All services desired : 1.0 version weight={}%, 1.1 version weight={}%", v0Weight, v1Weight);
        LOG.info("Route desired : A Service 1.0 version -> B Service 1.0 version, A Service 1.1 version -> B Service 1.1 version");

        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        for (int i = 0; i < sampleCount; i++) {
            String result = testRestTemplate.exchange(testUrl, HttpMethod.GET, requestEntity, String.class, new HashMap<String, String>()).getBody();

            boolean aV0Matched = false;
            boolean bV0Matched = false;
            boolean aV1Matched = false;
            boolean bV1Matched = false;
            String[] array = result.split("->");
            for (String value : array) {
                if (value.contains("discovery-guide-service-a")) {
                    if (value.contains("[V=1.0]")) {
                        aV0Count++;
                        aV0Matched = true;
                    }
                    if (value.contains("[V=1.1]")) {
                        aV1Count++;
                        aV1Matched = true;
                    }
                }
                if (value.contains("discovery-guide-service-b")) {
                    if (value.contains("[V=1.0]")) {
                        bV0Count++;
                        bV0Matched = true;
                    }
                    if (value.contains("[V=1.1]")) {
                        bV1Count++;
                        bV1Matched = true;
                    }
                }
            }

            Assert.assertEquals((aV0Matched && bV0Matched) || (aV1Matched && bV1Matched), true);
        }

        DecimalFormat format = new DecimalFormat("0.0000");
        double aV0Reslut = Double.valueOf(format.format((double) aV0Count * 100 / sampleCount));
        double aV1Reslut = Double.valueOf(format.format((double) aV1Count * 100 / sampleCount));
        double bV0Reslut = Double.valueOf(format.format((double) bV0Count * 100 / sampleCount));
        double bV1Reslut = Double.valueOf(format.format((double) bV1Count * 100 / sampleCount));

        LOG.info("Result : A service 1.0 version weight={}%", aV0Reslut);
        LOG.info("Result : A service 1.1 version weight={}%", aV1Reslut);
        LOG.info("Result : B service 1.0 version weight={}%", bV0Reslut);
        LOG.info("Result : B service 1.1 version weight={}%", bV1Reslut);

        Assert.assertEquals(aV0Reslut > v0Weight - resultOffset && aV0Reslut < v0Weight + resultOffset, true);
        Assert.assertEquals(aV1Reslut > v1Weight - resultOffset && aV1Reslut < v1Weight + resultOffset, true);
        Assert.assertEquals(bV0Reslut > v0Weight - resultOffset && bV0Reslut < v0Weight + resultOffset, true);
        Assert.assertEquals(bV1Reslut > v1Weight - resultOffset && bV1Reslut < v1Weight + resultOffset, true);

        Assert.assertEquals(String.valueOf(aV0Reslut), String.valueOf(bV0Reslut));
        Assert.assertEquals(String.valueOf(aV1Reslut), String.valueOf(bV1Reslut));
    }

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-customization-gray-2.xml", resetPath = "gray-default.xml")
    public void testStrategyCustomizationGray4(String group, String serviceId, String testUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("a", "1");
        headers.add("b", "2");

        LOG.info("Header : {}", headers);

        int aV0Count = 0;
        int aV1Count = 0;
        int bV0Count = 0;
        int bV1Count = 0;

        int v0Weight = 85;
        int v1Weight = 15;

        LOG.info("Sample count={}", sampleCount);
        LOG.info("Weight result offset desired={}%", resultOffset);
        LOG.info("All services desired : 1.0 version weight={}%, 1.1 version weight={}%", v0Weight, v1Weight);
        LOG.info("Route desired : A Service 1.0 version -> B Service 1.0 version, A Service 1.1 version -> B Service 1.1 version");

        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        for (int i = 0; i < sampleCount; i++) {
            String result = testRestTemplate.exchange(testUrl, HttpMethod.GET, requestEntity, String.class, new HashMap<String, String>()).getBody();

            boolean aV0Matched = false;
            boolean bV0Matched = false;
            boolean aV1Matched = false;
            boolean bV1Matched = false;
            String[] array = result.split("->");
            for (String value : array) {
                if (value.contains("discovery-guide-service-a")) {
                    if (value.contains("[V=1.0]")) {
                        aV0Count++;
                        aV0Matched = true;
                    }
                    if (value.contains("[V=1.1]")) {
                        aV1Count++;
                        aV1Matched = true;
                    }
                }
                if (value.contains("discovery-guide-service-b")) {
                    if (value.contains("[V=1.0]")) {
                        bV0Count++;
                        bV0Matched = true;
                    }
                    if (value.contains("[V=1.1]")) {
                        bV1Count++;
                        bV1Matched = true;
                    }
                }
            }

            Assert.assertEquals((aV0Matched && bV0Matched) || (aV1Matched && bV1Matched), true);
        }

        DecimalFormat format = new DecimalFormat("0.0000");
        double aV0Reslut = Double.valueOf(format.format((double) aV0Count * 100 / sampleCount));
        double aV1Reslut = Double.valueOf(format.format((double) aV1Count * 100 / sampleCount));
        double bV0Reslut = Double.valueOf(format.format((double) bV0Count * 100 / sampleCount));
        double bV1Reslut = Double.valueOf(format.format((double) bV1Count * 100 / sampleCount));

        LOG.info("Result : A service 1.0 version weight={}%", aV0Reslut);
        LOG.info("Result : A service 1.1 version weight={}%", aV1Reslut);
        LOG.info("Result : B service 1.0 version weight={}%", bV0Reslut);
        LOG.info("Result : B service 1.1 version weight={}%", bV1Reslut);

        Assert.assertEquals(aV0Reslut > v0Weight - resultOffset && aV0Reslut < v0Weight + resultOffset, true);
        Assert.assertEquals(aV1Reslut > v1Weight - resultOffset && aV1Reslut < v1Weight + resultOffset, true);
        Assert.assertEquals(bV0Reslut > v0Weight - resultOffset && bV0Reslut < v0Weight + resultOffset, true);
        Assert.assertEquals(bV1Reslut > v1Weight - resultOffset && bV1Reslut < v1Weight + resultOffset, true);

        Assert.assertEquals(String.valueOf(aV0Reslut), String.valueOf(bV0Reslut));
        Assert.assertEquals(String.valueOf(aV1Reslut), String.valueOf(bV1Reslut));
    }

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-rule-version.xml", resetPath = "gray-default.xml")
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
                if (value.contains("discovery-guide-service-a")) {
                    if (value.contains("[V=1.0]")) {
                        aV0Matched = true;
                    }
                    if (value.contains("[V=1.1]")) {
                        aV1Matched = true;
                    }
                }
                if (value.contains("discovery-guide-service-b")) {
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

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-rule-region.xml", resetPath = "gray-default.xml")
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
                if (value.contains("discovery-guide-service-a")) {
                    if (value.contains("[R=dev]")) {
                        aDevMatched = true;
                    }
                    if (value.contains("[R=qa]")) {
                        aQaMatched = true;
                    }
                }
                if (value.contains("discovery-guide-service-b")) {
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

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-rule-version-weight.xml", resetPath = "gray-default.xml")
    public void testVersionWeightRuleGray(String group, String serviceId, String testUrl) {
        int aV0Count = 0;
        int aV1Count = 0;
        int bV0Count = 0;
        int bV1Count = 0;

        int aV0Weight = 75;
        int aV1Weight = 25;
        int bV0Weight = 35;
        int bV1Weight = 65;

        LOG.info("Sample count={}", sampleCount);
        LOG.info("Weight result offset desired={}%", resultOffset);
        LOG.info("A service desired : 1.0 version weight={}%, 1.1 version weight={}%", aV0Weight, aV1Weight);
        LOG.info("B service desired : 1.0 version weight={}%, 1.1 version weight={}%", bV0Weight, bV1Weight);

        for (int i = 0; i < sampleCount; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            String[] array = result.split("->");
            for (String value : array) {
                if (value.contains("discovery-guide-service-a")) {
                    if (value.contains("[V=1.0]")) {
                        aV0Count++;
                    }
                    if (value.contains("[V=1.1]")) {
                        aV1Count++;
                    }
                }
                if (value.contains("discovery-guide-service-b")) {
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
        double aV0Reslut = Double.valueOf(format.format((double) aV0Count * 100 / sampleCount));
        double aV1Reslut = Double.valueOf(format.format((double) aV1Count * 100 / sampleCount));
        double bV0Reslut = Double.valueOf(format.format((double) bV0Count * 100 / sampleCount));
        double bV1Reslut = Double.valueOf(format.format((double) bV1Count * 100 / sampleCount));

        LOG.info("Result : A service 1.0 version weight={}%", aV0Reslut);
        LOG.info("Result : A service 1.1 version weight={}%", aV1Reslut);
        LOG.info("Result : B service 1.0 version weight={}%", bV0Reslut);
        LOG.info("Result : B service 1.1 version weight={}%", bV1Reslut);

        Assert.assertEquals(aV0Reslut > aV0Weight - resultOffset && aV0Reslut < aV0Weight + resultOffset, true);
        Assert.assertEquals(aV1Reslut > aV1Weight - resultOffset && aV1Reslut < aV1Weight + resultOffset, true);
        Assert.assertEquals(bV0Reslut > bV0Weight - resultOffset && bV0Reslut < bV0Weight + resultOffset, true);
        Assert.assertEquals(bV1Reslut > bV1Weight - resultOffset && bV1Reslut < bV1Weight + resultOffset, true);
    }

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-rule-region-weight.xml", resetPath = "gray-default.xml")
    public void testRegionWeightRuleGray(String group, String serviceId, String testUrl) {
        int aDevCount = 0;
        int aQaCount = 0;
        int bDevCount = 0;
        int bQaCount = 0;

        int aDevWeight = 95;
        int aQaWeight = 5;
        int bDevWeight = 95;
        int bQaWeight = 5;

        LOG.info("Sample count={}", sampleCount);
        LOG.info("Weight result offset desired={}%", resultOffset);
        LOG.info("A service desired : dev region weight={}%, qa region weight={}%", aDevWeight, aQaWeight);
        LOG.info("B service desired : dev region weight={}%, qa region weight={}%", bDevWeight, bQaWeight);

        for (int i = 0; i < sampleCount; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            String[] array = result.split("->");
            for (String value : array) {
                if (value.contains("discovery-guide-service-a")) {
                    if (value.contains("[R=dev]")) {
                        aDevCount++;
                    }
                    if (value.contains("[R=qa]")) {
                        aQaCount++;
                    }
                }
                if (value.contains("discovery-guide-service-b")) {
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
        double aDevReslut = Double.valueOf(format.format((double) aDevCount * 100 / sampleCount));
        double aQaReslut = Double.valueOf(format.format((double) aQaCount * 100 / sampleCount));
        double bDevReslut = Double.valueOf(format.format((double) bDevCount * 100 / sampleCount));
        double bQaReslut = Double.valueOf(format.format((double) bQaCount * 100 / sampleCount));

        LOG.info("Result : A service dev region weight={}%", aDevReslut);
        LOG.info("Result : A service qa region weight={}%", aQaReslut);
        LOG.info("Result : B service dev region weight={}%", bDevReslut);
        LOG.info("Result : B service qa region weight={}%", bQaReslut);

        Assert.assertEquals(aDevReslut > aDevWeight - resultOffset && aDevReslut < aDevWeight + resultOffset, true);
        Assert.assertEquals(aQaReslut > aQaWeight - resultOffset && aQaReslut < aQaWeight + resultOffset, true);
        Assert.assertEquals(bDevReslut > bDevWeight - resultOffset && bDevReslut < bDevWeight + resultOffset, true);
        Assert.assertEquals(bQaReslut > bQaWeight - resultOffset && bQaReslut < bQaWeight + resultOffset, true);
    }

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-rule-version-composite.xml", resetPath = "gray-default.xml")
    public void testVersionCompositeRuleGray(String group, String serviceId, String testUrl) {
        int aV0Count = 0;
        int aV1Count = 0;

        int aV0Weight = 40;
        int aV1Weight = 60;

        LOG.info("Sample count={}", sampleCount);
        LOG.info("Weight result offset desired={}%", resultOffset);
        LOG.info("A service desired : 1.0 version weight={}%, 1.1 version weight={}%", aV0Weight, aV1Weight);
        LOG.info("Route desired : A Service 1.0 version -> B Service 1.0 version, A Service 1.1 version -> B Service 1.1 version");

        for (int i = 0; i < sampleCount; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            boolean aV0Matched = false;
            boolean bV0Matched = false;
            boolean aV1Matched = false;
            boolean bV1Matched = false;
            String[] array = result.split("->");
            for (String value : array) {
                if (value.contains("discovery-guide-service-a")) {
                    if (value.contains("[V=1.0]")) {
                        aV0Count++;
                        aV0Matched = true;
                    }
                    if (value.contains("[V=1.1]")) {
                        aV1Count++;
                        aV1Matched = true;
                    }
                }
                if (value.contains("discovery-guide-service-b")) {
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
        double aV0Reslut = Double.valueOf(format.format((double) aV0Count * 100 / sampleCount));
        double aV1Reslut = Double.valueOf(format.format((double) aV1Count * 100 / sampleCount));
        LOG.info("Result : A service 1.0 version weight={}%", aV0Reslut);
        LOG.info("Result : A service 1.1 version weight={}%", aV1Reslut);

        Assert.assertEquals(aV0Reslut > aV0Weight - resultOffset && aV0Reslut < aV0Weight + resultOffset, true);
        Assert.assertEquals(aV1Reslut > aV1Weight - resultOffset && aV1Reslut < aV1Weight + resultOffset, true);
    }
}