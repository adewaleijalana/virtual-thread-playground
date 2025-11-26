package com.javaguru.part3;

import com.javaguru.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.ThreadFactory;

public class Lec01ThreadFactory {

    private static final Logger log = LoggerFactory.getLogger(Lec01ThreadFactory.class);

    static void main() {
        demo(Thread.ofVirtual().name("rose-", 1).factory());
        CommonUtils.sleep(Duration.ofSeconds(4));
    }

    private static void demo(ThreadFactory factory) {
        for (int i = 0; i < 3; i++) {
            var thread = factory.newThread(() -> {
                log.info("Task started: {}", Thread.currentThread());
                 var cd1 = factory.newThread(() -> {
                     log.info("Task in child 1 started: {}", Thread.currentThread());
                     CommonUtils.sleep(Duration.ofSeconds(2));
                     log.info("Task in child 1 ended: {}", Thread.currentThread());
                 });
                 cd1.start();

                log.info("Task ended: {}", Thread.currentThread());

            });
            thread.start();
        }

    }

    private static void ioTask() {
        CommonUtils.sleep(Duration.ofSeconds(10));
    }
}
