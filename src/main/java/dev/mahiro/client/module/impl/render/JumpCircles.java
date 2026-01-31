package dev.mahiro.client.module.impl.render;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.events.render.Render3DEvent;
import dev.mahiro.client.events.render.WorldLoadEvent;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.module.impl.client.ClickGui;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.ColorValue;
import dev.mahiro.client.values.impl.EnumValue;
import dev.mahiro.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Blocks;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JumpCircles extends Module {

    public enum ColorMode {
        Custom, Client, Rainbow
    }

    private final NumberValue<Integer> maxTime = new NumberValue<>("Max Time", "最大时间", 3000, 1000, 8000, 100);
    private final NumberValue<Double> radius = new NumberValue<>("Radius", "半径", 2.0, 0.5, 5.0, 0.1);
    private final NumberValue<Integer> segments = new NumberValue<>("Segments", "分段数", 60, 20, 120, 5);
    private final EnumValue<ColorMode> colorMode = new EnumValue<>("Color Mode", "颜色模式", ColorMode.Client);
    private final ColorValue circleColor = new ColorValue("Circle Color", "光圈颜色", new Color(255, 100, 255, 200), () -> colorMode.is(ColorMode.Custom));
    private final BoolValue fade = new BoolValue("Fade Effect", "淡出效果", true);
    private final BoolValue glow = new BoolValue("Glow", "发光", true);
    private final NumberValue<Integer> glowLayers = new NumberValue<>("Glow Layers", "发光层数", 5, 1, 10, 1, glow::get);
    private final BoolValue rotate = new BoolValue("Rotate", "旋转", true);
    private final NumberValue<Double> rotateSpeed = new NumberValue<>("Rotate Speed", "旋转速度", 2.0, 0.5, 10.0, 0.5, rotate::get);

    private final List<JumpCircle> circles = new ArrayList<>();
    private boolean wasOnGround = true;

    public JumpCircles() {
        super("JumpCircles", "跳跃光圈", Category.Render);
    }

    @Override
    protected void onEnable() {
        circles.clear();
        wasOnGround = true;
    }

    @Override
    protected void onDisable() {
        circles.clear();
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        boolean onGround = mc.player.isOnGround();

        if (onGround && !wasOnGround) {
            Vec3d pos = mc.player.getPos();
            double y = pos.y + 0.01;

            BlockPos blockPos = mc.player.getBlockPos();
            if (mc.world.getBlockState(blockPos).getBlock() == Blocks.SNOW) {
                y += 0.125;
            }

            circles.add(new JumpCircle(new Vec3d(pos.x, y, pos.z), circles.size()));
        }

        wasOnGround = onGround;
    }

    @EventHandler
    public void onRender3D(Render3DEvent event) {
        if (circles.isEmpty()) return;

        Iterator<JumpCircle> iterator = circles.iterator();
        while (iterator.hasNext()) {
            JumpCircle circle = iterator.next();
            if (circle.getProgress() >= 1.0f) {
                iterator.remove();
            }
        }

        if (circles.isEmpty()) return;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.disableCull();
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);

        for (JumpCircle circle : circles) {
            renderCircle(event.getMatrices(), circle, event.getTickDelta());
        }

        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    private void renderCircle(MatrixStack matrices, JumpCircle circle, float tickDelta) {
        float progress = circle.getProgress();
        float waveProgress = valWave01(1.0f - progress);

        float currentRadius = (float) ((progress > 0.5f ?
                easeOutElastic(waveProgress * waveProgress) :
                easeOutBack(waveProgress)) * radius.get());

        float alpha = (float) easeOutCirc(valWave01(1.0f - progress));
        if (progress < 0.5f) {
            alpha *= (float) easeInOutExpo(alpha);
        }

        if (alpha < 0.01f || currentRadius < 0.01f) return;

        double rotation = 0;
        if (rotate.get()) {
            rotation = easeInOutElastic(waveProgress) * 90.0 / (1.0 + waveProgress);
            rotation += (System.currentTimeMillis() % 36000) / 100.0 * rotateSpeed.get();
        }

        Color baseColor = getCircleColor(circle.getIndex());
        int r = baseColor.getRed();
        int g = baseColor.getGreen();
        int b = baseColor.getBlue();

        Vec3d camPos = mc.gameRenderer.getCamera().getPos();
        Vec3d pos = circle.getPos();

        matrices.push();
        matrices.translate(pos.x - camPos.x, pos.y - camPos.y, pos.z - camPos.z);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) rotation));

        Matrix4f matrix = matrices.peek().getPositionMatrix();

        if (glow.get()) {
            for (int i = glowLayers.get(); i > 0; i--) {
                float layerRadius = currentRadius * (1.0f + i * 0.15f);
                float layerAlpha = alpha * (1.0f - (float) i / (glowLayers.get() + 1)) * 0.5f;
                drawFilledCircle(matrix, layerRadius, new Color(r, g, b, (int) (layerAlpha * 255)));
            }
        }

        if (fade.get()) {
            int fadeSteps = 8;
            for (int i = fadeSteps; i > 0; i--) {
                float stepRadius = currentRadius * ((float) i / fadeSteps);
                float stepAlpha = alpha * ((float) i / fadeSteps);
                drawFilledCircle(matrix, stepRadius, new Color(r, g, b, (int) (stepAlpha * 200)));
            }
        } else {
            drawFilledCircle(matrix, currentRadius, new Color(r, g, b, (int) (alpha * 200)));
        }

        drawCircleOutline(matrix, currentRadius, new Color(r, g, b, (int) (alpha * 255)));

        matrices.pop();
    }

    private void drawFilledCircle(Matrix4f matrix, float radius, Color color) {
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);

        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;
        float a = color.getAlpha() / 255f;

        buffer.vertex(matrix, 0, 0, 0).color(r, g, b, a);

        int segs = segments.get();
        for (int i = 0; i <= segs; i++) {
            double angle = Math.PI * 2 * i / segs;
            float x = (float) (Math.cos(angle) * radius);
            float z = (float) (Math.sin(angle) * radius);
            buffer.vertex(matrix, x, 0, z).color(r, g, b, a * 0.5f);
        }

        BufferRenderer.drawWithGlobalProgram(buffer.end());
    }

    private void drawCircleOutline(Matrix4f matrix, float radius, Color color) {
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);

        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;
        float a = color.getAlpha() / 255f;

        int segs = segments.get();
        for (int i = 0; i <= segs; i++) {
            double angle = Math.PI * 2 * i / segs;
            float x = (float) (Math.cos(angle) * radius);
            float z = (float) (Math.sin(angle) * radius);
            buffer.vertex(matrix, x, 0, z).color(r, g, b, a);
        }

        BufferRenderer.drawWithGlobalProgram(buffer.end());
    }

    private Color getCircleColor(int index) {
        return switch (colorMode.get()) {
            case Custom -> circleColor.get();
            case Client -> ClickGui.color(index * 50);
            case Rainbow -> {
                float hue = ((System.currentTimeMillis() % 3000) / 3000f + index * 0.1f) % 1f;
                yield Color.getHSBColor(hue, 0.8f, 1f);
            }
        };
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        circles.clear();
    }

    private class JumpCircle {
        private final long startTime;
        private final Vec3d pos;
        private final int index;

        public JumpCircle(Vec3d pos, int index) {
            this.startTime = System.currentTimeMillis();
            this.pos = pos;
            this.index = index;
        }

        public float getProgress() {
            return (float) (System.currentTimeMillis() - startTime) / maxTime.get();
        }

        public Vec3d getPos() {
            return pos;
        }

        public int getIndex() {
            return index;
        }
    }

    private static double easeOutElastic(double value) {
        double c4 = (2 * Math.PI) / 3;
        return value <= 0 ? 0 : value >= 1 ? 1 : Math.pow(2, -10 * value) * Math.sin((value * 10 - 0.75) * c4) + 1;
    }

    private static double easeOutBack(double value) {
        double c1 = 1.70158, c3 = c1 + 1;
        return 1 + c3 * Math.pow(value - 1, 3) + c1 * Math.pow(value - 1, 2);
    }

    private static double easeInOutElastic(double value) {
        double c5 = (2 * Math.PI) / 4.5;
        return value <= 0 ? 0 : value >= 1 ? 1 : value < 0.5 ?
                -(Math.pow(2, 20 * value - 10) * Math.sin((20 * value - 11.125) * c5)) / 2 :
                (Math.pow(2, -20 * value + 10) * Math.sin((20 * value - 11.125) * c5)) / 2 + 1;
    }

    private static double easeOutCirc(double value) {
        return Math.sqrt(1 - Math.pow(value - 1, 2));
    }

    private static double easeInOutExpo(double value) {
        return value <= 0 ? 0 : value >= 1 ? 1 : value < 0.5 ?
                Math.pow(2, 20 * value - 10) / 2 :
                (2 - Math.pow(2, -20 * value + 10)) / 2;
    }

    private static float valWave01(float value) {
        return (value > 0.5f ? 1.0f - value : value) * 2.0f;
    }
}