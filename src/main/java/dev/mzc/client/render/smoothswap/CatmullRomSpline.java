package dev.mzc.client.render.smoothswap;

import dev.mzc.client.render.smoothswap.Vec2;

public class CatmullRomSpline {
    static float tension = 0f;
    static float alpha = 0f;

    private final Segment segment;

    public float x, oldX;

    public CatmullRomSpline(Vec2 p0, Vec2 p1, Vec2 p2, Vec2 p3) {

        x = (float) p2.v[0];
        oldX = (float) p1.v[0];

        double t01 = Math.pow(Vec2.distance(p0, p1), alpha);
        double t12 = Math.pow(Vec2.distance(p1, p2), alpha);
        double t23 = Math.pow(Vec2.distance(p2, p3), alpha);

        Vec2 m1 = Vec2.sum(Vec2.diff(p2, p1), (Vec2.diff((Vec2.diff(p1, p0).divideScalar(t01)), (Vec2.diff(p2, p0).divideScalar(t01 + t12)))).multiplyScalar(t12)).multiplyScalar(1.0f - tension);
        Vec2 m2 = Vec2.sum(Vec2.diff(p2, p1), (Vec2.diff((Vec2.diff(p3, p2).divideScalar(t23)), (Vec2.diff(p3, p1).divideScalar(t12 + t23)))).multiplyScalar(t12)).multiplyScalar(1.0f - tension);

        segment = new Segment(Vec2.sum(Vec2.diff(p1, p2).multiplyScalar(2.0d), Vec2.sum(m1, m2)),
                Vec2.diff(Vec2.diff(Vec2.diff(Vec2.diff(p1, p2).multiplyScalar(-3), m1), m1), m2),
                m1,
                p1);
    }

    public Segment getSegment() {
        return this.segment;
    }


    public record Segment(Vec2 a, Vec2 b, Vec2 c, Vec2 d) {

        public Vec2 getPoint(double t) {
            return Vec2.sum(a.copy().multiplyScalar(t * t * t).copy(), Vec2.sum(b.copy().multiplyScalar(t * t).copy(), Vec2.sum(c.copy().multiplyScalar(t).copy(), d)));
        }
    }
}
