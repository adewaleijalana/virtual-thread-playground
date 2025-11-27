package com.javaguru.part6;

import com.javaguru.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class RunAsync {
    private static final Logger log = LoggerFactory.getLogger(RunAsync.class);

    static void main() {
        log.info("main starts");
        runAsync()
                .thenRun(() -> log.info("it is done"))
                .exceptionally(ex -> {
                    log.error("exceptionally", ex);
                    return null;
                });

        log.info("main ends");

        CommonUtils.sleep(Duration.ofSeconds(3));
    }

    private static CompletableFuture<Void> runAsync() {
        log.info("Starting method slowTask");
        var result = CompletableFuture.runAsync(() -> {
            CommonUtils.sleep(Duration.ofSeconds(1));
//            log.info("Task completed");
            throw  new RuntimeException("Oops");
        }, Executors.newVirtualThreadPerTaskExecutor());
        log.info("Finished method slowTask");
        return result;
    }
}
