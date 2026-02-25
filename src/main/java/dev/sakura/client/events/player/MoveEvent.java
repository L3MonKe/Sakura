package dev.sakura.client.events.player;

import dev.sakura.client.events.Cancellable;
import net.minecraft.util.math.Vec3d;

public class MoveEvent extends Cancellable {
    private double x;
    private double y;
    private double z;

    public MoveEvent(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public MoveEvent(Vec3d vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Vec3d getVec() {
        return new Vec3d(x, y, z);
    }

    public void setVec(Vec3d vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
    }
}