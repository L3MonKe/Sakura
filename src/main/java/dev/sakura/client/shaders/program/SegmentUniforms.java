package dev.sakura.client.shaders.program;

import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;

final class SegmentUniforms {
    static final int MAX_SEGMENTS = 64;

    private SegmentUniforms() {
    }

    static int uniformSize() {
        Std140SizeCalculator calc = new Std140SizeCalculator().putVec4().putVec4().putVec4().putVec4().putVec4();
        for (int i = 0; i < MAX_SEGMENTS; i++) {
            calc.putVec4();
        }
        for (int i = 0; i < MAX_SEGMENTS; i++) {
            calc.putVec4();
        }
        return calc.get();
    }

    static int clampCount(int segmentCount) {
        return Math.max(0, Math.min(MAX_SEGMENTS, segmentCount));
    }

    static void computeVerticalBounds(float scale, float scaledHeight, float fallbackPxY, float fallbackPxH, float[] segmentRects, int count, float[] outTopBottom) {
        float topY = Float.POSITIVE_INFINITY;
        float bottomY = Float.NEGATIVE_INFINITY;
        for (int i = 0; i < count; i++) {
            int base = i * 4;
            float segY = segmentRects[base + 1];
            float segH = segmentRects[base + 3];
            float segTop = (scaledHeight - (segY + segH)) * scale;
            float segBottom = (scaledHeight - segY) * scale;
            topY = Math.min(topY, segTop);
            bottomY = Math.max(bottomY, segBottom);
        }
        if (!Float.isFinite(topY) || !Float.isFinite(bottomY)) {
            topY = fallbackPxY;
            bottomY = fallbackPxY + fallbackPxH;
        }
        outTopBottom[0] = topY;
        outTopBottom[1] = bottomY;
    }

    static void putSegments(Std140Builder builder, float scale, float scaledHeight, float[] segmentRects, float[] segmentRadii, int count) {
        for (int i = 0; i < MAX_SEGMENTS; i++) {
            if (segmentRects != null && i < count) {
                int base = i * 4;
                float segX = segmentRects[base];
                float segY = segmentRects[base + 1];
                float segW = segmentRects[base + 2];
                float segH = segmentRects[base + 3];

                float segPxX = segX * scale;
                float segPxY = (scaledHeight - (segY + segH)) * scale;
                float segPxW = segW * scale;
                float segPxH = segH * scale;
                builder.putVec4(segPxX, segPxY, segPxW, segPxH);
            } else {
                builder.putVec4(0.0f, 0.0f, 0.0f, 0.0f);
            }
        }

        for (int i = 0; i < MAX_SEGMENTS; i++) {
            if (segmentRadii != null && i < count) {
                float r = Math.max(0.0f, segmentRadii[i] * scale);
                builder.putVec4(r, 0.0f, 0.0f, 0.0f);
            } else {
                builder.putVec4(0.0f, 0.0f, 0.0f, 0.0f);
            }
        }
    }
}

