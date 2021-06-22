package com.example.datagenerator.datageneration;


import com.example.datagenerator.util.CsvReader;
import com.example.datagenerator.weathersensor.Weather;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class DataGenerator {

    private static final Random RAND = new Random();



    CsvReader weatherReader = new CsvReader("data.csv", ",");

    @Scheduled(fixedRate = 3000)
    public void produceWeatherData() {
        String[] rawWeather = weatherReader.getRow(
                RAND.nextInt(weatherReader.getNumberOfDataEntries() - 1) + 1);
        Weather randomWeatherMeasurement = Weather.createWeather(rawWeather);
    }

    @Scheduled(fixedRate = 3500)
    public void produceX() {

    }


}
