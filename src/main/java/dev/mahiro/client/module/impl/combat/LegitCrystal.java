package dev.mahiro.client.module.impl.combat;

import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.events.EventType;
import dev.mahiro.client.events.packet.PacketEvent;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.utils.time.TimerUtil;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class LegitCrystal extends Module {

    private final NumberValue<Double> cooldown = new NumberValue<>("Cooldown", "冷却", 50.0, 0.0, 1000.0, 1.0);
    private final BoolValue selfToggle = new BoolValue("Self Toggle", "自动关闭", true);

    private final TimerUtil timerUtil = new TimerUtil();
    
    private boolean hasAttacked = false;
    private boolean crystalBroken = false;
    private boolean crystalPlaced = false;

    public LegitCrystal() {
        super("LegitCrystal", "合法水晶连招 (Ghost)", Category.Combat);
    }

    @Override
    public void onEnable() {
        hasAttacked = false;
        crystalBroken = false;
        crystalPlaced = false;
        timerUtil.reset();
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (nullCheck()) return;

        if (event.getType() == EventType.SEND && event.getPacket() instanceof PlayerInteractEntityC2SPacket) {
            HitResult hit = mc.crosshairTarget;
            if (hit instanceof EntityHitResult entityHit) {
                 Entity target = entityHit.getEntity();
                 if (!(target instanceof EndCrystalEntity)) {
                     hasAttacked = true;
                     crystalBroken = false;
                     crystalPlaced = false;
                     timerUtil.reset();
                 }
            }
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (!crystalBroken) {
            breakCrystal();
        }

        handlePlacement();
    }

    private void breakCrystal() {
        HitResult hit = mc.crosshairTarget;
        if (!(hit instanceof EntityHitResult entityHit)) return;
        Entity target = entityHit.getEntity();

        if (target instanceof EndCrystalEntity && timerUtil.passedMS(cooldown.get())) {
            mc.interactionManager.attackEntity(mc.player, target);
            mc.player.swingHand(Hand.MAIN_HAND);
            timerUtil.reset();
            crystalBroken = true;
            if (selfToggle.get()) {
                toggle();
            }
        }
    }

    private void handlePlacement() {
        if (!hasAttacked) return;
        if (!timerUtil.passedMS(cooldown.get())) return;

        if (isObsidianInCrosshair()) {
             if (!crystalPlaced) {
                 int crystalSlot = findSlot(Items.END_CRYSTAL);
                 if (crystalSlot != -1) {
                     if (mc.player.getInventory().selectedSlot != crystalSlot) {
                         mc.player.getInventory().selectedSlot = crystalSlot;
                         return; 
                     }
                     placeBlock();
                     crystalPlaced = true;
                     timerUtil.reset();
                 }
             }
        } else if (isBlockInCrosshair()) {
            int obsidianSlot = findSlot(Items.OBSIDIAN);
            if (obsidianSlot != -1) {
                 if (mc.player.getInventory().selectedSlot != obsidianSlot) {
                     mc.player.getInventory().selectedSlot = obsidianSlot;
                     timerUtil.reset();
                     return;
                 }
                 placeBlock();
                 
                 int crystalSlot = findSlot(Items.END_CRYSTAL);
                 if (crystalSlot != -1) {
                     mc.player.getInventory().selectedSlot = crystalSlot;
                 }
                 timerUtil.reset();
            }
        }
    }

    private void placeBlock() {
        if (mc.crosshairTarget instanceof BlockHitResult hitResult) {
            mc.player.swingHand(mc.player.getActiveHand());
            mc.getNetworkHandler().sendPacket(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, hitResult, 0));
        }
    }

    private boolean isObsidianInCrosshair() {
        if (mc.crosshairTarget instanceof BlockHitResult hitResult) {
            BlockPos pos = hitResult.getBlockPos();
            return mc.world.getBlockState(pos).getBlock() == Blocks.OBSIDIAN;
        }
        return false;
    }
    
    private boolean isBlockInCrosshair() {
        return mc.crosshairTarget instanceof BlockHitResult;
    }

    private int findSlot(Item item) {
        for (int i = 0; i < 9; i++) {
            if (mc.player.getInventory().getStack(i).getItem() == item) {
                return i;
            }
        }
        return -1;
    }
}
