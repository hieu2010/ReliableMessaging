package com.example.cloudservermock.command;

import com.example.cloudservermock.data.Weather;
import com.example.cloudservermock.repo.ServerWeatherRepo;
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
public class CommandTaskWrapper {

    private static final Logger LOGGER = LogManager.getLogger(CommandTaskWrapper.class);

    // for home conditions
    private static final double HAZARDOUS_TEMP_START = 25;
    private static final double HAZARDOUS_HUM_START = 55;

    private static final short POLL_INTERVAL = 3000;
    private static final short NO_OLDER_THAN_MIN = 5;

    private ScheduledFuture state;
    private TaskScheduler taskScheduler;
    private ServerWeatherRepo weatherRepo;

    public static Command command = null;

    @Autowired
    public CommandTaskWrapper(TaskScheduler taskScheduler,
                               ServerWeatherRepo weatherRepo) {
        this.taskScheduler = taskScheduler;
        this.weatherRepo = weatherRepo;
    }

    @PostConstruct
    public void init() {
        start();
    }

    class CommandTask implements Runnable {
        @Override
        public void run() {
            // First, get data from the DB that is no older than x
            List<Weather> data = weatherRepo.getNoOlderThan(NO_OLDER_THAN_MIN);
            // Watch out for 'cold-start'
            if (!data.isEmpty()) {
                command = prepareInstructionForLocalComp(data);
            } else {
                command = Command.ERROR;
            }
            // LOGGER.info("Command: {}", command);
        }
    }

    private Command prepareInstructionForLocalComp(List<Weather> data) {

        double averageTemp = data.stream()
                .mapToDouble(Weather::getTemp)
                .average()
                .orElse(0d);

        double averageHum = data.stream()
                .mapToDouble(Weather::getRhum)
                .average()
                .orElse(0d);

        if (averageTemp > HAZARDOUS_TEMP_START && averageHum > HAZARDOUS_HUM_START) {
            return Command.HAZARDOUS_TEMPERATURE_AND_HUMIDITY;
        } else if (averageHum > HAZARDOUS_HUM_START) {
            return Command.REDUCE_HUMIDITY;
        } else if (averageTemp > HAZARDOUS_TEMP_START) {
            return Command.REDUCE_TEMPERATURE;
        } else {
            return Command.HUMIDITY_AND_TEMPERATURE_OK;
        }
    }

    public void start() {
        state = taskScheduler.scheduleAtFixedRate(
                new CommandTask(),
                POLL_INTERVAL
        );
    }
}
