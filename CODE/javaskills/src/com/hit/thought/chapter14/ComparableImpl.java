package com.hit.thought.chapter14;

/**
 * author:Charies Gavin
 * date:2018/4/22,10:30
 * https:github.com/guobinhit
 * description:实现Comparable接口
 */
public class ComparableImpl implements Comparable<ComparableImpl> {

    int age;

    // 显示声明构造函数，强制必须传一个 int 类型的参数
    public ComparableImpl(int age) {
        this.age = age;
    }

    @Override
    public int compareTo(ComparableImpl comparable) {
        return age < comparable.age ? -1 : (age == comparable.age ? 0 : 1);
    }

    public static void main(String[] args) {
        ComparableImpl gavin = new ComparableImpl(28);
        ComparableImpl zora = new ComparableImpl(27);
        System.out.println(gavin.compareTo(zora));
    }
}
