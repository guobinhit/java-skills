package com.hit.thought.chapter9;

/**
 * author:Charies Gavin
 * date:2018/3/1,9:03
 * https:github.com/guobinhit
 * description:测试类中嵌套接口
 */
public class NestInterface {
    /**
     * 嵌套在类内部的接口
     */
    interface InterfaceA {
        void methodA();
    }

    /**
     * 实现接口，并用 public 修饰
     */
    public class NestB implements InterfaceA {

        @Override
        public void methodA() {
            System.out.println("public class NestB implements InterfaceA");
        }
    }

    /**
     * 实现接口，并用 private 修饰
     */
    private class NestC implements InterfaceA {

        @Override
        public void methodA() {
            System.out.println("private class NestC implements InterfaceA");
        }
    }

    /**
     * 获取 NestB 的实例方法
     *
     * @return NestB
     */
    public NestB getNestB() {
        return new NestB();
    }

    /**
     * 获取 NestC 的实例方法
     *
     * @return NestC
     */
    public NestC getNestC() {
        return new NestC();
    }

    public static void main(String[] args) {
        NestInterface ni = new NestInterface();
        NestB nestB = ni.getNestB();
        NestC nestC = ni.getNestC();
        nestB.methodA();
        nestC.methodA();
    }
}
