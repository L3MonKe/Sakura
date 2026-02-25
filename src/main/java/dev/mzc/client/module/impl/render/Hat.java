package dev.mzc.client.module.impl.render;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.mzc.client.events.render.Render3DEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.utils.color.ColorUtil;
import dev.mzc.client.utils.math.MathUtil;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.ColorValue;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

import java.awt.*;

public class Hat extends Module {

    public enum Mode {
        Astolfo("阿斯托尔福"),
        Sexy("性感"),
        Fade("渐变"),
        Dynamic("动态");

        private final String cnName;

        Mode(String cnName) {
            this.cnName = cnName;
        }
    }

    public enum Style {
        Cone("斗笠"),
        Halo("光环"),
        Ring("圆环"),
        TopHat("高礼帽"),
        Crown("王冠");

        private final String cnName;

        Style(String cnName) {
            this.cnName = cnName;
        }
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Sexy);
    private final EnumValue<Style> style = new EnumValue<>("Style", "样式", Style.Cone);
    private final NumberValue<Integer> points = new NumberValue<>("Points", "点数", 30, 3, 180, 1);
    private final NumberValue<Double> size = new NumberValue<>("Size", "大小", 0.5, 0.1, 3.0, 0.1);
    private final NumberValue<Double> offsetValue = new NumberValue<>("Offset", "偏移", 2000.0, 0.0, 5000.0, 100.0);
    private final NumberValue<Double> height = new NumberValue<>("Height", "高度", 0.25, 0.05, 1.0, 0.05, () -> style.is(Style.Cone) || style.is(Style.TopHat) || style.is(Style.Crown));
    private final NumberValue<Double> thickness = new NumberValue<>("Thickness", "厚度", 0.06, 0.01, 0.5, 0.01, () -> style.is(Style.Halo) || style.is(Style.Ring) || style.is(Style.TopHat) || style.is(Style.Crown));
    private final NumberValue<Double> yOffset = new NumberValue<>("YOffset", "高度偏移", 0.12, 0.0, 0.5, 0.01, () -> style.is(Style.Ring) || style.is(Style.TopHat));
    private final NumberValue<Double> elytraYOffset = new NumberValue<>("Elytra YOffset", "鞘翅偏移", 0.10, 0.0, 0.5, 0.01);
    private final ColorValue colorValue = new ColorValue("Color", "颜色", new Color(255, 255, 255), () -> mode.is(Mode.Fade) || mode.is(Mode.Dynamic));
    private final ColorValue secondColorValue = new ColorValue("Second Color", "第二颜色", new Color(0, 0, 0), () -> mode.is(Mode.Fade));
    private final BoolValue onlyThirdPerson = new BoolValue("Only Third Person", "仅第三人称", true);

    private final double[][] positions = new double[181][2];
    private int lastPoints;
    private double lastSize;

    public Hat() {
        super("Hat", "帽子", Category.Render);
        this.setType(ModuleType.All);
    }

    private void computeChineseHatPoints(int points, double radius) {
        for (int i = 0; i <= points; i++) {
            double circleX = radius * StrictMath.cos(i * Math.PI * 2 / points);
            double circleZ = radius * StrictMath.sin(i * Math.PI * 2 / points);
            this.positions[i][0] = circleX;
            this.positions[i][1] = circleZ;
        }
    }

    @EventHandler
    public void onRender3D(Render3DEvent event) {
        if (nullCheck()) return;

        if (this.lastSize != this.size.get() || this.lastPoints != this.points.get()) {
            this.lastSize = this.size.get();
            this.lastPoints = this.points.get();
            this.computeChineseHatPoints(this.lastPoints, this.lastSize);
        }

        drawHat(event.getMatrices(), event.getTickDelta(), mc.player);
    }

    public void drawHat(MatrixStack matrices, float tickDelta, PlayerEntity player) {
        if (player == mc.player && mc.options.getPerspective().isFirstPerson() && onlyThirdPerson.get()) {
            return;
        }

        int pointCount = this.points.get();
        double radius = this.size.get();

        Color[] colors = new Color[181];
        Color[] colorMode = getColorMode();

        for (int i = 0; i < colors.length; ++i) {
            colors[i] = this.fadeBetween(colorMode, this.offsetValue.get(), (double) i * ((double) this.offsetValue.get() / this.points.get()));
        }

        Vec3d camera = mc.gameRenderer.getCamera().getPos();
        double x = MathHelper.lerp(tickDelta, player.lastRenderX, player.getX()) - camera.x;
        double y = MathHelper.lerp(tickDelta, player.lastRenderY, player.getY()) - camera.y;
        double z = MathHelper.lerp(tickDelta, player.lastRenderZ, player.getZ()) - camera.z;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.disableCull();
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);

        matrices.push();

        matrices.translate(x, y + 1.9, z);

