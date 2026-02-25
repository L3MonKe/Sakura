package dev.mzc.client.module.impl.movement;

import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.utils.player.MovementUtil;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;

public class Fly extends Module {
    public enum Mode {
        Creative("创造"), Normal("普通");

        private final String cnName;

        Mode(String cnName) {
            this.cnName = cnName;
        }
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Creative);
    private final NumberValue<Double> hSpeed = new NumberValue<>("Speed", "水平速度", 1.0, 0.1, 10.0, 0.1);
    private final NumberValue<Double> vSpeed = new NumberValue<>("VSpeed", "垂直速度", 1.0, 0.1, 10.0, 0.1);

    public Fly() {
        super("Fly", "飞行", Category.Movement);
        this.setType(ModuleType.Hack);
    }

    @Override
    public void onDisable() {
        if (mc.player != null) {
            if (!mc.player.isCreative()) {
                mc.player.getAbilities().allowFlying = false;
                mc.player.getAbilities().flying = false;
            }
            mc.player.getAbilities().setFlySpeed(0.05f);
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;
        setSuffix(mode.get().name());

        if (mode.is(Mode.Creative)) {
            mc.player.getAbilities().allowFlying = true;
            mc.player.getAbilities().setFlySpeed(hSpeed.get().floatValue() / 10.0f);
            
            if (mc.player.getAbilities().flying) {
                 if (mc.options.jumpKey.isPressed()) {
                    mc.player.setVelocity(mc.player.getVelocity().x, vSpeed.get(), mc.player.getVelocity().z);
                } else if (mc.options.sneakKey.isPressed()) {
                    mc.player.setVelocity(mc.player.getVelocity().x, -vSpeed.get(), mc.player.getVelocity().z);
                }
            }
        } else if (mode.is(Mode.Normal)) {
            mc.player.getAbilities().allowFlying = false;
            mc.player.getAbilities().flying = false;
            
            double x = 0, y = 0, z = 0;
            
            if (MovementUtil.isMoving()) {
                double[] dir = MovementUtil.directionSpeedKey(hSpeed.get());
                x = dir[0];
                z = dir[1];
            }
            
            if (mc.options.jumpKey.isPressed()) {
                y = vSpeed.get();
            } else if (mc.options.sneakKey.isPressed()) {
                y = -vSpeed.get();
            }
            
            mc.player.setVelocity(x, y, z);
        }
    }
}
