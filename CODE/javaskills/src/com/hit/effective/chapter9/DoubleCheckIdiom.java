package com.hit.effective.chapter9;

/**
 * author:Charies Gavin
 * date:2018/6/22,9:17
 * https:github.com/guobinhit
 * description:双重检查模式
 */
public class DoubleCheckIdiom {
    private class FieldType {
    }

    private volatile FieldType field;

    FieldType getField() {
        // 此语句是为了在 field 已经被初始化时，进行一次读取操作，用于提升性能
        FieldType result = field;
        // 第一次检查，无锁定
        if (result == null) {
            // 第二次检查，有锁定
            synchronized (this) {
                result = field;
                if (result == null) {
                    field = result = new FieldType();
                }
            }
        }
        return result;
    }
}
