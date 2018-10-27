package com.hit.thought.chapter15;

/**
 * author:Charies Gavin
 * date:2018/5/5,10:00
 * https:github.com/guobinhit
 * description:土拨鼠
 */
public class Groundhog {
    protected int number;

    public Groundhog(int n) {
        number = n;
    }

    @Override
    public String toString() {
        return "Groundhog #" + number;
    }
}
