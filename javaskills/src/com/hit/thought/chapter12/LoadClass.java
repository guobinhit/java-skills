package com.hit.thought.chapter12;

/**
 * author:Charies Gavin
 * date:2018/3/17,18:00
 * https:github.com/guobinhit
 * description:测试类加载
 */
public class LoadClass {
    public static void main(String[] args) {
        System.out.println("Hello BeiJing!");
        new ChaoYang();

        try {
            // Class 类的静态 forName() 方法的参数为类的全限定名(包括包名)字符串
            Class.forName("com.hit.thought.chapter12.XiCheng");
        } catch (ClassNotFoundException e) {
            System.out.println("Not Fund Class!");
        }

        new DongCheng();
        System.out.println("Bye BeiJing!");
    }
}

class ChaoYang {
    static {
        System.out.println("CHAOYANG!");
    }
}

class XiCheng {
    static {
        System.out.println("XICHENG!");
    }
}

class DongCheng {
    static {
        System.out.println("DONGCHENG!");
    }
}
