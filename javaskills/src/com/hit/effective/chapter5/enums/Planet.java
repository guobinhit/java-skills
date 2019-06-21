package com.hit.effective.chapter5.enums;

/**
 * author:Charies Gavin
 * date:2018/6/6,8:44
 * https:github.com/guobinhit
 * description:枚举类型中定义常量域
 */
public enum Planet {
    MERCURY(3.302e+23, 2.439e6),
    VENUS(4.869e+24, 6.052e6),
    EARTH(5.975e+23, 6.378e6),
    MARS(6.419e+23, 3.393e6),
    JUPITER(1.899e+27, 7.149e7),
    SATURN(5.685e+26, 6.027e7),
    URANUS(8.683e+25, 2.556e7),
    NEPTUNE(1.024e+26, 2.477e7);

    // In kilograms
    private final double mass;
    // In meters
    private final double radius;
    // In m / s^2
    private final double surfaceGravity;

    // Universal gravitational constant in m^3 / kg s^2
    private static final double G = 6.67300E-11;

    // constructor
    Planet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
        surfaceGravity = G * mass / (radius * radius);
    }

    public double getMass() {
        return mass;
    }

    public double getRadius() {
        return radius;
    }

    public double getSurfaceGravity() {
        return surfaceGravity;
    }

    public double surfaceWeight(double mass) {
        // F = ma
        return mass * surfaceGravity;
    }

    public static void main(String[] args) {
        System.out.println(EARTH.getMass());
        System.out.println(EARTH.getRadius());
        System.out.println(EARTH.getSurfaceGravity());
        System.out.println(EARTH.surfaceWeight(EARTH.getMass()));
    }
}
