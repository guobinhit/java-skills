# 并发实战 之「 线程安全性」

在早期的计算机中不包含操作系统，它们从头到尾只能执行一个程序，并且这个程序能访问计算机中的所有资源。在这种环境中，不仅程序难以编写和运行，而且对于昂贵且稀有的计算机资源来说也是一种浪费！为了实现较高的资源利用率、公平性以及便利性，这促使人们在计算机中加入操作系统来实现多个程序同时执行。

进程（`Process`）是计算机中的程序关于某数据集合上的一次运行活动，是系统进行资源分配和调度的基本单位，也是操作系统结构的基础。**线程允许在同一个进程中同时存在多个程序控制流，其会共享进程范围内的资源，例如内存句柄和文件句柄，但每个线程都有各自的程序计数器、栈以及局部变量等**。线程也被称为轻量级进程。

## 多线程

如果程序总是以单线程方式来执行，那么无论是线程的安全性、活跃性还是性能都很好分析，但实际上，程序还会以多线程的方式执行，这时在对其进行分析就不那么容易了。举一个简单的例子，代码如下：

```java
public class UnsafeSequence {
    private int value;

    /**
     * 返回唯一的数值
     */
    public int getNext() {
        return value++;
    }
}
```
上面的程序很简单，目的就是为了在每次调用之后，返回唯一的数值。在单线程环境中，其安全性是显然的，因为每次只会有一个线程执行这个方法，所以每次`value`的值都会递增`1`，没毛病。但是在多线程环境中，这个程序就会出问题了，其有可能在连续的两次调用中返回同一个数值。至于为什么会出现这样的现象，则是因为递增运算`someVariable++`虽然看上去是单个操作，但事实上它包含三个独立的操作，分别为：读取`value`，将`value`加`1`，再将计算结果写入`value`。因此，在错误的执行时序下，可能出现两次读取的`value`值为同一个值的情况，这样的话，两次调用返回同一个值也就不难理解了。

这种由于错误的执行时序而导致程序出现错误结果的现象，称之为**竞态条件**。当然，这也说明了一个问题，那就是：**如果错误地假设程序中的操作将按照某种特定顺序来执行，那么会存在各种可能的风险**。在开发并发代码时，一定要注意线程安全性是不可破坏的。**安全性的含义是“永远不发生糟糕的事情”，而活跃性的含义是“某件正确的事情最终会发生”**。

## 安全性

要编写线程安全的代码，其核心在于要对状态访问操作进行管理，特别是对共享的（`Shared`）和可变的（`Mutable`）状态的访问。**从非正式的意义上来说，对象的状态是指存储在状态变量（例如实例或静态域）中的数据，其可能包括其他依赖对象的域**。“共享”意味着可以由多线程同时访问，而“可变”则意味着变量的值在其生命周期内可以发生变化。

Java 中的主要同步机制是关键字`synchronized`，它提供了一种独占的加锁方式，但“同步”这个术语还包括`volatile`类型的变量，显式锁以及原子变量。如果当多个线程访问同一个可变的状态变量时没有使用合适的同步，那么程序就会出现错误。有三种方式可以修复这个问题，分别为：

- 不在线程之间共享该状态变量；
- 将状态变量修改为不可变的变量；
- 在访问状态变量时使用同步。

当设计线程安全的类时，良好的面向对象技术、不可修改性，以及清晰的不变性规范都能起到一定的帮忙作用。在线程安全性中，最核心的概念就是正确性，而正确性的含义是，某个类的行为与规范完全一致。因此，在理解了正确性的含义之后，我们就可以对“线程安全性”给出一个定义，即**当多线程访问某个类时，这个类始终都能表现出正确的行为，那么就称这个类是线程安全的**。由于在线程安全类中封装了必要的同步机制，因此客户端无须进一步采取同步措施。

## 原子性

在这里，我们已经知道了某个类之所以不安全，就是因为在访问该类的状态时出了问题。那么，如果某个类根本就没有状态呢？

