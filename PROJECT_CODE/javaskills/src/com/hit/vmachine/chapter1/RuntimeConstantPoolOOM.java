package com.hit.vmachine.chapter1;

import java.util.ArrayList;
import java.util.List;

/**
 * author:Charies Gavin
 * date:2018/8/05,17:14
 * https:github.com/guobinhit
 * description:测试方法区和运行时常量池溢出
 * VM Args: -XX:PermSize=10m -XX:MaxPermSize=10m
 */
public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {
        // 使用 List 保持着常量池引用，避免 Full GC 回收常量池行为
        List<String> list = new ArrayList<String>();
        // 10MB 的 PermSize 在 integer 范围内足够产生 OOM 了
        int i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }
    }
}
