package com.example.relmes.datageneration.generator;

import com.example.relmes.commons.repo.WeatherRepo;
import com.example.relmes.commons.sensor.Weather;
import com.example.relmes.datageneration.util.Converter;
import com.example.relmes.datageneration.util.CsvReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DataGenerator {

    private static final Logger LOGGER = LogManager.getLogger(DataGenerator.class);

    private static final short DATA_GENERATION_INTERVAL = 200;

    private static final Random RAND = new Random();
    private final CsvReader weatherReader = new CsvReader("data", ",");

    @Autowired
    WeatherRepo weatherRepo;

    @Scheduled(fixedRate = DATA_GENERATION_INTERVAL)
    public void produceWeatherData() {
        //LOGGER.info("Producing data...");
        // First, produce random weather data from a csv file
        String[] rawWeather = weatherReader.getRow(
                RAND.nextInt(weatherReader.getNumberOfDataEntries() - 1) + 1);
        Weather randomWeatherMeasurement = Weather.createWeather(rawWeather);
        // Second, save it to the mongo
        weatherRepo.save(Converter.convertMeasurement(randomWeatherMeasurement));
    }

}
