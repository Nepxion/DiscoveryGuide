package com.nepxion.discovery.guide.service.permission;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;
import com.nepxion.matrix.proxy.aop.AbstractInterceptor;

public class PermissionInterceptor extends AbstractInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(PermissionInterceptor.class);

    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private StrategyContextHolder strategyContextHolder;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Permission permissionAnnotation = getPermissionAnnotation(invocation);
        if (permissionAnnotation != null) {
            return invokePermission(invocation, permissionAnnotation);
        }

        return invocation.proceed();
    }

    private Permission getPermissionAnnotation(MethodInvocation invocation) {
        Class<?> proxiedClass = getProxiedClass(invocation);
        if (proxiedClass.isAnnotationPresent(Permission.class)) {
            return proxiedClass.getAnnotation(Permission.class);
        }

        Method method = invocation.getMethod();
        if (method.isAnnotationPresent(Permission.class)) {
            return method.getAnnotation(Permission.class);
        }

        return null;
    }

    private Object invokePermission(MethodInvocation invocation, Permission permissionAnnotation) throws Throwable {
        String name = permissionAnnotation.name();
        if (StringUtils.isEmpty(name)) {
            throw new PermissionException("Annotation [Permission]'s name is null or empty");
        }
        String label = permissionAnnotation.label();
        String description = permissionAnnotation.description();

        String group = pluginAdapter.getGroup();
        String serviceId = pluginAdapter.getServiceId();
        String serviceType = pluginAdapter.getServiceType();

        String proxyType = getProxyType(invocation);
        String proxiedClassName = getProxiedClassName(invocation);
        String methodName = getMethodName(invocation);
        String parameterTypesValue = getMethodParameterTypesValue(invocation);

        String user = strategyContextHolder.getHeader("user");

        LOG.info("权限拦截：Service [group={}, serviceId={}, serviceType={}, user={}]", group, serviceId, serviceType, user);
        LOG.info("权限拦截：Permission [name={}, label={}, description={}, proxyType={}, proxiedClass={}, method={}, parameterTypesValue={}]", name, label, description, proxyType, proxiedClassName, methodName, parameterTypesValue);

        // 跟数据库中的权限数据比对，如果判断无权限，可以抛出异常
        // throw new PermissionException("No permission to proceed method [name=" + methodName + ", parameterTypes=" + parameterTypesValue + "], permissionName=" + name + ", permissionLabel=" + label);

        return invocation.proceed();
    }
}