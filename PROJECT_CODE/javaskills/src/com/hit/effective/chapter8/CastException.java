package com.hit.effective.chapter8;

/**
 * author:Charies Gavin
 * date:2018/6/12,9:10
 * https:github.com/guobinhit
 * description:异常转译及异常链
 */
public class CastException {
    public static void main(String[] args) {
        try {
            // Use lower-level abstraction to do our biding
        } catch (LowerLevelException e) {
            // throw higher-level exception
            throw new HigherLevelException(e);
        }
    }
}
