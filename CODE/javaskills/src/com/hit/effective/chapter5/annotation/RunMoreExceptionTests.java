package com.hit.effective.chapter5.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * author:Charies Gavin
 * date:2018/6/8,8:45
 * https:github.com/guobinhit
 * description:测试MoreExceptionTest注解
 */
public class RunMoreExceptionTests {
    /**
     * 该方法为抛出 ArithmeticException 异常，因此可以通过 @ExceptionTest 测试
     */
    @MoreExceptionTest({IndexOutOfBoundsException.class, NullPointerException.class})
    public static void testAnnocation() {
        List<String> list = new ArrayList<String>();
        list.add(5, null);
    }


    public static void main(String[] args) throws Exception {
        int tests = 0;
        int passed = 0;
        Class testClass = Class.forName("com.hit.effective.chapter5.annotation.RunMoreExceptionTests");
//        Class testClass = Class.forName(args[0]);
        for (Method method : testClass.getDeclaredMethods()) {
            // 判断类中的被 @Test 注解的方法
            if (method.isAnnotationPresent(MoreExceptionTest.class)) {
                tests++;
                try {
                    // 通过反射，执行被注解的方法
                    method.invoke(null);
                    System.out.printf("Test %s failed: no exception%n", method);
                } catch (InvocationTargetException warppedExc) {
                    Throwable exc = warppedExc.getCause();
                    Class<? extends Exception>[] excTypes = method.getAnnotation(MoreExceptionTest.class).value();
                    int oldPassed = passed;
                    for (Class<? extends Exception> excType : excTypes) {
                        if (excType.isInstance(exc)) {
                            passed++;
                            break;
                        }
                    }
                    if (passed == oldPassed) {
                        System.out.printf("Test %s failed: %s %n", method, exc);
                    }

                } catch (Exception exc) {
                    System.out.println("Invalid @Test: " + method);
                }
            }
        }
        System.out.printf("Passed: %d, Failed: %d%n", passed, tests - passed);
    }
}
