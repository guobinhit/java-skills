package com.hit.effective.chapter1;

/**
 * author:Charies Gavin
 * date:2018/5/27,11:35
 * https:github.com/guobinhit
 * description:终结方法守卫者
 */
public class Foo {
    /**
     * 终结方法守护者
     */
    private final Object finalizerGuardian = new Object() {
        @Override
        protected void finalize() throws Throwable {
            // 终结外围类对象
        }
    };
}
