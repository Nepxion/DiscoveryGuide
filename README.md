![](https://nepxion.github.io/Discovery/docs/discovery-doc/Banner.png)

# Discovery【探索】云原生微服务解决方案
![Total visits](https://visitor-badge.laobi.icu/badge?page_id=Nepxion&title=total%20visits)  [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?label=license)](https://github.com/Nepxion/Discovery/blob/6.x.x/LICENSE)  [![Maven Central](https://img.shields.io/maven-central/v/com.nepxion/discovery.svg?label=maven)](https://search.maven.org/artifact/com.nepxion/discovery)  [![Javadocs](http://www.javadoc.io/badge/com.nepxion/discovery-plugin-framework-starter.svg)](http://www.javadoc.io/doc/com.nepxion/discovery-plugin-framework-starter)  [![Build Status](https://github.com/Nepxion/Discovery/workflows/build/badge.svg)](https://github.com/Nepxion/Discovery/actions)  [![Codacy Badge](https://app.codacy.com/project/badge/Grade/5c42eb719ef64def9cad773abd877e8b)](https://www.codacy.com/gh/Nepxion/Discovery/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Nepxion/Discovery&amp;utm_campaign=Badge_Grade)  [![Stars](https://img.shields.io/github/stars/Nepxion/Discovery.svg?label=Stars&style=flat&logo=GitHub)](https://github.com/Nepxion/Discovery/stargazers)  [![Stars](https://gitee.com/Nepxion/Discovery/badge/star.svg?theme=gvp)](https://gitee.com/Nepxion/Discovery/stargazers)

[![Wiki](https://badgen.net/badge/icon/wiki?icon=wiki&label=GitHub)](https://github.com/Nepxion/Discovery/wiki)  [![Wiki](https://badgen.net/badge/icon/wiki?icon=wiki&label=Gitee)](https://gitee.com/nepxion/Discovery/wikis/pages?sort_id=3993615&doc_id=1124387)  [![Discovery PPT](https://img.shields.io/badge/Discovery%20-ppt-brightgreen?logo=Microsoft%20PowerPoint)](https://nepxion.github.io/Discovery/docs/link-doc/discovery-ppt.html)  [![Discovery Page](https://img.shields.io/badge/Discovery%20-page-brightgreen?logo=Microsoft%20Edge)](https://nepxion.github.io/Discovery/)  [![Discovery Platform Page](https://img.shields.io/badge/Discovery%20Platform%20-page-brightgreen?logo=Microsoft%20Edge)](https://nepxion.github.io/DiscoveryPlatform)  [![Polaris Page](https://img.shields.io/badge/Polaris%20-page-brightgreen?logo=Microsoft%20Edge)](https://polaris-paas.github.io/polaris-wiki)

<a href="https://github.com/Nepxion" tppabs="#" target="_blank"><img width="25" height="25" src="https://nepxion.github.io/Discovery/docs/icon-doc/github.png"></a>&nbsp;  <a href="https://gitee.com/Nepxion" tppabs="#" target="_blank"><img width="25" height="25" src="https://nepxion.github.io/Discovery/docs/icon-doc/gitee.png"></a>&nbsp;  <a href="https://search.maven.org/search?q=g:com.nepxion" tppabs="#" target="_blank"><img width="25" height="25" src="https://nepxion.github.io/Discovery/docs/icon-doc/maven.png"></a>&nbsp;  <a href="https://nepxion.github.io/Discovery/docs/contact-doc/wechat.jpg" tppabs="#" target="_blank"><img width="25" height="25" src="https://nepxion.github.io/Discovery/docs/icon-doc/wechat.png"></a>&nbsp;  <a href="https://nepxion.github.io/Discovery/docs/contact-doc/dingding.jpg" tppabs="#" target="_blank"><img width="25" height="25" src="https://nepxion.github.io/Discovery/docs/icon-doc/dingding.png"></a>&nbsp;  <a href="https://nepxion.github.io/Discovery/docs/contact-doc/gongzhonghao.jpg" tppabs="#" target="_blank"><img width="25" height="25" src="https://nepxion.github.io/Discovery/docs/icon-doc/gongzhonghao.png"></a>&nbsp;  <a href="mailto:1394997@qq.com" tppabs="#"><img width="25" height="25" src="https://nepxion.github.io/Discovery/docs/icon-doc/email.png"></a>

如果您觉得本框架具有一定的参考价值和借鉴意义，请帮忙在页面右上角 [**Star**]

## 入门必读
本文只介绍入门Discovery【探索】最基础的功能和用法，更多资料请参考相关文档

### 快速上手
[如何快速搭建和运行示例](https://github.com/Nepxion/Discovery/wiki/如何快速搭建和运行示例)

### 功能体验
[如何执行全链路简单蓝绿发布](https://github.com/Nepxion/Discovery/wiki/如何执行全链路简单蓝绿发布)<br>
[如何执行全链路简单灰度发布](https://github.com/Nepxion/Discovery/wiki/如何执行全链路简单灰度发布)<br>
[如何执行全链路高级蓝绿发布](https://github.com/Nepxion/Discovery/wiki/如何执行全链路高级蓝绿发布)<br>
[如何执行全链路高级灰度发布](https://github.com/Nepxion/Discovery/wiki/如何执行全链路高级灰度发布)<br>

### 快速集成
[如何快速集成框架](https://github.com/Nepxion/Discovery/wiki/如何快速集成框架)

### 故障定位
开启Debug模式，通过增加启动参数`-Dstrategy.debug=true`启动所有的网关和服务。详细资料参考如下链接：<br>
[如何解决蓝绿灰度发布失效问题](https://github.com/Nepxion/Discovery/wiki/如何解决蓝绿灰度发布失效问题)

### 异步探针
开启异步探针，通过增加启动参数`-javaagent:C:/opt/discovery-agent/discovery-agent-starter-1.3.0.jar`启动所有的网关和服务，`C:/opt`需要变更为具体使用场景下的目录。详细资料参考如下链接：<br>
[如何在异步场景下使用探针Agent](https://github.com/Nepxion/Discovery/wiki/如何在异步场景下使用探针Agent)

### 自动化测试

[如何执行全链路自动化模拟流程测试](https://github.com/Nepxion/Discovery/wiki/如何执行全链路自动化模拟流程测试)<br>
[如何执行全链路自动化流量侦测测试](https://github.com/Nepxion/Discovery/wiki/如何执行全链路自动化流量侦测测试)

## 请联系我
微信、钉钉、公众号和文档

![](https://nepxion.github.io/Discovery/docs/contact-doc/wechat-1.jpg)![](https://nepxion.github.io/Discovery/docs/contact-doc/dingding-1.jpg)![](https://nepxion.github.io/Discovery/docs/contact-doc/gongzhonghao-1.jpg)![](https://nepxion.github.io/Discovery/docs/contact-doc/document-1.jpg)

## Star走势图
[![Stargazers over time](https://starchart.cc/Nepxion/Discovery.svg)](https://starchart.cc/Nepxion/Discovery)