package com.example.relmes.commons.repo;

import com.example.relmes.datageneration.documents.MongoWeather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WeatherRepoCustomImpl implements WeatherRepoCustom {

    // collections
    private static final String LOCAL_WEATHER = "local-weather";
    private static final String LOCAL_WEATHER_LOG = "local-weather-log";

    private final MongoTemplate mongoTemplate;

    @Autowired
    public WeatherRepoCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<MongoWeather> getLast(int maxCount) {
        return mongoTemplate.find(new Query().limit(maxCount), MongoWeather.class);
    }

    @Override
    public List<MongoWeather> getLogEntries() {
        return mongoTemplate.find(Query.query(
                Criteria.where("temp").gt(-100)
        ), MongoWeather.class, LOCAL_WEATHER_LOG);
    }

    @Override
    public void addLogEntries(List<MongoWeather> data) {
        data.forEach(value -> mongoTemplate.save(value, LOCAL_WEATHER_LOG));
    }
}
