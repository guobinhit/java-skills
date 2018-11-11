# 编程思想 之「继承、组合、fianl」

提起「复用类」三个字，相信我们脑海中浮现的都是「组合」和「继承」，实际上，在 Java 中复用类的方法也确实是这两种。

### 继承

Java 用`super`关键字表示超类（或称之为基类）的意思，意味着：当前类是从超类中继承而来的。当我们创建一个继承超类的导出类的对象的时候，该对象就包含了一个超类的子对象。这个子对象与我们用超类直接创建的对象是一样的，两者的区别在于，后者来自于外部，而超类的子对象被包装在导出类对象的内部。Java 会自动在导出类的构造器中插入对基类构造器的调用，例如，

```
package com.hit.chapter7;

/**
 * author:Charies Gavin
 * date:2017/12/30,16:22
 * https:github.com/guobinhit
 * description:测试导出类对象包含子类对象
 */
public class ClassA extends ClassB {
    public ClassA() {
        System.out.println("==== ClassA extends ClassB ==");
    }

    public static void main(String[] args) {
        new ClassA();
    }
}

class ClassB extends ClassC {
    public ClassB() {
        System.out.println("==== ClassB extends ClassC ==");
    }
}

class ClassC {
    public ClassC() {
        System.out.println("==== ClassC ==");
    }
}
```
![class-a](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/reuse-class/class-a.png)

通过上面的测试，我们会发现：**类的构建过程是从超类向外“扩散”的**。

如果没有默认的超类构造器，或者想调用一个带参数的超类构造器，我们就必须使用关键字`super`显式地编写调用超类构造器的语句，并且配以适当的参数列表。此外，还要注意：**调用超类构造器必须是我们在导出类构造器中做的第一件事**。

### 组合

我们有一个设计原则，那就是：**多用组合，少用继承**。

相比于继承来说，组合更加灵活。组合通常用于想在新类中使用现有类的功能而非它的接口这种情形。举一个简单的例子，如果我们想创建一个`Car`，而 1 个`Car`包含 4 个`Wheel`、4 个`Door`和 6 个`Window`，这显然是`has-a`的关系而不是`is-a`的关系，何况 Java 也不支持多继承，这时使用组合就显得尤为合理.

### final

一个既是`static`又是`final`的域只占据一段不能改变的存储空间。

对于基本类型，`final`使数值恒定不变；而对于对象的引用，`final`使引用恒定不变，然后对象自身却是可以被修改的。

在我们定义一个常量的时候，大都会使用如下格式：

```
public static final int CONSTANT = 521;
```
其中，定义为

- `public`，表示它可以被用于包外；
- `static`，强调它只有一个；
- `final`，则说明它是一个常量。

Java 允许生产`空白final`，所谓`空白final`是指被声明为`final`但又未给定初值的域。不过我们必须在域的定义处或者每个构造器中用表达式对`final`进行赋值，以确保`空白final`在使用前必须被初始化。

Java 也允许在参数列表中以声明的的方式将参数指明为`final`，这意味着我们无法在方法中更改参数所指向的对象。例如，

```
package com.hit.chapter7;

/**
 * author:Charies Gavin
 * date:2017/12/30,17:34
 * https:github.com/guobinhit
 * description:测试方法中的 final 参数
 */
public class FinalParamter {
    /**
     * 无法修改 final 参数所指向的引用
     * @param apple
     */
//    public void eat(final Apple apple){
//        apple = new Apple();
//        apple.peel(apple);
//    }

    public void wash(Apple apple) {
        apple = new Apple();
        apple.peel(apple);
    }

    /**
     * 无法修改 final 参数所指向的引用
     * @param i
     */
//    public void printNum_1(final int i) {
//        System.out.println(i++);
//    }

    public void printNum_2(final int j) {
        System.out.println(j + 1);
    }

    public static void main(String[] args) {
        FinalParamter finalParamter = new FinalParamter();
        finalParamter.wash(null);
        finalParamter.printNum_2(5);
    }
}

class Apple {
    public void peel(Apple apple) {
        System.out.println("Apple apple, peel apple...");
    }
}
```
在 Java SE5 之前，使用`final`关键字修饰方法主要有两种原因，

- 第 1 个原因：锁定方法，以防止任何继承类修改它的定义；
- 第 2 个原因：提升运行效率。

但是随着 JDK 版本的迭代，我们已经不需要使用`final`方法来提升效率了，现在使用`final`方法的唯一理由就是：**禁止子类覆盖父类中的方法**。

类中所有的`private`方法都隐式地指定为`final`的；`final`类中所有的方法也都隐式指定为`final`的，因此`final`类无法继承，其方法也无法覆盖。

一般来说，“类的代码在初次使用时才加载”，其含义通常是指：**加载发生于创建类的第一个对象之时，但是当访问`static`域或者`static`方法时，也会发生加载**。实际上，构造器也是`static`方法，尽管`static`关键字没有显式地写出来。因此，更准确的讲，类是在其任何`static`成员被访问时加载的。



----------

———— ☆☆☆ —— 返回 -> [The Skills of Java](https://github.com/guobinhit/java-skills/blob/master/README.md) <- 目录 —— ☆☆☆ ————
