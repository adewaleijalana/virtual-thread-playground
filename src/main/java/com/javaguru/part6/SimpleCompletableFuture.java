package com.javaguru.part6;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class SimpleCompletableFuture {
    private static final Logger log = LoggerFactory.getLogger(SimpleCompletableFuture.class);

    static void main() {
        log.info("Starting SimpleCompletableFuture in main");
        var result = fastTask();
        log.info("result: {}", result.join());
        log.info("Finished SimpleCompletableFuture in main");
    }

    private static CompletableFuture<String> fastTask() {
        log.info("Starting SimpleCompletableFuture fastTask");
        var completableFuture = new CompletableFuture<String>();
        completableFuture.complete("Hello World of CompletableFuture");
        log.info("Finished SimpleCompletableFuture fastTask");
        return completableFuture;
    }
}
