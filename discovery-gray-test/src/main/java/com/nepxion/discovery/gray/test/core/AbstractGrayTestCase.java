package com.nepxion.discovery.gray.test.core;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.gray.test.operation.TestOperation;

public abstract class AbstractGrayTestCase extends AbstractTestCase {
    private TestOperation testOperation;
    private String grayServiceUrl;
    private String grayConfigPath;

    public AbstractGrayTestCase(TestOperation testOperation, String grayServiceUrl, String grayConfigPath) {
        this.testOperation = testOperation;
        this.grayServiceUrl = grayServiceUrl;
        this.grayConfigPath = grayConfigPath;
    }

    @Override
    public void beforeTest() {
        super.beforeTest();

        testOperation.update(grayServiceUrl, grayConfigPath);
    }

    @Override
    public void afterTest() {
        testOperation.reset(grayServiceUrl);

        super.afterTest();
    }
}