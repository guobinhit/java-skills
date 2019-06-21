package com.hit.thought.chapter13;

import java.util.ArrayList;
import java.util.List;

/**
 * author:Charies Gavin
 * date:2018/4/21,17:15
 * https:github.com/guobinhit
 * description:测试泛型方法和可变参数
 */
public class GenericVarargs {
    // 使用可变参数的泛型方法
    public static <T> List<T> makeList(T... args) {
        List<T> list = new ArrayList<T>();
        for (T item : args) {
            list.add(item);
        }
        return list;
    }

    public static void main(String[] args) {
        List<String> ls = makeList("Hello");
        System.out.println(ls);
        ls = makeList("Hello", "World");
        System.out.println(ls);
    }
}
