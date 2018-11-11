# 编程思想 之「泛型」

对于一般的类和方法，只能使用具体的类型：要么是基本类型，要么是自定义类型。如果我们要想编写可以应用于多种类型的代码，这种限制就会对代码产生非常大的束缚。在面向对象的语言中，多态算是一种泛化的机制，因为我们可以将方法的参数类型设为基类，那么该方法就可以接受从这个基类中导出的任何类作为参数。为了实现将代码复用于多种类型，Java SE5 引入了一个非常重要的概念“泛型”，其含义为：**泛化的代码，适用于多种具体的类型**。遗憾的是，虽然 Java 泛型的出现使得 Java 向前迈进了一大步，但是由于 Java 语言是在发行近 10 年后才引入泛型的，为了兼容老代码，Java 的泛型并不纯粹。

## 泛型

在 Java SE5 之前，我们为了持有多个类型的对象，可以直接持有`Object`类型的对象，例如：

```
public class SimpleHolder {
    private Object obj;

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public static void main(String[] args) {
        SimpleHolder holder = new SimpleHolder();
        holder.setObj("HelloWorld");
        String s = (String) holder.getObj();
        System.out.println(s);
        holder.setObj(521);
        Integer i = (Integer) holder.getObj();
        System.out.println(i);
    }
}
```

如上述代码所示，我们只创建了一个`SimpleHolder`类型的对象，其却先后持有了两种不同类型的对象。但是创建泛型的主要目的之一是用来指定容器持有什么类型的对象，而且由编译器来保证类型的正确性。因此，与其使用`Object`类型，我们更喜欢暂时不指定类型，而是稍后再决定具体使用什么类型。要达到这个目的，需要使用**类型参数，用尖括号扩住，放在类名后面**，然后在使用这个类的时候，用实际的参数类型替换此参数，如下面的示例中，`T`就是类型参数：

```
public class GenericHolder<T> {
    private T obj;

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public static void main(String[] args) {
        GenericHolder<String> holder = new GenericHolder<String>();
        holder.setObj("HelloWorld");
        String s = holder.getObj();
        System.out.println(s);
//        holder.setObj(521);
//        Integer i = (Integer) holder.getObj();
//        System.out.println(i);
    }
}
```

如上述代码所示，在我们创建`GenericHolder`对象的时候，必须指明想要持有什么类型的对象，并将其置于尖括号内，然后`GenericHolder`对象只能持有该类型或其子类型，不允许再持有其他类型的对象，例如我们在上面声明`GenericHolder`对象持有`String`类型的对象，要是再持有`Integer`类型（`int`类型自动装箱）的对象，编译器就会报错了，这说明了泛型的核心概念：**告诉编译器我们想要使用什么类型，然后编译器帮我们处理一起细节**。泛型也可以应用于接口，例如下面的生成器接口：

```
public interface Generator<T> {
    T next();
}
```

在实现泛型接口的时候，与实现一般的接口唯一的区别就是：**需要指定泛型接口的类型参数**。

```
public class Fibonacci implements Generator<Integer> {
    private int count = 0;

    @Override
    public Integer next() {
        return fin(count++);
    }

    private int fin(int n) {
        if (n < 2) {
            return 1;
        } else {
            return fin(n - 2) + fin(n - 1);
        }
    }

    public static void main(String[] args) {
        Fibonacci fibonacci = new Fibonacci();
        for (int i = 0; i < 20; i++) {
            System.out.println(fibonacci.next() + " ");
        }
    }
}
```

如上述代码所示，我们在实现`Generator`接口的时候，指明了类型参数为`Integer`，但是在`Fibonacci`类内部，我们使用的明明都是`int`类型啊，为啥不直接将类型参数设为`int`类型呢？这引出了 Java 泛型的一个局限性，即：**基本数据类型不能作为类型参数**。不过，由于 Java SE5 在引入泛型的同时，还引入了基本数据类型的自动装箱和自动拆箱功能，因此这个局限并不影响我们的使用。除了泛型接口，还有泛型方法，想要定义泛型方法，只需将泛型参数列表置于方法的返回值之前：

