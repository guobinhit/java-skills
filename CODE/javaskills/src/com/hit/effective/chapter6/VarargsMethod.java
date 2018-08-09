package com.hit.effective.chapter6;

/**
 * author:Charies Gavin
 * date:2018/6/9,11:11
 * https:github.com/guobinhit
 * description:可变参数
 */
public class VarargsMethod {
    public static int sum(int... args) {
        int sum = 0;
        for (int arg : args) {
            sum += arg;
        }
        return sum;
    }

    public static int min(int firstArg, int... remainingArgs) {
        int min = firstArg;
        for (int arg : remainingArgs) {
            if (arg < min) {
                min = arg;
            }
        }
        return min;
    }

    public static void main(String[] args) {
        System.out.println(sum(1, 2, 3, 4, 5));

        System.out.println(min(0, 1, 2, 3, -1));
    }
}
