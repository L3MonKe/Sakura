package dev.sakura.client.module.impl.movement;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.input.MoveInputEvent;
import dev.sakura.client.event.impl.player.PlayerTickEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.player.MoveUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;

public class Speed extends Module {
    public enum Mode {
        Vanilla,
        Strafe
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Vanilla);

    // Vanilla Settings
    private final NumberValue<Double> vanillaSpeed = new NumberValue<>("Speed", "速度", 1.0, 0.1, 10.0, 0.1, () -> mode.is(Mode.Vanilla));
    private final BoolValue vanillaAutoJump = new BoolValue("Auto Jump", "自动跳跃", true, () -> mode.is(Mode.Vanilla));

    // Strafe Settings
    private final BoolValue strafeFastStop = new BoolValue("Fast Stop", "快速停止", true, () -> mode.is(Mode.Strafe));

    public Speed() {
        super("Speed", "速度", Category.Movement);
    }

    @Override
    public String getSuffix() {
        return mode.get().name();
    }

    @EventHandler
    public void onTick(PlayerTickEvent event) {
        if (nullCheck()) return;

        switch (mode.get()) {
            case Vanilla:
                handleVanilla();
                break;
            case Strafe:
                handleStrafe();
                break;
        }
    }

    @EventHandler
    public void onMoveInput(MoveInputEvent event) {
        if (nullCheck()) return;

        switch (mode.get()) {
            case Vanilla:
                if (vanillaAutoJump.get() && MoveUtil.isMoving()) {
                    event.setJump(true);
                }
                break;
            case Strafe:
                if (MoveUtil.isMoving()) {
                    event.setJump(true);
                }
                break;
        }
    }

    private void handleVanilla() {
        double speed = vanillaSpeed.get();
        if (MoveUtil.isMoving()) {
            MoveUtil.strafe(speed);
        } else {
            MoveUtil.setMotionX(0);
            MoveUtil.setMotionZ(0);
        }
    }

    private void handleStrafe() {
        if (MoveUtil.isMoving()) {
            MoveUtil.strafe(MoveUtil.getSpeed());
        } else if (strafeFastStop.get()) {
            MoveUtil.setMotionX(0);
            MoveUtil.setMotionZ(0);
        }
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;
        if (mode.is(Mode.Vanilla)) {
            // 0.221 is the base speed value used in Opal's VanillaSpeed onDisable
            // MoveUtil.getBaseSpeed(false, 0.221) should replicate getSwiftnessSpeed(0.221D)
            double maxSpeed = MoveUtil.getBaseSpeed(false, 0.221);
            double targetSpeed = Math.min(MoveUtil.getSpeed(), maxSpeed);
            
            if (MoveUtil.isMoving()) {
                MoveUtil.strafe(targetSpeed);
            } else {
                MoveUtil.setMotionX(0);
                MoveUtil.setMotionZ(0);
            }
        }
    }
}
