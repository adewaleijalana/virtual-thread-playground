package com.javaguru.part1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class Task {
    private static final Logger log = LoggerFactory.getLogger(Task.class);

    public static void ioIntensiveMethod(int i) {
        try {
            log.info("staring io task: {}", i);
            Thread.sleep(Duration.ofSeconds(10));
            log.info("completed io task: {}", i);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
