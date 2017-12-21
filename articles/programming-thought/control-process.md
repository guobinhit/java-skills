# 编程思想 之「控制流程」

在 Java 中，`if-else`是最基本的控制程序流程的形式，例如

```
if(boolean-expression)
	statement
else
	statement
```
其中`else`是可选的，如果省略`else`，则简化为如下形式：
```
if(boolean-expression)
	statement
```


### 迭代

常见的迭代形式有三种，分别为`for`、`while`和`do-while`，其中**`while`和`do-while`的唯一区别就是`do-while`至少会执行一次循环**。在一个控制表达式中，**只有`for`循环可以定义多个变量（且变量的生存周期为循环体内部）**，在其他任何选择或者迭代语句中都不能使用这种方式。

在这里，着重介绍`for`循环，其有两种形式，一种是普通的`for`循环，其形式如下：

```
for(initialization; boolean-expression; step) {
    statement
}
```
另一种是高级的`for`循环，我们也称之为`foreach`循环，其形式如下：

```
for(variable-type variable-name : collection) {
    statement
}
```


----------


- **未完待续。。。。**



----------

———— ☆☆☆ —— 返回 -> [那些年，关于 Java 的那些事儿](https://github.com/guobinhit/java-skills/blob/master/README.md) <- 目录 —— ☆☆☆ ————
