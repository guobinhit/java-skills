package com.hit.thought.chapter12;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * author:Charies Gavin
 * date:2018/3/18,15:240
 * https:github.com/guobinhit
 * description:动态代理
 */
public class SimpleDynamicProxy {

    public static void main(String[] args) {
        RealObject realObject = new RealObject();

        consumer(realObject);

        Interface proxy = (Interface) Proxy.newProxyInstance(
                // 被代理类的类加载器
                Interface.class.getClassLoader(),
                // 希望该代理实现的接口列表
                new Class[]{Interface.class},
                // InvocationHandler 接口的实现类
                new DynamicProxyHandler(realObject));

        consumer(proxy);
    }
    
    public static void consumer(Interface iface) {
        iface.doSomething();
        iface.somethingElse("Ops!");
    }

}

class DynamicProxyHandler implements InvocationHandler {
    private Object proxied;

    public DynamicProxyHandler(Object proxied) {
        this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("---> proxy: " + proxy.getClass() +
                ", method: " + method +
                ", args: " + args);
        if (args != null) {
            for (Object arg : args) {
                System.out.println(" " + arg);
            }
        }
        return method.invoke(proxied, args);
    }
}
