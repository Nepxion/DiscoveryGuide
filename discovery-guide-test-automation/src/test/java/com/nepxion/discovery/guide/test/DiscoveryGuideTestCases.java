package com.nepxion.discovery.guide.test;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.nepxion.discovery.common.entity.InspectorEntity;
import com.nepxion.discovery.plugin.test.automation.annotation.DTest;
import com.nepxion.discovery.plugin.test.automation.annotation.DTestConfig;

public class DiscoveryGuideTestCases {
    private static final Logger LOG = LoggerFactory.getLogger(DiscoveryGuideTestCases.class);

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Value("${gray.weight.testcase.sample.count:3000}")
    private Integer sampleCount;

    @Value("${gray.weight.testcase.result.offset:5}")
    private Integer resultOffset;

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-default.xml", resetPath = "gray-default.xml")
    public void testNoGray(String group, String serviceId, String testUrl) {
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

    @DTest
    public void testVersionRouteFilter1(String testUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("user", "zhangsan");

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

    @DTest
    public void testVersionRouteFilter2(String testUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("user", "lisi");

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
                    if (value.contains("[V=1.0]")) {
                        bMatched = true;
                    }
                }
            }

            Assert.assertEquals(aMatched && bMatched, true);
        }
    }

    @DTest
    public void testRegionRouteFilter1(String testUrl) {
        testUrl += "?user=zhangsan";

        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            boolean aMatched = false;
            boolean bMatched = false;
            String[] array = result.split("->");
            for (String value : array) {
                if (value.contains("discovery-guide-service-a")) {
                    if (value.contains("[R=dev]")) {
                        aMatched = true;
                    }
                }
                if (value.contains("discovery-guide-service-b")) {
                    if (value.contains("[R=qa]")) {
                        bMatched = true;
                    }
                }
            }

            Assert.assertEquals(aMatched && bMatched, true);
        }
    }

    @DTest
    public void testRegionRouteFilter2(String testUrl) {
        testUrl += "?user=lisi";

        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            boolean aMatched = false;
            boolean bMatched = false;
            String[] array = result.split("->");
            for (String value : array) {
                if (value.contains("discovery-guide-service-a")) {
                    if (value.contains("[R=qa]")) {
                        aMatched = true;
                    }
                }
                if (value.contains("discovery-guide-service-b")) {
                    if (value.contains("[R=dev]")) {
                        bMatched = true;
                    }
                }
            }

            Assert.assertEquals(aMatched && bMatched, true);
        }
    }

    @DTest
    public void testAddressRouteFilter1(String testUrl) {
        List<String> cookieList = new ArrayList<String>();
        cookieList.add("user=zhangsan");
        cookieList.add("Path=/");
        cookieList.add("Domain=nepxion");
        cookieList.add("Expires=Fri, 07 Oct 2050 15:00:00 GMT");

        HttpHeaders headers = new HttpHeaders();
        headers.put("Cookie", cookieList);

        LOG.info("Header : {}", headers);

        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.exchange(testUrl, HttpMethod.GET, requestEntity, String.class, new HashMap<String, String>()).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            int index = result.indexOf(":3001");
            int lastIndex = result.lastIndexOf(":4002");

            Assert.assertNotEquals(index, -1);
            Assert.assertNotEquals(lastIndex, -1);
            Assert.assertNotEquals(index, lastIndex);
        }
    }

    @DTest
    public void testAddressRouteFilter2(String testUrl) {
        List<String> cookieList = new ArrayList<String>();
        cookieList.add("user=lisi");
        cookieList.add("Path=/");
        cookieList.add("Domain=nepxion");
        cookieList.add("Expires=Fri, 07 Oct 2050 15:00:00 GMT");

        HttpHeaders headers = new HttpHeaders();
        headers.put("Cookie", cookieList);

        LOG.info("Header : {}", headers);

        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.exchange(testUrl, HttpMethod.GET, requestEntity, String.class, new HashMap<String, String>()).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            int index = result.indexOf(":3002");
            int lastIndex = result.lastIndexOf(":4001");

            Assert.assertNotEquals(index, -1);
            Assert.assertNotEquals(lastIndex, -1);
            Assert.assertNotEquals(index, lastIndex);
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

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-blue-green.xml", resetPath = "gray-default.xml")
    public void testStrategyBlueGreen1(String group, String serviceId, String testUrl) {
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

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-blue-green.xml", resetPath = "gray-default.xml")
    public void testStrategyBlueGreen2(String group, String serviceId, String testUrl) {
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

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-blue-green.xml", resetPath = "gray-default.xml")
    public void testStrategyBlueGreen3(String group, String serviceId, String testUrl) {
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

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-blue-green-header-1.xml", resetPath = "gray-default.xml")
    public void testStrategyBlueGreenHeader1(String group, String serviceId, String testUrl) {
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
                    if (value.contains("[V=1.1]")) {
                        bMatched = true;
                    }
                }
            }

            Assert.assertEquals(aMatched && bMatched, true);
        }
    }

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-blue-green-header-2.xml", resetPath = "gray-default.xml")
    public void testStrategyBlueGreenHeader2(String group, String serviceId, String testUrl) {
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

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

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-blue-green-header-3.xml", resetPath = "gray-default.xml")
    public void testStrategyBlueGreenHeader3(String group, String serviceId, String testUrl) {
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

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-blue-green-header-parameter-cookie.xml", resetPath = "gray-default.xml")
    public void testStrategyBlueGreenHeaderParameterCookie(String group, String serviceId, String testUrl) {
        // 条件参数a来自于Http Query Parameter
        testUrl += "?a=1";

        // 条件参数b来自于Http Cookie
        List<String> cookieList = new ArrayList<String>();
        cookieList.add("b=2");
        cookieList.add("Path=/");
        cookieList.add("Domain=nepxion");
        cookieList.add("Expires=Fri, 07 Oct 2050 15:00:00 GMT");

        // 条件参数c来自于Http Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("c", "3");
        headers.put("Cookie", cookieList);

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

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-blue-green-header-1.xml", resetPath = "gray-default.xml")
    public void testInspectStrategyBlueGreenHeader1(String group, String serviceId, String testUrl) {
        InspectorEntity inspectorEntity = new InspectorEntity();
        inspectorEntity.setServiceIdList(Arrays.asList("discovery-guide-service-b"));
        for (int i = 0; i < 4; i++) {
            InspectorEntity resultInspectorEntity = testRestTemplate.postForEntity(testUrl, inspectorEntity, InspectorEntity.class).getBody();
            String result = resultInspectorEntity.getResult();

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

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-blue-green-header-2.xml", resetPath = "gray-default.xml")
    public void testInspectStrategyBlueGreenHeader2(String group, String serviceId, String testUrl) {
        InspectorEntity inspectorEntity = new InspectorEntity();
        inspectorEntity.setServiceIdList(Arrays.asList("discovery-guide-service-b"));
        for (int i = 0; i < 4; i++) {
            InspectorEntity resultInspectorEntity = testRestTemplate.postForEntity(testUrl, inspectorEntity, InspectorEntity.class).getBody();
            String result = resultInspectorEntity.getResult();

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

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-blue-green-header-3.xml", resetPath = "gray-default.xml")
    public void testInspectStrategyBlueGreenHeader3(String group, String serviceId, String testUrl) {
        InspectorEntity inspectorEntity = new InspectorEntity();
        inspectorEntity.setServiceIdList(Arrays.asList("discovery-guide-service-b"));
        for (int i = 0; i < 4; i++) {
            InspectorEntity resultInspectorEntity = testRestTemplate.postForEntity(testUrl, inspectorEntity, InspectorEntity.class).getBody();
            String result = resultInspectorEntity.getResult();

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

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-gray-1.xml", resetPath = "gray-default.xml")
    public void testStrategyGray1(String group, String serviceId, String testUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("a", "3");

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

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-gray-2.xml", resetPath = "gray-default.xml")
    public void testStrategyGray2(String group, String serviceId, String testUrl) {
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

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-gray-2.xml", resetPath = "gray-default.xml")
    public void testStrategyGray3(String group, String serviceId, String testUrl) {
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

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-gray-2.xml", resetPath = "gray-default.xml")
    public void testStrategyGray4(String group, String serviceId, String testUrl) {
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

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-blacklist.xml", resetPath = "gray-default.xml")
    public void testBlacklist1(String group, String serviceId, String testUrl) {
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            int index = result.indexOf(":3002");
            int lastIndex = result.lastIndexOf(":4002");

            Assert.assertNotEquals(index, -1);
            Assert.assertNotEquals(lastIndex, -1);
            Assert.assertNotEquals(index, lastIndex);
        }
    }

    @DTest
    public void testBlacklist2(String testUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("n-d-address-blacklist", "3001;4001");

        LOG.info("Header : {}", headers);

        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.exchange(testUrl, HttpMethod.GET, requestEntity, String.class, new HashMap<String, String>()).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            int index = result.indexOf(":3002");
            int lastIndex = result.lastIndexOf(":4002");

            Assert.assertNotEquals(index, -1);
            Assert.assertNotEquals(lastIndex, -1);
            Assert.assertNotEquals(index, lastIndex);
        }
    }

    @DTest
    public void testBlacklist3(String testUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("n-d-address-blacklist", "3*2;4??1");

        LOG.info("Header : {}", headers);

        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.exchange(testUrl, HttpMethod.GET, requestEntity, String.class, new HashMap<String, String>()).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            int index = result.indexOf(":3001");
            int lastIndex = result.lastIndexOf(":4002");

            Assert.assertNotEquals(index, -1);
            Assert.assertNotEquals(lastIndex, -1);
            Assert.assertNotEquals(index, lastIndex);
        }
    }

    @DTest
    public void testEnv(String testUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("n-d-env", "env1");

        LOG.info("Header : {}", headers);

        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.exchange(testUrl, HttpMethod.GET, requestEntity, String.class, new HashMap<String, String>()).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            int index = result.indexOf("[E=env1]");
            int lastIndex = result.lastIndexOf("[E=env1]");

            Assert.assertNotEquals(index, -1);
            Assert.assertNotEquals(lastIndex, -1);
            Assert.assertNotEquals(index, lastIndex);
        }
    }

    @DTestConfig(group = "#group", serviceId = "discovery-guide-service-b-sentinel-authority", executePath = "sentinel-authority-1.json", resetPath = "sentinel-default.json")
    public void testSentinelAuthority1(String group, String testUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("location", "shanghai");

        LOG.info("Header : {}", headers);

        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        int count = 0;
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.exchange(testUrl, HttpMethod.GET, requestEntity, String.class, new HashMap<String, String>()).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            if (result.contains("AuthorityRule")) {
                count++;
            }
        }

        Assert.assertEquals(count, 2);
    }

    @DTestConfig(group = "#group", serviceId = "discovery-guide-service-b-sentinel-authority", executePath = "sentinel-authority-1.json", resetPath = "sentinel-default.json")
    public void testSentinelAuthority2(String group, String testUrl) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            if (result.contains("AuthorityRule")) {
                count++;
            }
        }

        Assert.assertEquals(count, 4);
    }

    @DTestConfig(group = "#group", serviceId = "discovery-guide-service-b-sentinel-authority", executePath = "sentinel-authority-2.json", resetPath = "sentinel-default.json")
    public void testSentinelAuthority3(String group, String testUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("location", "shanghai");

        LOG.info("Header : {}", headers);

        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        int count = 0;
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.exchange(testUrl, HttpMethod.GET, requestEntity, String.class, new HashMap<String, String>()).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            if (result.contains("AuthorityRule")) {
                count++;
            }
        }

        Assert.assertEquals(count, 4);
    }

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "#executePath")
    public void testDynamicRoute1(String group, String serviceId, String[] testUrls, String executePath) {
        String result1 = testRestTemplate.getForEntity(testUrls[0], String.class).getBody();

        LOG.info("Result1 : {}", result1);

        int index1 = result1.indexOf("][P=");

        Assert.assertNotEquals(index1, -1);

        String result2 = testRestTemplate.getForEntity(testUrls[1], String.class).getBody();

        LOG.info("Result2 : {}", result2);

        int index2 = result2.indexOf("][P=");

        Assert.assertNotEquals(index2, -1);

        String result3 = testRestTemplate.getForEntity(testUrls[2], String.class).getBody();

        LOG.info("Result3 : {}", result3);

        int index3 = result3.indexOf("][P=");

        Assert.assertEquals(index3, -1);
    }

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "#executePath", resetPath = "#resetPath")
    public void testDynamicRoute2(String group, String serviceId, String[] testUrls, String executePath, String resetPath) {
        String result1 = testRestTemplate.getForEntity(testUrls[0], String.class).getBody();

        LOG.info("Result1 : {}", result1);

        int index1 = result1.indexOf("][P=");

        Assert.assertEquals(index1, -1);

        String result2 = testRestTemplate.getForEntity(testUrls[1], String.class).getBody();

        LOG.info("Result2 : {}", result2);

        int index2 = result2.indexOf("][P=");

        Assert.assertEquals(index2, -1);

        String result3 = testRestTemplate.getForEntity(testUrls[2], String.class).getBody();

        LOG.info("Result3 : {}", result3);

        int index3 = result3.indexOf("][P=");

        Assert.assertNotEquals(index3, -1);
    }

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-version-4.xml", resetPath = "gray-default.xml")
    public void testNacosDynamicalMetadataUpdated(String group, String serviceId, String testUrl) {
        String nacosUrl = "http://localhost:8848/nacos/v1/ns/instance/metadata/batch?namespaceId=public";

        String instancesJson1 = "[" + "{\"ip\":\"" + getNacosLocalIP() + "\",\"port\": \"3001\",\"ephemeral\":\"true\",\"clusterName\":\"DEFAULT\"}" + "]";
        String metadataJson1 = "{\"version\":\"2.0\"}";
        String response1 = processNacosDynamicalMetadata(nacosUrl, "public", "DEFAULT_GROUP", "discovery-guide-service-a", instancesJson1, metadataJson1, HttpMethod.PUT);
        LOG.info("Nacos dynamical metadata processed Result : {}", response1);

        String instancesJson2 = "[" + "{\"ip\":\"" + getNacosLocalIP() + "\",\"port\": \"4001\",\"ephemeral\":\"true\",\"clusterName\":\"DEFAULT\"}" + "]";
        String metadataJson2 = "{\"version\":\"2.0\"}";
        String response2 = processNacosDynamicalMetadata(nacosUrl, "public", "DEFAULT_GROUP", "discovery-guide-service-b", instancesJson2, metadataJson2, HttpMethod.PUT);
        LOG.info("Nacos dynamical metadata processed Result : {}", response2);

        try {
            Thread.sleep(45000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            int index = result.indexOf("[V=2.0]");
            int lastIndex = result.lastIndexOf("[V=2.0]");

            Assert.assertNotEquals(index, -1);
            Assert.assertNotEquals(lastIndex, -1);
            Assert.assertNotEquals(index, lastIndex);
        }
    }

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-version-5.xml", resetPath = "gray-default.xml")
    public void testNacosDynamicalMetadataDeleted(String group, String serviceId, String testUrl) {
        String nacosUrl = "http://localhost:8848/nacos/v1/ns/instance/metadata/batch?namespaceId=public";

        String instancesJson1 = "[" + "{\"ip\":\"" + getNacosLocalIP() + "\",\"port\": \"3001\",\"ephemeral\":\"true\",\"clusterName\":\"DEFAULT\"}" + "]";
        String metadataJson1 = "{\"version\":\"1.0\"}";
        String response1 = processNacosDynamicalMetadata(nacosUrl, "public", "DEFAULT_GROUP", "discovery-guide-service-a", instancesJson1, metadataJson1, HttpMethod.DELETE);
        LOG.info("Nacos dynamical metadata processed Result : {}", response1);

        String instancesJson2 = "[" + "{\"ip\":\"" + getNacosLocalIP() + "\",\"port\": \"4001\",\"ephemeral\":\"true\",\"clusterName\":\"DEFAULT\"}" + "]";
        String metadataJson2 = "{\"version\":\"1.0\"}";
        String response2 = processNacosDynamicalMetadata(nacosUrl, "public", "DEFAULT_GROUP", "discovery-guide-service-b", instancesJson2, metadataJson2, HttpMethod.DELETE);
        LOG.info("Nacos dynamical metadata processed Result : {}", response2);

        try {
            Thread.sleep(45000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            int index = result.indexOf("[V=default]");
            int lastIndex = result.lastIndexOf("[V=default]");

            Assert.assertNotEquals(index, -1);
            Assert.assertNotEquals(lastIndex, -1);
            Assert.assertNotEquals(index, lastIndex);
        }
    }

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-version-1.xml", resetPath = "gray-default.xml")
    public void testNacosDynamicalMetadataRestored(String group, String serviceId, String testUrl) {
        String nacosUrl = "http://localhost:8848/nacos/v1/ns/instance/metadata/batch?namespaceId=public";

        String instancesJson1 = "[" + "{\"ip\":\"" + getNacosLocalIP() + "\",\"port\": \"3001\",\"ephemeral\":\"true\",\"clusterName\":\"DEFAULT\"}" + "]";
        String metadataJson1 = "{\"version\":\"1.0\"}";
        String response1 = processNacosDynamicalMetadata(nacosUrl, "public", "DEFAULT_GROUP", "discovery-guide-service-a", instancesJson1, metadataJson1, HttpMethod.PUT);
        LOG.info("Nacos dynamical metadata processed Result : {}", response1);

        String instancesJson2 = "[" + "{\"ip\":\"" + getNacosLocalIP() + "\",\"port\": \"4001\",\"ephemeral\":\"true\",\"clusterName\":\"DEFAULT\"}" + "]";
        String metadataJson2 = "{\"version\":\"1.0\"}";
        String response2 = processNacosDynamicalMetadata(nacosUrl, "public", "DEFAULT_GROUP", "discovery-guide-service-b", instancesJson2, metadataJson2, HttpMethod.PUT);
        LOG.info("Nacos dynamical metadata processed Result : {}", response2);

        try {
            Thread.sleep(45000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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

    public String processNacosDynamicalMetadata(String url, String namespace, String group, String serviceId, String instancesJson, String metadataJson, HttpMethod httpMethod) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.add("namespace", namespace);
        parameters.add("serviceName", group + "@@" + serviceId);
        parameters.add("instances", instancesJson);
        parameters.add("metadata", metadataJson);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(parameters, headers);

        return testRestTemplate.exchange(url, httpMethod, requestEntity, String.class).getBody();
    }

    private String getNacosLocalIP() {
        try {
            return System.getProperty("com.alibaba.nacos.client.naming.local.ip", InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            return null;
        }
    }
}