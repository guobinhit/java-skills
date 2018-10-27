package com.hit.thought.chapter14;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * author:Charies Gavin
 * date:2018/4/23,9:30
 * https:github.com/guobinhit
 * description:测试队列
 */
public class QueueDemo {
    public static void main(String[] args) {
        Queue<Integer> integerQueue = new LinkedList<Integer>();

        Random random = new Random(47);

        for (int i = 0; i < 5; i++) {
            // offer() 将元素插入对尾
            integerQueue.offer(random.nextInt(i + 10));
        }

        printQueue(integerQueue);

        Queue<String> stringQueue = new LinkedList<String>();

        for (String str : "May the force with you".split(" ")) {
            // offer() 将元素插入对尾
            stringQueue.offer(str);
        }

        printQueue(stringQueue);
    }

    private static void printQueue(Queue queue) {
        // peek() 在移除元素的情况下，返回对头元素，如果对头元素不为 null，则说明队列不为空
        while (queue.peek() != null) {
            // remove() 移除并返回对头元素
            System.out.println(queue.remove() + " ");
        }
        System.out.println();
    }
}
