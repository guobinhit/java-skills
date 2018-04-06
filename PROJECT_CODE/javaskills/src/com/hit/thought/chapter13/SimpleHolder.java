package com.hit.thought.chapter13;

/**
 * author:Charies Gavin
 * date:2018/3/20,8:30
 * https:github.com/guobinhit
 * description:简单持有对象
 */
public class SimpleHolder {
    private Object obj;

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public static void main(String[] args) {
        SimpleHolder holder = new SimpleHolder();
        holder.setObj("HelloWorld");
        String s = (String) holder.getObj();
        System.out.println(s);
        holder.setObj(521);
        Integer i = (Integer) holder.getObj();
        System.out.println(i);
    }
}
