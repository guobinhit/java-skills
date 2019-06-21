package com.hit.thought.chapter15;

import java.util.*;

/**
 * author:Charies Gavin
 * date:2018/5/5,18:20
 * https:github.com/guobinhit
 * description:只读容器
 */
public class ReadOnly {
    public static void main(String[] args) {
        Collection<String> collection = new ArrayList<String>();

        collection.add("Blank Panther");
        collection.add("Doctor Strange");
        collection.add("Iron Man");

        List<String> list = Collections.unmodifiableList(new ArrayList<String>(collection));
        Set<String> set = Collections.unmodifiableSet(new HashSet<String>(collection));

        System.out.println(list);
        System.out.println(set);

        // 下面的添加语句会引起 UnsupportedOperationException 异常
        list.add("Captain America");
        System.out.println(list);
    }
}
