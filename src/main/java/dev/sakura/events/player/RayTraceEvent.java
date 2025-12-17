package dev.sakura.events.player;

import dev.sakura.events.Cancellable;
import net.minecraft.entity.Entity;

/**
 * @Author：jiuxian_baka
 * @Date：2025/12/17 23:10
 * @Filename：RayTraceEvent
 */
public class RayTraceEvent extends Cancellable {

    public Entity entity;
    public float yaw;
    public float pitch;

    public Entity getEntity() {
        return this.entity;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public RayTraceEvent(Entity entity, float yaw, float pitch) {
        this.entity = entity;
        this.yaw = yaw;
        this.pitch = pitch;
    }
}
