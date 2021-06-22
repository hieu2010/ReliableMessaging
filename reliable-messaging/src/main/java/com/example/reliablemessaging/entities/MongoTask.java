package com.example.reliablemessaging.entities;

import com.example.reliablemessaging.task.TaskState;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Document("task")
public class MongoTask {

    @Id
    private String taskId;

    private Instant createdAt = null;
    private Instant finishedAt = null;

    private TaskState state;
    private int priority;
    private String err;
    private Map<String, Object> weatherSensorData = new HashMap<>();

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setFinishedAt(Instant finishedAt) {
        this.finishedAt = finishedAt;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public void setWeatherSensorData(Map<String, Object> weatherSensorData) {
        this.weatherSensorData = weatherSensorData;
    }

    public String getTaskId() {
        return taskId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getFinishedAt() {
        return finishedAt;
    }

    public TaskState getState() {
        return state;
    }

    public int getPriority() {
        return priority;
    }

    public String getErr() {
        return err;
    }

    public Map<String, Object> getWeatherSensorData() {
        return weatherSensorData;
    }
}
