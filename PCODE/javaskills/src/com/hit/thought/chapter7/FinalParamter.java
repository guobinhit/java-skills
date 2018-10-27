package com.hit.thought.chapter7;

/**
 * author:Charies Gavin
 * date:2017/12/30,17:34
 * https:github.com/guobinhit
 * description:测试方法中的 final 参数
 */
public class FinalParamter {
    /**
     * 无法修改 final 参数所指向的引用
     * @param apple
     */
//    public void eat(final Apple apple){
//        apple = new Apple();
//        apple.peel(apple);
//    }

    public void wash(Apple apple) {
        apple = new Apple();
        apple.peel(apple);
    }

    /**
     * 无法修改 final 参数所指向的引用
     * @param i
     */
//    public void printNum_1(final int i) {
//        System.out.println(i++);
//    }

    public void printNum_2(final int j) {
        System.out.println(j + 1);
    }

    public static void main(String[] args) {
        FinalParamter finalParamter = new FinalParamter();
        finalParamter.wash(null);
        finalParamter.printNum_2(5);
    }
}

class Apple {
    public void peel(Apple apple) {
        System.out.println("Apple apple, peel apple...");
    }
}
