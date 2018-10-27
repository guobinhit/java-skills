package com.hit.thought.chapter15.mapself;

import java.util.*;

/**
 * author:Charies Gavin
 * date:2018/5/5,13:00
 * https:github.com/guobinhit
 * description:SlowMap
 */
public class SlowMap<K, V> extends AbstractMap<K, V> {
    private List<K> keys = new ArrayList<K>();
    private List<V> values = new ArrayList<V>();

    public V put(K key, V value) {
        // the old value or null
        V oldValue = get(key);
        if (!keys.contains(key)) {
            keys.add(key);
            values.add(value);
        } else {
            values.set(keys.indexOf(key), value);
        }
        return oldValue;
    }

    /**
     * key is type Object, not K
     *
     * @param key
     * @return value
     */
    public V get(Object key) {
        if (!keys.contains(key)) {
            return null;
        } else {
            return values.get(keys.indexOf(key));
        }
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new HashSet<Map.Entry<K, V>>();
        Iterator<K> ki = keys.iterator();
        Iterator<V> vi = values.iterator();
        while (ki.hasNext()) {
            set.add(new MapEntry<K, V>(ki.next(), vi.next()));
        }
        return set;
    }

    public static void main(String[] args) {
        SlowMap<String, String> slowMap = new SlowMap<String, String>();
        slowMap.put("Beijing", "Beijing");
        slowMap.put("Heilongjiang", "Harbin");
        slowMap.put("Hebei", "Shijiazhuang");
        System.out.println(slowMap);
        System.out.println(slowMap.get("Beijing"));
        System.out.println(slowMap.get(""));
        System.out.println(slowMap.entrySet());
        System.out.println(slowMap.put("Hebei", "Hengshui"));
        System.out.println(slowMap);
    }
}
