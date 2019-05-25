# Nepxion Discovery Gray
[![Total lines](https://tokei.rs/b1/github/Nepxion/DiscoveryGray?category=lines)](https://github.com/Nepxion/DiscoveryGray)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?label=license)](https://github.com/Nepxion/DiscoveryGray/blob/master/LICENSE)
[![Build Status](https://travis-ci.org/Nepxion/DiscoveryGray.svg?branch=master)](https://travis-ci.org/Nepxion/DiscoveryGray)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/8e39a24e1be740c58b83fb81763ba317)](https://www.codacy.com/project/HaojunRen/DiscoveryGray/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Nepxion/DiscoveryGray&amp;utm_campaign=Badge_Grade_Dashboard)

Nepxion Discovery Gray是Nepxion Discovery的极简示例，有助于使用者快速入门，它采用配置中心配置路由规则映射在网关过滤器中植入Header信息而实现，当然也支持从界面传入Header信息，主要包括版本路由和区域路由两种。如果使用者需要更强大的功能，请参考[https://github.com/Nepxion/Discovery](https://github.com/Nepxion/Discovery)

## 操作演示
- 下载代码并导入IDE
- 分别启动两个网关服务和四个实例服务
  - DiscoveryGrayZuul.java
  - DiscoveryGrayGateway.java
  - DiscoveryGrayServiceA1.java
  - DiscoveryGrayServiceA2.java
  - DiscoveryGrayServiceB1.java
  - DiscoveryGrayServiceB2.java
- 在浏览器中执行[http://localhost:5001/discovery-gray-service-a/invoke/gateway](http://localhost:5001/discovery-gray-service-a/invoke/gateway)，测试没有灰度路由的情况下，通过Spring Cloud Gateway网关的调用结果，打印出全路径结果，例如：
```xml
gateway -> discovery-gray-service-a[192.168.0.107:3001][V1.0][Region=dev] -> discovery-gray-service-b[192.168.0.107:4001][V1.0][Region=qa]
```
- 在浏览器中执行[http://localhost:5002/discovery-gray-service-a/invoke/zuul](http://localhost:5002/discovery-gray-service-a/invoke/zuul)，测试没有灰度路由的情况下，通过Zuul网关的调用结果，打印出全路径结果，例如：
```xml
zuul -> discovery-gray-service-a[192.168.0.107:3001][V1.0][Region=dev] -> discovery-gray-service-b[192.168.0.107:4001][V1.0][Region=qa]
```


