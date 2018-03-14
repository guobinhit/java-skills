package com.hit.thought.chapter13;

/**
 * author:Charies Gavin
 * date:2018/3/12,9:10
 * https:github.com/guobinhit
 * description:
 */
public class LinkedStack<T> {
    private static class Node<U> {
        U item;
        Node<U> next;

        Node() {
            item = null;
            next = null;
        }

        Node(U item, Node<U> next) {
            this.item = item;
            this.next = next;
        }

        boolean end() {
            return item == null && next == null;
        }
    }

    private Node<T> top = new Node<T>();

    public void push(T item) {
        top = new Node<T>(item, top);
    }

    public T pop() {
        T result = top.item;
        if (!top.end()) {
            top = top.next;
        }
        return result;
    }

    public static void main(String[] args) {
        LinkedStack<String> ls = new LinkedStack<String>();
        for (String str : "if i were a boy".split(" ")) {
            ls.push(str);
        }
        String s;
        while ((s = ls.pop()) != null) {
            System.out.println(s);
        }
    }
}
