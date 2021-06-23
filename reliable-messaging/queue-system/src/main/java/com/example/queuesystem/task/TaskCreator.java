package com.example.queuesystem.task;

import com.example.commons.weathersensor.Weather;
import com.example.queuesystem.entities.MongoTask;

import com.example.queuesystem.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;

@Component
public class TaskCreator {

    private final IdGenerator idGenerator;

    @Autowired
    public TaskCreator(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public MongoTask createMongoTask(int priority, Weather data) {

        MongoTask mongoTask = new MongoTask();
        mongoTask.setTaskId(idGenerator.generateId());
        //mongoTask.setTaskId("id1"); only for test
        mongoTask.setCreatedAt(Instant.now());
        mongoTask.setPriority(priority);
        mongoTask.setState(TaskState.CREATED);

        var sensorData = new HashMap<String, Object>(); //how will it be serialized??
        Integer coco = data.getCoco();
        String time = data.getTime();
        sensorData.put("coco", coco);
        sensorData.put("time", time);
        mongoTask.setWeatherSensorData(sensorData);

        return mongoTask;

    }
}
