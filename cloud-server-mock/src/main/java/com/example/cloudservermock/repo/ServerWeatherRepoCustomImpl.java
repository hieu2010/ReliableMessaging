package com.example.cloudservermock.repo;

import com.example.cloudservermock.data.Weather;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ServerWeatherRepoCustomImpl implements ServerWeatherRepoCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ServerWeatherRepoCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Weather> getLastX(int maxCount) {
        return mongoTemplate.find(new Query().limit(maxCount), Weather.class);
    }

    @Override
    public Weather findOneByTime_Local(String time_local) {
        Query idQuery = new Query();
        idQuery.addCriteria(Criteria.where("time_local").is(time_local));

        return mongoTemplate.findOne(idQuery, Weather.class, "server-weather");
    }

    @Override
    public DeleteResult removeFirstX(int maxCount) {
        return mongoTemplate.remove(new Query().limit(maxCount), "server-weather");
    }
}