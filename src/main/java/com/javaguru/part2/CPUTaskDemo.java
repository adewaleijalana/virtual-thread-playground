package com.javaguru.part2;

import com.javaguru.util.CommonUtils;

public class CPUTaskDemo {

    static void main() {
        System.out.println(CommonUtils.timer(() -> Task.findFibonacci(46)));
    }
}
