package com.hit.thought.chapter14;

import java.util.ArrayList;
import java.util.List;

/**
 * author:Charies Gavin
 * date:2018/4/22,11:10
 * https:github.com/guobinhit
 * description:泛型和向上转型
 */
public class GenericAndUpcasting {
    public static void main(String[] args) {
        List<Animal> animals = new ArrayList<Animal>();

        animals.add(new Cat());
        animals.add(new Dog());
        animals.add(new Tiger());

        for (Animal animal : animals) {
            System.out.println(animal);
        }
    }
}

class Animal {
}

class Cat extends Animal {
}

class Dog extends Animal {
}

class Tiger extends Animal {
}
