package dev.mzc.client.render.smoothswap;

import java.util.ArrayList;
import java.util.List;

public class CatmullRomUtil {

    public static List<CatmullRomSpline> getDefaultSplines() {
        List<Vec2> points = new ArrayList<>();
        points.add(new Vec2(0, 0));
        points.add(new Vec2(0, 0));
        points.add(new Vec2(1, 1));
        points.add(new Vec2(1, 1));
        return splinesFromPoints(points);
    }

    public static List<CatmullRomSpline> splinesFromPoints(List<Vec2> points) {
        List<CatmullRomSpline> splines = new ArrayList<>();

        for (int i = 1; i < points.size() - 2; i++) {
            Vec2 p0 = points.get(i - 1);
            Vec2 p1 = points.get(i);
            Vec2 p2 = points.get(i + 1);
            Vec2 p3 = points.get(i + 2);
            splines.add(new CatmullRomSpline(p0, p1, p2, p3));
        }
        return splines;
    }

    public static double getProgress(double t, List<CatmullRomSpline> segments) {
        CatmullRomSpline currentSegment = getSegmentForT(t, segments);

        double progress = SwapUtil.map(t, currentSegment.oldX, currentSegment.x, 1, 0);

        return currentSegment.getSegment().getPoint(progress).v[1];
    }

    private static CatmullRomSpline getSegmentForT(double t, List<CatmullRomSpline> segments) {
        for (CatmullRomSpline spline : segments) {
            if (t >= spline.oldX && t < spline.x) {
                return spline;
            }
        }
        return segments.get(0);
    }
}
