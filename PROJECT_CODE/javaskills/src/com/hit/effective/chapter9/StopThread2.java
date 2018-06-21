package com.hit.effective.chapter9;

import java.util.concurrent.TimeUnit;

/**
 * author:Charies Gavin
 * date:2018/6/21,8:13
 * https:github.com/guobinhit
 * description:线程示例2(需要将读和写操作都同步，否则同步不会起作用)
 */
public class StopThread2 {
    private static boolean stopRequested;

    private static synchronized void requestStop() {
        stopRequested = true;
    }

    private static synchronized boolean isStopRequested() {
        return stopRequested;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread backgroundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (!isStopRequested()) {
                    i++;
                    System.out.println("Thread running i = " + i);
                }
            }
        });
        backgroundThread.start();

        TimeUnit.SECONDS.sleep(1);
        requestStop();
    }
}
