# 编程思想 之「初始化与清理」

与其他语言相比，Java 的一大特点就是其自动的初始化与清理功能。对于基本数据类型的全局变量，Java 自动将其初始化为对应的默认值，具体可以参考「[对象漫谈](https://github.com/guobinhit/java-skills/blob/master/articles/programming-thought/object-ramble.md)」中的内容；对于对象，我们可以通过构造方法对其进行初始化；对于清理操作，Java 提供了垃圾回收机制，其可以帮我们自动清理不再使用的对象，释放资源。

### 构造方法

构造方法是一种特殊的方法，它是一个与类同名且没有返回值类型的方法。对象的创建就是通过构造方法来完成，其功能主要是完成对象的初始化。当类实例化一个对象时会自动调用构造方法。构造方法和其他方法一样也可以重载。在 Java 中，任何变量在被使用前都必须先设置初值，构造方法就是专门为类的成员变量赋初值的方法。构造方法的特殊性主要反映在如下几个方面：

 - 构造方法的作用主要有两个，分别为构造出来一个类的实例和对构造出来的类的实例（对象）进行初始化；
 - 构造方法的名字必须与定义他的类名完全相同，没有返回类型，甚至连`void`也没有；
 - 主要完成对象的初始化工作，构造方法的调用是在创建一个对象时使用`new`操作完成的；
 - 类中必定有构造方法，若不写，系统自动添加无参构造方法；
 - 接口不允许被实例化，因此接口中没有构造方法；
 - 不能被`static`、`final`、`synchronized`、`abstract`和`native`修饰；
 - 构造方法在初始化对象时自动执行，一般不能显式地直接调用；
 - 当同一个类存在多个构造方法时，Java 编译系统会自动按照初始化时最后面括号的参数个数以及参数类型来自动的一一对应，完成构造函数的调用；
 - 构造方法分为两种，分别为无参的构造方法和有参的构造方法；
 - 构造方法可以被重载，没有参数的构造方法称为默认构造方法，与一般的方法一样，构造方法可以进行任何操作，但是经常将它设计为进行各种初始化操作，比如初始化对象的属性。　　

我们也可以通过构造代码块（包括静态和非静态两种）给对象进行初始化，**对象一建立构造代码块就执行，而且优先于构造函数执行**。构造代码块和构造函数的区别在于构造代码块是给所有不同对象的共性进行统一初始化，构造函数则是给对应的对象进行初始化。

此外，还有一点需要特别注意：**如果类中定义了构造方法且都不是无参的，那么编译器也不会自动创建无参的构造方法，而是根据参数个数和类型，按顺序进行匹配，直到找到对应的构造方法；当我们调用了无参的构造方法实例化对象时，编译器就会报错啦，因为现在全是有参的构造方法，没有无参的构造方法。**

在 GitHub 的项目「[java-skills](https://github.com/guobinhit/java-skills)」中，提供了测试代码，感谢兴趣的同学可以自行下载体验。

### 初始化

对于 Java 语言，它会尽量保证：所有变量在使用前都能够得到恰当的初始化。我们也无法阻止自动初始化的进行，它将在构造器被调用之前发生。在类的内部，**变量定义的先后顺序决定了初始化的顺序，即使变量定义散布于方法定义之间，它们仍然会在任何方法（包括构造器）被调用之前得到初始化**。

无论创建多少个对象，静态数据都只占用一份存储区域。由于`static`关键字不能应用于局部变量，因此它只能作用于域。静态初始化只有在必要时刻才会进行，静态对象也不会再次被初始化。

初始化的顺序是先静态对象（如果它们尚未因前面的对象创建过程而被初始化），然后是非静态对象。**构造方法可以看成静态方法**。静态初始化只在`Class`对象首次加载的时候进行一次。

显式的静态初始化和非静态的实例初始化类似，都在构造器之前执行初始化动作，两者的区别在于：静态初始化有`static`关键字修饰且只能被初始化一次；而非静态的实例初始化则可以被初始化多次。


### 清理：垃圾回收

垃圾回收，一直都是 Java 语言中一个值得称赞的特性。But，它并没有我们想象中的那么好用。**垃圾回收只与内存有关**，也就是说，使用垃圾回收的唯一原因就是为了回收程序不再使用的内存。如果 JVM 并未面临内存耗尽的情形，它是不会浪费时间去执行垃圾回收以恢复内存的。这意味着，垃圾回收不能如我们预期那样的工作，甚至它可能都不会工作，以至于某些已经废弃的对象根本就不会被回收。

有些人可能会说，“我们可以调用`finaliza()`方法来保证垃圾回收器的工作”，这其实是误解了`finaliza()`方法的含义。`finaliza()`方法的工作原理是：一旦垃圾回收器准备好释放对象占用的存储空间，将首先调用其`finaliza()`方法，并且在下一次垃圾回收动作发生时，才会真正回收对象占用的内存。因此，利用`finaliza()`方法，我们可以在垃圾回收器执行时做一些重要的清理工作。


### 方法重载与 this 关键字

在「[对象漫谈](https://github.com/guobinhit/java-skills/blob/master/articles/programming-thought/object-ramble.md)」中，我们曾说过，**方法名和参数列表构成了「方法签名」，它能够唯一的标识出一个具体的方法**。方法重载，是指方法名相同，而参数个数不同、参数类型不同或者参数个数和参数类型都不同。实际上，参数顺序不同也可以区分两个方法，但是建议不要这么做，因此这会让代码难以维护。例如，

```
package com.hit.chapter5;

/**
 * author:Charies Gavin
 * date:2017/12/22,21:04
 * https:github.com/guobinhit
 * description:测试方法重载
 */
public class Overload {
    public static void main(String[] args) {
        introduce(18, "Charies");
        introduce("Charies", 18);
    }

    public static void introduce(int age, String name) {
        System.out.println("My name is " + name + ", I'm " + age + " old!");
    }

    public static void introduce(String name, int age) {
        System.out.println("My name is " + name + ", I'm " + age + " old!");
    }
}
```

如上述代码所示，我们通过声明不同的参数顺序，区分了重载方法。But，**我们不能通过方法的返回值来区分重载方法**。

此外，**如果传入的数据类型（实际参数类型）小于方法中声明的形式参数类型，实际参数类型就会被提升**，如声明`int`类型、传入`short`类型，则`short`类型会自动被提升为`int`类型。**如果传入的实际参数类型较大，就会通过类型转换来执行窄化转换**，如声明`int`类型、传入`double`类型，则`double`类型会自动窄化为`int`类型。

对于同一个类型的多个对象，编译器是如何让它们能够正确调用各自的方法呢？例如，

```
package com.hit.chapter5;

/**
 * author:Charies Gavin
 * date:2017/12/25,9:07
 * https:github.com/guobinhit
 * description:测试 this 关键字
 */
public class MottoHIT {
    public static void main(String[] args) {
        Hiter hiter_1 = new Hiter();
        Hiter hiter_2 = new Hiter();
        hiter_1.sayMotto("Zora");
        hiter_2.sayMotto("Charies");
    }
}

class Hiter {
    public void sayMotto(String name) {
        System.out.println(name + ": 规格严格，功夫到家");
    }
}
```
![motto-hit](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/initial-and-clean/motto-hit.png)

实际上，在编译的时候，编译器会“偷偷”的将“所操作对象的引用”当做第一个参数传递给调用的方法，例如上述的调用会变为：

```
Hiter.sayMotto(hiter_1, "Zora");
Hiter.sayMotto(hiter_2, "Charies");
```
上面的格式为编译器内部的表示形式，我们并不能这样书写代码，也编译不过。为此，Java 提供了一个关键字`this`，**`this`只能在方法的内部调用，表示“调用方法的那个对象”的引用**。

此外，**如果在构造器中为`this`添加了参数列表，那么将产生对符合此参数列表的构造器的明确调用**。尽管可以用`this`调用构造器，但仅能调用一个不能调用多个，并且对构造器的调用必须为构造器内的第一行代码，否则编译器就会报错。如下图所示，

![class-animal](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/initial-and-clean/class-animal.png)

在了解`this`关键字之后，我们会发现`static`方法其实就是没有`this`关键字的方法。我们也应该听说过，“在静态方法的内部不能调用非静态方法，反之可以”。实际上，这并不是绝对的，如果我们传递一个对象的引用到静态方法里，然后通过这个引用，我们就能够调用非静态方法和非静态的数据成员。




----------

———— ☆☆☆ —— 返回 -> [The Skills of Java](https://github.com/guobinhit/java-skills/blob/master/README.md) <- 目录 —— ☆☆☆ ————
