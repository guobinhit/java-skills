package com.hit.effective.chapter7;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * author:Charies Gavin
 * date:2018/6/11,8:47
 * https:github.com/guobinhit
 * description:For-each循环
 */
public class ForEachCircle {
    enum Face {ONE, TWO, THREE, FOUR, FIVE, SIX}

    public static void main(String[] args) {
        Collection<Face> faces = Arrays.asList(Face.values());

        // 包含一个BUG
        for (Iterator<Face> i = faces.iterator(); i.hasNext(); ) {
            for (Iterator<Face> j = faces.iterator(); j.hasNext(); ) {
                System.out.println(i.next() + " " + j.next());
            }
        }

        System.out.println("+++ 上面有Bug，下面为修复版 1 +++");

        // 修复版 1
        for (Iterator<Face> i = faces.iterator(); i.hasNext(); ) {
            Face tempFace = i.next();
            for (Iterator<Face> j = faces.iterator(); j.hasNext(); ) {
                System.out.println(tempFace + " " + j.next());
            }
        }

        System.out.println("+++ 下面为修复版 2 +++");

        // 修复版 2
        for (Face face1 : faces) {
            for (Face face2 : faces) {
                System.out.println(face1 + " " + face2);
            }
        }
    }
}