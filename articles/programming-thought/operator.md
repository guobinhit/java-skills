# 编程思想 之「操作符」

在 Java 编程的过程中，我们对数据的处理，都是通过操作符来实现的。例如，用于赋值的赋值操作符、用于运算的运算操作符、用于比较的比较操作符，还包括逻辑操作符、按位操作符、移位操作符、三元操作符等等。操作符的种类很多，用法也各不相同，下面让我们一起来看看 Java 的各类操作符中，一些值得我们特别注意的知识点。

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
 * description:测试赋值操作符
 */
public class AssignmentOperator {
    public static void main(String[] args) {
        // 创建两个对象
        Apple apple1 = new Apple("green");
        Apple apple2 = new Apple();
        // 输出两个初始化对象，apple1 初始化 color 为 green，apple2 初始化 color 为 null
        System.out.println("Initial: apple1 color is " + apple1.color + ", apple2 color is " + apple2.color);
        // 将 apple1 的引用赋值给 apple2
        apple2 = apple1;
        // 输出赋值后的两个对象，两个对象拥有同一个引用
        System.out.println("Assignment: apple1 color is " + apple1.color + ", apple2 color is " + apple2.color);
        // 修改 apple2 的引用
        apple2.color = "red";
        // 输出 apple2 修改后的两个对象，两个对象都发生变化
        System.out.println("Modify: apple1 color is " + apple1.color + ", apple2 color is " + apple2.color);
    }

}

