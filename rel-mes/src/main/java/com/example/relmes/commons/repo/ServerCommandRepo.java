package com.example.relmes.commons.repo;

import com.example.relmes.commons.repo.ServerCommandRepoCustom;
import com.example.relmes.datageneration.documents.MongoCommand;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ServerCommandRepo extends MongoRepository<MongoCommand, String>, ServerCommandRepoCustom {
}
