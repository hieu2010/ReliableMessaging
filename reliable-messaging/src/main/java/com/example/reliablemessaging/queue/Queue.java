package com.example.reliablemessaging.queue;

import com.example.reliablemessaging.entities.MongoQueue;
import com.example.reliablemessaging.repo.QueueRepo;
import com.example.reliablemessaging.repo.TaskRepo;
import com.example.reliablemessaging.entities.MongoTask;
import com.example.reliablemessaging.task.TaskCreator;
import com.example.reliablemessaging.task.TaskState;
import com.example.reliablemessaging.task.WeatherSensorData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Queue implements QueueSystem {

    private static final Logger LOGGER = LogManager.getLogger(Queue.class);

    private final MongoTemplate mongoTemplate;
    private final TaskCreator taskCreator;
    private final QueueRepo queueRepo;
    private final TaskRepo taskRepo;

    @Autowired
    public Queue(MongoTemplate mongoTemplate,
                 TaskCreator taskCreator,
                 QueueRepo queueRepo,
                 TaskRepo taskRepo) {
        this.mongoTemplate = mongoTemplate;
        this.taskCreator = taskCreator;
        this.queueRepo = queueRepo;
        this.taskRepo = taskRepo;

    }

    @Override
    public void ensureQueueExists(String queueName) {
        // try to find a queue
        Optional<MongoQueue> mongoQueue = queueRepo.findById(queueName);
        if (mongoQueue.isEmpty()) {
            MongoQueue newQueue = new MongoQueue();
            newQueue.setQueue(queueName);
            queueRepo.save(newQueue);
        }
    }

    @Override
    public List<String> createTask(String queueName, WeatherSensorData sensorData) {
        return createTasks(queueName, Collections.singletonList(sensorData));
    }

    @Override
    public List<String> createTasks(String queueName, List<WeatherSensorData> weatherSensorData) {
        LOGGER.info("Creating tasks for {} weather data units", weatherSensorData.size());
        while (true) {
            try {
                List<String> createdTasksIds = createTasksInTransaction(
                        queueName,
                        weatherSensorData
                );
                return createdTasksIds;
            } catch (Exception e) {
                LOGGER.info("The task creation process was not successful. " +
                        "Reason: {}. Retrying...", e.getMessage());
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<String> createTasksInTransaction(String queueName, List<WeatherSensorData> weatherSensorData) {
        List<MongoTask> mongoTasks = new ArrayList<>();
        for (WeatherSensorData singleSensorReading : weatherSensorData) {
            MongoTask task = taskCreator.createMongoTask(1, singleSensorReading);
            mongoTasks.add(task);
        }
        taskRepo.saveAll(mongoTasks);
        return mongoTasks.stream().map(MongoTask::getTaskId).collect(Collectors.toList());

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public synchronized List<MongoTask> getAndStartTasks(String queueName, int maxCount) {
        List<MongoTask> notStartedTasks = taskRepo.findAllStartable()
                .stream()
                .sorted((t1, t2) -> Integer.compare(t2.getPriority(), t1.getPriority()))
                .collect(Collectors.toList());

        List<MongoTask> toBeStarted = new ArrayList<>();
        for (int i=0; i<maxCount&&i<notStartedTasks.size(); i++) {
            toBeStarted.add(notStartedTasks.get(i));
        }
        // start the tasks
        List<MongoTask> startedTasks = new ArrayList<>();
        for (MongoTask mongoTask : toBeStarted) {
            mongoTask.setState(TaskState.RUNNING);
            startedTasks.add(mongoTask);
        }
        taskRepo.updateAll(toBeStarted);
        return startedTasks;
    }
}
