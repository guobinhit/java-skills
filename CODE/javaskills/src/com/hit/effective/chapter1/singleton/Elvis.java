package com.hit.effective.chapter1.singleton;

/**
 * author:Charies Gavin
 * date:2018/5/26,11:51
 * https:github.com/guobinhit
 * description:单例模式，提供公有静态 final 域成员
 */
public class Elvis {
    /**
     * 设置公有静态 final 域
     */
    public static final Elvis INSTANCE = new Elvis();

    /**
     * 私有化构造器
     */
    private Elvis() {
    }
}
