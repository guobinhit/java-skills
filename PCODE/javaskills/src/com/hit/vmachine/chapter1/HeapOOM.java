package com.hit.vmachine.chapter1;

import java.util.ArrayList;
import java.util.List;

/**
 * author:Charies Gavin
 * date:2018/8/05,15:30
 * https:github.com/guobinhit
 * description:测试 Java 堆溢出
 * VM Args: -Xms20m -Xmx30m -XX:+HeapDumpOnOutOfMemoryError
 */
public class HeapOOM {
    static class OOMObject {
    }

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<OOMObject>();
        // 无限循环，直至发生 OOM 为止
        while (true) {
            list.add(new OOMObject());
        }
    }
}
