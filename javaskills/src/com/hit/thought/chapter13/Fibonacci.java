package com.hit.thought.chapter13;

/**
 * author:Charies Gavin
 * date:2018/3/19,9:15
 * https:github.com/guobinhit
 * description:斐波那契数列
 */
public class Fibonacci implements Generator<Integer> {
    private int count = 0;

    @Override
    public Integer next() {
        return fin(count++);
    }

    private int fin(int n) {
        if (n < 2) {
            return 1;
        } else {
            return fin(n - 2) + fin(n - 1);
        }
    }

    public static void main(String[] args) {
        Fibonacci fibonacci = new Fibonacci();
        for (int i = 0; i < 20; i++) {
            System.out.println(fibonacci.next() + " ");
        }
    }
}
