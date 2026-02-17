package dev.sakura.client.module.impl.combat;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.mixin.accessor.IMinecraftClient;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.movement.Scaffold;
import dev.sakura.client.module.impl.movement.Stuck;
import dev.sakura.client.utils.math.MathUtil;
import dev.sakura.client.utils.player.InvUtil;
import dev.sakura.client.utils.rotation.MovementFix;
import dev.sakura.client.utils.rotation.Priority;
import dev.sakura.client.utils.rotation.Rotation;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class AutoThrow extends Module {
    public AutoThrow() {
        super("AutoThrow", "自动投掷", Category.Combat);
    }

    private final NumberValue<Integer> minRange = new NumberValue<>("Min Range", "最小距离", 3, 0, 6, 1);
    private final NumberValue<Integer> maxRange = new NumberValue<>("Max Range", "最大距离", 10, 6, 20, 1);
    private final NumberValue<Integer> minDelay = new NumberValue<>("Min Delay", "最小延迟", 100, 0, 1000, 10);
    private final NumberValue<Integer> maxDelay = new NumberValue<>("Max Delay", "最大延迟", 300, 0, 1000, 10);
    private final BoolValue wallCheck = new BoolValue("Wall Check", "墙体检测", true);
    private final NumberValue<Integer> rotationSpeed = new NumberValue<>("Rotation Speed", "旋转速度", 10, 1, 10, 1);
    private final NumberValue<Integer> rotationBackSpeed = new NumberValue<>("Rotation Back Speed", "回转速度", 10, 0, 10, 1);

    private ThrowInfo pendingPlan;
    private Rotation pendingRotation;
    private final TimerUtil timer = new TimerUtil();

    private static final double speed = 1.5;
    private static final double gravity = 0.03;

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

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

        ((IMinecraftClient) mc).hookDoItemUse();

        if (rotation != null) {
            mc.player.setYaw(originalYaw);
            mc.player.setPitch(originalPitch);
        }

        InvUtil.swapBack();
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
        Vec3d velocity = target.getVelocity();
        if (target.isOnGround()) {
            velocity = new Vec3d(velocity.x, 0.0, velocity.z);
        }

        Vec3d shooterPos = new Vec3d(mc.player.getX(), mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()), mc.player.getZ());
        Vec3d baseTarget = new Vec3d(target.getX(), target.getY() + target.getHeight() * 0.6, target.getZ());
        Vec3d shooterMovement = getShooterMovement();

        double time = 0.0;
        ThrowSolution solution = null;
        for (int i = 0; i < 3; i++) {
            Vec3d predicted = baseTarget.add(velocity.multiply(time));
            solution = solveThrowSolution(shooterPos, predicted, shooterMovement);
            if (solution == null) break;
            time = solution.time;
        }

        if (solution != null) {
            return solution.rotation;
        }

        double predictX = baseTarget.x + velocity.x * time;
        double predictY = baseTarget.y + velocity.y * time;
        double predictZ = baseTarget.z + velocity.z * time;

        double x = predictX - shooterPos.x;
        double z = predictZ - shooterPos.z;
        double h = predictY - shooterPos.y;
        double horizontal = Math.sqrt(x * x + z * z);

        float yaw = (float) (Math.toDegrees(Math.atan2(z, x)) - 90.0F);
        float pitch = -getTrajAngleSolutionLow((float) horizontal, (float) h, (float) speed, (float) gravity);
        return new Rotation(yaw, MathHelper.clamp(pitch, -90.0F, 90.0F));
    }

    private ThrowSolution solveThrowSolution(Vec3d from, Vec3d to, Vec3d shooterMovement) {
        double dx = to.x - from.x;
        double dy = to.y - from.y;
        double dz = to.z - from.z;
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        if (horizontal < 1.0E-6) {
            return null;
        }

        double vpx = shooterMovement.x;
        double vpz = shooterMovement.z;
        double vpy = mc.player.isOnGround() ? 0.0 : shooterMovement.y;

        double tMin = 0.05;
        double tMax = Math.max(1.0, horizontal / speed * 3.0);

        for (int i = 0; i < 30; i++) {
            double t = (tMin + tMax) * 0.5;
            double vx = dx / t - vpx;
            double vz = dz / t - vpz;
            double vy = (dy + 0.5 * gravity * t * t) / t - vpy;
            double requiredSpeedSq = vx * vx + vy * vy + vz * vz;
            if (requiredSpeedSq > speed * speed) {
                tMin = t;
            } else {
                tMax = t;
            }
        }

        double t = tMax;
        double vx = dx / t - vpx;
        double vz = dz / t - vpz;
        double vy = (dy + 0.5 * gravity * t * t) / t - vpy;
        double requiredSpeedSq = vx * vx + vy * vy + vz * vz;
        if (requiredSpeedSq > speed * speed * 1.1) {
            return null;
        }

        double horizSpeed = Math.sqrt(vx * vx + vz * vz);
        float yaw = (float) (Math.toDegrees(Math.atan2(vz, vx)) - 90.0F);
        float pitch = (float) -Math.toDegrees(Math.atan2(vy, horizSpeed));
        return new ThrowSolution(new Rotation(yaw, MathHelper.clamp(pitch, -90.0F, 90.0F)), t);
    }

    private Vec3d getShooterMovement() {
        Vec3d movement = mc.player.getMovement();
        if (mc.player.isOnGround()) {
            return new Vec3d(movement.x, 0.0, movement.z);
        }
        return movement;
    }

    private float getTrajAngleSolutionLow(float distance, float height, float velocity, float gravity) {
        float v2 = velocity * velocity;
        float under = v2 * v2 - gravity * (gravity * distance * distance + 2.0f * height * v2);
        if (under <= 0.0f) {
            return (float) Math.toDegrees(Math.atan2(height, distance));
        }
        return (float) Math.toDegrees(Math.atan((v2 - Math.sqrt(under)) / (gravity * distance)));
    }

    private boolean isThrowable(ItemStack stack) {
        return !stack.isEmpty() && (stack.getItem() == Items.EGG || stack.getItem() == Items.SNOWBALL);
    }

    private record ThrowInfo(Hand hand, int hotbarSlot) {
    }

    private record ThrowSolution(Rotation rotation, double time) {
    }
}
