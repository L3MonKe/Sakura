package dev.sakura.client.module.impl.movement;

import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.events.input.MoveInputEvent;
import dev.sakura.client.events.player.SlowdownEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class NoSlowBJD extends Module {
    public enum Mode {
        None("None"),
        Heypixel2_3("Heypixel 2/3"),
        Grim50("Grim50%"),
        Grim1_3("Grim 1/3"),
        Jump("Jump");

        private final String displayName;

        Mode(String displayName) {
            this.displayName = displayName;
        }
    }

    public final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.None);
    public final BoolValue food = new BoolValue("Food", "食物", true);
    public final BoolValue bow = new BoolValue("Bow", "弓", true);
    public final BoolValue crossbow = new BoolValue("Crossbow", "弩", true);
    private int onGroundTick;

    public NoSlowBJD() {
        super("NoSlowBJD", "无减速BJD", Category.Movement);
    }

    @Override
    protected void onEnable() {
        onGroundTick = 0;
    }

    @Override
    protected void onDisable() {
        onGroundTick = 0;
    }

    @Override
    public String getSuffix() {
        return mode.get().displayName;
    }

    @EventHandler
    public void onSlowdown(SlowdownEvent event) {
        if (nullCheck()) return;
        if (checkFood() && mc.player.getItemUseTimeLeft() > 30) return;

        if (!food.get() && checkFood()) return;
        if (!bow.get() && checkItem(Items.BOW)) return;
        if (!crossbow.get() && checkItem(Items.CROSSBOW)) return;

        switch (mode.get()) {
            case Jump -> grimJump(event);
            case Grim50 -> grim50(event);
            case None -> none(event);
            case Grim1_3 -> grim1_3(event);
            case Heypixel2_3 -> heypixel2_3(event);
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;
        if (mc.player.isOnGround()) {
            onGroundTick++;
        } else {
            onGroundTick = 0;
        }
    }

    @EventHandler
    public void onMoveInput(MoveInputEvent event) {
        if (nullCheck()) return;
        if (mode.get() != Mode.Jump) return;
        if (!mc.player.isOnGround()) return;
        if (!mc.player.isUsingItem()) return;
        if (event.getForward() == 0 && event.getStrafe() == 0) return;
        event.setJump(true);
    }

    private void grimJump(SlowdownEvent event) {
        if (onGroundTick == 1 && mc.player.getItemUseTimeLeft() <= 30) {
            event.setSlowdown(false);
            if (!mc.player.isSprinting()) mc.player.setSprinting(true);
        }
    }

    private void grim50(SlowdownEvent event) {
        if (mc.player.getItemUseTimeLeft() % 2 == 0 && mc.player.getItemUseTimeLeft() <= 30) {
            event.setSlowdown(false);
            if (!mc.player.isSprinting()) mc.player.setSprinting(true);
        }
    }

    private void none(SlowdownEvent event) {
        event.setSlowdown(false);
        if (!mc.player.isSprinting()) mc.player.setSprinting(true);
    }

    private void grim1_3(SlowdownEvent event) {
        if (mc.player.getItemUseTimeLeft() % 3 == 0 && (!checkFood() || mc.player.getItemUseTimeLeft() <= 30)) {
            event.setSlowdown(false);
            if (!mc.player.isSprinting()) mc.player.setSprinting(true);
        }
    }

    private void heypixel2_3(SlowdownEvent event) {
        if (mc.player.getItemUseTimeLeft() % 3 != 0 && (!checkFood() || mc.player.getItemUseTimeLeft() <= 30)) {
            event.setSlowdown(false);
            if (!mc.player.isSprinting()) mc.player.setSprinting(true);
        }
    }

    private boolean checkFood() {
        ItemStack mainHandItem = mc.player.getMainHandStack();
        ItemStack offhandItem = mc.player.getOffHandStack();
        return mainHandItem.isOf(Items.GOLDEN_APPLE)
                || offhandItem.isOf(Items.GOLDEN_APPLE)
                || mainHandItem.isOf(Items.ENCHANTED_GOLDEN_APPLE)
                || offhandItem.isOf(Items.ENCHANTED_GOLDEN_APPLE)
                || mainHandItem.isOf(Items.POTION)
                || offhandItem.isOf(Items.POTION);
    }

    private boolean checkItem(Item item) {
        ItemStack mainHandItem = mc.player.getMainHandStack();
        ItemStack offhandItem = mc.player.getOffHandStack();
        return mainHandItem.isOf(item) || offhandItem.isOf(item);
    }
}
