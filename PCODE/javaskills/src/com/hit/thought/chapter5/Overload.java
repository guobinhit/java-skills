package com.hit.thought.chapter5;

/**
 * author:Charies Gavin
 * date:2017/12/22,21:04
 * https:github.com/guobinhit
 * description:测试方法重载
 */
public class Overload {
    public static void main(String[] args) {
        introduce(18, "Charies");
        introduce("Charies", 18);
    }

    public static void introduce(int age, String name) {
        System.out.println("My name is " + name + ", I'm " + age + " old!");
    }

    public static void introduce(String name, int age) {
        System.out.println("My name is " + name + ", I'm " + age + " old!");
    }
}
