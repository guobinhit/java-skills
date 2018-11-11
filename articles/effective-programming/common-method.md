# 效率编程 之「对于所有对象都通用的方法」

### 第 1 条：覆盖`equals`方法时请遵守通用约定

覆盖`equals`方法看似很简单，但是有许多覆盖方式会导致错误，并且后果非常严重。最容易避免这类问题的办法就是不覆盖`equals`方法，在这种情况下，类的每个实例都只与它自身相等。如果类满足了以下任何一个条件，就不需要我们覆盖`equals`方法：

- 类的每个实例本质上都是唯一的；
- 不关心类是否提供了“逻辑相等”的测试功能；
- 超类已经覆盖了`equals`方法，从超类继承过来的行为对于子类也是合适的；
- 类是私有的或是包级私有的，可以确定它的`equals`方法永远不会被调用。

有一种“值类”不需要覆盖`equals`方法，即用实例受控确保“每个值至多只存在一个对象”的类，如枚举类型。否则的话，如果要覆盖`equals`方法，则需要满足以下等价关系：

- 自反性，对于任何非`null`的引用值`x`，`x.equals(x)`必须返回`true`；
- 对称性，对于任何非`null`的引用值`x`和`y`，当且仅当`x.equals(y)`返回`true`时，`y.equals(x)`必须返回`true`；
- 传递性，对于任何非`null`的引用值`x`、`y`和`z`，如果`x.equals(y)`返回`true`，并且`y.equals(z)`也返回`true`，那么`x.equals(z)`也必须返回`true`；
- 一致性，对于任何非`null`的引用值`x`和`y`，只要`equals()`的比较操作在对象中所用的信息没有被修改，多次调用`x.equals(y)`就会一致地返回`true`，或者一致地返回`false`；
- 对于任何非`null`的引用值`x`，`x.equals(null)`必须返回`fales`.

如果违反了上述等价关系，就会导致类在比较的时候出现不可预测的行为。例如，`Timestamp`的`equals`就违反了对称性，因此如果`Timestamp`和`Date`对象被用于同一个集合中，或者以其他方式被混合在一起，就会引起不正确的行为。无论类是否是不可变的，都不用使`equals`方法依赖于不可靠的资源。基于上述原则及要求，我们得出了以下实现高质量`equals`方法的诀窍：

1. **使用`==`操作符检查“参数是否为这个对象的引用”**。如果是，则返回`ture`。这只不过是一种性能优化，如果比较操作有可能很昂贵，就值得这么做。
2. **使用`instanceof`操作符检查“参数是否为正确的类型”**。如果不是，则返回`false`。一般来说，所谓“正确的类型”是指`equals`方法所在那个类。有些情况下，则是指该类所实现的某个接口。如果类实现的接口改进了`equals`约定，允许在实现了该接口的类之前进行比较，那么就使用接口。
3. **把参数转换成正确的类型**。因为转换之前进行过`instanceof`测试，所以确保会成功。
4. **对于该类中的每个“关键”域，检查参数中的域是否与该对象中对应的域相匹配**。如果这些测试全部成功，则返回`true`；否则返回`false`。如果第 2 步中的类型是个接口，就必须通过接口方法访问参数中的域；如果该类型是个类，也许就能直接访问参数中的域，这药取决于它们的可访问性。
5. **当我们编写完`equals`方法之后，应该问自己是三个问题：它是否是对称的、传递的、一致的**？

对于既不是`float`也不是`double`类型的基本类型域，可以使用`==`操作符进行比较；对于对象引用域，可以递归地调用`equals`方法；对于`float`域，可以使用`Float.compare`方法；对于`double`域，则使用`Double.compare`方法。对`float`和`double`域进行特殊的处理是有必要的，因此存在着`Float.NaN`、`-0.0f`以及类似的`double`常量。

域的比较顺序可能会影响到`equals`方法的性能。为了获得最佳的性能，应该最先比较最有可能不一致的域，或者是开销最低的域，最理想的情况是两个条件同时满足的域。

```
public final class PhoneNumber {
    private final short areaCode;
    private final short prefix;
    private final short lineNubmer;

    /**
     * 构造函数
     *
     * @param areaCode
     * @param prefix
     * @param lineNumber
     */
    public PhoneNumber(int areaCode, int prefix, int lineNumber) {
        rangeCheck(areaCode, 999, "area code");
        rangeCheck(prefix, 999, "prefix");
        rangeCheck(lineNumber, 9999, "line number");
        this.areaCode = (short) areaCode;
        this.prefix = (short) prefix;
        this.lineNubmer = (short) lineNumber;
    }

    /**
     * 参数校验方法
     *
     * @param arg
     * @param max
     * @param name
     */
    private static void rangeCheck(int arg, int max, String name) {
        if (arg < 0 || arg > max) {
            throw new IllegalArgumentException(name + " : " + arg);
        }
    }

    /**
     * 覆盖 equals 方法
     *
     * @param o
     * @return true or false
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PhoneNumber)) {
            return false;
        }
        PhoneNumber pn = (PhoneNumber) o;
        return pn.lineNubmer == lineNubmer
                && pn.prefix == prefix
                && pn.areaCode == areaCode;
    }
}
```
如上述代码所示，该类的`equals`方法就是根据上面的诀窍构造出来的，符合`equals`方法的各项等价关系以及通用约定。下面，给出有关`equals`方法的最后告诫：

