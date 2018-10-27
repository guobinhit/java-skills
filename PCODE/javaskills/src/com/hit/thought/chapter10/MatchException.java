package com.hit.thought.chapter10;

/**
 * author:Charies Gavin
 * date:2018/3/10,19:40
 * https:github.com/guobinhit
 * description:测试catch子句匹配异常的情况
 */
public class MatchException {
    public static void main(String[] args) throws Exception {

        /**
         * 基类异常可以匹配导出类异常，即可以通过基类异常 catch 住
         */
        try {
            ComplicatedException ce = new ComplicatedException();
            throw ce;
        } catch (SimplieException e) {
            System.out.println("Catch SimplieException");
        }

        /**
         * 导出类异常不能匹配基类异常，即不可以通过导出类异常 catch 住
         */
        try {
            SimplieException ce = new SimplieException();
            throw ce;
        } catch (ComplicatedException e) {
            System.out.println("Catch ComplicatedException");
        }

        /**
         * 先 catch 基类异常，再 catch 导出类异常
         */
//        try {
//            ComplicatedException ce = new ComplicatedException();
//            throw ce;
//        } catch (SimplieException e) {
//            System.out.println("Catch SimplieException");
//        } catch (ComplicatedException e) {
//            System.out.println("Catch ComplicatedException");
//        }
    }
}

class SimplieException extends Exception {
}

class ComplicatedException extends SimplieException {
}
