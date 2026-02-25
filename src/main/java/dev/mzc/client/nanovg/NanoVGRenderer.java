package dev.mzc.client.nanovg;

import dev.mzc.client.nanovg.util.state.States;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.nanovg.NanoVGGL3;

import java.util.function.Consumer;

import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Sakura NanoVG渲染器
 * <p>
 * 坐标系说明：
 * - draw(): 使用MC逻辑坐标（自动缩放），与鼠标坐标、Screen尺寸一致
 * - drawRaw(): 使用像素坐标，用于需要精确像素控制的场景
 */

public class NanoVGRenderer {
    public static final NanoVGRenderer INSTANCE = new NanoVGRenderer();

    private long vg = 0L;
    private boolean initialized = false;
    private boolean inFrame = false;
    private boolean scaled = false;

    public void initNanoVG() {
        if (!initialized) {
            vg = NanoVGGL3.nvgCreate(NanoVGGL3.NVG_ANTIALIAS | NanoVGGL3.NVG_STENCIL_STROKES);

            if (vg == 0L) {
                throw new RuntimeException("无法初始化NanoVG");
            }
            initialized = true;
        }
    }

    public long getContext() {
        if (!initialized) {
            initNanoVG();
        }
        return vg;
    }

    public void draw(Consumer<Long> drawingLogic) {
        if (!initialized) initNanoVG();
        if (inFrame) {
            drawingLogic.accept(vg);
            return;
        }

        States.INSTANCE.push();

        MinecraftClient mc = MinecraftClient.getInstance();
        int width = mc.getWindow().getWidth();
        int height = mc.getWindow().getHeight();

        nvgBeginFrame(vg, width, height, 1.0f);

        inFrame = true;

        float scale = (float) MinecraftClient.getInstance().getWindow().getScaleFactor();
        if (MinecraftClient.IS_SYSTEM_MAC) {
            scale /= 2;
        }

        nvgSave(vg);
        nvgScale(vg, scale, scale);
        scaled = true;

        try {
            drawingLogic.accept(vg);
        } finally {
            nvgRestore(vg);
            scaled = false;

            nvgEndFrame(vg);
            inFrame = false;
            States.INSTANCE.pop();
        }
    }

    /**
     * 在已开始的帧中临时暂停NanoVG（用于其他渲染器如Shader）
     */
    public void withRawCoords(Runnable drawer) {
        if (!inFrame) {
            throw new IllegalStateException("必须在draw()回调内使用");
        }

        MinecraftClient mc = MinecraftClient.getInstance();
        int width = mc.getWindow().getWidth();
        int height = mc.getWindow().getHeight();

        boolean wasScaled = scaled;

        nvgEndFrame(vg);

        drawer.run();

        nvgBeginFrame(vg, width, height, 1.0f);

        if (wasScaled) {
            float scale = (float) MinecraftClient.getInstance().getWindow().getScaleFactor();
            nvgSave(vg);
            nvgScale(vg, scale, scale);
        }
    }

    public boolean isInFrame() {
        return inFrame;
    }

    public boolean isScaled() {
        return scaled;
    }

    public void cleanup() {
        if (initialized && vg != 0L) {
            NanoVGGL3.nvgDelete(vg);
            vg = 0L;
            initialized = false;
        }
    }
}