```java
// 线程安全
public class StatelessFactorizer implements Servlet {
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        BigInteger i = extractFromRequest(servletRequest);
        BigInteger[] factors = factor(i);
        encodeIntoResponse(servletResponse, factors);
    }
}
```
与大多数`Servlet`相同，`StatelessFactorizer`是无状态的：它既不包含任何域，也不包含任何对其他类中域的引用。计算过程中的临时状态仅存在于线程栈上的局部变量中，并且只能由正在执行的线程访问。访问`StatelessFactorizer`的线程不会影响另一个访问同一个`StatelessFactorizer`的线程的计算结果，因为这两个线程并没有共享状态，就好像它们都在访问不同的实例。由于线程访问无状态对象的行为并不会影响其他线程中操作的正确性，因此**无状态对象是线程安全的**。当我们在无状态对象中增加一个状态时，会出现什么情况？

```java
// 非线程安全
public class UnsafeCountingFactorizer implements Servlet {
    private long count = 0;
    
    public  long getCount(){
        return count;
    }
    
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        BigInteger i = extractFromRequest(servletRequest);
        BigInteger[] factors = factor(i);
        ++count;
        encodeIntoResponse(servletResponse, factors);
    }
}
```
如上述代码所示，我们在`service`方法中对新增的属性`count`使用了递增运算。从前文可知，递增运算并非是一个单独的操作，而是包含了三个独立的操作。因此，在上述代码中存在竞态条件，也就是不安全的。归根结底，在上述代码中存在竞态条件的原因就是递增运算`someVariable++`不是原子操作。那什么是原子操作呢？

- 假设有两个操作 A 和 B，如果从执行 A 的线程来看，当另一个线程执行 B 时，要么将 B 全部执行完，要么完全不执行 B，那么 A 和 B 对彼此来说是原子的。原子操作是指，对于访问同一个状态的所有操作（包括该操作本身）来说，这个操作是一个以原子方式执行的操作。

因此，我们想方设法将上述的递增操作替换为原子操作就可以解决该竞态条件啦！代码如下：

```java
// 线程安全
public class CountingFactorizer implements Servlet {
    private final AtomicLong count =new AtomicLong(0);

    public  long getCount(){
        return count.get();
    }
    
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        BigInteger i = extractFromRequest(servletRequest);
        BigInteger[] factors = factor(i);
        count.incrementAndGet();
        encodeIntoResponse(servletResponse, factors);
    }
}
```
在`java.util.concurrent.atomic`包中包含了一些原子变量类，用于实现在数值和对象引用上的原子状态转换。在实际情况中，应尽可能地使用现有的线程安全对象（例如`AtomicLong`）来管理类的状态。与非线程安全的对象相比，判断线程安全对象的可能状态及其状态转换情况要更为容易，从而也更容易维护和验证线程安全性。

## 加锁机制

当在`Servlet`中添加了一个状态变量时，可以通过线程安全的对象来管理`Servlet`的状态以维护`Servlet`的线程安全性。但如果想在`Servlet`中添加更多的状态，那么是否只需添加更多的线程安全状态变量就足够了？

```java
// 线程安全
public class UnsafeCachingFactorizer implements Servlet {
    
    private final AtomicReference<BigInteger> lastNumber = 
            new AtomicReference<BigInteger>();
    
    private final AtomicReference<BigInteger[]> lastFactors =
            new AtomicReference<BigInteger[]>();
    
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        BigInteger i = extractFromRequest(servletRequest);
        if (i.equals(lastNumber.get())) {
            encodeIntoResponse(servletResponse, lastFactors.get());
        } else {
            BigInteger[] factors = factor(i);
            lastNumber.set(i);
            lastFactors.set(factors);
            encodeIntoResponse(servletResponse, factors);
        }
    }
}
```
如上述代码所示，新增了缓存机制，其目的是为了在遇到连续相同的数时，复用第一个数的结果。然而，这种方法并不正确。尽管这些原子引用本身都是线程安全的，但在`UnsafeCachingFactorizer`存在着竞态条件，单拿`set`方法来着，每次调用都是原子的，却仍然无法同时更新`lastNumber`和`lastFactors`这两个值。如果只修改了其中一个变量，那么在两次修改操作之间，其他线程将发现不变性条件被破坏了。同样，我们也不能保证会同时获取两个值：在线程 A 获取这两个值的过程中，线程 B 可能修改了它们，这样线程 A 也发现不变性条件被破坏了。**当在不变性条件中涉及多个变量时，各个变量之间并不是彼此独立的，而是某个变量的值会对其他变量的值产生约束。因此，当更新某一个变量时，需要在同一个原子操作中对其他变量同时进行更新**。

