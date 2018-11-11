# 编程思想 之「Java I/O 系统」

对程序语言的设计者来说，创建一个好的输入/输出（I/O）系统是一项艰难的任务。任务的难度大多数来自于要覆盖所有的可能性，不仅存在各种 I/O 源端和想要与之通信的接收端（如文件、控制台、网络链接等），而且还需要以多种不同的方式与它们进行通信（如顺序、随机存取、缓冲、二进制、按字符、按行、按字等）。Java 类库的设计者通过创建大量的类（装饰模式）来解决这个难题。自从 Java 1.0 版本以来，Java 的 I/O 类库发生了明显的变化，在原来面向字节的类中添加了面向字符和基于 Unicode 的类；在 JDK 1.4 中，添加了`nio`类，以改进 I/O 的性能及功能。

## 老 I/O

在 I/O 类库中，有一个`File`类，它既能代表一个特定文件的名称，又能代表一个目录下的一组文件的名称，如果它指的是一个文件集，我们就可以对此集合调用`list()`方法，这个方法会返回一个字符数组。此外，`File`类不仅仅只代表存在的文件或目录，也可以用`File`对象来创建新的目录或尚不存在的整个目录路径，还可以通过`File`对象查看文件的特性，如大小、最后修改日期、是否可读等等。

编程语言的 I/O 类库中常使用流这个抽象概念，它代表任何有能力产出数据的数据源对象或者是有能力接收数据的接收端对象。“流”屏蔽了实际的 I/O 设备中处理数据的细节。Java 类库中的 I/O 类分成输入和输出两部分，通过继承，任何自`InputStream`或`Reader`派生而来的类都含有名为`read()`的基本方法，用于读取单个字节或者字符数组；同样，任何自`OutputStream`或`Writer`派生而来的类都含有名为`write()`的基本方法，用于写单个字节或者字节数组。但是，我们通常不会直接用到`read()`和`write()`，它们存在的原因是别的类使用它们以提供更有用的接口。因此，我们很少使用单一的类来创建流对象，而是通过叠加多个流对象来提供所期望的功能。实际上，Java 中“流”类库让人迷糊的主要原因就在于：创建单一的结果流，却需要创建多个对象。同样，这也是 Java I/O 类库操作不便的原因，即：**我们必须创建许多类——“核心” I/O 类型加上所有的装饰器，才能得到我们所希望的单个 I/O 对象**。

`InputStream`和`OutputStream`是面向字节的 I/O 类型，而`Reader`和`Writer`则是面向字符的 I/O 类型且提供兼容 Unicode 字符的能力。`InputStreamReader`可以把`InputStream`转换为`Reader`，而`OutputStreamWriter`可以把`OutputStream`转换为`Writer`，设计`Reader`和`Writer`继承层次结构主要是为了国际化。几乎所有原始的 Java I/O 流类都有相应的`Reader`和`Writer`类来提供天然的 Unicode 操作，然后在某些场合，面向字节的`InputStream`和`OutputStream`才是正确的解决方案。例如，`java.util.zip`类库就是面向字节的而不是面向字符的。

实际上，在`InputStream`和`OutputStream`继承体系之外，还有一个自我独立的类`RandomAccessFile`，它适用于由大小已知的记录组成的文件，所以我们可以使用`seek()`将记录从一处转移到另一处，然后读取或者修改记录。文件中记录的大小并不一定都相同，只要我们能够确定那些记录有多大以及它们在文件中的位置即可。除了实现了`DataInput`和`DataOutput`接口（`DataInputStream`和`DataOutputStream`也实现了这个接口，且分别间接继承自`InputStream`和`OutputStream`）之外，它和这两个继承层次结构没有任何关联。在任何情况下，`RandomAccessFile`都是自我独立的，直接从`Object`派生而来。从本质上来说，`RandomAccessFile`的工作方式类似于把`DataInputStream`和`DataOutputStream`组合起来使用，还添加了一些方法。此外，只有`RandomAccessFile`支持搜寻方法，并且只适用于文件。在 JDK 1.4 中，`RandomAccessFile`的大多数功能由`nio`存储映射文件所取代。

