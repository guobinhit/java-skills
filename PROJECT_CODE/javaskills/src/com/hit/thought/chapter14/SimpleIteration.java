package com.hit.thought.chapter14;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * author:Charies Gavin
 * date:2018/4/22,14:00
 * https:github.com/guobinhit
 * description:测试迭代器效果
 */
public class SimpleIteration {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();

        list.add("Zora");
        list.add("I");
        list.add("love");
        list.add("you");

        // 获取容器的迭代器对象
        Iterator it = list.iterator();

        // 判断序列是否还有元素
        while (it.hasNext()) {
            // 获取下一个元素，首次调用获取第一个元素
            String str = (String) it.next();
            System.out.println(str);
        }
    }
}
