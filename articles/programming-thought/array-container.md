# 编程思想 之「数组、容器」

我们说，“Java 是面向对象的编程语言”，Java 中的所有行为都是围绕对象进行的，那么 Java 是如何持有对象的呢？实际上，在 Java 中，持有对象的方法只有两种，分别为：

- 数组；
- 容器。

接下来，我们就来看看 Java 是如何使用数组和容器的。

## 数组

数组与其他种类的容器之间的区别有三方面：效率、类型和保持基本对象的能力。**在 Java 中，数组是一种效率最高的存储和随机访问对象引用序列的方式**。在 Java SE5 之前，容器不能保持基本类型，但是随着自动包装机制的出现，容器也能非常方便的存储基本数据类型了，因此数组硕果仅存的优点就是效率。

无论使用哪种类型的数组，数组标识符其实就是一个引用，指向在堆中创建的一个真实对象，这个（数组）对象用以保存指向其他对象的引用。`[]`语法是访问数组对象的唯一方法。对象数组和基本类型数组在使用上几乎是相同的，唯一的区别就是：**对象数组保存的是引用，基本类型数组直接保存基本类型的值**。

```
public class ArrayOptions {
    public static void main(String[] args) {
        // 对象数组，三种初始化方式
        String[] strs = new String[10];
        String[] strs2 = new String[]{"Z", "O", "R", "A"};
        String[] strs3 = {"G", "A", "V", "I", "N"};

        System.out.println("strs: " + Arrays.toString(strs));
        System.out.println("strs2: " + Arrays.toString(strs2));
        System.out.println("strs3: " + Arrays.toString(strs3));

        strs = strs2;
        System.out.println("strs = strs2: " + Arrays.toString(strs));

        strs[0] = "K";
        System.out.println("strs & strs[0] = \"K\": " + Arrays.toString(strs));
        System.out.println("strs2: " + Arrays.toString(strs2));

        System.out.println("-------------------");

        // 基本类型数组，三种初始化方式
        int[] ints = new int[10];
        int[] ints2 = new int[]{1, 1, 2, 0};
        int[] ints3 = {2, 0, 1, 5};

        System.out.println("ints: " + Arrays.toString(ints) + " ints.length() = " + ints.length);
        System.out.println("ints2: " + Arrays.toString(ints2) + " ints2.length() = " + ints2.length);
        System.out.println("ints3: " + Arrays.toString(ints3) + " ints3.length() = " + ints3.length);

        for (int i = 0; i < ints2.length; i++) {
            ints[i] = ints2[i];
        }

        // length() 表示数组的长度，而不是数组中实际保持元素的个数
        System.out.println("ints: " + Arrays.toString(ints) + " ints.length() = " + ints.length);
    }
}
```

![array-options](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/array-container/array-options.png)

如上述代码及运行结果所示，我们用了三种方法对数组进行初始化，在数组初始化的过程中，我们可以直接将数组将要持有的对象或基本类型塞进数组，也可以仅声明数组的长度。如果仅声明数组的长度，则编译器会根据数组的类型进行默认初始化，一般来说，对于对象数组，数组中的所有元素将会默认初始化为`null`；对于基本类型数组，数组中的所有元素将会根据基本类型进行默认初始化，如`int`数组默认初始化为`0`，`float`数组默认初始化为`0.0`等。我们还使用了`Arrays.toString()`方法，用于打印一维数组中所有的元素，如果是多维数组，则可以使用`Arrays.deepToString()`方法；`length()`方法用于获取数组的长度，而非数组实际保存元素的个数。此外，在对数组引用进行赋值的时候，引起了同名现象，解决的方法也很简单，即对数组中的元素赋值，而不是数组引用进行赋值。通常情况下，数组与泛型不能很好地结合，我们不能实例化具有参数化类型的数组，例如：

```
Animal<Cat>[] animals = new Animal<Cat>[10];	// 非法初始化
```

擦除会移除参数类型信息，而数组必须知道它们所持有的确切类型，以强制保证类型安全。但是，我们可以参数化数组本身的类型：

```
public class ParameterizedArrayType {
    public static void main(String[] args) {
        Integer[] ints = {1, 1, 2, 0};
        Double[] doubles = {1.1, 1.1, 2.2, 0.0};
        Integer[] ints2 = new ClassParameter<Integer>().f(ints);
        Double[] doubles2 = new ClassParameter<Double>().f(doubles);
    }
}

class ClassParameter<T> {
    public T[] f(T[] arg) {
        return arg;
    }
}
```

在`java.util`类库中可以找到`Arrays`类，它有一套用于数组的`static`实用方法，如`equals()`用于比较两个数组是否相等（数组相等是基于内容的，`deepEquals()`用于比较多维数组）、`sort()`用于对数组进行排序、`hashCode()`用于产生数组的散列码等。此外，`Arrays.asList()`接受任意的序列或数组作为其参数，并将其转为`List`容器，但是在这种情况下，其底层表示的仍是数组，因此不能调整尺寸。

