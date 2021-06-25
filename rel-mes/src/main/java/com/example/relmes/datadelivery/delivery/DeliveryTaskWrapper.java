package com.example.relmes.datadelivery.delivery;

import com.example.relmes.commons.repo.WeatherRepo;
import com.example.relmes.datageneration.documents.MongoWeather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.ScheduledFuture;

@Component
public class DeliveryTaskWrapper {

    private static final short POLL_INTERVAL = 10000;
    private static final short MAX_POLL_COUNT = 100;

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

    class DeliveryTask implements Runnable {
        @Override
        public void run() {
            List<MongoWeather> data = weatherRepo.getLastX(MAX_POLL_COUNT);
            webClient
                    .post()
                    .body()

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
