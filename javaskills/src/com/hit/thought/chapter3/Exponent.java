package com.hit.thought.chapter3;

/**
 * author:Charies Gavin
 * date:2017/12/09,16:30
 * https:github.com/guobinhit
 * description:指数计数法
 */
public class Exponent {
    public static void main(String[] args) {
//        float loveu = 5e2f;
        /**
         * 在使用 指数计数法 的时候，编辑器默认将指数作为双精度数(double)来处理，
         * 如果初始化值后面没有 f 或者 F 的话，编译器会报错
         * 提示我们必须使用类型转换将 double 转换为 float 类型
         */
        float loveu = 5.21e2F;
        System.out.println(loveu);
    }
}
