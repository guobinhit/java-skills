# 效率编程 之「泛型」

### 第 1 条：请不要在新代码中使用原生态类型

每种泛型都定义了一组参数化类型，其构成格式为：先是类或者接口的名称，接着用尖括号（`<>`）把对应于泛型形式类型参数的实际类型参数列表括起来。例如，`List<String>`（读作“字符串列表”）是一个参数化的类型，表示元素类型为`String`的列表。每个泛型都定义了一个原生态类型，即不带任何实际参数的泛型名称。例如，与`List<E>`相对应的原生态类型是`List`。原生态类型就像是从类型声明中删除了所有泛型信息一样。实际上，原生态类型`List`与 Java 平台没有泛型之前的接口类型`List`完全一样。但是，如果使用原生态类型，就失掉了泛型在安全性和表述性方面的所有优势。

泛型类型有子类化的规则，`List<String>`是原生态类型`List`的一个子类型，而不是参数化类型`List<Object>`的子类型。因此，如果使用像`List`这样的原生态类型，就会失掉类型安全性，但是如果使用像`List<Object>`这样的参数化类型，则不会。如果要使用泛型，但不确定或者不关心实际的类型参数，就可以使用一个问号代替，称之为“无限制的通配符类型”。此外，在类文字中必须使用原生态类型（但是允许使用数组类型和基本类型），而不允许使用参数化类型。换句话说，`List.class`、`String[].class`和`int.class`都合法，但是`List<String>.class`和`List<?>.class`则不合法。

由于泛型信息可以在运行时被擦除，因此在参数化类型而非通配符类型上使用`instanceof`操作符是非法的。总之，使用原生态类型会在运行时导致异常，因此不要在新代码中使用。原生态类型只是为了与引入泛型之前的遗留代码进行兼容和互用而提供的。

### 第 2 条：消除非受检警告以及列表优先于数组

要尽可能地消除每一个非受检警告。如果无法消除警告，同时可以证明引起警告的代码是类型安全的，只有在这种情况下，可以用一个`@SuppressWarnings("unchecked")`注解来禁止这条警告。而且，应该始终在尽可能小的范围中使用`SuppressWarnings`注解。此外，将`SuppressWarnings`注解放在`return`语句中是非法的，因为它不是一个声明，而是应该声明一个局部变量来保持返回值，并注解其声明。每当使用`@SuppressWarnings("unchecked")`注解时，都要添加一条注释，说明为什么这么做事安全的。

数组和泛型不能很好地混合使用，因为数组是协变的、具体的，而泛型只在编译时强化它们的类型信息并在运行时擦除它们的元素类型信息。因此，创建泛型、参数化或者类型参数的数组是非法的。但是，创建泛型、参数化或者类型参数的列表却是合法的。为了获得泛型带来的类型安全，在面对数组和列表都能解决的问题时，要优先选择列表。

### 第 3 条：利用有限制通配符来提示 API 的灵活性

参数化类型是不可变的。换句话说，对于任何两个截然不同的类型`Type1`和`Type2`而言，`List<Type1>`既不是`List<Type2>`的子类型，也不是它的超类型。考虑下面的堆栈 API：

```
public class SimpleStackPECS {
	public SimpleStackPECS();
    public void push(E e);
	public E pop();
	public boolean isEmpty();
}
```

假设我们想要增加一个方法，让它按顺序将一系列的元素全部放到堆栈中。这是第一次尝试，如下：

```
public void pushAll(Iterable<E> src) {
     for (E e : src) {
         push(e);
     }
}
```
上面的方法编译时正确无误，结果却不尽人意。如果`Iterable`的元素类型与堆栈的完成匹配，没有问题；但是如果有一个`SimpleStackPECS<Number>`，并且调用了`push(intVal)`，这里`intVal`为`Integer`类型。这是可以的，因为`Integer`是`Number`的一个子类型。因此从逻辑上来说，下面的代码应该是可以的：

```
SimpleStackPECS<Number> simpleStack = new SimpleStackPECS<Number>();
Iterable<Integer> integers = ...;
simpleStack.pushAll(integers);
```

但是，如果尝试这么做，就会得到下面的错误消息，因为如前文所述，参数化类型是不可变的：

![simple-stack-push-all](https://github.com/guobinhit/java-skills/blob/master/images/effective-programming/generic-paradigm2/simple-stack-push-all.png)

幸运的是，有一种解决办法。Java 提供了一种特殊的参数化类型，称之为“有限制的通配符类型”，来处理类似的情况。`pushAll()`的输入参数不应该为“`E`的`Iterable`接口”，而应该为“`E`的某个子类型的`Iterable`接口”，有一种通配符类型正符合此意：`Iterable<? extends E>`。接下来，我们修改一下`pushAll()`来使用这个类型：

```
public void pushAll(Iterable<? extends E> src) {
    for (E e : src) {
        push(e);
    }
}
```
这么修改了之后，上面我们遇到的问题都解决啦！与`pushAll()`相对应的，我们提供一个`popAll()`方法，从堆栈中弹出每个元素，并将这些元素添加到指定的集合中。初始尝试编写的`popAll()`方法可能像下面这样：

```
public void popAll(Collection<E> dst) {
     while (!isEmpty()) {
        st.add(pop());
    }
}
```

如果目标集合的元素类型与堆栈的元素类型完全相同，这段代码编译时还是会正确无误，运行得很好。现在假设我们有一个`SimpleStackPECS<Number>`和类型为`Object`的变量，如果从堆栈中弹出每一个元素，并将它保存到该变量中：

```
SimpleStackPECS<Number> simpleStack = new SimpleStackPECS<Number>();
Collection<Object> objects = ...;
simpleStack.popAll(objects);
```
我们将会得到一个非常类似于第一次调用`pushAll()`时所得到的错误：

![cs](https://github.com/guobinhit/java-skills/blob/master/images/effective-programming/generic-paradigm2/simple-stack-pop-all.png)

这一次，通配符类型同样提供了一种解决办法。`popAll()`方法的输入参数类型不应该为“`E`的集合”，而应该为“`E`的某个超类型的集合”，有一种通配符类型正符合此意：`Collection<? supper E>`。接下来，我们修改一下`popAll()`来使用这个类型：

```
public void popAll(Collection<? super E> dst) {
     while (!isEmpty()) {
        dst.add(pop());
    }
}
```
做了这个变动之后，`SimpleStackPECS`和客户端的代码都可以正确无误地编译了。结论很明显，**为了获得最大限度的灵活性，要在表示生产者或消费者的输入参数上使用通配符类型**。如果某个输入参数既是生产者，又是消费者，那么通配符类型对你就没有什么好处了：因为你需要的是严格的类型匹配，这是不用任何通配符而得到的。下面的助记符便于我们记住要使用哪种通配符类型：

- `PECS`，表示`producer-extend`，`consumer-super`。

换句话说，如果参数化类型表示一个`T`生产者，就使用`<? extend T>`；如果参数化类型表示一个`T`消费者，就使用`<? super T>`。而且，`Comparable`始终是消费者，因此使用时始终应该是`Comparable<? super T>`优先于`Comparable<T>`；对于`Comparator`也是一样，因此使用时始终应该是`Comparator<? super T>`优先于`Comparator<T>`。一般来说，如果参数类型只在方法声明中出现一次，就可以用通配符取代它。


----------

———— ☆☆☆ —— 返回 -> [The Skills of Java](https://github.com/guobinhit/java-skills/blob/master/README.md) <- 目录 —— ☆☆☆ ————
