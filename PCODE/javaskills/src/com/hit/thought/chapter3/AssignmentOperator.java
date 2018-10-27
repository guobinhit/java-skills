package com.hit.thought.chapter3;

/**
 * author:Charies Gavin
 * date:2017/12/09,12:40
 * https:github.com/guobinhit
 * description:测试赋值操作符，赋值对象引用
 */
public class AssignmentOperator {
    public static void main(String[] args) {
        // 创建两个对象
        Apple apple1 = new Apple("green");
        Apple apple2 = new Apple();
        // 输出两个初始化对象，apple1 初始化 color 为 green，apple2 初始化 color 为 null
        System.out.println("Initial: apple1 color is " + apple1.color + ", apple2 color is " + apple2.color);
        // 将 apple1 的引用赋值给 apple2
        apple2 = apple1;
        // 输出赋值后的两个对象，两个对象拥有同一个引用
        System.out.println("Assignment: apple1 color is " + apple1.color + ", apple2 color is " + apple2.color);
        // 修改 apple2 的引用
        apple2.color = "red";
        // 输出 apple2 修改后的两个对象，两个对象都发生变化
        System.out.println("Modify: apple1 color is " + apple1.color + ", apple2 color is " + apple2.color);
    }

}

class Apple {
    // 成员变量
    String color;

    /**
     * 默认构造器
     */
    Apple() {
    }

    /**
     * 有参构造器
     *
     * @param color
     */
    Apple(String color) {
        this.color = color;
    }
}
