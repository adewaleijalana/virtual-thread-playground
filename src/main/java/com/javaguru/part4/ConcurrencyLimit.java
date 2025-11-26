package com.javaguru.part4;


import com.javaguru.part4.externalservice.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ConcurrencyLimit {

    private static final Logger log = LoggerFactory.getLogger(ConcurrencyLimit.class.getName());

    static void main() throws Exception {
        execute(Executors.newCachedThreadPool(), 20);
    }

    private static void execute(ExecutorService executorService, int taskCount) throws Exception {
        try(executorService) {
            for (int i = 1; i <= taskCount; i++) {
                int finalI = i;
                executorService.submit(() -> printProductInfo(finalI));
            }
            log.info("All tasks executed successfully");
        }
    }

    private static void printProductInfo(final int productId) {
        log.info("{} => {}", productId, Client.getProduct(productId));
    }
}
