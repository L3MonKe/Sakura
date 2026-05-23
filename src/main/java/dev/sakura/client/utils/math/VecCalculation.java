package dev.sakura.client.utils.math;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class VecCalculation {

    public static Box getHitBox(Entity entity) {
        double borderSize = entity.getTargetingMargin();
        Box box = entity.getBoundingBox();
        return box.expand(borderSize);
    }

    public static Vec3d getNearestPointBB(Vec3d eye, Box box) {
        double x = eye.x;
        double y = eye.y;
        double z = eye.z;

        if (x > box.maxX) x = box.maxX;
        else if (x < box.minX) x = box.minX;

        if (y > box.maxY) y = box.maxY;
        else if (y < box.minY) y = box.minY;

        if (z > box.maxZ) z = box.maxZ;
        else if (z < box.minZ) z = box.minZ;

        return new Vec3d(x, y, z);
    }

    public static double getDistanceToEntityBox(Entity from, Entity to) {
        Vec3d eyes = from.getEyePos();
        Vec3d nearestPoint = getNearestPointBB(eyes, getHitBox(to));
        return eyes.distanceTo(nearestPoint);
    }
}
