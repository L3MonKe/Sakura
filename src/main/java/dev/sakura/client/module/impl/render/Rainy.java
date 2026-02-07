package dev.sakura.client.module.impl.render;

import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WeatherRendering;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.List;

public class Rainy extends Module {
    public Rainy() {
        super("Rainy", "下雨", Category.Render);
    }

    private enum Mode {
        Sakura,
        Snow
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Sakura);

    private final NumberValue<Double> density = new NumberValue<>("Density", "密度", 12.0, 0.0, 80.0, 1.0, () -> mode.get() == Mode.Sakura);
    private final NumberValue<Double> radius = new NumberValue<>("Radius", "范围", 16.0, 2.0, 64.0, 1.0, () -> mode.get() == Mode.Sakura);
    private final NumberValue<Double> height = new NumberValue<>("Height", "高度", 12.0, 2.0, 32.0, 1.0, () -> mode.get() == Mode.Sakura);
    private final BoolValue onlyOutside = new BoolValue("Only Outside", "仅室外", true, () -> mode.get() == Mode.Sakura);
    private final BoolValue tint = new BoolValue("Tint", "染色", true, () -> mode.get() == Mode.Sakura);
    private final BoolValue gradient = new BoolValue("Gradient", "渐变", true, () -> mode.get() == Mode.Sakura && tint.get());
    private final ColorValue colorA = new ColorValue("Color A", "颜色1", new Color(255, 183, 197, 255), true, () -> mode.get() == Mode.Sakura && tint.get());
    private final ColorValue colorB = new ColorValue("Color B", "颜色2", new Color(255, 220, 235, 255), true, () -> mode.get() == Mode.Sakura && tint.get() && gradient.get());

    public boolean isSakura() {
        return mode.get() == Mode.Sakura;
    }

    public boolean isSnow() {
        return mode.get() == Mode.Snow;
    }

    public boolean isTintEnabled() {
        return tint.get();
    }

    public boolean isGradientEnabled() {
        return gradient.get();
    }

    public double getDensity() {
        return density.get();
    }

    public int getRadius() {
        return radius.get().intValue();
    }

    public int getHeight() {
        return height.get().intValue();
    }

    public boolean isOnlyOutside() {
        return onlyOutside.get();
    }

    public Color getPetalColor(double x, double z) {
        if (!tint.get()) return Color.WHITE;
        if (!gradient.get()) return colorA.get();
        long hx = Double.doubleToLongBits(x);
        long hz = Double.doubleToLongBits(z);
        return ((hx ^ hz) & 1L) == 0L ? colorA.get() : colorB.get();
    }

    public void renderSakura(VertexConsumer vertexConsumer, List<WeatherRendering.Piece> pieces, Vec3d pos, float intensity, int range, float gradient, boolean tintEnabled, boolean gradientEnabled, Color colorA, Color colorB, float[] DX, float[] DZ) {
        float f = range * range;
        float rA = colorA.getRed() / 255.0f;
        float gA = colorA.getGreen() / 255.0f;
        float bA = colorA.getBlue() / 255.0f;
        float rB = colorB.getRed() / 255.0f;
        float gB = colorB.getGreen() / 255.0f;
        float bB = colorB.getBlue() / 255.0f;

        for (WeatherRendering.Piece piece : pieces) {
            float dx = (float) ((double) piece.x() + 0.5 - pos.x);
            float dz = (float) ((double) piece.z() + 0.5 - pos.z);
            float distSq = (float) MathHelper.squaredHypot(dx, dz);
            float alpha = MathHelper.lerp(Math.min(distSq / f, 1.0f), intensity, 0.5f) * gradient;

            int color;
            if (tintEnabled) {
                boolean useA = true;
                if (gradientEnabled) {
                    int h = (piece.x() * 73428767) ^ (piece.z() * 9122713);
                    useA = (h & 1) == 0;
                }
                if (useA) {
                    color = ColorHelper.fromFloats(alpha, rA, gA, bA);
                } else {
                    color = ColorHelper.fromFloats(alpha, rB, gB, bB);
                }
            } else {
                color = ColorHelper.getWhite(alpha);
            }

            int normalIndex = (piece.z() - MathHelper.floor(pos.z) + 16) * 32 + piece.x() - MathHelper.floor(pos.x) + 16;
            float m = DX[normalIndex] / 2.0f;
            float n = DZ[normalIndex] / 2.0f;

            float x1 = dx - m;
            float x2 = dx + m;
            float y1 = (float) ((double) piece.topY() - pos.y);
            float y2 = (float) ((double) piece.bottomY() - pos.y);
            float z1 = dz - n;
            float z2 = dz + n;

            float u1 = piece.uOffset() + 0.0f;
            float u2 = piece.uOffset() + 1.0f;
            float v1 = (float) piece.bottomY() * 0.25f + piece.vOffset();
            float v2 = (float) piece.topY() * 0.25f + piece.vOffset();

            vertexConsumer.vertex(x1, y1, z1).texture(u1, v1).color(color).light(piece.lightCoords());
            vertexConsumer.vertex(x2, y1, z2).texture(u2, v1).color(color).light(piece.lightCoords());
            vertexConsumer.vertex(x2, y2, z2).texture(u2, v2).color(color).light(piece.lightCoords());
            vertexConsumer.vertex(x1, y2, z1).texture(u1, v2).color(color).light(piece.lightCoords());
        }
    }
}
