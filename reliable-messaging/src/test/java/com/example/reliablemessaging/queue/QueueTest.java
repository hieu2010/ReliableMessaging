package com.example.reliablemessaging.queue;

import com.example.reliablemessaging.task.TaskWrapper;
import com.example.reliablemessaging.weathersensor.Weather;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest
public class QueueTest {

    private static final String TEST_QUEUE = "testqueue";

    @Autowired
    private QueueSystem queue;

    @Test
    void producerTest() {
        queue.ensureQueueExists(TEST_QUEUE);
        String time = "t1";
        String coco = "1";
        String[] attrs = new String[]{time, "", "", "", "", "", "", "", "",
        "", "", "", coco};
        Weather weather = Weather.createWeather(attrs);
        List<String> taskIds = queue.createTask("testqueue", weather);
        //Assertions.assertEquals(Collections.singletonList("id1"), taskIds);
        Assertions.assertTrue(taskIds.get(0).length() > 10);
    }

    @Test
    void consumerTest() {
        AtomicReference<TaskWrapper> wrapperRef = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);
        queue.consume((task -> {
            wrapperRef.set(task);
            queue.markSuccess(TEST_QUEUE, task);
            latch.countDown();
        }));
    }

}
