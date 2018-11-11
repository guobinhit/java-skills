# 编程思想 之「容器深入研究」

在「[数组、容器](https://github.com/guobinhit/java-skills/blob/master/articles/programming-thought/array-container.md)」这篇博文中，我们已经介绍了 Java 容器类库的相关概念及基本特性，这对于使用容器来说已经够用了。在本篇博文中，我们则是要对容器进行更深层次的研究！首先，给出容器类库的比较完备的构件图：

![full-container-taxonomy](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/deep-container/full-container-taxonomy.png)

如上图所示，我们可以将 Java 容器划分为两类，分别为`Collection`和`Map`，而且所有的容器类都是从这两种类型的子类型或者实现。其中，小虚线框如`Collection`和`Map`表示接口、大虚线框如`AbstractCollection`和`AbstractMap`表示抽象类、实线框如`HashSet`和`HashMap`表示具体的实现类。在上述的容器构件图中，抽象类都实现了接口，其作用只是作为“实现了部分特定接口的工具”，例如我们要实现自己的`Map`，那么我们并不需要直接实现`Map`接口，只需要继承`AbstractMap`并实现新类型所必需的操作即可。当然，Java 的容器类库已经提供足够多的功能了，我们几乎不需要考虑创建自己容器类型的事情！

```
public class FullContainer {
    public static void main(String[] args) {
        // 使用 Collections 的 fill() 方法填充 List 对象
        List<String> stringList = new ArrayList<String>(Collections.nCopies(10, "Hello"));
        Collections.fill(stringList, "World");
        System.out.println(stringList);
        
        // 将一个容器对象当做参数传给另一个容器对象的构造器
        List<String> fullList = new ArrayList<String>(stringList);
        System.out.println(fullList);
    }
}
```
如上述代码所示，我们可以用`Collections.fill()`方法来填充容器，但该方法仅能作用于`List`，且填充的方式为复制同一个对象引用来填充整个容器，显然这样填充容器的效果并不好。不过，**所有的`Collection`子类型都有一个可以接受另一个`Collection`对象的构造器，并用所接收到`Collection`对象中的元素来填充新的容器**，这却为我们提供了一个填充容器的理想方法。此外，执行各种不同的添加和移除的方法在`Collection`接口中都是可选操作，这意味着：实现类并不需要为这些方法提供功能定义。

在众多的容器类中，`Set`和`Map`比较特殊。因为`Set`不保存重复元素，所以存入`Set`的每个元素都必须是唯一的，而保障集合中元素唯一性的方法就是：限制存入`Set`中的每个元素都必须定义`equals()`方法，如果使用的是`HashSet`，那么存入`Set`中的元素还必须定义`hashCode()`方法；如果使用的是`TreeSet`，那么存入`Set`中的元素必须实现`Comparable`接口。此外，如果使用的是`SortedSet`，则可以通过`comparator()`方法返回当前`Set`使用的`Comparator`或者`null`，其中`null`表示以自然方式排序。注意，`SortedSet`的意思是“按对象的比较函数对元素排序”，而不是值“元素插入的次序”，插入顺序可以用`LinkedHashSet`来保存。与`Set`类似，`Map`要求其键元素不能重复，因此其性质与`Set`相同，在此就不赘述啦！但有一点需要注意，那就是：虽然`Map`的键元素不能重复，但其键元素对应的值却是可以重复的。

对于 Java 的容器类，我们已经知道了`HashSet`和`HashMap`具有非常快的查询速度，也知道其使用了散列机制，但到现在为止，我们都没有介绍其散列机制是如何实现的。现在，以`Map`为例，在实现我们自己的`HashMap`的过程中，来了解散列机制。

- 使用散列的目的在于：想要使用一个对象来查找另一个对象；
- 散列的价值在于速度：散列使得查询得以快速进行。

由于存储一组元素最快的数据结构是数组，因此散列使用数组来表示键的信息。但数组在初始化容量之后，就不能进行扩容了，而我们希望在`Map`中保存数量不确定的值，这该如何是好？答案就是：**数组并不保存键本身**，而是通过键对象生成一个数字，将其作为数组的下标。这个数字就是散列码，它可以通过`hashCode()`方法生成。为解决数组容量的问题，不同的键可以生产相同的下标。也就是说，可能会有冲突。因此，数组多大就不重要了，任何键总能在数组中找到它的位置。

于是查询一个值的过程首先就是计算散列码，然后使用散列码查询数组。如果能够保证没有冲突（如果值的数量是固定的，那么就有可能没有冲突），那就有了一个完美的散列函数，但是这种情况只是特例，如`EnumMap`和`EnumSet`拥有完美的散列函数，但这是因为`enum`定义了固定数量的实例。通常，冲突由外部链接处理：**数组并不直接保存值，而是保存值的`list`，然后对`list`中的值使用`equals()`方法进行线性查询**。这部分的查询自然会比较慢，但是，如果散列函数好的话，数组的每个位置就只有较少的值。因此，不是查询整个`list`，而是快速地跳到数组的某个位置，只对很少的元素进行比较，这就是`HashMap`会如此之快的原因啦！

```
public class SimpleHashMap<K, V> extends AbstractMap<K, V> {
    /**
     * choose a prime number for the hash table size
     * to achieve a uniform distribution
     */
    static final int SIZE = 997;

    @SuppressWarnings("unchecked")
    private LinkedList<MapEntry<K, V>>[] buckets = new LinkedList[SIZE];

    public V put(K key, V value) {
        V oldValue = null;
        // get key index, "Math.abs(key.hashCode()) % SIZE" can be called hash function
        int index = Math.abs(key.hashCode()) % SIZE;
        if (buckets[index] == null) {
            // buckets's element is a list
            buckets[index] = new LinkedList<MapEntry<K, V>>();
        }
        LinkedList<MapEntry<K, V>> bucket = buckets[index];
        MapEntry<K, V> pair = new MapEntry<K, V>(key, value);
        // initial a boolean sign
        boolean found = false;
        // get bucket's iterator, bucket is a list
        ListIterator<MapEntry<K, V>> it = bucket.listIterator();
        while (it.hasNext()) {
            MapEntry<K, V> iPair = it.next();
            if (iPair.getKey().equals(key)) {
                oldValue = iPair.getValue();
                // replace old with new
                it.set(pair);
                found = true;
                break;
            }
        }
        /**
         * if found is true, it's mean this.key already include by one of element of buckets,
         * and old value is already replace whit new value, so !found is false, pass;
         *
         * otherwise, it's mean this.key a new element of buckets, so execute sentence below
         */
        if (!found) {
            buckets[index].add(pair);
        }
        return oldValue;
    }

    public V get(Object key) {
        int index = Math.abs(key.hashCode()) % SIZE;
        if (buckets[index] == null) {
            return null;
        }
        /**
         * if procedure execute this sentence, it's mean this.key has a corresponding index,
         * so, firstly, get list corresponding of index
         */
        for (MapEntry<K, V> iPair : buckets[index]) {
            // secondly, check key and get value
            if (iPair.getKey().equals(key)) {
                return iPair.getValue();
            }
        }
        return null;
    }


    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new HashSet<Map.Entry<K, V>>();
        for (LinkedList<MapEntry<K, V>> bucket : buckets) {
            if (bucket == null) {
                continue;
            }
            for (MapEntry<K, V> mpair : bucket) {
                set.add(mpair);
            }
        }
        return set;
    }

    public static void main(String[] args) {
        SimpleHashMap<String, String> simpleHashMap = new SimpleHashMap<String, String>();
        simpleHashMap.put("Beijing", "Beijing");
        simpleHashMap.put("Heilongjiang", "Harbin");
        simpleHashMap.put("Hebei", "Shijiazhuang");
        System.out.println(simpleHashMap);
        System.out.println(simpleHashMap.get("Beijing"));
        System.out.println(simpleHashMap.get(""));
        System.out.println(simpleHashMap.entrySet());
        System.out.println(simpleHashMap.put("Hebei", "Hengshui"));
        System.out.println(simpleHashMap);
    }
}
```
![simple-hash-map](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/deep-container/simple-hash-map.png)

如上述代码及运行结果图所示，我们实现了自己的`HashMap`并且其已经能够正确工作啦！由于散列表中的“槽位”通常称为桶位，因此我们将表示实际散列表的数组命名为`bucket`，而且为了让散列均匀分布，桶的数量通常使用质数。注意，为了能够自动处理冲突，使用了一个`LinkedList`的数组，每一个新的元素只是直接添加到`list`末尾的某个特定桶位中。即使 Java 不允许创建泛型数组，我们也可以创建指向这个数组的引用。在这里，向上转型为这种数组是很方便的，而且还可以防止在后面的代码中进行额外的转型。

此外，在上面的代码中，我们使用了自己定义的`MapEntry`，其继承自`Map.Entry`，具体的代码已经在 GitHub 上面的「[java-skills](https://github.com/guobinhit/java-skills)」项目中给出。呃，还有就是：为了更好的使用散列，编写我们自己的`hashCode()`方法是有必要的，而覆写`hashCode()`方法时最重要的因素就是“无论何时，对同一个对象调用`hashCode()`方法都应该生成相同的值”，好的`hashCode()`方法应该产生分布均匀的散列码。对于覆写`hashCode()`方法的技巧，将在「效率编程」中详细介绍！

对于`Map`容器，还有一些知识点，值得我们注意：

- 容量：表中的桶位数；
- 初始容量：表在创建时所拥有的桶位数；
- 尺寸：表中当前存储的项数；
- 负载因子：尺寸与容量之比，空表的负载因子是`0`，而半满表的负载因子是`0.5`，依次类推。

对于初始容量，`HashMap`和`HashSet`都具有允许我们自己指定初始容量的构造器；对于负载因子，`HashMap`和`HashSet`都具有允许我们自己指定负载因子的构造器，表示当负载情况达到该负载因子的水平时，容器将自动进行扩容，实现方式是使容量大致加倍，并重新将现有对象分布到新的桶位集中，称之为再散列；`HashMap`使用的默认负载因子是`0.75`，这意味着只有当表达到四分之三满时，才会进行再散列。

有的时候，我们也对容器有一些特殊的需求，如希望容器不能被修改、想要同步容器等等，这在容器的工具类`Collections`中已经给出了若干静态方法以支持的我们的特殊需求，如：

```
// 设定 Collection 或者 Map 为不可修改
Collections.unmodifiableList();
Collections.unmodifiableCollection();
Collections.unmodifiableMap();
Collections.unmodifiableSortedMap();
Collections.unmodifiableSet();
Collections.unmodifiableSortedSet();

// Collection 或者 Map 的同步控制
Collections.synchronizedList();
Collections.synchronizedCollection();
Collections.synchronizedMap();
Collections.synchronizedSortedMap();
Collections.synchronizedSet();
Collections.synchronizedSortedSet();
```

对于特定类型的“不可修改的”方法的调用并不会产生编译时的检查，但是转换完成后，任何会改变容器内容的操作都会引起`UnsupportedOperationException`异常。此外，“不可修改的”方法允许我们保留一份可修改的容器，作为类的`private`成员，然后通过某个方法调用返回对该容器的“只读”作用。

Java 容器有一种保护机制，能够防止多个进程同时修改同一个容器的内容。如果我们在迭代遍历某个容器的过程中，另一个线程介入其中，并且插入、删除或修改此容器内的某个对象，那么就会出现问题。Java 容器类类库采用“快速报错”的机制，它会探查容器上的任何除了我们的进程所进行的操作以外的所有变化，一旦它发现其他进程修改了容器，就会立刻抛出`ConcurrentModificationException`异常，这就是“快速报错”的意思，即不是使用复杂的算法在事后来检查问题。

`java.lang.ref`类库包含了一组类，这些类为垃圾回收提供了更多的灵活性。当存在可能会耗尽内存的大对象的时候，这些类显得特别有用。有三个继承自抽象类`Reference`的类：`SoftReference`、`WeakReference`和`PhantomReference`。当垃圾回收器正在考察的对象只能通过某个`Reference`对象才“可获得（指此对象可在程序中的某处找到）”时，上述这些不同的派生类为垃圾回收器提供了不同级别的间接性指示。

如果想要继续持有对某个对象的引用，希望以后还能够访问到该对象，但是也希望能够允许垃圾回收器释放它，这时就应该使用`Reference`对象。`SoftReference`、`WeakReference`和`PhantomReference`有强到弱排列，对应不同级别的“可获得性”。容器类中有一个特殊的`Map`，即`WeakHashMap`，它被用来保存`WeakReference`，`WeakHashMap`允许垃圾回收器自动清理键和值。


----------

———— ☆☆☆ —— 返回 -> [The Skills of Java](https://github.com/guobinhit/java-skills/blob/master/README.md) <- 目录 —— ☆☆☆ ————
