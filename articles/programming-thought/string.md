# 编程思想 之「字符串」

**字符串（`String`）对象是不可变的**，把`String`对象作为方法的参数时，其实都是复制一份引用，而该引用所指的对象一直待在单一的物理位置上，从未动过。我们可以给一个`String`对象起任意多的别名，因为`String`对象具有只读特性，所以指向它的任何引用都不能改变它的值。字符串的不可变性会带来一定的效率问题，为`String`对象重载过的`+`操作符就是一个例子，其中重载的含义为：一个操作符在应用于特定的类时，被赋予了特殊的意义。在此，值得我们注意的是：用于`String`对象的`+`和`+=`是 Java 中仅有的两个重载过的操作符。

```
package com.hit.thought.chapter11;

/**
 * author:Charies Gavin
 * date:2018/3/11,14:20
 * https:github.com/guobinhit
 * description:连接字符串测试
 */
public class ConnectionString {
    public static void main(String[] args) {
        String hello = "Hello";
        String helloWorld = hello + "World";
        System.out.println(helloWorld);
    }
}
```

![javap-c-class](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/string/javap-c-class.png)

如上图所示，通过`javap`反编译`ConnectionString.class`文件后，我们可以看到：在进行字符串拼接的时候，编译器是自动引入了`StringBuilder`对象并调用其`append()`方法来实现字符串拼接的，这是编译器对我们的代码进行优化的结果，因为`StringBuilder`更高效。自然而然的，我们会想到用操作符进行字符串拼接的时候会产生很多需要垃圾回收器来回收的中间对象，这正是其效率较低的原因所在。

特别地，在循环中直接使用`StringBuilder`对象显然比使用操作符来处理`String`对象更高效。如果我们已经知道最终的字符串的大概长度，那么预先指定`StringBuilder`的大小更是可以避免多次自动扩容。因此，当我们为一个类编写`toString()`方法的时候，应该首选用`StringBuilder`对象来构造输出结果。此外，`StringBuilder`是 Java SE5 引入的，在这之前 Java 用的是`StringBuffer`，两者的 API 完全相同，唯一的区别是：`StringBuffer`是线程安全的，可以用于多线程。一般来说，

- 对于操作效率而言，`StringBuilder > StringBuffer > String`；
- 对于线程安全而言，`StringBuffer`是线程安全的，可用于多线程；而`StringBuilder`是非线程安全的，用于单线程；
- 对于频繁的字符串操作而言，无论是`StringBuffer`还是`StringBuilder`，都优于`String`。

由于 Java 中所有类都继承根类`Object`，标准容器类自然也不例外。因此容器类都有`toString()`方法，并且覆盖了该方法，使得它生成的`String`结果能够表达容器自身，以及容器所包含的对象。如果我们希望`toString()`方法打印出对象的内存地址，也许我们会考虑使用`this`关键字，就像这样：

```
public class UnconsciousRecursion {
    public static void main(String[] args) {
        List<UnconsciousRecursion> list = new ArrayList<UnconsciousRecursion>();
        for (int i = 0; i < 5; i++) {
            UnconsciousRecursion ur = new UnconsciousRecursion();
            list.add(ur);
        }
        System.out.println(list);
    }

    @Override
    public String toString(){
        return "UnconsciousRecursion address: " + this + "\n";
    }
}
```

![stack-over-flow-error](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/string/stack-over-flow-error.png)

如上述代码及结果所示，在打印`list`的时候，发生了栈溢出，究其原因：

```
return "UnconsciousRecursion address: " + this + "\n";

```
这一行代码无意中触发了递归调用，因为如果字符串对象后面跟着`+`操作符，但`+`操作符后面却不是字符串对象的话，编译器就会强制将非字符串对象转换为字符串对象，而转换的方法正是调用`this`对象的`toString()`方法。因此，如果我们真想打印对象的内存地址的话，应该调用`super.toString()`而非`this`，就像这样：

```
return "UnconsciousRecursion address: " + super.toString() + "\n";
```

### 正则表达式

