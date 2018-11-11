# 编程思想 之「异常及错误处理」

在 Java 的异常及错误处理机制中，用`Throwable`这个类来表示可以作为异常被抛出的类。`Throwable`对象可以细分为两种类型（指从`Throwable`继承而得到的类型），分别为：

- `Error` ，表示编译时和系统错误；
- `Exception`，表示编译或运行时发生的与期望结果不相符的情形。

其中，除特殊情况外，我们一般不需要关心`Error`的问题；而`Exception`则是可以被抛出的基本类型，无论是在 Java 类库、用户方法还是在运行时故障中都可能抛出`Exception`型异常，因此`Exception`才是我们最需要关心的异常类型。在此篇文章中，我们就来了解如何处理`Exception`类型的异常。

## 异常

发现错误的理想时机是在编译阶段，也就是在我们试图运行程序之前。但实际上，编译期间并不能找出所有的错误，因此有一些错误只有到运行期间才能被发现。So，我们就需要一种恰当的方式将错误源及其信息传递出来，进而进行错误恢复处理以保证代码的健壮性。

我们将可能发生异常的代码置于`try`块之内，并用`catch`捕获异常，其格式大致如下：

```
try {
	// some code may cause exception
} catch(ExceptionType e) {
	// handle exception
}
```

抛出的异常必须在某处得到处理，其中的“某处”就是异常处理程序，也就是位于`catch`作用域内的程序。理论上，有两种异常处理的模型，分别为终止模型和恢复模型，虽然恢复模型看起来更吸引人，但其可能导致的高耦合风险让很多语言望而却步，事实也是大多数语言都选择了终止模型作为对异常的处理模型，例如 Java、C++、Python 等。

异常情形是指阻止当前方法或作用域继续执行的问题，异常说明则是声明某个方法将会抛出的异常类型。异常声明属于方法声明的一部分，紧跟在形式参数列表之后，其形式如：

```
public void methodName(String str) throws Exception {}
```

如果方法里的代码产生了异常却没有进行处理，编译器会发现这个问题并提醒我们：要么处理这个异常，要么就在异常说明中表明此方法将产生异常。这种在编译时被强制检查的异常被称为“被检查异常”或者“受检异常”，反之称为“未受检异常”。

```
package com.hit.thought.chapter10;

import java.util.Arrays;

/**
 * author:Charies Gavin
 * date:2018/3/10,16:20
 * https:github.com/guobinhit
 * description:自定义异常测试
 */
public class TestException {
    /**
     * 抛出异常
     *
     * @throws MyselfException
     */
    private static void throwExceptionMehtod() throws MyselfException {
        MyselfException me = new MyselfException("自定义异常");
        throw me;
    }

    public static void main(String[] args) {
        try {
            throwExceptionMehtod();
        } catch (MyselfException myselfException) {
            System.out.println("MyselfException: " + myselfException);
            System.out.println(Arrays.toString(myselfException.getStackTrace()));
            myselfException.printStackTrace();
        } catch (Exception exception) {
            System.out.println("Exception: " + exception);
        }
    }
}
```

![test-exception](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/handle-exception/test-exception.png)

如上述代码及结果图所示，在超类`Throwable`中提供了一个`printStackTrace()`方法，该方法打印`Throwable`的调用栈轨迹，调用栈显示了“把你带到异常抛出点”的方法调用序列。我们也可以通过`getStackTrace`方法来直接访问`printStackTrace()`方法所提供的信息，该方法返回一个由栈轨迹中的元素所构成的数组，其中每一个元素都表示栈中的一帧，元素`0`是栈顶元素，并且是调用序列中的最后一个方法的调用，数组中的最后一个元素和栈底是调用序列中的第一个方法调用。

有的时候，在我们捕获到异常后并没有很好的处理方式，因此需要重新抛出异常，把异常交给上一级环境中的异常处理程序来处理，这时，位于同一个`try`块的后续`catch`子块将被忽略。如果只是把当前异常对象重新抛出，那么`printStackTrace()`方法显示的将是原来异常抛出点的调用栈信息，而非重新抛出点的信息。想要更新异常抛出点信息，可以调用`Throwable`提供的`fillInStackTrace()`方法，该方法返回一个`Throwable`对象，它是通过把当前调用栈信息填入原来那个异常对象而建立的，因此调用`fillInStackTrace()`方法的那一行就成了异常新的发生点啦！

