package com.hit.effective.chapter1;

import java.util.HashMap;

/**
 * author:Charies Gavin
 * date:2018/5/23,20:50
 * https:github.com/guobinhit
 * description:静态工厂
 */
public class StaticFactory {
    public static Boolean valueOf(boolean b) {
        return b ? Boolean.TRUE : Boolean.FALSE;
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap();
    }

    public static void main(String[] args) {

    }
}
