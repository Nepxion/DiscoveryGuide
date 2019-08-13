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

public class DiscoveryGrayTestCases {
    @Autowired
    private TestRestTemplate restTemplate;

    public void testNoGrayInvoke(String type, String url) {
        System.out.println("----- Test NoGray Invoke for " + type + " started -----");
        int noRepeatCount = 0;
        List<String> resultList = new ArrayList<String>();
        for (int i = 0; i < 4; i++) {
            String result = restTemplate.getForEntity(url, String.class).getBody();
            System.out.println("Output" + (i + 1) + " : " + result);
            if (!resultList.contains(result)) {
                noRepeatCount++;
            }
            resultList.add(result);
        }

        Assert.assertEquals(noRepeatCount, 4);
        System.out.println("* Test Passed");
        
        System.out.println("----- Test NoGray Invoke for " + type + " finished ----");
    }
}