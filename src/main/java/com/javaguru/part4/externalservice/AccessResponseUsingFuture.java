package com.javaguru.part4.externalservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class AccessResponseUsingFuture {
    private static Logger log = LoggerFactory.getLogger(AccessResponseUsingFuture.class);

    static void main() throws ExecutionException, InterruptedException {

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            var product1 = executor.submit(() -> Client.getProduct(1));
            var product2 = executor.submit(() -> Client.getProduct(2));
            var product3 = executor.submit(() -> Client.getProduct(3));

            log.info("Getting product with id-1: {} with the thread: {}", product1.get(), Thread.currentThread().isVirtual());
            log.info("Getting product with id-2: {} with the thread: {}", product2.get(), Thread.currentThread().isVirtual());
            log.info("Getting product with id-3: {} with the thread: {}", product3.get(), Thread.currentThread().isVirtual());
        }

       log.info("Getting product: {}", Thread.currentThread().getName());
    }
}
