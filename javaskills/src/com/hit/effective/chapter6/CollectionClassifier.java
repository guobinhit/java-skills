package com.hit.effective.chapter6;

import java.math.BigInteger;
import java.util.*;

/**
 * author:Charies Gavin
 * date:2018/6/9,10:52
 * https:github.com/guobinhit
 * description:重载方法
 */
public class CollectionClassifier {
    public static String classify(Set<?> set) {
        return "Set";
    }

    public static String classify(List<?> list) {
        return "list";
    }

    public static String classify(Collection<?> collection) {
        return "Unknown Collection";
    }

    public static void main(String[] args) {
        Collection<?>[] collections = {
                new HashSet<String>(),
                new ArrayList<BigInteger>(),
                new HashMap<String, String>().values()
        };

        /**
         * 因为重载方法的选择是静态的，是在编译时选择的，
         * 因此都执行了 classify(Collection<?> collection) 方法
         */
        for (Collection<?> collection : collections) {
            System.out.println(classify(collection));
        }
    }
}
