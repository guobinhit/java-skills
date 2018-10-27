package com.hit.thought.chapter16.io;

import com.hit.thought.chapter16.Constants;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * author:Charies Gavin
 * date:2018/6/25,8:49
 * https:github.com/guobinhit
 * description:读取随机访问文件
 */
public class UsingRandomAccessFIle {
    static String file = Constants.FILE_ABSOLUTE_PATH + "rtest.dat";

    /**
     * 读取文件并打印
     *
     * @throws IOException
     */
    static void display() throws IOException {
        /**
         * RandomAccessFile 有两个构造器参数，分别为：
         * 1、fileName：表示文件名
         * 2、openMethod：表示打开文件的方式
         * 对于第二个参数，有两个指定的值，分别为：
         * r：表示以"只读"方式打开文件
         * rw：表示以"读写"方法打开文件
         */
        RandomAccessFile rf = new RandomAccessFile(file, "r");
        for (int i = 0; i < 7; i++) {
            System.out.println("Value " + i + ": " + rf.readDouble());
        }
        System.out.println(rf.readUTF());
        rf.close();
    }

    public static void main(String[] args) throws IOException {
        // 写入文件
        RandomAccessFile rf = new RandomAccessFile(file, "rw");
        for (int i = 0; i < 7; i++) {
            rf.writeDouble(i * 1.414);
        }
        rf.writeUTF("The end of the file");
        rf.close();

        display();

        // 修改文件
        rf = new RandomAccessFile(file, "rw");
        // 因为 double 总是 8 字节长，为了寻找第 5 个双精度值，我们需要用 5*8 来查找位置
        rf.seek(6 * 8);
        rf.writeDouble(47.0001);
        rf.close();

        display();
    }
}
