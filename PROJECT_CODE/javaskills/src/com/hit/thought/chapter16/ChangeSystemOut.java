package com.hit.thought.chapter16;

import java.io.PrintWriter;

/**
 * author:Charies Gavin
 * date:2018/6/26,9:14
 * https:github.com/guobinhit
 * description:改变输出流
 */
public class ChangeSystemOut {
    public static void main(String[] args) {
        /**
         * 需要使用 PrintWriter 两个参数的构造器，并将第二个参数设置为 true
         * 以便开启自动清空功能，否则，我们将看不到输出的内容
         */
        PrintWriter out = new PrintWriter(System.out, true);
        out.println("Hello World");
    }
}
