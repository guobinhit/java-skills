package com.hit.effective.chapter9;

import java.util.concurrent.TimeUnit;

/**
 * author:Charies Gavin
 * date:2018/6/21,8:26
 * https:github.com/guobinhit
 * description:线程示例3
 */
public class StopThread3 {
    private static volatile boolean stopRequested;

    public static void main(String[] args) throws InterruptedException {
        Thread backgroundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (!stopRequested) {
                    i++;
                    System.out.println("Thread running i = " + i);
                }
            }
        });
        backgroundThread.start();

        TimeUnit.SECONDS.sleep(1);
        stopRequested = true;
    }
}
