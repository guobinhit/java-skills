package com.hit.thought.chapter16.io;

import com.hit.thought.chapter16.Constants;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * author:Charies Gavin
 * date:2018/5/6,14:00
 * https:github.com/guobinhit
 * description:测试 EOF 异常
 */
public class TestEOF {
    public static void main(String[] args) throws IOException {
        DataInputStream in = new DataInputStream(
                new BufferedInputStream(
                        new FileInputStream(Constants.FILE_ABSOLUTE_PATH + "TestEOF.java")
                )
        );

        /**
         * 如果我们从 DataInputStream 用 readByte() 一次一个字节地读取字符，
         * 那么任何字节的值都是合法的结果，因此返回值不能用来检查输入是否结束。
         *
         * 相反，我们可以使用 available() 方法来查看还有多少可供存取的字符，
         * 因此，可以避免对 EOFException 的捕获和判断。
         *
         * 但是，available() 的工作方式会随着所读取的媒介类型的不同而有所不同；
         * 字面意思就是"在没有阻塞的情况下所能读取的字节数"。
         * 对于文件，意味着整个文件；对于不同类型的流，可能就不是这样的，因此要谨慎使用。
         *
         * 虽然我们可以通过捕获异常来检测输入的末尾，但是，使用异常进行流控制，被认为是对异常特性的错误使用
         */
        while (in.available() != 0) {
            System.out.println((char) in.readByte());
        }
    }
}
