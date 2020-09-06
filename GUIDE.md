[^_^]: ![](http://nepxion.gitee.io/docs/discovery-doc/Cover.jpg)

![](http://nepxion.gitee.io/docs/discovery-doc/Banner.png)

# Discovery【探索】微服务企业级解决方案入门必读

## 准备工作
为了更好的阐述框架的各项功能，本文围绕指南示例展开，请使用者先进行下面的准备工作。指南示例以Nacos为服务注册中心和配置中心展开介绍，使用者可自行换成其它服务注册中心和配置中心

### 环境搭建
① 下载代码

② Git clone https://github.com/Nepxion/DiscoveryGuide.git

③ 代码导入IDE

④ 下载Nacos服务器

- 从[https://github.com/alibaba/nacos/releases](https://github.com/alibaba/nacos/releases)获取nacos-server-x.x.x.zip，并解压

⑤ 启动Nacos服务器

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

- 在Postman中执行目录结构下 ”Nepxion“ -> ”Discovery指南网关接口“ -> ”Gateway网关调用示例“，调用地址为[http://localhost:5001/discovery-guide-service-a/invoke/gateway](http://localhost:5001/discovery-guide-service-a/invoke/gateway)，相关的Header值已经预设，供开发者修改。测试通过Spring Cloud Gateway网关的调用结果，结果为如下格式
```
gateway -> [ID=discovery-guide-service-a][P=Nacos][H=192.168.0.107:3001][V=1.0][R=dev][E=env1][Z=zone1][G=discovery-guide-group][TID=48682.7508.15870951148324081][SID=49570.77.15870951148480000] 
-> [ID=discovery-guide-service-b][P=Nacos][H=192.168.0.107:4001][V=1.0][R=qa][E=env1][Z=zone2][G=discovery-guide-group][TID=48682.7508.15870951148324081][SID=49571.85.15870951189970000]
```

- 在Postman中执行目录结构下 ”Nepxion“ -> ”Discovery指南网关接口“ -> ”Zuul网关调用示例“，调用地址为[http://localhost:5002/discovery-guide-service-a/invoke/zuul](http://localhost:5002/discovery-guide-service-a/invoke/zuul)，相关的Header值已经预设，供开发者修改。测试通过Zuul网关的调用结果，结果为如下格式
```
zuul -> [ID=discovery-guide-service-a][P=Nacos][H=192.168.0.107:3001][V=1.0][R=dev][E=env1][Z=zone1][G=discovery-guide-group][TID=48682.7508.15870951148324081][SID=49570.77.15870951148480000] 
-> [ID=discovery-guide-service-b][P=Nacos][H=192.168.0.107:4001][V=1.0][R=qa][E=env1][Z=zone2][G=discovery-guide-group][TID=48682.7508.15870951148324081][SID=49571.85.15870951189970000]
```

- 上述步骤在下面每次更改规则策略的时候执行，并验证结果和规则策略的期望值是否相同

### 执行灰度路由

#### 基于Header传递方式的灰度路由策略

在Postman上，设置Header为如下值
```
n-d-version={"discovery-guide-service-a":"1.0", "discovery-guide-service-b":"1.1"}
```

执行调用，根据返回值，验证discovery-guide-service-a是否选择1.0版本进行调用，discovery-guide-service-b是否选择1.1版本进行调用

#### 基于网关配置的灰度路由策略

- 增加Spring Cloud Gateway的基于版本匹配路由的灰度策略，Group为discovery-guide-group，Data Id为discovery-guide-gateway，策略内容如下
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <strategy>
        <version>{"discovery-guide-service-a":"1.0", "discovery-guide-service-b":"1.1"}</version>
    </strategy>
</rule>
```

执行调用，根据返回值，验证discovery-guide-service-a是否选择1.0版本进行调用，discovery-guide-service-b是否选择1.1版本进行调用

- 增加Zuul的基于版本匹配路由的灰度策略，Group为discovery-guide-group，Data Id为discovery-guide-zuul，策略内容如下
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <strategy>
        <version>{"discovery-guide-service-a":"1.0", "discovery-guide-service-b":"1.1"}</version>
    </strategy>
</rule>
```

执行调用，根据返回值，验证discovery-guide-service-a是否选择1.0版本进行调用，discovery-guide-service-b是否选择1.1版本进行调用