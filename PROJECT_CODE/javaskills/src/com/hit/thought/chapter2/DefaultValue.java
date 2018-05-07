package com.hit.thought.chapter2;

/**
 * author:Charies Gavin
 * date:2017/12/05,8:50
 * https:github.com/guobinhit
 * description:测试默认值
 */
public class DefaultValue {
    /**
     * 定义基本数据类型成员变量
     */
    public static boolean aBoolean;
    public static char aChar;
    public static byte aByte;
    public static short aShort;
    public static int anInt;
    public static long aLong;
    public static float aFloat;
    public static double aDouble;

    public static int defaultValue(){
        int x = 5211314;
//        int x;
        return x;
    }

    public static void main(String[] args) {
        /**
         * 直接输出未手动初始化的成员变量进行测试
         */
        System.out.println("aBoolean : " + aBoolean + " aChar : " + aChar + " aByte : " + aByte + " aShort : " + aShort
                + " anInt : " + anInt + " aLong : " + aLong + " aFloat : " + aFloat + " aDouble : " + aDouble);

        /**
         * 直接输出在方法中未初始化的局部变量，编译器会报错
         */
        System.out.println(defaultValue());
    }
}