- 覆盖`equals`方法时总要覆盖`hashCode`方法；
- 不用切图让`equals`方法过于智能；
- 不用将`equals`方法声明的`Object`对象替换为其他的类型。

### 第 2 条：覆盖`equals`方法时总要覆盖`hashCode`方法

一个很常见的错误根源在于没有覆盖`hashCode`方法。**在每个覆盖了`equals`方法的类中，也必须覆盖`hashCode`方法**。如果不这样做的话，就会违反`Object.hashCode`的通用约定，从而导致该类无法结合所有基于散列的集合一起正常工作，这样的集合包括`HashMap`、`HashSet`和`Hashtable`等。以上面 **第 1 条** 中的`PhoneNumber`类为例，如果我们企图将其与`HashMap`一起使用：

```
Map<PhoneNumber, String> amap = new HashMap<PhoneNumber, String>();
amap.put(new PhoneNumber(010, 521, 1314), "Gavin");
```

这时候，我们可能期望`amap.get(new PhoneNumber(010, 521, 1314))`会返回`Gavin`，但实际上返回的是`null`。出现这样现象的原因就是，我们没有覆盖`hashCode`方法，以至于两个相等的实例具有不相等的散列码。修正这个问题非常简单，只需为`PhoneNumber`类提供一个合适的`hashCode`方法即可。那么，`hashCode`方法应该是什么样的呢？编写一个合法但并不好用的`hashCode`方法没有任何价值。例如，下面这个方法总是合法，但是永远都不应该被正式使用：

```
@Override
public int hashCode() {
	return 20151120;
}
```
上面这个`hashCode`方法是合法的，因为它确保了相等的对象总是具有同样的散列码。但是它也是极为恶劣的，因为它使得每个对象都具有同样的散列码。因此，每个对象都被映射到同一个散列通中，使散列表退化为链表。它使得本该线性时间运行的程序变成了以平方级时间在运行。对于规模很大的散列表而言，这会关系到散列表能否正常工作。一个好的散列函数通常倾向于“为不相等的对象产生不相等的散列码”。理想情况下，散列函数应该把集合中不相等的实例均匀地分布到所有可能的散列值上。想要完全达到这种理想的情形是非常困难的，幸运的是，相对接近这种理想情形并不太困难。下面给出一种简单的解决办法：

- 1、把某个非零的常数值，比如说 1120，保存在一个名为`result`的`int`类型的变量中。
- 2、对于对象中每个关键域`f`（指`equals`方法中涉及的每个域），完成以下步骤：
  - a. 为该域计算`int`类型的散列码`c`：
     - i. 如果该域是`boolean`类型，则计算`(f?1:0)`。
     - ii. 如果该域是`byte`、`char`、`short`或者`int`类型，则计算`(int)f`。
     - iii. 如果该域是`long`类型，则计算`(int)(f^(f>>>32))`。
     - iv. 如果该域是`float`类型，则计算`Float.floatToIntBits(f)`。
     - v. 如果该域是`double`类型，则计算`Double.doubleToLongBits(f)`，然后按照步骤`2.a.iii`，为得到的`long`类型值计算散列值。
     - vi. 如果该域是一个对象引用，并且该类的`equals`方法通过递归地调用`equals`的方式来比较这个域，则同样为这个域递归地调用`hashCode`方法。如果需要更复杂的比较，则为这个域计算一个“范式”，然后针对这个范式调用`hashCode`方法。如果这个域的值为`null`，则返回`0`（或者其他某个常数，但通常是`0`）。
     - vii. 如果该域是一个数组，则要把每个元素当做单独的域来处理。也就是说，递归地应用上述规则，对每个重要的元素计算一个散列码，然后根据步骤`2.b`中的做法把这些散列值组合起来。如果数组域中的每个元素都很重要，可以利用 JDK 发行版本`1.5`中增加的`Arrays.hashCode`方法。
  - b. 按照下面的公式，把步骤`2.a`中计算得到的散列码`c`合并到`result`中：
      - `result = 31 * result + c;`
- 3、返回`result`。
- 4、写完了`hashCode`方法之后，问问自己“相等的实例是否都具有相等的散列码”。要编写单元测试来验证我们的推断。如果相等的实例有着不相等的散列码，则要找出原因，并修正错误。

在散列码的计算过程中，可以把冗余域排除在外。换句话说，如果一个域的值可以根据参与计算的其他域值计算出来，则可以把这样的域排除在外。必须排除`equals`比较计算中没有用到的任何域。但是，不用试图从散列码计算中排除掉一个对象的关键部分来提高性能。

```
@Override
public int hashCode() {
    int result = 1120;
    result = 31 * result + areaCode;
    result = 31 * result + prefix;
    result = 31 * result + lineNubmer;
    return result;
}
```
如上述代码所示，这个`hashCode`方法就是根据上面的方法构造出来的，满足`hashCode`方法的通用约定。



----------

———— ☆☆☆ —— 返回 -> [The Skills of Java](https://github.com/guobinhit/java-skills/blob/master/README.md) <- 目录 —— ☆☆☆ ————
