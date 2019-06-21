package com.hit.effective.chapter5.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * author:Charies Gavin
 * date:2018/6/8,8:08
 * https:github.com/guobinhit
 * description:测试Test注解
 */
public class RunTests {
    /**
     * 该方法为 静态无参 的，因此可以通过 @Test 测试
     */
    @Test
    public static void testAnnocation() {
        System.out.println("hello world");
    }

    /**
     * 该方法为 静态有参 的，因此不可以通过 @Test 测试
     */
    @Test
    public static void testAnnocation2(String word) {
        System.out.println(word);
    }

    /**
     * 该方法为 非静态无参 的，因此不可以通过 @Test 测试
     */
    @Test
    public void testAnnocation3() {
        System.out.println("hello world");
    }

    public static void main(String[] args) throws Exception {
        int tests = 0;
        int passed = 0;
        Class testClass = Class.forName("com.hit.effective.chapter5.annotation.RunTests");
//        Class testClass = Class.forName(args[0]);
        for (Method method : testClass.getDeclaredMethods()) {
            // 判断类中的被 @Test 注解的方法
            if (method.isAnnotationPresent(Test.class)) {
                tests++;
                try {
                    // 通过反射，执行被注解的方法
                    method.invoke(null);
                    passed++;
                } catch (InvocationTargetException warppedExc) {
                    Throwable exc = warppedExc.getCause();
                    System.out.println(method + " failed: " + exc);
                } catch (Exception exc) {
                    System.out.println("Invalid @Test: " + method);
                }
            }
        }
        System.out.printf("Passed: %d, Failed: %d%n", passed, tests - passed);
    }
}
