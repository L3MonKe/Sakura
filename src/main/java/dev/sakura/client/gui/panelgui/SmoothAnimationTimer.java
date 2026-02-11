package dev.sakura.client.gui.panelgui;

public class SmoothAnimationTimer {
    public float target;
    public float speed = 0.4f;
    public float value;

    private long lastUpdateMs = System.currentTimeMillis();

    public SmoothAnimationTimer(float target) {
        this.target = target;
        this.value = target;
    }

    public SmoothAnimationTimer(float target, float value) {
        this.target = target;
        this.value = value;
    }

    public SmoothAnimationTimer(float target, float value, float speed) {
        this.target = target;
        this.value = value;
        this.speed = speed;
    }

    public void update(boolean increment) {
        float desired = increment ? target : 0.0f;
        long now = System.currentTimeMillis();
        int deltaMs = (int) (now - lastUpdateMs);
        if (deltaMs < 0) {
            deltaMs = 0;
        }
        lastUpdateMs = now;
        float dynamicSpeed = Math.max(10.0f, Math.abs(value - desired) * 40.0f) * speed;
        value = AnimationUtils.getAnimationState(value, desired, dynamicSpeed, deltaMs);
    }

    public boolean isAnimationDone(boolean increment) {
        return increment ? value == target : value == 0.0f;
    }
}
