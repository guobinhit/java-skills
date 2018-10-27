package com.hit.thought.chapter14;

import java.util.Arrays;

/**
 * author:Charies Gavin
 * date:2018/4/22,11:30
 * https:github.com/guobinhit
 * description:多维数组
 */
public class MultidimensionalArray {
    public static void main(String[] args) {
        // 初始化多维数组
        int[][] multi = {
                {1, 1, 2, 0},
                {2, 0, 1, 5}
        };

        System.out.println("打印多维数组：" + Arrays.deepToString(multi));
    }
}
