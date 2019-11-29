package com.nepxion.discovery.guide.service.impl;

import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;


/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zhang shun
 * @version 1.0
 */
public class SentinelTracer {


    public static Tracer getTracer(){
        return GlobalTracer.get();
    }

}
