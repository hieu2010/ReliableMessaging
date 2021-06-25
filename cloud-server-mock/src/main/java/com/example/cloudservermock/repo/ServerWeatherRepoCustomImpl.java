package com.example.cloudservermock.repo;

import com.example.cloudservermock.data.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
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
}