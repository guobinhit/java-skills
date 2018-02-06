package com.hit.thought.chapter5;

/**
 * author:Charies Gavin
 * date:2017/12/22,20:30
 * https:github.com/guobinhit
 * description:测试构造方法
 */
public class StructureMethod {
    public static void main(String[] args) {
        Angel angel_1 = new Angel();
        Angel angel_2 = new Angel("Angela");
    }
}

class Angel {
    /**
     * 无参的构造方法
     */
    public Angel() {
        System.out.println("I'm a angel, but I don't know my name!");
    }

    /**
     * 有参的构造方法
     *
     * @param name
     */
    public Angel(String name) {
        System.out.println("I'm a angel, my name is " + name + "!");
    }
}
