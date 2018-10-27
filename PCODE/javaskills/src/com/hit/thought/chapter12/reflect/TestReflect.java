package com.hit.thought.chapter12.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * author:Charies Gavin
 * date:2018/3/18,18:30
 * https:github.com/guobinhit
 * description:测试反射效果
 */
public class TestReflect {
    public static void main(String[] args) throws Exception {
        System.out.println("=== 1 通过反射获取普通类的方法 ===");
        A a = C.makeA();
        a.f();
        System.out.println("类名为：" + a.getClass().getSimpleName());
        callHiddenMethod(a, "g");
        callHiddenMethod(a, "u");
        callHiddenMethod(a, "v");
        callHiddenMethod(a, "w");

        System.out.println("=== 2 通过反射获取私有内部类的方法 ===");
        A a1 = InnerA.makeA();
        a1.f();
        System.out.println("类名为：" + a1.getClass().getSimpleName());
        callHiddenMethod(a1, "g");
        callHiddenMethod(a1, "u");
        callHiddenMethod(a1, "v");
        callHiddenMethod(a1, "w");

        System.out.println("=== 3 通过反射获取匿名类的方法 ===");
        A a2 = AnonymousA.makeA();
        a2.f();
        System.out.println("类名为：" + a2.getClass().getSimpleName());
        callHiddenMethod(a2, "g");
        callHiddenMethod(a2, "u");
        callHiddenMethod(a2, "v");
        callHiddenMethod(a2, "w");

        System.out.println("=== 4 通过反射获取 private final 字段值 ===");
        PrivateFinalField pff = new PrivateFinalField();
        System.out.println(pff);
        // love 为非 static 字段
        Field field = pff.getClass().getDeclaredField("love");
        field.setAccessible(true);
        System.out.println("field.getInt(pff): " + field.get(pff));
        field.set(pff, 520);
        System.out.println(pff);
        // question 为 final 字段
        field = pff.getClass().getDeclaredField("question");
        field.setAccessible(true);
        System.out.println("field.get(pff): " + field.get(pff));
        field.set(pff, "What is your number?");
        System.out.println(pff);
    }

    public static void callHiddenMethod(Object object, String methodName) throws Exception {
        // 通过反射获取方法
        Method method = object.getClass().getDeclaredMethod(methodName);
        // 设置方法为可访问状态
        method.setAccessible(true);
        // 调用方法
        method.invoke(object);
    }
}

/**
 * 私有内部类
 */
class InnerA {
    private static class D implements A {

        @Override
        public void f() {
            System.out.println("InnerA: public D.f()");
        }

        public void g() {
            System.out.println("InnerA: public D.g()");
        }

        void u() {
            System.out.println("InnerA: package D.u()");
        }

        protected void v() {
            System.out.println("InnerA: protected D.v()");
        }

        private void w() {
            System.out.println("InnerA: private D.w()");
        }

        public static A makeA() {
            return new C();
        }
    }

    public static A makeA() {
        return new C();
    }
}

/**
 * 匿名类
 */
class AnonymousA {
    public static A makeA() {
        return new A() {

            @Override
            public void f() {
                System.out.println("AnonymousA: public f()");
            }

            public void g() {
                System.out.println("AnonymousA: public g()");
            }

            void u() {
                System.out.println("AnonymousA: package u()");
            }

            protected void v() {
                System.out.println("AnonymousA: protected v()");
            }

            private void w() {
                System.out.println("AnonymousA: private w()");
            }
        };
    }
}

/**
 * 私有静态字段
 */
class PrivateFinalField {
    private int love = 521;
    private final String words = "Hi girl!";
    private final String question = "What is your name?";

    @Override
    public String toString() {
        return words + question + love;
    }
}