Java 提供了一种内置的锁机制来支持原子性：同步代码块。同步代码块包括两部分：一个作为锁的对象引用，一个作为由这个锁保护的代码块。**以关键字`synchronized`来修饰的方法就是一种横跨整个方法体的同步代码块，其中该同步代码块的锁就是方法调用所在的对象**。静态的`synchronized`方法以`Class`对象作为锁。

```java
synchronized (lock) {
	// 访问或修改由锁保护的共享状态
}
```

每个 Java 对象都可以用做一个实现同步的锁，这些锁被称为内置锁（`Intrinsic Lock`）或监视器锁（`Monitor Lock`）。线程在进入同步代码块之前会自动获得锁，并且在退出同步代码块时自动释放锁，而无论是通过正常的控制路径退出，还是通过从代码块中抛出异常退出。**获得内置锁的唯一途径就是进入由这个锁保护的同步代码块或方法**。

```java
// 线程安全
public class SynchronizedFactorizer implements Servlet {

    private BigInteger lastNumber;
    private BigInteger[] lastFactors;

    @Override
    public synchronized void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        BigInteger i = extractFromRequest(servletRequest);
        if (i.equals(lastNumber)) {
            encodeIntoResponse(servletResponse, lastFactors);
        } else {
            BigInteger[] factors = factor(i);
            lastNumber = i;
            lastFactors = factors;
            encodeIntoResponse(servletResponse, factors);
        }
    }
}
```
如上述代码所示，虽然其并发性不高，但是却用`synchronized`保证了线程安全性。**Java 的内置锁相当于一种互斥体（或互斥锁），这意味着最多只有一个线程能持有这种锁**。当某个线程请求一个由其他线程持有的锁时，发出请求的线程就会阻塞。然而，**由于内置锁是可重入的，因此如果某个线程试图获取一个已经有它自己持有的锁，那么这个请求就会成功**。“重入”意味着获取锁的操作的粒度是“线程”，而不是“调用”。

```java
public class Widget {
    public synchronized void doSomething() {
    }
}

public class LoggingWidget extends Widget {
    public synchronized void doSomething() {
        System.out.println("Hello World");
        super.doSomething();
    }
}
```
如上述代码所示，如果内置锁不是可重入的，那么这段代码将发生死锁。

## 用锁来保护状态

由于锁能使其保护的代码路径以串行形式来访问，因此可以通过锁来构造一些协议以实现对共享状态的独占访问。只要始终遵循这些协议，就能确保状态的一致性。**对于可能被多个线程同时访问的可变状态变量，在访问它时都需要持有同一个锁，在这种情况下，我们称状态变量是由这个锁保护的**。每个共享的和可变的变量都应该只有一个锁来保护，从而使维护人员知道是哪一个锁。

一种常见的加锁约定是，将所有的可变状态都封装在对象的内部，并通过对象内部的内置锁对所有访问可变状态的代码路径进行同步，使得在该对象上不会发生并发访问。当某个变量由锁来保护时，意味着在每次访问这个变量时都需要首先获得锁，这样就确保在同一时刻只有一个线程可以访问这个变量。**对于每个包含多个变量的不变性条件，其中涉及的所有变量都需要由同一个锁来保护**。

----------

———— ☆☆☆ —— 返回 -> [The Skills of Java](https://github.com/guobinhit/java-skills/blob/master/README.md) <- 目录 —— ☆☆☆ ————
