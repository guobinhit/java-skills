package com.hit.concurrent.chapter3;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * author:Charies Gavin
 * date:2018/11/17,10:46
 * https:github.com/guobinhit
 * description:隐藏迭代器
 */
public class HiddenIterate {
    private final Set<Integer> set = new HashSet<Integer>();

    public synchronized void add(Integer i) {
        set.add(i);
    }

    public synchronized void remove(Integer i) {
        set.remove(i);
    }

    public void addTenThings() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            add(random.nextInt());
        }
        System.out.println("DEBUG: added ten elements to " + set);
    }
}
