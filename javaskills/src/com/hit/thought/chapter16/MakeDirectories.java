package com.hit.thought.chapter16;

import java.io.File;

/**
 * author:Charies Gavin
 * date:2018/5/6,11:00
 * https:github.com/guobinhit
 * description:演示 File 类的各种特性
 */
public class MakeDirectories {
    // 测试使用指南
    private static void usage() {
        System.err.println(
                "Usage: MakeDirectories path1 ...\n" +
                        "Creates each path\n" +
                        "Usage: MakeDirectories -d path1 ...\n" +
                        "Deletes each path\n" +
                        "Usage: MakeDirectories -r path1 path2\n" +
                        "Renames from path1 to path2"
        );
        System.exit(1);
    }

    // 获取 File 对象相关数据
    private static void fileData(File f) {
        System.out.println(
                "Absolute path: " + f.getAbsolutePath() +
                        "\n Can read: " + f.canRead() +
                        "\n Can write: " + f.canWrite() +
                        "\n get Name: " + f.getName() +
                        "\n getParent: " + f.getParent() +
                        "\n getPath: " + f.getPath() +
                        "\n length: " + f.length() +
                        "\n lastModified: " + f.lastModified()
        );

        // 判断 File 对象属性，并打印
        if (f.isFile()) {
            System.out.println("It's a file");
        } else if (f.isDirectory()) {
            System.out.println("It's a directory");
        }
    }

    public static void main(String[] args) {
        // 判断命令行参数
        if (args.length < 1) {
            usage();
        }

        // 如果命令行第一个参数为 -r 则执行此 if 语句
        if (args[0].equals("-r")) {
            if (args.length != 3) {
                usage();
            }
            File old = new File(args[1]), rename = new File(args[2]);
            old.renameTo(rename);
            fileData(old);
            fileData(rename);
            // 退出主函数
            return;
        }

        int count = 0;
        boolean del = false;
        if (args[0].equals("-d")) {
            count++;
            del = true;
        }
        count--;
        while (++count < args.length) {
            File f = new File(args[count]);
            // 如果存在
            if (f.exists()) {
                System.out.println(f + " exists");
                if (del) {
                    System.out.println("deleting... " + f);
                    f.delete();
                }
            } else { // 如果不存在
                if (!del) {
                    f.mkdir();
                    System.out.println("Created " + f);
                }
            }
            fileData(f);
        }
    }
}
