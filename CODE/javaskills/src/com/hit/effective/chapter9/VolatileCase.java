package com.hit.effective.chapter9;

import java.util.concurrent.atomic.AtomicLong;

/**
 * author:Charies Gavin
 * date:2018/6/21,8:31
 * https:github.com/guobinhit
 * description:Volatile操作符示例
 */
public class VolatileCase {
    private static volatile int nextSerialNumber = 0;

    private static final AtomicLong nextSerialNumber2 = new AtomicLong();

    /**
     * 没有同步
     *
     * @return
     */
//    public static int getNextSerialNumber() {
//        return nextSerialNumber++;
//    }
    public static synchronized int getNextSerialNumber() {
        return nextSerialNumber++;
    }

    public static long getNextSerialNumber2() {
        return nextSerialNumber2.getAndIncrement();
    }


    public static void main(String[] args) {
        Thread thread_1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    System.out.println(Thread.currentThread() + " 序列号：" + getNextSerialNumber());
                }
            }
        });

        Thread thread_2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    System.out.println(Thread.currentThread() + " 序列号：" + getNextSerialNumber());
                }
            }
        });

        thread_1.start();
        thread_2.start();
    }
}
