package com.zeta.client.module.impl.combat;

import com.zeta.client.events.client.TickEvent;
import com.zeta.client.gui.clickgui.ClickGuiScreen;
import com.zeta.client.module.Category;
import com.zeta.client.module.Module;
import com.zeta.client.utils.combat.CombatUtil;
import com.zeta.client.utils.player.InvUtil;
import com.zeta.client.utils.time.TimerUtil;
import com.zeta.client.values.impl.BoolValue;
import com.zeta.client.values.impl.EnumValue;
import com.zeta.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

public class AutoPot extends Module {
    public static AutoPot INSTANCE;

    private final EnumValue<Page> page = new EnumValue<>("Page", "页面", Page.General);

    private final NumberValue<Integer> delay = new NumberValue<>("Delay", "延迟", 100, 0, 200, 1,
            () -> page.is(Page.General));
    private final BoolValue usingPause = new BoolValue("UsingPause", "使用暂停", true,
            () -> page.is(Page.General));
    private final BoolValue inventory = new BoolValue("InventorySwap", "背包切换", true,
            () -> page.is(Page.General));
    private final BoolValue onlyGround = new BoolValue("OnlyGround", "仅地面", true,
            () -> page.is(Page.General));
    private final BoolValue rangeCheck = new BoolValue("RangeCheck", "范围检查", false,
            () -> page.is(Page.General));
    private final NumberValue<Double> range = new NumberValue<>("Range", "范围", 15.0, 0.0, 50.0, 0.5,
            () -> page.is(Page.General) && rangeCheck.get());
    private final NumberValue<Integer> pitch = new NumberValue<>("Pitch", "俯仰角", 88, 75, 90, 1,
            () -> page.is(Page.General));
    private final BoolValue snapBack = new BoolValue("SnapBack", "回转", true,
            () -> page.is(Page.General));

    private final BoolValue speed = new BoolValue("Speed", "速度", true, () -> page.is(Page.Effects));
    private final BoolValue resistance = new BoolValue("Turtlemaster", "神龟药水", true, () -> page.is(Page.Effects));
    private final BoolValue slowFalling = new BoolValue("SlowFalling", "缓降", true, () -> page.is(Page.Effects));
    private final BoolValue strength = new BoolValue("Strength", "力量", true, () -> page.is(Page.Effects));
    private final BoolValue fireResistance = new BoolValue("FireResistance", "抗火", false, () -> page.is(Page.Effects));
    private final BoolValue regeneration = new BoolValue("Regeneration", "再生", false, () -> page.is(Page.Effects));

    private final TimerUtil delayTimer = new TimerUtil();

    private boolean throwing = false;
    private float lastYaw, lastPitch;

    public AutoPot() {
        super("AutoPot", "自动药水", Category.Combat);
        INSTANCE = this;
    }

    public enum Page {
        General,
        Effects
    }

    @Override
    protected void onEnable() {
        throwing = false;
        delayTimer.reset();
        if (mc.player != null) {
            lastYaw = mc.player.getYaw();
            lastPitch = mc.player.getPitch();
        }
    }

    @Override
    protected void onDisable() {
        throwing = false;
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        setSuffix(page.get().name());

        if (rangeCheck.get()) {
            PlayerEntity enemy = CombatUtil.getClosestEnemy(range.get());
            if (enemy == null) return;
        }

        if (onlyGround.get()) {
            if (!mc.player.isOnGround()) return;
            if (mc.world.isAir(BlockPos.ofFloored(mc.player.getPos().add(0, -1, 0)))) return;
        }

        if (resistance.get() && needsEffect(StatusEffects.RESISTANCE, 2)) {
            tryThrowPotion(StatusEffects.RESISTANCE.value());
            return;
        }

        if (speed.get() && needsEffect(StatusEffects.SPEED, 0)) {
            tryThrowPotion(StatusEffects.SPEED.value());
            return;
        }

        if (slowFalling.get() && needsEffect(StatusEffects.SLOW_FALLING, 0)) {
            tryThrowPotion(StatusEffects.SLOW_FALLING.value());
            return;
        }

        if (strength.get() && needsEffect(StatusEffects.STRENGTH, 0)) {
            tryThrowPotion(StatusEffects.STRENGTH.value());
            return;
        }

        if (fireResistance.get() && needsEffect(StatusEffects.FIRE_RESISTANCE, 0)) {
            tryThrowPotion(StatusEffects.FIRE_RESISTANCE.value());
            return;
        }

        if (regeneration.get() && needsEffect(StatusEffects.REGENERATION, 0)) {
            tryThrowPotion(StatusEffects.REGENERATION.value());
        }
    }

