package dev.mzc.client.module.impl.movement;

import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.events.client.GameJoinEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class AutoWTap extends Module {

    private final NumberValue<Double> range = new NumberValue<>("Range", "距离(格)", 2.8, 0.0, 6.0, 0.1);
    private final NumberValue<Integer> tapTicks = new NumberValue<>("TapTicks", "按键时长(tick)", 3, 1, 10, 1);
    private final NumberValue<Integer> cooldownTicks = new NumberValue<>("Cooldown", "冷却(tick)", 10, 0, 60, 1);
    private final BoolValue onlyForward = new BoolValue("OnlyForward", "仅前进时触发", true);
    private final BoolValue onlyGround = new BoolValue("OnlyGround", "空中不触发", true);

    private boolean tapping;
    private int tapTimer;
    private int lastActionAge;
    private boolean prevForwardPressed;

    public AutoWTap() {
        super("AutoWTap", "自动WTap", Category.Movement);
        this.setType(ModuleType.Safe);
    }

    @Override
    protected void onEnable() {
        tapping = false;
        tapTimer = 0;
        lastActionAge = 0;
        prevForwardPressed = false;
    }

    @Override
    protected void onDisable() {
        resetKeys();
        tapping = false;
        tapTimer = 0;
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (mc.player.isDead()) {
            resetKeys();
            tapping = false;
            tapTimer = 0;
            lastActionAge = mc.player.age;
            prevForwardPressed = false;
            return;
        }

        if (mc.player.age < lastActionAge) {
            lastActionAge = 0;
        }

        if (tapping) {
            tapTimer--;
            if (tapTimer <= 0) {
                finishTap();
            }
            return;
        }

        HitResult hr = mc.crosshairTarget;
        if (!(hr instanceof EntityHitResult ehr)) return;
        if (!(ehr.getEntity() instanceof PlayerEntity target)) return;

        if (onlyGround.get()) {
            boolean onSolidGround = mc.player.isOnGround() && !mc.world.isAir(BlockPos.ofFloored(mc.player.getPos().add(0, -1, 0)));
            if (!onSolidGround) return;
        }

        double dist = mc.player.distanceTo(target);

        boolean requireForward = !onlyForward.get() || mc.options.forwardKey.isPressed();
        if (!requireForward) return;

        if (dist <= range.get()) {
            startWTap();
        }
    }

    private void startWTap() {
        if (!canAct()) return;
        KeyBinding forward = mc.options.forwardKey;
        prevForwardPressed = forward.isPressed();
        forward.setPressed(false);
        tapping = true;
        tapTimer = tapTicks.get();
        lastActionAge = mc.player.age;
    }

    private void finishTap() {
        KeyBinding forward = mc.options.forwardKey;
        if (forward != null && prevForwardPressed) forward.setPressed(true);
        tapping = false;
        tapTimer = 0;
    }

    private boolean canAct() {
        int age = mc.player.age;
        return age - lastActionAge >= cooldownTicks.get();
    }

    @EventHandler
    private void onGameJoin(GameJoinEvent event) {
        resetKeys();
        tapping = false;
        tapTimer = 0;
        lastActionAge = 0;
        prevForwardPressed = false;
    }

    private void resetKeys() {
        if (mc == null || mc.options == null) return;
        KeyBinding forward = mc.options.forwardKey;
        if (forward != null) forward.setPressed(false);
    }
}
