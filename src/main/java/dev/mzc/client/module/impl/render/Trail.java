package dev.mzc.client.module.impl.render;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.events.misc.WorldLoadEvent;
import dev.mzc.client.events.render.Render3DEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.utils.render.Render3DUtil;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.ColorValue;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;
import dev.mzc.client.utils.color.ColorUtil;
import dev.mzc.client.module.impl.client.ClickGui;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static dev.mzc.client.Sakura.mc;

public class Trail extends Module {

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Point);
    private final EnumValue<ColorMode> colorMode = new EnumValue<>("Color Mode", "颜色模式", ColorMode.Single);
    private final NumberValue<Integer> maxPoints = new NumberValue<>("Max Points", "最大点数", 100, 10, 1000, 10);
    private final NumberValue<Integer> lifeTime = new NumberValue<>("Life Time", "生命周期(ms)", 2000, 500, 10000, 100);
    private final NumberValue<Double> thickness = new NumberValue<>("Thickness", "线条粗细", 2.0, 0.1, 5.0, 0.1);
    private final NumberValue<Double> minDistance = new NumberValue<>("Min Distance", "最小采样距离", 0.05, 0.01, 0.5, 0.01);
    private final NumberValue<Double> height = new NumberValue<>("Height", "长条高度", 1.8, 0.1, 3.0, 0.1, () -> mode.get() == Mode.Line);
    private final ColorValue trailColor = new ColorValue("Color", "轨迹颜色", new Color(255, 255, 255, 200), () -> colorMode.get() == ColorMode.Single || colorMode.get() == ColorMode.Double);
    private final ColorValue secondColor = new ColorValue("Second Color", "第二颜色", new Color(255, 0, 0, 200), () -> colorMode.get() == ColorMode.Double);
    private final NumberValue<Integer> alpha = new NumberValue<>("Alpha", "透明度", 200, 0, 255, 1);
    private final NumberValue<Double> colorSpeed = new NumberValue<>("Color Speed", "颜色速度", 5.0, 1.0, 20.0, 0.5, () -> colorMode.get() != ColorMode.Single);
    private final BoolValue fade = new BoolValue("Fade", "渐变效果", true);
    private final BoolValue onlyThirdPerson = new BoolValue("Only Third Person", "仅第三人称", false);
    private final BoolValue antialias = new BoolValue("Antialias", "抗锯齿", true);

    private final List<Point> points = new ArrayList<>();

    public Trail() {
        super("Trail", "玩家移动拖尾轨迹", Category.Render);
        this.setType(ModuleType.All);
    }

    public enum Mode {
        Point, Line
    }

    public enum ColorMode {
        Single, Double, Client, Rainbow
    }

    @Override
    protected void onEnable() {
        points.clear();
    }

    @Override
    protected void onDisable() {
        points.clear();
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        // 在 Tick 中采样，但记录插值后的位置
        Vec3d pos = mc.player.getPos();
        points.add(new Point(pos, pos.add(0, height.get(), 0)));

        // 清理过期的点
        long now = System.currentTimeMillis();
        points.removeIf(p -> now - p.time > lifeTime.get());

        // 限制点数
        while (points.size() > maxPoints.get()) {
            points.remove(0);
        }
    }

    @EventHandler
    public void onRender3D(Render3DEvent event) {
        if (points.size() < 2) return;
        if (onlyThirdPerson.get() && mc.options.getPerspective().isFirstPerson()) return;

        long now = System.currentTimeMillis();

        if (antialias.get()) {
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
            GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
            GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
            GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_NICEST);
        }

        Render3DUtil.setup3D();
        Matrix4f matrix = event.getMatrices().peek().getPositionMatrix();
        Vec3d camPos = mc.getEntityRenderDispatcher().camera.getPos();
        float tickDelta = event.getTickDelta();

        // 获取玩家当前的插值位置作为轨迹的头部
        Vec3d headBottom = new Vec3d(
            MathHelper.lerp(tickDelta, mc.player.prevX, mc.player.getX()),
            MathHelper.lerp(tickDelta, mc.player.prevY, mc.player.getY()),
            MathHelper.lerp(tickDelta, mc.player.prevZ, mc.player.getZ())
        );
        Vec3d headTop = headBottom.add(0, height.get(), 0);

        if (mode.get() == Mode.Point) {
            // 渲染从最后一个点到当前玩家位置的连线
            Point lastPoint = points.get(points.size() - 1);
            Color headColor = getPointColor(points.size(), points.size() + 1, now, now);
            Render3DUtil.drawLine(event.getMatrices(), lastPoint.bottom, headBottom, headColor, thickness.get().floatValue());

            for (int i = 0; i < points.size() - 1; i++) {
                Point p1 = points.get(i);
                Point p2 = points.get(i + 1);

                Color color = getPointColor(i, points.size(), p1.time, now);
                Render3DUtil.drawLine(event.getMatrices(), p1.bottom, p2.bottom, color, thickness.get().floatValue());
            }
        } else if (mode.get() == Mode.Line) {
            RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
            BufferBuilder bufferBuilder = Tessellator.getInstance().begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);

            // 渲染历史点
            for (int i = 0; i < points.size(); i++) {
                Point p = points.get(i);
                Color color = getPointColor(i, points.size() + 1, p.time, now);
                int c = color.getRGB();

                bufferBuilder.vertex(matrix, (float) (p.bottom.x - camPos.x), (float) (p.bottom.y - camPos.y), (float) (p.bottom.z - camPos.z)).color(c);
                bufferBuilder.vertex(matrix, (float) (p.top.x - camPos.x), (float) (p.top.y - camPos.y), (float) (p.top.z - camPos.z)).color(c);
            }

            // 渲染到头部的连接
            Color headColor = getPointColor(points.size(), points.size() + 1, now, now);
            int hc = headColor.getRGB();
            bufferBuilder.vertex(matrix, (float) (headBottom.x - camPos.x), (float) (headBottom.y - camPos.y), (float) (headBottom.z - camPos.z)).color(hc);
            bufferBuilder.vertex(matrix, (float) (headTop.x - camPos.x), (float) (headTop.y - camPos.y), (float) (headTop.z - camPos.z)).color(hc);

            BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        }

        Render3DUtil.cleanup3D();

        if (antialias.get()) {
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
            GL11.glDisable(GL11.GL_POLYGON_SMOOTH);
        }
    }

    private Color getPointColor(int index, int total, long time, long now) {
        Color baseColor;
        float speed = colorSpeed.get().floatValue();

        switch (colorMode.get()) {
            case Single -> baseColor = trailColor.get();
            case Double -> {
                float ratio = (float) Math.sin((index * 0.1) + (System.currentTimeMillis() / 1000.0 * speed));
                ratio = (ratio + 1f) / 2f;
                baseColor = ColorUtil.interpolateColor(trailColor.get(), secondColor.get(), ratio);
            }
            case Client -> baseColor = ClickGui.color(index * 10);
            case Rainbow -> {
                float hue = (float) ((System.currentTimeMillis() / 10000.0 * speed + index * 0.01) % 1.0);
                baseColor = Color.getHSBColor(hue, 0.7f, 1.0f);
            }
            default -> baseColor = trailColor.get();
        }

        int finalAlpha = alpha.get();
        if (fade.get()) {
            float progress = 1.0f - (float) (now - time) / lifeTime.get();
            progress = MathHelper.clamp(progress, 0, 1);
            finalAlpha = (int) (finalAlpha * progress);
        }

        return new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), finalAlpha);
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        points.clear();
    }

    private static class Point {
        private final Vec3d bottom;
        private final Vec3d top;
        private final long time;

        public Point(Vec3d bottom, Vec3d top) {
            this.bottom = bottom;
            this.top = top;
            this.time = System.currentTimeMillis();
        }
    }
}
