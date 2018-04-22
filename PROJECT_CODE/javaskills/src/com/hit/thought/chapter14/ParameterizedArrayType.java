package com.hit.thought.chapter14;

/**
 * author:Charies Gavin
 * date:2018/4/22,12:10
 * https:github.com/guobinhit
 * description:参数化数组类型
 */
public class ParameterizedArrayType {
    public static void main(String[] args) {
        Integer[] ints = {1, 1, 2, 0};
        Double[] doubles = {1.1, 1.1, 2.2, 0.0};
        Integer[] ints2 = new ClassParameter<Integer>().f(ints);
        Double[] doubles2 = new ClassParameter<Double>().f(doubles);
//        ints2 = new MethodParameter.f(ints);
//        doubles2 = new MethodParameter.f(doubles);
    }
}

class ClassParameter<T> {
    public T[] f(T[] arg) {
        return arg;
    }
}

class MethodParameter {
    public static <T> T[] f(T[] arg) {
        return arg;
    }
}