        if (player.isSneaking()) {
            matrices.translate(0, -0.2, 0);
        }

        float yaw = MathUtil.interpolateFloat(player.prevHeadYaw, player.headYaw, tickDelta);
        matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(yaw));

        float pitch = MathUtil.interpolateFloat(player.prevPitch, player.getPitch(), tickDelta);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(pitch / 3.0f));
        matrices.translate(0, 0, pitch / 270.0);

        double dy = 0.0;
        if (style.get() == Style.Ring || style.get() == Style.TopHat) dy += yOffset.get();
        if (player.isGliding()) dy += elytraYOffset.get();
        if (dy != 0.0) matrices.translate(0, dy, 0);

        Matrix4f matrix = matrices.peek().getPositionMatrix();
        Tessellator tessellator = Tessellator.getInstance();

        if (style.get() == Style.Cone) {
            RenderSystem.lineWidth(2.0f);
            BufferBuilder outlineBuffer = tessellator.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);
            for (int i = 0; i < pointCount; i++) {
                double[] pos = this.positions[i];
                Color clr = colors[i];
                outlineBuffer.vertex(matrix, (float) pos[0], 0, (float) pos[1])
                        .color(clr.getRed(), clr.getGreen(), clr.getBlue(), 255);
            }
            double[] firstPos = this.positions[0];
            Color firstClr = colors[0];
            outlineBuffer.vertex(matrix, (float) firstPos[0], 0, (float) firstPos[1])
                    .color(firstClr.getRed(), firstClr.getGreen(), firstClr.getBlue(), 255);
            BufferRenderer.drawWithGlobalProgram(outlineBuffer.end());

            BufferBuilder coneBuffer = tessellator.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
            coneBuffer.vertex(matrix, 0, height.get().floatValue(), 0)
                    .color(255, 255, 255, 128);
            for (int i = 0; i <= pointCount; i++) {
                double[] pos = this.positions[i % pointCount];
                Color clr = colors[i % colors.length];
                coneBuffer.vertex(matrix, (float) pos[0], 0, (float) pos[1])
                        .color(clr.getRed(), clr.getGreen(), clr.getBlue(), 128);
            }
            BufferRenderer.drawWithGlobalProgram(coneBuffer.end());
        } else if (style.get() == Style.Halo || style.get() == Style.Ring) {
            double inner = Math.max(0.0, radius - thickness.get());
            BufferBuilder ring = tessellator.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);
            for (int i = 0; i <= pointCount; i++) {
                int idx = i % pointCount;
                double ox = this.positions[idx][0];
                double oz = this.positions[idx][1];
                double ix = inner / radius * ox;
                double iz = inner / radius * oz;
                Color clr = colors[idx];
                ring.vertex(matrix, (float) ox, 0, (float) oz).color(clr.getRed(), clr.getGreen(), clr.getBlue(), 160);
                ring.vertex(matrix, (float) ix, 0, (float) iz).color(clr.getRed(), clr.getGreen(), clr.getBlue(), 160);
            }
            BufferRenderer.drawWithGlobalProgram(ring.end());
        } else if (style.get() == Style.TopHat) {
            double brimInner = Math.max(0.0, radius - thickness.get());
            BufferBuilder brim = tessellator.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);
            for (int i = 0; i <= pointCount; i++) {
                int idx = i % pointCount;
                double ox = this.positions[idx][0];
                double oz = this.positions[idx][1];
                double ix = brimInner / radius * ox;
                double iz = brimInner / radius * oz;
                Color clr = colors[idx];
                brim.vertex(matrix, (float) ox, 0, (float) oz).color(clr.getRed(), clr.getGreen(), clr.getBlue(), 180);
                brim.vertex(matrix, (float) ix, 0, (float) iz).color(clr.getRed(), clr.getGreen(), clr.getBlue(), 180);
            }
            BufferRenderer.drawWithGlobalProgram(brim.end());

            double bodyRadius = brimInner * 0.6;
            BufferBuilder sides = tessellator.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);
            for (int i = 0; i <= pointCount; i++) {
                int idx = i % pointCount;
                double bx = bodyRadius / radius * this.positions[idx][0];
                double bz = bodyRadius / radius * this.positions[idx][1];
                Color clr = colors[idx];
                sides.vertex(matrix, (float) bx, 0, (float) bz).color(clr.getRed(), clr.getGreen(), clr.getBlue(), 160);
                sides.vertex(matrix, (float) bx, (float) height.get().doubleValue(), (float) bz).color(clr.getRed(), clr.getGreen(), clr.getBlue(), 160);
            }
            BufferRenderer.drawWithGlobalProgram(sides.end());

            BufferBuilder top = tessellator.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
            top.vertex(matrix, 0, (float) height.get().doubleValue(), 0).color(255, 255, 255, 160);
            for (int i = 0; i <= pointCount; i++) {
                int idx = i % pointCount;
                double tx = bodyRadius / radius * this.positions[idx][0];
                double tz = bodyRadius / radius * this.positions[idx][1];
                Color clr = colors[idx];
                top.vertex(matrix, (float) tx, (float) height.get().doubleValue(), (float) tz).color(clr.getRed(), clr.getGreen(), clr.getBlue(), 160);
            }
            BufferRenderer.drawWithGlobalProgram(top.end());
        } else if (style.get() == Style.Crown) {
            double baseInner = Math.max(0.0, radius - thickness.get());
            BufferBuilder base = tessellator.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);
            for (int i = 0; i <= pointCount; i++) {
                int idx = i % pointCount;
                double ox = this.positions[idx][0];
                double oz = this.positions[idx][1];
                double ix = baseInner / radius * ox;
                double iz = baseInner / radius * oz;
                Color clr = colors[idx];
                base.vertex(matrix, (float) ox, 0, (float) oz).color(clr.getRed(), clr.getGreen(), clr.getBlue(), 200);
                base.vertex(matrix, (float) ix, 0, (float) iz).color(clr.getRed(), clr.getGreen(), clr.getBlue(), 200);
            }
            BufferRenderer.drawWithGlobalProgram(base.end());

            int step = Math.max(1, pointCount / 12);
            BufferBuilder spikes = tessellator.begin(VertexFormat.DrawMode.TRIANGLES, VertexFormats.POSITION_COLOR);
            for (int i = 0; i < pointCount; i += step) {
                int j = (i + step) % pointCount;
                double x1 = this.positions[i][0];
                double z1 = this.positions[i][1];
                double x2 = this.positions[j][0];
                double z2 = this.positions[j][1];
                Color c1 = colors[i];
                Color c2 = colors[j];
                spikes.vertex(matrix, (float) x1, 0, (float) z1).color(c1.getRed(), c1.getGreen(), c1.getBlue(), 180);
                spikes.vertex(matrix, (float) x2, 0, (float) z2).color(c2.getRed(), c2.getGreen(), c2.getBlue(), 180);
                spikes.vertex(matrix, 0, (float) height.get().doubleValue(), 0).color(255, 255, 255, 200);
            }
            BufferRenderer.drawWithGlobalProgram(spikes.end());
        }

        matrices.pop();

        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    private Color[] getColorMode() {
        return switch (this.mode.get()) {
            case Astolfo -> new Color[]{
                    new Color(252, 106, 140), new Color(252, 106, 213),
                    new Color(218, 106, 252), new Color(145, 106, 252),
                    new Color(106, 140, 252), new Color(106, 213, 252),
                    new Color(106, 213, 252), new Color(106, 140, 252),
                    new Color(145, 106, 252), new Color(218, 106, 252),
                    new Color(252, 106, 213), new Color(252, 106, 140)
            };
            case Sexy -> new Color[]{
                    new Color(255, 150, 255), new Color(255, 132, 199),
                    new Color(211, 101, 187), new Color(160, 80, 158),
                    new Color(120, 63, 160), new Color(123, 65, 168),
                    new Color(104, 52, 152), new Color(142, 74, 175),
                    new Color(160, 83, 179), new Color(255, 110, 189),
                    new Color(255, 150, 255)
            };
            case Fade -> new Color[]{
                    this.colorValue.get(),
                    this.secondColorValue.get(),
                    this.colorValue.get()
            };
            case Dynamic -> new Color[]{
                    this.colorValue.get(),
                    ColorUtil.darker(this.colorValue.get(), 0.75f),
                    this.colorValue.get()
            };
        };
    }

    public Color fadeBetween(Color[] table, double speed, double offset) {
        return this.fadeBetween(table, (System.currentTimeMillis() + offset) % speed / speed);
    }

    public Color fadeBetween(Color[] table, double progress) {
        int i = table.length;
        if (progress == 1.0) {
            return table[0];
        }
        if (progress == 0.0) {
            return table[i - 1];
        }
        double max = Math.max(0.0, (1.0 - progress) * (i - 1));
        int min = (int) max;
        return this.fadeBetween(table[min], table[min + 1], max - min);
    }

    public Color fadeBetween(Color start, Color end, double progress) {
        if (progress > 1.0) {
            progress = 1.0 - progress % 1.0;
        }
        return this.gradient(start, end, progress);
    }

    public Color gradient(Color start, Color end, double progress) {
        double invert = 1.0 - progress;
        return new Color(
                (int) (start.getRed() * invert + end.getRed() * progress),
                (int) (start.getGreen() * invert + end.getGreen() * progress),
                (int) (start.getBlue() * invert + end.getBlue() * progress),
                (int) (start.getAlpha() * invert + end.getAlpha() * progress)
        );
    }
}
