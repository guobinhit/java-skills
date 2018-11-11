# 编程思想 之「接口、内部类」

在 Java 的语言体系中，类和接口是两种常见的定义对象的形式，内部类则是类的一种特殊形式。接口和内部类为我们提供了一种将抽象定义与具体实现相分离的更加结构化的方法。

## 接口

我们通过方法来描述对象的行为，一般来说，它含有方法体并且在方法体中描述了对象具体的行为细节。但是，在 Java 中还提供了一种不完整的方法机制，称之为抽象方法。与一般的方法相比，**抽象方法仅有声明而没有方法体**。例如，

```
abstract void methodName();
```

包含抽象方法的类叫做抽象类。如果一个类包含一个或多个抽象方法，则该类必须被限制为抽象的，即用`abstract`关键字修饰类。否则的话，编译器就会报错！

如果我们细读上述的文字，会发现这样的一个细节，那就是：只要类中含有至少一个抽象方法，则该类就是抽象类；并没有限制抽象类中是否可以含有非抽象的方法，而且实际上抽象类中是允许含有非抽象方法的。**在接口中，则不允许含有非抽象的方法**！因此与抽象类相比，接口可以称之为“更加纯粹的抽象类”。接口也可以包含域，只不过这些域隐式地、自动的是`static`和`final`的。也正是由于这个原因，接口是一种很便捷的用来创建常量组的工具。

我们使用接口的原因有两个，分别为：

- 为了能够向上转型为多个基类型以及由此而带来的灵活性（核心原因）；
- 防止客户端程序员创建该类的对象（与抽象基类相同）。

如果要创建不带任何方法定义和成员变量的基类，那么就应该选择接口而不是抽象类。

此外，我们可以在类中定义接口，也可以在接口中定义接口，简而言之，我们可以进行接口的嵌套。不过在这里有一点需要我们注意，那就是：**当我们实现某个接口的时候，并不需要实现嵌套在其内部的任何接口，而且被`private`修饰的接口也不能在定义它的类之外被实现**。当然，我们也可以指定所要实现的定义在接口内部的接口，具体格式如下：

```
ClassName implements OuterInterfaceName.InnerInterfaceName
```


