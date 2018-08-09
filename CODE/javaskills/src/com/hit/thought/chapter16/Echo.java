package com.hit.thought.chapter16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * author:Charies Gavin
 * date:2018/6/26,9:07
 * https:github.com/guobinhit
 * description:回显控制台输入的内容
 */
public class Echo {
    public static void main(String[] args) throws IOException {
        System.out.println("请输入需要回显的内容：");

        // 由于 System.in 是未经包装的标准输入流，因此将其转为 BufferReader
        BufferedReader stdin = new BufferedReader(
                new InputStreamReader(System.in)
        );

        String s;

        // 当程序收到 Ctrl + Z 的时候，终止程序
        while ((s = stdin.readLine()) != null && s.length() != 0) {
            System.out.println(s);
        }
    }
}
