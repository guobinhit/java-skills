package com.hit.thought.chapter15;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * author:Charies Gavin
 * date:2018/5/5,10:30
 * https:github.com/guobinhit
 * description:春天探测
 */
public class SpringDetector {
    /**
     * 由于通过反射获取类构造器的时候，有可能出现 NoSuchMethodException，因此直接在方法定义上进行声明
     *
     * @param type
     * @param <T>
     * @throws Exception
     */
    public static <T extends Groundhog> void detectSpring(Class<T> type) throws Exception {
        // 通过反射获取类构造器，有可能出现 NoSuchMethodException
        Constructor<T> ghot = type.getConstructor(int.class);

        Map<Groundhog, Prediction> map = new HashMap<Groundhog, Prediction>();

        for (int i = 0; i < 10; i++) {
            map.put(ghot.newInstance(i), new Prediction());
        }

        // 格式化打印的格式
        for (Groundhog groundhog : map.keySet()) {
            System.out.println(groundhog + " : " + map.get(groundhog));
        }

        // 又创建了一个 Groundhog 对象
        Groundhog gh = ghot.newInstance(3);

        System.out.println("Looking up prediction for " + gh);

        if (map.containsKey(gh)) {
            System.out.println(map.get(gh));
        } else {
            System.out.println("Key not found: " + gh);
        }
    }

    public static void main(String[] args) throws Exception {
        // 由于 Groundhog 未覆盖 equals() 方法，因此默认比较对象的地址
        detectSpring(Groundhog.class);

        System.out.println("------ ****** ------");

        // 在 Groundhot2 中覆盖了 equals() 方法，比较对象 number 的值
        detectSpring(Groundhot2.class);
    }
}
