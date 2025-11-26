package com.javaguru.part2;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Task {
    private static Logger logger = LoggerFactory.getLogger(Task.class);

    public static long findFibonacci(int n) {
        if (n < 2) {
            return n;
        }
        return findFibonacci(n - 1) + findFibonacci(n - 2);
    }
}
