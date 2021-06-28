package com.example.cloudservermock.repo;

import com.example.cloudservermock.command.Command;
import com.example.cloudservermock.command.MongoCommand;
import com.example.cloudservermock.data.Weather;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Repository
public class ServerWeatherRepoCustomImpl implements ServerWeatherRepoCustom {

    // collections
    private static final String SERVER_WEATHER = "server-weather";
    private static final String SERVER_WEATHER_LOG = "server-weather-log";

    private final long MAXDOCUMENT = 100;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ServerWeatherRepoCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Weather> getLast(int maxCount) {
        // To display latest weather data
        return mongoTemplate.find(new Query().limit(maxCount), Weather.class, SERVER_WEATHER);
    }

    @Override
    public Weather findOneById(String measurementId) {
        // To check for duplicates
        Query idQuery = new Query().addCriteria(Criteria.where("measurementId").is(measurementId));
        return mongoTemplate.findOne(idQuery, Weather.class, SERVER_WEATHER);
    }

    @Override
    public void create() {
        if (!mongoTemplate.getCollectionNames().contains(SERVER_WEATHER)) {
            // Create a capped collection that can only contain #MAXDOCUMENT documents
            CollectionOptions options = CollectionOptions.empty()
                    .capped()
					.size(5242880)
                    .maxDocuments(MAXDOCUMENT);
            mongoTemplate.createCollection(SERVER_WEATHER, options);
        } else {
            // For older versions of the server: drop the non-capped collection
            MongoCollection<Document> collection = mongoTemplate.getCollection(SERVER_WEATHER);
            if(collection.countDocuments() > MAXDOCUMENT) {
                collection.drop();
                create();
            }
        }
    }

    @Override
    public List<Weather> getNoOlderThan(short min) {
        Instant expired = Instant.now().minus(2, ChronoUnit.SECONDS);
        return mongoTemplate.find(Query.query(
                Criteria.where("time").lte(expired)
        ), Weather.class, SERVER_WEATHER);
    }

    @Override
    public List<Weather> getWeatherData() {
        return mongoTemplate.find(Query.query(
                Criteria.where("temp").gt(-100)
        ), Weather.class, SERVER_WEATHER);
    }

    @Override
    public void addWeatherLogEntries(List<Weather> weatherData) {
        weatherData.forEach(value -> mongoTemplate.save(value, SERVER_WEATHER_LOG));
    }

    @Override
    public List<Weather> getWeatherLogEntries() {
        return mongoTemplate.find(Query.query(
                Criteria.where("temp").gt(-100)
        ), Weather.class, SERVER_WEATHER_LOG);
    }
}