package com.hit.concurrent.chapter2;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * author:Charies Gavin
 * date:2018/11/3,13:31
 * https:github.com/guobinhit
 * description:安全性委托
 */
public class PersonSet {
    private final Set<Person> mySet = new HashSet<Person>();

    private final Set<Person> safeMySet = Collections.synchronizedSet(mySet);

    public void addPerson(Person p) {
        safeMySet.add(p);
    }

    public boolean containsPerson(Person p) {
        return safeMySet.contains(p);
    }
}

class Person {
}
