package com.nepxion.discovery.guide.service.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.google.common.eventbus.Subscribe;
import com.nepxion.discovery.plugin.framework.event.RuleClearedEvent;
import com.nepxion.discovery.plugin.framework.event.RuleFailureEvent;
import com.nepxion.discovery.plugin.framework.event.RuleUpdatedEvent;
import com.nepxion.discovery.plugin.strategy.event.StrategyAlarmEvent;
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
    public void onAlarm(StrategyAlarmEvent strategyAlarmEvent) {
        System.out.println("========== 告警类型=" + strategyAlarmEvent.getAlarmType());
        System.out.println("========== 告警内容=" + strategyAlarmEvent.getAlarmMap());
    }
}