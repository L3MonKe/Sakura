package dev.sakura.client.module.impl.movement;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.input.MoveInputEvent;
import dev.sakura.client.event.impl.render.Render3DEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.combat.KillAura;
import dev.sakura.client.utils.color.ColorUtil;
import dev.sakura.client.utils.player.MoveUtil;
import dev.sakura.client.utils.render.Render3DUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

public class TargetStrafe extends Module {

    private final NumberValue<Double> range = new NumberValue<>("Range", "范围", 3.0, 0.1, 8.0, 0.1);
    private final NumberValue<Double> followRange = new NumberValue<>("Follow Range", "跟随范围", 4.0, 0.1, 10.0, 0.1);
    private final BoolValue controlDirection = new BoolValue("Control Direction", "控制方向", true);
    private final BoolValue edgeCheck = new BoolValue("Edge Check", "边缘检测", true);
    private final BoolValue voidCheck = new BoolValue("Void Check", "虚空检测", true);
    private final BoolValue jumpOnly = new BoolValue("Jump Only", "仅跳跃时", true);
    private final BoolValue moveOnly = new BoolValue("Move Only", "仅移动时", true);
    private final BoolValue drawRadius = new BoolValue("Draw Radius", "绘制半径", true);
    private final EnumValue<ColorMode> colorMode = new EnumValue<>("Color Mode", "颜色模式", ColorMode.Static);
    private final ColorValue circleColor = new ColorValue("Color", "颜色", new Color(255, 255, 255), () -> colorMode.is(ColorMode.Static));
    private final ColorValue gradientColor1 = new ColorValue("Gradient Color 1", "渐变色1", new Color(0, 255, 255), () -> colorMode.is(ColorMode.Gradient));
    private final ColorValue gradientColor2 = new ColorValue("Gradient Color 2", "渐变色2", new Color(255, 0, 255), () -> colorMode.is(ColorMode.Gradient));
    private final NumberValue<Integer> gradientSpeed = new NumberValue<>("Gradient Speed", "渐变速度", 30, 1, 100, 1, () -> colorMode.is(ColorMode.Gradient));
    private final NumberValue<Integer> gradientStep = new NumberValue<>("Gradient Step", "渐变跨度", 4, 1, 50, 1, () -> colorMode.is(ColorMode.Gradient));
    private final NumberValue<Integer> circlePoints = new NumberValue<>("Points", "边数", 32, 3, 120, 1);

    private final BoolValue glow = new BoolValue("Glow", "发光", true);
    private final NumberValue<Double> glowRadius = new NumberValue<>("Glow Radius", "发光半径", 2.0, 1.0, 5.0, 0.5, glow::get);
    private final NumberValue<Integer> glowOpacity = new NumberValue<>("Glow Opacity", "发光透明度", 100, 0, 255, 5, glow::get);

    private int direction = 1;

    public enum ColorMode {
        Static, Gradient
    }

    public TargetStrafe() {
        super("TargetStrafe", "目标环绕", Category.Movement);
    }

    @EventHandler
    public void onMoveInput(MoveInputEvent event) {
        if (nullCheck()) return;

        KillAura killAura = Sakura.MODULES.getModule(KillAura.class);
        if (killAura == null || !killAura.isEnabled()) return;
        LivingEntity target = killAura.getCurrentTarget();

        if (target == null) return;

        if (moveOnly.get() && !MoveUtil.isMoving()) return;
        if (jumpOnly.get() && !mc.options.jumpKey.isPressed()) return;

        double distance = mc.player.distanceTo(target);
        if (distance > followRange.get()) return;

        if (mc.player.horizontalCollision) {
            direction = -direction;
        }

        if (controlDirection.get()) {
            if (mc.options.leftKey.isPressed()) direction = 1;
            if (mc.options.rightKey.isPressed()) direction = -1;
        }

        double speed = MoveUtil.getSpeed();
        double strafeYaw = Math.atan2(target.getZ() - mc.player.getZ(), target.getX() - mc.player.getX());

        Vec3d strafeVec = computeDirectionVec(strafeYaw, distance, speed, range.get().floatValue(), direction);
        Vec3d pointCoords = new Vec3d(mc.player.getX(), mc.player.getY(), mc.player.getZ()).add(strafeVec);

        if (!validatePoint(pointCoords)) {
            direction = -direction;
            strafeVec = computeDirectionVec(strafeYaw, distance, speed, range.get().floatValue(), direction);
        }

        double targetYaw = Math.toDegrees(Math.atan2(-strafeVec.x, strafeVec.z));
        MoveUtil.fixMovement(event, (float) targetYaw);

        if (event.getForward() < 0.8f) {
            mc.player.setSprinting(false);
        }
    }

