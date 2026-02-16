package dev.sakura.client.module.impl.combat;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.player.MotionEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.manager.impl.RotationManager;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.movement.Scaffold;
import dev.sakura.client.module.impl.movement.Stuck;
import dev.sakura.client.utils.math.MathUtil;
import dev.sakura.client.utils.rotation.MovementFix;
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

    private int rotationSet;
    private int swapBack = -1;
    private ThrowPlan pendingPlan;
    private final TimerUtil timer = new TimerUtil();

    private static final double PROJECTILE_SPEED = 0.6;
    private static final double PROJECTILE_GRAVITY = 0.006;

    @EventHandler
    public void onMotion(MotionEvent event) {
        if (event.getType() != EventType.PRE) {
            if (swapBack != -1) {
                mc.player.getInventory().setSelectedSlot(swapBack);
                swapBack = -1;
            }
            return;
        }

        if (Sakura.MODULES.getModule(Scaffold.class).isEnabled() || Sakura.MODULES.getModule(Stuck.class).isEnabled()) {
            rotationSet = 0;
            pendingPlan = null;
            return;
        }

        Rotation rotation;

        ThrowPlan plan = updateThrowPlan();
        if (plan == null) {
            return;
        }

        if (rotationSet > 0) {
            rotationSet--;
            if (rotationSet == 0) {
                if (pendingPlan != null) {
                    throwFromPlan(pendingPlan);
                    pendingPlan = null;
                }
            }
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
            Managers.ROTATION.setRotations(rotation, rotationSpeed.get(), MovementFix.NORMAL, RotationManager.Priority.Highest);
            rotationSet = 2;
            pendingPlan = plan;
            timer.reset();
        }
    }

    private void throwFromPlan(ThrowPlan plan) {
        if (plan.hand == Hand.MAIN_HAND) {
            int originalHotbar = mc.player.getInventory().getSelectedSlot();
            boolean shouldSwap = originalHotbar != plan.hotbarSlot;
            if (shouldSwap) {
                mc.player.getInventory().setSelectedSlot(plan.hotbarSlot);
                swapBack = originalHotbar;
            }
        }
        mc.interactionManager.interactItem(mc.player, plan.hand);
        mc.player.swingHand(plan.hand);
    }

    private ThrowPlan updateThrowPlan() {
        ItemStack offhand = mc.player.getOffHandStack();
        if (isThrowable(offhand)) {
            return new ThrowPlan(Hand.OFF_HAND, -1);
        }

        int selected = mc.player.getInventory().getSelectedSlot();
        ItemStack mainhand = mc.player.getInventory().getStack(selected);
        if (isThrowable(mainhand)) {
            return new ThrowPlan(Hand.MAIN_HAND, selected);
        }

        for (int hotbar = 0; hotbar < 9; hotbar++) {
            ItemStack stack = mc.player.getInventory().getStack(hotbar);
            if (isThrowable(stack)) {
                return new ThrowPlan(Hand.MAIN_HAND, hotbar);
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

    private Rotation getRotationToEntity(LivingEntity target) {
        Vec3d velocity = target.getVelocity();
        double targetX = target.getX();
        double targetY = target.getY() + target.getHeight() * 0.6;
        double targetZ = target.getZ();

        double time = 0.0;
        for (int i = 0; i < 3; i++) {
            double predictX = targetX + velocity.x * time;
            double predictZ = targetZ + velocity.z * time;
            double dx = predictX - mc.player.getX();
            double dz = predictZ - mc.player.getZ();
            double horizontal = Math.sqrt(dx * dx + dz * dz);
            time = horizontal / PROJECTILE_SPEED;
        }

        double predictX = targetX + velocity.x * time;
        double predictY = targetY + velocity.y * time;
        double predictZ = targetZ + velocity.z * time;

        double x = predictX - mc.player.getX();
        double z = predictZ - mc.player.getZ();
        double h = predictY - (mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()));
        double horizontal = Math.sqrt(x * x + z * z);

        float yaw = (float) (Math.toDegrees(Math.atan2(z, x)) - 90.0F);
        float pitch = -getTrajAngleSolutionLow((float) horizontal, (float) h, (float) PROJECTILE_SPEED, (float) PROJECTILE_GRAVITY);
        return new Rotation(yaw, MathHelper.clamp(pitch, -90.0F, 90.0F));
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

    private record ThrowPlan(Hand hand, int hotbarSlot) {
    }
}
