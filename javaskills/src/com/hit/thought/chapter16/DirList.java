package com.hit.thought.chapter16;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * author:Charies Gavin
 * date:2018/5/6,9:45
 * https:github.com/guobinhit
 * description:测试 File 类
 */
public class DirList {
    public static void main(String[] args) {
        /**
         * FIle(String pathName) or File(String pathName, String childName)
         *
         * File 有两个构造器，参数代表着文件路径的名称，如果有两个参数，则
         * 1、第一个参数，代表父路径；
         * 2、第二个参数，代表子路径。
         *
         * 如果 File 构造器参数为 . 则代表当前路径
         */
        File path = new File(".");
        String[] list;
        if (args.length == 0) {
            list = path.list();
        } else {
            list = path.list(new DirFilter(args[0]));
        }
        Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
        for (String dirItem : list) {
            System.out.println(dirItem);
        }
    }
}

/**
 * DirFilter 类存在的唯一目的就是实现我们自己的 accept() 方法，以实现回调
 */
class DirFilter implements FilenameFilter {
    private Pattern pattern;

    public DirFilter(String regex) {
        pattern = Pattern.compile(regex);
    }

    /**
     * accept() 方法必须接受一个代表某个特定文件所在目录的 File 对象，以及包含了那个文件名的一个 String
     * list() 方法会为此目录对象下的每个文件名调用 accept() 方法，
     * 来判断是否包含在内，判断的结果又 accept() 方法返回的 boolean 值表示。
     * <p>
     * accept() 会使用正则表达式的 matcher 对象，来查看此正则表达式 regex 是否匹配这个文件的名字，
     * 通过使用 accept() 方法，list() 方法会返回一个数组
     */
    @Override
    public boolean accept(File file, String name) {
        return pattern.matcher(name).matches();
    }
}
