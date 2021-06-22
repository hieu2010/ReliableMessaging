package com.example.queuesystem.task;

import java.time.Instant;
import java.util.Map;

public class Task {

    private String taskId;
    private Instant createdAt;
    private Map<String, Object> taskProps;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Map<String, Object> getTaskProps() {
        return taskProps;
    }

    public void setTaskProps(Map<String, Object> taskProps) {
        this.taskProps = taskProps;
    }
}
