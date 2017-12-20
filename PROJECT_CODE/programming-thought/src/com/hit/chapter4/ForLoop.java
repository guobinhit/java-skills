package com.hit.chapter4;

import java.util.Arrays;

/**
 * author:Charies Gavin
 * date:2017/12/20,9:43
 * https:github.com/guobinhit
 * description:测试两种 for 循环方法
 */
public class ForLoop {
    public static void main(String[] args) {
        // 创建并初始化一个整型数据
        int[] arr = new int[]{2, 0, 1, 5, 11, 20};

        System.out.println("普通的 for 循环方法：");

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }

        System.out.println();
        System.out.println("高级的 for 循环方法：");

        for (int i : arr) {
            System.out.print(i + " ");

        }

        System.out.println();

        System.out.println("用 Arrays 的 toString() 方法打印数组：");
        System.out.println(Arrays.toString(arr));
    }
}
