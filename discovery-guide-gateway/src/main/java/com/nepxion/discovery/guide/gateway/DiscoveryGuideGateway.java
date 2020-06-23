package com.nepxion.discovery.guide.gateway;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.nepxion.discovery.guide.gateway.impl.MyDiscoveryEnabledStrategy;
import com.nepxion.discovery.guide.gateway.impl.MyStrategyTracerAdapter;
import com.nepxion.discovery.plugin.strategy.adapter.DiscoveryEnabledStrategy;
import com.nepxion.discovery.plugin.strategy.adapter.StrategyTracerAdapter;

@SpringBootApplication
@EnableDiscoveryClient
public class DiscoveryGuideGateway {
    public static void main(String[] args) {
        // 彩色旗标显示设置
        System.setProperty("nepxion.banner.shown.ansi.mode", "true");

        new SpringApplicationBuilder(DiscoveryGuideGateway.class).run(args);
    }

    // ========== 下面的Bean配置以及impl目录下的类都是高级应用，可以全部删除 ==========
    // 自定义负载均衡的灰度策略
    @Bean
    public DiscoveryEnabledStrategy discoveryEnabledStrategy() {
        return new MyDiscoveryEnabledStrategy();
    }

    // 自定义灰度路由策略
    /*@Bean
    public GatewayStrategyRouteFilter gatewayStrategyRouteFilter() {
        return new MyGatewayStrategyRouteFilter();
    }*/

    // 自定义调用链上下文参数
    @Bean
    public StrategyTracerAdapter strategyTracerAdapter() {
        return new MyStrategyTracerAdapter();
    }

    // 自定义环境路由
    /*@Bean
    public EnvironmentRouteAdapter environmentRouteAdapter() {
        return new MyEnvironmentRouteAdapter();
    }*/

    // 防止多个网关上并行实时灰度发布产生混乱，对处于非灰度发布状态的服务，调用它的时候，只取它的老的稳定版本的实例
    /*@Bean
    public StrategyVersionFilterAdapter strategyVersionFilterAdapter() {
        return new DefaultStrategyVersionFilterAdapter(); 
    }

    // 防止多个网关上并行实时灰度发布产生混乱，对处于非灰度发布状态的服务，调用它的时候，只取它的给定的区域的实例
    @Bean
    public StrategyRegionFilterAdapter strategyRegionFilterAdapter() {
        return new StrategyRegionFilterAdapter() {
            @Override
            public List<String> filter(List<String> regionList) {
                return Arrays.asList("dev");
            }
        };
    }*/
}