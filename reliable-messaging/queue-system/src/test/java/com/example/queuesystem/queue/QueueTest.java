package com.example.queuesystem.queue;

import com.example.commons.weathersensor.Weather;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

@SpringBootTest
public class QueueTest {

    @Autowired
    private QueueSystem queue;

    @Test
    void producerTest() {
        queue.ensureQueueExists("testqueue");
        String time = "t1";
        String coco = "1";
        String[] attrs = new String[]{time, "", "", "", "", "", "", "", "",
        "", "", "", coco};
        Weather weatherSensorData = Weather.createWeather(attrs);
        List<String> taskIds = queue.createTask("testqueue", weatherSensorData);
        Assertions.assertTrue(taskIds.get(0).length() >= 10);
    }

}
