package dev.mzc.client.module.impl.movement;

import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.utils.player.MovementUtil;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;

public class Speed extends Module {
    public enum Mode {
        Strafe("Strafe"),
        Vanilla("原生"),
        OnGround("地面");

        private final String cnName;

        Mode(String cnName) {
            this.cnName = cnName;
        }


    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Strafe);
    private final NumberValue<Double> speed = new NumberValue<>("Speed", "速度", 0.5, 0.1, 10.0, 0.1);
    private final BoolValue autoJump = new BoolValue("AutoJump", "自动跳跃", true);
    private final BoolValue inAir = new BoolValue("InAir", "空中加速", true);

    public Speed() {
        super("Speed", "速度", Category.Movement);
        this.setType(ModuleType.Hack);
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (mc.player == null || mc.world == null) return;
        
        if (!MovementUtil.isMoving()) {
            mc.player.setVelocity(0, mc.player.getVelocity().y, 0);
            return;
        }

        switch (mode.get()) {
            case Strafe -> {
                if (autoJump.get() && mc.player.isOnGround()) {
                    mc.player.jump();
                }
                if (inAir.get() || mc.player.isOnGround()) {
                    MovementUtil.strafe(speed.get());
                }
            }
            case Vanilla -> {
                 if (autoJump.get() && mc.player.isOnGround()) {
                    mc.player.jump();
                }
                MovementUtil.strafe(speed.get());
            }
            case OnGround -> {
                 if (mc.player.isOnGround()) {
                    if (autoJump.get()) {
                         mc.player.jump();
                    }
                    MovementUtil.strafe(speed.get());
                }
            }
        }
    }
}
