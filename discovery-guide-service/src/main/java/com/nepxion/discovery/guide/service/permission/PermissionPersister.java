package com.nepxion.discovery.guide.service.permission;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;

public class PermissionPersister implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(PermissionPersister.class);

    @Autowired
    private PermissionAutoScanProxy permissionAutoScanProxy;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() instanceof AnnotationConfigApplicationContext) {
            LOG.info("Start to persist with following permission list...");
            LOG.info("------------------------------------------------------------");
            List<String> permissions = permissionAutoScanProxy.getPermissions();
            LOG.info("Permission={}", permissions);
            // 权限数据入库
            LOG.info("------------------------------------------------------------");
        }
    }
}