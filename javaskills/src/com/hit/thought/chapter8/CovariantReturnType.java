package com.hit.thought.chapter8;

/**
 * author:Charies Gavin
 * date:2018/1/4,22:10
 * https:github.com/guobinhit
 * description:测试协变返回类型
 */
public class CovariantReturnType {
    public static void main(String[] args) {
        Flower flower = new Flower();
        Plant plant = flower.kind();
        System.out.println("未使用协变返回类型：" + plant);
        // 使用协变返回类型
        flower = new Luoyangred();
        plant = flower.kind();
        System.out.println("使用协变返回类型后：" + plant);
    }
}

/**
 * 植物基类
 */
class Plant {
    public String toString() {
        return "Plant";
    }
}

/**
 * 牡丹花，继承自植物基类
 */
class Peony extends Plant {
    public String toString() {
        return "Peony";
    }
}

/**
 * 花
 */
class Flower {
    Plant kind() {
        return new Plant();
    }
}

/**
 * 洛阳红，十大贵品牡丹花之一
 */
class Luoyangred extends Flower {
    Peony kind() {
        return new Peony();
    }
}
