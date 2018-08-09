package com.hit.effective.chapter9;

/**
 * author:Charies Gavin
 * date:2018/6/22,9:31
 * https:github.com/guobinhit
 * description:单重检查模式
 */
public class SingleCheckIdiom {
    private class FieldType {
    }

    private volatile FieldType field;

    FieldType getField() {
        FieldType result = field;
        if (result == null) {
            field = result = new FieldType();
        }
        return result;
    }
}
