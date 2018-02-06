package com.hit.thought.chapter8;

/**
 * author:Charies Gavin
 * date:2018/1/3,9:16
 * https:github.com/guobinhit
 * description:测试初始化顺序，球形继承圆形类，间接继承图形基类(不是特别恰当)
 */
public class Global extends Circle {
    /**
     * 静态初始化块
     */
    static {
        System.out.println("Global: Static Initial.");
    }

    /**
     * 非静态初始化块
     */ {
        System.out.println("Global: Non-static Initial.");
    }

    /**
     * 默认无参构造方法
     */
    Global() {
        System.out.println("Global: Structure Method Initial.");
    }

    /**
     * 有参构造方法
     */
    Global(String str) {
        System.out.println("Global: Structure Method Initial ... " + str);
    }

    /**
     * 静态成员实例初始化
     */
    private static Circle staticircle = new Circle("in Global of Static Instance Initial.");

    /**
     * 非静态成员实例初始化
     */
    private Circle circle = new Circle("in Global of Non-static Instance Initial.");

    public static void main(String[] args) {
        /**
         * 在主方法中调用导出类构造方法，测试初始化顺序
         */
        new Global();
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
        System.out.println("Shape: Static Initial.");
    }

    /**
     * 非静态初始化块
     */ {
        System.out.println("Shape: Non-static Initial.");
    }

    /**
     * 默认无参构造方法
     */
    Shape() {
        System.out.println("Shape: Structure Method Initial.");
    }

    /**
     * 有参构造方法
     */
    Shape(String str) {
        System.out.println("Shape: Structure Method Initial ... " + str);
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
        System.out.println("Circle: Static Initial.");
    }

    /**
     * 非静态初始化块
     */ {
        System.out.println("Circle: Non-static Initial.");
    }

    /**
     * 默认无参构造方法
     */
    Circle() {
        System.out.println("Circle: Structure Method Initial.");
    }

    /**
     * 有参构造方法
     */
    Circle(String str) {
        System.out.println("Circle: Structure Method Initial ... " + str);
    }

    /**
     * 静态成员实例初始化
     */
    private static Shape staticShape = new Shape("in Circle of Static Instance Initial.");

    /**
     * 非静态成员实例初始化
     */
    private Shape shape = new Shape("in Circle of Non-static Instance Initial.");
}