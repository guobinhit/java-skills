# 效率编程 之「创建和销毁对象」

### 第 1 条：考虑用静态工厂方法代替构造器

类可以提供一个公有的静态工厂方法，它只是一个返回类的示例的静态方法。例如，

```
public static Boolean valueOf(boolean b) {
    return b ? Boolean.TRUE : Boolean.FALSE;
}
```

- **静态工厂方法与构造器不同的第一大优势在于，它们有名称**。特别地，在构造器的参数不能够清晰地描述正被返回的对象时，那么具适当名称的静态工厂更容易使用。
- **静态工厂方法与构造器不同的第二大优势在于，不必在每次调用它们的时候都创建一个新对象**。静态工厂方法能够为重复的调用返回相同的对象，这样有助于类总能严格控制在某个时刻哪些实例应该存在。
- **静态工厂方法与构造器不同的第三大优势在于，它们可以返回原返回类型的任何子类型的对象**。例如在 JDK 1.5 中引入的类`java.util.EnumSet`就没有公有构造器，仅有静态工厂方法，它们返回两种实现类之一，具体则取决于底层枚举类型的大小：如果它的元素有 64 个或者更少，就像大多数枚举类型一样，静态工厂方法就返回一个`RegalarEnumSet`实例，用单个`long`进行支持；如果枚举类型有 65 个或者更多的元素，静态工厂就返回`JumboEnumSet`实例，用`long`数组进行支持。
- **静态工厂方法与构造器不同的第四大优势在于，在创建参数化类型实例的时候，它们使代码变得更加简洁**。例如在调用参数化类的构造器时，即使类型参数很明显，也必须指出，这通常要求我们连续两次提供类型参数：

```
Map<String, List<String>> map = new HashMao<String, List<String>>();
```
如果有以下的静态工厂方法，

```
public static <K, V> HashMap<K, V> newHashMap() {
    return new HashMap<K, V>();
}
```
则上述繁琐的声明，将简化为：

```
Map<String, List<String>> map = Maps.newHashMap();
```
但是很遗憾，在标准的 JDK 中，并没有提供类似的静态工厂方法。不过，我们可以通过在项目中引入谷歌发布的`Guava`，使用类似上述的静态工厂方法。当然，静态工厂方法也不是尽善尽美的，也有其缺点：

- **静态工厂方法的主要缺点在于，类如果不含有公有的或者受保护的构造器，就不能被子类化**；
- **静态工厂方法的第二个缺点在于，它们与其他的静态方法实际上没有任何区别**。

简而言之，静态工厂方法和公有的构造器都各有用处，我们需要理解它们各自的长处。静态工厂方法通常更加合适，因此切记第一反应就是提供公有的构造器，而不是优先考虑静态工厂方法。

### 第 2 条：遇到多个构造器参数时要考虑用构建器

静态工厂和构造器有一个共同的局限性，那就是：它们都不能很好地扩展到大量的可选参数。当遇到需要使用多个参数初始化对象的时候，程序员一向习惯采用重叠构造器模式和`JavaBeans`模式，具体表现为：

```
/**
 * 重叠构造器模式
 */
public class NutritionFacts {
    // 必填项
    private final int servingSize;
    // 必填项
    private final int servings;
    // 选填项
    private final int calories;
    // 选填项
    private final int fat;
    // 选填项
    private final int sodium;
    // 选填项
    private final int carbohydrate;

    public NutritionFacts(int servingSize, int servings) {
        this(servingSize, servings, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories) {
        this(servingSize, servings, calories, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories, int fat) {
        this(servingSize, servings, calories, fat, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium) {
        this(servingSize, servings, calories, fat, sodium, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium, int carbohydrate) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;
        this.fat = fat;
        this.sodium = sodium;
        this.carbohydrate = carbohydrate;
    }
}
```
如上述代码所示，为**重叠构造器模式**。在这种模式下，我们提供第一个只有必要参数的构造器，第二个构造器有一个可选参数，第三个有两个可选参数，以此类推，最后一个构造器包含所有可选参数。当我们想要创建实例的时候，就利用参数列表最短的构造器，但列表中的参数必须与实际参数的顺序一一对应，如果参数列表中间出现某个参数不想赋值的情况时还必须用默认值占位，以防止错误初始化。当参数个数较少（一般少于 6 个参数）的时候，它看起来还不算糟糕，问题是随着参数个数的增加，它很快就会失去控制，一句话：重叠构造器模式可行，但是当有多个参数的时候，客户端代码会很难编写，并且难以阅读。