```
public class BufferedInputFile {
    /**
     * 将异常抛到控制台
     * 
     * @param filename 此参数为文件的全路径，例如：/Users/bin.guo/Documents/GitRepo/java-skills/PROJECT_CODE/javaskills/src/com/hit/thought/chapter16/io/BufferedInputFile.java
     * @return 文件内容
     * @throws IOException
     */
    public static String read(String filename) throws IOException {
        // 按行读取输入
        BufferedReader in = new BufferedReader(new FileReader(filename));
        String s;
        StringBuilder sb = new StringBuilder();
        while ((s = in.readLine()) != null) {
            sb.append(s + "\n");
        }
        // 特别注意：一定要记得关闭流
        in.close();
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        System.out.println(read(Constants.FILE_ABSOLUTE_PATH + "BufferedInputFile.java"));
    }
}
```
如上述代码所示，如果我们想要打开一个文件用于字符输入，可以使用以`String`或`File`对象作为文件名的`FileInputReader`，为了提供速度，我们可以对那个文件进行缓冲，也就是将所产生的引用传给一个`BufferedReader`构造器。由于`BufferedReader`也提供`readLine()`方法，所以这就是我们的最终对象和进行读取的接口。当`readLine()`返回`null`时，表示到达了文件的末尾。其中，`sb`用来累积文件的全部内容，包括必须添加的换行符，因为`readLine()`方法会自动删除换行符。最后，调用`close()`关闭文件。在这里，有一点需要我们特别注意，那就是：**在使用流对象的时候，一定要记得关闭流，以防止资源耗尽**。

```
public class FormattedMemoryInput {
    public static void main(String[] args) throws IOException {
        try {
            /**
             * 要读取格式化数据，可以使用 DataInputStream，它是一个面向字节的 I/O 类；
             * 此外，必须向 ByteArrayInputStream 提供字节数组
             */
            DataInputStream in = new DataInputStream(
                    new ByteArrayInputStream(
                            BufferedInputFile.read(Constants.FILE_ABSOLUTE_PATH + "FormattedMemoryInput.java")
                                    .getBytes()
                    ));
            while (true) {
                System.out.println((char) in.readByte());
            }
        } catch (EOFException e) { // EOF 表示 end of file，此异常表示文件已读取完毕
            System.out.println("End of Stream");
        }
    }
}
```

如上述代码所示，如果我们要读取格式化数据，可以使用`DataInputStream`，它是一个面向字节的 I/O 类。如果我们从`DataInputStream`用`readByte()`一次一个字节地读取字符，那么任何字节的值都是合法的结果，因此返回值不能用来检查输入是否结束。相反，我们可以使用`available()`方法来查看还有多少可供存取的字符，因此，可以避免对`EOFException`的捕获和判断。但是，`available()`的工作方式会随着所读取的媒介类型的不同而有所不同，字面意思就是"在没有阻塞的情况下所能读取的字节数"。对于文件，意味着整个文件；对于不同类型的流，可能就不是这样的，因此要谨慎使用。虽然我们可以通过捕获异常来检测输入的末尾，但是，使用异常进行流控制，被认为是对异常特性的错误使用。此外，在 GitHub 的「[java-skills](https://github.com/guobinhit/java-skills)」项目中，给出了很多关于 Java I/O 流的典型使用方式，有需要的同学，可以自行获取。


## 新 I/O

JDK 1.4 的`java.nio.*`包中引入了新的的 Java I/O 类库，其目的在于提高速度。实际上，旧的 I/O 包已经使用`nio`重新实现过，以便充分利用这种速度提高，因此，即使我们不显式地用`nio`编写代码，也能从中受益。**速度的提高来自于使用的结构更接近于操作系统执行 I/O 的方式：通道和缓冲器**。通道要么从缓冲器获得数据，要么向缓冲器发送数据。**唯一直接与通道交互的缓冲器是`ByteBuffer`，一个可以存储未加工字节的缓冲器 **。

当我们查询 JDK  文档中的`java.nio.ByteBuffer`时，会发现它是相当基础的类：通过告知分配多少存储空间来创建一个`ByteBuffer`对象，并且还有一个方法选择集，用于以原始的字节形式或基本数据类型输出和读取数据。但是，没办法输出或读取对象，即使是字符串对象也不行。这种处理虽然低级，但却正好，因为这是大多数操作系统中更有效的映射方式。

旧 I/O 类库中有三个类被修改了，用以产生`FileChannel`，这三个被修改的类是`FileInputStream`、`FileOutputStream`以及用于既读又写的`RandomAccessFile`。注意这些是字节操纵流，与底层的`nio`性质一致。`Reader`和`Writer`这种字符模式类不能用于产生通道；但是`java.nio.channels.Channels`类提供了实用方法，用以在通道中产生`Reader`和`Writer`。下面的简单实例演示了上面三种类型的流，用以产生可写的、可读可写的及可读的通道：






----------

———— ☆☆☆ —— 返回 -> [The Skills of Java](https://github.com/guobinhit/java-skills/blob/master/README.md) <- 目录 —— ☆☆☆ ————
