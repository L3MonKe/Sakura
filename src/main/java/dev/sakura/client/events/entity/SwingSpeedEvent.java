package dev.sakura.client.events.entity;

import dev.sakura.client.events.Cancellable;

public final class SwingSpeedEvent extends Cancellable {
    int swingSpeed;
    boolean selfOnly;

    public void setSwingSpeed(int swingSpeed) {
        this.swingSpeed = swingSpeed;
    }

    public int getSwingSpeed() {
        return swingSpeed;
    }

    public void setSelfOnly(boolean selfOnly) {
        this.selfOnly = selfOnly;
    }

    public boolean getSelfOnly() {
        return selfOnly;
    }
}
