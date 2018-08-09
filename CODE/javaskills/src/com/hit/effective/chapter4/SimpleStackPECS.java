package com.hit.effective.chapter4;

import java.util.*;

/**
 * author:Charies Gavin
 * date:2018/6/5,8:39
 * https:github.com/guobinhit
 * description:有限制通配符类型
 */
public class SimpleStackPECS<E> {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public SimpleStackPECS() {
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    /**
     * 压栈
     *
     * @param e
     */
    public void push(E e) {
        ensureCapacity();
        elements[size++] = e;
    }

    /**
     * 弹栈
     *
     * @return
     */
    public E pop() {
        if (size == 0) {
            throw new EmptyStackException();
        } else {
            Object result = elements[--size];
            elements[size] = null;
            return (E) result;
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

    /**
     * 判断栈内元素是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 压栈，将参数中的所有元素压入栈
     *
     * @param src
     */
    public void pushAll(Iterable<? extends E> src) {
        for (E e : src) {
            push(e);
        }
    }

    /**
     * 弹栈，将栈内所有原弹出栈，并添加到指定集合
     *
     * @param dst
     */
    public void popAll(Collection<? super E> dst) {
        while (!isEmpty()) {
            dst.add(pop());
        }
    }

    public static void main(String[] args) {
        SimpleStackPECS<Number> simpleStack = new SimpleStackPECS<Number>();

        List<Integer> integerList = new ArrayList<Integer>();
        integerList.add(5);
        integerList.add(2);
        integerList.add(1);

        Iterable<Integer> integers = integerList;

        simpleStack.pushAll(integers);

        Collection<Object> objects = new ArrayList<Object>();

        simpleStack.popAll(objects);
        for (Object s : objects) {
            System.out.println(s);
        }
    }
}
