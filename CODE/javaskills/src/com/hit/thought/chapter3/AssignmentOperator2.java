package com.hit.thought.chapter3;

/**
 * author:Charies Gavin
 * date:2017/12/09,12:59
 * https:github.com/guobinhit
 * description:测试赋值操作符，赋值基本数据类型(同时也解决了同名现象)
 */
public class AssignmentOperator2 {
    public static void main(String[] args) {
        // 创建两个对象
        Book book1 = new Book(18);
        Book book2 = new Book();
        // 输出两个初始化对象，book1 初始化 price 为 18，book2 初始化 price 为 0
        System.out.println("Initial: book1 price is " + book1.price + ", book2 price is " + book2.price);
        // 将 book1 的 price 值赋值给 book2 的 price 值
        book2.price = book1.price;
        // 输出赋值后的两个对象
        System.out.println("Assignment: book1 price is " + book1.price + ", book2 price is " + book2.price);
        // 修改 book2 的 price 值
        book2.price = 20;
        // 输出 book2 修改后的两个对象，由于两个对象拥有不同的引用，因此修改一个并不会影响另一个
        System.out.println("Modify: book1 price is " + book1.price + ", book2 price is " + book2.price);
    }
}

class Book {
    // 成员变量
    int price;

    /**
     * 默认构造器
     */
    Book() {
    }

    /**
     * 有参构造器
     *
     * @param price
     */
    Book(int price) {
        this.price = price;
    }
}
