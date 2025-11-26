package com.javaguru.part4;

import com.javaguru.part4.externalservice.Client;
import com.javaguru.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SchedulerWithVirtualThread {
    private static final Logger log = LoggerFactory.getLogger(SchedulerWithVirtualThread.class);

    static void main() {
        schedulerWithVirtualThread();
    }

    private static void schedulerWithVirtualThread() {
        var scheduler = Executors.newSingleThreadScheduledExecutor();
        var executor = Executors.newVirtualThreadPerTaskExecutor();

        try (scheduler; executor) {
            scheduler.scheduleAtFixedRate(() -> {
                executor.submit(() -> printProductInfo(2));
            }, 1, 3, TimeUnit.SECONDS);

            CommonUtils.sleep(Duration.ofSeconds(15));
        }


    }

    private static void printProductInfo(final int productId) {
        log.info("{} => {}", productId, Client.getProduct(productId));
    }
}
