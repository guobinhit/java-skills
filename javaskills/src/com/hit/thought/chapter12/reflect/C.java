package com.hit.thought.chapter12.reflect;

/**
 * author:Charies Gavin
 * date:2018/3/18,18:20
 * https:github.com/guobinhit
 * description:实现接口
 */
public class C implements A {
    @Override
    public void f() {
        System.out.println("public C.f()");
    }

    public void g() {
        System.out.println("public C.g()");
    }

    void u() {
        System.out.println("package C.u()");
    }

    protected void v() {
        System.out.println("protect C.v()");
    }

    private void w() {
        System.out.println("private C.w()");
    }

    public static A makeA() {
        return new C();
    }
}
