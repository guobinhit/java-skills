package com.hit.chapter9;

import java.util.Arrays;

/**
 * @author bin.guo
 * @Copyright 易宝支付(YeePay)
 * @date 1/22/18,4:18 PM
 * @description
 */
public class StringTest {
    public static void main(String[] args) {
        String[] strr1;
        String[] strr2;
        String[] strr3;
        String[] strr4;
        String str = "abc_def_g_h_i";
        for (int i = 2; i <7; i++) {
            strr1 = str.split("_", i);
            System.out.println("Number:" + i);
            System.out.println(Arrays.toString(strr1));
        }
//        }
//        strr1 = str.split("_", 4);
//        strr2 = str.split("_", 2);
//        strr3 = str.split("_", 5);
//        System.out.println("strr1:" + Arrays.toString(strr1));
//        System.out.println("strr2:" + Arrays.toString(strr2));
    }
}
