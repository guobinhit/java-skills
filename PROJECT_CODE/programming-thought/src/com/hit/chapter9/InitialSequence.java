package com.hit.chapter9;

/**
 * author:Charies Gavin
 * date:2017/12/07,8:50
 * https:github.com/guobinhit
 * description:测试初始化顺序
 */
public class InitialSequence extends Global {
    /**
     * 静态初始化块
     */
    static {
        System.out.println("Static Initial of InitialSequence.");
    }

    /**
     * 非静态初始化块
     */ {
        System.out.println("Non-static Initial of InitialSequence.");
    }

    /**
     * 默认无参构造方法
     */
    InitialSequence() {
        System.out.println("Structure Method of InitialSequence.");
    }

    /**
     * 有参构造方法
     */
    InitialSequence(String str) {
        System.out.println("Structure Method of InitialSequence ... " + str);
    }

    /**
     * 成员实例初始化
     */
    private Shape shape = new Shape("in InitialSequence");
    private Circle circle = new Circle("in InitialSequence");
    private Global global = new Global("in InitialSequence");

    public static void main(String[] args) {
        /**
         * 在主方法中调用导出类构造方法，测试初始化顺序
         */
        new InitialSequence();
        System.out.println("Initial Over!");
    }
}

/**
 * 图形基类
 */
class Shape {

    /**
     * 静态初始化块
     */
    static {
        System.out.println("Static Initial of Shape.");
    }

    /**
     * 非静态初始化块
     */ {
        System.out.println("Non-static Initial of Shape.");
    }

    /**
     * 默认无参构造方法
     */
    Shape() {
        System.out.println("Structure Method of Shape.");
    }

    /**
     * 有参构造方法
     */
    Shape(String str) {
        System.out.println("Structure Method of Shape ... " + str);
    }
}

/**
 * 圆形继承图形基类
 */
class Circle extends Shape {
    /**
     * 静态初始化块
     */
    static {
        System.out.println("Static Initial of Circle.");
    }

    /**
     * 非静态初始化块
     */ {
        System.out.println("Non-static Initial of Circle.");
    }

    /**
     * 默认无参构造方法
     */
    Circle() {
        System.out.println("Structure Method of Circle.");
    }

    /**
     * 有参构造方法
     */
    Circle(String str) {
        System.out.println("Structure Method of Circle ... " + str);
    }
}

/**
 * 球形继承圆形类，间接继承图形基类(不是特别恰当)
 */
class Global extends Circle {
    /**
     * 静态初始化块
     */
    static {
        System.out.println("Static Initial of Global.");
    }

    /**
     * 非静态初始化块
     */ {
        System.out.println("Non-static Initial of Global.");
    }

    /**
     * 默认无参构造方法
     */
    Global() {
        System.out.println("Structure Method of Global.");
    }

    /**
     * 有参构造方法
     */
    Global(String str) {
        System.out.println("Structure Method of Global ... " + str);
    }
}