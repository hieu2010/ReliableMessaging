package com.example.reliablemessaging.task;

import com.example.reliablemessaging.entities.MongoTask;
import com.example.reliablemessaging.queue.Queue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class WeatherDataProcessor implements DataProcessor<MongoTask> {

    private static final Logger LOGGER = LogManager.getLogger(WeatherDataProcessor.class);

    @Override
    public void process(TaskWrapper task) {
        LOGGER.info("Processing with coco: {}", task.getTaskProps().get("coco"));
    }
}
