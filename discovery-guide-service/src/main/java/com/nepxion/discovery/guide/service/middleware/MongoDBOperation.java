package com.nepxion.discovery.guide.service.middleware;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

@Component
public class MongoDBOperation {

	private static final Logger LOG = LoggerFactory.getLogger(MongoDBOperation.class);

	@Autowired
    private MongoTemplate mongoTemplate;

    public void invokeMongodb() {
        String group = "MyGroup";
        String dataId = "MyDataId";
        String message = "MyMessage";
        
        MongoDBEntity mongodbEntity = new MongoDBEntity();
        mongodbEntity.setGroup(group);
        mongodbEntity.setDataId(dataId);
        mongodbEntity.setMessage(message);
        mongoTemplate.save(mongodbEntity);
        
        LOG.info("Mongodb save, group={}, dataId={}, message={}", group, dataId, message);

        Criteria criteria = Criteria.where("group").is(group);
        Query query = Query.query(criteria);
        List<MongoDBEntity> mongodbEntitys = mongoTemplate.find(query, MongoDBEntity.class);

        LOG.info("Mongodb get, group={}, dataId={}, result={}", group, dataId, mongodbEntitys);
    }
    
}