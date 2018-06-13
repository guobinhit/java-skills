package com.hit.effective.chapter8;

/**
 * author:Charies Gavin
 * date:2018/6/12,9:08
 * https:github.com/guobinhit
 * description:模拟高层异常
 */
public class HigherLevelException extends RuntimeException {
    public HigherLevelException() {
        super();
    }

    public HigherLevelException(LowerLevelException e) {
        super(e);
    }
}
