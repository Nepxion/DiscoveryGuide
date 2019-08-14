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
import java.util.List;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.nepxion.discovery.plugin.test.automation.annotation.DTest;
import com.nepxion.discovery.plugin.test.automation.annotation.DTestGray;

public class MyTestCases {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @DTest
    public void testNoGray(String testUrl) {
        int noRepeatCount = 0;
        List<String> resultList = new ArrayList<String>();
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            System.out.println("Result" + (i + 1) + " : " + result);

            if (!resultList.contains(result)) {
                noRepeatCount++;
            }
            resultList.add(result);
        }

        Assert.assertEquals(noRepeatCount, 4);
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

            System.out.println("Result" + (i + 1) + " : " + result);

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

        int totolCount = 3000;
        int offset = 2;
        int aV0Weight = 90;
        int aV1Weight = 10;
        int bV0Weight = 20;
        int bV1Weight = 80;

        System.out.println("调用次数=" + totolCount + "，调用次数越大，随机权重越准确");
        System.out.println("A服务期望值 : 1.0版本随机权重=" + aV0Weight + "%, 1.1版本随机权重=" + aV1Weight + "%");
        System.out.println("B服务期望值 : 1.0版本随机权重=" + bV0Weight + "%, 1.1版本随机权重=" + bV1Weight + "%");
        System.out.println("随机权重允许偏离量=" + offset + "%");

        for (int i = 0; i < totolCount; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            String[] array = result.split("->");
            for (String value : array) {
                if (value.contains("discovery-gray-service-a")) {
                    if (value.contains("[V=1.0]")) {
                        aV0Count++;
                    } else {
                        aV1Count++;
                    }
                }
                if (value.contains("discovery-gray-service-b")) {
                    if (value.contains("[V=1.0]")) {
                        bV0Count++;
                    } else {
                        bV1Count++;
                    }
                }
            }
        }

        DecimalFormat format = new DecimalFormat("0.0000");
        double aV0Reslut = Double.valueOf(format.format((double) aV0Count * 100 / totolCount));
        double aV1Reslut = Double.valueOf(format.format((double) aV1Count * 100 / totolCount));
        double bV0Reslut = Double.valueOf(format.format((double) bV0Count * 100 / totolCount));
        double bV1Reslut = Double.valueOf(format.format((double) bV1Count * 100 / totolCount));

        System.out.println("A服务1.0版本服务随机权重=" + aV0Reslut + "%");
        System.out.println("A服务1.1版本服务随机权重=" + aV1Reslut + "%");
        System.out.println("B服务1.0版本服务随机权重=" + bV0Reslut + "%");
        System.out.println("B服务1.1版本服务随机权重=" + bV1Reslut + "%");

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

            System.out.println("Result" + (i + 1) + " : " + result);

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

        int totolCount = 3000;
        int offset = 2;
        int aDevWeight = 95;
        int aQaWeight = 5;
        int bDevWeight = 15;
        int bQaWeight = 85;

        System.out.println("调用次数=" + totolCount + "，调用次数越大，随机权重越准确");
        System.out.println("A服务期望值 : dev区域随机权重=" + aDevWeight + "%, qa区域随机权重=" + aQaWeight + "%");
        System.out.println("B服务期望值 : dev区域随机权重=" + bDevWeight + "%, qa区域随机权重=" + bQaWeight + "%");
        System.out.println("随机权重允许偏离量=" + offset + "%");

        for (int i = 0; i < totolCount; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            String[] array = result.split("->");
            for (String value : array) {
                if (value.contains("discovery-gray-service-a")) {
                    if (value.contains("[R=dev]")) {
                        aDevCount++;
                    } else {
                        aQaCount++;
                    }
                }
                if (value.contains("discovery-gray-service-b")) {
                    if (value.contains("[R=dev]")) {
                        bDevCount++;
                    } else {
                        bQaCount++;
                    }
                }
            }
        }

        DecimalFormat format = new DecimalFormat("0.0000");
        double aDevReslut = Double.valueOf(format.format((double) aDevCount * 100 / totolCount));
        double aQaReslut = Double.valueOf(format.format((double) aQaCount * 100 / totolCount));
        double bDevReslut = Double.valueOf(format.format((double) bDevCount * 100 / totolCount));
        double bQaReslut = Double.valueOf(format.format((double) bQaCount * 100 / totolCount));

        System.out.println("A服务dev区域服务随机权重=" + aDevReslut + "%");
        System.out.println("A服务qa区域服务随机权重=" + aQaReslut + "%");
        System.out.println("B服务dev区域服务随机权重=" + bDevReslut + "%");
        System.out.println("B服务qa区域服务随机权重=" + bQaReslut + "%");

        Assert.assertEquals(aDevReslut > aDevWeight - offset && aDevReslut < aDevWeight + offset, true);
        Assert.assertEquals(aQaReslut > aQaWeight - offset && aQaReslut < aQaWeight + offset, true);
        Assert.assertEquals(bDevReslut > bDevWeight - offset && bDevReslut < bDevWeight + offset, true);
        Assert.assertEquals(bQaReslut > bQaWeight - offset && bQaReslut < bQaWeight + offset, true);
    }
}