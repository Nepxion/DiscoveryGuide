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

import com.nepxion.discovery.gray.test.config.ConfigUpdateResolver;

public class DiscoveryGrayTestCases {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ConfigUpdateResolver configUpdateResolver;

    public void testNoGray(String type, String url, String testUrl) {
        System.out.println("---------- Test No Gray for " + type + " started ----------");

        int noRepeatCount = 0;
        List<String> resultList = new ArrayList<String>();
        for (int i = 0; i < 4; i++) {
            String result = restTemplate.getForEntity(url + testUrl, String.class).getBody();

            System.out.println("Output" + (i + 1) + " : " + result);

            if (!resultList.contains(result)) {
                noRepeatCount++;
            }
            resultList.add(result);
        }

        Assert.assertEquals(noRepeatCount, 4);

        System.out.println("* Passed");
        System.out.println("---------- Test No Gray for " + type + " finished ---------");
    }

    public void testVersionGray(String type, String url, String testUrl) {
        System.out.println("---------- Test Version Gray for " + type + " started ----------");

        configUpdateResolver.update(url, "test-config-version-1.xml");

        for (int i = 0; i < 4; i++) {
            String result = restTemplate.getForEntity(url + testUrl, String.class).getBody();

            System.out.println("Output" + (i + 1) + " : " + result);

            int index = result.indexOf("[V1.0]");
            int lastIndex = result.lastIndexOf("[V1.0]");

            Assert.assertNotEquals(index, -1);
            Assert.assertNotEquals(lastIndex, -1);
            Assert.assertNotEquals(index, lastIndex);
        }

        configUpdateResolver.reset(url);

        System.out.println("* Passed");

        System.out.println("---------- Test Version Gray for " + type + " finished ---------");
    }

    public void testRegionGray(String type, String url, String testUrl) {
        System.out.println("---------- Test Region Gray for " + type + " started ----------");

        configUpdateResolver.update(url, "test-config-region-1.xml");

        for (int i = 0; i < 4; i++) {
            String result = restTemplate.getForEntity(url + testUrl, String.class).getBody();

            System.out.println("Output" + (i + 1) + " : " + result);

            int index = result.indexOf("[Region=dev]");
            int lastIndex = result.lastIndexOf("[Region=dev]");

            Assert.assertNotEquals(index, -1);
            Assert.assertNotEquals(lastIndex, -1);
            Assert.assertNotEquals(index, lastIndex);
        }

        configUpdateResolver.reset(url);

        System.out.println("* Passed");

        System.out.println("---------- Test Region Gray for " + type + " finished ---------");
    }
}