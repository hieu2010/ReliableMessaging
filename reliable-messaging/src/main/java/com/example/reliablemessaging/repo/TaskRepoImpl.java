package com.example.reliablemessaging.repo;

import com.example.reliablemessaging.entities.MongoTask;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskRepoImpl implements TaskRepo {

    private static final String COLL_NAME = "task";

    private final String collectionName;
    private final MongoTemplate mongoTemplate;

    public TaskRepoImpl(MongoTemplate mongoTemplate) {
        this.collectionName = COLL_NAME;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void saveAll(Collection<MongoTask> mongoTasks) {
        mongoTemplate.insert(mongoTasks, COLL_NAME);
    }

    @Override
    public void update(MongoTask mongoTask) {

    }

    @Override
    public void updateAll(Collection<MongoTask> mongoTasks) {
        mongoTemplate.remove(Query.query(
                Criteria.where("taskId")
                        .in(mongoTasks.stream().map(MongoTask::getTaskId).collect(Collectors.toList()))),
                MongoTask.class,
                COLL_NAME);
        saveAll(mongoTasks);
    }

    @Override
    public List<MongoTask> findAllStartable() {
        return mongoTemplate.find(Query.query(
                Criteria.where("finishedAt").is(null))
                        .with(Sort.by(Sort.Direction.DESC, "priority")
                                .and(Sort.by(Sort.Direction.ASC, "createdAt"))),
                MongoTask.class,
                COLL_NAME);
    }

    @Override
    public void delete(String taskId) {
        mongoTemplate.remove(Query.query(Criteria.where("taskId").is(taskId)));
    }

    @Override
    public void deleteAll() {
        mongoTemplate.remove(Query.query(Criteria.where("taskId").ne(null)),
                MongoTask.class,
                COLL_NAME);
    }
}
