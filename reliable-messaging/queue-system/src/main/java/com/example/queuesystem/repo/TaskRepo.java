package com.example.queuesystem.repo;

import com.example.queuesystem.entities.MongoTask;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;

public interface TaskRepo {


    void saveAll(@NonNull Collection<MongoTask> mongoTasks);

    void update(@NonNull MongoTask mongoTask);

    void updateAll(@NonNull Collection<MongoTask> mongoTasks);

    List<MongoTask> findAllStartable();

    void delete(String taskId);

    void deleteAll();

}
