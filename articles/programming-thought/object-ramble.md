# 编程思想 之「对象漫谈」

在「[语言导论](https://github.com/guobinhit/java-skills/blob/master/articles/programming-thought/language-guide.md)」中，我们曾提到过「万物皆对象」，事实上，也确实如此。在面向对象编程的世界中，我们创建对象、操作对象、销毁对象，我们所做的一切动作都离不开对象。在本章中，就让我们一起来看看 Java 中的对象。

### 对象存储在哪里？

在 Java 中，对象存储在 5 个地方，分别为：

- **寄存器**。它是最快的存储区，位于处理器内部。一般来说，它根据需求进行分配，我们不能直接进行控制，也很难感觉到它的存在。
- **堆栈**。它位于 RAM（Random Access Memory，随机访问存储器），通过堆栈指针分配内存空间，指针下移，分配内存；指针上移，释放内存。堆栈的处理速度仅次于寄存器，但编译器必须知道存储在堆栈中内容的确切生命周期，这造成了一定的限制。一般来说，**基本数据类型，存储在堆栈中**。
- **堆**。堆是一种通用的内存池，也位于 RAM 中，它的处理速度稍慢于堆栈，用于存储所有的 Java 对象，更确切的说，**所有`new`出来的对象都存在堆中**。
- **常量存储**。常量值通常直接存在代码的内部，但有时为了将常量与其他内容分离，也会存在 ROM（Read Only Memory）中。
- **非 RAM 存储**。有的数据可以存活于程序之外，在程序没有运行的时候也可以存在，例如流对象和持久化对象（写入磁盘）。

对于**基本数据类型**的存储，则稍有不同：正常来说，我们通过`new`来创建对象，但当我们创建比较小的、简单的对象的时候，通过`new`来创建对象并不是很有效，因此 Java 直接将对象的值存储在堆栈中，基本数据类型正是如此。

我们都听过这样的一句话，“**Java 具有良好的移植性**”，而 Java 之所以能够具有良好的移植性的一部分原因就在于 Java 中基本数据类型所占的空间为固定值，而非其他语言中数据类型可能因为平台的不同而占据不同的存储空间。

| 基本类型 | 大小 | 最小值 |最大值 | 包装器类型 |
| ------------- |:-------------| :-----|:-------------| :-----|
| `boolean` | -- | --|--|`Boolean`|
| `char` | 16 bits | Unicode o |Unicode $2^{16}$-1|`Character`|
| `byte` | 8 bits | -128 |+127|`Byte`|
|`short`|16 bits|-$2^{15}$|+$2^{15}$-1|`Short`|
|`int`|32 bits|-$2^{31}$|+$2^{31}$-1|`Integer`|
|`long`|64 bits|-$2^{63}$|+$2^{63}$-1|`Long`|
|`float`|32 bits|IEEE754|IEEE754|`Float`|
|`double`|64 bits|IEEE754|IEEE754|`Double`|
|`void`|--|--|--|`Void`|

以上所有数值类型都有正负号，所有不要去寻找无符号的数值类型。`boolean`类型所占的存储空间大小没有明确指定，仅定义为能够取字面值`true`和`false`。在 JDK SE1.5 发布之后，**基本数据类型都具有对应的包装器类**，使得我们可以在堆中创建一个非基本对象，用来表示对应的基本类型，而且基本数据类型和其对应的包装器类型之间可以自动的进行转换，我们称之为自动装箱和自动拆箱。

在 Java 中还提供了两个用于进行高精度计算的类：`BigInteger`和`BigDecimal`。它们可以进行类似于基本数据类型的加减乘除运算，不过需要通过调用方法来实现，例如`add()`用于将两个高精度的数值进行求和操作。

- **`BigInteger`支持任意精度的整数**。也就是说，它可以表示任何大小的整数，而且不会像基本数据类型一旦超过其存储范围就会丢失精度。
- **`BigDecimal`支持任意精度的定点数**。利用这一特性，我们常用它进行精密的货币计算。

### 字段、方法和 static 关键字

若类的某个成员是基本数据类型，即使我们没有对其进行初始化操作，Java 也会确保它获得一个默认值。感觉兴趣的同学，可以执行以下程序进行测试：


```
/**
 * author:Charies Gavin
 * date:2017/12/05,8:50
 * https:github.com/guobinhit
 * description:测试默认值
 */
public class DefaultValue {
    /**
     * 定义基本数据类型成员变量
     */
    public static boolean aBoolean;
    public static char aChar;
    public static byte aByte;
    public static short aShort;
    public static int anInt;
    public static long aLong;
    public static float aFloat;
    public static double aDouble;
    public static void main(String[] args) {
        /**
         * 直接输出未手动初始化的成员变量进行测试
         */
        System.out.println("aBoolean : " + aBoolean + " aChar : " + aChar + " aByte : " + aByte + " aShort : " + aShort
                + " anInt : " + anInt + " aLong : " + aLong + " aFloat : " + aFloat + " aDouble : " + aDouble);
    }
}
```
对于不同的基本数据类型，其默认初始化值也不同，具体为：

| 基本类型 | 默认值 | 
| ------------- |:-------------| 
| `boolean` | `false` |
| `char` | `'\uoooo'(null)` | 
| `byte` | `(byte)0` | 
|`short`|`(short)0`|
|`int`|`0`|
|`long`|`0L`|
|`float`|`0.0F`|
|`double`|`0.0D`|

But，上述的默认初始化值并不适用于局部变量，即定义的变量并非是类级别的字段而是方法级别的字段，例如

```
public int defaultValue(){
    int x;
    return x;
}
```
这时，上述的变量`x`并不会自动的进行初始化，如果我们不手动进行初始化的话，编译器就会报错了。

对于方法（也可以称之为函数）而言，返回类型表示调用方法之后返回的值的类型；参数列表表示传递给方法的信息的类型和名称。方法名和参数列表构成了「方法签名」，它能够唯一的标识出一个具体的方法。

在我们创建一个类的时候，实际上只是给出了类对象的描述，包括属性（字段）和行为（方法），除非我们用`new`来真正的创建类对象，否则的话，我们并未获得任何对象。**只有在执行`new`来创建对象的时候，数据存储空间才真正被分配，其方法才能够被外界调用**。

当我们声明一个`static`域或方法的时候，就意味着这个域或方法不会与包含它的那个类的任何对象实例联系在一起。使用类名来调用`static`变量是首选方法，即`ClassName.StaticObject`。一个`static`字段对每个类来说都只有一份存储空间，但非`static`字段则是对每个对象都有一份存储空间。


----------

———— ☆☆☆ —— 返回 -> [The Skills of Java](https://github.com/guobinhit/java-skills/blob/master/README.md) <- 目录 —— ☆☆☆ ————
