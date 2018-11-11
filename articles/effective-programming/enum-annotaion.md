# 效率编程 之「枚举和注解」

### 第 1 条：用`enum`代替`int`常量

枚举类型是指由一组固定的常量组成合法值的类型，例如人的性别、中国的省份名称等。在 Java 1.5 发行版之前，表示枚举类的常用模式是声明一组具名的`int`常量，每个类型成员一个常量：

```
public class IntEnum {
    public static final int MAN = 0;
    public static final int WOMAN = 1;
}
```
上面的方法称之为“`int`枚举模式”，存在着很多的不足。它在类型安全性和使用方便性方面没有任何帮助。因为`int`枚举是编译时常量，被编译到使用它们的客户端中，如果与枚举常量关联的`int`值发生了变化，客户端就必须重新编译。否则的话，程序可以运行，但运行的行为就是不确定的。幸运的是，从 Java 1.5 发行版本开始，提供了专门用于表示枚举的`enum`类型：

```
public enum Orange {
    NAVEL,
    TEMOLE,
    BLOOD
}
```
Java 枚举类型的本质上是`int`值，其背后的基本思想非常简单：它们就是通过公有的静态`final`域为每个枚举常量导出实例的类。因为没有可以访问的构造器，枚举类型是真正`final`的。枚举还提供了编译时的安全性。包含同名常量的多个枚举类型可以在一个系统中和平共处，因为每个类型都有自己的命名空间。此外，枚举类型还允许添加任意的方法和域，并实现任意的接口。

```
public enum Planet {
    MERCURY(3.302e+23, 2.439e6),
    VENUS(4.869e+24, 6.052e6),
    EARTH(5.975e+23, 6.378e6),
    MARS(6.419e+23, 3.393e6),
    JUPITER(1.899e+27, 7.149e7),
    SATURN(5.685e+26, 6.027e7),
    URANUS(8.683e+25, 2.556e7),
    NEPTUNE(1.024e+26, 2.477e7);

    // In kilograms
    private final double mass;
    // In meters
    private final double radius;
    // In m / s^2
    private final double surfaceGravity;

    // Universal gravitational constant in m^3 / kg s^2
    private static final double G = 6.67300E-11;

    // constructor
    Planet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
        surfaceGravity = G * mass / (radius * radius);
    }

    public double getMass() {
        return mass;
    }

    public double getRadius() {
        return radius;
    }

    public double getSurfaceGravity() {
        return surfaceGravity;
    }

    public double surfaceWeight(double mass) {
        // F = ma
        return mass * surfaceGravity;
    }
}
```
编写一个像`Planet`这样的枚举类型并不难。**为了将数据与枚举常量关联起来，得声明实例域，并编写一个带有数据并将数据保存在域中的构造器**。枚举天生就是不可变的，因此所有的域都应该为`final`的。它们可以是公有的，但最好将它们做成是私有的，并提供公有的访问方法。

如果一个枚举具有普遍适用性，它就应该成为一个顶层类；如果它只是被用在一个特定的顶层类中，它就应该成为该顶层类的一个成员类。**如果枚举类型中定义了抽象方法，那么这个抽象方法就必须被它所有常量中的具体方法所覆盖**。例如，

```
public enum Operation {
    PLUS("+") {
        @Override
        double apply(double x, double y) {
            return x + y;
        }
    },
    MINUS("-") {
        @Override
        double apply(double x, double y) {
            return x - y;
        }
    },
    TIMES("*") {
        @Override
        double apply(double x, double y) {
            return x * y;
        }
    },
    DIVIDE("/") {
        @Override
        double apply(double x, double y) {
            return x / y;
        }
    };

    private final String symbol;

    Operation(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }

    abstract double apply(double x, double y);
}
```
枚举类型有一个自动产生的`valueOf(String)`方法，它将常量的名字转成常量本身；还有一个`values()`方法，可以返回枚举类型中定义的所有枚举值。**枚举构造器不可以访问枚举的静态域**，除了编译时常量域之外。这一限制是有必要的，因为构造器运行的时候，这些静态域还没有被初始化。此外，还有一种比较特殊的情况，即在枚举中设置枚举，我们称之为“策略枚举”，如：

