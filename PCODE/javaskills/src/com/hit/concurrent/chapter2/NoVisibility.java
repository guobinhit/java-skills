package com.hit.concurrent.chapter2;

/**
 * author:Charies Gavin
 * date:2018/10/27,10:12
 * https:github.com/guobinhit
 * description:可能出现无限循环
 */
public class NoVisibility {
    private static boolean ready;
    private static int number;

    public static class ReaderThread extends Thread {
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new ReaderThread().start();
        number = 20151120;
        ready = true;
    }
}
