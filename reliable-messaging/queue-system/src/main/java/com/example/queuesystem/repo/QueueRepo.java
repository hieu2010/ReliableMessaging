package com.example.queuesystem.repo;

import com.example.queuesystem.entities.MongoQueue;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QueueRepo extends MongoRepository<MongoQueue, String> {
}
