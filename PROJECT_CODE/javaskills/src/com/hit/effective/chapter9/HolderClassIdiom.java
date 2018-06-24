package com.hit.effective.chapter9;

/**
 * author:Charies Gavin
 * date:2018/6/22,9:00
 * https:github.com/guobinhit
 * description:延迟初始化Holder类模式
 */
public class HolderClassIdiom {
    private static class FieldHolder {
        static final int field = computeFiledValue();
    }

    static int getField() {
        return FieldHolder.field;
    }

    private static int computeFiledValue() {
        return 0;
    }
}
