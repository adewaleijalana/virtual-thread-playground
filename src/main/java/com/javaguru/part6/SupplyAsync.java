package com.javaguru.part6;

import com.javaguru.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class SupplyAsync {
    private static final Logger log = LoggerFactory.getLogger(SupplyAsync.class);

    static void main() {
        log.info("Starting SimpleCompletableFuture in main");
        var result = slowTask();
        result.thenAccept(s ->  log.info("result: {}",s))
                .exceptionally(ex -> {
                    log.info("exceptionally: {}",ex.getMessage());
                    return null;
                });
        log.info("Finished SimpleCompletableFuture in main");

        CommonUtils.sleep(Duration.ofSeconds(3));
    }

    private static CompletableFuture<Object> slowTask() {
        log.info("method start");

        var stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            log.info("running stringCompletableFuture");
//                    return "Hello World of CompletableFuture";
            throw new RuntimeException("Ooooooops");
                },
                Executors.newVirtualThreadPerTaskExecutor());
        log.info("method end");
        return stringCompletableFuture;
    }
}
