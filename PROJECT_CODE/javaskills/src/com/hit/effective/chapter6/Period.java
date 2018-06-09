package com.hit.effective.chapter6;

import java.util.Date;

/**
 * author:Charies Gavin
 * date:2018/6/9,9:48
 * https:github.com/guobinhit
 * description:保护性拷贝
 */
public final class Period {
    private final Date start;
    private final Date end;

    /**
     * 未使用保护性拷贝的构造器
     *
     * @param start
     * @param end
     */
//    public Period(Date start, Date end) {
//        if (start.compareTo(end) > 0)
//            throw new IllegalArgumentException(start + "after " + end);
//        this.start = start;
//        this.end = end;
//    }

    /**
     * 使用保护性拷贝的构造器
     *
     * @param start
     * @param end
     */
    public Period(Date start, Date end) {
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());

        if (this.start.compareTo(this.end) > 0)
            throw new IllegalArgumentException(start + "after " + end);
    }

    public Date start() {
        return new Date(start.getTime());
    }

    public Date end() {
        return new Date(end.getTime());
    }

    public static void main(String[] args) {
        Date start = new Date();
        Date end = new Date();

        Period period = new Period(start, end);
        System.out.println("Period: start = " + period.start + ", end = " + period.end);

        period.end().setYear(78);
        System.out.println("Period: start = " + period.start + ", end = " + period.end);
    }
}
