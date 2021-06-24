package com.example.reliablemessaging.completion;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TaskCompletionService {

    @PostConstruct
    public void start() {
        poll();
    }

}
