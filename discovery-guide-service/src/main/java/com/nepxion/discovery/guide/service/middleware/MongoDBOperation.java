package com.nepxion.discovery.guide.service.middleware;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Ankeway
 * @version 1.0
 */

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@Configuration
public class MongoDBOperation {
    private static final Logger LOG = LoggerFactory.getLogger(MongoDBOperation.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    public void operate() {
        String group = "MyGroup";
        String dataId = "MyDataId";
        String message = "MyMessage";

        MongoDBEntity mongoDBEntity = new MongoDBEntity();
        mongoDBEntity.setGroup(group);
        mongoDBEntity.setDataId(dataId);
        mongoDBEntity.setMessage(message);
        mongoTemplate.save(mongoDBEntity);

        LOG.info("::::: MongoDB saved, group={}, dataId={}, message={}", group, dataId, message);

        Criteria criteria = Criteria.where("group").is(group);
        Query query = Query.query(criteria);
        List<MongoDBEntity> mongoDBEntityList = mongoTemplate.find(query, MongoDBEntity.class);

        LOG.info("::::: MongoDB got, group={}, dataId={}, result={}", group, dataId, mongoDBEntityList);
    }
}