package com.nepxion.discovery.guide.service.permission;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.matrix.proxy.aop.DefaultAutoScanProxy;
import com.nepxion.matrix.proxy.mode.ProxyMode;
import com.nepxion.matrix.proxy.mode.ScanMode;
import com.nepxion.matrix.proxy.util.ProxyUtil;

public class PermissionAutoScanProxy extends DefaultAutoScanProxy {
    private static final long serialVersionUID = 1510424112586328165L;

    private String[] commonInterceptorNames;

    @SuppressWarnings("rawtypes")
    private Class[] annotations;

    private List<String> permissions = new ArrayList<String>();

    @Autowired
    private PluginAdapter pluginAdapter;

    public PermissionAutoScanProxy(String scanPackages) {
        super(scanPackages, ProxyMode.BY_CLASS_OR_METHOD_ANNOTATION, ScanMode.FOR_CLASS_OR_METHOD_ANNOTATION);
    }

    @Override
    protected String[] getCommonInterceptorNames() {
        if (commonInterceptorNames == null) {
            commonInterceptorNames = new String[] { "permissionInterceptor" };
        }

        return commonInterceptorNames;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Class<? extends Annotation>[] getClassAnnotations() {
        if (annotations == null) {
            annotations = new Class[] { Permission.class };
        }

        return annotations;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Class<? extends Annotation>[] getMethodAnnotations() {
        if (annotations == null) {
            annotations = new Class[] { Permission.class };
        }

        return annotations;
    }

    // 一旦指定的接口或者类名上的注解被扫描到，将会触发该方法
    @Override
    protected void classAnnotationScanned(Class<?> targetClass, Class<? extends Annotation> classAnnotation) {
        if (classAnnotation == Permission.class) {
            Permission permissionAnnotation = targetClass.getAnnotation(Permission.class);

            scanned(targetClass, null, permissionAnnotation);
        }
    }

    // 一旦指定的接口或者类的方法名上的注解被扫描到，将会触发该方法
    @Override
    protected void methodAnnotationScanned(Class<?> targetClass, Method method, Class<? extends Annotation> methodAnnotation) {
        if (methodAnnotation == Permission.class) {
            Permission permissionAnnotation = method.getAnnotation(Permission.class);

            scanned(targetClass, method, permissionAnnotation);
        }
    }

    private void scanned(Class<?> targetClass, Method method, Permission permissionAnnotation) {
        String name = permissionAnnotation.name();
        if (StringUtils.isEmpty(name)) {
            throw new PermissionException("Annotation [Permission]'s name is null or empty");
        }
        String label = permissionAnnotation.label();
        String description = permissionAnnotation.description();

        String group = pluginAdapter.getGroup();
        String serviceId = pluginAdapter.getServiceId();
        String serviceType = pluginAdapter.getServiceType();

        // 取类名、方法名和参数类型组合赋值
        String resource = null;
        if (method != null) {
            String className = targetClass.getName();
            String methodName = method.getName();
            Class<?>[] parameterTypes = method.getParameterTypes();
            String parameterTypesValue = ProxyUtil.toString(parameterTypes);
            resource = className + "." + methodName + "(" + parameterTypesValue + ")";
        } else {
            String className = targetClass.getName();
            resource = className;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("name=" + name + ", ");
        stringBuilder.append("label=" + label + ", ");
        stringBuilder.append("description=" + description + ", ");
        stringBuilder.append("group=" + group + ", ");
        stringBuilder.append("serviceId=" + serviceId + ", ");
        stringBuilder.append("serviceType=" + serviceType + ", ");
        stringBuilder.append("resource=" + resource);

        permissions.add(stringBuilder.toString());
    }

    public List<String> getPermissions() {
        return permissions;
    }
}