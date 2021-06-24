package com.example.reliablemessaging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ReliableMessagingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReliableMessagingApplication.class, args);
    }

}
