package com.example.cloudservermock.controller;

import com.example.cloudservermock.data.Weather;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import com.example.cloudservermock.repo.ServerWeatherRepo;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CloudController {

    private static final Logger LOGGER = LogManager.getLogger(CloudController.class);
    private static boolean started = true;

    @Autowired
    ServerWeatherRepo weatherRepo;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<String> answer(@RequestBody String dataAsCsv) {
        if (started) {
            // TODO:
            //  create a jar that sets up the server-weather collection instead
            weatherRepo.create();
            started = false;
        }
        // Load csv and remove duplicates
        String[] lines = dataAsCsv.split("\\n");
        LOGGER.info("Data received: {}", lines.length);
        int duplicateCount = 0;
        int addedCount = 0; // is/will this (be) needed?
        List<Weather> weatherList = new ArrayList<>();
        for(String s : lines) {
            Weather data = Weather.csvToString(s);
            if(weatherRepo.findOneById(data.getMeasurementId()) == null) {
                weatherList.add(data);
            } else {
                duplicateCount++;
            }
        }
        weatherRepo.saveAll(weatherList);
        return Mono.just("Length: " + dataAsCsv.length() + ", saved: " + weatherList.size() + ", duplicates: " + duplicateCount);
    }

    @GetMapping("/visualization")
    public List<Weather> health() {
        return weatherRepo.getWeatherData();
    }

}
