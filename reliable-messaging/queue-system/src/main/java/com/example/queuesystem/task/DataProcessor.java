package com.example.queuesystem.task;

import com.example.queuesystem.entities.MongoTask;

public interface DataProcessor {

    void process(MongoTask task);
}
