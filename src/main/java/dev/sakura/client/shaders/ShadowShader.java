package dev.sakura.client.shaders;

import dev.sakura.client.shaders.program.ShadowProgram;

import java.awt.*;

public class ShadowShader {
    private static final ShadowProgram SHADOW_PROGRAM = new ShadowProgram();

    public static void drawStairShadow(float x, float y, float width, float height, float range, float strength, Color color, float[] segmentRects, float[] segmentRadii, int segmentCount) {
        strength = Math.max(0f, Math.min(1f, strength));
        range = Math.max(0f, range);
        SHADOW_PROGRAM.renderStairShadow(x, y, width, height, range, strength, color, segmentRects, segmentRadii, segmentCount);
    }

    public static void drawStairShadowGradient(float x, float y, float width, float height, float range, float strength, Color startColor, Color endColor, float[] segmentRects, float[] segmentRadii, int segmentCount) {
        strength = Math.max(0f, Math.min(1f, strength));
        range = Math.max(0f, range);
        SHADOW_PROGRAM.renderStairShadow(x, y, width, height, range, strength, startColor, endColor, true, segmentRects, segmentRadii, segmentCount);
    }
}
