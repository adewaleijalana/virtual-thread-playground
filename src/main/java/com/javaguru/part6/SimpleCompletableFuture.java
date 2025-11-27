package com.javaguru.part6;

import com.javaguru.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class SimpleCompletableFuture {
    private static final Logger log = LoggerFactory.getLogger(SimpleCompletableFuture.class);

    static void main() {
        log.info("Starting SimpleCompletableFuture in main");
        var result = slowTask();
        result.thenAccept(s ->  log.info("result: {}",s));
        log.info("Finished SimpleCompletableFuture in main");

        CommonUtils.sleep(Duration.ofSeconds(3));
    }

    private static CompletableFuture<String> fastTask() {
        log.info("Starting SimpleCompletableFuture fastTask");
        var completableFuture = new CompletableFuture<String>();
        completableFuture.complete("Hello World of CompletableFuture");
        log.info("Finished SimpleCompletableFuture fastTask");
        return completableFuture;
    }


    private static CompletableFuture<String> slowTask() {
        log.info("Starting SimpleCompletableFuture slowTask");
        var completableFuture = new CompletableFuture<String>();
        Thread.ofVirtual().start(() -> {
            CommonUtils.sleep(Duration.ofSeconds(2));
            completableFuture.complete("Hello World of CompletableFuture");
        });
        log.info("Finished SimpleCompletableFuture slowTask");
        return completableFuture;
    }
}
