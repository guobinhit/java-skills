package com.hit.thought.chapter14;

import java.util.Arrays;

/**
 * author:Charies Gavin
 * date:2018/4/22,10:10
 * https:github.com/guobinhit
 * description:对象数组和基本类型数组
 */
public class ArrayOptions {
    public static void main(String[] args) {
        // 对象数组，三种初始化方式
        String[] strs = new String[10];
        String[] strs2 = new String[]{"Z", "O", "R", "A"};
        String[] strs3 = {"G", "A", "V", "I", "N"};

        System.out.println("strs: " + Arrays.toString(strs));
        System.out.println("strs2: " + Arrays.toString(strs2));
        System.out.println("strs3: " + Arrays.toString(strs3));

        strs = strs2;
        System.out.println("strs = strs2: " + Arrays.toString(strs));

        strs[0] = "K";
        System.out.println("strs & strs[0] = \"K\": " + Arrays.toString(strs));
        System.out.println("strs2: " + Arrays.toString(strs2));

        System.out.println("-------------------");

        // 基本类型数组，三种初始化方式
        int[] ints = new int[10];
        int[] ints2 = new int[]{1, 1, 2, 0};
        int[] ints3 = {2, 0, 1, 5};

        System.out.println("ints: " + Arrays.toString(ints) + " ints.length() = " + ints.length);
        System.out.println("ints2: " + Arrays.toString(ints2) + " ints2.length() = " + ints2.length);
        System.out.println("ints3: " + Arrays.toString(ints3) + " ints3.length() = " + ints3.length);

        for (int i = 0; i < ints2.length; i++) {
            ints[i] = ints2[i];
        }

        // length() 表示数组的长度，而不是数组中实际保持元素的个数
        System.out.println("ints: " + Arrays.toString(ints) + " ints.length() = " + ints.length);
    }
}
