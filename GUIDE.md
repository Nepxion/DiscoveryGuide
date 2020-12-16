[^_^]: ![](http://nepxion.gitee.io/docs/discovery-doc/Cover.jpg)

![](http://nepxion.gitee.io/docs/discovery-doc/Banner.png)

# Discovery【探索】微服务企业级解决方案新手快速入门

## 准备工作

![](http://nepxion.gitee.io/docs/icon-doc/information.png) 请注意，新手快速入门，用的是simple分支

### 环境搭建
① 下载代码，Git clone [https://github.com/Nepxion/DiscoveryGuide.git](https://github.com/Nepxion/DiscoveryGuide.git)，分支为simple

② 代码导入IDE

③ 下载Nacos服务器

- 从[https://github.com/alibaba/nacos/releases](https://github.com/alibaba/nacos/releases)获取nacos-server-x.x.x.zip，并解压

④ 启动Nacos服务器

- Windows环境下，运行bin目录下的startup.cmd
- Linux环境下，运行bin目录下的startup.sh

### 启动服务 
- 在IDE中，启动四个应用服务和两个网关服务，如下

| 类名 | 微服务 | 服务端口 | 版本 | 区域 | 环境 | 可用区
| --- | --- | --- | --- | --- | -- | -- | 
| DiscoveryGuideServiceA1.java | A1 | 3001 | 1.0 | dev | env1 | zone1 |
| DiscoveryGuideServiceA2.java | A2 | 3002 | 1.1 | qa | common | zone2 |
| DiscoveryGuideServiceB1.java | B1 | 4001 | 1.0 | qa | env1 | zone1 |
| DiscoveryGuideServiceB2.java | B2 | 4002 | 1.1 | dev | common | zone2 |
| DiscoveryGuideGateway.java | Gateway | 5001 | 1.0 | 无 | 无 | 无 |
| DiscoveryGuideZuul.java | Zuul | 5002 | 1.0 | 无 | 无 | 无 |

- 部署拓扑图

![](http://nepxion.gitee.io/docs/discovery-doc/BasicTopology.jpg)

全链路路径， 如下
```
API网关 -> 服务A（两个实例） -> 服务B（两个实例）
```

### 环境验证
通过Postman工具验证

- 导入Postman的测试脚本postman.json（位于根目录下）

- 在Postman中执行目录结构下〔Nepxion〕->〔Discovery指南网关接口〕->〔Gateway网关调用示例〕，调用地址为[http://localhost:5001/discovery-guide-service-a/invoke/gateway](http://localhost:5001/discovery-guide-service-a/invoke/gateway)，相关的Header值已经预设，供开发者修改。执行通过Spring Cloud Gateway网关发起的调用，结果为如下格式

```
gateway 
-> [ID=discovery-guide-service-a][T=service][P=Nacos][H=192.168.0.107:3001][V=1.0][R=dev][E=env1][Z=zone1][G=discovery-guide-group][TID=48682.7508.15870951148324081][SID=49570.77.15870951148480000] 
-> [ID=discovery-guide-service-b][T=service][P=Nacos][H=192.168.0.107:4001][V=1.0][R=qa][E=env1][Z=zone2][G=discovery-guide-group][TID=48682.7508.15870951148324081][SID=49571.85.15870951189970000]
```

- 在Postman中执行目录结构下〔Nepxion〕->〔Discovery指南网关接口〕->〔Zuul网关调用示例〕，调用地址为[http://localhost:5002/discovery-guide-service-a/invoke/zuul](http://localhost:5002/discovery-guide-service-a/invoke/zuul)，相关的Header值已经预设，供开发者修改。执行通过Zuul网关发起的调用，结果为如下格式

```
zuul 
-> [ID=discovery-guide-service-a][T=service][P=Nacos][H=192.168.0.107:3001][V=1.0][R=dev][E=env1][Z=zone1][G=discovery-guide-group][TID=48682.7508.15870951148324081][SID=49570.77.15870951148480000] 
-> [ID=discovery-guide-service-b][T=service][P=Nacos][H=192.168.0.107:4001][V=1.0][R=qa][E=env1][Z=zone2][G=discovery-guide-group][TID=48682.7508.15870951148324081][SID=49571.85.15870951189970000]
```

- 上述步骤在下面每次更改规则策略的时候执行，并验证结果和规则策略的期望值是否相同

## 执行蓝绿发布

![](http://nepxion.gitee.io/docs/icon-doc/information.png) 有如下两种简单方式，最终执行结果一致

### 基于Header传递方式的蓝绿发布策略

在Postman上，设置Header为如下值
```
n-d-version={"discovery-guide-service-a":"1.0", "discovery-guide-service-b":"1.1"}
```

执行调用，根据返回值，验证discovery-guide-service-a是否选择1.0版本进行调用，discovery-guide-service-b是否选择1.1版本进行调用

### 基于网关配置的蓝绿发布策略

分别对Spring Cloud Gateway和Zuul增加蓝绿发布策略

① 对于Spring Cloud Gateway，它的Group为discovery-guide-group，Data Id为discovery-guide-gateway

![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryGuide2-13.jpg)

② 对于Zuul，它的Group为discovery-guide-group，Data Id为discovery-guide-zuul

![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryGuide2-14.jpg)

③ 蓝绿发布策略内容统一如下
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <strategy>
        <version>{"discovery-guide-service-a":"1.0", "discovery-guide-service-b":"1.1"}</version>
    </strategy>
</rule>
```

执行调用，根据返回值，验证discovery-guide-service-a是否选择1.0版本进行调用，discovery-guide-service-b是否选择1.1版本进行调用

![](http://nepxion.gitee.io/docs/icon-doc/information.png) 上述简单示例以版本匹配蓝绿发布为例，更多的使用方式，请参考官方主页文档