    private Vec3d computeDirectionVec(double strafeYaw, double distance, double speed, float range, int direction) {
        double yaw = strafeYaw - (0.5f * Math.PI);
        double encirclement = distance - range;

        if (encirclement < -speed) encirclement = -speed;

        double encirclementX = -Math.sin(yaw) * encirclement;
        double encirclementZ = Math.cos(yaw) * encirclement;

        double strafeX = -Math.sin(strafeYaw) * speed * direction;
        double strafeZ = Math.cos(strafeYaw) * speed * direction;

        return new Vec3d(encirclementX + strafeX, 0.0, encirclementZ + strafeZ);
    }

    private boolean validatePoint(Vec3d point) {
        Box box = mc.player.getDimensions(mc.player.getPose()).getBoxAt(point);
        if (mc.world.getBlockCollisions(mc.player, box).iterator().hasNext()) return false;

        if (edgeCheck.get() && isCloseToFall(point)) return false;

        if (voidCheck.get() && wouldFallIntoVoid(point)) return false;

        return true;
    }

    private boolean isCloseToFall(Vec3d point) {
        point = new Vec3d(point.x, Math.floor(point.y), point.z);
        Box box = mc.player.getDimensions(mc.player.getPose()).getBoxAt(point)
                .expand(-0.05, 0.0, -0.05) // 稍微收缩
                .offset(0.0, -1.2, 0.0); // 检查下方 1.2 格

        return !mc.world.getBlockCollisions(mc.player, box).iterator().hasNext();
    }

    private boolean wouldFallIntoVoid(Vec3d point) {
        for (int i = 0; i < 5; i++) {
            BlockPos pos = BlockPos.ofFloored(point.x, point.y - i, point.z);
            if (!mc.world.getBlockState(pos).getCollisionShape(mc.world, pos).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @EventHandler
    public void onRender3D(Render3DEvent event) {
        if (!drawRadius.get()) return;

        KillAura killAura = Sakura.MODULES.getModule(KillAura.class);
        if (killAura == null || !killAura.isEnabled()) return;
        LivingEntity target = killAura.getCurrentTarget();
        if (target == null) return;

        drawCircle(event, target, range.get(), circleColor.get());
    }

    private void drawCircle(Render3DEvent event, LivingEntity entity, double radius, Color color) {
        MatrixStack stack = event.getMatrices();
        double x = MathHelper.lerp(event.getTickDelta(), entity.lastRenderX, entity.getX());
        double y = MathHelper.lerp(event.getTickDelta(), entity.lastRenderY, entity.getY());
        double z = MathHelper.lerp(event.getTickDelta(), entity.lastRenderZ, entity.getZ());

        int points = circlePoints.get();
        double anglePerPoint = 360.0 / points;

        for (int i = 0; i < points; i++) {
            double rad = Math.toRadians(i * anglePerPoint);
            double radNext = Math.toRadians((i + 1) * anglePerPoint);

            double x1 = Math.sin(rad) * radius;
            double z1 = Math.cos(rad) * radius;
            double x2 = Math.sin(radNext) * radius;
            double z2 = Math.cos(radNext) * radius;

            Color c;
            if (colorMode.is(ColorMode.Gradient)) {
                // 计算双色渐变
                c = ColorUtil.interpolateColorsBackAndForth(
                        gradientSpeed.get(),
                        i * gradientStep.get(),
                        gradientColor1.get(),
                        gradientColor2.get(),
                        false
                );
            } else {
                c = color;
            }

            if (glow.get()) {
                float radiusVal = glowRadius.get().floatValue();
                int opacityVal = glowOpacity.get();
                for (int w = 1; w <= 3; w++) {
                    Render3DUtil.drawLine(stack,
                            new Vec3d(x + x1, y, z + z1),
                            new Vec3d(x + x2, y, z + z2),
                            ColorUtil.applyOpacity(c, (opacityVal / (w * 1.5f)) / 255.0f),
                            2.0f + w * radiusVal);
                }
            }

            Render3DUtil.drawLine(stack,
                    new Vec3d(x + x1, y, z + z1),
                    new Vec3d(x + x2, y, z + z2),
                    c, 2.0f);
        }
    }
}
