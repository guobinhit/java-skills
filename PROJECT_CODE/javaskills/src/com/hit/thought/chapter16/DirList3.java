package com.hit.thought.chapter16;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * author:Charies Gavin
 * date:2018/5/6,10:00
 * https:github.com/guobinhit
 * description:第三种测试 File 类的方法
 */
public class DirList3 {
    public static void main(final String[] args) {
        final File path = new File(".");
        String[] lsit;
        if (args.length == 0) {
            lsit = path.list();
        } else {
            lsit = path.list(new FilenameFilter() {
                private Pattern pattern = Pattern.compile(args[0]);

                @Override
                public boolean accept(File file, String name) {
                    return pattern.matcher(name).matches();
                }
            });
        }
        Arrays.sort(lsit, String.CASE_INSENSITIVE_ORDER);
        for (String dirItem : lsit) {
            System.out.println(dirItem);
        }
    }
}
