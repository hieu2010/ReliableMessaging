package com.example.relmes.datadelivery.delivery;

import com.example.relmes.commons.repo.WeatherRepo;
import com.example.relmes.datageneration.documents.MongoWeather;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

@Component
public class DeliveryTaskWrapper {

    private static final Logger LOGGER = LogManager.getLogger(DeliveryTaskWrapper.class);

    private static final short POLL_INTERVAL = 10000;
    private static final short MAX_POLL_COUNT = 5;

    public static boolean success = false;
    public static boolean isRunning = true;

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
                success = false;
                // First, get data from the DB
                List<MongoWeather> data = weatherRepo.getLastX(MAX_POLL_COUNT);
                // Watch out for 'cold-start'
                if (data.isEmpty()) {
                    return;
                }
                final String mergedWeatherData = mergeWeatherData(data);
                LOGGER.info("Pulled {} entries", data.size());
                // Second, make a POST request
                String answer = null;
                do {
                    try {
                        answer = webClient
                                .post()
                                .body(BodyInserters.fromValue(mergedWeatherData))
                                .retrieve()
                                .bodyToMono(String.class)
                                .block();

                        success = true;
                        if (!isRunning) {
                            start();
                        }
                    } catch (Exception e) {
                        LOGGER.info("Error msg {}", e.getMessage());
                        LOGGER.info("Server error.");
                        try {
                            // busy waiting
                            Thread.sleep(2000);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                        if (isRunning) {
                            LOGGER.info("Stopping the task");
                            stop();
                        }
                    }
                } while (!success);
                LOGGER.info("Answer from the server: {}", answer);
                LOGGER.info("Transferring to the Cloud successful. Deleting processed records.");
                // delete processed records from the db
                List<String> idsToRemove = data.stream()
                        .map(MongoWeather::getMeasurementId)
                        .collect(Collectors.toList());
                weatherRepo.deleteAllById(idsToRemove);
            }
    }

    private String mergeWeatherData(List<MongoWeather> data) {
        final StringBuilder builder = new StringBuilder();
        data.forEach(val -> {
            builder.append(val.toString());
        });
        return builder.toString();
    }


    public void start() {
        isRunning = true;
        state = taskScheduler.scheduleAtFixedRate(
                new DeliveryTask(),
                POLL_INTERVAL
        );
    }

    public void stop() {
        isRunning = false;
        state.cancel(false);
    }
}
