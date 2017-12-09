# 编程思想 之「操作符」

在 Java 编程的过程中，我们对数据的处理，都是通过操作符来实现的。例如，用于比较的比较操作符、用于赋值的赋值操作符、用于运算的运算操作符等。操作符的种类很多，用法也各不相同，下面让我们一起来看看 Java 的各类操作符中，一些值得我们特别注意的知识点。

### 赋值操作符

对于赋值操作，相信大家都玩的特别熟，例如为基本数据类型赋值，

```
int love = 521;
```

在「[对象漫谈](https://github.com/guobinhit/java-skills/blob/master/articles/programming-thought/object-ramble.md)」中，我们知道了基本数据类型存储在堆栈中，而不是`new`出来的。此外，**基本数据类型存储了实际的数值，而不是指向对象的引用，所以在为其赋值的时候，是直接将一个地方的内容复制到了另一个地方**。而为一个对象进行赋值操作的时候，我们实际上是操作对象的引用，即将一个对象的引用赋值给另一个对象，因此两个对象通过同一个引用指向同一块存储空间。感兴趣的同学，可以运行如下程序进行测试，

```
package com.hit.chapter3;

/**
 * author:Charies Gavin
 * date:2017/12/09,12:40
 * https:github.com/guobinhit
 * description:测试赋值运算符，赋值对象引用
 */
public class Assignment {
    public static void main(String[] args) {
        /**
         * 创建两个对象
         */
        Apple apple1 = new Apple("green");
        Apple apple2 = new Apple();
        /**
         * 输出两个初始化对象，apple1 初始化 color 为 green，apple2 初始化 color 为 null
         */
        System.out.println("Initial: apple1 color is " + apple1.color + ", apple2 color is " + apple2.color);
        /**
         * 将 apple1 的引用赋值给 apple2
         */
        apple2 = apple1;
        /**
         * 输出赋值后的两个对象，两个对象拥有同一个引用
         */
        System.out.println("Assignment: apple1 color is " + apple1.color + ", apple2 color is " + apple2.color);
        /**
         * 修改 apple2 的引用
         */
        apple2.color = "red";
        /**
         * 输出 apple2 修改后的两个对象，两个对象都发生变化
         */
        System.out.println("Modify: apple1 color is " + apple1.color + ", apple2 color is " + apple2.color);
    }

}

class Apple {
    /**
     * 成员变量
     */
    String color;

    /**
     * 默认构造器
     */
    Apple() {
    }

    /**
     * 有参构造器
     *
     * @param color
     */
    Apple(String color) {
        this.color = color;
    }
}
```

![1](http://img.blog.csdn.net/20171209131754515)

如上图所示，当我们把对象`apple1`赋值给对象`apple2`的时候，两个对象就拥有了同一个引用，因此在我们修改`apple2`的值之后，`apple1`的值也受到了影响，这种现象，我们称之为「同名现象」。如果想要避免上述的同名现象，我们修改可以赋值代码为

```
apple2.color = apple1.color;
```

这样的话，我们仅是将`apple1`的`color`值赋值给了`apple2`的`color`值，而不是操作对象的引用。

### 算数运算符




----------

———— ☆☆☆ —— 返回 -> [那些年，关于 Java 的那些事儿](https://github.com/guobinhit/java-skills/blob/master/README.md) <- 目录 —— ☆☆☆ ————
