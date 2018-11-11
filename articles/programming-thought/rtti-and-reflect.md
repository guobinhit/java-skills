# 编程思想 之「运行时类型识别、反射」

运行时类型信息能够让我们在程序运行时发现和使用类型信息。在运行时识别对象和类的信息，主要有两个方式，分别为：

- 一种是传统的`RTTI`，它假定我们在编译时就已经知道了所有的类型；
- 另一种是反射机制，它允许我们在运行时发现和使用类的信息。

在本章中，我们就来了解这两种在运行时识别对象和类信息的方式。

## 运行时类型识别

运行时类型识别，简写为`RTTI`，即`Run-Time Type Identification`，含义为：在运行时，识别一个对象的类型。

Java 使用 Class 对象来执行其`RTTI`，而 Class 对象，包含了与类有关的信息。我们曾说过，“每个类在编译过后都会产生了一个`.class`文件”，实际上，每个`.class`文件都代表了一个 Class 对象。所有的类都是在对其第一次使用时，动态加载到 JVM 中的，因此 Java 程序在它运行之前并非被完全加载，其各个部分是在必需时才加载的。

```
package com.hit.thought.chapter12;

/**
 * author:Charies Gavin
 * date:2018/3/17,18:00
 * https:github.com/guobinhit
 * description:测试类加载
 */
public class LoadClass {
    public static void main(String[] args) {
        System.out.println("Hello BeiJing!");
        new ChaoYang();
        try {
            // Class 类的静态 forName() 方法的参数为类的全限定名(包括包名)字符串
            Class.forName("com.hit.thought.chapter12.XiCheng");
        } catch (ClassNotFoundException e) {
            System.out.println("Not Fund Class!");
        }
        new DongCheng();
        System.out.println("Bye BeiJing!");
    }
}

class ChaoYang {
    static {
        System.out.println("CHAOYANG!");
    }
}

class XiCheng {
    static {
        System.out.println("XICHENG!");
    }
}

class DongCheng {
    static {
        System.out.println("DONGCHENG!");
    }
}
```

![load-class](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/rtti-and-reflect/load-class.png)

