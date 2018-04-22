package com.hit.thought.chapter14;

import java.util.Arrays;
import java.util.Comparator;

/**
 * author:Charies Gavin
 * date:2018/4/22,10:45
 * https:github.com/guobinhit
 * description:实现Comparator接口
 */
public class ComparatorImpl implements Comparator<Person> {

    @Override
    public int compare(Person p1, Person p2) {
        return p1.age < p2.age ? -1 : (p1.age == p2.age ? 0 : 1);
    }

    public static void main(String[] args) {
        Person p1 = new Person("gavin", 28);
        Person p2 = new Person("zora", 27);

        Person[] lover = {p1, p2};

        for (Person person : lover) {
            System.out.println(person.name + " : " + person.age);
        }

        System.out.println("----- 排序后 -----");

        Arrays.sort(lover, new ComparatorImpl());

        for (Person person : lover) {
            System.out.println(person.name + " : " + person.age);
        }
    }
}

class Person {
    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
