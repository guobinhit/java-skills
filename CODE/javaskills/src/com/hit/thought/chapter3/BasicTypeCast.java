package com.hit.thought.chapter3;

/**
 * author:Charies Gavin
 * date:2017/12/09,16:50
 * https:github.com/guobinhit
 * description:数字类型转换
 */
public class BasicTypeCast {
    public static void main(String[] args) {
        // 定义变量并初始化
        float a = 0.3F, b = 0.6F;
        double c = 0.8D, d = 0.1D;
        // 调用 Math.round() 方法获取四舍五入的结果
        System.out.println("Math.rund(a): " + Math.round(a));
        System.out.println("Math.rund(b): " + Math.round(b));
        System.out.println("Math.rund(c): " + Math.round(c));
        System.out.println("Math.rund(d): " + Math.round(d));
    }
}