在 GitHub 的「[java-skills](https://github.com/guobinhit/java-skills)」项目中提供了测试的代码，感兴趣的同学可以自行下载体验。


## 内部类

内部类，其实就是将一个类定义在另一个类的内部，它允许我们将一些逻辑相关的类组织在一起。当生成一个内部类对象的时候，此对象与创建它的外围对象之间就建立了一种联系，它不需要任何特殊条件就能够访问外围对象的所有成员。此外，内部类还拥有其外围类的所有元素的访问权。

```
package com.hit.thought.chapter10;

/**
 * author:Charies Gavin
 * date:2018/3/2,9:23
 * https:github.com/guobinhit
 * description:测试内部类
 */
public class OuterClass {

    OuterClass() {
        System.out.println("Congratulations, Create OuterClass Completed!");
    }

    class InnerClass {
        InnerClass() {
            System.out.println("Congratulations, Create InnerClass Completed!");
        }

        public void sayHi() {
            System.out.println("Hi!");
        }

        /**
         * 在内部类中获取外部类对象，形式为 OuterClassName.this
         *
         * @return
         */
        public OuterClass getOuterClass() {
            return OuterClass.this;
        }
    }

    /**
     * 非静态方法
     */
    public void nonStaticMethod() {
        InnerClass innerClass = new InnerClass();
        innerClass.sayHi();
    }

    /**
     * 获取内部类的实例方法
     *
     * @return
     */
    public InnerClass getInnerClass() {
        return new InnerClass();
    }

    public static void main(String[] args) {
        OuterClass outerClass = new OuterClass();
        outerClass.nonStaticMethod();
        InnerClass innerClass = outerClass.getInnerClass();
        innerClass.sayHi();
        // 获取外部类对象的引用，并没有新建对象
        innerClass.getOuterClass();
        // 利用外部类对象直接创建内部类对象，使用 .new 语法
        InnerClass oi = outerClass.new InnerClass();
    }
}
```

如上面代码所示，

- 当我们需要生成对外部类对象的引用时，可以使用外部类的名字后面紧跟`.`和`this`；
- 当我们想要告知某些其他对象去创建其某个内部类的对象时，使用`.new`语法。

在这里，如果我们想要直接创建内部类对象的话，必须先创建外部类对象，然后通过外部类对象来创建内部类对象，**在拥有外部类对象之前是不可能创建内部类对象的**。但是，如果我们创建的时嵌套类（静态内部类），则不需要对外部类对象的引用。除了上面介绍的内部类之后，还有一种没有名字的内部类，我们称之为匿名内部类。参考如下代码：

```
public class OuterClass {

    OuterClass() {
        System.out.println("Congratulations, Create OuterClass Completed!");
    }

    /**
     * 非静态内部类
     */
    class InnerClass {
        InnerClass() {
            System.out.println("Congratulations, Create InnerClass Completed!");
        }

        public void sayHi() {
            System.out.println("Hi!");
        }

        /**
         * 在内部类中获取外部类对象，形式为 OuterClassName.this
         *
         * @return
         */
        public OuterClass getOuterClass() {
            return OuterClass.this;
        }
    }
    
   /**
     * 匿名类
     *
     * @return
     */
    public InnerClass anonymousInnerClass() {
        return new InnerClass() {
            public void sayHi() {
                System.out.println("Hello");
            }
        };
    }

    public static void main(String[] args) {
        OuterClass outerClass = new OuterClass();
        outerClass.anonymousInnerClass().sayHi();
    }
}
```
![outer-inner-class](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/interface-innerclass/outer-inner-class.png)

如上所示，我们在`anonymousInnerClass()`中创建了一个匿名内部类，并在其中定义了一个`sayHi()`方法。在这里，细心的同学可能会意识到：**匿名内部类，实际上就是继承了方法返回（类）类型的类而已**。既然继承了父类，自然也就可以覆盖父类中的方法，因此通过`anonymousInnerClass()`方法调用`sayHi()`方法的时候，返回的是子类（匿名内部类）中的版本。

在匿名内部类末尾的分号，并不是用来标记此内部类结束的。实际上，它标记的表达式的结束，只不过这个表达式恰巧包含了匿名内部类罢了。**如果定义一个匿名内部类，并且希望它使用一个在其外部定义的对象，那么编译器会要求其参数引用是`final`的**。例如，

```
/**
 * 在匿名内部类中引用外部参数时，必须将参数定义为 final 类型
 *
 * @return
 */
public InnerClass anonymousInnerClass2(final String name) {
	return new InnerClass() {
		public void sayHi() {
			System.out.println("Hello " + name);
        }
    };
}
```
如果不加`final`的话，我们就会得到一条编译器给出的错误提示：

![anonymous-inner-class2](https://github.com/guobinhit/java-skills/blob/master/images/programming-thought/interface-innerclass/anonymous-inner-class2.png)

此外，在匿名内部类中不可能有命名的构造器，因为它根本就没有名字！对于匿名内部类而言，实例初始化的实际效果就是构造器。**匿名内部类既可以扩展类，也可以实现接口，但不能两者兼备**。

如果不需要内部类对象与外围类对象之间有联系，那么可以将内部类声明为`static`类型，这就是我们所说的嵌套类。我们知道，普通的内部类对象隐式的保持了一个指向创建它的外围类对象的引用，但是对于嵌套类而言，并非如此：

- 要创建嵌套类对象，并不需要其外围类的对象；
- 不能从嵌套类的对象中访问非静态的外围类对象。

普通内部类和嵌套类还有一些区别，例如：

- 在普通内部类中，我们可以通过一个特殊的`this`关键字可以引用到外围类对象，但是嵌套类不可以；
- 普通内部类的字段与方法，只能放在类的外部层次上，所以普通的内部类不能有`static`数据和`static`字段，也不能包含嵌套类，但是嵌套类可以。

正常情况下，不能在接口内部放置任何代码，但是嵌套类可以作为接口的一部分。代码示例如下，

```
public interface ClassInInterface {
    void interfaceMethod();
    class InnerClassInInterface{
        void sayHello(){
            System.out.println("Hello World!");
        }

        public static void main(String[] args) {
            new InnerClassInInterface().sayHello();
        }
    }
}
```

每个内部类都能独立地继承自一个（接口的）实现，所以无论外围类是否已经继承了某个（接口的）实现，对于内部类都没有影响。而且，内部类允许继承多个类或者抽象类。当然，我们也可以继承内部类，不过在继承内部类的时候，那个指向外围类的“隐式的”引用必须被初始化，而在导出类中不再存在可以连接的默认对象。此外，在继承内部类的时候，还有一点需要特别注意，那就是：**我们不能在导出类中使用默认的构造器（不能编译），需要新建一个带有所继承的内部类所在外部类参数类型的构造器并显示调用外部类构造器的构造器**。好吧，我承认，上面的话说完之后我自己都有点晕，给个例子，相信大家看过之后就明白了：

```
class ContainInner {
    class Inner {
        public void sayName() {
            // 获取类名
            System.out.println("调用此方法的类名为：" + this.getClass().getSimpleName());
        }
    }
}

public class InheritInnerClass extends ContainInner.Inner {
    /**
     * 继承内部类，必须调用含参构造器，且参数类型为外部类类型
     *
     * @param ci
     */
    InheritInnerClass(ContainInner ci) {
        // 必须显示调用外部类构造器
        ci.super();
    }

    public static void main(String[] args) {
        ContainInner ci = new ContainInner();
        InheritInnerClass iic = new InheritInnerClass(ci);
        iic.sayName();
    }
}
```
如果上面代码所示，`InheritInnerClass`继承了内部类`Inner`，并没有继承外部类`ContainInner`，但是我们必须在`InheritInnerClass`的中新建一个以`ContainInner`类型为参数的构造器，且必须使用`super()`函数显示调用`ContainInner`的构造器。否则的话，代码将不能通过编译器。

我们也可以在代码块里面建立内部类，比较典型的是在方法里面建立内部类，称之为局部内部类。局部内部类和匿名内部类具有相同的行为和能力，两者唯一的区别或许就是局部内部类比匿名内部类多个名字啦！也正是基于此，使用局部内部类而不是匿名类内部类的理由是：

- 需要不止一个该内部类对象；
- 需要一个已命名的构造器，或者需要需要重载构造器。

此外，内部类编译后产生的`.class`文件的命名规则也是有严格规定的，格式为`OuterClassName$InnerClassName.class`，如果内部类是匿名的，编译器就会简单地产生一个数字作为其标识符，并替换上述格式中的`InnerClassName`位置。


----------

———— ☆☆☆ —— 返回 -> [The Skills of Java](https://github.com/guobinhit/java-skills/blob/master/README.md) <- 目录 —— ☆☆☆ ————
