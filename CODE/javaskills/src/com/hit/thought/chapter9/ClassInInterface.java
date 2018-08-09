package com.hit.thought.chapter9;

/**
 * author:Charies Gavin
 * date:2018/3/4,10:50
 * https:github.com/guobinhit
 * description:在接口内部定义内部类
 */
public interface ClassInInterface {
    void interfaceMethod();

    class InnerClassInInterface {
        void sayHello() {
            System.out.println("Hello World!");
        }

        public static void main(String[] args) {
            new InnerClassInInterface().sayHello();
        }
    }
}
