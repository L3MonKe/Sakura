package dev.mzc.client.render.smoothswap;

import org.jetbrains.annotations.NotNull;

public class Vec2 implements Comparable<Vec2> {
    public double[] v = new double[2];

    public Vec2(double x, double y) {
        v[0] = x;
        v[1] = y;
    }

    public static double distance(Vec2 v1, Vec2 v2) {
        return Math.sqrt(Math.pow(v1.v[0] - v2.v[0], 2) + Math.pow(v1.v[1] - v2.v[1], 2));
    }

    public static Vec2 sum(Vec2 v1, Vec2 v2) {
        return new Vec2(v1.v[0] + v2.v[0], v1.v[1] + v2.v[1]);
    }

    public static Vec2 diff(Vec2 v1, Vec2 v2) {
        return new Vec2(v1.v[0] - v2.v[0], v1.v[1] - v2.v[1]);
    }

    public Vec2 multiplyScalar(double scalar) {
        this.v[0] *= scalar;
        this.v[1] *= scalar;
        return this;
    }

    public Vec2 divideScalar(double scalar) {
        this.v[0] /= scalar;
        this.v[1] /= scalar;
        return this;
    }

    public Vec2 copy() {
        return new Vec2(this.v[0], this.v[1]);
    }

    @Override
    public int compareTo(@NotNull Vec2 o) {
        return Double.compare(this.v[0], o.v[0]);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vec2 vec2 = (Vec2) o;
        return Double.compare(vec2.v[0], v[0]) == 0 && Double.compare(vec2.v[1], v[1]) == 0;
    }
}
