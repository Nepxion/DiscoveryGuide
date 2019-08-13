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

import com.nepxion.discovery.gray.test.base.AbstractTestCase;
import com.nepxion.discovery.gray.test.gray.AbstractGrayTestCase;
import com.nepxion.discovery.gray.test.gray.GrayTestOperation;

public class MyTestCases {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private GrayTestOperation grayTestOperation;

    public void testNoGray(String url, String testUrl) {
        new AbstractTestCase() {
            @Override
            public void runTest() {
                int noRepeatCount = 0;
                List<String> resultList = new ArrayList<String>();
                for (int i = 0; i < 4; i++) {
                    String result = testRestTemplate.getForEntity(url + testUrl, String.class).getBody();

                    System.out.println("Result" + (i + 1) + " : " + result);

                    if (!resultList.contains(result)) {
                        noRepeatCount++;
                    }
                    resultList.add(result);
                }

                Assert.assertEquals(noRepeatCount, 4);
            }

            @Override
            public String getTestMethod() {
                return "testNoGray";
            }
        }.run();
    }

    public void testVersionGray(String url, String testUrl) {
        new AbstractGrayTestCase(grayTestOperation, url, "test-config-version-1.xml") {
            @Override
            public void runTest() {
                for (int i = 0; i < 4; i++) {
                    String result = testRestTemplate.getForEntity(url + testUrl, String.class).getBody();

                    System.out.println("Result" + (i + 1) + " : " + result);

                    int index = result.indexOf("[V1.0]");
                    int lastIndex = result.lastIndexOf("[V1.0]");

                    Assert.assertNotEquals(index, -1);
                    Assert.assertNotEquals(lastIndex, -1);
                    Assert.assertNotEquals(index, lastIndex);
                }
            }

            @Override
            public String getTestMethod() {
                return "testVersionGray";
            }
        }.run();
    }

    public void testRegionGray(String url, String testUrl) {
        new AbstractGrayTestCase(grayTestOperation, url, "test-config-region-1.xml") {
            @Override
            public void runTest() {
                for (int i = 0; i < 4; i++) {
                    String result = testRestTemplate.getForEntity(url + testUrl, String.class).getBody();

                    System.out.println("Result" + (i + 1) + " : " + result);

                    int index = result.indexOf("[Region=dev]");
                    int lastIndex = result.lastIndexOf("[Region=dev]");

                    Assert.assertNotEquals(index, -1);
                    Assert.assertNotEquals(lastIndex, -1);
                    Assert.assertNotEquals(index, lastIndex);
                }
            }

            @Override
            public String getTestMethod() {
                return "testRegionGray";
            }
        }.run();
    }
}