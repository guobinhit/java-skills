package com.hit.thought.chapter15;

import java.util.Random;

/**
 * author:Charies Gavin
 * date:2018/5/5,10:10
 * https:github.com/guobinhit
 * description:预测
 */
public class Prediction {
    private static Random random = new Random(47);
    private boolean shadow = random.nextDouble() > 0.5;

    @Override
    public String toString() {
        if (shadow) {
            return "Six more weeks of Winter!";
        } else {
            return "Early String!";
        }
    }
}
