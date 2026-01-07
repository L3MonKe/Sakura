package dev.sakura.client.module.impl.movement;

import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.player.FindItemResult;
import dev.sakura.client.utils.player.InvUtil;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;

/**
 * @author NiuRen0827
 * Time:21:44
 */
public class ArmorFly extends Module {
    private final BoolValue noGround = new BoolValue("OnlyNoGround", "在地面上不飞", false);
    private final BoolValue motion = new BoolValue("Motion", "移动操控", false);
    private final NumberValue<Integer> delay = new NumberValue<>("Delay", "延迟", 3, 0, 50, 1);
    //  ValueNumber fireworkDelay = new ValueNumber("FireworkDelay", "FireworkDelay", 50, 0, 500);
    public static ArmorFly INSTANCE;

    public ArmorFly() {
        super("ArmorFly", "甲飞", Category.Movement);
        INSTANCE = this;
    }

    private final TimerUtil fireworkTimer = new TimerUtil();
    private boolean finish = false;

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;
        if (noGround.get() && mc.player.isOnGround()) return;

        if (silentSwapEquipElytra()) {
            if (!motion.get() || mc.options.forwardKey.isPressed()) {
                FindItemResult firework = InvUtil.find(Items.FIREWORK_ROCKET);
                if (firework.found() && fireworkTimer.delay(delay.get().floatValue())) {
                    mc.player.jump();
                    mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_FALL_FLYING));
                    useFirework(firework);
                    fireworkTimer.reset();
                }
            }
        }
        if (silentSwapEquipChestplate()) {
            finish = true;
        }
        if (!mc.options.sneakKey.isPressed() && !(mc.options.forwardKey.isPressed() && motion.get()
                && !mc.player.isOnGround())) {
            mc.player.setVelocity(mc.player.getVelocity().multiply(0, 0, 0));
        }
    }

    private boolean silentSwapEquipChestplate() {
        if (mc.player.getEquippedStack(EquipmentSlot.CHEST).getItem()
                .equals(Items.DIAMOND_CHESTPLATE)
                || mc.player.getEquippedStack(EquipmentSlot.CHEST).getItem()
                .equals(Items.NETHERITE_CHESTPLATE)) {
            return false;
        }

        FindItemResult hotbarChestplateSlot = InvUtil.findInHotbar(Items.NETHERITE_CHESTPLATE);
        if (!hotbarChestplateSlot.found()) {
            hotbarChestplateSlot = InvUtil.findInHotbar(Items.DIAMOND_CHESTPLATE);
        }

        // If we have a chestplate in our hotbar, we can immediately swap
        if (hotbarChestplateSlot.found()) {
            mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, 6,
                    hotbarChestplateSlot.slot(), SlotActionType.SWAP, mc.player);
            return true;
        }

        // Search for the chestplate in the inventory
        FindItemResult inventorySlot = InvUtil.find(Items.NETHERITE_CHESTPLATE);
        if (!inventorySlot.found()) {
            inventorySlot = InvUtil.find(Items.DIAMOND_CHESTPLATE);
        }

        if (!inventorySlot.found()) {
            return false;
        }

        // Pick a good slot in the hotbar that isn't a totem (try to prevent tfails while dhanding?)
        FindItemResult hotbarSlot = InvUtil.findInHotbar(x -> {
            return x.getItem() != Items.TOTEM_OF_UNDYING;
        });

        // Move chestplate to hotbarSlot
        mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, inventorySlot.slot(),
                hotbarSlot.found() ? hotbarSlot.slot() : 0, SlotActionType.SWAP, mc.player);

        // Equip chestplate
        mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, 6,
                hotbarSlot.found() ? hotbarSlot.slot() : 0, SlotActionType.SWAP, mc.player);

        // Move old item back to hotbar slot
        mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, inventorySlot.slot(),
                hotbarSlot.found() ? hotbarSlot.slot() : 0, SlotActionType.SWAP, mc.player);

        return true;
    }

    private boolean silentSwapEquipElytra() {
        if (mc.player.getEquippedStack(EquipmentSlot.CHEST).getItem().equals(Items.ELYTRA)) {
            return false;
        }

        FindItemResult inventorySlot = InvUtil.findInHotbar(Items.ELYTRA);

        if (inventorySlot.found()) {
            mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, 6,
                    inventorySlot.slot(), SlotActionType.SWAP, mc.player);
            return true;
        }

        inventorySlot = InvUtil.find(Items.ELYTRA);

        if (!inventorySlot.found()) {
            return false;
        }

        FindItemResult hotbarSlot = InvUtil.findInHotbar(x -> {
            return x.getItem() != Items.TOTEM_OF_UNDYING;
        });

        // Move elytra to hotbarSlot
        mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, inventorySlot.slot(),
                hotbarSlot.found() ? hotbarSlot.slot() : 0, SlotActionType.SWAP, mc.player);

        // Equip elytra
        mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, 6,
                hotbarSlot.found() ? hotbarSlot.slot() : 0, SlotActionType.SWAP, mc.player);

        // Move old item back to hotbar slot
        mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, inventorySlot.slot(),
                hotbarSlot.found() ? hotbarSlot.slot() : 0, SlotActionType.SWAP, mc.player);

        return true;
    }

    private void useFirework(FindItemResult firework) {
        if (!InvUtil.invSwap(firework.slot())) ;
        mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
        mc.player.swingHand(Hand.MAIN_HAND);

        InvUtil.invSwapBack();
    }
}
