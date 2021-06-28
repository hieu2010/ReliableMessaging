package com.example.relmes.commons.repo;

import com.example.relmes.datageneration.documents.MongoCommand;

import java.util.List;

public interface ServerCommandRepoCustom {

    void addCommandLogEntry(MongoCommand command);
    List<MongoCommand> getLastXCommandLogEntries(short count);
}
