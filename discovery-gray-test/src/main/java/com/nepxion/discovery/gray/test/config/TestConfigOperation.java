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

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.property.DiscoveryContent;

public class TestConfigOperation {
    public static final String CONFIG_UPDATE_URL = "config/update-sync";

    @Autowired
    private TestRestTemplate testRestTemplate;

    public String update(String url, String configPath) {
        String content = null;
        try {
            DiscoveryContent discoveryContent = new DiscoveryContent(configPath, DiscoveryConstant.ENCODING_UTF_8);
            content = discoveryContent.getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return testRestTemplate.postForEntity(url + CONFIG_UPDATE_URL, content, String.class).getBody();
    }

    public String reset(String url) {
        String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
                "<rule>\r\n" +
                "\r\n" +
                "</rule>";

        return testRestTemplate.postForEntity(url + CONFIG_UPDATE_URL, content, String.class).getBody();
    }
}