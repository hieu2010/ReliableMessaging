package com.example.cloudservermock.repo;

import com.example.cloudservermock.command.MongoCommand;

import java.util.List;

public interface ServerCommandRepoCustom {

    void addCommandLogEntry(MongoCommand command);
    List<MongoCommand> getLastXCommandLogEntries(short count);
}
