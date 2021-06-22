package com.example.reliablemessaging.task;

import com.example.reliablemessaging.entities.MongoTask;

public interface DataProcessor {

    void process(MongoTask task);
}
