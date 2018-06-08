package com.hit.effective.chapter5.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * author:Charies Gavin
 * date:2018/6/8,8:33
 * https:github.com/guobinhit
 * description:测试ExceptionTest注解
 */
public class RunExceptionTests {
    /**
     * 该方法为抛出 ArithmeticException 异常，因此可以通过 @ExceptionTest 测试
     */
    @ExceptionTest(ArithmeticException.class)
    public static void testAnnocation() {
        int i = 0;
        i = i / i;
    }

    /**
     * 该方法抛出 ArrayIndexOutOfBoundsException 异常，因此不可以通过 @ExceptionTest 测试
     */
    @ExceptionTest(ArithmeticException.class)
    public static void testAnnocation2() {
        int[] a = new int[0];
        int i = a[1];
    }

    /**
     * 该方法没有抛出异常，因此不可以通过 @ExceptionTest 测试
     */
    @ExceptionTest(ArithmeticException.class)
    public static void testAnnocation3() {
    }

    public static void main(String[] args) throws Exception {
        int tests = 0;
        int passed = 0;
        Class testClass = Class.forName("com.hit.effective.chapter5.annotation.RunExceptionTests");
//        Class testClass = Class.forName(args[0]);
        for (Method method : testClass.getDeclaredMethods()) {
            // 判断类中的被 @Test 注解的方法
            if (method.isAnnotationPresent(ExceptionTest.class)) {
                tests++;
                try {
                    // 通过反射，执行被注解的方法
                    method.invoke(null);
                    passed++;
                } catch (InvocationTargetException warppedExc) {
                    Throwable exc = warppedExc.getCause();
                    Class<? extends Exception> excType = method.getAnnotation(ExceptionTest.class).value();
                    if (excType.isInstance(exc)) {
                        passed++;
                    } else {
                        System.out.printf("Test %s failed: expected %s, got %s%n", method, excType.getName(), exc);

                    }
                } catch (Exception exc) {
                    System.out.println("Invalid @Test: " + method);
                }
            }
        }
        System.out.printf("Passed: %d, Failed: %d%n", passed, tests - passed);
    }
}
