package com.nepxion.discovery.gray.test.config;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

public class ConfigOperation {
    public static final String ENCODING_UTF_8 = "UTF-8";
    public static final String CONFIG_UPDATE_URL = "config/update-sync";
    public static final String TEST_RULE_EMPTY_PATH = "test-config-empty.xml";

    @Autowired
    private TestRestTemplate restTemplate;

    public String update(String url, String configPath) {
        String content = null;
        try {
            ConfigContent configContent = new ConfigContent(configPath, ENCODING_UTF_8);
            content = configContent.getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return restTemplate.postForEntity(url + CONFIG_UPDATE_URL, content, String.class).getBody();
    }

    public String reset(String url) {
        return update(url, TEST_RULE_EMPTY_PATH);
    }
}