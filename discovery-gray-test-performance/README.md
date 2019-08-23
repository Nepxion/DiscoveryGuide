# Nepxion Discovery Performance Test

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

