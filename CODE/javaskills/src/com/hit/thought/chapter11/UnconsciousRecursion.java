package com.hit.thought.chapter11;

import java.util.ArrayList;
import java.util.List;

/**
 * author:Charies Gavin
 * date:2018/3/11,15:20
 * https:github.com/guobinhit
 * description:无意识递归调用
 */
public class UnconsciousRecursion {
    public static void main(String[] args) {
        List<UnconsciousRecursion> list = new ArrayList<UnconsciousRecursion>();
        for (int i = 0; i < 5; i++) {
            UnconsciousRecursion ur = new UnconsciousRecursion();
            list.add(ur);
        }
        System.out.println(list);
    }

    /**
     * 由 this 导致的递归调用
     */
    @Override
    public String toString(){
        return "UnconsciousRecursion address: " + this + "\n";
    }

    /**
     * 获取对象的内存地址，应该用 super.toString() 方法而非 this
     */
//    @Override
//    public String toString(){
//        return "UnconsciousRecursion address: " + super.toString() + "\n";
//    }
}
