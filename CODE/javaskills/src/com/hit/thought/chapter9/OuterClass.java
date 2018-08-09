package com.hit.thought.chapter9;

/**
 * author:Charies Gavin
 * date:2018/3/2,9:23
 * https:github.com/guobinhit
 * description:测试内部类
 */
public class OuterClass {

    OuterClass() {
        System.out.println("Congratulations, Create OuterClass Completed!");
    }

    /**
     * 非静态内部类
     */
    class InnerClass {
        InnerClass() {
            System.out.println("Congratulations, Create InnerClass Completed!");
        }

        public void sayHi() {
            System.out.println("Hi!");
        }

        /**
         * 在内部类中获取外部类对象，形式为 OuterClassName.this
         *
         * @return
         */
        public OuterClass getOuterClass() {
            return OuterClass.this;
        }
    }

    /**
     * 静态内部类，也称之为 嵌套类
     */
    static class StaticInnerClass {
        StaticInnerClass() {
            System.out.println("Congratulations, Create StaticInnerClass Completed!");
        }
    }

    /**
     * 匿名内部类
     *
     * @return
     */
    public InnerClass anonymousInnerClass() {
        return new InnerClass() {
            public void sayHi() {
                System.out.println("Hello");
            }
        };
    }

    /**
     * 在匿名内部类中引用外部参数时，必须将参数定义为 final 类型
     *
     * @return
     */
    public InnerClass anonymousInnerClass2(final String name) {
        return new InnerClass() {
            public void sayHi() {
                System.out.println("Hello " + name);
            }
        };
    }

    /**
     * 非静态方法
     */
    public void nonStaticMethod() {
        InnerClass innerClass = new InnerClass();
        innerClass.sayHi();
    }

    /**
     * 获取内部类的实例方法
     *
     * @return
     */
    public InnerClass getInnerClass() {
        return new InnerClass();
    }

    public static void main(String[] args) {
        OuterClass outerClass = new OuterClass();
        outerClass.nonStaticMethod();
        InnerClass innerClass = outerClass.getInnerClass();
        innerClass.sayHi();

        // 获取外部类对象的引用，并没有新建对象
        innerClass.getOuterClass();

        // 利用外部类对象直接创建内部类对象，使用 .new 语法
        InnerClass oi = outerClass.new InnerClass();

        // 创建嵌套类，不需要利用外部类对象的引用
        StaticInnerClass staticInnerClass = new StaticInnerClass();

        // 创建匿名类，覆盖 sayHi() 方法
        outerClass.anonymousInnerClass().sayHi();

        outerClass.anonymousInnerClass2("World").sayHi();
    }
}
