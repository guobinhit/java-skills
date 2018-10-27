package com.hit.thought.chapter10;

/**
 * author:Charies Gavin
 * date:2018/3/10,16:00
 * https:github.com/guobinhit
 * description:自定义异常
 */
public class MyselfException extends Exception {
    /**
     * 默认构造器
     */
    MyselfException() {
    }

    /**
     * 含参构造器
     *
     * @param msg
     */
    MyselfException(String msg) {
        super(msg);
    }
}
