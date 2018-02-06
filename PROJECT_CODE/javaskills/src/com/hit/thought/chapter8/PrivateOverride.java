package com.hit.thought.chapter8;

/**
 * author:Charies Gavin
 * date:2018/1/3,8:55
 * https:github.com/guobinhit
 * description:测试私有方法是否能被覆盖
 */
public class PrivateOverride {
    private void test_1() {
        System.out.println("私有方法能被覆盖吗？答案：不能。");
    }

    public void test_2() {
        System.out.println("公有方法能被覆盖吗？答案：能。");
    }

    public static void main(String[] args) {
        PrivateOverride po = new DerivedOverride();
        po.test_1();
        po.test_2();
    }
}

class DerivedOverride extends PrivateOverride {
    public void test_1() {
        System.out.println("Oh, my god, 我们成功覆盖了私有方法！");
    }

    public void test_2() {
        System.out.println("Hi, buddy, 我们成功覆盖了公有方法！");
    }
}
