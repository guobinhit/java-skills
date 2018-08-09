package com.hit.thought.chapter16.nio;

import com.hit.thought.chapter12.reflect.B;
import com.hit.thought.chapter16.Constants;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author bin.guo
 * @Copyright 易宝支付(YeePay)
 * @date 6/28/18,9:21 AM
 * @description
 */
public class GetChannel {
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) throws IOException {
        // Write a file
        FileChannel fc = new FileOutputStream(
                Constants.FILE_ABSOLUTE_PATH_NIO + "data.txt").getChannel();
        fc.write(ByteBuffer.wrap("Hello World ".getBytes()));
        fc.close();

        // Add to the end of the file
        fc = new RandomAccessFile(
                Constants.FILE_ABSOLUTE_PATH_NIO + "data.txt", "rw").getChannel();
        fc.position(fc.size());
        fc.write(ByteBuffer.wrap("See U!".getBytes()));
        fc.close();

        // Read the file
        fc = new FileInputStream(
                Constants.FILE_ABSOLUTE_PATH_NIO + "data.txt").getChannel();
        ByteBuffer buff = ByteBuffer.allocate(BUFFER_SIZE);
        fc.read(buff);
        buff.flip();
        while (buff.hasRemaining()) {
            System.out.print((char) buff.get());
        }
    }
}
