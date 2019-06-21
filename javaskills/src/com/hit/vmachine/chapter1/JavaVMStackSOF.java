package com.hit.vmachine.chapter1;

/**
 * author:Charies Gavin
 * date:2018/8/05,16:59
 * https:github.com/guobinhit
 * description:测试虚拟机栈和本地方法栈溢出
 * VM Args: -Xss128k
 */
public class JavaVMStackSOF {
    private int stackLength = 1;

    // 循环调用
    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) throws Throwable {
        JavaVMStackSOF sof = new JavaVMStackSOF();
        try {
            sof.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length: " + sof.stackLength);
            throw e;
        }
    }
}
