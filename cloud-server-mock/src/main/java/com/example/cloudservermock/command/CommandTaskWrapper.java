package com.example.cloudservermock.command;

import com.example.cloudservermock.data.Weather;
import com.example.cloudservermock.repo.ServerCommandRepo;
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

    public static boolean success = false;
    public static boolean isRunning = true;

    private ScheduledFuture state;

    private TaskScheduler taskScheduler;
    private ServerWeatherRepo weatherRepo;
    private ServerCommandRepo commandRepo;
    private WebClient webClient;

    private static Command prevCommand = Command.HUMIDITY_AND_TEMPERATURE_OK;
    @Autowired
    public CommandTaskWrapper(TaskScheduler taskScheduler,
                               ServerWeatherRepo weatherRepo,
                               ServerCommandRepo commandRepo,
                               WebClient webClient) {
        this.taskScheduler = taskScheduler;
        this.weatherRepo = weatherRepo;
        this.webClient = webClient;
        this.commandRepo = commandRepo;
    }

    @PostConstruct
    public void init() {
        start();
    }

    class CommandTask implements Runnable {
        @Override
        public void run() {
            success = false;
            // First, get data from the DB that is no older than x
            List<Weather> data = weatherRepo.getNoOlderThan(NO_OLDER_THAN_MIN);
            // Watch out for 'cold-start'
            if (data.isEmpty()) {
                return;
            }
            // Second, create a command for the local component
            // according to the values in data
            Command commandForTheLocalComponent = prepareInstructionForLocalComp(data);
            if(commandForTheLocalComponent != prevCommand) {
                LOGGER.info("Command changed to: {}", commandForTheLocalComponent);
                prevCommand = commandForTheLocalComponent;
                commandRepo.save(MongoCommand.convert(commandForTheLocalComponent));
                // Third, make a POST request
                do {
                    try {
                        // just fire and forget
                         webClient
                                .post()
                                .body(BodyInserters.fromValue(commandForTheLocalComponent.toString()))
                                .retrieve()
                                .bodyToMono(Void.class)
                                .block();

                        success = true;
                        // TODO: Add command into a collection
                        // Dawid: I moved this above, before the if
                        if (!isRunning) {
                            start();
                        }
                    } catch (Exception e) {
                        LOGGER.info("Server error. Message: {}", e.getMessage());
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
                LOGGER.info("Transferring to the Local Component successful.");
            } else {
                LOGGER.info("No new command ({})", prevCommand);
            }
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
        isRunning = true;
        state = taskScheduler.scheduleAtFixedRate(
                new CommandTask(),
                POLL_INTERVAL
        );
    }

    public void stop() {
        isRunning = false;
        state.cancel(false);
    }
}