class Apple {
    // 成员变量
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

![test-assignment](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/operator/test-assignment.png)

如上图所示，当我们把对象`apple1`赋值给对象`apple2`的时候，两个对象就拥有了同一个引用，因此在我们修改`apple2`的值之后，`apple1`的值也受到了影响，这种现象，我们称之为「同名现象」。如果想要避免上述的同名现象，我们可以修改赋值代码为

```
apple2.color = apple1.color;
```

这样的话，我们仅是将`apple1`的`color`值赋值给了`apple2`的`color`值，而不是操作对象的引用。

### 算术操作符

在 Java 的算术操作符中，整数的除法（`/`）会直接省略掉结果的小数位，而不是四舍五入。

```
package com.hit.chapter3;

import java.util.Random;

/**
 * author:Charies Gavin
 * date:2017/12/09,13:50
 * https:github.com/guobinhit
 * description:测试算术操作符
 */
public class ArithmeticOperator {
    public static void main(String[] args) {
        /**
         * 测试随机整数除法
         */
        randomDivide();
    }

    /**
     * 随机整数除法
     */
    private static void randomDivide() {
        Random random = new Random();
        int x = random.nextInt(10) + 1;
        int y = random.nextInt(10) + 1;
        int z = x / y;
        System.out.println("整数除法，默认省略结果的小数位：" + x + " / " + y + " = " + z);
    }
}
```
![test-arithmetic](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/operator/test-arithmetic.png)

如上所示，我们创建了一个随机整数除法，并测试了整数除法会默认省略结果的小数位。在`randomDivide()`方法中，我们使用了`Random`类，**如果我们在创建`Random`对象的时候没有传入任何参数，那么 Java 就会将当前时间作为随机数生成器的种子**，因此在每次执行上述程序的时候，都会得到不同的结果。其中，随机数生成器的种子用于随机数生成器的初始化值，对于相同的种子，总会产生相同的随机数序列。此外，在我们调用`nextInt()`方法的时候，我们进行了`+1`操作，这是因为**传递给`nextInt()`的参数设置了所能产生随机数的上限，而其下限为`0`，下限可以取到，上限取不到**，因此`+1`操作可以防止除数为`0`的情况。

在算术操作符中，一元减号（`-`）和一元加号（`+`）与二元减号和二元加号都使用相同的符号。根据表达式的书写形式，编译器会自动判断出使用哪一种算术操作符。其中，一元减号用于转变数据的符号，而一元加号只是为了一元减号相对于，它（`+`）唯一的作用就是将较小类型的操作数提升为`int`类型。

此外，在算术操作符中有两个比较特殊的操作符，那就是递增（`++`）和递减（`--`），递增和递减操作符不仅改变了变量，并且以变量的值作为其结果。**对于前缀递增和前缀递减（如`i++`或者`i--`），会先执行运算，再生成值；对于后缀递增和后缀递减（如`++i`或者`--i`），会先生成值，再执行运算**。

### 关系操作符

在 Java 语言中，关系操作符包括`>`、`<`、`>=`、`<=`、`==`和`!=`等，其生成的结果为`boolean`类型，其中有两个关系操作符需要我们特别注意，那就是`==`和`!=`，执行下面的程序，进行测试：

```
package com.hit.chapter3;

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
```

![test-relation](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/operator/test-relation.png)

如上所示，我们创建了两个`Integer`类型的对象，通过关系操作符来比较，发现结果出人意料，两个`Integer`类型的`521`竟然被判断为`false`，也就是不相等。实际上，这是正常的，因为`==`和`!=`比较的是对象的引用，我们通过`new`创建了两个`Integer`类型的对象，虽然这两个对象的内容相同，但它们在堆上拥有不同的存储空间，也就拥有了不同的对象引用。通过对`equalsFunction`的测试，我们发现**调用`java.lang`包（默认导入）的`equals()`方法，可以正确的比较两个对象的内容**。But，下面的程序可能就要让我们对之前的判断持怀疑态度了，

```
package com.hit.chapter3;

/**
 * author:Charies Gavin
 * date:2017/12/10,14:43
 * https:github.com/guobinhit
 * description:测试关系操作符
 */
public class RelationOperator {
    public static void main(String[] args) {
        // 创建两个自定义的 Cartoon 对象
        Cartoon c1 = new Cartoon();
        Cartoon c2 = new Cartoon();
        // 为两个 Cartoon 对象赋值
        c1.name = c2.name = "Naruto";
        // 调用 equals() 方法进行比较
        equalsFunction(c1, c2);
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
}
```
![test-relation-2](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/operator/test-relation-2.png)

如上所示，我们创建了两个自定义类的对象，然后同样是通过`equals()`方法对两个含有相同内容的对象进行比较判断，其结果竟然是`false`？不是说好`equals()`方法比较的是对象的内容吗？怎么转眼就被打脸了呢？好吧，在这里纠正一下，**`equals()`方法默认是比较对象的引用**，不过在大多数的 Java 类库中都实现了`equals()`方法，以便用来比较对象的内容，而非比较对象的引用。因此，如果我们想使用`equals()`方法来比较我们自定义类型的内容而非引用的话，则需要覆盖`Object`类（终极根类）中的`equals()`方法，而在覆盖`equals()`方法的同时，建议同时覆盖`hashCode()`方法。下面给出一个同时覆盖`equals()`和`hashCode()`的示例：

```
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
```

![test-relation-3](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/operator/test-relation-3.png)

在此，强烈建议：**不要用`==`操作符来检测两个字符串是否相等**！因为`==`操作符只能确定两个字符串是否放在同一个位置上。当然，如果两个字符串放置在同一个位置上，它们必然相等，但是完全有可能将内容相同的多个字符串的拷贝位置放置在不同的位置上。如果虚拟机始终将相同的字符串共享，就可以使用`==`操作符来检测两个字符串是否相等。但实际上，**只有字符串常量是共享的**。

### 其他操作符

在逻辑操作符中，与（`&&`）、或（`||`）、非（`!`）操作只能作用于布尔值。如果在应该使用`String`值的地方使用了布尔值，那么布尔值会自动转换成适当的文本形式。对于布尔值，按位操作符和逻辑操作符具有相同的效果，只不过按位操作符不会中途“短路”而已。

在移位操作符中，如果`char`、`byte`或者`short`类型的数值进行移位操作，那么在进行移位之前，它们会先被转换为`int`类型，并且得到的结果也是`int`类型的值。对于二进制数，如果最高位（最前面）的数字（符号位）是`0`，则为正数；是`1`，则为负数。

在对基本数据类型执行算术运算或者按位运算的时候，只要类型比`int`小（即`char`、`byte`或者`short`），那么在运算之前，这些值会自动被转换成`int`类型。通常，表达式中出现的最大的数据类型决定了表达式结果的数据类型。

如果表达式以一个字符串开头，那么后续所有操作数都必须是字符串型，如果后续的操作数不是字符串型，则会被自动转换为字符串型，并且编译器会把双引号内的字符序列自动转成字符串。

在将`float`或者`double`转型为整型值时，总是对该数字执行截尾操作。如果想要得到舍入的结果，则需要使用`java.lang.Math`中的`round()`方法。除`boolea`类外，任何一种基本数据类型都可以通过类型转换变为其他基本类型。

此外，在使用指数计数法的时候，例如

```
float loveu = 5.21e2F;
```

编译器**通常会将指数作为双精度数（`double`）来处理**，如果在初始化值后面没有`F`或者`f`的话，编译器就会报错，提示我们必须使用类型转换将`double`转换为`float`类型。

----------

———— ☆☆☆ —— 返回 -> [The Skills of Java](https://github.com/guobinhit/java-skills/blob/master/README.md) <- 目录 —— ☆☆☆ ————
