package com.hit.thought.chapter9;

/**
 * author:Charies Gavin
 * date:2018/3/1,9:23
 * https:github.com/guobinhit
 * description:测试接口中嵌套接口
 */
public interface OuterInterface {
    /**
     * 在接口内部定义接口 A
     */
    interface InnerInterfaceA {
        void methodInnerInterfaceA();
    }

    /**
     * 在接口内部定义接口 B
     */
    interface InnerInterfaceB {
        void methodInnerInterfaceB();
    }

    /**
     * 定义外部接口方法
     */
    void methodOuterInterface();
}

/**
 * 直接实现外部接口，仅需要实现外部接口定义的方法，并不需要实现外部接口内定义的内部接口的方法
 */
class ImplOuterInterface implements OuterInterface {

    @Override
    public void methodOuterInterface() {
        System.out.println("Overrider OuterInterface method!");
    }
}

/**
 * 我们也可以指定实现某接口内部定义的内部接口
 */
class ImplInnerInterface implements OuterInterface.InnerInterfaceA, OuterInterface.InnerInterfaceB {

    @Override
    public void methodInnerInterfaceA() {
        System.out.println("Overrider OuterInterface.InnerInterfaceA method!");
    }

    @Override
    public void methodInnerInterfaceB() {
        System.out.println("Overrider OuterInterface.InnerInterfaceB method!");
    }
}
