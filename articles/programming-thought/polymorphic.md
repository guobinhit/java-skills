# 编程思想 之「多态、初始化顺序、协变返回类型」

在面向对象的编程语言中，有三个特性，分别为：**封装**、**继承**和**多态**。实现多态的前提是继承，多态的作用是消除类型之间的耦合关系。对于多态，我们常说的词有两个，分别为：**向上转型**和**向下转型**。

我们**把对某个对象的引用视为其基类型的引用的做法，称之为向上转型**；把对某个对象的引用视为其导出类型的引用的做法，称之为向下转型。之所以如此称呼，是因为我们习惯性在类的继承体系中把基类放在最上面，而把导出类放在下面，因此从导出类到基类的过程是一个向上看的过程，反之亦然。

![extends-system](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/polymorphic/extends-system.png)

在「[语言导论](https://github.com/guobinhit/java-skills/blob/master/articles/programming-thought/language-guide.md)」中，我们曾提到前期绑定和后期绑定的概念，我们**将一个方法调用同一个方法主体关联起来的动作称之为「绑定」**。如果在程序执行前就进行绑定（由编译器和链接程序实现），这就是前期绑定，它是面向过程的编程语言的默认绑定方式，例如 C 语言；如果在运行时根据对象的类型进行绑定，这就是后期绑定，也称之为动态绑定和运行时绑定。

在 Java 中，除了`static`方法和`final`方法（`private`方法属于`final`方法）之外，其他所有方法都是后期绑定。**如果想要取消某个方法的后期绑定，将其声明为`final`类型即可**。

对于多态或者说继承，Java 是有一个“缺陷”的，那就是：**不能覆盖`private`方法**。测试代码如下，

```
package com.hit.chapter8;

/**
 * author:Charies Gavin
 * date:2018/1/3,8:55
 * https:github.com/guobinhit
 * description:测试私有方法是否能被覆盖
 */
public class PrivateOverride {
    private void test_1() {
        System.out.println("私有方法能被覆盖吗？答案：不能。");
    }

    public void test_2() {
        System.out.println("公有方法能被覆盖吗？答案：能。");
    }

    public static void main(String[] args) {
        PrivateOverride po = new DerivedOverride();
        po.test_1();
        po.test_2();
    }
}

class DerivedOverride extends PrivateOverride {
    public void test_1() {
        System.out.println("Oh, my god, 我们成功覆盖了私有方法！");
    }

    public void test_2() {
        System.out.println("Hi, buddy, 我们成功覆盖了公有方法！");
    }
}
```
![private-override](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/polymorphic/private-override.png)

通过上面的测试，我们发现：**只有非`private`方法才能被覆盖**。因此，在导出类中，对于基类中的`private`方法，最好采用不同的名字，以防止混乱的发生。此外，**只有普通的方法调用是多态的，如果某个方法是静态的，它的行为就是不具有多态性**。静态方法是与类，而不是与单个的对象相关联的。**任何域访问操作都是由编译器继解析，因此域也不是多态的**。

### 初始化顺序测试

在这一部分，我们来测试程序的初始化顺序，包括静态初始化、非静态初始化、实例初始化和构造器等，完整的测试代码如下：

```
package com.hit.chapter8;

/**
 * author:Charies Gavin
 * date:2018/1/3,9:16
 * https:github.com/guobinhit
 * description:测试初始化顺序，球形继承圆形类，间接继承图形基类(不是特别恰当)
 */
public class Global extends Circle {
    /**
     * 静态初始化块
     */
    static {
        System.out.println("Global: Static Initial.");
    }

    /**
     * 非静态初始化块
     */ {
        System.out.println("Global: Non-static Initial.");
    }

    /**
     * 默认无参构造方法
     */
    Global() {
        System.out.println("Global: Structure Method Initial.");
    }

    /**
     * 有参构造方法
     */
    Global(String str) {
        System.out.println("Global: Structure Method Initial ... " + str);
    }

    /**
     * 静态成员实例初始化
     */
    private static Circle staticircle = new Circle("in Global of Static Instance Initial.");

    /**
     * 非静态成员实例初始化
     */
    private Circle circle = new Circle("in Global of Non-static Instance Initial.");

    public static void main(String[] args) {
        /**
         * 在主方法中调用导出类构造方法，测试初始化顺序
         */
        new Global();
        System.out.println("Initial Over!");
    }
}

/**
 * 图形基类
 */
class Shape {

    /**
     * 静态初始化块
     */
    static {
        System.out.println("Shape: Static Initial.");
    }

    /**
     * 非静态初始化块
     */ {
        System.out.println("Shape: Non-static Initial.");
    }

    /**
     * 默认无参构造方法
     */
    Shape() {
        System.out.println("Shape: Structure Method Initial.");
    }

    /**
     * 有参构造方法
     */
    Shape(String str) {
        System.out.println("Shape: Structure Method Initial ... " + str);
    }
}

/**
 * 圆形继承图形基类
 */
class Circle extends Shape {
    /**
     * 静态初始化块
     */
    static {
        System.out.println("Circle: Static Initial.");
    }

    /**
     * 非静态初始化块
     */ {
        System.out.println("Circle: Non-static Initial.");
    }

    /**
     * 默认无参构造方法
     */
    Circle() {
        System.out.println("Circle: Structure Method Initial.");
    }

    /**
     * 有参构造方法
     */
    Circle(String str) {
        System.out.println("Circle: Structure Method Initial ... " + str);
    }

    /**
     * 静态成员实例初始化
     */
    private static Shape staticShape = new Shape("in Circle of Static Instance Initial.");

    /**
     * 非静态成员实例初始化
     */
    private Shape shape = new Shape("in Circle of Non-static Instance Initial.");
}
```
![global](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/polymorphic/global.png)

观察上图，我们能够发现程序的初始化规律。以`基类1 -> 基类2 -> 导出类`的继承结构为例，初始化顺序大致如下：

- 从`基类1`开始，先进行静态初始化，然后依次向外扩散至`基类2`和`导出类`；
- 然后，依次进行`基类1`的非静态初始化和构造器初始化；
- 再依次进行`基类2`的非静态初始化和构造器初始化；
- 最后，才是`导出类`的非静态初始化和构造器初始化。

如果当前类含有静态实例初始化，则它的静态实例初始化将在基类的非静态初始化和构造器初始化之前执行，也在导出类的静态初始化之前执行；如果还含有非静态实例初始化，它会在当前类的非静态初始化之后、构造器初始化之前，进行初始化。此外，无论是静态初始化还是非静态初始化，都会在构造器初始化之前进行初始化。实际上，在上述任何初始化动作发生之前，都会先将分配给对象的存储空间初始化为二进制的零。

现在，我们已经知道了对象的初始化顺序，与之相反的，则是对象的销毁顺序。由于字段的初始化顺序是按照声明的顺序进行的，因此对于字段，销毁的顺序意味着与声明的顺序相反。对于基类，则是先对其导出类进行清理，然后才是基类。

### 协变返回类型

在 Java SE5 中，添加了协变返回类型，它表示**在导出类中的被覆盖的方法可以返回基类方法的返回类型的某种导出类型**。对于上述协变返回类型的定义，读起来有些让人吐血，简单点，通过下面的程序理解协变返回类型：

```
package com.hit.chapter8;

/**
 * author:Charies Gavin
 * date:2018/1/4,22:10
 * https:github.com/guobinhit
 * description:测试协变返回类型
 */
public class CovariantReturnType {
    public static void main(String[] args) {
        Flower flower = new Flower();
        Plant plant = flower.kind();
        System.out.println("未使用协变返回类型：" + plant);
        // 使用协变返回类型
        flower = new Luoyangred();
        plant = flower.kind();
        System.out.println("使用协变返回类型后：" + plant);
    }
}

/**
 * 植物基类
 */
class Plant {
    public String toString() {
        return "Plant";
    }
}

/**
 * 牡丹花，继承自植物基类
 */
class Peony extends Plant {
    public String toString() {
        return "Peony";
    }
}

/**
 * 花
 */
class Flower {
    Plant kind() {
        return new Plant();
    }
}

/**
 * 洛阳红，十大贵品牡丹花之一
 */
class Luoyangred extends Flower {
    Peony kind() {
        return new Peony();
    }
}
```
![covariant-return-type](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/polymorphic/covariant-return-type.png)

如上图所示，展示了使用协变返回类型后的效果。在 Java SE5 之前，强制导出类中被覆盖的方法必须返回基类方法的返回类型，但是在增加协变返回类型之后，我们可以让在导出类中被覆盖的方法返回基类方法的返回类型的某种导出类型，也就是说可以返回更加具体的返回类型。例如上例中的`kind()`方法，在 Java SE5 之前，只能返回`Plant`，但是在使用协变返回类型之后，我们可以直接返回更加具体的`Peony`类型。

----------

———— ☆☆☆ —— 返回 -> [The Skills of Java](https://github.com/guobinhit/java-skills/blob/master/README.md) <- 目录 —— ☆☆☆ ————
