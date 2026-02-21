package dev.sakura.client.module.impl.movement;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.impl.player.MotionEvent;
import dev.sakura.client.event.impl.player.SlowdownEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.player.MoveUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Hand;

public class NoSlow extends Module {
    public NoSlow() {
        super("NoSlow", "无减速", Category.Movement);
    }

    public enum Mode {
        Cancel(""),
        Jump("Jump"),
        Grim50("Grim 1/2"),
        Grim33("Grim 1/3"),
        GrimSword("GrimSword");

        private final String displayName;

        Mode(String displayName) {
            this.displayName = displayName;
        }
    }

    public final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Grim50);
    public final BoolValue food = new BoolValue("Food", "食物", true);
    public final BoolValue bow = new BoolValue("Bow", "弓", true);
    public final BoolValue crossbow = new BoolValue("Crossbow", "弩", true);
    public final BoolValue sword = new BoolValue("Sword", "剑", true);
    public final BoolValue shield = new BoolValue("Shield", "盾牌", true);

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
        if (!sword.get() && checkSword()) return;
        if (!shield.get() && checkItem(Items.SHIELD)) return;

        switch (mode.get()) {
            case Cancel -> cancel(event);
            case Jump -> jump(event);
            case Grim50 -> grim50(event);
            case Grim33 -> grim33(event);
            case GrimSword -> grimSword(event);
        }
    }

    @EventHandler
    public void onMotion(MotionEvent event) {
        if (nullCheck()) return;

        if (mode.is(Mode.GrimSword) && checkSword()) {
            if (event.getType() == EventType.PRE) {
                if (mc.player.isUsingItem()) {
                    Hand hand = mc.player.getActiveHand();
                    if (hand == Hand.MAIN_HAND) {
                        // Send offhand interact packet
                        // so that grim focuses on offhand noslow checks that don't exist.
                        mc.getNetworkHandler().sendPacket(new PlayerInteractItemC2SPacket(Hand.OFF_HAND, 0, mc.player.getYaw(), mc.player.getPitch()));
                    } else {
                        // Switch slots (based on 1.8 grim switch noslow)
                        int slot = mc.player.getInventory().getSelectedSlot();
                        mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(slot % 8 + 1));
                        mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(slot % 7 + 2));
                        mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(slot));
                    }
                }
            }
        }
    }

    private void cancel(SlowdownEvent event) {
        event.setSlowdown(false);
    }

    private void jump(SlowdownEvent event) {
        if (onGroundTick == 1 && mc.player.getItemUseTimeLeft() <= 30) {
            event.setSlowdown(false);
        }
    }

    private void grim50(SlowdownEvent event) {
        if (mc.player.getItemUseTimeLeft() % 2 == 0 && mc.player.getItemUseTimeLeft() <= 30) {
            event.setSlowdown(false);
        }
    }

    private void grim33(SlowdownEvent event) {
        if (mc.player.getItemUseTimeLeft() % 3 == 0 && (!checkFood() || mc.player.getItemUseTimeLeft() <= 30)) {
            event.setSlowdown(false);
        }
    }

    private void grimSword(SlowdownEvent event) {
        if (checkSword()) {
            event.setSlowdown(false);
        }
    }

    private boolean checkItem(Item item) {
        ItemStack mainHandItem = mc.player.getMainHandStack();
        ItemStack offhandItem = mc.player.getOffHandStack();
        return mainHandItem.isOf(item) || offhandItem.isOf(item);
    }

    private boolean checkSword() {
        ItemStack mainHandItem = mc.player.getMainHandStack();
        ItemStack offhandItem = mc.player.getOffHandStack();
        return mainHandItem.isIn(ItemTags.SWORDS) || offhandItem.isIn(ItemTags.SWORDS);
    }

    private boolean checkFood() {
        ItemStack mainHandItem = mc.player.getMainHandStack();
        ItemStack offhandItem = mc.player.getOffHandStack();
        return mainHandItem.isOf(Items.GOLDEN_APPLE) || offhandItem.isOf(Items.GOLDEN_APPLE) || mainHandItem.isOf(Items.ENCHANTED_GOLDEN_APPLE) || offhandItem.isOf(Items.ENCHANTED_GOLDEN_APPLE) || mainHandItem.isOf(Items.POTION) || offhandItem.isOf(Items.POTION);
    }
}
