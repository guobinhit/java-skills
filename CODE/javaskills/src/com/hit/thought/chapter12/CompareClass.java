package com.hit.thought.chapter12;

/**
 * author:Charies Gavin
 * date:2018/3/18,16:20
 * https:github.com/guobinhit
 * description:比较类类型
 */
public class CompareClass {
    public static void main(String[] args) {
        compareClass(new BaseClass());
        System.out.println();
        compareClass(new SubClass());
    }

    public static void compareClass(Object o) {
        // 获取待测试类的类类型
        System.out.println("Testing o of type : " + o.getClass());

        // 通过 instanceof 和 isInstance() 进行比较
        System.out.println("o instanceof BaseClass : " + (o instanceof BaseClass));
        System.out.println("o instanceof SubClass : " + (o instanceof SubClass));
        System.out.println("BaseClass.isInstance(o) : " + BaseClass.class.isInstance(o));
        System.out.println("SubClass.isInstance(o) : " + SubClass.class.isInstance(o));

        // 通过 == 和 equals 进行比较
        System.out.println("o.getClass() == BaseClass.class : " + (o.getClass() == BaseClass.class));
        System.out.println("o.getClass() == SubClass.class : " + (o.getClass() == SubClass.class));
        System.out.println("o.getClass.equals(BaseClass.class) : " + o.getClass().equals(BaseClass.class));
        System.out.println("o.getClass.equals(SubClass.class) : " + o.getClass().equals(SubClass.class));
    }
}

class BaseClass {
}

class SubClass extends BaseClass {
}
