package com.example.reliablemessaging.task;

import com.example.reliablemessaging.entities.MongoTask;

@FunctionalInterface
public interface DataProcessor {

    void process(TaskWrapper task); // for now this is a mongo task, but this should be abstract for a general task

}
