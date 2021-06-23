//package com.example.reliablemessaging.consumer;
//
//import com.example.reliablemessaging.entities.MongoTask;
//import com.example.reliablemessaging.queue.QueueSystem;
//import com.example.reliablemessaging.task.DataProcessor;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//import java.util.List;
//
//public class PollingTaskConsumer {
//
//    private static final Logger LOGGER = LogManager.getLogger(PollingTaskConsumer.class);
//
//    private static final short THREAD_COUNT = 1;
//    private static final short POLL_INTERVAL = 5; //secs
//    private static final short POLL_COUNT = 1;
//    private static final String QUEUE_NAME = "testqueue";
//
//    private final ThreadPoolTaskExecutor executor;
//
//    private final QueueSystem queueSystem;
//    private final DataProcessor dataProcessor;
//
//    public PollingTaskConsumer(QueueSystem queueSystem, DataProcessor dataProcessor) {
//        this.queueSystem = queueSystem;
//        this.dataProcessor = dataProcessor;
//        this.executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(THREAD_COUNT);
//        executor.initialize();
//        this.start();
//    }
//
//    public void start() {
//        LOGGER.info("Starting executor...");
//        for (int i = 0; i < THREAD_COUNT; i++) {
//            int finalI = i;
//            executor.submit(() -> poll(finalI));
//        }
//    }
//
//    private void poll(int threadId) {
//        try {
//            List<MongoTask> tasks = queueSystem.getAndStartTasks(QUEUE_NAME, POLL_COUNT);
//            if (!tasks.isEmpty()) {
//                for (MongoTask task : tasks) {
//                    processTask(task, threadId);
//                }
//            } else {
//                try {
//                    LOGGER.info("No current tasks available. Retrying in {}", POLL_INTERVAL);
//                    Thread.sleep(POLL_INTERVAL);
//                } catch (InterruptedException e) {
//                    LOGGER.info("Something went wrong.");
//                    Thread.currentThread().interrupt();
//                }
//            }
//        } catch (Exception e) {
//            LOGGER.info("Polling error: {}", e.getMessage());
//        }
//    }
//
//    private void processTask(MongoTask task, int threadId) {
//        LOGGER.info("Processing tasks with id: {}", task.getTaskId());
//        try {
//            dataProcessor.process(task);
//        } catch (Exception e) {
//            LOGGER.info("Task processing error: {}", e.getMessage());
//        }
//
//    }
//
//
//
//
//}
