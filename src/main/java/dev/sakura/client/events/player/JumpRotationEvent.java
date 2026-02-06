package dev.sakura.client.events.player;

import dev.sakura.client.events.Cancellable;

public class JumpRotationEvent extends Cancellable {
    private float yaw;

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getYaw() {
        return this.yaw;
    }

    public JumpRotationEvent(float yaw) {
        this.yaw = yaw;
    }
}
