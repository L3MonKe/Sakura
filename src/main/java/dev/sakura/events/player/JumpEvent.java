package dev.sakura.events.player;

import dev.sakura.events.Cancellable;

/**
 * @Author：jiuxian_baka
 * @Date：2025/12/17 23:39
 * @Filename：JumpEvent
 */
public class JumpEvent extends Cancellable {
    private float yaw;

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getYaw() {
        return this.yaw;
    }

    public JumpEvent(float yaw) {
        this.yaw = yaw;
    }
}
