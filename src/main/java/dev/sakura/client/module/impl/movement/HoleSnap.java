package dev.sakura.client.module.impl.movement;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.sakura.client.Sakura;
import dev.sakura.client.events.EventType;
import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.events.packet.PacketEvent;
import dev.sakura.client.events.player.MoveEvent;
import dev.sakura.client.events.render.Render3DEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.color.ColorUtil;
import dev.sakura.client.utils.player.MovementUtil;
import dev.sakura.client.utils.world.HoleUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

import java.awt.*;

public class HoleSnap extends Module {
    private final NumberValue<Double> range = new NumberValue<>("Range", "范围", 5.0, 1.0, 10.0, 0.5);
    private final NumberValue<Integer> timeout = new NumberValue<>("Timeout", "超时", 40, 0, 100, 1);
    private final BoolValue doubleHole = new BoolValue("DoubleHole", "双孔", true);
    private final BoolValue anyHole = new BoolValue("AnyHole", "任意孔", true);
    private final BoolValue includeUp = new BoolValue("Up", "包含上方", true);
    private final ColorValue color = new ColorValue("Color", "颜色", new Color(255, 255, 255, 100));
    private final NumberValue<Double> circleSize = new NumberValue<>("CircleSize", "圆环大小", 1.0, 0.1, 2.5, 0.1);
    private final BoolValue fade = new BoolValue("Fade", "淡出", true);
    private final NumberValue<Integer> segments = new NumberValue<>("Segments", "分段数", 180, 30, 360, 10);

    private BlockPos holePos;
    private Vec3d targetPos;
    private int stuckTicks;
    private int enabledTicks;
    private boolean resetMove;

    public HoleSnap() {
        super("HoleSnap", "进坑", Category.Movement);
    }

    @Override
    protected void onEnable() {
        if (nullCheck()) {
            setState(false);
            return;
        }
        resetMove = false;
        stuckTicks = 0;
        enabledTicks = 0;
        holePos = HoleUtil.getHole(range.get().floatValue(), doubleHole.get(), anyHole.get(), includeUp.get());
        if (holePos == null) {
            setState(false);
        }
    }

    @Override
    protected void onDisable() {
        holePos = null;
        targetPos = null;
        stuckTicks = 0;
        enabledTicks = 0;
        if (mc.player != null && resetMove) {
            MovementUtil.setMotionX(0);
            MovementUtil.setMotionZ(0);
        }
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (event.getType() != EventType.RECEIVE) return;
        if (event.getPacket() instanceof PlayerPositionLookS2CPacket) {
            setState(false);
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        holePos = HoleUtil.getHole(range.get().floatValue(), doubleHole.get(), anyHole.get(), includeUp.get());
        if (holePos == null) {
            setState(false);
            return;
        }

        enabledTicks++;
        if (enabledTicks > timeout.get()) {
            setState(false);
            return;
        }

        setSuffix(String.valueOf(enabledTicks));
    }

    @EventHandler
    public void onMove(MoveEvent event) {
        if (mc.player == null || !mc.player.isAlive() || mc.player.isGliding()) {
            setState(false);
            return;
        }

        if (stuckTicks > 8) {
            setState(false);
            return;
        }

        if (holePos == null) {
            setState(false);
            return;
        }

        Vec3d playerPos = mc.player.getPos();
        targetPos = new Vec3d(holePos.getX() + 0.5, mc.player.getY(), holePos.getZ() + 0.5);

        if (HoleUtil.isDoubleHole(holePos)) {
            Direction facing = HoleUtil.is3Block(holePos);
            if (facing != null) {
                targetPos = targetPos.add(
                        facing.getOffsetX() * 0.5,
                        facing.getOffsetY() * 0.5,
                        facing.getOffsetZ() * 0.5
                );
            }
        }

        resetMove = true;
        float rotation = getRotationTo(playerPos, targetPos).x;
        float yawRad = rotation / 180.0f * (float) Math.PI;
        double dist = playerPos.distanceTo(targetPos);
        double cappedSpeed = Math.min(0.2873, dist);
        double x = -Math.sin(yawRad) * cappedSpeed;
        double z = Math.cos(yawRad) * cappedSpeed;

        event.setX(x);
        event.setZ(z);

        if (Math.abs(x) < 0.1 && Math.abs(z) < 0.1 && playerPos.y <= holePos.getY() + 0.5) {
            setState(false);
            return;
        }

        if (mc.player.horizontalCollision) {
            stuckTicks++;
        } else {
            stuckTicks = 0;
        }
    }

    @EventHandler
    public void onRender3D(Render3DEvent event) {
        if (targetPos == null || holePos == null) return;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();

        Color c = color.get();
        Vec3d pos = new Vec3d(targetPos.x, holePos.getY(), targetPos.z);

        if (fade.get()) {
            double temp = 0.01;
            for (double i = 0; i < circleSize.get(); i += temp) {
                drawCircle(event.getMatrices(), ColorUtil.applyOpacity(c, (int) Math.min(c.getAlpha() * 2 / (circleSize.get() / temp), 255)), i, pos, segments.get());
            }
        } else {
            drawCircle(event.getMatrices(), c, circleSize.get(), pos, segments.get());
        }

        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }

    private Vec2f getRotationTo(Vec3d from, Vec3d to) {
        Vec3d vec = to.subtract(from);
        double xz = Math.hypot(vec.x, vec.z);
        double yaw = normalizeAngle(Math.toDegrees(Math.atan2(vec.z, vec.x)) - 90.0);
        double pitch = normalizeAngle(Math.toDegrees(-Math.atan2(vec.y, xz)));
        return new Vec2f((float) yaw, (float) pitch);
    }

    private double normalizeAngle(double angle) {
        angle = angle % 360.0;
        if (angle >= 180.0) angle -= 360.0;
        if (angle < -180.0) angle += 360.0;
        return angle;
    }

    private void drawCircle(MatrixStack matrixStack, Color color, double circleSize, Vec3d pos, int segments) {
        if (mc.world == null) return;

        Vec3d camPos = mc.gameRenderer.getCamera().getPos();
        Matrix4f matrix = matrixStack.peek().getPositionMatrix();

        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;
        float a = color.getAlpha() / 255f;

        BufferBuilder bufferBuilder = Tessellator.getInstance().begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);

        for (double i = 0; i < 360; i += (360.0 / segments)) {
            double x = Math.sin(Math.toRadians(i)) * circleSize;
            double z = Math.cos(Math.toRadians(i)) * circleSize;
            Vec3d tempPos = new Vec3d(pos.x + x, pos.y, pos.z + z).subtract(camPos);
            bufferBuilder.vertex(matrix, (float) tempPos.x, (float) tempPos.y, (float) tempPos.z).color(r, g, b, a);
        }

        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
    }

    public static boolean isActive() {
        return Sakura.MODULES.getModule(HoleSnap.class).isEnabled();
    }
}