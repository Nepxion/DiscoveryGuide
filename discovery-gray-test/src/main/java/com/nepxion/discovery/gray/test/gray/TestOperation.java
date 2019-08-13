package com.nepxion.discovery.gray.test.gray;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.property.DiscoveryContent;
import com.nepxion.discovery.common.util.UrlUtil;
import com.nepxion.discovery.gray.test.constant.TestConstant;

public class TestOperation {
    public static final String REMOTE_UPDATE_URL = "console/remote-config/update";
    public static final String UPDATE_URL = "console/config/update-sync";

    @Value("${" + TestConstant.SPRING_APPLICATION_TEST_GRAY_TO_CONFIGCENTER + ":true}")
    private Boolean toConfigCenter;

    @Value("${" + TestConstant.SPRING_APPLICATION_TEST_CONSOLE_URL + "}")
    private String consoleUrl;

    @Autowired
    private TestRestTemplate testRestTemplate;

    public String update(String group, String serviceId, String path) {
        String content = null;
        try {
            DiscoveryContent discoveryContent = new DiscoveryContent(path, DiscoveryConstant.ENCODING_UTF_8);
            content = discoveryContent.getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return execute(group, serviceId, content);
    }

    public String clear(String group, String serviceId) {
        String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
                "<rule>\r\n" +
                "\r\n" +
                "</rule>";

        return execute(group, serviceId, content);
    }

    private String execute(String group, String serviceId, String content) {
        String url = toConfigCenter ? consoleUrl + UrlUtil.formatContextPath(REMOTE_UPDATE_URL) + group + "/" + serviceId : consoleUrl + UrlUtil.formatContextPath(UPDATE_URL) + serviceId;

        return testRestTemplate.postForEntity(url, content, String.class).getBody();
    }
}