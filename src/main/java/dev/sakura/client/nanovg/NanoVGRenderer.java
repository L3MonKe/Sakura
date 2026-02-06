package dev.sakura.client.nanovg;

import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.sakura.client.nanovg.util.state.States;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.RenderPipelines;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.GL33C;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.Consumer;

import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;

public class NanoVGRenderer {
    public static final NanoVGRenderer INSTANCE = new NanoVGRenderer();

    private long vg = 0L;
    private boolean initialized = false;
    private boolean inFrame = false;
    private final ThreadLocal<ScreenBatch> screenBatch = new ThreadLocal<>();
    private final List<Consumer<Long>> queuedScreenDrawCalls = new ArrayList<>();

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

    public void beginBatch() {
        ScreenBatch batch = screenBatch.get();
        if (batch != null) {
            batch.depth++;
            return;
        }
        screenBatch.set(new ScreenBatch());
    }

    public void endBatch() {
        ScreenBatch batch = screenBatch.get();
        if (batch == null) {
            return;
        }
        batch.depth--;
        if (batch.depth > 0) {
            return;
        }
        screenBatch.remove();
        if (batch.drawCalls.isEmpty()) {
            return;
        }
        queuedScreenDrawCalls.addAll(batch.drawCalls);
    }

    public void flushScreenQueue() {
        if (queuedScreenDrawCalls.isEmpty()) {
            return;
        }
        List<Consumer<Long>> drawCalls = List.copyOf(queuedScreenDrawCalls);
        queuedScreenDrawCalls.clear();
        this.draw(vg -> {
            for (Consumer<Long> draw : drawCalls) {
                draw.accept(vg);
            }
        });
    }

    public void draw(Consumer<Long> drawingLogic) {
        if (!initialized) initNanoVG();

        ScreenBatch batch = screenBatch.get();
        if (batch != null) {
            batch.drawCalls.add(drawingLogic);
            return;
        }

        if (inFrame) { // 防止叠帧
            drawingLogic.accept(vg);
            return;
        }

        MinecraftClient mc = MinecraftClient.getInstance();
        States.INSTANCE.push();

        inFrame = true;

        float scaleFactor = (float) mc.getWindow().getScaleFactor();
        nvgBeginFrame(vg, mc.getWindow().getFramebufferWidth() / scaleFactor, mc.getWindow().getFramebufferHeight() / scaleFactor, scaleFactor);

        drawingLogic.accept(vg);

        Framebuffer framebuffer = mc.getFramebuffer();
        CommandEncoder encoder = RenderSystem.getDevice().createCommandEncoder();
        try (RenderPass renderPass = encoder.createRenderPass(() -> "NanoVG", framebuffer.getColorAttachmentView(), OptionalInt.empty(), null, OptionalDouble.empty())) {
            renderPass.setPipeline(RenderPipelines.GUI);
            nvgEndFrame(vg);
        }
        States.INSTANCE.pop();
        GL33C.glViewport(0, 0, mc.getWindow().getFramebufferWidth(), mc.getWindow().getFramebufferHeight());
        inFrame = false;
    }

    public boolean isInFrame() {
        return inFrame;
    }

    public void cleanup() {
        if (initialized && vg != 0L) {
            NanoVGGL3.nvgDelete(vg);
            vg = 0L;
            initialized = false;
        }
    }

    private static final class ScreenBatch {
        private final List<Consumer<Long>> drawCalls = new ArrayList<>();
        private int depth = 1;

        private ScreenBatch() {
        }
    }
}