Java 有两种方式来提供比较功能，一种是实现`java.lang.Comparable`接口，覆盖`compareTO()`方法，使我们的类具有“天生”的比较能力；另一种是实现`java.util.Comparator`接口，并覆盖`compare()`方法和`equals()`方法，但是一般情况下，我们不需要覆盖`equals()`方法，除非有特殊的性能需要。在这里，有一点需要我们特别注意，那就是：**基本类型数组无法使用`Comparator`进行排序**。此外，以上两个接口的具体实现及使用方法，已经在 GitHub 上的项目「[java-skills](https://github.com/guobinhit/java-skills)」中给出示例，有需要的同学，可以自行下载源码。


## 容器

与数组相比，容器类都可以自动调整自己的尺寸，且能够与泛型很好的结合使用。通过使用泛型，就可以在编译期防止将错误类型的对象放置到容器中，而且当我们指定了某个类型作为泛型参数时，并不仅限于只能将该确切类型的对象放置到容器中，向上转型也可以像作用于其他类型一样作用于泛型。

```
public class GenericAndUpcasting {
    public static void main(String[] args) {
        List<Animal> animals = new ArrayList<Animal>();
        
        animals.add(new Cat());
        animals.add(new Dog());
        animals.add(new Tiger());

        for (Animal animal : animals) {
            System.out.println(animal);
        }
    }
}

class Animal {}

class Cat extends Animal {}

class Dog extends Animal {}

class Tiger extends Animal {}
```

![generic-and-upcasting](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/array-container/generic-and-upcasting.png)

如上述代码及运行结果所示，我们可以将`Animal`的子类型放置到保存`Animal`类型的容器中。程序的输出是从`Object`默认的`toString()`方法产生的，该方法将打印类名，后面紧跟着该对象的散列码的无符号十六进制表示。Java 容器类类库的用途是“保存对象”，并将其划分为两个不同的概念：

- `Collection`，一个独立元素的序列，包括`List`、`Set`、`Queue`等；
- `Map`，一组成对的“键值对”对象，包括`HashMap`、`TreeMap`等。

在创建容器的时候，我们习惯于使用接口的形式，例如：

```
List<String> list = new ArrayList<String>();
```

这样做的目的在于如果我们决定修改接口的实现，我们只需要在创建处修改它。因此，我们应该创建一个具体类的对象，将其转型为对应的接口，然后在其余的代码中都使用这个接口。在使用容器的时候，我们经常搭配“迭代器”进行使用，那么迭代器是什么呢？迭代器是一个对象，它的工作是遍历并选择序列中的对象，而客户端程序员不必知道或关心该序列底层的结构。此外，迭代器通常被称为“轻量级对象”，因为创建它的代价很小。我们可以通过迭代器执行如下操作：

- 使用`iterator()`方法让容器返回一个`Iterator`对象，其将准备好返回序列的第一个元素；
- 使用`next()`方法获得序列中的下个元素，首次迭代获取序列的第一个元素；
- 使用`hasNext()`方法检查序列中是否还有元素，返回`boolean`值；
- 使用`remove()`方法将迭代器新返回的元素删除。

```
public class SimpleIteration {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();

        list.add("Zora");
        list.add("I");
        list.add("love");
        list.add("you");

        // 获取容器的迭代器对象
        Iterator it = list.iterator();

        // 判断序列是否还有元素
        while (it.hasNext()) {
            // 获取下一个元素，首次调用获取第一个元素
            String str = (String) it.next();
            System.out.println(str);
        }
    }
}
```

如上述代码所示，演示了迭代器的常见操作。此外，`Iterator`还可以移除由`next()`产生的最后一个元素，这意味着在调用`remove()`之前必须先调用`next()`方法。`ListIterator`是一个更加强大的`Iterator`的子类型，虽然它只能用于各种`List`类的访问，但是它可以双向移动，而`Iterator`只能进行单向移动。此外，`foreach`语句也可以用于循环数组或其他任何的`Iterable`，但是这并不意味着数组肯定也是一个`Iterable`，而且任何自动包装也不会自动发生。

```
public class TestContainer {
    public static void main(String[] args) {
        System.out.println(fill(new ArrayList<String>()));
        System.out.println(fill(new LinkedList<String>()));
        System.out.println(fill(new HashSet<String>()));
        System.out.println(fill(new TreeSet<String>()));
        System.out.println(fill(new LinkedHashSet<String>()));
        System.out.println(fill(new HashMap<String, String>()));
        System.out.println(fill(new TreeMap<String, String>()));
        System.out.println(fill(new LinkedHashMap<String, String>()));
    }

    /**
     * 填充Collection
     *
     * @param collection
     * @return Collection
     */
    private static Collection fill(Collection<String> collection) {
        collection.add("Baidu");
        collection.add("Alibaba");
        collection.add("Tencent");
        collection.add("Twitter");
        collection.add("Google");
        collection.add("Facebook");
        collection.add("Baidu");
        return collection;
    }

    /**
     * 填充Map
     *
     * @param map
     * @return Map
     */
    private static Map fill(Map<String, String> map) {
        map.put("Robin Li", "Baidu");
        map.put("Jack Ma", "Alibaba");
        map.put("Pony", "Tencent");
        map.put("Evan Williams", "Twitter");
        map.put("Larry Page", "Google");
        map.put("Mark Zuckerberg", "Facebook");
        map.put("Robin Li", "Baidu");
        return map;
    }
}
```

![test-container](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/array-container/test-container.png)

通过观察上述代码及运行结果，我们可以得到容器的一些特性，如：

- `Collection`类型的容器，每个“槽”位只能保存一个元素，这类容器包括：`List`，其以特定的顺序保存元素；`Set`，其保存的元素不能重复；`Queue`其允许容器的一“端”插入对象，并从另一“端移除对象。
- `Map`类型的容器，每个”槽“位保存了两个对象，即“键”和与之关联的“值”。
- `ArrayList`和`LinkedList`都是`List`的子类型，它们都按元素插入的顺序保存对象，两者的区别在于底层的实现以及执行某些类型的操作时的性能，其中`ArrayList`是用数组实现的，因此在执行查询操作时性能优于`LinkedList`；而`LinkedList`是用链表实现的，因此在执行增删操作时性能优于`ArrayList`。
- `HashSet`、`TreeSet`、`LinkedHashSet`都是`Set`的子类型，对于相同的对象，其只保存一次，其中`HashSet`使用相当复杂的方式来存储对象，以保障查询时的速度，因此其拥有最快的查询速度；`TreeSet`，按照比较结果的升序保存对象；`LinkedHashSet`，按照添加元素的顺序保存对象，但因为它在保障查询速度的同时还维护了插入元素的顺序，因此其查询速度略慢于`HashSet`。
- `HashMap`、`TreeMap`、`LinkedHashMap`都是`Map`的子类型，对于每个键，`Map`只接受存储一次，与`Set`一样，其中`HashMap`也提供了最快的查询速度，也没有按照任何明显的顺序来保持其元素；`TreeMap`，按照比较结果的升序保存键；而`LinkedHashMap`，则按照插入的顺序保存键，但查询速度略慢于`HashMap`。

栈通常是指后进先出（`LIFO`）的容器，有时栈也被称为叠加栈，因为最后“压入”栈的元素，第一个“弹出”栈。因为`LinkedList`具有能够直接实现栈的功能的所有方法，因此，我们可以直接将其作为栈来使用：

```
public class Stack<T> {
    private LinkedList<T> storage = new LinkedList<T>();

    // 压栈方法，用于存入元素
    public void push(T element) {
        storage.addFirst(element);
    }

    // 获取栈中第一个存入的元素
    public T peek() {
        return storage.getFirst();
    }

    // 弹栈方法，移除最后一个入栈元素
    public T pop() {
        return storage.removeFirst();
    }

    // 判断栈是否为空
    public boolean isEmpty() {
        return storage.isEmpty();
    }

    // 打印栈
    public String toString() {
        return storage.toString();
    }

    public static void main(String[] args) {
        Stack<String> stack = new Stack<String>();
        
        // 循环入栈
        for (String str : "Hi girl what is your name".split(" ")) {
            stack.push(str);
        }

        // 循环出栈
        while (!stack.isEmpty()) {
            System.out.println(stack.pop() + " ");
        }
    }
}
```

队列是一个典型的先进先出（`FIFO`）的容器，即从容器的一端放入元素，从另一端取出元素，并且元素放入容器的顺序和取出的顺序是相同。队列常被当作一种可靠的将对象从程序的某区域传输到另一个区域的途径。`LinkedList`提供了支持队列行为的方法，并且它实现了`Queue`接口，因此`LinkedList`可以用作`Queue`的一种实现：

```
public class QueueDemo {
    public static void main(String[] args) {
        Queue<Integer> integerQueue = new LinkedList<Integer>();

        Random random = new Random(47);

        for (int i = 0; i < 5; i++) {
            // offer() 将元素插入对尾
            integerQueue.offer(random.nextInt(i + 10));
        }

        printQueue(integerQueue);

        Queue<String> stringQueue = new LinkedList<String>();
        
        for (String str : "May the force with you".split(" ")) {
            // offer() 将元素插入对尾
            stringQueue.offer(str);
        }
        
        printQueue(stringQueue);
    }

    private static void printQueue(Queue queue) {
        // peek() 在移除元素的情况下，返回对头元素，如果对头元素不为 null，则说明队列不为空
        while (queue.peek() != null) {
            // remove() 移除并返回对头元素
            System.out.println(queue.remove() + " ");
        }
        System.out.println();
    }
}
```
如上述代码所示，`offer()`将元素插入对尾；`peek()`在移除元素的情况下，返回对头元素，如果对头元素不为`null`，则说明队列不为空；`remove()`移除并返回对头元素。此外，`PriorityQueue`是`Queue`的一个子类，它根据队列规则（即在给定一组队列中的元素的情况下，确定下一个弹出队列的元素的规则），声明下一个弹出的元素是最需要的元素，即具有最高的优先级。


----------

———— ☆☆☆ —— 返回 -> [The Skills of Java](https://github.com/guobinhit/java-skills/blob/master/README.md) <- 目录 —— ☆☆☆ ————