```
public class FillException {
    public static void main(String[] args) throws Exception {
        try {
            newCatchException();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void newCatchException() throws Exception {
        try {
            getException();
        } catch (Exception e) {
            System.out.println("Oh, catch a new exception");
            // 通过 fillInStackTrace() 方法重新抛出异常
            throw (Exception) e.fillInStackTrace();
        }
    }

    private static void catchException() throws Exception {
        try {
            getException();
        } catch (Exception e) {
            System.out.println("Oh, catch a exception");
            throw e;
        }
    }

    private static void getException() throws Exception {
        Exception exception = new Exception("Ops, cause a exception");
        throw exception;
    }
}
```

![catch-a-new-exception](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/handle-exception/catch-a-new-exception.png)

如上图所示，在使用`fillInStackTrace()`方法重新抛出异常之后，有关原来异常发生点的信息丢失了，剩下的都是与新的抛出点有关的信息。这同捕获一种异常但抛出另一种异常得到的结果类似。在实际编程中，这样丢失异常信息的事显然是不能接收的，因此我们就需要通过一种名为“异常链”的异常结构将原始异常信息与新的异常信息链接起来，从而保证异常信息的完整性。

在 JDK1.4 之前，程序员必须自己手写代码来保存原始的异常信息。但是现在，所有的`Throwable`子类在构造器中都可以接受一个`cause`对象（实际上就是`Throwable`对象）作为参数，这个`cause`对象就是原始异常。通过把原始异常当做参数传递给新异常的构造器使得即使当前位置创建并抛出了新的异常，也能通过异常链追踪到异常最初发生的位置。在`Throwable`的子类中，只有三种基本类型的异常提供了带`cause`参数的构造器，分别为`Error`、`Exception`和`RuntimeException`。**如果要把其他类型的异常链接起来，则需要使用`initCause()`方法**。

```
/**
 * 构造同一类型异常链使用构造器即可
 *
 * @throws Exception
 */
private static void catchExceptionStructure() throws Exception {
    try {
        getException();
    } catch (Exception e) {
        System.out.println("Oh, catch a exception");
        throw new Exception(e);
    }
}

/**
 * 构造非同一类型异常链需要使用 initCause() 方法
 *
 * @throws Exception
 */
private static void catchExceptionInitCause() throws Exception {
    try {
        getException();
    } catch (Exception e) {
        System.out.println("Oh, catch a exception");
        MyselfException myselfException = new MyselfException();
        myselfException.initCause(e);
        throw myselfException;
    }
}
```

![catch-simple-exception](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/handle-exception/catch-simple-exception.png)

## 运行时异常


在 Java 标准异常体系中，有一个特例，那就是`RuntimeException`及其所有子类型异常。特殊在哪里？观察如下代码及其输出结果：

```
public class TestRuntimException {
    public static void main(String[] args) {
        catchRuntimeException();
    }

    private static void catchRuntimeException() {
        getRuntimeException();
    }

    /**
     * 创建运行时异常并抛出
     */
    private static void getRuntimeException() {
        RuntimeException re = new RuntimeException();
        throw re;
    }
}
```

![runtime-exception](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/handle-exception/runtime-exception.png)

如上述所示，对于`RuntimeException`及其所有子类型异常，编译器不需要异常说明，其输出结果被告诉给`System.err`，因此如果`RuntimeException`及其子类型异常没有被捕获而直达`main()`，那么在程序退出前将调用异常的`printStackTrace()`方法。此外，有一点需要我们特别注意，那就是：**只能在代码中忽略`RuntimeException`及其子类型异常，其他类型异常的处理都是由编译器强制实施的**。究其原因，`RuntimeException`代表的是编程错误。

