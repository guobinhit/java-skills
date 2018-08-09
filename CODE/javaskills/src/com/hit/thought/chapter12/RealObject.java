package com.hit.thought.chapter12;

/**
 * author:Charies Gavin
 * date:2018/3/18,15:30
 * https:github.com/guobinhit
 * description:实现接口
 */
public class RealObject implements Interface {

    @Override
    public void doSomething() {
        System.out.println("doSomething!");
    }

    @Override
    public void somethingElse(String arg) {
        System.out.println("somethingElse " + arg);
    }
}
