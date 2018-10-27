package com.hit.thought.chapter7;

/**
 * author:Charies Gavin
 * date:2017/12/30,16:22
 * https:github.com/guobinhit
 * description:测试导出类对象包含子类对象
 */
public class ClassA extends ClassB {
    public ClassA() {
        System.out.println("==== ClassA extends ClassB ==");
    }

    public static void main(String[] args) {
        new ClassA();
    }
}

class ClassB extends ClassC {
    public ClassB() {
        super("extend show time");
        System.out.println("==== ClassB extends ClassC ==");
    }
}

class ClassC {
    public ClassC() {
        System.out.println("==== ClassC ==");
    }

    public ClassC(String description) {
        System.out.println(description);
        System.out.println("==== ClassC ==");
    }
}
