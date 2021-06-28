package com.example.cloudservermock.repo;

import com.example.cloudservermock.command.MongoCommand;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ServerCommandRepo extends MongoRepository<MongoCommand, String>, ServerCommandRepoCustom {
}
