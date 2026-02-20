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

public class Flight extends Module {

    public enum Mode {
        Vanilla,
        AirWalk
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Vanilla);
    private final NumberValue<Double> speed = new NumberValue<>("Speed", "速度", 1.0, 0.1, 10.0, 0.1, () -> mode.is(Mode.Vanilla));
    private final BoolValue onlyParallel = new BoolValue("Parallel", "平行", false);

    public Flight() {
        super("Flight", "飞行", Category.Movement);
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
            case AirWalk:
                handleAirWalk();
                break;
        }
    }

    @EventHandler
    public void onMoveInput(MoveInputEvent event) {
        if (nullCheck()) return;

        if (mode.is(Mode.Vanilla)) {
            event.setSneak(false);
        }
    }

    private void handleVanilla() {
        double moveSpeed = speed.get();
        double motionY = 0;

        if (!onlyParallel.get()) {
            if (mc.options.jumpKey.isPressed()) {
                motionY = moveSpeed;
            } else if (mc.options.sneakKey.isPressed()) {
                motionY = -moveSpeed;
            }
        }

        if (MoveUtil.isMoving()) {
            MoveUtil.strafe(moveSpeed);
        } else {
            MoveUtil.setMotionX(0);
            MoveUtil.setMotionZ(0);
        }
        MoveUtil.setMotionY(motionY);
    }

    private void handleAirWalk() {
        double motionY = 0;

        if (!onlyParallel.get()) {
            if (mc.options.jumpKey.isPressed()) {
                motionY = 0.42;
            } else if (mc.options.sneakKey.isPressed()) {
                motionY = -0.42;
            }
        }

        MoveUtil.setMotionY(motionY);
        mc.player.setOnGround(true);
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        double maxSpeed = MoveUtil.getBaseSpeed(false, 0.221);
        double targetSpeed = Math.min(MoveUtil.getSpeed(), maxSpeed);

        if (MoveUtil.isMoving()) {
            MoveUtil.strafe(targetSpeed);
        } else {
            MoveUtil.setMotionX(0);
            MoveUtil.setMotionZ(0);
        }

        MoveUtil.setMotionY(0);
    }
}
