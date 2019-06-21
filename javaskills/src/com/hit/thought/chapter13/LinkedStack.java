package com.hit.thought.chapter13;

/**
 * author:Charies Gavin
 * date:2018/3/19,8:40
 * https:github.com/guobinhit
 * description:链式存储结构
 */
public class LinkedStack<T> {
    // 内部类，用于存储及维护链式结构
    private static class Node<U> {
        // 存储的值
        U item;

        // 存储下一个节点的元素
        Node<U> next;

        /**
         * 默认构造器
         */
        Node() {
            item = null;
            next = null;
        }

        /**
         * 含参构造器
         *
         * @param item
         * @param next
         */
        Node(U item, Node<U> next) {
            this.item = item;
            this.next = next;
        }

        // 判断栈中下一个元素是否为空
        boolean end() {
            return item == null && next == null;
        }
    }

    // 末端哨兵，用于监视末端元素
    private Node<T> top = new Node<T>();

    // 压栈
    public void push(T item) {
        /**
         * top 的结构示例为 top<item3,top<item2, top<item1, top<null, null>>>>
         * 栈中元素的性质为"先入后出，后入先出"，因此最后一个压栈的元素在最前面，也最先弹栈
         */
        top = new Node<T>(item, top);
    }

    // 弹栈
    public T pop() {
        // 弹栈元素为链式存储结构中的从前往后数第一个 item 元素
        T result = top.item;
        // 如果判断 top.end() 为 false，则说明栈中下一个元素非 null，因此末端哨兵下移
        if (!top.end()) {
            top = top.next;
        }
        return result;
    }

    public static void main(String[] args) {
        LinkedStack<String> linkedStack = new LinkedStack<String>();
        for (String str : "If I were boy".split(" ")) {
            linkedStack.push(str);
        }
        String s;
        while ((s = linkedStack.pop()) != null) {
            System.out.println(s);
        }
    }
}
