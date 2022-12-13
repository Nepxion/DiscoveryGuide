package com.nepxion.discovery.guide.config.processor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.nepxion.discovery.common.nacos.proccessor.NacosProcessor;

// 把继承类（extends）换成如下任何一个，即可切换配置中心，代码无需任何变动
// 1. NacosProcessor
// 2. ApolloProcessor
// 3. ConsulProcessor
// 4. EtcdProcessor
// 5. ZookeeperProcessor
// 6. RedisProcessor
// Group和DataId自行决定，需要注意
// 1. 对于Nacos、Redis、Zookeeper配置中心，Group和DataId需要和界面相对应
// 2. 对于Apollo、Consul、Etcd配置中心，Key的格式为Group-DataId
// 可以同时支持多个配置中心的订阅，需要同时创建多个不同的Processor，同时@Bean方式进入到Spring容器
public class MyConfigProcessor extends NacosProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(MyConfigProcessor.class);

    @Override
    public void beforeInitialization() {
        LOG.info("订阅器初始化之前，可以做一些工作");
    }

    @Override
    public void afterInitialization() {
        LOG.info("订阅器初始化之后，可以做一些工作");
    }

    @Override
    public String getGroup() {
        return "b";
    }

    @Override
    public String getDataId() {
        return "a";
    }

    @Override
    public String getDescription() {
        // description为日志打印显示而设置，作用是帮助使用者在日志上定位订阅器是否在执行
        return "My subscription";
    }

    @Override
    public void callbackConfig(String config) {
        // config为配置中心对应键值的内容变更，使用者可以根据此变更对业务模块做回调处理
        LOG.info("监听配置改变：config=" + config);
    }
}