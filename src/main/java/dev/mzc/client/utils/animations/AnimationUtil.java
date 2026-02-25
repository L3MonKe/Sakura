package dev.mzc.client.utils.animations;

import dev.mzc.client.utils.math.FrameRateCounter;
import dev.mzc.client.utils.math.MathUtil;

public class AnimationUtil {
    public static float deltaTime() {
        return FrameRateCounter.INSTANCE.getFps() > 5 ? (1f / FrameRateCounter.INSTANCE.getFps()) : 0.016f;
    }

    public static float fast(float end, float start, float multiple) {
        float clampedDelta = MathUtil.clamp(deltaTime() * multiple, 0f, 1f);
        return (1f - clampedDelta) * end + clampedDelta * start;
    }
}
