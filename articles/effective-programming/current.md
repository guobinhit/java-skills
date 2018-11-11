# 效率编程 之「并发」

### 第 1 条：同步访问共享的可变数据

关键字`synchronized`可以保证在同一时刻，只有一个线程可以执行某一个方法，或者某一个代码块。许多程序员把同步的概念仅仅理解为一种互斥的方式，即当一个对象被一个线程修改的时候，可以阻止另一个线程观察到对象内部不一致的状态。按照这种观点，对象被创建的时候处于一致的状态，当有方法访问它的时候，它就被锁定了。这些方法观察到对象的状态，并且可能会引起状态转变，即把对象从一种一致的状态转换到另一种一致的状态。正确地使用同步可以保证没有任何方法会看到对象处于不一致的状态中。

上述的观点是正确的，但是它并没有说明同步的全部意义。如果没有同步，一个线程的变化就不能被其他线程看到。**同步不仅可以阻止一个线程看到对象处于不一致的状态之中，它还可以保证进入同步方法或者同步代码块的每个线程，都看到由同一个锁保护的之前所有的修改状态**。

Java 语言规范保证读或者写一个变量是原子的，除非这个变量是`long`或者`double`类型。换句话说，**读取一个非`long`或者`double`类型的变量，可以保证返回的值是某个线程保存在该变量中的，即使多个线程在没有同步的情况下并发地修改这个变量也是如此**。我们可能听说过，“为了提高性能，在读或写原子数据的时候，应该避免使用同步”，这个建议是非常危险而错误的。虽然语言规范保证了线程在读取原子数据的时候，不会看到任意的数值，但是它并不保证一个线程写入的值对于另一个线程将是可见的。**为了在线程之间进行可靠的通信，也为了互斥访问，同步是必要的**。

```
public class StopThread {
    private static boolean stopRequested;

    public static void main(String[] args) throws InterruptedException {
        Thread backgroundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (!stopRequested) {
                    i++;
                    System.out.println("Thread running i = " + i);
                }
            }
        });
        backgroundThread.start();

        TimeUnit.SECONDS.sleep(1);
        stopRequested = true;
    }
}
```
考虑上面的代码，它的本意是期望这个程序在运行大约一秒钟左右，之后主线程将`stopRequested`设置为`true`，以使后台线程的循环终止。但实际上，这个程序在何时终止是不可预知的，因为没有同步，就不能保证后台线程何时“看到”主线程对`stopRequested`的值所做的改变，其结果很有可能导致一个活性失败，即程序无法前进。修正这个问题的一种方式是同步访问`stopRequested`域：

```
public class StopThread2 {
    private static boolean stopRequested;

    private static synchronized void requestStop() {
        stopRequested = true;
    }

    private static synchronized boolean isStopRequested() {
        return stopRequested;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread backgroundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (!isStopRequested()) {
                    i++;
                    System.out.println("Thread running i = " + i);
                }
            }
        });
        backgroundThread.start();

        TimeUnit.SECONDS.sleep(1);
        requestStop();
    }
}
```

如上述代码所示，写方法和读方法都被同步了。只同步写方法还不够！实际上，**如果读和写操作没有都被同步，同步就不会起作用**。`StopThread2`中被同步方法的动作即使没有同步也是原子的。换句话说，这些方法的同步只是为了它的通信效果，而不是为了互斥访问。虽然循环的每个迭代中的同步开销很小，还是有其他更正确的替代方法，它更加简洁，性能也可能更好。如果`stopRequested`被声明为`volatile`，`StopThread2`中的锁就可以省略。**虽然`volatile`修饰符不执行互斥访问，但它可以保证任何一个线程在读取该域的时候都将看到最近刚刚被写入的值**：

```
public class StopThread3 {
    private static volatile boolean stopRequested;

    public static void main(String[] args) throws InterruptedException {
        Thread backgroundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (!stopRequested) {
                    i++;
                    System.out.println("Thread running i = " + i);
                }
            }
        });
        backgroundThread.start();

        TimeUnit.SECONDS.sleep(1);
        stopRequested = true;
    }
}
```
在使用`volatile`的时候务必要小心。考虑下面的方法，假设它要产生序列号：

```
private static volatile int nextSerialNumber = 0;

public static int getNextSerialNumber() {
    return nextSerialNumber++;
}
```
这个方法的目的是要确保每个调用都返回不同的值（只要不超过 $2^{32}$ 个调用）。这个方法的状态只包含一个可原子访问的域：`nextSerialNumber`，这个域的所有可能的值都是合法的。因此，不需要任何同步来保护它的约束条件。然后，如果没有同步，这个方法仍然无法正常工作。问题在于，增量操作符（`++`）不是原子的，它执行两项操作：首先它读取值，然后写回一个新值，相当于原来的值再加上`1`。如果第二个线程在第一个线程读取旧值和写新值期间读取这个域，第二个线程就会与第一个线程一起看到同一个值，并返回相同的序列号。这就是安全性失败，即程序会计算出错误的结果。修正`getNextSerialNumber()`方法的一种方法是在它的声明中增加`synchronized`修饰符：