```
/**
 * JavaBeans模式
 */
public class NutritionFacts2 {
    // 必填项
    private int servingSize = -1;
    // 必填项
    private int servings = -1;
    // 选填项
    private int calories = 0;
    // 选填项
    private int fat = 0;
    // 选填项
    private int sodium = 0;
    // 选填项
    private int carbohydrate = 0;

    public NutritionFacts2() {
    }

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }
}
```

如上述代码所示，为**`JavaBeans`模式**。在这种模式下，有一个很严重的缺点，那就是：构造过程被分到了几个调用中，有可能导致`JavaBean`在构造过程中处于不一致的状态。类无法通过校验构造器参数的有效性来保证一致性，试图使用处于不一致状态的对象，将会导致失败。与此有关的另一点不足在于，`JavaBean`模式阻止了把类做成不可变的可能，这就需要程序员付出额外的努力来确保它的线程安全。

幸运的是，还有第三种替代方法，既能保证像重叠构造器模式那样的安全性，也能保证像`JavaBeans`模式那么好的可读性，这就是`Builder`模式，其示例如下：

```
/**
 * Builder模式
 */
public class NutritionFacts3 {
    // 必填项
    private final int servingSize;
    // 必填项
    private final int servings;
    // 选填项
    private final int calories;
    // 选填项
    private final int fat;
    // 选填项
    private final int sodium;
    // 选填项
    private final int carbohydrate;

    public static class Builder {
        // 必填项
        private final int servingSize;
        // 必填项
        private final int servings;
        // 选填项，初始化为默认值
        private int calories = 0;
        private int fat = 0;
        private int sodium = 0;
        private int carbohydrate = 0;

        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }

        public Builder calories(int calories) {
            this.calories = calories;
            return this;
        }

        public Builder fat(int fat) {
            this.fat = fat;
            return this;
        }

        public Builder sodium(int sodium) {
            this.sodium = sodium;
            return this;
        }

        public Builder carbohydrate(int carbohydrate) {
            this.carbohydrate = carbohydrate;
            return this;
        }
        
        public NutritionFacts3 build() {
            return new NutritionFacts3(this);
        }
    }

    /**
     * 私有化构造器，通过类的静态内部类来构造对象
     *
     * @param builder
     */
    private NutritionFacts3(Builder builder) {
        servingSize = builder.servingSize;
        servings = builder.servings;
        calories = builder.calories;
        fat = builder.fat;
        sodium = builder.sodium;
        carbohydrate = builder.carbohydrate;
    }
}
```
如上述代码所示，为**`Builder`模式**。在这种模式下，不直接生成想要的对象，而是让客户端利用所有必要的参数调用构造器（或者静态工厂），得到一个`builder`对象；然后客户端在`builder`对象上调用类似`setter`的方法，来设置每个相关的可选参数；最后，客户端调用无参的`build()`方法来生成不可变的对象。其中，`builder`就是客户端构建的类的静态成员类！如果类的构造器或者静态工厂中具有多个参数，设计这种类时，`Builder`模式就是种不错的选择，特别是当大多数参数都是可选的时候。

### 第 3 条：用私有构造器或者枚举类型强化`Singleton`属性

`Singleton`指仅仅被实例化一次的类，`Singleton`通常被用来代表那些本质上唯一的系统组件，比如窗口管理器或者文件系统。在 Java 1.5 发行版本之前，实现`Singleton`有两种方法，这两种方法都要把构造器保持为私有的，并导出公有的静态成员，以便允许客户端能够访问类的唯一实例。

- **第 1 种方法**：私有化构造器，并设置公有静态`final`域

```
public class Elvis {
    /**
     * 设置公有静态 final 域
     */
    public static final Elvis INSTANCE = new Elvis();

	/**
     * 私有化构造器
     */
    private Elvis() {
    }
}
```
在这种模式下，私有构造器仅被调用一次，用于初始化公有静态`final`域`Elvis.INSTANCE`，由于缺少公有的或者受保护的构造器，所以保证了类的全局唯一性。但是有一点需要我们注意，那就是：享有特权的客户端可以借助`AccessibleObject.setAcciessible()`方法，通过反射机制调用私有构造器。如果需要抵御这种攻击，可以修改构造器，让它被要求创建第二个实例的时候抛出异常。

- **第 2 种方法**：私有化构造器，提供一个公有的静态工厂方法

```
public class Elvis {
    private static final Elvis INSTANCE = new Elvis2();

    /**
     * 私有化构造器
     */
    private Elvis() {
    }

    /**
     * 公有静态工厂
     */
    public static Elvis getInstance() {
        return INSTANCE;
    }
}
```

在这种模式下，所有对静态方法`Elves.getInstance()`的调用，都会返回同一个对象的引用，所以永远不会创建其他的`Elves`实例。此方法的优势在于，它提供了灵活性：在不改变其 API 的前提下，我们可以改变该类是否应该为`Singleton`的想法。当然，对于「第 1 种方法」的提醒依然使用。

