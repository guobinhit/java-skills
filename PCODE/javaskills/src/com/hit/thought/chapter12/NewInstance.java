package com.hit.thought.chapter12;

/**
 * author:Charies Gavin
 * date:2018/3/11,15:20
 * https:github.com/guobinhit
 * description:创建类实例
 */
public class NewInstance {
    public static void main(String[] args) {
        Class c = null;

        try {
            c = Class.forName("com.hit.thought.chapter12.Instance");
        } catch (ClassNotFoundException e) {
            System.out.println("Sorry, this class isn't found!");
        }

        Instance instance = null;
        try {
            // 使用 newInstance() 来创建类实例，则该类必须含有默认的构造器
            instance = (Instance) c.newInstance();
        } catch (Exception e) {
            System.out.println("Get instance exception!");
        }
        instance.helleInstance();
    }
}

class Instance {

    /**
     * 放开默认构造器(无参构造器)的注释，则程序正常运行
     */
//    public Instance() {
//    }

    /**
     * 非默认构造器
     *
     * @param name
     */
    public Instance(String name) {
        System.out.printf("Instance name is: " + name);
    }

    public void helleInstance() {
        System.out.println("Hello instance!");
    }
}
