package com.hit.thought.chapter13;

import java.util.ArrayList;

/**
 * author:Charies Gavin
 * date:2018/4/21,17:23
 * https:github.com/guobinhit
 * description:测试擦除的效果
 */
public class ErasedTypeEquivalence {
    public static void main(String[] args) {
        Class c1 = new ArrayList<String>().getClass();
        Class c2 = new ArrayList<Integer>().getClass();
        System.out.println("ArrayList<String> == ArrayList<Integer> : " + (c1 == c2));
    }
}
