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
public class Elvis2 {
    private static final Elvis2 INSTANCE = new Elvis2();

    /**
     * 私有化构造器
     */
    private Elvis2() {
    }

    /**
     * 公有静态工厂
     */
    public static Elvis2 getInstance() {
        return INSTANCE;
    }
}
```










----------

———— ☆☆☆ —— 返回 -> [那些年，关于 Java 的那些事儿](https://github.com/guobinhit/java-skills/blob/master/README.md) <- 目录 —— ☆☆☆ ————
