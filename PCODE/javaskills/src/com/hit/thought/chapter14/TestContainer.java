package com.hit.thought.chapter14;

import java.util.*;

/**
 * author:Charies Gavin
 * date:2018/4/23,8:30
 * https:github.com/guobinhit
 * description:测试容器
 */
public class TestContainer {
    public static void main(String[] args) {
        System.out.println(fill(new ArrayList<String>()));
        System.out.println(fill(new LinkedList<String>()));
        System.out.println(fill(new HashSet<String>()));
        System.out.println(fill(new TreeSet<String>()));
        System.out.println(fill(new LinkedHashSet<String>()));
        System.out.println(fill(new HashMap<String, String>()));
        System.out.println(fill(new TreeMap<String, String>()));
        System.out.println(fill(new LinkedHashMap<String, String>()));
    }

    /**
     * 填充Collection
     *
     * @param collection
     * @return Collection
     */
    private static Collection fill(Collection<String> collection) {
        collection.add("Baidu");
        collection.add("Alibaba");
        collection.add("Tencent");
        collection.add("Twitter");
        collection.add("Google");
        collection.add("Facebook");
        collection.add("Baidu");
        return collection;
    }

    /**
     * 填充Map
     *
     * @param map
     * @return Map
     */
    private static Map fill(Map<String, String> map) {
        map.put("Robin Li", "Baidu");
        map.put("Jack Ma", "Alibaba");
        map.put("Pony", "Tencent");
        map.put("Evan Williams", "Twitter");
        map.put("Larry Page", "Google");
        map.put("Mark Zuckerberg", "Facebook");
        map.put("Robin Li", "Baidu");
        return map;
    }
}