正则表达式又称规则表达式，在代码中常简写为`regex`、`regexp`或`RE`，是对字符串操作的一种逻辑公式，就是用事先定义好的一些特定字符及这些特定字符的组合，组成一个“规则字符串”，这个“规则字符串”用来表达对字符串的一种过滤逻辑。正则表达式的特点包括：

- 灵活性、逻辑性和功能性非常的强；
- 可以迅速地用极简单的方式达到字符串的复杂控制；
- 对于初学者来说，比较晦涩难懂。

正则表达式对字符串的操作主要表现在三个方面，分别为：

- 匹配；
- 分割；
- 替换。

对上述三个功能，`String`类也提供了一些方法进行支持，如`matches()`、`split`和`replace`等。

```
public class StringRegularExpression {
    public static void main(String[] args) {
        String str = "123_456_789";
        // matches() 方法用于匹配字符串
        System.out.println("匹配字符串：" + str.matches("-?\\S+"));
        // split() 方法用于分割字符串
        System.out.println("分割字符串：" + Arrays.toString(str.split("_")));
        // replace() 方法用于替换字符串
        System.out.println("替换字符串：" + str.replace("_", "$"));
    }
}
```

![string-regular-expression](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/string/string-regular-expression.png)

在 Java 中，`\\`的意思是“我要插入一个正则表达式的反斜线，其后面的字符具有特殊的意义”，如示例中我们用`\\S+`表示“一个或多个非空白符”。如果我们想要插入一个普通的反斜线，则应该使用`\\\\`，不过换行和制表符之类的符号只需要使用单反斜线，如`\t`等。在正则表达式中，括号`()`有着将表达式分组的效果，而竖线`|`则表示或操作。此外，在使用`split()`方法的时候，原始字符串中与正则表达式匹配的部分，在最终的结果中都不存在了。而且，如果正则表达式不是只使用一次的话，非`String`对象的正则表达式具有更佳的性能。为了更好的使用正则表达式对象，我们先来看看一些典型的字符类以及预定义的类：

| 字符类 | 含义| 
| ------------- |:-------------| 
| . | 任意字符 | 
| [abc] | 包含 a、b 和 c 中的任一字符（和 a \| b \| c 的作用相同） | 
| [^abc] | 除了 a、b 和 c 之外的任何字符 | 
| [a-zA-Z] | 从 a 到 z 或 从 A 到 Z 的任何字符 | 
| [abc[xyz]] | 包含 a、b、c、x、y 和 z 中的任一字符 | 
| [a-z&&[xyz]] | 包含 x、y 和 z 中的任一字符 | 
| \s | 空白符（空格、制表符、换行、回车等） | 
| \S | 非空白符 [^\s] | 
| \d | 数字[0-9] | 
| \D | 非数字[^0-9] | 
| \w | 词字符[a-zA-Z0-9] | 
| \W | 非词字符[^\w] | 

接下来，再来看看边界匹配符：

| 边界匹配符 | 含义| 
| ------------- |:-------------| 
| ^ | 一行的开始 | 
| $ | 一行的结束 | 
| \b| 词的边界 | 
| \B | 非词的边界 | 
| \G | 前一个匹配的结果 | 

此外，还有量词的概念，量词描述了一个模式吸收输入文本的方式，包含三种类型，分别为：

- 贪婪型：为所有可能的模式发现尽可能多的匹配结果；
- 勉强型：用问号来指定，匹配满足模式所需的最少字符数；
- 占有型：仅 Java 语言中可用，不保存匹配的中间状态，常用于防止表达式失控。

| 贪婪型 | 勉强型|  占有型|  如何匹配| 
| ------------- |:-------------| :-------------| :-------------| 
| X? | X?? | X?+ | 一个或零个 X | 
| X* | X*? | X*?+ | 零个或多个 X | 
| X+| X+? | X++ | 一个或多个 X | 
| X{n} | X{n}? | X{n}+ | 恰好 n 次 X | 
| X{n,} | X{n,}? | X{n,}+ | 至少 n 次 X | 
| X{n,m} | X{n,m}? | X{n,m}+ | X 至少 n 次，且不超过 m 次 | 

现在，我们一起来看看创建正则表达式对象的过程：

