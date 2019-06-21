package com.hit.thought.chapter14;

import java.util.LinkedList;

/**
 * author:Charies Gavin
 * date:2018/4/23,9:00
 * https:github.com/guobinhit
 * description:用LinkedList实现栈
 */
public class Stack<T> {
    private LinkedList<T> storage = new LinkedList<T>();

    // 压栈方法，用于存入元素
    public void push(T element) {
        storage.addFirst(element);
    }

    // 获取栈中第一个存入的元素
    public T peek() {
        return storage.getFirst();
    }

    // 弹栈方法，移除最后一个入栈元素
    public T pop() {
        return storage.removeFirst();
    }

    // 判断栈是否为空
    public boolean isEmpty() {
        return storage.isEmpty();
    }

    // 打印栈
    public String toString() {
        return storage.toString();
    }

    public static void main(String[] args) {
        Stack<String> stack = new Stack<String>();

        // 循环入栈
        for (String str : "Hi girl what is your name".split(" ")) {
            stack.push(str);
        }

        // 循环出栈
        while (!stack.isEmpty()) {
            System.out.println(stack.pop() + " ");
        }
    }
}
