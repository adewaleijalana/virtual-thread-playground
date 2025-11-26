package com.javaguru.part1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;


public class InboundOutboundTaskDemo {

    private static Logger logger = LoggerFactory.getLogger(InboundOutboundTaskDemo.class);
    private static final int MAX_PLATFORM_THREADS = 10;
    private static final int MAX_VIRTUAL_THREADS = 10;

    static void main() {
        System.out.println(Runtime.getRuntime().availableProcessors());
//        platformDaemonThread();
//        virtualThreadDemo();

        changePriority(Thread.ofPlatform().unstarted(() -> {}));
        changePriority(Thread.ofVirtual().unstarted(() -> {}));
    }



    static void changePriority(Thread thread){
        thread.setPriority(6);
        System.out.println(thread.getPriority());
    }

    private static void platformThreadDemo1() {
        for (int i = 0; i < MAX_PLATFORM_THREADS; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> Task.ioIntensiveMethod(finalI));

            thread.start();
        }
    }

    private static void platformThreadDemo2() {
        var builder = Thread.ofPlatform().name("rose-", 1);
        for (int i = 0; i < MAX_PLATFORM_THREADS; i++) {
            int finalI = i;
            Thread thread = builder.unstarted(() -> Task.ioIntensiveMethod(finalI));
            thread.start();
        }
    }


    private static void platformDaemonThread() {
        var latch = new CountDownLatch(MAX_PLATFORM_THREADS);
        var builder = Thread.ofPlatform().daemon().name("daemon-", 1);
        for (int i = 0; i < MAX_PLATFORM_THREADS; i++) {
            int finalI = i;
            Thread thread = builder.unstarted(() -> {
                Task.ioIntensiveMethod(finalI);
                latch.countDown();
            });
            thread.start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private static void virtualThreadDemo() {
        var latch = new CountDownLatch(MAX_PLATFORM_THREADS);
        var builder = Thread.ofVirtual().name("virtual-", 1);
        for (int i = 0; i < MAX_VIRTUAL_THREADS; i++) {
            int finalI = i;
            Thread thread = builder.unstarted(() -> {
                Task.ioIntensiveMethod(finalI);
                latch.countDown();
            });
            thread.start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
