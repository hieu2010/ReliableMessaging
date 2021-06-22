package com.example.reliablemessaging.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("queue")
public class MongoQueue {

    @Id
    private String queue;

    public MongoQueue() {
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }
}
