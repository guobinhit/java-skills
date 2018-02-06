package com.hit.thought.chapter5;

/**
 * author:Charies Gavin
 * date:2017/12/25,9:07
 * https:github.com/guobinhit
 * description:测试 this 关键字
 */
public class MottoHIT {
    public static void main(String[] args) {
        Hiter hiter_1 = new Hiter();
        Hiter hiter_2 = new Hiter();
        hiter_1.sayMotto("Zora");
        hiter_2.sayMotto("Charies");
    }
}

class Hiter {
    public void sayMotto(String name) {
        System.out.println(name + ": 规格严格，功夫到家");
    }
}
