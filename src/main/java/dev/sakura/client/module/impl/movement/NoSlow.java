package dev.sakura.client.module.impl.movement;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.player.SlowdownEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class NoSlow extends Module {
    public NoSlow() {
        super("NoSlow", "无减速", Category.Movement);
    }

    public enum Mode {
        Cancel(""),
        Jump("Jump"),
        Grim50("Grim 1/2"),
        Grim33("Grim 1/3");

        private final String displayName;

        Mode(String displayName) {
            this.displayName = displayName;
        }
    }

    public final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Grim50);
    public final BoolValue food = new BoolValue("Food", "食物", true);
    public final BoolValue bow = new BoolValue("Bow", "弓", true);
    public final BoolValue crossbow = new BoolValue("Crossbow", "弩", true);

    private int onGroundTick = 0;

    @Override
    public String getSuffix() {
        return mode.get().displayName;
    }

    @Override
    public void onEnable() {
        onGroundTick = 0;
    }

    @Override
    public void onDisable() {
        onGroundTick = 0;
    }

    @EventHandler
    public void onSlowdown(SlowdownEvent event) {
        if (nullCheck()) return;
        if (checkFood() && mc.player.getItemUseTimeLeft() > 30) return;

        if (!food.get() && checkFood()) return;
        if (!bow.get() && checkItem(Items.BOW)) return;
        if (!crossbow.get() && checkItem(Items.CROSSBOW)) return;

        switch (mode.get()) {
            case Cancel -> cancel(event);
            case Jump -> jump(event);
            case Grim50 -> grim50(event);
            case Grim33 -> grim33(event);
        }
    }

    private void cancel(SlowdownEvent event) {
        event.setSlowdown(false);
    }

    private void jump(SlowdownEvent event) {
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

    private void grim33(SlowdownEvent event) {
        if (mc.player.getItemUseTimeLeft() % 3 == 0 && (!checkFood() || mc.player.getItemUseTimeLeft() <= 30)) {
            event.setSlowdown(false);
            if (!mc.player.isSprinting()) mc.player.setSprinting(true);
        }
    }

    private boolean checkItem(Item item) {
        ItemStack mainHandItem = mc.player.getMainHandStack();
        ItemStack offhandItem = mc.player.getOffHandStack();
        return mainHandItem.isOf(item) || offhandItem.isOf(item);
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
}
