package com.hit.thought.chapter16;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * author:Charies Gavin
 * date:2018/6/26,9:00
 * https:github.com/guobinhit
 * description:读取二进制文件
 */
public class BinaryFile {
    public static byte[] read(File bFile) throws IOException {
        BufferedInputStream br = new BufferedInputStream(
                new FileInputStream(bFile)
        );

        try {
            byte[] data = new byte[br.available()];
            br.read(data);
            return data;
        } finally {
            br.close();
        }
    }

    public static byte[] read(String bFile) throws IOException {
        return read(new File(bFile).getAbsoluteFile());
    }
}
