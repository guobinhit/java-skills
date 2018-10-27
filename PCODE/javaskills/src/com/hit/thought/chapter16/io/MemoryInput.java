package com.hit.thought.chapter16.io;

import com.hit.thought.chapter16.Constants;

import java.io.IOException;
import java.io.StringReader;

/**
 * author:Charies Gavin
 * date:2018/5/6,13:30
 * https:github.com/guobinhit
 * description:内存输入
 */
public class MemoryInput {
    public static void main(String[] args) throws IOException {
        StringReader in = new StringReader(BufferedInputFile.read(Constants.FILE_ABSOLUTE_PATH + "MemoryInput.java"));
        int c;
        // 此对象用于积累文件的内容，与 while 循环输出的内容做对比
        StringBuilder sb = new StringBuilder();
        while ((c = in.read()) != -1) {
            sb.append((char) c);

            /**
             * read() 方法是以 int 形式返回下一个字节，
             * 因此必须将类型转换为 char 才能正确打印
             */
            System.out.println((char) c);
        }
        System.out.println(sb);
    }
}
