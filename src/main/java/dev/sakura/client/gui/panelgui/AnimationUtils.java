package dev.sakura.client.gui.panelgui;

public final class AnimationUtils {
    private AnimationUtils() {
    }

    public static float getAnimationState(float animation, float finalState, float speed, int deltaMs) {
        float add = deltaMs * (speed / 1000.0f);
        if (animation < finalState) {
            if (animation + add < finalState) {
                animation += add;
            } else {
                animation = finalState;
            }
        } else if (animation - add > finalState) {
            animation -= add;
        } else {
            animation = finalState;
        }
        return animation;
    }
}

