package com.example.cloudservermock.repo;

import com.example.cloudservermock.data.Weather;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    private final long MAXDOCUMENT = 100;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ServerWeatherRepoCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Weather> getLastX(int maxCount) {
        // To display latest weather data
        return mongoTemplate.find(new Query().limit(maxCount), Weather.class, "server-weather");
    }

    @Override
    public Weather findOneByTime(Instant time) {
        // To check for duplicates
        Query idQuery = new Query().addCriteria(Criteria.where("time").is(time));
        return mongoTemplate.findOne(idQuery, Weather.class, "server-weather");
    }

    @Override
    public void create() {
        if (!mongoTemplate.getCollectionNames().contains("server-weather")) {
            // Create a capped collection that can only contain #MAXDOCUMENT documents
            CollectionOptions options = CollectionOptions.empty()
                    .capped()
					.size(5242880)
                    .maxDocuments(MAXDOCUMENT);
            mongoTemplate.createCollection("server-weather", options);
        } else {
            // For older versions of the server: drop the non-capped collection
            MongoCollection<Document> collection = mongoTemplate.getCollection("server-weather");
            if(collection.countDocuments() > MAXDOCUMENT) {
                collection.drop();
                create();
            }
        }
    }

    @Override
    public List<Weather> getNoOlderThan(short min) {
        Instant expired = Instant.now().minus(2, ChronoUnit.MINUTES);
        return mongoTemplate.find(Query.query(
                Criteria.where("localTimeInstant").gt(expired)
        ), Weather.class, "server-weather");
    }

    @Override
    public List<Weather> getWeatherData() {
        return mongoTemplate.find(Query.query(
                Criteria.where("temp").gt(-100)
        ), Weather.class, "server-weather");
    }
}