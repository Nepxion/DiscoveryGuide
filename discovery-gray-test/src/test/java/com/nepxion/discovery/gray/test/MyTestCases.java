package com.nepxion.discovery.gray.test;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

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

    @DTestGray(group = "#group", serviceId = "#serviceId", path = "test-config-version-1.xml")
    public void testVersionGray1(String group, String serviceId, String testUrl) {
        testVersionGray(testUrl);
    }

    @DTestGray(group = "#group", serviceId = "#serviceId", path = "test-config-version-2.xml")
    public void testVersionGray2(String group, String serviceId, String testUrl) {
        testVersionGray(testUrl);
    }

    @DTestGray(group = "#group", serviceId = "#serviceId", path = "test-config-version-3.xml")
    public void testVersionGray3(String group, String serviceId, String testUrl) {
        testVersionGray(testUrl);
    }

    private void testVersionGray(String testUrl) {
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

    @DTestGray(group = "#group", serviceId = "#serviceId", path = "test-config-region-1.xml")
    public void testRegionGray1(String group, String serviceId, String testUrl) {
        testRegionGray(testUrl);
    }

    @DTestGray(group = "#group", serviceId = "#serviceId", path = "test-config-region-2.xml")
    public void testRegionGray2(String group, String serviceId, String testUrl) {
        testRegionGray(testUrl);
    }

    @DTestGray(group = "#group", serviceId = "#serviceId", path = "test-config-region-3.xml")
    public void testRegionGray3(String group, String serviceId, String testUrl) {
        testRegionGray(testUrl);
    }

    private void testRegionGray(String testUrl) {
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
}