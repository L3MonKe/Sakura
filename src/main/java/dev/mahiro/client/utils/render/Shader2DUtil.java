package dev.mahiro.client.utils.render;

import dev.mahiro.client.shaders.BlurProgram;
import dev.mahiro.client.nanovg.NanoVGRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.nanovg.NVGPaint;

import java.awt.*;

import static dev.mahiro.client.Mahiro.mc;
import static org.lwjgl.nanovg.NanoVG.*;

public class Shader2DUtil {
    public static BlurProgram BLUR_PROGRAM;

    public static void init() {
        BLUR_PROGRAM = new BlurProgram();
    }

    public static void drawQuadBlur(MatrixStack matrices, float x, float y, float width, float height, float blurStrength, float blurOpacity) {
        if (BLUR_PROGRAM == null) {
            BLUR_PROGRAM = new BlurProgram();
        }

        int radius = Math.max(0, Math.round(blurStrength));
        BLUR_PROGRAM.applyBlur(radius);
        int image = BLUR_PROGRAM.getNvgImageId();

        float opacity = Math.max(0f, Math.min(1f, blurOpacity));
        if (image == -1) {
            return;
        }

        float screenWidth = mc.getWindow().getScaledWidth();
        float screenHeight = mc.getWindow().getScaledHeight();

        NanoVGRenderer.INSTANCE.draw(vg -> {
            NVGPaint paint = NVGPaint.create();
            nvgImagePattern(vg, 0, 0, screenWidth, screenHeight, 0.0f, image, opacity, paint);

            nvgBeginPath(vg);
            nvgRect(vg, x, y, width, height);
            nvgFillPaint(vg, paint);
            nvgFill(vg);
        });
    }

    public static void drawRoundedBlur(MatrixStack matrices, float x, float y, float width, float height, float radius, Color c1, float blurStrenth, float blurOpacity) {
        if (BLUR_PROGRAM == null) {
            BLUR_PROGRAM = new BlurProgram();
        }

        int blurRadius = Math.max(0, Math.round(blurStrenth));
        BLUR_PROGRAM.applyBlur(blurRadius);
        int image = BLUR_PROGRAM.getNvgImageId();

        float opacity = Math.max(0f, Math.min(1f, blurOpacity));
        if (image == -1) {
            return;
        }

        float screenWidth = mc.getWindow().getScaledWidth();
        float screenHeight = mc.getWindow().getScaledHeight();

        NanoVGRenderer.INSTANCE.draw(vg -> {
            NVGPaint paint = NVGPaint.create();
            nvgImagePattern(vg, 0, 0, screenWidth, screenHeight, 0.0f, image, opacity, paint);

            nvgBeginPath(vg);
            if (radius > 0.0f) {
                nvgRoundedRect(vg, x, y, width, height, radius);
            } else {
                nvgRect(vg, x, y, width, height);
            }
            nvgFillPaint(vg, paint);
            nvgFill(vg);
        });
    }
}
