package com.example.relmes.localserver;

import com.example.relmes.commons.repo.WeatherRepo;
import com.example.relmes.commons.repo.ServerCommandRepo;
import com.example.relmes.datageneration.documents.MongoCommand;
import com.example.relmes.datageneration.documents.MongoWeather;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LocalServer {

    private static final Logger LOGGER = LogManager.getLogger(LocalServer.class);

    @Autowired
    WeatherRepo weatherRepo;

    @Autowired
    ServerCommandRepo commandRepo;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void getAnswerFromTheCloud(@RequestBody String order) {
        LOGGER.info("Received the following order from the Cloud: {}", order);
        commandRepo.save(MongoCommand.strConvert(order));
    }

    @GetMapping("/getWeatherLog")
    public List<MongoWeather> getWeatherLog() {
        return weatherRepo.getLogEntries();
    }
}
