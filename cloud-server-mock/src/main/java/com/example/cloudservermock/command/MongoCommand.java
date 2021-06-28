package com.example.cloudservermock.command;

import com.example.cloudservermock.util.IdGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("server-command")
public class MongoCommand {

    @Id
    String commandId;

    Command command;
    Instant time = null;

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public static MongoCommand convert(Command command) {
        MongoCommand mongoCommand = new MongoCommand();
        mongoCommand.setCommandId(IdGenerator.generateId());
        mongoCommand.setCommand(command);
        mongoCommand.setTime(Instant.now());
        return mongoCommand;
    }
}
