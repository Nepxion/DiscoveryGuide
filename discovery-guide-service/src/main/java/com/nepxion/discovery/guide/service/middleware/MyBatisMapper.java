package com.nepxion.discovery.guide.service.middleware;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.ibatis.annotations.Select;

public interface MyBatisMapper {
    @Select("SELECT COUNT(*) FROM permission")
    int count();
}