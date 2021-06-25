package com.example.relmes.datadelivery.delivery;

import com.example.relmes.commons.repo.WeatherRepo;
import com.example.relmes.datageneration.documents.MongoWeather;
import com.example.relmes.datageneration.generator.DataGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

@Component
public class DeliveryTaskWrapper {

    private static final Logger LOGGER = LogManager.getLogger(DeliveryTaskWrapper.class);

    private static final short POLL_INTERVAL = 10000;
    private static final short MAX_POLL_COUNT = 100;

    public static boolean pauseTask = false;

    private ScheduledFuture state;

    private TaskScheduler taskScheduler;
    private WeatherRepo weatherRepo;
    private WebClient webClient;

    @Autowired
    public DeliveryTaskWrapper(TaskScheduler taskScheduler,
                               WeatherRepo weatherRepo,
                               WebClient webClient) {
        this.taskScheduler = taskScheduler;
        this.weatherRepo = weatherRepo;
        this.webClient = webClient;
    }

    @PostConstruct
    public void init() {
        start();
    }

    class DeliveryTask implements Runnable {
        @Override
        public void run() {
            if(!DeliveryTaskWrapper.pauseTask) {
                List<MongoWeather> data = weatherRepo.getLastX(MAX_POLL_COUNT);
                final StringBuilder builder = new StringBuilder();
                data.forEach(val -> {
                    builder.append(val.toString());
                });

                LOGGER.info("Pulled {} entries", data.size());
                String answer = webClient
                        .post()
                        .body(BodyInserters.fromValue(builder.toString()))
                        .retrieve()
                        .bodyToMono(String.class)
                        .block(); // should it block?
                LOGGER.info("Answer from the server: {}", answer);
                if(false) {
                    DeliveryTaskWrapper.pauseTask = true;
                }
            }
        }
    }


    public void start() {
        state = taskScheduler.scheduleAtFixedRate(
                new DeliveryTask(),
                POLL_INTERVAL
        );
    }

    public void stop() {
        state.cancel(false);
    }
}
