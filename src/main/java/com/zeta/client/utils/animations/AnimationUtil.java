package com.zeta.client.utils.animations;

import com.zeta.client.utils.math.FrameRateCounter;
import net.minecraft.util.math.MathHelper;

public class AnimationUtil {
    public static float deltaTime() {
        return FrameRateCounter.INSTANCE.getFps() > 5 ? (1f / FrameRateCounter.INSTANCE.getFps()) : 0.016f;
    }

    public static float fast(float end, float start, float multiple) {
        float clampedDelta = MathHelper.clamp(deltaTime() * multiple, 0f, 1f);
        return (1f - clampedDelta) * end + clampedDelta * start;
    }

    public static float smoothstep(float a, float b, float x) {
        if (a == b) return x >= b ? 1f : 0f;
        float t = MathHelper.clamp((x - a) / (b - a), 0f, 1f);
        return t * t * (3f - 2f * t);
    }

    public static float easeOutCubic(float t) {
        t = MathHelper.clamp(t, 0f, 1f);
        float inv = 1f - t;
        return 1f - inv * inv * inv;
    }

    public static float easeInOutCubic(float t) {
        t = MathHelper.clamp(t, 0f, 1f);
        return t < 0.5f ? 4f * t * t * t : 1f - (float) Math.pow(-2f * t + 2f, 3f) / 2f;
    }

    public static float easeOutExpo(float t) {
        t = MathHelper.clamp(t, 0f, 1f);
        return t >= 1f ? 1f : 1f - (float) Math.pow(2.0, -10.0 * t);
    }
}
