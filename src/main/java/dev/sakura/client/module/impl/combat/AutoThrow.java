package dev.sakura.client.module.impl.combat;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.movement.Scaffold;
import dev.sakura.client.module.impl.movement.Stuck;
import dev.sakura.client.utils.math.MathUtil;
import dev.sakura.client.utils.player.InvUtil;
import dev.sakura.client.utils.player.MoveUtil;
import dev.sakura.client.utils.rotation.MovementFix;
import dev.sakura.client.utils.rotation.Priority;
import dev.sakura.client.utils.rotation.Rotation;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class AutoThrow extends Module {
    public AutoThrow() {
        super("AutoThrow", "自动投掷", Category.Combat);
    }

    private final NumberValue<Double> minRange = new NumberValue<>("Min Range", "最小距离", 4.5, 0.0, 6.0, 0.25);
    private final NumberValue<Double> maxRange = new NumberValue<>("Max Range", "最大距离", 7.0, 5.0, 20.0, 0.25);
    private final NumberValue<Integer> minDelay = new NumberValue<>("Min Delay", "最小延迟", 100, 0, 1000, 10);
    private final NumberValue<Integer> maxDelay = new NumberValue<>("Max Delay", "最大延迟", 300, 0, 1000, 10);
    private final NumberValue<Integer> swapDelay = new NumberValue<>("Swap Back Delay", "切换延迟", 200, 0, 500, 10);
    private final BoolValue wallCheck = new BoolValue("Wall Check", "墙体检测", true);
    private final NumberValue<Integer> rotationSpeed = new NumberValue<>("Rotation Speed", "旋转速度", 10, 1, 10, 1);
    private final NumberValue<Integer> rotationBackSpeed = new NumberValue<>("Rotation Back Speed", "回转速度", 10, 0, 10, 1);

    private ThrowInfo pendingPlan;
    private Rotation pendingRotation;
    private boolean pendingSwapBack = false;
    private final TimerUtil timer = new TimerUtil();
    private final TimerUtil swapTimer = new TimerUtil();

    @Override
    protected void onEnable() {
        pendingPlan = null;
        pendingRotation = null;
        pendingSwapBack = false;

        timer.reset();
        swapTimer.reset();
    }

    @Override
    protected void onDisable() {
        pendingPlan = null;
        pendingRotation = null;
        pendingSwapBack = false;
        timer.reset();
        swapTimer.reset();
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (pendingSwapBack && swapTimer.passedMillise(swapDelay.get())) {
            InvUtil.swapBack();
            pendingSwapBack = false;
        }

        if (Sakura.MODULES.getModule(Scaffold.class).isEnabled() || Sakura.MODULES.getModule(Stuck.class).isEnabled()) {
            pendingPlan = null;
            pendingRotation = null;
            return;
        }

        Rotation rotation;

        if (pendingPlan != null) {
            if (pendingRotation != null && isFacing(pendingRotation)) {
                Rotation backRotation = new Rotation(mc.player.getYaw(), mc.player.getPitch());
                throwItem(pendingPlan, pendingRotation);
                pendingPlan = null;
                pendingRotation = null;
                Managers.ROTATION.setRotations(backRotation, rotationBackSpeed.get(), MovementFix.NORMAL, Priority.Highest);
            }
            return;
        }

        ThrowInfo plan = updateThrowInfo();
        if (plan == null) {
            return;
        }

        LivingEntity target = Managers.COMBAT.getClosestEnemy(minRange.get(), maxRange.get());
        if (target == null) {
            return;
        }

        if (wallCheck.get() && !mc.player.canSee(target)) {
            return;
        }

        if (timer.passedMillise(MathUtil.getRandom(minDelay.get(), maxDelay.get())) && canRotate(plan.hand)) {
            rotation = getRotationToEntity(target);
            Managers.ROTATION.setRotations(rotation, rotationSpeed.get(), MovementFix.NORMAL, Priority.Highest);
            pendingPlan = plan;
            pendingRotation = rotation;
            timer.reset();
        }
    }

    private void throwItem(ThrowInfo plan, Rotation rotation) {
        float originalYaw = mc.player.getYaw();
        float originalPitch = mc.player.getPitch();
        if (rotation != null) {
            mc.player.setYaw(rotation.yaw);
            mc.player.setPitch(rotation.pitch);
        }
        if (plan.hand == Hand.MAIN_HAND && mc.player.getInventory().getSelectedSlot() != plan.hotbarSlot) {
            InvUtil.swap(plan.hotbarSlot, true);
        }

        mc.interactionManager.interactItem(mc.player, plan.hand);
        mc.player.swingHand(plan.hand);

        if (rotation != null) {
            mc.player.setYaw(originalYaw);
            mc.player.setPitch(originalPitch);
        }

        pendingSwapBack = true;
        swapTimer.reset();
    }

    private ThrowInfo updateThrowInfo() {
        ItemStack offhand = mc.player.getOffHandStack();
        if (isThrowable(offhand)) {
            return new ThrowInfo(Hand.OFF_HAND, -1);
        }

        int selected = mc.player.getInventory().getSelectedSlot();
        ItemStack mainhand = mc.player.getInventory().getStack(selected);
        if (isThrowable(mainhand)) {
            return new ThrowInfo(Hand.MAIN_HAND, selected);
        }

        for (int hotbar = 0; hotbar < 9; hotbar++) {
            ItemStack stack = mc.player.getInventory().getStack(hotbar);
            if (isThrowable(stack)) {
                return new ThrowInfo(Hand.MAIN_HAND, hotbar);
            }
        }
        return null;
    }

    private boolean canRotate(Hand hand) {
        if (mc.player.isUsingItem()) {
            return false;
        }
        ItemStack stack = hand == Hand.MAIN_HAND ? mc.player.getMainHandStack() : mc.player.getOffHandStack();
        if (stack.isEmpty()) {
            return true;
        }
        Item item = stack.getItem();
        if (item instanceof EnderPearlItem) {
            return false;
        }
        if (item instanceof BowItem) {
            return false;
        }
        if (item instanceof PotionItem || item instanceof SplashPotionItem || item instanceof LingeringPotionItem) {
            return false;
        }
        return !stack.contains(DataComponentTypes.FOOD);
    }

    private boolean isFacing(Rotation target) {
        Rotation current = Managers.ROTATION.rotations;
        if (current == null) {
            current = new Rotation(mc.player.getYaw(), mc.player.getPitch());
        }
        float yawDiff = Math.abs(MathHelper.wrapDegrees(current.yaw - target.yaw));
        float pitchDiff = Math.abs(MathHelper.wrapDegrees(current.pitch - target.pitch));
        return yawDiff <= 2.0f && pitchDiff <= 2.0f;
    }

    private Rotation getRotationToEntity(LivingEntity target) {
        Vec3d origin = mc.player.getEyePos().add(0.0, -0.1, 0.0);
        Box targetBox = target.getBoundingBox();
        Vec3d targetCenter = new Vec3d((targetBox.minX + targetBox.maxX) * 0.5, (targetBox.minY + targetBox.maxY) * 0.5, (targetBox.minZ + targetBox.maxZ) * 0.5);
        int maxTicks = MathHelper.clamp((int) Math.ceil(mc.player.distanceTo(target) / 0.2), 8, 60);

        Rotation bestRotation = null;
        double bestError = Double.MAX_VALUE;

        for (int ticks = 1; ticks <= maxTicks; ticks++) {
            Vec3d targetMotion = MoveUtil.getMotionVec(target, ticks, true);
            Vec3d predictedCenter = targetCenter.add(targetMotion);
            Box predictedBox = targetBox.offset(targetMotion);

            Rotation initial = rotationToPoint(origin, predictedCenter);
            Rotation refined = refineRotation(origin, predictedCenter, predictedBox, ticks, initial);
            double error = simulateError(origin, refined.yaw, refined.pitch, predictedBox, ticks);

            if (error < bestError) {
                bestError = error;
                bestRotation = refined;
                if (bestError <= 1.0E-4) {
                    break;
                }
            }
        }

        if (bestRotation == null) {
            return rotationToPoint(origin, targetCenter);
        }
        return bestRotation;
    }

    private Rotation refineRotation(Vec3d origin, Vec3d targetCenter, Box targetBox, int ticks, Rotation initial) {
        float bestYaw = initial.yaw;
        float bestPitch = initial.pitch;
        double bestError = simulateError(origin, bestYaw, bestPitch, targetBox, ticks);
        float stepYaw = 4.0f;
        float stepPitch = 4.0f;

        for (int i = 0; i < 6; i++) {
            float baseYaw = bestYaw;
            float basePitch = bestPitch;
            for (int yawStep = -1; yawStep <= 1; yawStep++) {
                for (int pitchStep = -1; pitchStep <= 1; pitchStep++) {
                    float yaw = baseYaw + stepYaw * yawStep;
                    float pitch = MathHelper.clamp(basePitch + stepPitch * pitchStep, -89.0f, 89.0f);
                    double error = simulateError(origin, yaw, pitch, targetBox, ticks);
                    if (error < bestError) {
                        bestError = error;
                        bestYaw = yaw;
                        bestPitch = pitch;
                    }
                }
            }
            stepYaw *= 0.5f;
            stepPitch *= 0.5f;
        }

        return new Rotation(MathHelper.wrapDegrees(bestYaw), MathHelper.clamp(bestPitch, -89.0f, 89.0f));
    }

    private double simulateError(Vec3d origin, float yaw, float pitch, Box targetBox, int ticks) {
        Vec3d pos = origin;
        Vec3d velocity = getThrowVelocity(yaw, pitch);

        for (int i = 0; i < ticks; i++) {
            pos = pos.add(velocity);
            double drag = isWater(pos) ? 0.8 : 0.99;
            velocity = velocity.multiply(drag);
            velocity = velocity.add(0.0, -0.03, 0.0);
        }

        return distanceSquaredToBox(pos, targetBox);
    }

    private Vec3d getThrowVelocity(float yaw, float pitch) {
        float yawRad = yaw * ((float) Math.PI / 180.0F);
        float pitchRad = pitch * ((float) Math.PI / 180.0F);
        float x = -MathHelper.sin(yawRad) * MathHelper.cos(pitchRad);
        float y = -MathHelper.sin(pitchRad);
        float z = MathHelper.cos(yawRad) * MathHelper.cos(pitchRad);
        Vec3d velocity = new Vec3d(x, y, z).normalize().multiply(1.5f);
        Vec3d movement = mc.player.getMovement();
        return velocity.add(movement.x, mc.player.isOnGround() ? 0.0 : movement.y, movement.z);
    }

    private Rotation rotationToPoint(Vec3d origin, Vec3d target) {
        Vec3d diff = target.subtract(origin);
        double distance = Math.hypot(diff.x, diff.z);
        float yaw = (float) (MathHelper.atan2(diff.z, diff.x) * MathUtil.TO_DEGREES) - 90.0f;
        float pitch = (float) (-(MathHelper.atan2(diff.y, distance) * MathUtil.TO_DEGREES));
        return new Rotation(yaw, pitch);
    }

    private double distanceSquaredToBox(Vec3d point, Box box) {
        double x = MathHelper.clamp(point.x, box.minX, box.maxX);
        double y = MathHelper.clamp(point.y, box.minY, box.maxY);
        double z = MathHelper.clamp(point.z, box.minZ, box.maxZ);
        double dx = point.x - x;
        double dy = point.y - y;
        double dz = point.z - z;
        return dx * dx + dy * dy + dz * dz;
    }

    private boolean isWater(Vec3d pos) {
        BlockPos blockPos = BlockPos.ofFloored(pos.x, pos.y, pos.z);
        return mc.world.getBlockState(blockPos).getBlock() == Blocks.WATER;
    }

    private boolean isThrowable(ItemStack stack) {
        return !stack.isEmpty() && (stack.getItem() == Items.EGG || stack.getItem() == Items.SNOWBALL);
    }

    private record ThrowInfo(Hand hand, int hotbarSlot) {
    }
}
