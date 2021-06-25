package com.example.cloudservermock.controller;

import com.example.cloudservermock.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class CloudController {

    private static final Logger LOGGER = LogManager.getLogger(CloudController.class);

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<Integer> answer(@RequestBody String dataAsCsv) {
        LOGGER.info("Received: {}", dataAsCsv);
        return Mono.just(dataAsCsv.length());
    }

}
