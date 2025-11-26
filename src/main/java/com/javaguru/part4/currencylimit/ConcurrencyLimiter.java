package com.javaguru.part4.currencylimit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

public class ConcurrencyLimiter implements AutoCloseable{

    private static final Logger log = LoggerFactory.getLogger(ConcurrencyLimiter.class);

    private final ExecutorService executorService;
    private final Semaphore semaphore;

    public ConcurrencyLimiter(ExecutorService executorService, int limit) {
        this.executorService = executorService;
        this.semaphore = new Semaphore(limit);
    }

    public <T> Future<T> submit(Callable<T> callable) {
        return executorService.submit(() -> callableWrapper(callable));
    }

    private <T> T callableWrapper(final Callable<T> callable) {
        try {
            semaphore.acquire();
            return callable.call();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }finally {
            semaphore.release();
        }
    }

    @Override
    public void close() throws Exception {
        this.executorService.close();
    }
}
