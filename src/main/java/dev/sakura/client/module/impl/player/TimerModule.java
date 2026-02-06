package dev.sakura.client.module.impl.player;

import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.events.client.TimerEvent;
import dev.sakura.client.events.key.KeyEvent;
import dev.sakura.client.events.type.KeyAction;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.player.MoveUtil;
import dev.sakura.client.values.impl.BoolValue;
import meteordevelopment.orbit.EventHandler;
import org.lwjgl.glfw.GLFW;

public class TimerModule extends Module {
    public TimerModule() {
        super("Timer", "变速", Category.Player);
    }

    public final BoolValue moveCharge = new BoolValue("Move Charge", "移动充能", true);
    public final BoolValue pulse = new BoolValue("Pulse", "脉冲模式", true);

    private static final double CHARGE_SPEED = 0.9; //AntiSB
    private static final double CHARGE_TIME = 3.4;
    private static final double SPEED = 1.3;
    private static final double PULSE_DURATION = 1.1;
    private static final double NORMAL_DURATION = 0.58;

    private boolean active;
    private double progress;
    private long lastUpdateNs;

    public float getTimerSpeed() {
        if (isEnabled()) {
            if (active && progress > 0) {
                if (pulse.get()) {
                    int tick = mc.player.age % 4;
                    if (tick == 0) {
                        return 0.8f; // 回血 tick
                    }
                }
                return (float) SPEED;
            }
            if (moveCharge.get() && progress < 1.0) {
                return (float) CHARGE_SPEED;
            }
        }
        return 1.0f;
    }

    @Override
    protected void onEnable() {
        active = false;
        progress = 0.0;
        lastUpdateNs = System.nanoTime();
    }

    @Override
    protected void onDisable() {
        active = false;
        progress = 0.0;
    }

    @EventHandler
    public void onTick(TickEvent.Post e) {
        if (nullCheck()) return;

        long now = System.nanoTime();
        double dt = (now - lastUpdateNs) / 1_000_000_000.0;
        lastUpdateNs = now;

        if (active) {
            double duration = pulse.get() ? PULSE_DURATION : NORMAL_DURATION;
            double d = Math.max(0.1, duration);
            progress -= dt / d;
            if (progress <= 0.0) {
                progress = 0.0;
                active = false;
            }
        } else {
            if (moveCharge.get() || !MoveUtil.isMoving()) {
                double d = Math.max(0.1, CHARGE_TIME);
                progress += dt / d;
                if (progress > 1.0) {
                    progress = 1.0;
                }
            }
        }
    }

    public boolean isActive() {
        return active;
    }

    public double getProgress() {
        return progress;
    }

    @EventHandler
    public void onKey(KeyEvent event) {
        if (event.getAction() != KeyAction.Press) return;

        // 硬编码按键 X
        if (event.getKey() == GLFW.GLFW_KEY_X) {
            if (active) {
                active = false;
            } else {
                if (progress > 0) {
                    active = true;
                }
            }
        }
    }

    @EventHandler
    public void onTimerEvent(TimerEvent event) {
        if (nullCheck()) return;

        if (active && progress > 0) {
            event.set(getTimerSpeed());
        }
    }
}
