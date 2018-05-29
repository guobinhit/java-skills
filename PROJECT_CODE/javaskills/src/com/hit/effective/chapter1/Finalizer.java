package com.hit.effective.chapter1;

/**
 * author:Charies Gavin
 * date:2018/5/27,11:29
 * https:github.com/guobinhit
 * description:终结方法
 */
public class Finalizer {
    @Override
    protected void finalize() throws Throwable {
        try {
            // 终结子类状态
        } finally {
            super.finalize();
        }
    }
}
