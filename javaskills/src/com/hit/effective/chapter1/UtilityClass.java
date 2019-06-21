package com.hit.effective.chapter1;

/**
 * author:Charies Gavin
 * date:2018/5/27,9:58
 * https:github.com/guobinhit
 * description:通过私有构造器强化不可实例化能力
 */
public class UtilityClass {
    /**
     * 私有化构造器，并在类内部无意识调用该构造器的时候，抛出错误
     */
    private UtilityClass() {
        throw new AssertionError();
    }
}
