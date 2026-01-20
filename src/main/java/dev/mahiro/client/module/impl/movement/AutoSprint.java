package dev.mahiro.client.module.impl.movement;

import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.events.player.JumpRotationEvent;
import dev.mahiro.client.manager.Managers;
import dev.mahiro.client.manager.impl.RotationManager;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.utils.player.MovementUtil;
import dev.mahiro.client.utils.rotation.MovementFix;
import dev.mahiro.client.utils.vector.Rotation;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.EnumValue;
import dev.mahiro.client.values.impl.MultiBoolValue;
import dev.mahiro.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class AutoSprint extends Module {
    public enum Mode {
        Legit("Legit"),
        Omnidirectional("Omnidirectional"),
        Omnirotational("Omnirotational");

        private final String displayName;

        Mode(String displayName) {
            this.displayName = displayName;
        }
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Legit);

    private final MultiBoolValue ignore = new MultiBoolValue("Ignore", "忽略", List.of(
            new BoolValue("Blindness", "失明", false),
            new BoolValue("Hunger", "饥饿", false),
            new BoolValue("Collision", "碰撞", false)
    ));

    private final MultiBoolValue stopOn = new MultiBoolValue("StopOn", "停止于", List.of(
            new BoolValue("Ground", "地面", true),
            new BoolValue("Air", "空中", true),
            new BoolValue("UsingItem", "使用物品", true)
    ));

    private final NumberValue<Double> rotationSpeed = new NumberValue<>("RotationSpeed", "旋转速度", 0.5, 0.0, 1.0, 0.01, () -> mode.is(Mode.Omnirotational));

    public AutoSprint() {
        super("AutoSprint", "自动疾跑", Category.Movement);
    }

    @Override
    public String getSuffix() {
        return mode.get().displayName;
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (mode.is(Mode.Omnirotational) && MovementUtil.isMoving()) {
            float targetYaw = getMovementDirectionYaw(mc.player.getYaw(), mc.player.input.movementForward, mc.player.input.movementSideways);
            Managers.ROTATION.setRotations(new Rotation(targetYaw, mc.player.getPitch()), rotationSpeed.get(), MovementFix.OFF, RotationManager.Priority.Lowest);
        }

        if (!MovementUtil.isMoving()) {
            return;
        }

        if (shouldPreventSprint()) {
            if (mc.player.isSprinting()) {
                mc.player.setSprinting(false);
            }
            return;
        }

        if (!shouldSprint()) {
            return;
        }

        if (!mc.player.isSprinting()) {
            mc.player.setSprinting(true);
        }
    }

    @EventHandler
    public void onJumpRotation(JumpRotationEvent event) {
        if (nullCheck()) return;
        if (!mode.is(Mode.Omnidirectional)) return;
        if (!MovementUtil.isMoving()) return;

        float targetYaw = getMovementDirectionYaw(event.getYaw(), mc.player.input.movementForward, mc.player.input.movementSideways);
        event.setYaw(targetYaw);
    }

    private boolean shouldSprint() {
        if (!ignore.isEnabled("Blindness")) {
            StatusEffectInstance effect = mc.player.getStatusEffect(StatusEffects.BLINDNESS);
            if (effect != null && effect.getDuration() > 0) {
                return false;
            }
        }

        if (!ignore.isEnabled("Hunger")) {
            if (mc.player.getHungerManager().getFoodLevel() <= 6) {
                return false;
            }
        }

        if (!ignore.isEnabled("Collision")) {
            if (mc.player.horizontalCollision) {
                return false;
            }
        }

        return mode.is(Mode.Omnidirectional) || mode.is(Mode.Omnirotational) || mc.player.input.movementForward > 0;
    }

    private boolean shouldPreventSprint() {
        if (!mode.is(Mode.Legit)) return false;

        if (stopOn.isEnabled("UsingItem") && isSlowDueToUsingItem()) {
            return true;
        }

        boolean check = mc.player.isOnGround() ? stopOn.isEnabled("Ground") : stopOn.isEnabled("Air");
        if (!check) return false;

        if (mode.is(Mode.Omnidirectional)) return false;

        Rotation currentRotation = Managers.ROTATION.getRotation();
        float deltaYawRad = (mc.player.getYaw() - currentRotation.yaw) * MathHelper.RADIANS_PER_DEGREE;

        float forward = mc.player.input.movementForward;
        float sideways = mc.player.input.movementSideways;

        boolean hasForwardMovement = forward * MathHelper.cos(deltaYawRad) + sideways * MathHelper.sin(deltaYawRad) > 1.0E-5F;
        return !hasForwardMovement;
    }

    private boolean isSlowDueToUsingItem() {
        if (!mc.player.isUsingItem()) return false;
        if (mc.player.getActiveItem() == null || mc.player.getActiveItem().isEmpty()) return false;
        return !(mc.player.getActiveItem().getItem() instanceof BlockItem);
    }

    private float getMovementDirectionYaw(float baseYaw, float forward, float strafe) {
        if (forward == 0.0F && strafe == 0.0F) return baseYaw;
        double dir = MovementUtil.getDirection(baseYaw, forward, strafe);
        return MathHelper.wrapDegrees((float) Math.toDegrees(dir));
    }
}
