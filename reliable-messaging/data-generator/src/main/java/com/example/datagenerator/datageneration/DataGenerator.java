package com.example.datagenerator.datageneration;


import com.example.commons.util.CsvReader;
import com.example.commons.weathersensor.Weather;
import com.example.queuesystem.queue.QueueSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class DataGenerator {

    private static final String WEATHER_QUEUE = "task"; //this should be changes later because its
    // kinda misleading

    private static final Random RAND = new Random();

    private final CsvReader weatherReader = new CsvReader("data.csv", ",");

    private final QueueSystem queue;

    @Autowired
    public DataGenerator(QueueSystem queue) {
        this.queue = queue;
    }

    @Scheduled(fixedRate = 3000)
    public void produceWeatherData() {
        // First, produce random weather data from a csv file
        String[] rawWeather = weatherReader.getRow(
                RAND.nextInt(weatherReader.getNumberOfDataEntries() - 1) + 1);
        Weather randomWeatherMeasurement = Weather.createWeather(rawWeather);
        // Second, create tasks out of data and send to mongo
        queue.createTask(WEATHER_QUEUE, randomWeatherMeasurement);
    }

    @Scheduled(fixedRate = 3500)
    public void produceX() {

    }


}
