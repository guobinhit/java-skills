package com.hit.thought.chapter16.io;

import com.hit.thought.chapter16.Constants;

import java.io.*;

/**
 * author:Charies Gavin
 * date:2018/5/6,15:00
 * https:github.com/guobinhit
 * description:优化缓存输入和输出
 */
public class FileOutputShortcut {
    private static String file = Constants.FILE_ABSOLUTE_PATH + "FileOutputShortcut.out";

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(
                new StringReader(
                        BufferedInputFile.read(Constants.FILE_ABSOLUTE_PATH + "FileOutputShortcut.java")
                )
        );

        /**
         * 在 Java SE5 中，PrintWriter 新增了一个辅助构造器，
         * 使得我们不必在每次希望创建文本文件并向其中写入时，
         * 都去执行所有的装饰工作
         */
        PrintWriter out = new PrintWriter(file);

        // 用于显示行号
        int lineCount = 1;
        String s;
        while ((s = in.readLine()) != null) {
            out.println(lineCount++ + " : " + s);
        }
        out.close();
        System.out.println(BufferedInputFile.read(file));
    }
}
