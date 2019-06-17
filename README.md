# Nepxion Discovery Gray
[![Total lines](https://tokei.rs/b1/github/Nepxion/DiscoveryGray?category=lines)](https://github.com/Nepxion/DiscoveryGray)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?label=license)](https://github.com/Nepxion/DiscoveryGray/blob/master/LICENSE)
[![Build Status](https://travis-ci.org/Nepxion/DiscoveryGray.svg?branch=master)](https://travis-ci.org/Nepxion/DiscoveryGray)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/8e39a24e1be740c58b83fb81763ba317)](https://www.codacy.com/project/HaojunRen/DiscoveryGray/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Nepxion/DiscoveryGray&amp;utm_campaign=Badge_Grade_Dashboard)

Nepxion Discovery Gray是Nepxion Discovery的极简示例，有助于使用者快速入门。它基于Spring Cloud Greenwich和Finchley版而制作（使用者可自行换成Edgware版），主要功能包括：
- 网关灰度路由。采用配置中心配置路由规则映射在网关过滤器中植入Header信息而实现，主要包括版本路由和区域路由两种
- 服务灰度权重。采用配置中心配置权重规则映射在全链路而实现，主要包括版本权重和区域区域两种
- 自定义网关和服务的路由策略。采用简单编程方式，根据业务参数自定义路由策略
- 自定义网关和服务的”禁止注册“、”禁止被发现“、”禁止被负载均衡“策略。采用简单编程方式，根据业务参数自定义服务发现和负载均衡策略

阿里巴巴Nacos是新一代集服务注册发现中心和配置中心为一体的中间件。它是构建以“服务”为中心的现代应用架构 (例如微服务范式、云原生范式) 的服务基础设施，支持几乎所有主流类型的“服务”的发现、配置和管理，更敏捷和容易地构建、交付和管理微服务平台

示例以Nacos为服务注册中心和配置中心（使用者可自行换成其它服务注册中心和配置中心），通过Gateway和Zuul调用两个版本或者区域的服务，模拟网关灰度路由和服务灰度权重的功能。如果使用者需要更强大的功能，请参考[https://github.com/Nepxion/Discovery](https://github.com/Nepxion/Discovery)

![Alt text](https://github.com/Nepxion/Docs/blob/master/discovery-doc/DiscoveryGray1.jpg)

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
| DiscoveryGrayServiceB1.java | B1 | 4001 | 1.0 | qa |
| DiscoveryGrayServiceB2.java | B2 | 4002 | 1.1 | dev |
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

## 网关灰度路由策略

### 配置网关灰度路由规则
在Nacos配置中心，增加网关灰度路由规则

- 增加Zuul的基于区域路由的灰度规则，Group为discovery-gray-group，Data Id为discovery-gray-zuul，规则内容如下，实现从Zuul发起的调用都走区域为dev的服务：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <strategy>
        <region>dev</region>
    </strategy>
</rule>
```
![Alt text](https://github.com/Nepxion/Docs/blob/master/discovery-doc/DiscoveryGray2.jpg)

每个服务调用的区域都可以自行指定，见下面第二条。当所有服务都选同一区域的时候，可以简化成下面第一条
```xml
<region>dev</region>
<region>{"discovery-gray-service-a":"dev", "discovery-gray-service-b":"dev"}</region>
```

如果上述表达式还未满足需求，也可以采用通配符（具体详细用法，参考Spring AntPathMatcher）
```xml
* - 表示调用范围为所有服务的所有区域
d* - 表示调用范围为所有服务的d开头的所有区域
```
或者
```xml
"discovery-gray-service-b":"d*;q?" - 表示discovery-gray-service-b服务的区域调用范围是d开头的所有区域，或者是q开头的所有区域（末尾必须是1个字符）
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
![Alt text](https://github.com/Nepxion/Docs/blob/master/discovery-doc/DiscoveryGray3.jpg)

每个服务调用的版本都可以自行指定，见下面第二条。当所有服务都选同一版本的时候，可以简化成下面第一条
```xml
<version>1.0</version>
<version>{"discovery-gray-service-a":"1.0", "discovery-gray-service-b":"1.0"}</version>
```

如果上述表达式还未满足需求，也可以采用通配符（具体详细用法，参考Spring AntPathMatcher）
```xml
* - 表示调用范围为所有服务的所有版本
1.* - 表示调用范围为所有服务的1开头的所有版本
```
或者
```xml
"discovery-gray-service-b":"1.*;1.2.?" - 表示discovery-gray-service-b服务的版本调用范围是1开头的所有版本，或者是1.2开头的所有版本（末尾必须是1个字符）
```

### 验证网关灰度路由调用
重复“验证无灰度发布和路由的调用”步骤，结果显示，在反复执行下，只会调用到符合网关灰度路由规则的服务，请仔细观察

### 其它更多的方式
除了上面通过配置中心发布灰度规则外，还有如下三种方式:

#### 通过前端传入灰度路由规则
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

#### 通过自定义网关Filter设置灰度路由规则
继承覆盖GatewayStrategyRouteFilter和ZuulStrategyRouteFilter，并覆盖掉如下方法
```java
protected String getRouteVersion();

protected String getRouteRegion();

protected String getRouteAddress();
```

例如：
```java
// 当Header中传来的用户为张三，执行一条路由路径；李四，执行另一条路由路径
public class MyRouteFilter extends GatewayStrategyRouteFilter {
    @Override
    protected String getRouteVersion() {
        String user = strategyContextHolder.getHeader("user");

        if (StringUtils.equals(user, "zhangsan")) {
            return "{\"discovery-gray-service-a\":\"1.0\", \"discovery-gray-service-b\":\"1.1\"}";
        } else if (StringUtils.equals(user, "lisi")) {
            return "{\"discovery-gray-service-a\":\"1.1\", \"discovery-gray-service-b\":\"1.0\"}";
        }

        return super.getRouteVersion();
    }
}
```

```java
// 当Header中传来的用户为张三，执行一条路由路径；李四，执行另一条路由路径
public class MyRouteFilter extends ZuulStrategyRouteFilter {
    @Override
    protected String getRouteVersion() {
        String user = strategyContextHolder.getHeader("user");

        if (StringUtils.equals(user, "zhangsan")) {
            return "{\"discovery-gray-service-a\":\"1.0\", \"discovery-gray-service-b\":\"1.1\"}";
        } else if (StringUtils.equals(user, "lisi")) {
            return "{\"discovery-gray-service-a\":\"1.1\", \"discovery-gray-service-b\":\"1.0\"}";
        }

        return super.getRouteVersion();
    }
}
```

#### 通过业务参数自定义路由规则

- 根据业务参数自定义路由规则。下面代码既适用于Zuul和Spring Cloud Gateway网关，也适用于Service微服务
```java
// 实现了组合策略，版本路由策略+区域路由策略+IP和端口路由策略+自定义策略
public class DiscoveryGrayEnabledStrategy extends AbstractDiscoveryEnabledStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(DiscoveryGrayEnabledStrategy.class);

    @Override
    public boolean apply(Server server) {
        // 对Rest调用传来的Header参数（例如：mobile）做策略
        String mobile = strategyContextHolder.getHeader("mobile");
        String serviceId = pluginAdapter.getServerServiceId(server);
        String version = pluginAdapter.getServerMetadata(server).get(DiscoveryConstant.VERSION);

        LOG.info("负载均衡用户定制触发：mobile={}, serviceId={}, version={}", mobile, serviceId, version);

        if (StringUtils.isNotEmpty(mobile)) {
            // 手机号以移动138开头，路由到1.0版本的服务上
            if (mobile.startsWith("138") && StringUtils.equals(version, "1.0")) {
                return true;
                // 手机号以联通133开头，路由到2.0版本的服务上
            } else if (mobile.startsWith("133") && StringUtils.equals(version, "1.1")) {
                return true;
            } else {
                // 其它情况，直接拒绝请求
                return false;
            }
        }

        return true;
    }
}
```

## 服务灰度权重策略

### 配置服务灰度权重规则
在Nacos配置中心，增加服务灰度权重规则

注意：网关灰度路由和服务灰度权重功能会叠加，为了不影响演示效果，请先清除网关灰度路由的规则（在Nacos上删除对应的两条配置即可）

- 增加区域权重的灰度规则，Group为discovery-gray-group，Data Id为discovery-gray-group（全局发布，两者都是组名），规则内容如下，实现区域为dev的服务提供90%的流量，区域为qa的服务提供10%的流量：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <discovery>
        <weight>
            <region provider-weight-value="dev=90;qa=10"/>
        </weight>
    </discovery>
</rule>
```
![Alt text](https://github.com/Nepxion/Docs/blob/master/discovery-doc/DiscoveryGray4.jpg)

- 增加服务权重的灰度规则，Group为discovery-gray-group，Data Id为discovery-gray-group（全局发布，两者都是组名），规则内容如下，实现a服务1.0版本提供90%的流量，1.1版本提供10%的流量；b服务1.0版本提供20%的流量，1.1版本提供80%的流量：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <discovery>
        <weight>
            <service provider-service-name="discovery-gray-service-a" provider-weight-value="1.0=90;1.1=10"/>
            <service provider-service-name="discovery-gray-service-b" provider-weight-value="1.0=20;1.1=80"/>
        </weight>
    </discovery>
</rule>
```
![Alt text](https://github.com/Nepxion/Docs/blob/master/discovery-doc/DiscoveryGray5.jpg)

### 验证服务灰度权重调用
重复“验证无灰度发布和路由的调用”步骤，结果显示，在反复执行下，只会调用到符合服务灰度权重的服务，请仔细观察被随机权重调用到的概率

## 自定义网关和服务的”禁止注册“、”禁止被发现“、”禁止被负载均衡“策略

- 根据业务参数自定义服务发现和负载均衡策略。下面代码既适用于Zuul和Spring Cloud Gateway网关，也适用于Service微服务

下述场景，通过修改配置文件中的spring.cloud.nacos.discovery.metadata.group，分别改为mygroup1或者mygroup2或者mygroup3，再通过Postman调用，体验效果

自定义服务注册策略
```java
// 当本服务的元数据中的group为mygroup1，禁止被注册到注册中心
public class MyRegisterListener extends AbstractRegisterListener {
    @Override
    public void onRegister(Registration registration) {
        String serviceId = registration.getServiceId().toLowerCase();
        String group = registration.getMetadata().get(DiscoveryConstant.GROUP);
        if (StringUtils.equals(group, "mygroup1")) {
            throw new DiscoveryException("服务名=" + serviceId + "，组名=" + group + "的服务禁止被注册到注册中心");
        }
    }

    @Override
    public void onDeregister(Registration registration) {

    }

    @Override
    public void onSetStatus(Registration registration, String status) {

    }

    @Override
    public void onClose() {

    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE - 1;
    }
}
```

自定义服务发现策略
```java
// 当目标服务的元数据中的group为mygroup2，禁止被本服务发现（只用于DiscoveryClient.getInstances接口方法用）
public class MyDiscoveryListener extends AbstractDiscoveryListener {
    @Override
    public void onGetInstances(String serviceId, List<ServiceInstance> instances) {
        Iterator<ServiceInstance> iterator = instances.iterator();
        while (iterator.hasNext()) {
            ServiceInstance serviceInstance = iterator.next();
            String group = serviceInstance.getMetadata().get(DiscoveryConstant.GROUP);
            if (StringUtils.equals(group, "mygroup2")) {
                iterator.remove();

                System.out.println("********** 服务名=" + serviceId + "，组名=" + group + "的服务禁止被本服务发现");
            }
        }
    }

    @Override
    public void onGetServices(List<String> services) {

    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE - 1;
    }
}
```

自定义服务负载均衡策略
```java
// 当目标服务的元数据中的group为mygroup2，禁止被本服务负载均衡
public class MyLoadBalanceListener extends AbstractLoadBalanceListener {
    @Autowired
    private PluginAdapter pluginAdapter;

    @Override
    public void onGetServers(String serviceId, List<? extends Server> servers) {
        Iterator<? extends Server> iterator = servers.iterator();
        while (iterator.hasNext()) {
            Server server = iterator.next();
            String group = pluginAdapter.getServerMetadata(server).get(DiscoveryConstant.GROUP);
            if (StringUtils.equals(group, "mygroup3")) {
                iterator.remove();

                System.out.println("********** 服务名=" + serviceId + "，组名=" + group + "的服务禁止被本服务负载均衡");
            }
        }
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE - 1;
    }
}
```

## Star走势图

[![Stargazers over time](https://starchart.cc/Nepxion/DiscoveryGray.svg)](https://starchart.cc/Nepxion/DiscoveryGray)