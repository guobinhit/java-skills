# 效率编程 之「方法」

### 第 1 条：检查参数的有效性

绝对多数方法和构造器对于传递给它们的参数值都会有某些限制。例如，索引值必须是非空的、对象引用不能为`null`等。我们应该在文档中清楚地指明所有这些限制，并且在方法体的开头处检查参数，以强制施加这些限制。对于非公有的方法，我们也可以使用断言来检查它们的参数，例如下面的冒泡排序方法：

```
private static void bubbleSort(int[] array) {
    // 使用断言
    assert array != null;

    // 冒泡排序核心算法
    for (int i = array.length - 1; i > 0; i--) {
        for (int j = 0; j < i; j++) {
            if (array[j] > array[j + 1]) {
                int temp = array[j];
                array[j] = array[j + 1];
                array[j + 1] = temp;
             }
        }
    }
}
```

从本质上将，断言是在声称被断言的条件将会为真，无论外围包的客户端如何使用它。不同于一般的有效性检查，如果断言失败，将会抛出`AssertionError`，如我们将`null`传递给上面的`bubbleSort()`方法，将会得到如下错误信息：

![assertion-error](https://github.com/guobinhit/java-skills/blob/master/images/effective-programming/method/assertion-error.png)

此外，如果要开启断言（默认是不开启断言模式的），需要我们手动配置`VM`启动参数。例如，在 IntelliJ IDEA 中，我们可以通过在`VM options`中设置`-ea`参数来开启断言：

![configurations](https://github.com/guobinhit/java-skills/blob/master/images/effective-programming/method/configurations.png)

简而言之，每当编写方法或者构造器的时候，我们应该考虑它的参数有哪些限制，也应该把这些限制写到文档中，并且在这个方法体的开头出，通过显式的检查来实施这些限制。

### 第 2 条：必要时进行保护性拷贝

要假设类的客户端会尽其所能来破坏这个类的约束条件，因此我们必须保护性地设计程序。例如，考虑下面的类，它声称可以表示一段不可变的时间周期：

```
public final class Period {
    private final Date start;
    private final Date end;

    public Period(Date start, Date end) {
        if (start.compareTo(end) > 0)
            throw new IllegalArgumentException(start + "after " + end);
        this.start = start;
        this.end = end;
    }

    public Date start() {
        return start;
    }

    public Date end() {
        return end;
    }
}
```

乍一看，这个类似乎是不可变的，并且加强了约束条件：周期的起始时间不能在结束时间之后。然而，因为`Date`类本身是可变的，因此很容易违反这个约束条件：

```
Date start = new Date();
Date end = new Date();
        
Period period = new Period(start,end);
System.out.println("Period: start = " + period.start + ", end = " + period.end);

end.setYear(78);
System.out.println("Period: start = " + period.start + ", end = " + period.end);
```

![period-test](https://github.com/guobinhit/java-skills/blob/master/images/effective-programming/method/period-test.png)

如上图所示，显然，在我们创建`Period`之后，其结束时间被改变了，打破我们设置的约束条件。为了保护`Period`实例的内部信息避免受到这种攻击，**对于构造器的每个可变参数进行保护性拷贝是必要的**，并且使用备份对象作为`Period`实例的组件，而不使用原始的对象：

```
public Period(Date start, Date end) {
    this.start = new Date(start.getTime());
    this.end = new Date(end.getTime());

    if (this.start.compareTo(this.end) > 0)
        throw new IllegalArgumentException(start + "after " + end);
}
```

![period-test-2](https://github.com/guobinhit/java-skills/blob/master/images/effective-programming/method/period-test-2.png)

如上图所示，用了新的构造器之后，上述的攻击对于`Period`实例不再有效。注意，**保护性拷贝是在检查参数的有效性之前进行的，并且有效性检查是针对拷贝之后的对象，而不是针对原始对象**。同时也请注意，我们没有用`Date`的`clone()`方法来进行保护性拷贝，因为`Date`是非`final`的，不能保证`clone()`方法一定返回类为`java.util.Date`的对象：它有可能返回专门出于恶意的目的而设计的不可信任子类的实例。例如，这样的子类可以在每个实例被创建的时候，把指向该实例的引用记录到一个私有的静态列表中，并且允许攻击者访问这个列表。这将使得攻击者可以自由地控制所有的实例。为了阻止这种攻击，**对于参数类型可以被不可信任方子类化的参数，请不要使用`clone()`方法进行保护性拷贝**。虽然替换构造器就可以成功避免上述的攻击，但是改变`Period`实例仍然是有可能的，因为它的访问方法提供了对其可变内部成员的访问能力：

```
Date start = new Date();
Date end = new Date();
        
Period period = new Period(start,end);
System.out.println("Period: start = " + period.start + ", end = " + period.end);

period.end().setYear(78);
System.out.println("Period: start = " + period.start + ", end = " + period.end);
```

![period-test-3](https://github.com/guobinhit/java-skills/blob/master/images/effective-programming/method/period-test-3.png)

为了防御这第二种攻击，只需修改两个访问方法，使它返回可变内部域的保护性拷贝即可：

```
public Date start() {
    return new Date(start.getTime());
}

public Date end() {
    return new Date(end.getTime());
}
```
![period-test-4](https://github.com/guobinhit/java-skills/blob/master/images/effective-programming/method/period-test-4.png)

如上图所示，采用了新的构造器和新的访问方法之后，`Period`真正是不可变的了。值得一提的是，有经验的程序员通常使用`Date.getTime()`返回`long`基本类型作为内部的时间表示法，而不是使用`Date`对象引用，因为`Date`是可变的。同理，长度非零的数组总是可变的，因此在把内部数组返回给客户端之前，应该总要进行保护性拷贝；另一种方案是，给客户端返回该数组的不可变视图。简而言之，如果类具有从客户端得到或者返回到客户端的可变组件，类就必须保护性地拷贝这些组件。

### 第 3 条：谨慎设计方法签名以及慎用重载

遵守下面的建议，可以帮助我们设计一个比较好的方法签名：

- 谨慎地选择方法的名称，方法的名称应该始终遵循标准的命名习惯；
- 不用过于追求提供便利的方法，每个方法都应该尽其所能；
- 避免过长的参数列表，目标是四个参数，或者更少，相同类型的长参数列表格外有害；
- 对于参数类型，要优先使用接口而不是类；
- 对于`boolean`类型的参数，要优先使用两个元素的枚举类型。

此外，对于方法的重载，我们也要特别注意，例如：

```
public class CollectionClassifier {
    public static String classify(Set<?> set) {
        return "Set";
    }

    public static String classify(List<?> list) {
        return "list";
    }

    public static String classify(Collection<?> collection) {
        return "Unknown Collection";
    }

    public static void main(String[] args) {
        Collection<?>[] collections = {
                new HashSet<String>(),
                new ArrayList<BigInteger>(),
                new HashMap<String, String>().values()
        };

        for (Collection<?> collection : collections) {
            System.out.println(classify(collection));
        }
    }
}
```

如上述程序所示，它的意图是好的，它试图根据一个集合是`Set`、`List`，或者其他类型的集合，来对它进行分类。但实际上，运行该程序后，它并没有按我们的预期执行，而是连续打印了`Unknown Collection`三次。这是因为`classify()`方法重载了，而**要调用哪个重载方法是在编译时做出决定的**。对于`for`循环中的全部三次迭代，参数的编译时类型都是相同的：`Collection<?>`。虽然每次迭代的运行时的类型都是不同的，但这并不影响对重载方法的选择。因为该参数的编译时类型为`Collection<?>`，所以，唯一合适的重载方法是第三个：`classify(Collection<?>)`，在循环的每次迭代中，都会调用这个重载方法。

对于重载方法的选择是静态的，而对于被覆盖方法的选择则是动态的。选择被覆盖的方法的正确版本是在运行时进行的，选择的依据是被调用方法所在对象的运行时类型。我们应该避免胡乱地使用重载机制，最安全而保守的策略是，永远都不要导出具有相同参数数目的重载方法。简而言之，“能够重载方法”并不意味着就“应该重载方法”。


----------

———— ☆☆☆ —— 返回 -> [The Skills of Java](https://github.com/guobinhit/java-skills/blob/master/README.md) <- 目录 —— ☆☆☆ ————
