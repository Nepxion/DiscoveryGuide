![Alt text](https://github.com/HaojunRen/Docs/raw/master/discovery-doc/Cover.jpg)

# Nepxion Discovery 框架测试
[![Total lines](https://tokei.rs/b1/github/Nepxion/Discovery?category=lines)](https://tokei.rs/b1/github/Nepxion/Discovery?category=lines)  [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?label=license)](https://github.com/Nepxion/Discovery/blob/master/LICENSE)  [![Maven Central](https://img.shields.io/maven-central/v/com.nepxion/discovery.svg?label=maven%20central)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.nepxion%22%20AND%20discovery)  [![Javadocs](http://www.javadoc.io/badge/com.nepxion/discovery-plugin-framework.svg)](http://www.javadoc.io/doc/com.nepxion/discovery-plugin-framework)  [![Build Status](https://travis-ci.org/Nepxion/Discovery.svg?branch=master)](https://travis-ci.org/Nepxion/Discovery)  [![Codacy Badge](https://api.codacy.com/project/badge/Grade/8e39a24e1be740c58b83fb81763ba317)](https://www.codacy.com/project/HaojunRen/Discovery/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Nepxion/Discovery&amp;utm_campaign=Badge_Grade_Dashboard)

每一个访问路过的朋友，如果您觉得这个开源框架不错，请顺手在页面右上角帮它[**Star**]一下

![Alt text](https://github.com/HaojunRen/Docs/raw/master/discovery-doc/Star2.jpg)

如果本文档由于Github网速原因无法完整阅读，请访问
- [Nepxion Discovery【探索】微服务企业级解决方案(PDF版)](http://nepxion.gitee.io/docs/link-doc/discovery-solution-pdf.html)
- [Nepxion Discovery【探索】测试篇(PDF版)](http://nepxion.gitee.io/docs/link-doc/discovery-test-pdf.html) 或 [Nepxion Discovery【探索】测试篇(HTML版)](http://nepxion.gitee.io/docs/link-doc/discovery-test.html)

Nepxion Discovery【探索】框架测试，集成自动化测试和压力测试两个模块：
- 自动化测试，基于Spring Boot/Spring Cloud的自动化测试框架，包括普通调用测试、灰度调用测试和扩展调用测试（例如：支持阿里巴巴的Sentinel，FF4j的功能开关等）。通过注解形式，跟Spring Boot内置的测试机制集成，使用简单方便。该自动化测试框架的现实意义，可以把服务注册发现中心、远程配置中心、负载均衡、灰度发布、熔断降级限流、功能开关、Feign或者RestTemplate调用等中间件或者组件，一条龙组合起来进行自动化测试
- 压力测试，基于wrk的异步压力测试框架，能用很少的线程压测出很大的并发量，使用简单方便

## 目录
- [请联系我](#请联系我)
- [相关链接](#相关链接)
    - [源码主页](#源码主页)
    - [指南主页](#指南主页)
    - [文档主页](#文档主页)
- [自动化测试](#自动化测试)
    - [架构设计](#架构设计)
    - [启动控制台](#启动控制台)
    - [配置文件](#配置文件)
    - [测试用例](#测试用例)
        - [测试包引入](#测试包引入)
        - [测试入口程序](#测试入口程序)
        - [普通调用测试](#普通调用测试)
        - [灰度调用测试](#灰度调用测试)
        - [扩展调用测试](#扩展调用测试)
    - [测试报告](#测试报告)
- [压力测试](#压力测试)
    - [测试环境](#测试环境)
    - [测试介绍](#测试介绍)
    - [测试步骤](#测试步骤)
- [Star走势图](#Star走势图)

## 请联系我
微信、公众号和文档

![Alt text](https://github.com/HaojunRen/Docs/raw/master/zxing-doc/微信-1.jpg)![Alt text](https://github.com/HaojunRen/Docs/raw/master/zxing-doc/公众号-1.jpg)![Alt text](https://github.com/HaojunRen/Docs/raw/master/zxing-doc/文档-1.jpg)

## 相关链接

### 源码主页
[源码主页](https://github.com/Nepxion/Discovery)

### 指南主页
[指南主页](https://github.com/Nepxion/DiscoveryGuide)

### 文档主页
[文档主页](https://gitee.com/Nepxion/Docs/tree/master/web-doc)

## 自动化测试

### 架构设计

通过Matrix Aop框架，实现TestAutoScanProxy和TestInterceptor拦截测试用例，实现配置内容的自动化推送

### 启动控制台

运行[指南示例](https://github.com/Nepxion/DiscoveryGuide)下的DiscoveryGuideConsole.java控制台服务，它是连接服务注册发现中心、远程配置中心和服务的纽带，自动化测试利用控制台实现配置的自动更新和清除

### 配置文件

```vb
# 自动化测试框架内置配置
# 测试用例类的扫描路径
spring.application.test.scan.packages=com.nepxion.discovery.guide.test
# 测试用例的配置内容推送时，是否打印配置日志。缺失则默认为true
spring.application.test.config.print.enabled=true
# 测试用例的配置内容推送后，等待生效的时间。推送远程配置中心后，再通知各服务更新自身的配置缓存，需要一定的时间，缺失则默认为3000
spring.application.test.config.operation.await.time=3000
# 测试用例的配置内容推送的控制台地址。控制台是连接服务注册发现中心、远程配置中心和服务的纽带
spring.application.test.console.url=http://localhost:6001/

# 业务测试配置
# Spring Cloud Gateway网关配置
gateway.group=discovery-guide-group
gateway.service.id=discovery-guide-gateway
gateway.test.url=http://localhost:5001/discovery-guide-service-a/invoke/gateway

# Zuul网关配置
zuul.group=discovery-guide-group
zuul.service.id=discovery-guide-zuul
zuul.test.url=http://localhost:5002/discovery-guide-service-a/invoke/zuul

# 每个测试用例执行循环次数
testcase.loop.times=1

# 测试用例的灰度权重测试开关。由于权重测试需要大量采样调用，会造成整个自动化测试时间很长，可以通过下面开关开启和关闭。缺失则默认为true
gray.weight.testcases.enabled=true
# 测试用例的灰度权重采样总数。采样总数越大，灰度权重准确率越高，但耗费时间越长
gray.weight.testcase.sample.count=1500
# 测试用例的灰度权重准确率偏离值。采样总数越大，灰度权重准确率偏离值越小
gray.weight.testcase.result.offset=5
```

### 测试用例

自动化测试代码参考[指南示例自动化测试](https://github.com/Nepxion/DiscoveryGuide/tree/master/discovery-guide-test-automation)

- 自动化测试场景以API网关是测试的触发点，全链路如下：

```
API网关 -> 服务A（两个实例） -> 服务B（两个实例）
```

- 各个实例部署情况如下：

| 类名 | 微服务 | 服务端口 | 版本 | 区域 |
| --- | --- | --- | --- | --- |
| DiscoveryGuideServiceA1.java | A1 | 3001 | 1.0 | dev |
| DiscoveryGuideServiceA2.java | A2 | 3002 | 1.1 | qa |
| DiscoveryGuideServiceB1.java | B1 | 4001 | 1.0 | qa |
| DiscoveryGuideServiceB2.java | B2 | 4002 | 1.1 | dev |
| DiscoveryGuideGateway.java | Gateway | 5001 | 1.0 | 无 |
| DiscoveryGuideZuul.java | Zuul | 5002 | 1.0 | 无 |

#### 测试包引入

```xml
<dependencies>
    <dependency>
        <groupId>com.nepxion</groupId>
        <artifactId>discovery-plugin-test-starter</artifactId>
        <version>${discovery.version}</version>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <configuration>
                <compilerArgs>
                    <arg>-parameters</arg>
                </compilerArgs>
                <encoding>${project.build.sourceEncoding}</encoding>
                <source>${java.version}</source>
                <target>${java.version}</target>
            </configuration>
        </plugin>
    </plugins>
</build>
```

注意：对于带有注解@DTestConfig的测试用例，要用到Spring的Spel语法格式（即group = "#group", serviceId = "#serviceId"），需要引入Java8的带"-parameters"编译方式，见上面的<compilerArgs>参数设置

在IDE环境里需要设置"-parameters"的Compiler Argument：
- Eclipse加"-parameters"参数：https://www.concretepage.com/java/jdk-8/java-8-reflection-access-to-parameter-names-of-method-and-constructor-with-maven-gradle-and-eclipse-using-parameters-compiler-argument
- Idea加"-parameters"参数：http://blog.csdn.net/royal_lr/article/details/52279993

#### 测试入口程序

结合Spring Boot Junit，TestApplication.class为测试框架内置应用启动程序，MyTestConfiguration用于初始化所有测试用例类。在测试方法上面加入JUnit的@Test注解

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TestApplication.class, MyTestConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyTest {
    @Autowired
    private MyTestCases myTestCases;

    private static long startTime;

    @BeforeClass
    public static void beforeTest() {
        startTime = System.currentTimeMillis();
    }

    @AfterClass
    public static void afterTest() {
        LOG.info("* Finished automation test in {} seconds", (System.currentTimeMillis() - startTime) / 1000);
    }

    @Test
    public void testNoGray() throws Exception {
        myTestCases.testNoGray(gatewayTestUrl);
        myTestCases.testNoGray(zuulTestUrl);
    }

    @Test
    public void testVersionStrategyGray() throws Exception {
        myTestCases.testVersionStrategyGray1(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        myTestCases.testVersionStrategyGray1(zuulGroup, zuulServiceId, zuulTestUrl);
    }
}
```

```java
@Configuration
public class MyTestConfiguration {
    @Bean
    public MyTestCases myTestCases() {
        return new MyTestCases();
    }
}
```

#### 普通调用测试

在测试方法上面增加注解@DTest，通过断言Assert来判断测试结果。注解@DTest内容如下：

```java
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DTest {

}
```

代码如下：

```java
public class MyTestCases {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @DTest
    public void testNoGray(String testUrl) {
        int noRepeatCount = 0;
        List<String> resultList = new ArrayList<String>();
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            if (!resultList.contains(result)) {
                noRepeatCount++;
            }
            resultList.add(result);
        }

        Assert.assertEquals(noRepeatCount, 4);
    }
}
```

#### 灰度调用测试

在测试方法上面增加注解@DTestConfig，通过断言Assert来判断测试结果。注解DTestConfig注解内容如下：

```java
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DTestConfig {
    // 组名
    String group();

    // 服务名
    String serviceId();

    // 组名-服务名组合键值的前缀
    String prefix() default StringUtils.EMPTY;

    // 组名-服务名组合键值的后缀
    String suffix() default StringUtils.EMPTY;

    // 执行配置的文件路径。测试用例运行前，会把该文件里的内容推送到远程配置中心或者服务
    String executePath();

    // 重置配置的文件路径。测试用例运行后，会把该文件里的内容推送到远程配置中心或者服务。该文件内容是最初的默认配置
    // 如果该注解属性为空，则直接删除从配置中心删除组名-服务名组合键值
    String resetPath() default StringUtils.EMPTY;
}
```

代码如下：

```java
public class MyTestCases {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-version-1.xml", resetPath = "gray-default.xml")
    public void testVersionStrategyGray(String group, String serviceId, String testUrl) {
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            int index = result.indexOf("[V=1.0]");
            int lastIndex = result.lastIndexOf("[V=1.0]");

            Assert.assertNotEquals(index, -1);
            Assert.assertNotEquals(lastIndex, -1);
            Assert.assertNotEquals(index, lastIndex);
        }
    }
}
```

灰度配置文件gray-strategy-version-1.xml的内容如下：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <strategy>
        <version>1.0</version>
    </strategy>
</rule>
```

灰度配置文件gray-default.xml的内容如下：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>

</rule>
```

#### 扩展调用测试

除了支持灰度自动化测试外，使用者可扩展出以远程配置中心内容做变更的自动化测试。以阿里巴巴的Sentinel为例子，测试实现方式如下：
- 远程配置中心约定，Apollo上Key的格式为{group}-{serviceId}-sentinel，Nacos上Group为代码中的{group}，Data ID为{serviceId}-{suffix}，即{serviceId}-sentinel
- 执行测试用例前，把执行限流降级熔断等逻辑的内容（executePath = "sentinel-test.xml"）推送到远程配置中心
- 执行测试用例，通过断言Assert来判断测试结果
- 执行测试用例后，把修改过的内容（resetPath = "sentinel-default.xml"）复原，再推送一次到远程配置中心

```java
public class MyTestCases {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @DTestConfig(group = "#group", serviceId = "#serviceId", suffix = "sentinel" executePath = "sentinel-test.xml", resetPath = "sentinel-default.xml")
    public void testSentinel(String group, String serviceId, String testUrl) {
        ...
    }
}
```

### 测试报告

- 路由策略测试报告样例
```
---------- Run automation testcase :: testNoGray() ----------
Result1 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result2 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result3 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result4 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
* Passed
---------- Run automation testcase :: testEnabledStrategyGray1() ----------
Header : [mobile:"138"]
Result1 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result2 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result3 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result4 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
* Passed
---------- Run automation testcase :: testVersionStrategyGray() ----------
Result1 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result2 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result3 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result4 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
* Passed
---------- Run automation testcase :: testRegionStrategyGray() ----------
Result1 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result2 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result3 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result4 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
* Passed
---------- Run automation testcase :: testVersionWeightStrategyGray() ----------
Sample count=3000
Weight result offset desired=5%
A service desired : 1.0 version weight=90%, 1.1 version weight=10%
B service desired : 1.0 version weight=20%, 1.1 version weight=80%
Result : A service 1.0 version weight=89.6%
Result : A service 1.1 version weight=10.4%
Result : B service 1.0 version weight=20.1333%
Result : B service 1.1 version weight=79.8667%
* Passed
---------- Run automation testcase :: testRegionWeightStrategyGray() ----------
Sample count=3000
Weight result offset desired=5%
A service desired : dev region weight=85%, qa region weight=15%
B service desired : dev region weight=85%, qa region weight=15%
Result : A service dev region weight=83.7667%
Result : A service qa region weight=16.2333%
Result : B service dev region weight=86.2%
Result : B service qa region weight=13.8%
* Passed
```

- 路由规则测试报告样例
```
---------- Run automation testcase :: testStrategyCustomizationGray() ----------
Header : [a:"1", b:"2"]
Result1 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result2 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result3 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result4 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
* Passed
---------- Run automation testcase :: testVersionRuleGray() ----------
Result1 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result2 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result3 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result4 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
* Passed
---------- Run automation testcase :: testRegionRuleGray() ----------
Result1 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result2 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result3 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result4 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
* Passed
---------- Run automation testcase :: testVersionWeightRuleGray() ----------
Sample count=3000
Weight result offset desired=5%
A service desired : 1.0 version weight=75%, 1.1 version weight=25%
B service desired : 1.0 version weight=35%, 1.1 version weight=65%
Result : A service 1.0 version weight=75.2667%
Result : A service 1.1 version weight=24.7333%
Result : B service 1.0 version weight=35.1667%
Result : B service 1.1 version weight=64.8333%
* Passed
---------- Run automation testcase :: testRegionWeightRuleGray() ----------
Sample count=3000
Weight result offset desired=5%
A service desired : dev region weight=95%, qa region weight=5%
B service desired : dev region weight=95%, qa region weight=5%
Result : A service dev region weight=94.9333%
Result : A service qa region weight=5.0667%
Result : B service dev region weight=95.0667%
Result : B service qa region weight=4.9333%
* Passed
---------- Run automation testcase :: testVersionCompositeRuleGray() ----------
Sample count=3000
Weight result offset desired=5%
A service desired : 1.0 version weight=40%, 1.1 version weight=60%
Route desired : A Service 1.0 version -> B Service 1.0 version, A Service 1.1 version -> B Service 1.1 version
Result : A service 1.0 version weight=39.8333%
A service 1.1 version weight=60.1667%
* Passed
```

## 压力测试

### 测试环境
- 准备两台机器部署Spring Cloud应用
- 准备一台机器部署网关（Spring Cloud或者Zuul）
- 准备一台机器部署压测工具

### 测试介绍
- 使用wrk脚本进行性能测试，wrk脚本示例，请参考[压测脚本](https://github.com/Nepxion/DiscoveryGuide/tree/master/discovery-guide-test-automation/post.lua)，下面的测试命令行可以不必带脚本参数
- 使用wrk详细说明参考[https://github.com/wg/wrk](https://github.com/wg/wrk)

### 测试步骤
- 登录到wrk的机器，进入wrk目录
- 运行命令 wrk -t64 -c2000 -d30s -H "id: 123" -H "token: abc" --timeout=2s --latency --script=post.lua http://localhost:5001/discovery-guide-service-a/invoke/gateway
```
使用方法: wrk <选项> <被测HTTP服务的URL>
  Options:
    -c, --connections 跟服务器建立并保持的TCP连接数量
    -d, --duration    压测时间。例如：2s，2m，2h
    -t, --threads     使用多少个线程进行压测
    -s, --script      指定Lua脚本路径
    -H, --header      为每一个HTTP请求添加HTTP头。例如：-H "id: 123" -H "token: abc"，冒号后面要带空格
        --latency     在压测结束后，打印延迟统计信息
        --timeout     超时时间
```
- 等待结果，Requests/sec 表示每秒处理的请求数

## Star走势图

[![Stargazers over time](https://starchart.cc/Nepxion/Discovery.svg)](https://starchart.cc/Nepxion/Discovery)