package com.hit.thought.chapter10;

/**
 * author:Charies Gavin
 * date:2018/3/10,17:40
 * https:github.com/guobinhit
 * description:运行时异常测试
 */
public class TestRuntimException {
    public static void main(String[] args) {
        catchRuntimeException();
    }

    private static void catchRuntimeException() {
        getRuntimeException();
    }

    /**
     * 创建运行时异常并抛出
     */
    private static void getRuntimeException() {
        RuntimeException re = new RuntimeException();
        throw re;
    }
}
