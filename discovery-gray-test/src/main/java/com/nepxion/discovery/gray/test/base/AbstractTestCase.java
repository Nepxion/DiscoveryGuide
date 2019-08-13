package com.nepxion.discovery.gray.test.base;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;

public abstract class AbstractTestCase implements TestCase {
    @Override
    public void run() {
        beforeTest();
        runTest();
        afterTest();
    }

    @Override
    public void beforeTest() {
        String testMethod = getTestMethod();
        String testType = getTestType();

        if (StringUtils.isNotEmpty(testType)) {
            System.out.println("---------- Run " + testMethod + " for " + testType + "----------");
        } else {
            System.out.println("---------- Run " + testMethod + " ----------");
        }
    }

    @Override
    public void afterTest() {
        System.out.println("* Passed");
    }

    @Override
    public String getTestType() {
        return null;
    }
}