在这里，不知道大家是否还记得在本文开篇处，我们给出的异常处理格式？实际上，它并不完整，真正完整的异常处理格式应该是：

```
try {
	// some code may cause exception
} catch(ExceptionType e) {
	// handle exception
} finally {
	// some code must be executed
}
```

其中，关键字`finally`的含义为：**无论异常是否被抛出，`finally`子句总能被执行**。多用于把内存之外的资源恢复到它们的初始状态。

```
public class FinallyException {
    public static void main(String[] args) throws Exception {
        // 循环调用 testFinally() 方法
        for (int i = 1; i < 5; i++) {
            testFinally(i);
        }
    }

    /**
     * 测试 finally 子句
     *
     * @param i
     * @throws Exception
     */
    private static void testFinally(int i) throws Exception {
        try {
            System.out.println("Initial test finally...");
            if (i == 1) {
                System.out.println("i = " + i);
                return;
            }
            if (i == 2) {
                System.out.println("i = " + i);
                return;
            }
            if (i == 3) {
                System.out.println("i = " + i);
                return;
            }
            Exception exception = new Exception("Ops, it's a exception!");
            throw exception;
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            System.out.println("Hey buddy, u come in finally block!");
        }
    }
}
```

![initial-test-finally](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/handle-exception/initial-test-finally.png)

如上述所示，无论我们的方法从那里返回，或者是发生异常，`finally`子句总能够执行。此外，当涉及到`break`和`continue`语句的时候，`finally`子句也会得到执行。

接下来，我们聊一聊异常的限制。那么，何为异常的限制呢？即**当覆盖方法的时候，只能抛出在基类方法的异常说明里列出的那些异常**。这个限制意味着，当基类使用的代码应用到其派生类对象的时候，一样能正常工作，异常也不例外。对于异常的限制，有几点需要我们特别注意：

- 异常限制对构造器不起作用；
- 派生类构造器的异常说明必须包含基类构造器的异常说明；
- 派生类构造器不能捕获基类构造器抛出的异常。

尽管在继承过程中，编译器会对异常说明做强制要求，但异常说明本身并不属于方法类型的一部分，方法类型是由方法的名字与参数列表的类型组成的。因此，不能基于异常说明来重载方法。此外，一个出现在基类方法的异常说明中的异常，不一定会出现在派生类方法的异常说明里。

最后，在`catch`子句查找异常类型的时候，并不要求抛出的异常同处理程序所声明的异常完全匹配。例如，

```
public class MatchException {
    public static void main(String[] args) throws Exception {

        /**
         * 基类异常可以匹配导出类异常，即可以通过基类异常 catch 住
         */
        try {
            ComplicatedException ce = new ComplicatedException();
            throw ce;
        } catch (SimplieException e) {
            System.out.println("Catch SimplieException");
        }

        /**
         * 导出类异常不能匹配基类异常，即不可以通过导出类异常 catch 住
         */
        try {
            SimplieException ce = new SimplieException();
            throw ce;
        } catch (ComplicatedException e) {
            System.out.println("Catch ComplicatedException");
        }
    }
}

class SimplieException extends Exception {
}

class ComplicatedException extends SimplieException {
}
```

![catch-simple-exception](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/handle-exception/catch-simple-exception.png)

观察上图，我们可以得出结论：

- 基类异常可以匹配导出类异常，即可以通过声明基类异常`catch`住导出类异常；
- 导出类异常不能匹配基类异常，即不可以通过声明导出类异常`catch`住基类异常。

此外，如果我们先`catch`基类异常，再`catch`导出类异常，编译器是不允许的：

![declaration](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/handle-exception/declaration.png)

如上图所示，IDEA 自带的编译器会提示我们调整异常`catch`的顺序。如果我们不调整，强制编译：

![tips-exception](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/handle-exception/tips-exception.png)

如上图所示，编译器会跟我们抱怨导出类异常已经被`catch`住了，从而拒绝执行代码。

----------

———— ☆☆☆ —— 返回 -> [The Skills of Java](https://github.com/guobinhit/java-skills/blob/master/README.md) <- 目录 —— ☆☆☆ ————
