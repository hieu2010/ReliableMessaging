package com.example.reliablemessaging.task;

import com.example.reliablemessaging.entities.MongoTask;
import org.springframework.stereotype.Component;

@Component
public class WeatherDataProcessor implements DataProcessor {

    @Override
    public void process(MongoTask task) {
        // do sth with the weather data
        // save in mongo in a new collection

    }
}
