# 编程思想 之「控制流程」

在 Java 中，`if-else`是最基本的控制程序流程的形式，例如

```
if(boolean-expression)
    statement
else
    statement
```
其中`else`是可选的，如果省略`else`，则简化为如下形式：
```
if(boolean-expression)
    statement
```


### 迭代

常见的迭代形式有三种，分别为`for`、`while`和`do-while`，其中`while`和`do-while`的唯一区别就是`do-while`至少会执行一次循环。在一个控制表达式中，**只有`for`循环可以定义多个变量（且变量的生存周期为循环体内部）**，在其他任何选择或者迭代语句中都不能使用这种方式。

在这里，着重介绍`for`循环，其有两种形式，一种是普通的`for`循环，其形式如下：

```
for(initialization; boolean-expression; step) {
    statement
}
```
另一种是高级的`for`循环，我们也称之为`foreach`循环，其形式如下：

```
for(variable-type variable-name : collection) {
    statement
}
```
- **对于一般的`for`循环**，其第 1 部分通常用于给变量进行初始化；第 2 部分给出每次新一轮循环执行之前的判断条件；第 3 部分则给出迭代规则，也就是变量的更新方式。尽管 Java 允许在`for`循环的各个部分放置任何表达式，但有一条不成文的规则，那就是：`for`循环的 3 个部分应该对同一个变量进行初始化、判断和更新。

此外，在循环的时候，检测两个浮点数是否相等需要格外的小心，例如：

```
for(double x = 0; x != 10; x += 0.1)
```
这个`for`循环就可能永远都不会结束。因为 0.1 无法精确地用二进制表示，所以，`x`的值将从`9.99999999999998`直接跳到`10.09999999999998`. 如果在该`for`循环的下面加一条输出语句，好吧，你将会看到控制台疯狂输出。

当我们在`for`循环的第 1 部分声明一个变量之后，这个变量的作用域就是整个`for`循环，在`for`循环结束之后，这个变量就将被清理掉，无法继续使用。因此，如果我们想在`for`循环结束之后，继续使用这个变量，那么我们就得在这个`for`循环开始之前定义这个变量，简单点，在这个`for`循环的上一行定义就可以。与之相反的，在`while`循环中定义的变量，当`while`循环结束之后，仍然可以继续使用这个变量。

- **对于高级的`for`循环**，其可以用来依次处理数组中的每一个元素（其他类型的元素集合亦可），而不用指定具体的数组角标。在这里，定义的变量用于临时存储集合中的每一个元素，并执行相应的语句，集合表达式必须是一个数组或者是一个实现了`Iterable`接口的类对象。

虽然高级的`for`循环可以帮助我们快速遍历数组（以其为例）中的全部元素，但是大多数时候，我们还得使用一般的`for`循环，例如我们仅需要操作数组中的某一个元素，或者利用数组的角标完成某种需求等等。

下面，给出一个利用上述两种`for`循环测试输出数组元素的代码示例，感兴趣的同学可以自己运行体验一下：

```
package com.hit.chapter4;

import java.util.Arrays;

/**
 * author:Charies Gavin
 * date:2017/12/20,9:43
 * https:github.com/guobinhit
 * description:测试两种 for 循环方法
 */
public class ForLoop {
    public static void main(String[] args) {
        // 创建并初始化一个整型数据
        int[] arr = new int[]{2, 0, 1, 5, 11, 20};

        System.out.println("普通的 for 循环方法：");

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }

        System.out.println();
        System.out.println("高级的 for 循环方法：");

        for (int i : arr) {
            System.out.print(i + " ");

        }

        System.out.println();

        System.out.println("用 Arrays 的 toString() 方法打印数组：");
        System.out.println(Arrays.toString(arr));

        // 小心，这里有毒 ~~~
//        doubleForTest();
    }

    /**
     * 测试 double 数据类型的 for 循环
     */
    private static void doubleForTest() {
        for (double x = 0; x != 10; x += 0.1) {
            System.out.println(x);
        }
    }
}
```

![for-loop](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/control-process/for-loop.png)

如上图所示，我们还能够发现另外一种更为简单的打印数组中所有元素的方法，那就是：利用`Arrays`类中的`toString()`方法，其返回一个包含数组中所有元素的字符串，这个字符串被放置在一个中括号内部并用逗号进行分隔。

### return、break、continue、goto

`return`关键字有两种用途：

- 指定一个方法返回什么值；
- 强行退出当前的方法，并返回那个值。

在任何迭代语句的主体部分，都可以用`break`和`continue`控制循环的流程，其中：

- `break`用于强行退出循环，不执行循环中剩余的语句；
- `continue`用于停止执行当前的迭代，然后退回循环起始处，开始下一次迭代。

**无论`break`还是`continue`其本身都只能中断最内层的循环**。如果想中断外层的选择，则需要配置标签机制共同使用。虽然 Java 中没有`goto`关键字（其在源码级上做跳转），但`goto`仍然是其保留字且有类似于`goto`的标签机制。**标签起作用的唯一的地方刚好是在迭代语句之前**。而在迭代之前设置标签的唯一理由是：**我们希望在其中嵌套另一个迭代或者一个开关**。

同样，在 Java 里需要使用标签的唯一理由就是因为有循环嵌套的存在，并且想从多层嵌套中`break`或者`continue`。当然，如果想在中断循环的同时退出，用一个`return`即可。此外，在 Java 中，有两种无限循环的格式，分别为`for(;;)`和`while(true)`，编译器将两者视为同一回事。




----------

———— ☆☆☆ —— 返回 -> [The Skills of Java](https://github.com/guobinhit/java-skills/blob/master/README.md) <- 目录 —— ☆☆☆ ————