无论是「第 1 种方法」还是「第 2 种方法」，想要使`Singleton`类变成可序列化（`Serializable`）的，仅仅在声明中加上`implements Serializable`是不够的。为了维护并保证`Singleton`，必须声明所有实例域都是瞬时（`transient`）的，并提供一个`readResolve()`方法。否则，每次反序列化一个序列化的实例时，都会创建一个新的实例。比如说，在我们的例子中，会导致“假冒的`Elves`”。为了防止这种情况，要在`Elves`类中加入下面这个`readResolve()`方法：

```
/**
 * 防止反序列化是生成假冒的实例
 */
private Object readResolve() {
	return INSTANCE;
}
```

- **第 3 种方法**：编写一个包含单个元素的枚举类型

```
public enum Elvis {
    INSTANCE;
}
```
如上述代码所示，自 Java 1.5 发行版本起，实现`Singleton`有了第 3 种方法，即：只需编写一个包含单个元素的枚举类型。这种方法在功能上与公有域方法相近，但是它更加简洁，无偿地提供了序列化机制，绝对防止多次实例化，即使是面对复杂的序列化或者反射攻击的时候。虽然这种方法还没有广泛采用，但是**单元素枚举类型已经成为实现`Singleton`的最佳方法**。

### 第 4 条：通过私有构造器强化不可实例化的能力

正常情况下，对于工具类来说，它是不希望被实例化的，实例对它没有任何意义。然而，在缺失显式构造器的时候，编译器会自动提供一个公有的、无参的缺省构造器，这意味着：我们仍然可以通过公有的、无参构造器来实例化该工具类。这显然与我们期望的结果相违背，为了实现不可实例化的效果，我们可以显式声明一个私有的、无参构造器，如：

```
public class UtilityClass {
    /**
     * 私有化构造器，并在类内部无意识调用该构造器的时候，抛出错误
     */
    private UtilityClass() {
        throw new AssertionError();
    }
}
```

由于显式的构造器是私有的，所以不可以在该类的外部访问它。`AssertionError()`不是必须的，但是它可以避免不小心在类的内部调用构造器。这种习惯用法也有副作用，它使得类不可以被子类化。所有的构造器都必须显式或隐式地调用超类构造器，在这种情况下，子类就没有可访问的超类构造器可调用了。

### 第 5 条：避免创建不必要的对象以及消除过期的对象引用

一般类说，最好能够重用对象而不是在每次需要的时候就创建一个相同功能的新对象。如果对象是不可变的，它就始终可以被重用。对于同时提供了静态工厂方法和构造器的不可变类，通常是优先使用静态工厂方法而不是构造器，以避免创建不必要的对象。当然，除了重用不可变的对象之外，也可以重用那些已知不会被修改的可变对象。此外，要优先使用基本类型而不是装箱基本类型，要当心无意识的自动装箱。现在，考虑下面这个简单的栈实现的例子：

```
public class SimpleStack {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public SimpleStack() {
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    /**
     * 压栈
     *
     * @param e
     */
    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    /**
     * 弹栈
     *
     * @return
     */
    public Object pop() {
        if (size == 0) {
            throw new EmptyStackException();
        } else {
            return elements[--size];
        }
    }

    /**
     * 保证栈的容量，在必要时，进行自动扩容
     */
    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }
}
```
这段程序中并没有很明显的错误。但实际上，该程序存在着“内存泄漏”的风险，随着垃圾回收器活动的增加，或者由于内存占用的不断增加，程序性能的降低会逐渐表现出来。在极端情况下，这种内存泄漏会导致磁盘交换，甚至导致程序失败。那么，程序中哪里发生了内存泄漏呢？如果一个栈先是增长，然后再收缩，那么，从栈中弹出来的对象将不会被当做垃圾回收，即使使用的程序不再引用这些对象，它们也不会被回收。这是因为，**栈内部维护着对这些对象的过期引用**。

所谓过期引用，是指永远也不会再被解除的引用。而这种由于过期引用导致的内存泄漏，称之为“无意识的对象保持”。即使只有少量的几个对象的引用被无意识地保留下来，也会有许许多多的对象被排除在垃圾回收机制之外，从而对性能造成潜在的重大影响。对于这类问题的修改方法很简单：**一旦对象引用过期，只需清空这些引用即可**。对于上述例子中的`SimpleStack`类而言，只要一个元素被弹出栈，指向它的引用就过期了，因此只需要修改`pop()`方法即可：

