package com.hit.thought.chapter16.io;

import com.hit.thought.chapter16.Constants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * author:Charies Gavin
 * date:2018/5/6,13:20
 * https:github.com/guobinhit
 * description:缓冲输入流
 */
public class BufferedInputFile {
    /**
     * 将异常抛到控制台
     *
     * @param filename 此参数为文件的全路径，例如：/Users/bin.guo/Documents/GitRepo/java-skills/PROJECT_CODE/javaskills/src/com/hit/thought/chapter16/io/BufferedInputFile.java
     * @return 文件内容
     * @throws IOException
     */
    public static String read(String filename) throws IOException {
        // 按行读取输入
        BufferedReader in = new BufferedReader(new FileReader(filename));
        String s;
        StringBuilder sb = new StringBuilder();
        while ((s = in.readLine()) != null) {
            sb.append(s + "\n");
        }
        // 特别注意：一定要记得关闭流
        in.close();
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        System.out.println(read(Constants.FILE_ABSOLUTE_PATH + "BufferedInputFile.java"));
    }
}
