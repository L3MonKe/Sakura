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

    private int currentOffhandSlot = -1;
    private Item currentOffhandItem = null;
    private final List<Slot> gappleStackSlots = new ArrayList<>();
    private final List<Slot> throwableStackSlots = new ArrayList<>();
    private final List<Slot> protectedSlots = new ArrayList<>();

    private Slot currentBestBlockSlot = null;

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if ((mode.is(Mode.InvOpen) && mc.currentScreen instanceof InventoryScreen)
                || (mode.is(Mode.NoMove) && !MovementUtil.isMoving())
                || mode.is(Mode.Always)) {

            List<Slot> itemSlots = mc.player.playerScreenHandler.slots.stream().filter(Slot::hasStack).filter(slot -> slot.id > 4).toList();
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

            for (Slot slot : itemSlots) {
                Item item = slot.getStack().getItem();

                if (isProtectedItem(slot.getStack())) {
                    protectedSlots.add(slot);
                    continue;
                }

                if (slot.getStack().getItem() == Items.TNT && keepTNT.get()) {
                    continue;
                }

                if (item == Items.GOLDEN_APPLE || item == Items.ENCHANTED_GOLDEN_APPLE) {
                    gappleStackSlots.add(slot);
                } else if (isThrowableItem(slot.getStack())) {
                    throwableStackSlots.add(slot);
                }

                if (item instanceof SwordItem) {
                    if (bestSwordSlot == null || InvUtil.getDamage(slot.getStack()) > InvUtil.getDamage(bestSwordSlot.getStack())) {
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
                    if (targetSlot < 0 || targetSlot >= bestArmorSlots.length) {
                        continue;
                    }
                    Slot bestArmorSlot = bestArmorSlots[targetSlot];
                    if (bestArmorSlot == null || InvUtil.getDamage(slot.getStack()) > InvUtil.getDamage(bestArmorSlot.getStack())) {
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
                        int newCount = slot.getStack().getCount();

                        if (currentBestBlockSlot != null && currentBestBlockSlot.hasStack() &&
                                currentBestBlockSlot.getStack().getItem() instanceof BlockItem &&
                                InvUtil.getBlockIndex() > blocks.get()) {
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
                    if (bestAxeSlot == null || InvUtil.getDamage(slot.getStack()) > InvUtil.getDamage(bestAxeSlot.getStack())) {
                        unnecessarySlots.add(bestAxeSlot);
                        bestAxeSlot = slot;
                    } else {
                        unnecessarySlots.add(slot);
                    }
                } else if (item instanceof PickaxeItem) {
                    if (bestPickaxeSlot == null || InvUtil.getDamage(slot.getStack()) > InvUtil.getDamage(bestPickaxeSlot.getStack())) {
                        unnecessarySlots.add(bestPickaxeSlot);
                        bestPickaxeSlot = slot;
                    } else {
                        unnecessarySlots.add(slot);
                    }
                } else if (item instanceof BowItem) {
                    if (bestBowSlot == null || InvUtil.getDamage(slot.getStack()) > InvUtil.getDamage(bestBowSlot.getStack())) {
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
                    if (bestEnderPearlSlot == null || slot.getStack().getCount() > bestEnderPearlSlot.getStack().getCount()) {
                        unnecessarySlots.add(bestEnderPearlSlot);
                        bestEnderPearlSlot = slot;
                    } else {
                        unnecessarySlots.add(slot);
                    }
                } else if (item.getComponents().contains(DataComponentTypes.FOOD)) {
                    if (item == Items.GOLDEN_APPLE || item == Items.ENCHANTED_GOLDEN_APPLE) {
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

            if (currentBestBlockSlot != null && (!currentBestBlockSlot.hasStack() ||
                    !(currentBestBlockSlot.getStack().getItem() instanceof BlockItem))) {
                currentBestBlockSlot = null;
            }

            if (timer.passedMS(randomBetween(delay.getMin(), delay.get()))) {
                for (Slot slot : unnecessarySlots) {
                    if (slot != null && !isProtectedItem(slot.getStack())) {
                        if (slot.getStack().getItem() instanceof BlockItem) {
                            if (InvUtil.getBlockIndex() > blocks.get()) {
                                click(slot, 1, SlotActionType.THROW);
                                timer.reset();
                                return;
                            }
                        } else {
                            click(slot, 1, SlotActionType.THROW);
                            timer.reset();
                            return;
                        }
                    }
                }
            }

            if (timer.passedMS(randomBetween(delay.getMin(), delay.get()))) {
                for (Slot slot : protectedSlots) {
                    if (slot.id >= 36 && slot.id <= 44) {
                        for (int i = 9; i < 36; i++) {
                            Slot backpackSlot = mc.player.playerScreenHandler.slots.get(i);
                            if (!backpackSlot.hasStack()) {
                                click(slot, i, SlotActionType.SWAP);
                                break;
                            }
                        }
                        timer.reset();
                        return;
                    }
                }
            }

            if (timer.passedMS(randomBetween(delay.getMin(), delay.get()))) {
                for (int i = 0; i < bestArmorSlots.length; i++) {
                    Slot slot = bestArmorSlots[i];
                    if (slot != null) {
                        if (!isArmorEquipped(i, slot)) {
                            click(slot, 0, SlotActionType.QUICK_MOVE);
                            timer.reset();
                            return;
                        }
                    }
                }
            }

            if (timer.passedMS(randomBetween(delay.getMin(), delay.get()))) {
                if (bestSwordSlot != null && bestSwordSlot.getStack().getItem() instanceof SwordItem) {
                    int targetSlotId = 36 + slotSword.get() - 1;
                    if (bestSwordSlot.id != targetSlotId) {
                        click(bestSwordSlot, slotSword.get() - 1, SlotActionType.SWAP);
                        timer.reset();
                        return;
                    }
                }

                if (bestBlockSlot != null && bestBlockSlot.getStack().getItem() instanceof BlockItem) {
                    int targetSlotId = 36 + slotBlock.get() - 1;
                    if (bestBlockSlot.id != targetSlotId && InvUtil.isBlockPlaceable(bestBlockSlot.getStack()) &&
                            InvUtil.getBlockIndex() > blocks.get()) {
                        click(bestBlockSlot, slotBlock.get() - 1, SlotActionType.SWAP);
                        timer.reset();
                        return;
                    }
                }

                if (bestFoodSlot != null && bestFoodSlot.getStack().getItem().getComponents().contains(DataComponentTypes.FOOD)) {
                    int targetSlotId = 36 + slotFood.get() - 1;
                    if (bestFoodSlot.id != targetSlotId) {
                        click(bestFoodSlot, slotFood.get() - 1, SlotActionType.SWAP);
                        timer.reset();
                        return;
                    }
                }

                if (bestEnderPearlSlot != null && bestEnderPearlSlot.getStack().getItem() instanceof EnderPearlItem) {
                    int targetSlotId = 36 + slotPearl.get() - 1;
                    if (bestEnderPearlSlot.id != targetSlotId) {
                        click(bestEnderPearlSlot, slotPearl.get() - 1, SlotActionType.SWAP);
                        timer.reset();
                        return;
                    }
                }

                if (bestFishingRodSlot != null && bestFishingRodSlot.getStack().getItem() instanceof FishingRodItem) {
                    int targetSlotId = 36 + slotFishingRod.get() - 1;
                    if (bestFishingRodSlot.id != targetSlotId) {
                        click(bestFishingRodSlot, slotFishingRod.get() - 1, SlotActionType.SWAP);
                        timer.reset();
                        return;
                    }
                }

                if (bestBowSlot != null && bestBowSlot.getStack().getItem() instanceof BowItem) {
                    int targetSlotId = 36 + slotBow.get() - 1;
                    if (bestBowSlot.id != targetSlotId) {
                        click(bestBowSlot, slotBow.get() - 1, SlotActionType.SWAP);
                        timer.reset();
                        return;
                    }
                }

                if (bestAxeSlot != null && bestAxeSlot.getStack().getItem() instanceof AxeItem) {
                    int targetSlotId = 36 + slotAxe.get() - 1;
                    if (bestAxeSlot.id != targetSlotId) {
                        click(bestAxeSlot, slotAxe.get() - 1, SlotActionType.SWAP);
                        timer.reset();
                        return;
                    }
                }

                if (bestPickaxeSlot != null && bestPickaxeSlot.getStack().getItem() instanceof PickaxeItem) {
                    int targetSlotId = 36 + slotPickaxe.get() - 1;
                    if (bestPickaxeSlot.id != targetSlotId) {
                        click(bestPickaxeSlot, slotPickaxe.get() - 1, SlotActionType.SWAP);
                        timer.reset();
                        return;
                    }
                }

                if (bestBucketSlot != null && bestBucketSlot.getStack().getItem() == Items.WATER_BUCKET) {
                    int targetSlotId = 36 + slotBucket.get() - 1;
                    if (bestBucketSlot.id != targetSlotId && slotBucket.get() > 0) {
                        click(bestBucketSlot, slotBucket.get() - 1, SlotActionType.SWAP);
                        timer.reset();
                        return;
                    }
                }
                timer.reset();
                return;
            }

            if (timer.passedMS(randomBetween(delay.getMin(), delay.get()))) {
                manageOffhand();
                timer.reset();
            }
        } else {
            timer.reset();
            currentBestBlockSlot = null;
        }
    }

    private boolean isProtectedItem(ItemStack stack) {
        Item item = stack.getItem();
        return (item == Items.END_CRYSTAL) ||
                (item == Items.TOTEM_OF_UNDYING);
    }

    private void manageOffhand() {
        OffhandMode mode = offHandMode.get();
        if (mode == OffhandMode.None) return;

        Slot offhandSlot = getOffhandSlot();
        if (offhandSlot == null) return;

        ItemStack offhandStack = offhandSlot.getStack();

        if (mode == OffhandMode.Gapple) {
            if (!isGapple(offhandStack.getItem())) {
                if (!gappleStackSlots.isEmpty()) {
                    gappleStackSlots.sort((slot1, slot2) -> {
                        Item item1 = slot1.getStack().getItem();
                        Item item2 = slot2.getStack().getItem();

                        if (item1 == Items.ENCHANTED_GOLDEN_APPLE && item2 != Items.ENCHANTED_GOLDEN_APPLE) {
                            return -1;
                        } else if (item1 != Items.ENCHANTED_GOLDEN_APPLE && item2 == Items.ENCHANTED_GOLDEN_APPLE) {
                            return 1;
                        }
                        return Integer.compare(slot2.getStack().getCount(), slot1.getStack().getCount());
                    });

                    Slot bestGappleSlot = gappleStackSlots.get(0);
                    if (bestGappleSlot.id != 45) {
                        if (offhandStack.isEmpty()) {
                            putItemInSlotOFF(45, bestGappleSlot.id);
                        } else {
                            putItemInSlotOFF(bestGappleSlot.id, 45);
                        }
                        currentOffhandSlot = 45;
                        currentOffhandItem = bestGappleSlot.getStack().getItem();
                        timer.reset();
                    }
                }
            }
        } else if (mode == OffhandMode.Throwable) {
            if (!isThrowableItem(offhandStack)) {
                if (!throwableStackSlots.isEmpty()) {
                    throwableStackSlots.sort((slot1, slot2) -> {
                        ItemStack item1 = slot1.getStack();
                        ItemStack item2 = slot2.getStack();
                        int priority1 = getThrowablePriority(item1);
                        int priority2 = getThrowablePriority(item2);

                        if (priority1 != priority2) {
                            return Integer.compare(priority2, priority1);
                        }
                        return Integer.compare(item2.getCount(), item1.getCount());
                    });

                    Slot bestThrowableSlot = throwableStackSlots.get(0);
                    if (bestThrowableSlot.id != 45) {
                        if (offhandStack.isEmpty()) {
                            putItemInSlotOFF(45, bestThrowableSlot.id);
                        } else {
                            putItemInSlotOFF(bestThrowableSlot.id, 45);
                        }
                        currentOffhandSlot = 45;
                        currentOffhandItem = bestThrowableSlot.getStack().getItem();
                        timer.reset();
                    }
                }
            }
        }
    }

    private boolean isBetterGapple(ItemStack newStack, ItemStack currentStack) {
        Item newItem = newStack.getItem();
        Item currentItem = currentStack.getItem();

        if (newItem == Items.ENCHANTED_GOLDEN_APPLE && currentItem != Items.ENCHANTED_GOLDEN_APPLE) {
            return true;
        } else if (newItem != Items.ENCHANTED_GOLDEN_APPLE && currentItem == Items.ENCHANTED_GOLDEN_APPLE) {
            return false;
        }

        if (newStack.getCount() > currentStack.getCount()) {
            return true;
        } else if (newStack.getCount() < currentStack.getCount()) {
            return false;
        }

        return Math.random() < 0.5;
    }

    private boolean isBetterThrowable(ItemStack newStack, ItemStack currentStack) {
        int newPriority = getThrowablePriority(newStack);
        int currentPriority = getThrowablePriority(currentStack);

        if (newPriority > currentPriority) {
            return true;
        } else if (newPriority == currentPriority) {
            return newStack.getCount() > currentStack.getCount();
        }
        return false;
    }

    private void putItemInSlotOFF(int slot, int slotIn) {
        mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, slot, 0, SlotActionType.PICKUP, mc.player);
        mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, slotIn, 0, SlotActionType.PICKUP, mc.player);
    }

    private boolean isGapple(Item item) {
        return item == Items.GOLDEN_APPLE || item == Items.ENCHANTED_GOLDEN_APPLE;
    }

    private Slot getOffhandSlot() {
        return mc.player.playerScreenHandler.slots.stream()
                .filter(slot -> slot.id == 45)
                .findFirst()
                .orElse(null);
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

    public static double randomBetween(double min, double max) {
        return (max + (min - max) * Math.random());
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
        currentOffhandSlot = -1;
        currentOffhandItem = null;
        currentBestBlockSlot = null;
        gappleStackSlots.clear();
        throwableStackSlots.clear();
    }
}
