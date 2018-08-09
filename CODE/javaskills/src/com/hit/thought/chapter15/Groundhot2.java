package com.hit.thought.chapter15;

/**
 * author:Charies Gavin
 * date:2018/5/5,10:05
 * https:github.com/guobinhit
 * description:土拨鼠2
 */
public class Groundhot2 extends Groundhog {
    public Groundhot2(int n) {
        super(n);
    }

    /**
     * 覆盖 hashCode() 方法，由于示例比较简单，且 number 不会重复，因此直接返回 number 即可
     */
    @Override
    public int hashCode() {
        return number;
    }

    /**
     * 覆盖 equals() 方法，Object 默认的 equals() 方法比较的是对象的地址
     * 操作符 instanceof 会悄悄地检查此对象是否为 null，如果其左边对象的参数为 null，则返回 false
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof Groundhot2 && (number == ((Groundhot2) o).number);
    }
}
