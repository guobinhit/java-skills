package com.hit.thought.chapter11;

import java.util.Arrays;

/**
 * author:Charies Gavin
 * date:2018/3/11,16:00
 * https:github.com/guobinhit
 * description:测试 String 类支持正则表达式的方法
 */
public class StringRegularExpression {
    public static void main(String[] args) {
        String str = "123_456_789";
        // matches() 方法用于匹配字符串
        System.out.println("匹配字符串：" + str.matches("-?\\S+"));
        // split() 方法用于分割字符串
        System.out.println("分割字符串：" + Arrays.toString(str.split("_")));
        // replace() 方法用于替换字符串
        System.out.println("替换字符串：" + str.replace("_", "$"));
    }
}
