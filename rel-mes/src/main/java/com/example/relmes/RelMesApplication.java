package com.example.relmes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableMongoRepositories(basePackages = {"com.example.relmes.commons.repo"})
@EnableScheduling
@SpringBootApplication
public class RelMesApplication {

    public static void main(String[] args) {
        SpringApplication.run(RelMesApplication.class, args);
    }

}
