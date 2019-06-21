package com.hit.concurrent.chapter1;

/**
 * author:Charies Gavin
 * date:2018/10/21,18:06
 * https:github.com/guobinhit
 * description:线程安全类，内置锁可重入
 */
public class Widget {
    public synchronized void doSomething() {
    }
}

class LoggingWidget extends Widget {
    public synchronized void doSomething() {
        System.out.println("Hello World");
        super.doSomething();
    }
}
