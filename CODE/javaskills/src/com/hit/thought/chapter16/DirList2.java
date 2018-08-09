package com.hit.thought.chapter16;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * author:Charies Gavin
 * date:2018/5/6,9:50
 * https:github.com/guobinhit
 * description:使用匿名内部类测试 File 类
 */
public class DirList2 {
    // 如果匿名内部类中用到了外部参数，需要将参数声明为 final 类型
    private static FilenameFilter filter(final String regex) {
        // 创建一个匿名内部类
        return new FilenameFilter() {
            private Pattern pattern = Pattern.compile(regex);

            @Override
            public boolean accept(File file, String name) {
                return pattern.matcher(name).matches();
            }
        };
    }

    public static void main(String[] args) {
        File path = new File(".");
        String[] list;
        if (args.length == 0) {
            list = path.list();
        } else {
            list = path.list(filter(args[0]));
        }
        Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
        for (String dirItem : list) {
            System.out.println(dirItem);
        }
    }
}
