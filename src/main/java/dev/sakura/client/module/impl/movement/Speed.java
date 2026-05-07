package dev.sakura.client.module.impl.movement;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TimerEvent;
import dev.sakura.client.event.impl.input.MoveInputEvent;
import dev.sakura.client.event.impl.player.PlayerTickEvent;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.player.MoveUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;

public class Speed extends Module {
    public enum Mode {
        Vanilla,
        Strafe,
        Grim
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Vanilla);

    // Vanilla Settings
    private final NumberValue<Double> vanillaSpeed = new NumberValue<>("Speed", "速度", 1.0, 0.1, 10.0, 0.1, () -> mode.is(Mode.Vanilla));
    private final BoolValue vanillaAutoJump = new BoolValue("Auto Jump", "自动跳跃", true, () -> mode.is(Mode.Vanilla));

    // Strafe Settings
    private final BoolValue strafeFastStop = new BoolValue("Fast Stop", "快速停止", true, () -> mode.is(Mode.Strafe));

    // Grim variables
    private double fallStartY = 0;
    private int fallDurationTicks = 0;
    private int storedFallTicks = 0;
    private int riseDurationTicks = 0;
    private boolean isFalling = false;
    private boolean isRising = false;
    private boolean firstRise = true;

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
            case Grim:
                handleGrim();
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
            if (Sakura.MODULES.getModule(Scaffold.class).isEnabled()) {
                MoveUtil.strafe(speed, Managers.ROTATION.getYaw());
            } else {
                MoveUtil.strafe(speed);
            }
        } else {
            MoveUtil.setMotionX(0);
            MoveUtil.setMotionZ(0);
        }
    }

    private void handleStrafe() {
        if (MoveUtil.isMoving()) {
            if (Sakura.MODULES.getModule(Scaffold.class).isEnabled()) {
                MoveUtil.strafe(MoveUtil.getSpeed(), Managers.ROTATION.getYaw());
            } else {
                MoveUtil.strafe(MoveUtil.getSpeed());
            }
        } else if (strafeFastStop.get()) {
            MoveUtil.setMotionX(0);
            MoveUtil.setMotionZ(0);
        }
    }

    private void handleGrim() {
        double motionY = MoveUtil.getMotionY();
        double currentY = mc.player.getY();

        if (mc.player.isOnGround()) {
            resetGrim();
            return;
        }

        if (motionY < 0) {
            if (!isFalling) {
                isFalling = true;
                isRising = false;
                fallStartY = currentY;
                fallDurationTicks = 0;
            }

            double fallDistance = fallStartY - currentY;
            if (fallDistance <= 2.0) {
                fallDurationTicks++;
                targetTimer = 1.5f;
            } else {
                targetTimer = 1.0f;
            }
        } else if (motionY > 0) {
            if (isFalling) {
                storedFallTicks = fallDurationTicks;
                isFalling = false;
                isRising = true;
                firstRise = false;
                riseDurationTicks = 0;
            }

            double riseDistance = currentY - fallStartY;
            if (riseDistance <= 2.0) {
                if (!firstRise && riseDurationTicks < storedFallTicks) {
                    targetTimer = 0.8f;
                    riseDurationTicks++;
                } else {
                    targetTimer = 1.0f;
                }
            } else {
                targetTimer = 1.0f;
            }
        } else {
            targetTimer = 1.0f;
        }
    }

    private void resetGrim() {
        isFalling = false;
        isRising = false;
        fallDurationTicks = 0;
        storedFallTicks = 0;
        riseDurationTicks = 0;
        firstRise = true;
        targetTimer = 1.0f;
    }

    private float targetTimer = 1.0f;

    @EventHandler
    public void onTimerEvent(TimerEvent event) {
        if (nullCheck()) return;
        if (!mode.is(Mode.Grim)) return;

        event.set(targetTimer);
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        if (mode.is(Mode.Grim)) {
            resetGrim();
        }

        targetTimer = 1.0f;

        if (mode.is(Mode.Vanilla)) {
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