```
public Object pop() {
    if (size == 0) {
        throw new EmptyStackException();
    } else {
        Object result = elements[--size];
        elements[size] = null;
        return result;
    }
}
```
通过上述代码，即可在栈弹出元素的时候，清空其引用。清空过期引用还有一个好处，那就是：如果它们以后又被错误的解除引用，程序就会立即抛出`NullPointerException`异常。但实际上，清空对象引用应该是一种例外，而不是一种规范行为。消除过期引用最好的方法是让包含该引用的变量结束其生命周期。在`SimpleStack`中，之所以需要我们手动消除内存泄漏的风险，其原因在于：`SimpleStack`类自己管理内存。一般而言，只有类自己管理内存，程序员就应该警惕内存泄漏问题。除此之外，还有两种情况，有可能导致内存泄漏，分别为：

- **缓存**，一旦我们把对象引用放到缓存中，它就很容易被遗忘掉，从而使得它不在有用之后很长一段时间内仍然留着缓存中；
- **监听器和其他回调**，如果我们实行了一个 API，客户端在这个 API 中注册回调，却没有显式地取消注册，那么除非我们采取某些动作，否则它们就会积聚。

### 第 6 条：避免使用终结方法

终结方法（`finalize`）通常是不可预测的，也是很危险的，一般情况下也是不必要的。终结方法的缺点在于不能保证会被及时地执行，**从一个对象变得不可到达开始，到它的终结方法被执行，所花费的这段时间是任意长的**。这意味着，注重时间的任务不应该由终结方法来完成。虽然及时地执行终结方法正是垃圾回收算法的一个主要功能，但这种算法在不同的 JVM 实现中会大相径庭。如果程序依赖于终结方法被执行的时间点，那么这个程序在不同的 JVM 中运行的表现可能就会截然不同。

Java 语言规范不仅不保证终结方法会被及时地执行，而且根本就不保证它们会被执行。当一个程序终止的时候，某些已经无法访问的对象上的终结方法却根本没有被执行，这是完成有可能的。结论是：**不应该依赖终结方法来更新重要的持久状态**。不要被`System.gc()`或者`System.runFinalization()`这两个方法所诱惑，它们确实增加了终结方法被执行的机会，但是它们并不保证终结方法一定会被执行。

正常情况下，未被捕获的异常将会是线程终止，并打印出栈轨迹，但是，如果异常发生在终结方法之中，则不会如此，甚至连警告都不会打印出来。这意味着，在终结方法执行的过程中，未被捕获的异常会使对象处于破坏的状态，如果另一个线程企图使用这种被破坏的对象，则可能发生任何不确定的行为。而且，**使用终结方法有一个非常严重的性能损失**。

此外，“终结方法链”不会被自动执行。如果类（不是`Object`）有终结方法，并且子类覆盖了终结方法，子类的终结方法就必须手动调用超类的终结方法。我们应该在一个`try`块中终结子类，并在相应的`finally`块中调用超类的终结方法。例如，

```
public class Finalizer {
    @Override
    protected void finalize() throws Throwable {
        try {
            // 终结子类状态
        } finally {
            super.finalize();
        }
    }
}
```
如果子类实现者覆盖了超类的终结方法，但是忘了手动调用超类的终结方法，或者有意选择不调用超类的终结方法，那么超类的终结方法将永远不会被调用到。要防范这样错心大意或者恶意的子类是有可能的，代价就是为每个将被终结的对象创建一个附件的对象。不是把终结方法放到要求终结处理的类中，而是把终结方法放到一个匿名类中，该匿名类的唯一作用就是终结它的外围实例。我们称这个匿名类为“终结方法守护者”，示例如：

```
public class Foo {
    /**
     * 终结方法守护者
     */
    private final Object finalizerGuardian = new Object() {
        @Override
        protected void finalize() throws Throwable {
            // 终结外围类对象
        }
    };
}
```

外围类的每个实例都会创建一个这样的守护者。外围实例在它的私有实例域中保存着一个对其终结方法守护者的唯一引用，因此终结方法守卫者与外围实例可以同时启动终结过程。当守护者被终结的时候，它执行外围实例所期望的终结行为，就好像它的终结方法时外围对象上的一个方法一样。

总之，除非是作为安全网，或者是为了终止非关键的本地资源，否则请不要使用终结方法。如果父类（非`Object`）覆盖了终结方法，就要记住在子类的终结方法中手动调用`super.finalize()`。如果需要把终结方法与公有的非`final`类关联起来，请考虑使用终结方法守护者，以确保即使子类的终结方法未能调用`super.finalize()`，该终结方法也会被执行。





----------

———— ☆☆☆ —— 返回 -> [The Skills of Java](https://github.com/guobinhit/java-skills/blob/master/README.md) <- 目录 —— ☆☆☆ ————
