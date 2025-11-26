package com.javaguru.part4.externalservice;


import com.javaguru.part4.currencylimit.ConcurrencyLimiter;
import com.javaguru.part4.currencylimit.ConcurrencyLimiterWithOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;


public class ConcurrencyLimitWithSemaphoreWithOrder {

    private static final Logger log = LoggerFactory.getLogger(ConcurrencyLimitWithSemaphoreWithOrder.class.getName());

    static void main() throws Exception {
        var factory = Thread.ofVirtual().name("rose-", 1).factory();
        var concurrencyLimiter = new ConcurrencyLimiterWithOrder(Executors.newThreadPerTaskExecutor(factory), 3);
        execute(concurrencyLimiter, 20);
    }

    private static void execute(final ConcurrencyLimiterWithOrder concurrencyLimit, int taskCount) throws Exception {
        try(concurrencyLimit) {
            for (int i = 1; i <= taskCount; i++) {
                int finalI = i;
                concurrencyLimit.submit(() -> printProductInfo(finalI));
            }
            log.info("All tasks executed successfully");
        }
    }

    private static String printProductInfo(final int productId) {
        var product = Client.getProduct(productId);
        log.info("{} => {}", productId, product);
        return product;
    }
}
