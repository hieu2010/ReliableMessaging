package com.example.relmes.generator;

import com.example.relmes.sensor.Weather;
import com.example.relmes.util.CsvReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DataGenerator {

    private static final short DATA_GENERATION_INTERVAL = 1000;

    private static final Random RAND = new Random();
    private final CsvReader weatherReader = new CsvReader("data.csv", ",");

    @Scheduled(fixedRate = DATA_GENERATION_INTERVAL)
    public void produceWeatherData() {
        // First, produce random weather data from a csv file
        String[] rawWeather = weatherReader.getRow(
                RAND.nextInt(weatherReader.getNumberOfDataEntries() - 1) + 1);
        Weather randomWeatherMeasurement = Weather.createWeather(rawWeather);
        // Second, save it to the mongo

    }

}
