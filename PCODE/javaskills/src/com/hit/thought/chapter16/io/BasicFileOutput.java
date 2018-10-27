package com.hit.thought.chapter16.io;

import com.hit.thought.chapter16.Constants;

import java.io.*;

/**
 * author:Charies Gavin
 * date:2018/5/6,14:30
 * https:github.com/guobinhit
 * description:缓存输入与输出
 */
public class BasicFileOutput {
    private static String file = Constants.FILE_ABSOLUTE_PATH + "BasicFileOutput.out";

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(
                new StringReader(
                        BufferedInputFile.read(Constants.FILE_ABSOLUTE_PATH + "BasicFileOutput.java")
                )
        );

        /**
         * FileWriter 对象可以向文件写入数据
         */
        PrintWriter out = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(file)
                )
        );

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
