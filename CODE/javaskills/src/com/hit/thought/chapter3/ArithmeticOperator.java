package com.hit.thought.chapter3;

import java.util.Random;

/**
 * author:Charies Gavin
 * date:2017/12/09,13:50
 * https:github.com/guobinhit
 * description:测试算术操作符
 */
public class ArithmeticOperator {
    public static void main(String[] args) {
        /**
         * 测试随机整数除法
         */
        randomDivide();
    }

    /**
     * 随机整数除法
     */
    private static void randomDivide() {
        Random random = new Random();
        int x = random.nextInt(10) + 1;
        int y = random.nextInt(10) + 1;
        int z = x / y;
        System.out.println("整数除法，默认省略结果的小数位：" + x + " / " + y + " = " + z);
    }
}
