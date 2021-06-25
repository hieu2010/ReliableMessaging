package com.example.cloudservermock.controller;

import com.example.cloudservermock.data.Weather;
import com.mongodb.client.result.DeleteResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import com.example.cloudservermock.repo.ServerWeatherRepo;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CloudController {

    private static final Logger LOGGER = LogManager.getLogger(CloudController.class);

    @Autowired
    ServerWeatherRepo weatherRepo;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<String> answer(@RequestBody String dataAsCsv) {
        LOGGER.info("Received: {}", dataAsCsv.length());
        String[] lines = dataAsCsv.split("\\n");

        int duplicateCount = 0;
        int deleteCount = 0;
        int addedCount = 0;
        List<Weather> weatherList = new ArrayList<Weather>();
        for(String s : lines) {

            Weather data = Weather.csvToString(s);
            if(weatherRepo.findOneByTime_Local(data.getTime_local()) == null) {
                weatherList.add(data);
            } else {
                LOGGER.info("Line: {}", weatherRepo.findOneByTime_Local(data.getTime_local()));
                duplicateCount++;
            }
        }
        weatherRepo.saveAll(weatherList);
        int entryCount = (int) weatherRepo.count();
        if( entryCount > 200) {
            entryCount -= 200;
            DeleteResult dr = weatherRepo.removeFirstX(entryCount);
            deleteCount = (int) dr.getDeletedCount();
        }
        return Mono.just("Data length: " + String.valueOf(dataAsCsv.length() + "\nData deleted: " + deleteCount + "\nData saved: " + weatherList.size() + "\nDuplicates: " + duplicateCount));
    }

}
