package com.hit.thought.chapter16.io;

import com.hit.thought.chapter16.Constants;

import java.io.*;

/**
 * author:Charies Gavin
 * date:2018/6/25,8:38
 * https:github.com/guobinhit
 * description:存储和恢复数据
 */
public class StoringAndRecoveringData {
    public static void main(String[] args) throws IOException {
        DataOutputStream out = new DataOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(Constants.FILE_ABSOLUTE_PATH + "Data.txt")
                )
        );

        out.writeDouble(3.14159);
        out.writeUTF("That was pi");
        out.writeDouble(1.41413);
        out.writeUTF("Square root  of 2");
        out.close();

        DataInputStream in = new DataInputStream(
                new BufferedInputStream(
                        new FileInputStream(Constants.FILE_ABSOLUTE_PATH + "Data.txt")
                )
        );

        System.out.println(in.readDouble());
        // Only readUTF() will recover the Java-UTF String proper;y
        System.out.println(in.readUTF());
        System.out.println(in.readDouble());
        System.out.println(in.readUTF());
    }
}
