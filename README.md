# Nepxion Discovery Gray
[![Total lines](https://tokei.rs/b1/github/Nepxion/DiscoveryGray?category=lines)](https://github.com/Nepxion/DiscoveryGray)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?label=license)](https://github.com/Nepxion/DiscoveryGray/blob/master/LICENSE)
[![Build Status](https://travis-ci.org/Nepxion/DiscoveryGray.svg?branch=master)](https://travis-ci.org/Nepxion/DiscoveryGray)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/8e39a24e1be740c58b83fb81763ba317)](https://www.codacy.com/project/HaojunRen/DiscoveryGray/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Nepxion/DiscoveryGray&amp;utm_campaign=Badge_Grade_Dashboard)

Nepxion Discovery Gray是Nepxion Discovery的极简示例，有助于使用者快速入门，它采用配置中心配置路由规则映射在网关过滤器中植入Header信息而实现，当然也支持从界面传入Header信息，主要包括版本路由和区域路由两种。实例以Nacos为服务注册中心和配置中心，通过Gateway和Zull调用两个版本或者区域的服务，模拟灰度发布和路由功能。如果使用者需要更强大的功能，请参考[https://github.com/Nepxion/Discovery](https://github.com/Nepxion/Discovery)

## 环境搭建和运行
- 下载代码并导入IDE
- 启动Nacos服务器
  - 从[https://github.com/alibaba/nacos/releases](https://github.com/alibaba/nacos/releases)获取nacos-server-x.x.x.zip，并解压
  - 运行bin目录下的startup命令行
- 启动四个实例服务和两个网关服务，如下： 

| 类名 | 微服务 | 服务端口 | 版本 | 区域 |
| --- | --- | --- | --- | --- |
| DiscoveryGrayServiceA1.java | A1 | 3001 | 1.0 | dev |
| DiscoveryGrayServiceA2.java | A2 | 3002 | 1.1 | qa |
| DiscoveryGrayServiceB1.java | B1 | 4001 | 1.0 | dev |
| DiscoveryGrayServiceB2.java | B2 | 4002 | 1.1 | qa |
| DiscoveryGrayGateway.java | Gateway | 5001 | 1.0 | 无 |
| DiscoveryGrayZuul.java | Zuul | 5002 | 1.0 | 无 |

## 验证无灰度发布和路由的调用
- 在浏览器中执行[http://localhost:5001/discovery-gray-service-a/invoke/gateway](http://localhost:5001/discovery-gray-service-a/invoke/gateway)。测试没有灰度配置的情况下，通过Spring Cloud Gateway网关的调用结果。该结果显示，在反复执行下，所有服务都会被调用到，如下：
```xml
gateway -> discovery-gray-service-a[192.168.0.107:3001][V1.0][Region=dev] 
-> discovery-gray-service-b[192.168.0.107:4001][V1.0][Region=qa]
```

- 在浏览器中执行[http://localhost:5002/discovery-gray-service-a/invoke/zuul](http://localhost:5002/discovery-gray-service-a/invoke/zuul)。测试没有灰度路由的情况下，通过Zuul网关的调用结果。该结果显示，在反复执行下，所有服务都会被调用到，如下：
```xml
zuul -> discovery-gray-service-a[192.168.0.107:3001][V1.0][Region=dev] 
-> discovery-gray-service-b[192.168.0.107:4001][V1.0][Region=qa]
```

## 配置灰度发布和路由规则
在Nacos配置中心，增加灰度规则

- 增加Zuul的基于区域路由的灰度规则，Group为discovery-gray-group，Data Id为discovery-gray-zuul，规则内容如下，实现从Zuul发起的调用都走区域为dev的服务：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <strategy>
        <region>dev</region>
    </strategy>
</rule>
```

每个服务调用的区域都可以自行指定，见下面第二条。当所有服务都选同一区域的时候，可以简化成下面第一条
```xml
<region>dev</region>
<region>{"discovery-gray-service-a":"dev", "discovery-gray-service-b":"dev"}</region>
```

- 增加Spring Cloud Gateway的基于版本路由的灰度规则，Group为discovery-gray-group，Data Id为discovery-gray-gateway，规则内容如下，实现从Spring Cloud Gateway发起的调用都走版本为1.0的服务：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <strategy>
        <version>1.0</version>
    </strategy>
</rule>
```

每个服务调用的版本都可以自行指定，见下面第二条。当所有服务都选同一版本的时候，可以简化成下面第一条
```xml
<version>1.0</version>
<version>{"discovery-gray-service-a":"1.0", "discovery-gray-service-b":"1.0"}</version>
```

## 验证灰度发布和路由调用
重复上述浏览器的调用，验证存在灰度发布和路由下的调用。结果显示，在反复执行下，只会调用到符合灰度规则的服务，请仔细观察


## 更多的实现方式

除了上面通过配置中心发布灰度规则外，还有如下两种方式:

### 通过前端传入灰度发布和路由规则
通过前端（Postman）方式传入灰度路由规则，来代替配置中心方式。注意：当配置中心和界面都配置后，以界面传入优先

- 区域规则，Header格式如下任选一个：
```xml
n-d-region=dev
n-d-region={"discovery-gray-service-a":"dev", "discovery-gray-service-b":"dev"}
```

- 版本规则，Header格式如下任选一个：
```xml
n-d-version=1.0
n-d-version={"discovery-gray-service-a":"1.0", "discovery-gray-service-b":"1.0"}
```  

### 通过自定义网关Filter设置灰度发布和路由规则
继承覆盖GatewayStrategyRouteFilter和ZuulStrategyRouteFilter，并覆盖掉如下方法
```java
protected String getRouteVersion();

protected String getRouteRegion();

protected String getRouteAddress();
```

## Star走势图

[![Stargazers over time](https://starchart.cc/Nepxion/DiscoveryGray.svg)](https://starchart.cc/Nepxion/DiscoveryGray)