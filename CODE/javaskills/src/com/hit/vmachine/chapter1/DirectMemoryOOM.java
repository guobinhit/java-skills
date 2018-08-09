package com.hit.vmachine.chapter1;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * author:Charies Gavin
 * date:2018/8/05,17:26
 * https:github.com/guobinhit
 * description:测试本机直接内测溢出
 * VM Args: -Xmx20m -XX:DirectMemorySize=10m
 */
public class DirectMemoryOOM {
    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) throws Exception {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        while (true) {
            unsafe.allocateMemory(_1MB);
        }
    }
}
