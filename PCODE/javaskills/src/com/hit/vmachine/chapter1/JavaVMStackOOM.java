package com.hit.vmachine.chapter1;

/**
 * author:Charies Gavin
 * date:2018/8/05,17:04
 * https:github.com/guobinhit
 * description:测试虚拟机栈和本地方法栈溢出
 * VM Args: -Xss2m
 */
public class JavaVMStackOOM {
    private void dontStop() {
        while (true) {
        }
    }

    public void stackLeakByThread() {
        while (true) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    dontStop();
                }
            });
            thread.start();
        }
    }

    public static void main(String[] args) {
        JavaVMStackOOM oom = new JavaVMStackOOM();
        oom.stackLeakByThread();
    }
}
