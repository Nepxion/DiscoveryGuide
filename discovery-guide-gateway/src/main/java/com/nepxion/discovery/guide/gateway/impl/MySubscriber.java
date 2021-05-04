package com.nepxion.discovery.guide.gateway.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.google.common.eventbus.Subscribe;
import com.nepxion.discovery.plugin.framework.event.AlarmEvent;
import com.nepxion.discovery.plugin.framework.event.RuleClearedEvent;
import com.nepxion.discovery.plugin.framework.event.RuleFailureEvent;
import com.nepxion.discovery.plugin.framework.event.RuleUpdatedEvent;
import com.nepxion.discovery.plugin.strategy.gateway.event.GatewayStrategyRouteAddedEvent;
import com.nepxion.discovery.plugin.strategy.gateway.event.GatewayStrategyRouteDeletedEvent;
import com.nepxion.discovery.plugin.strategy.gateway.event.GatewayStrategyRouteModifiedEvent;
import com.nepxion.discovery.plugin.strategy.gateway.event.GatewayStrategyRouteUpdatedAllEvent;
import com.nepxion.eventbus.annotation.EventBus;

@EventBus
public class MySubscriber {
    @Subscribe
    public void onRuleUpdated(RuleUpdatedEvent ruleUpdatedEvent) {
        System.out.println("========== 规则执行更新, rule=" + ruleUpdatedEvent.getRule());
    }

    @Subscribe
    public void onRuleCleared(RuleClearedEvent ruleClearedEvent) {
        System.out.println("========== 规则执行清空");
    }

    @Subscribe
    public void onRuleRuleFailure(RuleFailureEvent ruleFailureEvent) {
        System.out.println("========== 规则更新失败, rule=" + ruleFailureEvent.getRule() + ", exception=" + ruleFailureEvent.getException());
    }

    @Subscribe
    public void onAlarm(AlarmEvent alarmEvent) {
        System.out.println("========== 告警类型=" + alarmEvent.getAlarmType());
        System.out.println("========== 告警内容=" + alarmEvent.getAlarmMap());
    }

    @Subscribe
    public void onGatewayStrategyRouteAdded(GatewayStrategyRouteAddedEvent gatewayStrategyRouteAddedEvent) {
        System.out.println("========== 增加网关路由=" + gatewayStrategyRouteAddedEvent.getGatewayStrategyRouteEntity());
    }

    @Subscribe
    public void onGatewayStrategyRouteModified(GatewayStrategyRouteModifiedEvent gatewayStrategyRouteModifiedEvent) {
        System.out.println("========== 修改网关路由=" + gatewayStrategyRouteModifiedEvent.getGatewayStrategyRouteEntity());
    }

    @Subscribe
    public void onGatewayStrategyRouteDeleted(GatewayStrategyRouteDeletedEvent gatewayStrategyRouteDeletedEvent) {
        System.out.println("========== 删除网关路由=" + gatewayStrategyRouteDeletedEvent.getRouteId());
    }

    @Subscribe
    public void onGatewayStrategyRouteUpdatedAll(GatewayStrategyRouteUpdatedAllEvent gatewayStrategyRouteUpdatedAllEvent) {
        System.out.println("========== 更新全部网关路由=" + gatewayStrategyRouteUpdatedAllEvent.getGatewayStrategyRouteEntityList());
    }
}