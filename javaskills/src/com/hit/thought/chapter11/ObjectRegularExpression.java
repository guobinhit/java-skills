package com.hit.thought.chapter11;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author:Charies Gavin
 * date:2018/3/11,19:12
 * https:github.com/guobinhit
 * description:创建正则表达式对象
 */
public class ObjectRegularExpression {
    /**
     * 自定义编译期常量
     */
    public static final String POEM = "If you were a teardrop in my eye,\n" +
            "For fear of losing you,\n" +
            "I would never cry.\n" +
            "And if the golden sun,\n" +
            "Should cease to shine its light,\n" +
            "Just one smile from you,\n" +
            "would make my whole world bright.";

    public static void main(String[] args) {
        // 定义正则表达式字符串，含义为：找出每行后三个单词
        String regex = "(?m)(\\S+)\\s+((\\S+)\\s+(\\S+))$";
        // 编译正则表达式字符串，获取 Pattern 对象
        Pattern pattern = Pattern.compile(regex);
        // 调用 Pattern 对象的 matcher() 方法，获取 Matcher 对象
        Matcher matcher = pattern.matcher(POEM);
        // 使用 find() 查找多个匹配结果
        while (matcher.find()) {
            // groupCount() 方法返回该匹配器的模式中的分组数目，不包括第 0 组
            for (int i = 0; i <= matcher.groupCount() ; i++) {
                // group(i) 返回前一次匹配的第 0 组，即整个匹配
                System.out.println("[" + matcher.group(i) + "]");
            }
        }
    }
}
