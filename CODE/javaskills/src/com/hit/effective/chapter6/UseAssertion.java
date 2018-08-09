package com.hit.effective.chapter6;

/**
 * author:Charies Gavin
 * date:2018/6/8,9:10
 * https:github.com/guobinhit
 * description:使用断言校验参数的有效性
 */
public class UseAssertion {

    public static void main(String[] args) {
        int[] array = {3, -2, 1, -1, 0, 2, -3};
        System.out.println("Before sort:");
        printArray(array);

        bubbleSort(null);

        System.out.println("After sort:");
        printArray(array);
    }

    /**
     * 冒泡排序
     *
     * @param array
     */
    private static void bubbleSort(int[] array) {
        // 使用断言
        assert array != null;

        // 冒泡排序核心算法
        for (int i = array.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    // 打印数组的便利方法
    public static void printArray(int[] arr) {
        System.out.print("{");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("}");
    }
}

