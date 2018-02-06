package com.hit.thought.chapter3;

/**
 * author:Charies Gavin
 * date:2017/12/10,14:43
 * https:github.com/guobinhit
 * description:测试关系操作符
 */
public class RelationOperator {
    public static void main(String[] args) {
        // 创建两个 Integer 对象
        Integer i1 = new Integer(521);
        Integer i2 = new Integer(521);
        // 调用两个私有的静态方法进行比较判断
        equivalentOperator(i1, i2);
        equalsFunction(i1, i2);
        // 创建两个自定义的 Cartoon 对象
        Cartoon c1 = new Cartoon();
        Cartoon c2 = new Cartoon();
        // 为两个 Cartoon 对象赋值
        c1.name = c2.name = "Naruto";
        // 调用 equals() 方法进行比较
        equalsFunction(c1, c2);
    }

    /**
     * 通过恒等运算符比较两个对象
     *
     * @param o1
     * @param o2
     */
    private static void equivalentOperator(Object o1, Object o2) {
        System.out.println(o1 + " == " + o2 + " : " + (o1 == o2));
        System.out.println(o1 + " != " + o2 + " : " + (o1 != o2));
    }

    /**
     * 通过 equals() 方法比较两个对象
     *
     * @param o1
     * @param o2
     */
    private static void equalsFunction(Object o1, Object o2) {
        System.out.println("(" + o1 + ").equals(" + o2 + ") : " + (o1).equals(o2));
        System.out.println("!(" + o1 + ").equals(" + o2 + ") : " + (!(o1).equals(o2)));
    }


}

/**
 * 自定义卡通类
 */
class Cartoon {
    String name;

    /**
     * 覆盖 Object 根类中的 hashCode() 方法
     * @return 哈希值
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * 覆盖 Object 根类中的 equals() 方法
     * @param o
     * @return true or false
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Cartoon) {
            if (this.name.hashCode() == ((Cartoon) o).name.hashCode())
                return true;
            return false;
        } else {
            return false;
        }
    }
}

