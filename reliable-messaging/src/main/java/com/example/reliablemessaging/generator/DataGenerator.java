package com.example.reliablemessaging.generator;




import com.example.reliablemessaging.queue.QueueSystem;
import com.example.reliablemessaging.utils.CsvReader;
import com.example.reliablemessaging.weathersensor.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class DataGenerator {

    private static final String WEATHER_QUEUE = "task"; //this should be changes later because its
    // kinda misleading

    private static final Random RAND = new Random();

    CsvReader weatherReader = new CsvReader("data", ",");

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
        // Second, ensure that the queue exists
        queue.ensureQueueExists(WEATHER_QUEUE);
        // Third, create tasks out of data and send to mongo
        queue.createTask(WEATHER_QUEUE, randomWeatherMeasurement);
    }


//
//    @Scheduled(fixedRate = 3500)
//    public void produceX() {
//
//    }


}
