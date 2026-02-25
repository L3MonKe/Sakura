package dev.mzc.client.utils.vector;


public final class Rotation {
    public float yaw, pitch;

    public Rotation(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Rotation(Rotation vector) {
        this(vector.yaw, vector.pitch);
    }

    public Rotation add(float x, float y) {
        return new Rotation(this.yaw + x, this.pitch + y);
    }
}
