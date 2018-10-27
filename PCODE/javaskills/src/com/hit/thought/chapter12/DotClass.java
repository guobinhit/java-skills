package com.hit.thought.chapter12;

import java.util.Random;

/**
 * author:Charies Gavin
 * date:2018/3/18,10:00
 * https:github.com/guobinhit
 * description:测试通过 .class 获取 Class 对象
 */
public class DotClass {
    public static Random random = new Random();

    public static void main(String[] args) {

        // 使用 .class 获取 Class 对象，并不直接初始化
        Class initable = Initable.class;

        System.out.println("After creating Initable reference!");

        System.out.println(Initable.staticFinal);

        System.out.println(Initable.staticFinal2);

        System.out.println(Initable2.staticNonFinal);

        Class initable3 = null;

        try {
            // 使用 forName() 方法获取 Class 对象，直接进行初始化
            initable3 = Class.forName("com.hit.thought.chapter12.Initable3");
        } catch (ClassNotFoundException e) {
            System.out.println("Sorry, I don't get Initable3 class!");
        }

        System.out.println("After creating Initable3 reference!");

        System.out.println(Initable3.staticNonFinal);
    }
}

class Initable {
    // static final 常量，编译期常量
    static final int staticFinal = 20180202;

    // static final 常量，非编译期常量
    static final int staticFinal2 = DotClass.random.nextInt(100);

    static {
        System.out.println("Initialing Initable");
    }
}

class Initable2 {
    // static 非 final 常量
    static int staticNonFinal = 20180218;

    static {
        System.out.println("Initialing Initable2");
    }
}

class Initable3 {
    // static 非 final 常量
    static int staticNonFinal = 20180318;

    static {
        System.out.println("Initialing Initable3");
    }
}
