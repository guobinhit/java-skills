package com.hit.effective.chapter1;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * author:Charies Gavin
 * date:2018/5/27,10:15
 * https:github.com/guobinhit
 * description:简单栈实现(含有内存泄漏危险)
 */
public class SimpleStack {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public SimpleStack() {
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    /**
     * 压栈
     *
     * @param e
     */
    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    /**
     * 弹栈
     *
     * @return
     */
    public Object pop() {
        if (size == 0) {
            throw new EmptyStackException();
        } else {
            Object result = elements[--size];
            elements[size] = null;
            return result;

            // 如果仅返回 elements[--size]，栈内部会维护着过期引用，有内存泄漏的风险
            // return elements[--size];
        }
    }

    /**
     * 保证栈的容量，在必要时，进行自动扩容
     */
    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }

    public static void main(String[] args) {
        SimpleStack simpleStack = new SimpleStack();
        for (String string : "1,2,3,4,5,6".split(",")) {
            simpleStack.push(string);
            System.out.print(string + " ");
        }

        System.out.println();

        while (simpleStack.size > 0) {
            System.out.print(simpleStack.pop() + " ");
        }
    }
}
