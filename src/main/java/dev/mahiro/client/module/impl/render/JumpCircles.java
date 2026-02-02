package dev.mahiro.client.module.impl.render;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.events.render.Render3DEvent;
import dev.mahiro.client.events.render.WorldLoadEvent;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.module.impl.client.ClickGui;
import dev.mahiro.client.utils.animations.Easing;
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
import java.util.List;

public class JumpCircles extends Module {
    public JumpCircles() {
        super("JumpCircles", "跳跃光圈", Category.Render);
    }

    public enum ColorMode {
        Custom, Client, Rainbow, Astolfo
    }

    public enum Mode {
        Fill, Outline, Both
    }

    private final NumberValue<Integer> maxTime = new NumberValue<>("Max Time", "最大时间", 2000, 500, 5000, 100);
    private final NumberValue<Double> radius = new NumberValue<>("Radius", "半径", 2.5, 0.5, 5.0, 0.1);
    private final NumberValue<Integer> segments = new NumberValue<>("Segments", "分段数", 60, 20, 120, 5);
    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Both);
    private final EnumValue<ColorMode> colorMode = new EnumValue<>("Color Mode", "颜色模式", ColorMode.Client);
    private final ColorValue circleColor = new ColorValue("Circle Color", "光圈颜色", new Color(255, 100, 255, 200), () -> colorMode.is(ColorMode.Custom));

    private final BoolValue depthTest = new BoolValue("DepthTest", "深度测试", false);
    private final BoolValue fade = new BoolValue("Fade Effect", "淡出效果", true);
    private final BoolValue glow = new BoolValue("Glow", "发光", true);
    private final NumberValue<Integer> glowLayers = new NumberValue<>("Glow Layers", "发光层数", 3, 1, 10, 1, glow::get);
    private final BoolValue rotate = new BoolValue("Rotate", "旋转", false);
    private final NumberValue<Double> rotateSpeed = new NumberValue<>("Rotate Speed", "旋转速度", 2.0, 0.5, 10.0, 0.5, rotate::get);

    private final List<JumpCircle> circles = new ArrayList<>();
    private boolean wasOnGround = true;

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

            circles.add(new JumpCircle(new Vec3d(pos.x, y, pos.z)));
        }

        wasOnGround = onGround;
    }

    @EventHandler
    public void onRender3D(Render3DEvent event) {
        if (circles.isEmpty()) return;

        circles.removeIf(circle -> circle.getProgress() >= 1.0f);

        if (circles.isEmpty()) return;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        if (!depthTest.get()) {
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
        }
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
        // Use a smoother ease out for expansion
        float expansion = (float) Easing.CUBIC_OUT.ease(progress);

        float currentRadius = (float) (expansion * radius.get());

        // Alpha fades out as it expands
        float alpha = 1.0f - (float) Easing.QUAD_IN.ease(progress);

        if (alpha < 0.01f || currentRadius < 0.01f) return;

        double rotation = 0;
        if (rotate.get()) {
            rotation = (System.currentTimeMillis() % 36000) / 100.0 * rotateSpeed.get();
        }

        Color baseColor = getCircleColor();
        int r = baseColor.getRed();
        int g = baseColor.getGreen();
        int b = baseColor.getBlue();

        Vec3d camPos = mc.gameRenderer.getCamera().getPos();
        Vec3d pos = circle.getPos();

        matrices.push();
        matrices.translate(pos.x - camPos.x, pos.y - camPos.y, pos.z - camPos.z);
        if (rotate.get()) {
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) rotation));
        }

        Matrix4f matrix = matrices.peek().getPositionMatrix();

        if (mode.is(Mode.Fill) || mode.is(Mode.Both)) {
            if (glow.get()) {
                for (int i = 0; i < glowLayers.get(); i++) {
                    float layerAlpha = alpha * 0.4f * (1.0f - (float) i / glowLayers.get());
                    float layerRadius = currentRadius * (1.0f - i * 0.05f);
                    if (layerRadius <= 0) break;
                    drawFilledCircle(matrix, layerRadius, new Color(r, g, b, (int) (layerAlpha * 255)));
                }
            } else if (fade.get()) {
                drawFilledCircle(matrix, currentRadius, new Color(r, g, b, (int) (alpha * 100))); // Softer fill
                drawFilledCircle(matrix, currentRadius * 0.8f, new Color(r, g, b, (int) (alpha * 150)));
            } else {
                drawFilledCircle(matrix, currentRadius, new Color(r, g, b, (int) (alpha * 180)));
            }
        }

        if (mode.is(Mode.Outline) || mode.is(Mode.Both)) {
            drawCircleOutline(matrix, currentRadius, new Color(r, g, b, (int) (alpha * 255)), 2.0f);
        }

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
            buffer.vertex(matrix, x, 0, z).color(r, g, b, 0f);
        }

        BufferRenderer.drawWithGlobalProgram(buffer.end());
    }

    private void drawCircleOutline(Matrix4f matrix, float radius, Color color, float width) {
        RenderSystem.lineWidth(width);
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
        RenderSystem.lineWidth(1.0f);
    }

    private Color getCircleColor() {
        return switch (colorMode.get()) {
            case Custom -> circleColor.get();
            case Client -> ClickGui.color(0);
            case Rainbow -> {
                float hue = ((System.currentTimeMillis() % 3000) / 3000f) % 1f;
                yield Color.getHSBColor(hue, 0.8f, 1f);
            }
            case Astolfo -> {
                double speed = 0.5;
                double offset = 0;
                double hue = (System.currentTimeMillis() * speed + offset * 10) / 1000.0;
                hue = hue % 1.0;
                if (hue > 0.5) hue = 0.5 - (hue - 0.5);
                hue = hue + 0.5;
                yield Color.getHSBColor((float) hue, 0.5f, 1f);
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

        public JumpCircle(Vec3d pos) {
            this.startTime = System.currentTimeMillis();
            this.pos = pos;
        }

        public float getProgress() {
            return (float) (System.currentTimeMillis() - startTime) / maxTime.get();
        }

        public long getAge() {
            return System.currentTimeMillis() - startTime;
        }

        public Vec3d getPos() {
            return pos;
        }
    }
}
