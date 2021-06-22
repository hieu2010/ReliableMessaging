package com.example.queuesystem.task;

import com.example.queuesystem.entities.MongoTask;
import org.springframework.stereotype.Component;

@Component
public class WeatherDataProcessor implements DataProcessor {

    @Override
    public void process(MongoTask task) {
        // do sth with the weather data
        // save in mongo in a new collection

    }
}