    private boolean needsEffect(RegistryEntry<StatusEffect> effect, int minAmplifier) {
        if (!mc.player.hasStatusEffect(effect)) {
            return true;
        }
        if (minAmplifier > 0) {
            StatusEffectInstance instance = mc.player.getStatusEffect(effect);
            return instance != null && instance.getAmplifier() < minAmplifier;
        }
        return false;
    }

    private void tryThrowPotion(StatusEffect targetEffect) {
        throwing = checkThrow(targetEffect);
        if (throwing && delayTimer.delay(delay.get().floatValue())) {
            throwPotion(targetEffect);
        }
    }

    public void throwPotion(StatusEffect targetEffect) {
        if (mc.player == null || mc.interactionManager == null) return;

        lastYaw = mc.player.getYaw();
        lastPitch = mc.player.getPitch();

        int newSlot;

        if (inventory.get() && (newSlot = findPotionInventorySlot(targetEffect)) != -1) {
            sendLookPacket(lastYaw, pitch.get().floatValue());
            InvUtil.invSwap(newSlot);
            useItem();
            InvUtil.invSwapBack();
            mc.player.getInventory().updateItems();
            if (snapBack.get()) {
                sendLookPacket(lastYaw, lastPitch);
            }
            delayTimer.reset();
        } else if ((newSlot = findPotion(targetEffect)) != -1) {
            sendLookPacket(lastYaw, pitch.get().floatValue());
            InvUtil.swap(newSlot, false);
            useItem();
            InvUtil.swapBack();
            if (snapBack.get()) {
                sendLookPacket(lastYaw, lastPitch);
            }
            delayTimer.reset();
        }
    }

    private void sendLookPacket(float yaw, float pitch) {
        if (mc.getNetworkHandler() == null || mc.player == null) return;
        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(
                yaw, pitch, mc.player.isOnGround(), mc.player.horizontalCollision
        ));
    }

    private void useItem() {
        if (mc.interactionManager == null || mc.player == null) return;
        mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
    }

    public boolean checkThrow(StatusEffect targetEffect) {
        if (isDisabled()) return false;

        if (mc.currentScreen != null
                && !(mc.currentScreen instanceof ChatScreen)
                && !(mc.currentScreen instanceof InventoryScreen)
                && !(mc.currentScreen instanceof ClickGuiScreen)
                && !(mc.currentScreen instanceof GameMenuScreen)) {
            return false;
        }

        if (usingPause.get() && mc.player.isUsingItem()) {
            return false;
        }

        boolean hasInHotbar = findPotion(targetEffect) != -1;
        boolean hasInInventory = inventory.get() && findPotionInventorySlot(targetEffect) != -1;

        return hasInHotbar || hasInInventory;
    }

    private int findPotion(StatusEffect effect) {
        return InvUtil.findInHotbar(stack -> isPotion(stack, effect)).slot();
    }

    private int findPotionInventorySlot(StatusEffect effect) {
        return InvUtil.find(stack -> isPotion(stack, effect)).slot();
    }

    private boolean isPotion(ItemStack stack, StatusEffect effect) {
        if (!stack.isOf(Items.SPLASH_POTION)) return false;
        var contents = stack.get(DataComponentTypes.POTION_CONTENTS);
        if (contents == null) return false;
        for (StatusEffectInstance instance : contents.getEffects()) {
            if (instance.getEffectType().value() == effect) return true;
        }
        return false;
    }

    public boolean isThrowing() {
        return throwing;
    }
}