package com.hit.thought.chapter13;

/**
 * author:Charies Gavin
 * date:2018/4/21,16:30
 * https:github.com/guobinhit
 * description:测试擦除的边界
 */
public class Manipulation {
    public static void main(String[] args) {
        Erased erased = new Erased();

        Manipulator<Erased> manipulator = new Manipulator<Erased>(erased);
        manipulator.manipulator();

        /**
         * 泛型类型参数将擦除到它的第一个边界，编译器实际上会把类型参数替换为它的擦除
         * 例如，Manipulator2<Erased, Erased2> 将擦除到 Erased
         */
        Manipulator2<Erased, Erased2> manipulator2 = new Manipulator2<Erased, Erased2>(erased);
        manipulator2.manipulator();
    }
}

class Erased {
    public void erased() {
        System.out.println("Erased: erasing...");
    }
}

class Erased2 {
    public void erased() {
        System.out.println("Erased2: erasing...");
    }
}

class Manipulator<T> {
    private T obj;

    public Manipulator(T t) {
        obj = t;
    }

    public void manipulator() {
//        obj.erased();
    }
}

/**
 * 指定泛型边界
 *
 * @param <T>
 */
class Manipulator2<T extends Erased, Erased2> {
    private T obj;

    public Manipulator2(T t) {
        obj = t;
    }

    public void manipulator() {
        obj.erased();
    }
}