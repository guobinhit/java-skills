package com.hit.thought.chapter10;

import java.util.Arrays;

/**
 * author:Charies Gavin
 * date:2018/3/10,16:20
 * https:github.com/guobinhit
 * description:自定义异常测试
 */
public class TestException {
    /**
     * 抛出异常
     *
     * @throws MyselfException
     */
    private static void throwExceptionMehtod() throws MyselfException {
        MyselfException me = new MyselfException("自定义异常");
        throw me;
    }

    public static void main(String[] args) {
        try {
            throwExceptionMehtod();
        } catch (MyselfException myselfException) {
            System.out.println("MyselfException: " + myselfException);
            System.out.println(Arrays.toString(myselfException.getStackTrace()));
            myselfException.printStackTrace();
        } catch (Exception exception) {
            System.out.println("Exception: " + exception);
        }
    }
}
