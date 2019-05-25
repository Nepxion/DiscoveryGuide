# Nepxion Discovery Gray
[![Total lines](https://tokei.rs/b1/github/Nepxion/DiscoveryGray?category=lines)](https://github.com/Nepxion/DiscoveryGray)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?label=license)](https://github.com/Nepxion/DiscoveryGray/blob/master/LICENSE)
[![Build Status](https://travis-ci.org/Nepxion/DiscoveryGray.svg?branch=master)](https://travis-ci.org/Nepxion/DiscoveryGray)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/8e39a24e1be740c58b83fb81763ba317)](https://www.codacy.com/project/HaojunRen/DiscoveryGray/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Nepxion/DiscoveryGray&amp;utm_campaign=Badge_Grade_Dashboard)

Nepxion Discovery Gray是Nepxion Discovery的极简示例，有助于使用者快速入门，它采用配置中心配置路由规则映射在网关过滤器中植入Header信息而实现，当然也支持从界面传入Header信息，主要包括版本路由和区域路由两种。实例以Nacos为服务注册中心和配置中心，通过Gateway和Zull调用两个版本或者区域的服务，模拟灰度发布和路由功能。如果使用者需要更强大的功能，请参考[https://github.com/Nepxion/Discovery](https://github.com/Nepxion/Discovery)

## 环境搭建
- 下载代码并导入IDE
- 启动四个实例服务和两个网关服务，如下： 

| 类名 | 微服务 | 服务端口 | 版本 | 区域 |
| --- | --- | --- | --- | --- |
| DiscoveryGrayServiceA1.java | A1 | 3001 | 1.0 | dev |
| DiscoveryGrayServiceA2.java | A2 | 3002 | 1.1 | qa |
| DiscoveryGrayServiceB1.java | B1 | 4001 | 1.0 | dev |
| DiscoveryGrayServiceB2.java | B2 | 4002 | 1.1 | qa |
| DiscoveryGrayGateway.java | Gateway | 5001 | 1.0 | 无 |
| DiscoveryGrayZuul.java | Zuul | 5002 | 1.0 | 无 |

## 验证无灰度发布和路由调用
- 验证无灰度发布和路由下的调用

1.在浏览器中执行[http://localhost:5001/discovery-gray-service-a/invoke/gateway](http://localhost:5001/discovery-gray-service-a/invoke/gateway)，测试没有灰度路由的情况下，通过Spring Cloud Gateway网关的调用结果，打印出全路径结果，例如：
```xml
gateway -> discovery-gray-service-a[192.168.0.107:3001][V1.0][Region=dev] -> discovery-gray-service-b[192.168.0.107:4001][V1.0][Region=qa]
```

2.在浏览器中执行[http://localhost:5002/discovery-gray-service-a/invoke/zuul](http://localhost:5002/discovery-gray-service-a/invoke/zuul)，测试没有灰度路由的情况下，通过Zuul网关的调用结果，打印出全路径结果，例如：
```xml
zuul -> discovery-gray-service-a[192.168.0.107:3001][V1.0][Region=dev] -> discovery-gray-service-b[192.168.0.107:4001][V1.0][Region=qa]
```

## 配置灰度发布和路由规则
- 在Nacos配置中心，增加灰度规则

1.增加Zuul的灰度规则，Group为discovery-gray-group，Data Id为discovery-gray-zuul，内容如下：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <strategy>
        <region>dev</region>
    </strategy>
</rule>
```
注意：
```xml
<region>dev</region>
```
等效于
```xml
<region>{"discovery-gray-service-a":"dev", "discovery-gray-service-b":"dev"}</region>
```
上述配置，将实现从Zuul发起的调用都走区域为dev的服务

2.增加Spring Cloud Gateway的灰度规则，Group为discovery-gray-group，Data Id为discovery-gray-gateway，内容如下：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <strategy>
        <version>1.0</version>
    </strategy>
</rule>
```
注意：
```xml
<version>1.0</version>
```
等效于
```xml
<version>{"discovery-gray-service-a":"1.0", "discovery-gray-service-b":"1.0"}</version>
```
上述配置，将实现从Spring Cloud Gateway发起的调用都走版本为1.0的服务

## 验证灰度发布和路由调用
- 重复上述浏览器的调用，验证存在灰度发布和路由下的调用。观察输出的版本号和区域值是否匹配灰度发布和路由规则

## 通过前端传入灰度发布和路由规则
- 通过前端（Postman）方式传入灰度路由规则。注意：当配置中心和界面都配置后，以界面传入优先

区域规则，Header格式如下任选一个：
```xml
n-d-region=dev
n-d-region={"discovery-gray-service-a":"dev", "discovery-gray-service-b":"dev"}
```
版本规则，Header格式如下任选一个：
```xml
n-d-version=1.0
n-d-version={"discovery-gray-service-a":"1.0", "discovery-gray-service-b":"1.0"}
```  
