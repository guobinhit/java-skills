package com.hit.effective.chapter1.singleton;

import java.io.Serializable;

/**
 * author:Charies Gavin
 * date:2018/5/26,12:05
 * https:github.com/guobinhit
 * description:单例模式，提供公有静态工厂方法
 */
public class Elvis2 implements Serializable {

    private static final long serialVersionUID = 8894903144474616531L;

    private static final Elvis2 INSTANCE = new Elvis2();

    /**
     * 私有化构造器
     */
    private Elvis2() {
    }

    /**
     * 公有静态工厂
     */
    public static Elvis2 getInstance() {
        return INSTANCE;
    }

    /**
     * 防止反序列化是生成假冒的实例
     */
    private Object readResolve() {
        return INSTANCE;
    }
}
