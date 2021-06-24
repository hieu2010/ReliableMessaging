package com.example.reliablemessaging.queue;

import com.example.reliablemessaging.entities.MongoTask;
import com.example.reliablemessaging.task.DataProcessor;
import com.example.reliablemessaging.task.TaskWrapper;
import com.example.reliablemessaging.weathersensor.Weather;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QueueSystem {

    // transactions

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void ensureQueueExists(String queueName);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    List<String> createTasksInTransaction(String queueName, List<Weather> weatherSensorData);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    List<MongoTask> getAndStartTasks(String queueName, int maxCount);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void updateTasks(String queueName);

    // this list contains a single string, but the method uses list for
    // conveniece reasons - usage of a single method in the implementation
    List<String> createTask(String queueName, Weather sensorData);
    List<String> createTasks(String queueName, List<Weather> sensorData);
    void markSuccess(String queueName, TaskWrapper task);

    void consume(DataProcessor processor);




}