```
private static int nextSerialNumber = 0;

public static synchronized int getNextSerialNumber() {
    return nextSerialNumber++;
}
```

还有一种方法，就是使用`AtomicLong`类，它是`java.util.concurrent.atomic`的一部分，它所做的工作正是我们想要的，并且有可能比同步版的`getNextSerialNumber()`执行得更好：

```
private static final AtomicLong nextSerialNumber = new AtomicLong();

public static long getNextSerialNumber() {
    return nextSerialNumber.getAndIncrement();
}
```
此外，如果方法修改了静态域，那么我们也必须同步对这个域的访问，即使它往往只用于单线程。客户端在这种方法上执行外部同步是不可能的，因为不可能保证其他不相关的客户也会执行外部同步。简而言之，当多个线程共享可变数据的时候，每个读或者写操作的线程都必须执行同步。如果没有同步，就无法保证一个线程所做的修改可以被另一个线程获知。未能同步共享可变数据会造成程序的活性失败和安全性失败，这样的失败是最难以调试的。它们可能是间歇性的，且与时间相关，程序的行为在不同的 VM 上可能根本不同。如果只需要线程之间的通信，而不需要互斥，`volatile`修饰符是一种可以接受的同步形式，但要正确的使用它可能需要一些技巧。

### 第 2 条：避免过度同步以及并发工具优先于`wait`和`notify`

为了避免活性失败和安全性失败，在一个被同步的方法或者代码块中，永远不要放弃对客户端的控制。换句话说，在一个被同步的区域内部，不要调用设计成要被覆盖的方法，或者是由客户端以函数对象的形式提供的方法。

通常，我们应该在同步区域内做尽可能少的工作。更重要的是，永远不要过度同步。在这个多核的时代，过度同步的实际成本并不是指获取锁所花费的 CPU 时间，而是值失去了并行的机会，以及因为需要确保每个核都有一个一致的内存视图而导致的延迟。过度同步的另一项潜在开销在于，它会限制 VM 优化代码执行的能力。

此外，直接使用`wait`和`notify`比较困难，我们应该使用更高级的并发工具来代替。`java.util.concurrent`中更高级的工具分成三类，分别为：`Executor Framework`、并发集合（`Concurrent Collection`）以及同步器（`Synchronizer`）。

并发集合为标准的集合接口（如`List`、`Queue`、`Map`）提供了高性能的并发实现。为了提供高并发性，这些实现在内部自己管理同步。因此，并发集合不可能排除并发活动；将它锁定也没有什么作用，只会使程序的速度变慢。而且除非不得已，否则应该优先使用`ConcurrentHashMap`，而不是使用`Collections.synchronizedMap`或者`Hashtable`。只要用并发`Map`代替老式的同步`Map`，就可以极大地提升并发应用程序的性能。更一般地，应该优先使用并发集合，而不是使用外部同步的结合。

同步器是一些使线程能够等待另一个线程的对象，允许它们协调动作。最常用的同步器是和`Semaphore`，较不常用的是`CyclicBarrier`和`Exchanger`。其中，倒计数锁存器（`CountDownLatch`）是一次性的障碍，允许一个或者多个线程等待一个或者多个其他线程来做某些事情。`CountDownLatch`的唯一构造器带有一个`int`类型的参数，这个`int`参数是指允许所有在等待的线程被处理之前，必须在锁存器上调用`countDown()`方法的次数。

对于间歇性定时，始终应该优先使用`System.nanoTime`，而不是`System.currentTimeMills`。`System.nanoTime`更加准确也更加精确，它不受系统的实时时钟的调整所影响。此外，虽然我们始终应该优先使用并发工具，而不是使用`wait`和`notify`，但可能必须维护使用了`wait`和`notify`的遗留代码。**`wait`方法被用来使线程等待某个条件，它必须在同步区域内部被调用，这个同步区域将对象锁定在了调用`wait`方法的对象上**。下面是使用`wait`方法的标准模式：

