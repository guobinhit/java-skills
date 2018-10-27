package com.hit.thought.chapter13;

/**
 * author:Charies Gavin
 * date:2018/4/21,17:15
 * https:github.com/guobinhit
 * description:测试泛型方法
 */
public class GenericMethod {
    // 定义泛型方法，只需将泛型参数列表置于方法的返回值之前
    private  <T> void getParameterClassName(T t) {
        System.out.println(t.getClass().getName());
    }

    public static void main(String[] args) {
        GenericMethod gm = new GenericMethod();
        gm.getParameterClassName("zora");
        gm.getParameterClassName(521);
        gm.getParameterClassName(new String[]{"love"});

        // 显式地指明类型
        GenericMethod gm2 = new GenericMethod();
        gm2.<String>getParameterClassName("5211314");
    }
}
