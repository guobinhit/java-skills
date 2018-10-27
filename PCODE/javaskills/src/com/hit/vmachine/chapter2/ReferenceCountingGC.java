package com.hit.vmachine.chapter2;

/**
 * author:Charies Gavin
 * date:2018/8/26,16:59
 * https:github.com/guobinhit
 * description:引用计数算法
 */
public class ReferenceCountingGC {
    public Object instance = null;
    private static final int _1MB = 1024 * 1024;

    /**
     * 该成员属性的唯一意义就是占点内存，以便能在 GC 日志中看清楚是否被回收过
     */
    private byte[] bigSize = new byte[2 * _1MB];

    public static void main(String[] args) {
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();
        objA.instance = objB;
        objB.instance = objA;

        objA = null;
        objB = null;

        /**
         * 假设在这行发生 GC，objA 和 objB 是否能被回收？
         */
        System.gc();
    }
}
