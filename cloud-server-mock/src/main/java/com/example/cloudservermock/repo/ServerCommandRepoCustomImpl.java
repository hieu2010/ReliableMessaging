package com.example.cloudservermock.repo;

import com.example.cloudservermock.command.MongoCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class ServerCommandRepoCustomImpl implements ServerCommandRepoCustom {

    private static final String SERVER_COMMAND_LOG = "server-command-log";

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ServerCommandRepoCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void addCommandLogEntry(MongoCommand command) {
        mongoTemplate.save(command, SERVER_COMMAND_LOG);
    }

    @Override
    public List<MongoCommand> getLastXCommandLogEntries(short count) {
        Query query = new Query();
        query.limit(count);
        return mongoTemplate.find(query.with(
                Sort.by(Sort.Direction.DESC, "time")), MongoCommand.class
        );

    }
}