- 首先，定义一个正则表达式字符串，如`String regex = "\\d"`；
- 然后，用`Pattern`类的静态`compile`方法编译正则表达式字符串生产`Pattern`对象，如`Pattern.compile(regex)`；
- 再调用`Pattern`对象的`matcher()`方法，生成一个`Matcher`对象；
- 最后，使用`Matcher`对象提供的各种方法处理字符串。

```
public class RegularExpressionExample {
    public static void main(String[] args) {
        obtainRegex();
    }

    public static void obtainRegex() {
        String str = "Hi girl, I like you!";
        String regex = "\\b[a-z]{3}\\b";

        // 将正则表达式封装成对象
        Pattern pattern = Pattern.compile(regex);

        // 使用 Matcher 对象的方法对字符串进行操作，为了获取三个字母组成的单词，可以用查找 find() 方法
        Matcher matcher = pattern.matcher(str);
        System.out.println(str);

        while (matcher.find()) {
            // 获取匹配的字符串子序列
            System.out.println(matcher.group());
        }
    }
}
```

![hi-girl](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/string/hi-girl.png)

如上述所示，演示了如何通过`Pattern`和`Matcher`对象使用正则表达式，其中`Pattern`对象表示编译后的正则表达式，`Matcher`对象则提供了众多可供正则表达式使用的方法。例如，

- `find()`，用来在`CharSequence`中查找多个匹配；
- `groupCount()`，返回该匹配器的模式中的分组数目，不包括第 0 组；
- `group()`，返回前一次匹配操作的第 0 组，即整个匹配；
- `group(i)`，返回前一次匹配操作期间指定的组号，可能返回`null`；
- `reset()`，将现有的`Matcher`对象应用于一个新的字符序列。


多数的正则表达式操作都接受`CharSequence`类型的参数。在 Unix / Linux 上，命令行中的正则表达式必须用引号括起来。此外，正则表达式中还有一个组的概念，**组就是用括号划分的正则表达式**，可以根据组的编号来引用某个组。组号为 0 表示整个表达式，组号为 1 表示被第一对括号括起来的组，依次类推。因此，在下面的表达式中

```
A(B(C(D)))E
```
中含有 4 个组，分别为：组 0 是 ABCDE，组 1 是 BCD，组 2 是 CD，组 3 是 D。

```
public class ObjectRegularExpression {
    /**
     * 自定义编译期常量
     */
    public static final String POEM = "If you were a teardrop in my eye,\n" +
            "For fear of losing you,\n" +
            "I would never cry.\n" +
            "And if the golden sun,\n" +
            "Should cease to shine its light,\n" +
            "Just one smile from you,\n" +
            "would make my whole world bright.";

    public static void main(String[] args) {
        // 定义正则表达式字符串，含义为：找出每行后三个单词
        String regex = "(?m)(\\S+)\\s+((\\S+)\\s+(\\S+))$";
        // 编译正则表达式字符串，获取 Pattern 对象
        Pattern pattern = Pattern.compile(regex);
        // 调用 Pattern 对象的 matcher() 方法，获取 Matcher 对象
        Matcher matcher = pattern.matcher(POEM);
        // 使用 find() 查找多个匹配结果
        while (matcher.find()) {
            // groupCount() 方法返回该匹配器的模式中的分组数目，不包括第 0 组
            for (int i = 0; i <= matcher.groupCount() ; i++) {
                // group(i) 返回前一次匹配的第 0 组，即整个匹配
                System.out.println("[" + matcher.group(i) + "]");
            }
        }
    }
}
```

![test-reg](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/string/test-reg.png)

如上述所示，我们用正则表达式`(?m)(\\S+)\\s+((\\S+)\\s+(\\S+))$`来匹配每行的后三个单词。正常来说，符号`$`是与整个输入序列的末端相匹配，但是为了让正则表达式注意到输入序列的换行符，我们通过输入序列开头的模式标记`(?m)`来完成。至于模式标记是什么？我们可以简单的将其理解为“作用于正则表达式，并让正则表达式起特定效果的标记”。

----------

———— ☆☆☆ —— 返回 -> [The Skills of Java](https://github.com/guobinhit/java-skills/blob/master/README.md) <- 目录 —— ☆☆☆ ————
