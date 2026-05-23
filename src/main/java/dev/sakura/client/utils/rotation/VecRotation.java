package dev.sakura.client.utils.rotation;

import net.minecraft.util.math.Vec3d;

public class VecRotation {
    public Vec3d vec;
    public Rotation rotation;

    public VecRotation(Vec3d vec, Rotation rotation) {
        this.vec = vec;
        this.rotation = rotation;
    }
}
