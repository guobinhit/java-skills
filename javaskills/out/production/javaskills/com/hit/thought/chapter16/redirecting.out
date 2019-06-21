package com.hit.thought.chapter16;

import java.io.*;

/**
 * author:Charies Gavin
 * date:2018/6/26,9:21
 * https:github.com/guobinhit
 * description:重定向标准 IO 流
 */
public class Redirecting {
    public static void main(String[] args) throws IOException {
        PrintStream console = System.out;
        BufferedInputStream in = new BufferedInputStream(
                new FileInputStream(Constants.FILE_ABSOLUTE_PATH_EXCLUDE_IO + "Redirecting.java")
        );
        PrintStream out = new PrintStream(
                new BufferedOutputStream(
                        new FileOutputStream(Constants.FILE_ABSOLUTE_PATH_EXCLUDE_IO + "redirecting.out")
                )
        );
        System.setIn(in);
        System.setOut(out);
        System.setErr(out);
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in)
        );
        String s;
        while ((s = br.readLine()) != null) {
            System.out.println(s);
        }
        out.close();
        System.setOut(console);
    }
}
