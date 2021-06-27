package com.example.cloudservermock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CloudServerMockApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudServerMockApplication.class, args);
    }

}