如上图所示，Class 对象仅在需要的时候才被加载，`static`初始化则是在类加载时进行的。其中，`forName()`为 Class 类（所有 Class 对象都属于这个类）一个静态方法，它以类的全限定名（包括包名）字符串为参数，返回值为是对应参数的 Class 对象的引用。**无论何时，只要我们想在运行时使用类型信息，就必须首先获得对恰当的 Class 对象的引用**。此外，如果想使用`newIntance()`方法来创建类实例，则该类必须含有默认（无参）构造器，代码示例已在「[java-skills](https://github.com/guobinhit/java-skills)」中给出。

Java 还提供了另外一种获取 Class 对象引用的方法，即使用**类字面常量**，其形式为：

- `ClassName.class`

如上例中 LoadClass 类的类字面常量为`LoadClass.class`，类字面常量根除了对`forName()`方法的调用且在编译时就会受到检查，因此它更安全，也更高效。**类字面常量不仅可以应用于普通的类，也可以应用于接口、数组以及基本数据类型**。此外，对于基本数据类型的包装器类，还有一个标准字段`Type`，`Type`字段是一个引用，指向对应的基本数据类型的 Class 对象。

在这里，有一点需要我们特别注意，那就是：**当使用`.class`来创建对 Class 对象的引用时，不会自动地初始化该 Class 对象**。为了使用类而做的准备工作实际上包含三个步骤，分别为：

- **加载**：由类加载器执行，该步骤将查找字节码（通常在环境变量所指定的路径中查找），并从这些字节码中创建一个 Class 对象；
- **链接**：在这个阶段将验证类中的字节码，为静态域分配存储空间，并且如果必需的话，将解析这个类创建的对其他类的所有引用；
- **初始化**：如果该类具有超类，则对其初始化，执行静态初始化器和静态初始化块。

因此，在使用`.class`来创建对 Class 对象的引用时，初始化被延迟到了对静态方法（构造器隐式地是静态的）或者非常数静态域进行首次引用时才执行：

```
public class DotClass {
    public static Random random = new Random();

    public static void main(String[] args) {

        // 使用 .class 获取 Class 对象，并不直接初始化
        Class initable = Initable.class;

        System.out.println("After creating Initable reference!");

        System.out.println(Initable.staticFinal);

        System.out.println(Initable.staticFinal2);

        System.out.println(Initable2.staticNonFinal);

        Class initable3 = null;

        try {
            // 使用 forName() 方法获取 Class 对象，直接进行初始化
            initable3 = Class.forName("com.hit.thought.chapter12.Initable3");
        } catch (ClassNotFoundException e) {
            System.out.println("Sorry, I don't get Initable3 class!");
        }

        System.out.println("After creating Initable3 reference!");

        System.out.println(Initable3.staticNonFinal);
    }
}

class Initable {
    // static final 常量，编译期常量
    static final int staticFinal = 20180202;

    // static final 常量，非编译期常量
    static final int staticFinal2 = DotClass.random.nextInt(100);

    static {
        System.out.println("Initialing Initable");
    }
}

class Initable2 {
    // static 非 final 常量
    static int staticNonFinal = 20180218;

    static {
        System.out.println("Initialing Initable2");
    }
}

class Initable3 {
    // static 非 final 常量
    static int staticNonFinal = 20180318;

    static {
        System.out.println("Initialing Initable3");
    }
}
```

![dot-class](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/rtti-and-reflect/dot-class.png)

如上述代码及结果图所示，使用`.class`获取 Class 对象，并没有直接进行初始化操作，而是在我们调用其静态成员的时候才进行初始化操作；使用`forName()`方法获取 Class 对象，则是直接进行初始化，因为我们可以看到`Initable3`中静态块中的输出语句先输出。此外，如果一个`static final`值是编译期常量，如`Initable.staticFinal`，那么这个值不需要对类进行初始化就可以读取；如果一个`static final`值不是编译期常量，如`Initable.staticFinal2`，那么对`Initable.staticFinal2`的访问将强制先进行类的初始化操作，然后才能获取该值。如果一个`static`域不是`final`的，那么在访问它时，总是要求在它被读取之前，先进行链接（为这个域分配存储空间）和初始化（初始化该存储空间）。

Class 引用总是指向某个 Class 对象，它可以制造类的实例，并包含可作用于这些实例的所有方法代码。通过使用泛型语法，可以让编译器强制执行额外的类型检查，因此在 Java SE5 中，`Class<?>`优于平凡的`Class`，即便两者是等价的。如果我们想要创建一个 Class 引用，它被限定为某种类型或者该类型的任何子类型，我们可以使用通配符`?`与关键字`extends`结合的方法，如`Class<? extends Object>`，创建一个范围。

到现在为止，我们已经了解到的`RTTI`运行时类型识别形式，包括：

- 传统的类型转换，如`(Object) SubClass`；
- 代表对象的类型的 Class 对象。

实际上，`RTTI`除了上述两种形式之外，还有第三种形式，即`instanceof`关键字，其返回一个布尔值，告诉我们对象是不是某个特定类型的实例。Java 对`instanceof`有比较严格的限制，只能将其与命名类型进行比较，而不能与 Class 对象作比较。此外，`Class.isInstance`方法提供了一种动态检测对象的途径。

```
public class CheckClass {
    public static void main(String[] args) {
        Letter a = new Letter("a");
        Letter b = new Letter("b");
        Letter c = new Letter("c");

//        if (a instanceof Letter) {
//            a.printlnName();
//        }
//        if (b instanceof Letter) {
//            b.printlnName();
//        }
//        if (c instanceof Letter) {
//            c.printlnName();
//        }

        List<Letter> letters = new ArrayList<Letter>();

        letters.add(a);
        letters.add(b);
        letters.add(c);

        // 获取 Letter 类对象
        Class letterClass = Letter.class;

        // 循环 letters 列表
        for (Letter letter : letters) {
            // 动态测试对象类型
            if (letterClass.isInstance(letter)) {
                letter.printlnName();
            }
        }
    }

}

class Letter {
    String name;

    public Letter(String name) {
        this.name = name;
    }

    public void printlnName() {
        System.out.println("This is a Letter class instance: " + name);
    }
}
```
如上述代码所示，使用`Class.isInstance`方法，消除了对`instanceof`语句枯燥的调用。此外，在查询类型信息的时候，使用`instanceof`的形式（即以`instanceof`的形式或者`isInstance()`的形式，它们产生的结果相同）与直接比较 Class 对象有一个很重要的区别。观察如下示例：

```
public class CompareClass {
    public static void main(String[] args) {
        compareClass(new BaseClass());
        System.out.println();
        compareClass(new SubClass());
    }

    public static void compareClass(Object o) {
        // 获取待测试类的类类型
        System.out.println("Testing o of type : " + o.getClass());

        // 通过 instanceof 和 isInstance() 进行比较
        System.out.println("o instanceof BaseClass : " + (o instanceof BaseClass));
        System.out.println("o instanceof SubClass : " + (o instanceof SubClass));
        System.out.println("BaseClass.isInstance(o) : " + BaseClass.class.isInstance(o));
        System.out.println("SubClass.isInstance(o) : " + SubClass.class.isInstance(o));

        // 通过 == 和 equals 进行比较
        System.out.println("o.getClass() == BaseClass.class : " + (o.getClass() == BaseClass.class));
        System.out.println("o.getClass() == SubClass.class : " + (o.getClass() == SubClass.class));
        System.out.println("o.getClass.equals(BaseClass.class) : " + o.getClass().equals(BaseClass.class));
        System.out.println("o.getClass.equals(SubClass.class) : " + o.getClass().equals(SubClass.class));
    }
}

class BaseClass {
}

class SubClass extends BaseClass {
}
```

![compare-class](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/rtti-and-reflect/compare-class.png)

通过观察上述代码及结果图，我们可以得出结论：

- `instanceof`和`isInstance()`保持了类型的概念，表示“你是这个类或者这个类的派生类吗？”
- `==`和`equals()`仅比较实际的 Class 对象，没有考虑继承，它要么是这个确切的类型，要么不是。



## 反射


运行时类型识别，有一个很大的局限，那就是：在编译时，编译器必须知道所有要通过`RTTI`来处理的类。但实际上，我们并不能在编译期知道所有要处理的类，因此这就需要我们通过其他手段在程序运行时来发现和获取类型信息，Java 中使用的方法称之为反射。

在 Java 中，反射是通过`Class`类和`java.lang.reflect`类库来实现的，其中`java.lang.reflect`类库包含了`Field`、`Method`以及`Constructor`类（每个类都实现了`Member`接口），这些类的对象是由 JVM 在运行时创建的，用来表示未知类里对应的成员。我们可以使用`Constructor`创建新的对象，用`get()`和`set()`方法读取和修改`Field`对象关联的字段，用`invoke()`方法调用与`Method`对象关联的方法。另外，我们还可以通过`getFields()`、`getMethods()`和`getConstructors()`方法来获取未知类中对应字段、方法以及构造器的对象的数组。

在这里，我们要意识到：反射并没有什么神奇之处！当通过反射来处理一个未知类型的对象时，JVM 只是简单地检查这个对象，看它属于哪个特定的类，并规定在用它做其他事情之前必须先加载这个类的 Class 对象而已。因此，这个类的`.class`文件对于 JVM 来说必须是可以获取的，要么在本地机器上获取， 要么在网络上获取。所有`RTTI`和反射之间真正的区别只在于：

- 对于`RTTI`来说，编译器在编译时打开和检查`.class`文件；
- 对于反射来说，`.class`文件在编译时是不可获取的，而是在运行时打开和检查`.class`文件。

通常情况下，我们并不需要直接使用反射，反射在 Java 中是用来支持其他特性的，如对象序列化、JavaBean 等。当然，如果能动态的获取某个类的信息还是很有用的，特别是在我们需要创建更加动态的代码时。

代理是基本的设计模式之一，它是我们为了提供额外的或不同的操作，而插入的用来代替“实际”对象的对象。Java 的动态代理比代理的思想更近了一步，因为它可以动态地创建代理并动态地处理对所代理方法的调用。在动态代理上所做的所有调用都会被重定向到单一的调用处理器上，它的工作是揭示调用的类型并确定相应的策略。

```
public class SimpleDynamicProxy {

    public static void main(String[] args) {
        RealObject realObject = new RealObject();

        consumer(realObject);

        Interface proxy = (Interface) Proxy.newProxyInstance(
                // 被代理类的类加载器
                Interface.class.getClassLoader(),
                // 希望该代理实现的接口列表
                new Class[]{Interface.class},
                // InvocationHandler 接口的实现类
                new DynamicProxyHandler(realObject));
        
        consumer(proxy);
    }
    
    public static void consumer(Interface iface) {
        iface.doSomething();
        iface.somethingElse("Ops!");
    }

}

class DynamicProxyHandler implements InvocationHandler {
    private Object proxied;

    public DynamicProxyHandler(Object proxied) {
        this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("---> proxy: " + proxy.getClass() +
                ", method: " + method +
                ", args: " + args);
        if (args != null) {
            for (Object arg : args) {
                System.out.println(" " + arg);
            }
        }
        return method.invoke(proxied, args);
    }
}
```

如上述代码所述，我们通过调用静态方法`Proxy.newProxyInstance()`创建了一个动态代理，该方法需要三个参数，分别为：

- 一个类加载器；
- 一个希望该代理实现的接口列表；
- 一个`InvocationHandler`接口的实现。

动态代理可以将所有调用重定向到调用处理器，因此通常会向调用处理器的构造器传递一个“实际”对象的引用，从而使得调用处理器在执行其任务时，可以将请求转发。通常，我们会执行代理操作，然后使用`Method.invoke()`方法将请求转发给被代理对象，并传入必需的参数。

```
public class TestReflect {
    public static void main(String[] args) throws Exception {
        System.out.println("=== 1 通过反射获取普通类的方法 ===");
        A a = C.makeA();
        a.f();
        System.out.println("类名为：" + a.getClass().getSimpleName());
        callHiddenMethod(a, "g");
        callHiddenMethod(a, "u");
        callHiddenMethod(a, "v");
        callHiddenMethod(a, "w");

        System.out.println("=== 2 通过反射获取私有内部类的方法 ===");
        A a1 = InnerA.makeA();
        a1.f();
        System.out.println("类名为：" + a1.getClass().getSimpleName());
        callHiddenMethod(a1, "g");
        callHiddenMethod(a1, "u");
        callHiddenMethod(a1, "v");
        callHiddenMethod(a1, "w");

        System.out.println("=== 3 通过反射获取匿名类的方法 ===");
        A a2 = AnonymousA.makeA();
        a2.f();
        System.out.println("类名为：" + a2.getClass().getSimpleName());
        callHiddenMethod(a2, "g");
        callHiddenMethod(a2, "u");
        callHiddenMethod(a2, "v");
        callHiddenMethod(a2, "w");

        System.out.println("=== 4 通过反射获取 private final 字段值 ===");
        PrivateFinalField pff = new PrivateFinalField();
        System.out.println(pff);
        // love 为非 static 字段
        Field field = pff.getClass().getDeclaredField("love");
        field.setAccessible(true);
        System.out.println("field.getInt(pff): " + field.get(pff));
        field.set(pff, 520);
        System.out.println(pff);
        // question 为 final 字段
        field = pff.getClass().getDeclaredField("question");
        field.setAccessible(true);
        System.out.println("field.get(pff): " + field.get(pff));
        field.set(pff, "What is your number?");
        System.out.println(pff);
    }

    public static void callHiddenMethod(Object object, String methodName) throws Exception {
        // 通过反射获取方法
        Method method = object.getClass().getDeclaredMethod(methodName);
        // 设置方法为可访问状态
        method.setAccessible(true);
        // 调用方法
        method.invoke(object);
    }
}
```

![test-reflect](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/rtti-and-reflect/test-reflect.png)

如上述代码及结果图所示，我们可以通过反射访问普通类、私有局部类和匿名类的任何访问权限的方法，由此可见反射的强悍之处。看起来没有任何方式可以阻止反射到达并调用那些非公共访问权限的方法。对于域来说，也是如此，即便是`private`和`final`也不能阻止反射，不过虽然`final`不能阻止反射，却可以阻止反射修改字段的值，如上例中的`question`字段。此外，示例中的所有代码均可以在 GitHub 中的「[java-skills](https://github.com/guobinhit/java-skills)」项目中获取！


----------

———— ☆☆☆ —— 返回 -> [The Skills of Java](https://github.com/guobinhit/java-skills/blob/master/README.md) <- 目录 —— ☆☆☆ ————
