package com.example.cloudservermock.controller;

import com.example.cloudservermock.data.Weather;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import com.example.cloudservermock.repo.ServerWeatherRepo;

@RestController
public class CloudController {

    private static final Logger LOGGER = LogManager.getLogger(CloudController.class);

    @Autowired
    ServerWeatherRepo weatherRepo;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<Integer> answer(@RequestBody String dataAsCsv) {
        LOGGER.info("Received: {}", dataAsCsv.length());
        String[] lines = dataAsCsv.split("\\n");
        for(String s : lines) {
            Weather data = Weather.csvToString(s);
            weatherRepo.save(data);
        }
        return Mono.just(dataAsCsv.length());
    }

}
