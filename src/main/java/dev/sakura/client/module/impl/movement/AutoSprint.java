package dev.sakura.client.module.impl.movement;

import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.events.player.SprintEvent;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.player.MovementUtil;
import dev.sakura.client.utils.vector.Rotation;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.util.math.MathHelper;

public class AutoSprint extends Module {
    public AutoSprint() {
        super("AutoSprint", "自动疾跑", Category.Movement);
    }

    public enum Mode {
        PressKey,
        Rage,
        Grim,
        Rotation
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Grim);
    private final NumberValue<Integer> rotationSpeed = new NumberValue<>("Rotation Speed", "旋转速度", 10, 0, 10, 1, () -> mode.is(Mode.Rotation));

    @Override
    public String getSuffix() {
        return mode.get().name();
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (mc.world == null || mc.player == null) return;

        if (mode.get() == Mode.PressKey) {
            mc.options.sprintKey.setPressed(!shouldPauseSprinting());
        } else {
            mc.player.setSprinting(shouldSprint());
        }

        if ((mc.player.getHungerManager().getFoodLevel() > 6 || mc.player.isCreative())
                && MovementUtil.isMoving()
                //&& !mc.player.isFallFlying()
                && !mc.player.isSneaking()
                && !mc.player.isRiding()
                && !mc.player.isTouchingWater()
                && !mc.player.isInLava()
                && !mc.player.isHoldingOntoLadder()
                && !mc.player.hasStatusEffect(StatusEffects.BLINDNESS)) {
            if (mode.is(Mode.Rotation)) {
                Managers.ROTATION.setRotations(new Rotation(getSprintYaw(mc.player.getYaw()), Managers.ROTATION.getPitch()), rotationSpeed.get());
            }
        }
    }

    @EventHandler
    public void onSprint(SprintEvent event) {
        event.cancel();
        event.setSprint(shouldSprint());
    }

    private boolean shouldSprint() {
        if (shouldPauseSprinting()) return false;
        if ((mc.player.getHungerManager().getFoodLevel() > 6 || mc.player.isCreative())
                && MovementUtil.isMoving()
                && !mc.player.isSneaking()
                && !mc.player.isRiding()
                && !mc.player.isHoldingOntoLadder()
                && !mc.player.hasStatusEffect(StatusEffects.BLINDNESS)) {
            switch (mode.get()) {
                case Grim -> {
                    if (MoveFix.isActive()) {
                        return mc.player.input.movementForward == 1;
                    } else {
                        return mc.options.forwardKey.isPressed() && MathHelper.angleBetween(mc.player.getYaw(), Managers.ROTATION.getYaw()) < 40;
                    }
                }
                case Rotation -> {
                    if (MoveFix.isActive()) {
                        return mc.player.input.movementForward == 1;
                    } else {
                        return MathHelper.angleBetween(getSprintYaw(mc.player.getYaw()), Managers.ROTATION.getYaw()) < 40;
                    }
                }
                case Rage -> {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean shouldPauseSprinting() {
        if (!isEnabled(Scaffold.class)) {
            return false;
        }
        return mc.player.getMainHandStack().getItem() instanceof BlockItem || mc.player.getOffHandStack().getItem() instanceof BlockItem;
    }

    public float getSprintYaw(float yaw) {
        if (mc.options.forwardKey.isPressed() && !mc.options.backKey.isPressed()) {
            if (mc.options.leftKey.isPressed() && !mc.options.rightKey.isPressed()) {
                yaw -= 45f;
            } else if (mc.options.rightKey.isPressed() && !mc.options.leftKey.isPressed()) {
                yaw += 45f;
            }
        } else if (mc.options.backKey.isPressed() && !mc.options.forwardKey.isPressed()) {
            yaw += 180f;
            if (mc.options.leftKey.isPressed() && !mc.options.rightKey.isPressed()) {
                yaw += 45f;
            } else if (mc.options.rightKey.isPressed() && !mc.options.leftKey.isPressed()) {
                yaw -= 45f;
            }
        } else if (mc.options.leftKey.isPressed() && !mc.options.rightKey.isPressed()) {
            yaw -= 90f;
        } else if (mc.options.rightKey.isPressed() && !mc.options.leftKey.isPressed()) {
            yaw += 90f;
        }
        return MathHelper.wrapDegrees(yaw);
    }
}