```
synchronized(obj) {
	while(<condition does not hold>) {
		obj.wait(); // Release lock, and reacquires on wakeup
	}
	doSomething(); // Perform action appropriate to condition 
}
```
始终应该使用`while`循环模式来调用`wait`方法；永远不要在循环之外调用`wait`方法。循环会在等待之前和之后测试条件。现在已经没有理由在新代码中使用`wait`和`notify`，即使有，也是极少的。如果我们在维护使用`wait`和`notify`的代码，务必确保始终是利用标准的模式从`while`循环内部调用`wait`。一般情况下，我们应该优先使用`notifyAll`，而不是使用`notify`。如果使用`notify`，请一定要小心，以确保程序的活性。

### 第 3 条：慎用延迟初始化

延迟初始化是延迟到需要域的值时才将它初始化的这种行为。如果永远不需要这个值，这个域就永远不会被初始化。这种方法即适用于静态域，也适用于实例域。虽然延迟初始化主要是一种优化，但它也可以用来打破类和实例初始化中的有害循环。**在大多数情况下，正常的初始化要优先于延迟初始化**。下面是正常初始化的实例域的一个典型声明，注意其中使用了`final`修饰符：

```
private final FieldType field = computeFieldValue();
```
**如果利用延迟初始化来破坏初始化的循环，就要使用同步访问方法**，因为它是最简单、最清楚的替代方法：

```
private FieldType field；

synchronized FieldType getField() {
	if (field == null) {
		field = computeFieldValue();
	}
	return field;
}
```
这两种习惯模式（正常的初始化和使用了同步访问方法的延迟初始化）应用到静态域上时保持不变，除了给域和访问方法声明添加了`static`修饰符之外。**如果出于性能的考虑需要对静态域使用延迟初始化，就使用“延迟初始化`Holder`类模式”**。这种模式保证了类要到被用到的时候才会初始化：

```
private static class FieldHolder {
    static final FieldType field = computeFiledValue();
}

static FieldType getField() {
    return FieldHolder.field;
}
```

当`getField`方法第一次被调用时，它第一次读取`FieldHolder.field`，导致`FieldHolder`类得到初始化。这种模式的魅力在于，`getField`方法没有被同步，并且只执行一个域的访问，因此延迟初始化实际上并没有增加任何访问成本。现代 VM 将在初始化该类的时候，同步域的访问。一旦这个类被初始化，VM 将修补代码，以便后续对该域的访问不会导致任何测试或者让步。

**如果出于性能的考虑而需要对实例域使用延迟初始化，就使用“双重检查模式”**。这种模式避免了在域被初始化之后访问这个域时的锁定开销，其背后的思想是：两次检查域的值，第一次检查时没有锁定，看看这个域是否被初始化；第二次检查时有锁定。只有当第二次检查时表明这个域没有被初始化，才会对这个域进行初始化。因为如果域已经被初始化就不会有锁定，且域被声明为`volatile`很重要，它保证了线程间通信可靠性。下面就是这种习惯模式：

```
private volatile FieldType field;

FieldType getField() {
    FieldType result = field;
    // 第一次检查，无锁定
    if (result == null) {
         // 第二次检查，有锁定
         synchronized (this) {
              result = field;
              if (result == null) {
	              field = result = new FieldType();
	          }
          }
     }
     return result;
}
```
这段代码可能看起来似乎有些费解，尤其对于需要用到局部变量`result`可能有点不解。这个变量的作用是确保`filed`只在已经被初始化的情况下读取一次。虽然这不是严格需要，但是可以提升性能，并且因为给低级的并发编程应用了一些标准，因此更加优雅。双重检查模式的两个变量值得一提，有时候，我们可能需要延迟初始化一个可以接受重复初始化的实例域。如果处于这种情况，就可以使用双重检查惯用法的一个变形，它省去了第二次检查。没错，就是“单重检查模式”。下面就是这样的一个例子，注意`field`仍然被声明为`volatile`类型：

```
private volatile FieldType field;

FieldType getField() {
    FieldType result = field;
    if (result == null) {
        field = result = new FieldType();
    }
    return result;
}
```

当双重检查模式或者单重检查模式应用到数值类型的基本类型域时，就用`0`来检查这个域（它是数值类型基本变量的默认值），而不是`null`。简而言之，大多数的域应该正常地进行初始化，而不是延迟初始化。如果为了达到性能目标，或者为了破坏有害的初始化循环，而必须延迟初始化一个域，就可以使用相应的延迟初始化方法。对于实例域，就使用“双重检查模式”；对于静态域，则使用“延迟初始化`Holder`类模式”；对于可以接受重复初始化的实例域，也可以考虑使用“单重检查模式”。




----------

———— ☆☆☆ —— 返回 -> [The Skills of Java](https://github.com/guobinhit/java-skills/blob/master/README.md) <- 目录 —— ☆☆☆ ————
