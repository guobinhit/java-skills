package com.hit.thought.chapter15.mapself;

import java.util.*;

/**
 * author:Charies Gavin
 * date:2018/5/5,14:15
 * https:github.com/guobinhit
 * description:SimpleHashMap
 */
public class SimpleHashMap<K, V> extends AbstractMap<K, V> {
    /**
     * choose a prime number for the hash table size
     * to achieve a uniform distribution
     */
    static final int SIZE = 997;

    @SuppressWarnings("unchecked")
    private LinkedList<MapEntry<K, V>>[] buckets = new LinkedList[SIZE];

    public V put(K key, V value) {
        V oldValue = null;
        // get key index, "Math.abs(key.hashCode()) % SIZE" can be called hash function
        int index = Math.abs(key.hashCode()) % SIZE;
        if (buckets[index] == null) {
            // buckets's element is a list
            buckets[index] = new LinkedList<MapEntry<K, V>>();
        }
        LinkedList<MapEntry<K, V>> bucket = buckets[index];
        MapEntry<K, V> pair = new MapEntry<K, V>(key, value);
        // initial a boolean sign
        boolean found = false;
        // get bucket's iterator, bucket is a list
        ListIterator<MapEntry<K, V>> it = bucket.listIterator();
        while (it.hasNext()) {
            MapEntry<K, V> iPair = it.next();
            if (iPair.getKey().equals(key)) {
                oldValue = iPair.getValue();
                // replace old with new
                it.set(pair);
                found = true;
                break;
            }
        }
        /**
         * if found is true, it's mean this.key already include by one of element of buckets,
         * and old value is already replace whit new value, so !found is false, pass;
         *
         * otherwise, it's mean this.key a new element of buckets, so execute sentence below
         */
        if (!found) {
            buckets[index].add(pair);
        }
        return oldValue;
    }

    public V get(Object key) {
        int index = Math.abs(key.hashCode()) % SIZE;
        if (buckets[index] == null) {
            return null;
        }
        /**
         * if procedure execute this sentence, it's mean this.key has a corresponding index,
         * so, firstly, get list corresponding of index
         */
        for (MapEntry<K, V> iPair : buckets[index]) {
            // secondly, check key and get value
            if (iPair.getKey().equals(key)) {
                return iPair.getValue();
            }
        }
        return null;
    }


    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new HashSet<Map.Entry<K, V>>();
        for (LinkedList<MapEntry<K, V>> bucket : buckets) {
            if (bucket == null) {
                continue;
            }
            for (MapEntry<K, V> mpair : bucket) {
                set.add(mpair);
            }
        }
        return set;
    }

    public static void main(String[] args) {
        SimpleHashMap<String, String> simpleHashMap = new SimpleHashMap<String, String>();
        simpleHashMap.put("Beijing", "Beijing");
        simpleHashMap.put("Heilongjiang", "Harbin");
        simpleHashMap.put("Hebei", "Shijiazhuang");
        System.out.println(simpleHashMap);
        System.out.println(simpleHashMap.get("Beijing"));
        System.out.println(simpleHashMap.get(""));
        System.out.println(simpleHashMap.entrySet());
        System.out.println(simpleHashMap.put("Hebei", "Hengshui"));
        System.out.println(simpleHashMap);
    }
}
