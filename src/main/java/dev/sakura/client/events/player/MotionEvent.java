package dev.sakura.client.events.player;

import dev.sakura.client.events.Cancellable;
import dev.sakura.client.events.EventType;

public class MotionEvent extends Cancellable {
    private final EventType type;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private boolean onGround;
    private boolean shouldSendExtraC03;

    public MotionEvent(EventType type, float yaw, float pitch) {
        this.type = type;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public EventType getType() {
        return this.type;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public boolean isShouldSendExtraC03() {
        return shouldSendExtraC03;
    }

    public void setShouldSendExtraC03(boolean shouldSendExtraC03) {
        this.shouldSendExtraC03 = shouldSendExtraC03;
    }

    public MotionEvent(EventType type, double x, double y, double z, float yaw, float pitch, boolean onGround) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
        this.shouldSendExtraC03 = false;
    }
}
