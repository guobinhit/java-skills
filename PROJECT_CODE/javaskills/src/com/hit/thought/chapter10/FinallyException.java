package com.hit.thought.chapter10;

/**
 * author:Charies Gavin
 * date:2018/3/10,19:06
 * https:github.com/guobinhit
 * description:测试finally关键字
 */
public class FinallyException {
    public static void main(String[] args) throws Exception {
        // 循环调用 testFinally() 方法
        for (int i = 1; i < 5; i++) {
            testFinally(i);
        }
    }

    /**
     * 测试 finally 子句
     *
     * @param i
     * @throws Exception
     */
    private static void testFinally(int i) throws Exception {
        try {
            System.out.println("Initial test finally...");
            if (i == 1) {
                System.out.println("i = " + i);
                return;
            }
            if (i == 2) {
                System.out.println("i = " + i);
                return;
            }
            if (i == 3) {
                System.out.println("i = " + i);
                return;
            }
            Exception exception = new Exception("Ops, it's a exception!");
            throw exception;
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            System.out.println("Hey buddy, u come in finally block!");
        }
    }
}
