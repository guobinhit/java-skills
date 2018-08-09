package com.hit.thought.chapter12;

import java.util.ArrayList;
import java.util.List;

/**
 * author:Charies Gavin
 * date:2018/3/18,17:20
 * https:github.com/guobinhit
 * description:检查类类型
 */
public class CheckClass {
    public static void main(String[] args) {
        Letter a = new Letter("a");
        Letter b = new Letter("b");
        Letter c = new Letter("c");

//        if (a instanceof Letter) {
//            a.printlnName();
//        }
//        if (b instanceof Letter) {
//            b.printlnName();
//        }
//        if (c instanceof Letter) {
//            c.printlnName();
//        }

        List<Letter> letters = new ArrayList<Letter>();

        letters.add(a);
        letters.add(b);
        letters.add(c);

        // 获取 Letter 类对象
        Class letterClass = Letter.class;

        // 循环 letters 列表
        for (Letter letter : letters) {
            // 动态测试对象类型
            if (letterClass.isInstance(letter)) {
                letter.printlnName();
            }
        }
    }

}

class Letter {
    String name;

    public Letter(String name) {
        this.name = name;
    }

    public void printlnName() {
        System.out.println("This is a Letter class instance: " + name);
    }
}