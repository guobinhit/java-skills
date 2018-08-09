package com.hit.thought.chapter15;

import java.util.*;

/**
 * author:Charies Gavin
 * date:2018/5/5,9:30
 * https:github.com/guobinhit
 * description:填充容器
 */
public class FullContainer {
    public static void main(String[] args) {
        // 使用 Collections 的 fill() 方法填充 List 对象
        List<String> stringList = new ArrayList<String>(Collections.nCopies(10, "Hello"));
        Collections.fill(stringList, "World");
        System.out.println(stringList);

        // 将一个容器对象当做参数传给另一个容器对象的构造器
        List<String> fullList = new ArrayList<String>(stringList);
        System.out.println(fullList);

        // 打印 TreeSet 的比较器
        SortedSet sortedSet = new TreeSet();
        System.out.println(sortedSet.comparator());
    }
}
