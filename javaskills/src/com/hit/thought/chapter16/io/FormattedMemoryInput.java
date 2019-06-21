package com.hit.thought.chapter16.io;

import com.hit.thought.chapter16.Constants;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

/**
 * author:Charies Gavin
 * date:2018/5/6,13:45
 * https:github.com/guobinhit
 * description:格式化内存输入
 */
public class FormattedMemoryInput {
    public static void main(String[] args) throws IOException {
        try {
            /**
             * 要读取格式化数据，可以使用 DataInputStream，它是一个面向字节的 I/O 类；
             * 此外，必须向 ByteArrayInputStream 提供字节数组
             */
            DataInputStream in = new DataInputStream(
                    new ByteArrayInputStream(
                            BufferedInputFile.read(Constants.FILE_ABSOLUTE_PATH + "FormattedMemoryInput.java")
                                    .getBytes()
                    ));
            while (true) {
                System.out.println((char) in.readByte());
            }
        } catch (EOFException e) { // EOF 表示 end of file，此异常表示文件已读取完毕
            System.out.println("End of Stream");
        }
    }
}
