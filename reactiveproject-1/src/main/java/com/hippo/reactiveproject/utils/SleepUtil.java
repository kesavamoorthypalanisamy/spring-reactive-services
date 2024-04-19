package com.hippo.reactiveproject.utils;

public class SleepUtil {
    public static void sleepInSeconds(int seconds) {
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
