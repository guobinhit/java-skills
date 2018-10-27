package com.hit.thought.chapter11;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author:Charies Gavin
 * date:2018/3/11,16:30
 * https:github.com/guobinhit
 * description:正则表达式对象
 */
public class RegularExpressionExample {
    public static void main(String[] args) {
        obtainRegex();
//        replaceRegex();
//        splitRegex();
//        matchRegex();
    }

    /**
     * 获取
     */
    public static void obtainRegex() {
        String str = "Hi girl, I like you!";
        String regex = "\\b[a-z]{3}\\b";

        // 将正则表达式封装成对象
        Pattern pattern = Pattern.compile(regex);

        // 使用 Matcher 对象的方法对字符串进行操作，为了获取三个字母组成的单词，可以用查找 find() 方法
        Matcher matcher = pattern.matcher(str);
        System.out.println(str);

        while (matcher.find()) {
            // 获取匹配的字符串子序列
            System.out.println(matcher.group());
        }
    }

    /**
     * 替换
     */
    public static void replaceRegex() {
        // 替换字符串中重叠的字母为单独一个字母
        String str = "Harbingggggongyedaxxxxxue";
        str = str.replaceAll("(.)\\1+", "$1");
        System.out.println(str);

        // 对手机号进行模糊处理
        String tel = "18800001234";
        tel = tel.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        System.out.println(tel);
    }

    /**
     * 分割
     */
    public static void splitRegex() {
        // 以字符串中的重叠字母进行分割
        String str = "Harbiniiigongyejjjjdaxue";
        String[] names = str.split("(.)\\1+");

        for(String name : names){
            System.out.println(name);
        }
    }

    /**
     * 匹配
     */
    public static void matchRegex() {
        // 验证该手机号是否满足号码规则
        String tel = "18800005238";
        String regex = "1[358]\\d{9}";
        boolean b = tel.matches(regex);
        System.out.println(tel + " : " + b);
    }
}
