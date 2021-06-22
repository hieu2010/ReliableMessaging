package com.example.queuesystem.queue;

import com.example.commons.weathersensor.Weather;
import com.example.queuesystem.entities.MongoTask;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QueueSystem {

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void ensureQueueExists(String queueName);

    @Transactional(propagation = Propagation.REQUIRED)
    List<String> createTasksInTransaction(String queueName, List<Weather> weatherSensorData);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    List<MongoTask> getAndStartTasks(String queueName, int maxCount);

    // this list contains a single string, but the method uses list for
    // conveniece reasons - usage of a single method in the implementation
    List<String> createTask(String queueName, Weather sensorData);
    List<String> createTasks(String queueName, List<Weather> sensorData);




}
