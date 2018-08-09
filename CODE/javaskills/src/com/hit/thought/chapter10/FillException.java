package com.hit.thought.chapter10;

/**
 * author:Charies Gavin
 * date:2018/3/10,16:50
 * https:github.com/guobinhit
 * description:重新抛出异常测试
 */
public class FillException {
    public static void main(String[] args) throws Exception {
        try {
            catchExceptionInitCause();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void newCatchException() throws Exception {
        try {
            getException();
        } catch (Exception e) {
            System.out.println("Oh, catch a new exception");
            // 通过 fillInStackTrace() 方法重新抛出异常
            throw (Exception) e.fillInStackTrace();
        }
    }

    private static void catchException() throws Exception {
        try {
            getException();
        } catch (Exception e) {
            System.out.println("Oh, catch a exception");
            throw e;
        }
    }

    /**
     * 构造同一类型异常链使用构造器即可
     *
     * @throws Exception
     */
    private static void catchExceptionStructure() throws Exception {
        try {
            getException();
        } catch (Exception e) {
            System.out.println("Oh, catch a exception");
            throw new Exception(e);
        }
    }

    /**
     * 构造非同一类型异常链需要使用 initCause() 方法
     *
     * @throws Exception
     */
    private static void catchExceptionInitCause() throws Exception {
        try {
            getException();
        } catch (Exception e) {
            System.out.println("Oh, catch a exception");
            MyselfException myselfException = new MyselfException();
            myselfException.initCause(e);
            throw myselfException;
        }
    }

    private static void getException() throws Exception {
        Exception exception = new Exception("Ops, cause a exception");
        throw exception;
    }
}