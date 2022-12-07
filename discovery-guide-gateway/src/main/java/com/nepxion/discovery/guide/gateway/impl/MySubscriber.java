package com.nepxion.discovery.guide.gateway.impl;

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

import com.google.common.eventbus.Subscribe;
import com.nepxion.discovery.plugin.framework.event.RuleClearedEvent;
import com.nepxion.discovery.plugin.framework.event.RuleFailureEvent;
import com.nepxion.discovery.plugin.framework.event.RuleUpdatedEvent;
import com.nepxion.discovery.plugin.strategy.event.StrategyAlarmEvent;
import com.nepxion.discovery.plugin.strategy.gateway.event.GatewayStrategyRouteAddedEvent;
import com.nepxion.discovery.plugin.strategy.gateway.event.GatewayStrategyRouteDeletedEvent;
import com.nepxion.discovery.plugin.strategy.gateway.event.GatewayStrategyRouteModifiedEvent;
import com.nepxion.discovery.plugin.strategy.gateway.event.GatewayStrategyRouteUpdatedAllEvent;
import com.nepxion.eventbus.annotation.EventBus;

@EventBus
public class MySubscriber {
    private static final Logger LOG = LoggerFactory.getLogger(MySubscriber.class);

    @Subscribe
    public void onRuleUpdated(RuleUpdatedEvent ruleUpdatedEvent) {
        LOG.info("规则执行更新, rule=" + ruleUpdatedEvent.getRule());
    }

    @Subscribe
    public void onRuleCleared(RuleClearedEvent ruleClearedEvent) {
        LOG.info("规则执行清空");
    }

    @Subscribe
    public void onRuleRuleFailure(RuleFailureEvent ruleFailureEvent) {
        LOG.info("规则更新失败, rule=" + ruleFailureEvent.getRule() + ", exception=" + ruleFailureEvent.getException());
    }

    @Subscribe
    public void onAlarm(StrategyAlarmEvent strategyAlarmEvent) {
        LOG.info("告警类型=" + strategyAlarmEvent.getAlarmType());
        LOG.info("告警内容=" + strategyAlarmEvent.getAlarmMap());
    }

    @Subscribe
    public void onGatewayStrategyRouteAdded(GatewayStrategyRouteAddedEvent gatewayStrategyRouteAddedEvent) {
        LOG.info("增加网关路由=" + gatewayStrategyRouteAddedEvent.getGatewayStrategyRouteEntity());
    }

    @Subscribe
    public void onGatewayStrategyRouteModified(GatewayStrategyRouteModifiedEvent gatewayStrategyRouteModifiedEvent) {
        LOG.info("修改网关路由=" + gatewayStrategyRouteModifiedEvent.getGatewayStrategyRouteEntity());
    }

    @Subscribe
    public void onGatewayStrategyRouteDeleted(GatewayStrategyRouteDeletedEvent gatewayStrategyRouteDeletedEvent) {
        LOG.info("删除网关路由=" + gatewayStrategyRouteDeletedEvent.getRouteId());
    }

    @Subscribe
    public void onGatewayStrategyRouteUpdatedAll(GatewayStrategyRouteUpdatedAllEvent gatewayStrategyRouteUpdatedAllEvent) {
        LOG.info("更新全部网关路由=" + gatewayStrategyRouteUpdatedAllEvent.getGatewayStrategyRouteEntityList());
    }
}