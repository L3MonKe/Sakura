package dev.sakura.client.module.impl.player;

import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.player.InvUtil;
import dev.sakura.client.utils.player.MovementUtil;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.*;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class InvManager extends Module {
    public InvManager() {
        super("InvManager", "背包管理", Category.Player);
    }

    public enum Mode {
        InvOpen,
        NoMove,
        Always
    }

    public enum OffhandMode {
        Gapple,
        Throwable,
        None
    }

    public final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.InvOpen);
    private final BoolValue swing = new BoolValue("Swing", "挥手动画", true);
    public final EnumValue<OffhandMode> offHandMode = new EnumValue<>("Offhand Mode", "副手模式", OffhandMode.None);
    public final NumberValue<Integer> delay = new NumberValue<>("Delay", "延迟", 1, 1, 250, 1);
    private final BoolValue keepTNT = new BoolValue("Keep TNT", "保留TNT", true);
    public final NumberValue<Integer> blocks = new NumberValue<>("Blocks", "方块阈值", 128, 16, 512, 1);

    public final NumberValue<Integer> slotSword = new NumberValue<>("Sword Slot", "剑槽位", 1, 0, 9, 1);
    public final NumberValue<Integer> slotBlock = new NumberValue<>("Block Slot", "方块槽位", 2, 0, 9, 1);
    public final NumberValue<Integer> slotFood = new NumberValue<>("Food Slot", "食物槽位", 3, 0, 9, 1);
    public final NumberValue<Integer> slotPearl = new NumberValue<>("Pearl Slot", "珍珠槽位", 4, 0, 9, 1);
    public final NumberValue<Integer> slotAxe = new NumberValue<>("Axe Slot", "斧子槽位", 5, 0, 9, 1);
    public final NumberValue<Integer> slotPickaxe = new NumberValue<>("Pickaxe Slot", "镐子槽位", 6, 0, 9, 1);
    public final NumberValue<Integer> slotBucket = new NumberValue<>("Bucket Slot", "水桶槽位", 7, 0, 9, 1);
    public final NumberValue<Integer> slotBow = new NumberValue<>("Bow Slot", "弓槽位", 8, 0, 9, 1);
    public final NumberValue<Integer> slotFishingRod = new NumberValue<>("FishingRod Slot", "鱼竿槽位", 9, 0, 9, 1);

    private final TimerUtil timer = new TimerUtil();

    private long nextDelayMs = 0L;
    private final List<Slot> gappleStackSlots = new ArrayList<>();
    private final List<Slot> throwableStackSlots = new ArrayList<>();
    private final List<Slot> protectedSlots = new ArrayList<>();

    private Slot currentBestBlockSlot = null;

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        boolean shouldRun =
                (mode.is(Mode.InvOpen) && mc.currentScreen instanceof InventoryScreen)
                        || (mode.is(Mode.NoMove) && !MovementUtil.isMoving())
                        || mode.is(Mode.Always);
        if (!shouldRun) {
            timer.reset();
            nextDelayMs = 0L;
            currentBestBlockSlot = null;
            return;
        }

        List<Slot> unnecessarySlots = new ArrayList<>();

        gappleStackSlots.clear();
        throwableStackSlots.clear();
        protectedSlots.clear();

        Slot bestSwordSlot = null;
        Slot bestBlockSlot = null;
        Slot bestFoodSlot = null;
        Slot[] bestArmorSlots = new Slot[4];
        Slot bestBowSlot = null;
        Slot bestEnderPearlSlot = null;
        Slot bestFishingRodSlot = null;
        Slot bestAxeSlot = null;
        Slot bestPickaxeSlot = null;
        Slot bestBucketSlot = null;

        for (Slot slot : mc.player.playerScreenHandler.slots) {
            if (!slot.hasStack()) continue;
            if (slot.id < 9 || slot.id > 44) continue;

            ItemStack stack = slot.getStack();
            Item item = stack.getItem();

            if (isProtectedItem(stack)) {
                protectedSlots.add(slot);
                continue;
            }

            if (item == Items.TNT && keepTNT.get()) {
                continue;
            }

            if (isGapple(item)) {
                gappleStackSlots.add(slot);
            } else if (isThrowableItem(stack)) {
                throwableStackSlots.add(slot);
            }

            if (item instanceof SwordItem) {
                if (bestSwordSlot == null || InvUtil.getDamage(stack) > InvUtil.getDamage(bestSwordSlot.getStack())) {
                    unnecessarySlots.add(bestSwordSlot);
                    bestSwordSlot = slot;
                } else {
                    unnecessarySlots.add(slot);
                }
            } else if (item instanceof ArmorItem armorItem) {
                int targetSlot = switch (armorItem.getDefaultStack().get(DataComponentTypes.EQUIPPABLE).slot()) {
                    case HEAD -> 0;
                    case CHEST -> 1;
                    case LEGS -> 2;
                    case FEET -> 3;
                    default -> -1;
                };
                if (targetSlot < 0 || targetSlot >= bestArmorSlots.length) continue;

                Slot bestArmorSlot = bestArmorSlots[targetSlot];
                if (bestArmorSlot == null || InvUtil.getDamage(stack) > InvUtil.getDamage(bestArmorSlot.getStack())) {
                    unnecessarySlots.add(bestArmorSlot);
                    bestArmorSlots[targetSlot] = slot;
                } else {
                    unnecessarySlots.add(slot);
                }
            } else if (item instanceof BlockItem && InvUtil.isBlockPlaceable(item.getDefaultStack())) {
                if (bestBlockSlot == null) {
                    bestBlockSlot = slot;
                } else {
                    int currentCount = bestBlockSlot.getStack().getCount();
                    int newCount = stack.getCount();

                    if (currentBestBlockSlot != null && currentBestBlockSlot.hasStack()
                            && currentBestBlockSlot.getStack().getItem() instanceof BlockItem
                            && InvUtil.getBlockIndex() > blocks.get()) {
                        if (slot.id == currentBestBlockSlot.id) {
                            bestBlockSlot = slot;
                        } else if (newCount > currentBestBlockSlot.getStack().getCount() + 10) {
                            unnecessarySlots.add(bestBlockSlot);
                            bestBlockSlot = slot;
                            currentBestBlockSlot = slot;
                        } else {
                            unnecessarySlots.add(slot);
                        }
                    } else {
                        if (newCount > currentCount && InvUtil.getBlockIndex() > blocks.get()) {
                            unnecessarySlots.add(bestBlockSlot);
                            bestBlockSlot = slot;
                            currentBestBlockSlot = slot;
                        } else if (newCount == currentCount) {
                            if (currentBestBlockSlot != null && currentBestBlockSlot.id == bestBlockSlot.id) {
                                unnecessarySlots.add(slot);
                            } else {
                                if (slot.id < bestBlockSlot.id) {
                                    unnecessarySlots.add(bestBlockSlot);
                                    bestBlockSlot = slot;
                                    currentBestBlockSlot = slot;
                                } else {
                                    unnecessarySlots.add(slot);
                                }
                            }
                        } else {
                            if (InvUtil.getBlockIndex() > blocks.get()) {
                                unnecessarySlots.add(slot);
                            }
                        }
                    }
                }
            } else if (item instanceof AxeItem) {
                if (bestAxeSlot == null || InvUtil.getDamage(stack) > InvUtil.getDamage(bestAxeSlot.getStack())) {
                    unnecessarySlots.add(bestAxeSlot);
                    bestAxeSlot = slot;
                } else {
                    unnecessarySlots.add(slot);
                }
            } else if (item instanceof PickaxeItem) {
                if (bestPickaxeSlot == null || InvUtil.getDamage(stack) > InvUtil.getDamage(bestPickaxeSlot.getStack())) {
                    unnecessarySlots.add(bestPickaxeSlot);
                    bestPickaxeSlot = slot;
                } else {
                    unnecessarySlots.add(slot);
                }
            } else if (item instanceof BowItem) {
                if (bestBowSlot == null || InvUtil.getDamage(stack) > InvUtil.getDamage(bestBowSlot.getStack())) {
                    unnecessarySlots.add(bestBowSlot);
                    bestBowSlot = slot;
                } else {
                    unnecessarySlots.add(slot);
                }
            } else if (item instanceof FishingRodItem) {
                if (bestFishingRodSlot == null) {
                    bestFishingRodSlot = slot;
                } else {
                    unnecessarySlots.add(slot);
                }
            } else if (item instanceof EnderPearlItem) {
                if (bestEnderPearlSlot == null || stack.getCount() > bestEnderPearlSlot.getStack().getCount()) {
                    unnecessarySlots.add(bestEnderPearlSlot);
                    bestEnderPearlSlot = slot;
                } else {
                    unnecessarySlots.add(slot);
                }
            } else if (item.getComponents().contains(DataComponentTypes.FOOD)) {
                if (isGapple(item)) {
                    if (bestFoodSlot == null) {
                        bestFoodSlot = slot;
                    } else {
                        Item currentBest = bestFoodSlot.getStack().getItem();
                        if (item == Items.ENCHANTED_GOLDEN_APPLE && currentBest == Items.GOLDEN_APPLE) {
                            bestFoodSlot = slot;
                        } else if (item == Items.ENCHANTED_GOLDEN_APPLE && currentBest == Items.ENCHANTED_GOLDEN_APPLE) {
                            bestFoodSlot = slot;
                        } else if (item == Items.GOLDEN_APPLE && currentBest == Items.GOLDEN_APPLE) {
                            bestFoodSlot = slot;
                        }
                    }
                } else {
                    if (bestFoodSlot == null) {
                        bestFoodSlot = slot;
                    } else {
                        unnecessarySlots.add(slot);
                    }
                }
            } else if (item == Items.WATER_BUCKET) {
                if (bestBucketSlot == null) {
                    bestBucketSlot = slot;
                } else {
                    unnecessarySlots.add(slot);
                }
            }
        }

        if (currentBestBlockSlot != null && (currentBestBlockSlot.id < 9 || currentBestBlockSlot.id > 44
                || !currentBestBlockSlot.hasStack()
                || !(currentBestBlockSlot.getStack().getItem() instanceof BlockItem))) {
            currentBestBlockSlot = null;
        }

        if (!readyToAct()) {
            return;
        }

        if (tryDropUnnecessary(unnecessarySlots)) {
            return;
        }
        if (tryMoveProtected()) {
            return;
        }
        if (tryEquipArmor(bestArmorSlots)) {
            return;
        }
        if (trySwapHotbar(bestSwordSlot, bestBlockSlot, bestFoodSlot, bestEnderPearlSlot, bestFishingRodSlot, bestBowSlot, bestAxeSlot, bestPickaxeSlot, bestBucketSlot)) {
            return;
        }
        if (manageOffhand()) {
            return;
        }
    }

    private boolean readyToAct() {
        if (nextDelayMs <= 0L) {
            nextDelayMs = randomDelayMs();
        }
        return timer.passedMS(nextDelayMs);
    }

    private void markActed() {
        timer.reset();
        nextDelayMs = randomDelayMs();
    }

    private long randomDelayMs() {
        int min = Math.max(0, delay.getMin());
        int max = Math.max(min, delay.get());
        if (max == min) return max;
        return ThreadLocalRandom.current().nextLong((long) min, (long) max + 1L);
    }

    private boolean tryDropUnnecessary(List<Slot> unnecessarySlots) {
        for (Slot slot : unnecessarySlots) {
            if (slot == null) continue;
            if (slot.id < 9 || slot.id > 44) continue;
            if (!slot.hasStack()) continue;
            if (isProtectedItem(slot.getStack())) continue;

            if (slot.getStack().getItem() instanceof BlockItem) {
                if (InvUtil.getBlockIndex() <= blocks.get()) continue;
            }

            click(slot, 1, SlotActionType.THROW);
            markActed();
            return true;
        }
        return false;
    }

    private boolean tryMoveProtected() {
        for (Slot slot : protectedSlots) {
            if (slot.id < 36 || slot.id > 44) continue;
            for (int i = 9; i < 36; i++) {
                Slot backpackSlot = mc.player.playerScreenHandler.slots.get(i);
                if (!backpackSlot.hasStack()) {
                    click(slot, i, SlotActionType.SWAP);
                    markActed();
                    return true;
                }
            }
        }
        return false;
    }

    private boolean tryEquipArmor(Slot[] bestArmorSlots) {
        for (int i = 0; i < bestArmorSlots.length; i++) {
            Slot slot = bestArmorSlots[i];
            if (slot == null) continue;
            if (isArmorEquipped(i, slot)) continue;
            click(slot, 0, SlotActionType.QUICK_MOVE);
            markActed();
            return true;
        }
        return false;
    }

    private boolean trySwapHotbar(
            Slot bestSwordSlot,
            Slot bestBlockSlot,
            Slot bestFoodSlot,
            Slot bestEnderPearlSlot,
            Slot bestFishingRodSlot,
            Slot bestBowSlot,
            Slot bestAxeSlot,
            Slot bestPickaxeSlot,
            Slot bestBucketSlot
    ) {
        int swordIndex = slotSword.get() - 1;
        if (bestSwordSlot != null && swordIndex >= 0 && swordIndex < 9 && bestSwordSlot.getStack().getItem() instanceof SwordItem) {
            int targetSlotId = 36 + swordIndex;
            if (bestSwordSlot.id != targetSlotId) {
                click(bestSwordSlot, swordIndex, SlotActionType.SWAP);
                markActed();
                return true;
            }
        }

        int blockIndex = slotBlock.get() - 1;
        if (bestBlockSlot != null && blockIndex >= 0 && blockIndex < 9 && bestBlockSlot.getStack().getItem() instanceof BlockItem) {
            int targetSlotId = 36 + blockIndex;
            if (bestBlockSlot.id != targetSlotId && InvUtil.isBlockPlaceable(bestBlockSlot.getStack()) && InvUtil.getBlockIndex() > blocks.get()) {
                click(bestBlockSlot, blockIndex, SlotActionType.SWAP);
                markActed();
                return true;
            }
        }

        int foodIndex = slotFood.get() - 1;
        if (bestFoodSlot != null && foodIndex >= 0 && foodIndex < 9 && bestFoodSlot.getStack().getItem().getComponents().contains(DataComponentTypes.FOOD)) {
            int targetSlotId = 36 + foodIndex;
            if (bestFoodSlot.id != targetSlotId) {
                click(bestFoodSlot, foodIndex, SlotActionType.SWAP);
                markActed();
                return true;
            }
        }

        int pearlIndex = slotPearl.get() - 1;
        if (bestEnderPearlSlot != null && pearlIndex >= 0 && pearlIndex < 9 && bestEnderPearlSlot.getStack().getItem() instanceof EnderPearlItem) {
            int targetSlotId = 36 + pearlIndex;
            if (bestEnderPearlSlot.id != targetSlotId) {
                click(bestEnderPearlSlot, pearlIndex, SlotActionType.SWAP);
                markActed();
                return true;
            }
        }

        int rodIndex = slotFishingRod.get() - 1;
        if (bestFishingRodSlot != null && rodIndex >= 0 && rodIndex < 9 && bestFishingRodSlot.getStack().getItem() instanceof FishingRodItem) {
            int targetSlotId = 36 + rodIndex;
            if (bestFishingRodSlot.id != targetSlotId) {
                click(bestFishingRodSlot, rodIndex, SlotActionType.SWAP);
                markActed();
                return true;
            }
        }

        int bowIndex = slotBow.get() - 1;
        if (bestBowSlot != null && bowIndex >= 0 && bowIndex < 9 && bestBowSlot.getStack().getItem() instanceof BowItem) {
            int targetSlotId = 36 + bowIndex;
            if (bestBowSlot.id != targetSlotId) {
                click(bestBowSlot, bowIndex, SlotActionType.SWAP);
                markActed();
                return true;
            }
        }

        int axeIndex = slotAxe.get() - 1;
        if (bestAxeSlot != null && axeIndex >= 0 && axeIndex < 9 && bestAxeSlot.getStack().getItem() instanceof AxeItem) {
            int targetSlotId = 36 + axeIndex;
            if (bestAxeSlot.id != targetSlotId) {
                click(bestAxeSlot, axeIndex, SlotActionType.SWAP);
                markActed();
                return true;
            }
        }

        int pickaxeIndex = slotPickaxe.get() - 1;
        if (bestPickaxeSlot != null && pickaxeIndex >= 0 && pickaxeIndex < 9 && bestPickaxeSlot.getStack().getItem() instanceof PickaxeItem) {
            int targetSlotId = 36 + pickaxeIndex;
            if (bestPickaxeSlot.id != targetSlotId) {
                click(bestPickaxeSlot, pickaxeIndex, SlotActionType.SWAP);
                markActed();
                return true;
            }
        }

        int bucketIndex = slotBucket.get() - 1;
        if (bestBucketSlot != null && bucketIndex >= 0 && bucketIndex < 9 && bestBucketSlot.getStack().getItem() == Items.WATER_BUCKET) {
            int targetSlotId = 36 + bucketIndex;
            if (bestBucketSlot.id != targetSlotId) {
                click(bestBucketSlot, bucketIndex, SlotActionType.SWAP);
                markActed();
                return true;
            }
        }

        return false;
    }

    private boolean isProtectedItem(ItemStack stack) {
        Item item = stack.getItem();
        return (item == Items.END_CRYSTAL) ||
                (item == Items.TOTEM_OF_UNDYING);
    }

    private boolean manageOffhand() {
        OffhandMode mode = offHandMode.get();
        if (mode == OffhandMode.None) return false;

        Slot offhandSlot = getOffhandSlot();
        if (offhandSlot == null) return false;

        ItemStack offhandStack = offhandSlot.getStack();

        if (mode == OffhandMode.Gapple) {
            if (!isGapple(offhandStack.getItem())) {
                Slot bestGappleSlot = findBestGappleSlot();
                if (bestGappleSlot != null && bestGappleSlot.id != 45) {
                    putItemInSlotOFF(bestGappleSlot.id, 45);
                    markActed();
                    return true;
                }
            }
        } else if (mode == OffhandMode.Throwable) {
            if (!isThrowableItem(offhandStack)) {
                Slot bestThrowableSlot = findBestThrowableSlot();
                if (bestThrowableSlot != null && bestThrowableSlot.id != 45) {
                    putItemInSlotOFF(bestThrowableSlot.id, 45);
                    markActed();
                    return true;
                }
            }
        }
        return false;
    }

    private Slot findBestGappleSlot() {
        Slot best = null;
        for (Slot slot : gappleStackSlots) {
            if (slot == null || !slot.hasStack()) continue;
            Item item = slot.getStack().getItem();
            if (!isGapple(item)) continue;

            if (best == null) {
                best = slot;
                continue;
            }

            Item bestItem = best.getStack().getItem();
            if (item == Items.ENCHANTED_GOLDEN_APPLE && bestItem != Items.ENCHANTED_GOLDEN_APPLE) {
                best = slot;
                continue;
            }
            if (item != Items.ENCHANTED_GOLDEN_APPLE && bestItem == Items.ENCHANTED_GOLDEN_APPLE) {
                continue;
            }

            int count = slot.getStack().getCount();
            int bestCount = best.getStack().getCount();
            if (count > bestCount || (count == bestCount && slot.id < best.id)) {
                best = slot;
            }
        }
        return best;
    }

    private Slot findBestThrowableSlot() {
        Slot best = null;
        for (Slot slot : throwableStackSlots) {
            if (slot == null || !slot.hasStack()) continue;
            ItemStack stack = slot.getStack();
            if (!isThrowableItem(stack)) continue;

            if (best == null) {
                best = slot;
                continue;
            }

            int priority = getThrowablePriority(stack);
            ItemStack bestStack = best.getStack();
            int bestPriority = getThrowablePriority(bestStack);
            if (priority > bestPriority) {
                best = slot;
                continue;
            }
            if (priority < bestPriority) {
                continue;
            }

            int count = stack.getCount();
            int bestCount = bestStack.getCount();
            if (count > bestCount || (count == bestCount && slot.id < best.id)) {
                best = slot;
            }
        }
        return best;
    }

    private void putItemInSlotOFF(int fromSlotId, int toSlotId) {
        int syncId = mc.player.currentScreenHandler.syncId;
        mc.interactionManager.clickSlot(syncId, fromSlotId, 0, SlotActionType.PICKUP, mc.player);
        mc.interactionManager.clickSlot(syncId, toSlotId, 0, SlotActionType.PICKUP, mc.player);
        mc.interactionManager.clickSlot(syncId, fromSlotId, 0, SlotActionType.PICKUP, mc.player);
    }

    private boolean isGapple(Item item) {
        return item == Items.GOLDEN_APPLE || item == Items.ENCHANTED_GOLDEN_APPLE;
    }

    private Slot getOffhandSlot() {
        if (mc.player.playerScreenHandler.slots.size() > 45) {
            Slot slot = mc.player.playerScreenHandler.slots.get(45);
            if (slot != null && slot.id == 45) return slot;
        }
        for (Slot slot : mc.player.playerScreenHandler.slots) {
            if (slot.id == 45) return slot;
        }
        return null;
    }

    private boolean isThrowableItem(ItemStack stack) {
        Item item = stack.getItem();
        return item == Items.SNOWBALL || item == Items.EGG || item == Items.EXPERIENCE_BOTTLE;
    }

    private int getThrowablePriority(ItemStack stack) {
        Item item = stack.getItem();
        if (item == Items.SNOWBALL || item == Items.EGG) return 2;
        if (item == Items.EXPERIENCE_BOTTLE) return 1;
        return 0;
    }

    private void click(Slot slot, int button, SlotActionType type) {
        mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, slot.id, button, type, mc.player);
        if (swing.get() && type == SlotActionType.THROW) {
            mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
        }
    }

    private boolean isArmorEquipped(int index, Slot slot) {
        List<ItemStack> equippedArmor = new ArrayList<>();
        for (ItemStack armor : mc.player.getArmorItems()) {
            equippedArmor.add(armor);
        }

        int equippedIndex = 3 - index;

        if (equippedIndex >= 0 && equippedIndex < equippedArmor.size()) {
            ItemStack equipped = equippedArmor.get(equippedIndex);
            ItemStack candidate = slot.getStack();

            return isBetterOrEqualArmor(equipped, candidate);
        }
        return false;
    }

    private boolean isBetterOrEqualArmor(ItemStack equipped, ItemStack candidate) {
        if (equipped.getItem() != candidate.getItem()) {
            return false;
        }

        double equippedDamage = InvUtil.getDamage(equipped);
        double candidateDamage = InvUtil.getDamage(candidate);

        return equippedDamage <= candidateDamage;
    }

    @Override
    protected void onDisable() {
        nextDelayMs = 0L;
        currentBestBlockSlot = null;
        gappleStackSlots.clear();
        throwableStackSlots.clear();
    }
}
