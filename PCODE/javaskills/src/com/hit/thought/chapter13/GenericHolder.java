package com.hit.thought.chapter13;

/**
 * author:Charies Gavin
 * date:2018/3/20,8:30
 * https:github.com/guobinhit
 * description:泛型持有对象
 */
public class GenericHolder<T> {
    private T obj;

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public static void main(String[] args) {
        GenericHolder<String> holder = new GenericHolder<String>();
        holder.setObj("HelloWorld");
        String s = holder.getObj();
        System.out.println(s);
//        holder.setObj(521);
//        Integer i = (Integer) holder.getObj();
//        System.out.println(i);
    }
}