```
public enum PayrollDay {
    MONDAY(PayType.WEEKDAY), TUESDAY(PayType.WEEKDAY), WEDNESDAY(PayType.WEEKDAY),
    THURSDAY(PayType.WEEKDAY), FRIDAY(PayType.WEEKDAY), SATURADY(PayType.WEEKEND), SUNDAY(PayType.WEEKEND);

    private final PayType payType;

    PayrollDay(PayType payType) {
        this.payType = payType;
    }

    // 调用策略枚举中的方法，计算工资
    double pay(double hoursWorked, double payRate) {
        return payType.pay(hoursWorked, payRate);
    }

    // 策略枚举
    private enum PayType {
        WEEKDAY {
            @Override
            double overtimePay(double hours, double payRate) {
                return hours <= HOURS_PER_SHIFT ? 0 :
                        (hours - HOURS_PER_SHIFT) * payRate / 2;
            }
        },
        WEEKEND {
            @Override
            double overtimePay(double hours, double payRate) {
                return hours * payRate / 2;
            }
        };

        private static final int HOURS_PER_SHIFT = 8;

        // 强制策略枚举中的每个枚举都覆盖此方法
        abstract double overtimePay(double hours, double payRate);

        // 实际计算工资的方法
        double pay(double hoursWorked, double payRate) {
            double basePay = hoursWorked * payRate;
            return basePay + overtimePay(hoursWorked, payRate);
        }
    }
}
```
如上述代码所示，我们在实现计算工资（基础工资 + 超时工资）的情景下，使用了策略枚举。通过策略枚举，使我们的代码更加安全和简洁。总之，如果多个枚举常量同时共享相同的行为，就应该考虑使用策略枚举。

### 第 2 条：注解优先于命名模式

在 Java 1.5 发行版之前，一般使用命名模式表明程序元素需要通过某种工具或者框架进行特殊处理。例如，JUnit 测试框架原本要求它的用户一定要用`test`作为测试方法的开头，这种方法可行，但是有几个很严重的缺点：

- 文字拼写错误会导致失败，且没有任何提示；
- 无法确保它们只用于相应的程序元素上；
- 它们没有提供将参数值与程序元素管理起来的好方法。

不过，注解的出现，很好的解决了所有这些问题。假设想要定义一个注解类型来指定简单的测试，它们自动运行，并在抛出错误时失败。以下就是这样的一个注解类型，命名为`Test`：

```
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test {
}
```
`Test`注解类型的声明就是它自身通过`Retention`和`Target`注解进行了注解。注解类型中的这种注解被称作元注解。`@Retention(RetentionPolicy.RUNTIME)`元注解表明，`Test`注解应该在运行时保留，如果没有保留，测试工具就无法知道`Test`注解；`@Target(ElementType.METHOD)`元注解表明，`Test`注解只在方法声明中才是合法的，它不能运用到类声明、域声明或者其他程序元素上。此外，`Test`注解只能用于无参的静态方法。注解永远不会改变被注解代码的语义，但是使它可以通过工具进行特殊的处理。例如像这种简单的测试运行类：

```
public class RunTests {
    /**
     * 该方法为 静态无参 的，因此可以通过 @Test 测试
     */
    @Test
    public static void testAnnocation() {
        System.out.println("hello world");
    }

    /**
     * 该方法为 静态有参 的，因此不可以通过 @Test 测试
     */
    @Test
    public static void testAnnocation2(String word) {
        System.out.println(word);
    }

    /**
     * 该方法为 非静态无参 的，因此不可以通过 @Test 测试
     */
    @Test
    public void testAnnocation3() {
        System.out.println("hello world");
    }

    public static void main(String[] args) throws Exception {
        int tests = 0;
        int passed = 0;
        Class testClass = Class.forName(args[0]);
        for (Method method : testClass.getDeclaredMethods()) {
            // 判断类中的被 @Test 注解的方法
            if (method.isAnnotationPresent(Test.class)) {
                tests++;
                try {
                    // 通过反射，执行被注解的方法
                    method.invoke(null);
                    passed++;
                } catch (InvocationTargetException warppedExc) {
                    Throwable exc = warppedExc.getCause();
                    System.out.println(method + " failed: " + exc);
                } catch (Exception exc) {
                    System.out.println("Invalid @Test: " + method);
                }
            }
        }
        System.out.printf("Passed: %d, Failed: %d%n", passed, tests - passed);
    }
}
```

![run-tests](https://github.com/guobinhit/java-skills/blob/master/images/effective-programming/enum-annotaion/run-tests.png)

如上述代码及执行结果图所示，通过使用完全匹配的类名如`com.hit.effective.chapter5.annotation.RunTests`，并通过调用`Method.invoke()`反射式地运行类中所有标注了`Test`的方法。`isAnnotationPresent()`方法告知该工具要运行哪些方法。如果测试方法抛出异常，反射机制就会将它封装在`InvocationTargetException`中。该工具捕捉到了这个异常，并打印失败报告，包含测试方法抛出的原始异常，这些信息通过`getCause()`方法从`InvocationTargetException`中提取出来。如果尝试通过反射调用测试方法时抛出`InvocationTargetException`之外的任何异常，表明编译时没有捕捉到`Test`注解的无效用法。

除上述方法之外，我们也可以通过判断是否抛出某种特定的异常作为判断是否通过测试的标准，具体方法可以参考 GitHub 上的「[java-skills](https://github.com/guobinhit/java-skills)」项目中的`RunExceptionTests`和`RunMoreExceptionTests`两个注解测试示例。总之，既然有了注解，就完全没有理由再使用命名模式了。



----------

———— ☆☆☆ —— 返回 -> [The Skills of Java](https://github.com/guobinhit/java-skills/blob/master/README.md) <- 目录 —— ☆☆☆ ————
