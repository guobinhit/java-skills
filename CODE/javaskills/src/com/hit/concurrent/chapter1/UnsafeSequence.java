package com.hit.concurrent.chapter1;

/**
 * author:Charies Gavin
 * date:2018/10/21,10:32
 * https:github.com/guobinhit
 * description:非线程安全类，存在竞态条件
 */
public class UnsafeSequence {
    private int value;

    /**
     * 返回唯一的数值
     */
    public int getNext() {
        return value++;
    }
}