```
public class GenericMethod {
    // 定义泛型方法，只需将泛型参数列表置于方法的返回值之前
    private  <T> void getParameterClassName(T t) {
        System.out.println(t.getClass().getName());
    }

    public static void main(String[] args) {
        GenericMethod gm = new GenericMethod();
        gm.getParameterClassName("zora");
        gm.getParameterClassName(521);
        gm.getParameterClassName(new String[]{"love"});
    }
}
```

如上述代码所示，**是否拥有泛型方法，与其所在的类是否是泛型并没有关系**。如果`static`方法需要使用泛型能力，就必须使其成为泛型方法。此外，当我们使用泛型类时，必须在创建对象的时候指定类型参数的值，而使用泛型方法的时候，通常不必指明参数类型的值，因为编译器会为我们找出具体的类型，这称之为类型参数推断。在泛型方法中，我们也可以显式地指明类型，不过这种语法很少使用。要显式地指明类型，必须在点操作符与方法名之间插入尖括号，然后把类型设置与尖括号内；

- 如果是在定义该方法的类内部，必须在点操作符之前使用`this`关键字；
- 如果是使用`static`的方法，必须在点操作符之前加上类名。

```
// 显式地指明类型
GenericMethod gm2 = new GenericMethod();
gm2.<String>getParameterClassName("5211314");
```

泛型方法与可变参数列表也能够很好地共存：

```
public class GenericVarargs {
    // 使用可变参数的泛型方法
    public static <T> List<T> makeList(T... args) {
        List<T> list = new ArrayList<T>();
        for (T item : args) {
            list.add(item);
        }
        return list;
    }

    public static void main(String[] args) {
        List<String> ls = makeList("Hello");
        System.out.println(ls);
        ls = makeList("Hello", "World");
        System.out.println(ls);
    }
}
```
除此之外，泛型还可以应用于内部类以及匿名内部类，而且泛型的一个重要的好处就是能够简单、安全地创建复杂的模型。接下来，看一个有意思的现象：

```
public class ErasedTypeEquivalence {
    public static void main(String[] args) {
        Class c1 = new ArrayList<String>().getClass();
        Class c2 = new ArrayList<Integer>().getClass();
        System.out.println("ArrayList<String> == ArrayList<Integer> : " + (c1 == c2));
    }
}
```

![erased-type-equivalence](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/generic-paradigm/erased-type-equivalence.png)

如上述代码及运行结果所示，这是一个残酷的事实：**在泛型代码内部，无法获得任何有关泛型参数类型的信息**。究其原因，Java 的泛型是使用擦除来实现的，这意味着当我没在使用泛型时， 任何具体的类型信息都被擦除了，我们唯一知道的就是我们在使用一个对象。因此，`List<String>`和`List<Integer>`在运行时实际上是相同的类型，这两种形式都被擦除成`List`，即原生类型。有的时候，我们可能会用到如下形式：

```
class ClassName<T extends Erased, Erased2>
```

这时，泛型类型参数将擦除到它的第一个边界，且编译器实际上会把类型参数替换为它的擦除，如上述形式所示，泛型类型参数将被擦除到`Eraesd`。在基于擦除的实现中，泛型类型被当做第二类型处理，即不能在某些重要的上下文环境中使用的类型。**泛型类型只有在静态类型检查期间才出现，在此之后，程序中的所有泛型类型都将被擦除，替换为它们的非泛型上界**。擦除的核心动机是它使得泛化的客户端可以用非泛化的类库来使用，反之亦然，这经常被称之为“迁移兼容性”。

泛型不能用于显示地引用运行时类型的操作之中，我们必须时刻提醒自己，我们只是看起来好像拥有有关参数的类型信息而已。但是即使擦除在方法或类内部移除了有关实际类型的信息，编译器仍然可以确保在方法或类内部使用的类型的内部一致性。因为擦除在方法体中移除了类型信息，所以在运行时的问题就是边界，即**对象进入和离开方法的地点**，这正是编译器在编译期执行类型检查并插入转型代码的地点。





----------

———— ☆☆☆ —— 返回 -> [The Skills of Java](https://github.com/guobinhit/java-skills/blob/master/README.md) <- 目录 —— ☆☆☆ ————
