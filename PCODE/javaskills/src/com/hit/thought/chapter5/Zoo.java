package com.hit.thought.chapter5;

/**
 * author:Charies Gavin
 * date:2017/12/30,13:40
 * https:github.com/guobinhit
 * description:测试 this 关键字
 */
public class Zoo {
    public Zoo() {
        System.out.println("Welcome to CG's Zoo!");
    }

    public static void main(String[] args) {
        new Zoo();
        new Animal();
    }

}

class Animal {
    public Animal() {
        /**
         * 只能在构造器中用 this 关键字调用对应参数的构造器
         */
        this("tiger");
        System.out.println("Animals");

        //this("panda");
    }

    public Animal(String type) {
        System.out.println("This is a " + type);
    }
}
