# Nepxion Discovery Performance Test
[![Total lines](https://tokei.rs/b1/github/Nepxion/Discovery?category=lines)](https://tokei.rs/b1/github/Nepxion/Discovery?category=lines)  [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?label=license)](https://github.com/Nepxion/Discovery/blob/master/LICENSE)  [![Maven Central](https://img.shields.io/maven-central/v/com.nepxion/discovery.svg?label=maven%20central)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.nepxion%22%20AND%20discovery)  [![Javadocs](http://www.javadoc.io/badge/com.nepxion/discovery-plugin-framework.svg)](http://www.javadoc.io/doc/com.nepxion/discovery-plugin-framework)  [![Build Status](https://travis-ci.org/Nepxion/Discovery.svg?branch=master)](https://travis-ci.org/Nepxion/Discovery)  [![Codacy Badge](https://api.codacy.com/project/badge/Grade/8e39a24e1be740c58b83fb81763ba317)](https://www.codacy.com/project/HaojunRen/Discovery/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Nepxion/Discovery&amp;utm_campaign=Badge_Grade_Dashboard)

路过的同学，如果您觉得这个开源框架不错，顺手给它点个Star吧

![Alt text](https://github.com/Nepxion/Docs/raw/master/discovery-doc/Star2.jpg)

Nepxion Discovery Performance Test

## 测试环境
1. 准备两台机器部署spring cloud 应用(F版本)
2. 准备一台机器部署网关(zuul)
3. 准备一台机器部署压测工具 
3. 应用编写hello接口，返回world

## 测试方法
使用wrk脚本进行性能测试，wrk脚本请参考[压测脚本](https://github.com/Nepxion/DiscoveryGray/tree/master/discovery-gray-test-automation/hello.lua)

wrk详细说明参考https://github.com/wg/wrk

## 测试步骤
1. 登录到wrk的机器，进入wrk目录
2. 运行命令 wrk -t64 -c2000 -d30s -T10s --latency http://zuul.xxx.com --script=scripts/hello.lua
     参数说明： -t表示多少线程，这个可以设置为CPU核心的2到四倍;-c是开启多少个连接，可以理解为并发数;-d表示持续时间，可以用s,m等;-T 设置接口超时时间
                --latency 表示结果显示延迟时间;--script 指定要运行的脚本
   
3. 等待结果,Requests/sec 表示每秒处理的请求数

![Alt text](https://github.com/Nepxion/Docs/raw/master/zxing-doc/微信-1.jpg)![Alt text](https://github.com/Nepxion/Docs/raw/master/zxing-doc/公众号-1.jpg)

