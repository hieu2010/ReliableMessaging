package com.example.cloudservermock.controller;

import com.example.cloudservermock.data.Weather;
import com.mongodb.client.result.DeleteResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
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
        if(started) {
            // TODO:
            //  create a jar that sets up the server-weather collection instead
            weatherRepo.create();
            started = false;
        }
        // Load csv and remove duplicates
        String[] lines = dataAsCsv.split("\\n");
        LOGGER.info("Data received: {}", lines.length);
        int duplicateCount = 0;
        int addedCount = 0;
        List<Weather> weatherList = new ArrayList<Weather>();
        for(String s : lines) {
            Weather data = Weather.csvToString(s);
            if(weatherRepo.findOneByTime_Local(data.getTime_local()) == null) {
                weatherList.add(data);
            } else {
                duplicateCount++;
            }
        }
        weatherRepo.saveAll(weatherList);
        return Mono.just("Data length: " + String.valueOf(dataAsCsv.length() + "\nData saved: " + weatherList.size() + "\nDuplicates: " + duplicateCount));
    }

    @GetMapping("/health")
    public String health() {
        return "hello world";
    }

}
