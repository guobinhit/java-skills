package com.hit.thought.chapter9;

/**
 * author:Charies Gavin
 * date:2018/3/4,11:28
 * https:github.com/guobinhit
 * description:继承内部类
 */
class ContainInner {
    class Inner {
        public void sayName() {
            // 获取类名
            System.out.println("调用此方法的类名为：" + this.getClass().getSimpleName());
        }
    }
}

public class InheritInnerClass extends ContainInner.Inner {
    /**
     * 继承内部类，必须调用含参构造器，且参数类型为外部类类型
     *
     * @param ci
     */
    InheritInnerClass(ContainInner ci) {
        // 必须显示调用外部类构造器
        ci.super();
    }

    public static void main(String[] args) {
        ContainInner ci = new ContainInner();
        InheritInnerClass iic = new InheritInnerClass(ci);
        iic.sayName();
    }
}
