package com.hit.effective.chapter2;

import java.util.HashMap;
import java.util.Map;

/**
 * author:Charies Gavin
 * date:2018/6/23,11:20
 * https:github.com/guobinhit
 * description:override equals & hashCode
 */
public final class PhoneNumber {
    private final short areaCode;
    private final short prefix;
    private final short lineNubmer;

    /**
     * 构造函数
     *
     * @param areaCode
     * @param prefix
     * @param lineNumber
     */
    public PhoneNumber(int areaCode, int prefix, int lineNumber) {
        rangeCheck(areaCode, 999, "area code");
        rangeCheck(prefix, 999, "prefix");
        rangeCheck(lineNumber, 9999, "line number");
        this.areaCode = (short) areaCode;
        this.prefix = (short) prefix;
        this.lineNubmer = (short) lineNumber;
    }

    /**
     * 参数校验方法
     *
     * @param arg
     * @param max
     * @param name
     */
    private static void rangeCheck(int arg, int max, String name) {
        if (arg < 0 || arg > max) {
            throw new IllegalArgumentException(name + " : " + arg);
        }
    }

    /**
     * 覆盖 equals 方法
     *
     * @param o
     * @return true or false
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PhoneNumber)) {
            return false;
        }
        PhoneNumber pn = (PhoneNumber) o;
        return pn.lineNubmer == lineNubmer
                && pn.prefix == prefix
                && pn.areaCode == areaCode;
    }

    @Override
    public int hashCode() {
        int result = 1120;
        result = 31 * result + areaCode;
        result = 31 * result + prefix;
        result = 31 * result + lineNubmer;
        return result;
    }

    public static void main(String[] args) {
        Map<PhoneNumber, String> amap = new HashMap<PhoneNumber, String>();
        amap.put(new PhoneNumber(010, 521, 1314), "Gavin");

        System.out.println(amap.get(new PhoneNumber(010, 521, 1314)));
    }
}
