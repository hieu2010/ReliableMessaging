package com.example.relmes.localserver;

import com.example.relmes.datageneration.generator.DataGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocalServer {

    private static final Logger LOGGER = LogManager.getLogger(LocalServer.class);

    @PostMapping
    @ResponseStatus
    public void getAnswerFromTheCloud(@RequestBody String order) {
        LOGGER.info("Received the following order from the Cloud: {}", order);
    }


}
