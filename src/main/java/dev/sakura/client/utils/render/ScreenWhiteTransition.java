package dev.sakura.client.utils.render;

import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.system.MemoryStack;

import static dev.sakura.client.Sakura.mc;

public final class ScreenWhiteTransition {
    private static boolean active;
    private static long startMs;
    private static long durationMs;
    private static Runnable peakAction;
    private static boolean peakActionDone;
    private static boolean fadeOnNextNewMenuOpen;

    private ScreenWhiteTransition() {
    }

    public static void startToScreen(Screen target, long duration) {
        startToAction(() -> mc.setScreen(target), duration);
    }

    public static void startToAction(Runnable action, long duration) {
        if (active || action == null) {
            return;
        }
        active = true;
        startMs = System.currentTimeMillis();
        durationMs = Math.max(1L, duration);
        peakAction = action;
        peakActionDone = false;
    }

    public static void startFromWhite(long duration) {
        active = true;
        durationMs = Math.max(1L, duration);
        startMs = System.currentTimeMillis() - durationMs / 2L;
        peakAction = null;
        peakActionDone = true;
    }

    public static boolean isActive() {
        return active;
    }

    public static void markFadeOnNextNewMenuOpen() {
        fadeOnNextNewMenuOpen = true;
    }

    public static boolean consumeNewMenuFadeRequest() {
        boolean value = fadeOnNextNewMenuOpen;
        fadeOnNextNewMenuOpen = false;
        return value;
    }

    public static void renderNanoVg(long vg, int width, int height) {
        float alpha = computeAlpha();
        if (alpha <= 0f) {
            return;
        }
        try (MemoryStack stack = MemoryStack.stackPush()) {
            NVGColor color = NVGColor.malloc(stack);
            NanoVG.nvgRGBA((byte) 255, (byte) 255, (byte) 255, (byte) (Math.max(0, Math.min(255, (int) (alpha * 255f)))), color);
            NanoVG.nvgBeginPath(vg);
            NanoVG.nvgRect(vg, 0f, 0f, width, height);
            NanoVG.nvgFillColor(vg, color);
            NanoVG.nvgFill(vg);
        }
    }

    public static void clearNewMenuFadeRequest() {
        fadeOnNextNewMenuOpen = false;
    }

    private static float computeAlpha() {
        if (!active) {
            return 0f;
        }
        long elapsed = System.currentTimeMillis() - startMs;
        float progress = Math.min(1f, elapsed / (float) durationMs);
        float alpha = progress < 0.5f ? progress / 0.5f : 1f - (progress - 0.5f) / 0.5f;
        alpha = Math.max(0f, Math.min(1f, alpha));
        if (!peakActionDone && progress >= 0.5f) {
            peakActionDone = true;
            Runnable action = peakAction;
            peakAction = null;
            if (action != null) {
                action.run();
            }
        }
        if (progress >= 1f) {
            active = false;
            peakAction = null;
        }
        return alpha;
    }
}
