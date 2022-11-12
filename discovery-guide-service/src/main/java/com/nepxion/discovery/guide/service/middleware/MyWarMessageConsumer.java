package com.nepxion.discovery.guide.service.middleware;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.plugin.strategy.ware.entity.WareMessage;
import com.nepxion.discovery.plugin.strategy.ware.message.WareMessageConsumer;

public class MyWarMessageConsumer implements WareMessageConsumer {
    @Override
    public void consume(WareMessage wareMessage) throws Exception {
        System.out.println("===== 收到转发消息，来源：" + wareMessage.getType() + "，内容：" + wareMessage.getObject());
    }
}