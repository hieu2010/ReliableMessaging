package com.example.relmes.datageneration.documents;

import com.example.relmes.commons.command.Command;
import com.example.relmes.datageneration.util.Converter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("local-command")
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
        mongoCommand.setCommandId(Converter.generateId());
        mongoCommand.setCommand(command);
        mongoCommand.setTime(Instant.now());
        return mongoCommand;
    }
}
