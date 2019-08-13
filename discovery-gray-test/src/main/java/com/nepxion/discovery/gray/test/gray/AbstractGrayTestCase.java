package com.nepxion.discovery.gray.test.gray;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.gray.test.base.AbstractTestCase;

public abstract class AbstractGrayTestCase extends AbstractTestCase {
    private GrayTestOperation grayTestOperation;
    private String url;
    private String configPath;

    public AbstractGrayTestCase(GrayTestOperation grayTestOperation, String url, String configPath) {
        this.grayTestOperation = grayTestOperation;
        this.url = url;
        this.configPath = configPath;
    }

    @Override
    public void beforeTest() {
        super.beforeTest();

        grayTestOperation.update(url, configPath);
    }

    @Override
    public void afterTest() {
        grayTestOperation.clear(url);

        super.afterTest();
    }
}