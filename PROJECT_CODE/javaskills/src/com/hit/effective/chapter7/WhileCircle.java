package com.hit.effective.chapter7;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * author:Charies Gavin
 * date:2018/6/11,8:33
 * https:github.com/guobinhit
 * description:While循环
 */
public class WhileCircle {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        List<String> list2 = new ArrayList<String>();

        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            System.out.println("列表一中的元素：" + it.next());
        }

        // 以下循环包含一个BUG
        Iterator<String> it2 = list2.iterator();
        while (it.hasNext()) {
            System.out.println("列表二中的元素：" + it.next());
        }
    }
}
