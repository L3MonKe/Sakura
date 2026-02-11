package dev.sakura.client.event.impl.entity;

import dev.sakura.client.event.Cancellable;

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
