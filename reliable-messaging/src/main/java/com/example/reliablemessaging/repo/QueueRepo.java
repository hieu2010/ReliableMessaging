package com.example.reliablemessaging.repo;

import com.example.reliablemessaging.entities.MongoQueue;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QueueRepo extends MongoRepository<MongoQueue, String> {
}
