package com.github.webfluxdemo.util;

public class SleepUtil {

    public static void sleepSeconds(final int input) {
        try {
            Thread.sleep(input * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
