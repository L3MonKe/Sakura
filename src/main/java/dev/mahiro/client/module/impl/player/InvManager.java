package dev.mahiro.client.module.impl.player;

import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.utils.player.EnchantmentUtil;
import dev.mahiro.client.utils.player.MovementUtil;
import dev.mahiro.client.utils.player.SlotUtil;
import dev.mahiro.client.utils.time.TimerUtil;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.EnumValue;
import dev.mahiro.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Block;
import net.minecraft.block.CropBlock;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.*;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final BoolValue keepTNT = new BoolValue("Keep TNT", "保留TNT", false);
    public final NumberValue<Integer> blocks = new NumberValue<>("Blocks", "方块阈值", 512, 16, 512, 1);

    public final NumberValue<Integer> slotSword = new NumberValue<>("Sword Slot", "剑槽位", 1, 0, 9, 1);
    public final NumberValue<Integer> slotFood = new NumberValue<>("Food Slot", "食物槽位", 2, 0, 9, 1);
    public final NumberValue<Integer> slotPearl = new NumberValue<>("Pearl Slot", "珍珠槽位", 3, 0, 9, 1);
    public final NumberValue<Integer> slotBlock = new NumberValue<>("Block Slot", "方块槽位", 4, 0, 9, 1);
    public final NumberValue<Integer> slotAxe = new NumberValue<>("Axe Slot", "斧子槽位", 5, 0, 9, 1);
    public final NumberValue<Integer> slotPickaxe = new NumberValue<>("Pickaxe Slot", "镐子槽位", 6, 0, 9, 1);
    public final NumberValue<Integer> slotBucket = new NumberValue<>("Bucket Slot", "水桶槽位", 7, 0, 9, 1);
    public final NumberValue<Integer> slotBow = new NumberValue<>("Bow Slot", "弓槽位", 8, 0, 9, 1);
    public final NumberValue<Integer> slotFishingRod = new NumberValue<>("FishingRod Slot", "鱼竿槽位", 9, 0, 9, 1);

    private final TimerUtil timer = new TimerUtil();

    private Slot currentBestBlockSlot = null;
    private final List<Slot> protectedSlots = new ArrayList<>();

    @Override
    protected void onDisable() {
        currentBestBlockSlot = null;
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if ((mode.is(Mode.InvOpen) && mc.currentScreen instanceof InventoryScreen) || (mode.is(Mode.NoMove) && !MovementUtil.isMoving()) || mode.is(Mode.Always)) {
            if (mc.player.playerScreenHandler != mc.player.currentScreenHandler) {
                return;
            }

            List<Slot> hookSlot = new ArrayList<>();

            protectedSlots.clear();

            Slot bestSwordSlot = null;
            Slot bestBlockSlot = null;
            Item bestBlockItem = null;
            Slot bestFoodSlot = null;
            Slot[] bestArmorSlots = new Slot[4];
            Slot bestBowSlot = null;
            Slot bestEnderPearlSlot = null;
            Slot bestFishingRodSlot = null;
            Slot bestAxeSlot = null;
            Slot bestPickaxeSlot = null;
            Slot bestBucketSlot = null;

            List<Slot> blockSlots = new ArrayList<>();
            Map<Item, Integer> blockTotals = new HashMap<>();

            for (Slot slot : mc.player.playerScreenHandler.slots) {
                if (!slot.hasStack() || slot.id <= 8 || slot.id == SlotUtil.indexToId(40)) {
                    continue;
                }
                Item item = slot.getStack().getItem();

                if (isProtectedItem(slot.getStack())) {
                    protectedSlots.add(slot);
                    continue;
                }

                if (slot.getStack().getItem() == Items.TNT && keepTNT.get()) {
                    continue;
                }

                if (item instanceof SwordItem) {
                    if (bestSwordSlot == null || getSwordScore(slot.getStack()) > getSwordScore(bestSwordSlot.getStack())) {
                        hookSlot.add(bestSwordSlot);
                        bestSwordSlot = slot;
                    } else {
                        hookSlot.add(slot);
                    }
                } else if (item instanceof ArmorItem armorItem) {
                    int targetSlot = switch (armorItem.getDefaultStack().get(DataComponentTypes.EQUIPPABLE).slot()) {
                        case HEAD -> 0;
                        case CHEST -> 1;
                        case LEGS -> 2;
                        case FEET -> 3;
                        default -> -1;
                    };
                    if (targetSlot < 0) {
                        continue;
                    }
                    double equippedScore = getEquippedArmorScoreByIndex(targetSlot);
                    double candidateScore = getArmorScore(slot.getStack());
                    if (equippedScore >= candidateScore) {
                        hookSlot.add(slot);
                        continue;
                    }
                    Slot bestArmorSlot = bestArmorSlots[targetSlot];
                    if (bestArmorSlot == null || candidateScore > getArmorScore(bestArmorSlot.getStack())) {
                        hookSlot.add(bestArmorSlot);
                        bestArmorSlots[targetSlot] = slot;
                    } else {
                        hookSlot.add(slot);
                    }
                } else if (item instanceof BlockItem && isBlockPlaceable(item.getDefaultStack())) {
                    blockSlots.add(slot);
                    blockTotals.merge(item, slot.getStack().getCount(), Integer::sum);
                } else if (item instanceof AxeItem) {
                    if (bestAxeSlot == null || getAxeScore(slot.getStack()) > getAxeScore(bestAxeSlot.getStack())) {
                        hookSlot.add(bestAxeSlot);
                        bestAxeSlot = slot;
                    } else {
                        hookSlot.add(slot);
                    }
                } else if (item instanceof PickaxeItem) {
                    if (bestPickaxeSlot == null || getPickaxeScore(slot.getStack()) > getPickaxeScore(bestPickaxeSlot.getStack())) {
                        hookSlot.add(bestPickaxeSlot);
                        bestPickaxeSlot = slot;
                    } else {
                        hookSlot.add(slot);
                    }
                } else if (item instanceof BowItem) {
                    if (bestBowSlot == null || getBowScore(slot.getStack()) > getBowScore(bestBowSlot.getStack())) {
                        hookSlot.add(bestBowSlot);
                        bestBowSlot = slot;
                    } else {
                        hookSlot.add(slot);
                    }
                } else if (item instanceof FishingRodItem) {
                    if (bestFishingRodSlot == null || getFishingRodScore(slot.getStack()) > getFishingRodScore(bestFishingRodSlot.getStack())) {
                        hookSlot.add(bestFishingRodSlot);
                        bestFishingRodSlot = slot;
                    } else {
                        hookSlot.add(slot);
                    }
                } else if (item instanceof EnderPearlItem) {
                    if (bestEnderPearlSlot == null || getPearlScore(slot.getStack()) > getPearlScore(bestEnderPearlSlot.getStack())) {
                        hookSlot.add(bestEnderPearlSlot);
                        bestEnderPearlSlot = slot;
                    } else {
                        hookSlot.add(slot);
                    }
                } else if (item.getComponents().contains(DataComponentTypes.FOOD) && !(offHandMode.get() == OffhandMode.Gapple && isGapple(item))) {
                    if (bestFoodSlot == null || getFoodScore(slot.getStack()) > getFoodScore(bestFoodSlot.getStack())) {
                        hookSlot.add(bestFoodSlot);
                        bestFoodSlot = slot;
                    } else {
                        hookSlot.add(slot);
                    }
                } else if (item == Items.WATER_BUCKET) {
                    if (bestBucketSlot == null) {
                        bestBucketSlot = slot;
                    } else {
                        hookSlot.add(slot);
                    }
                }
            }

            bestBlockItem = getBestBlockItem(blockTotals);
            bestBlockSlot = findBestBlockSlot(blockSlots, bestBlockItem);
            if (bestBlockItem != null && getBlockIndex() > blocks.get()) {
                for (Slot blockSlot : blockSlots) {
                    if (blockSlot == null || !blockSlot.hasStack()) continue;
                    if (blockSlot.getStack().getItem() != bestBlockItem) {
                        hookSlot.add(blockSlot);
                    }
                }
            }

            if (currentBestBlockSlot != null && (!currentBestBlockSlot.hasStack() || !(currentBestBlockSlot.getStack().getItem() instanceof BlockItem))) {
                currentBestBlockSlot = null;
            }

            if (timer.passedMS(delay.get())) {
                for (Slot slot : hookSlot) {
                    if (slot != null && !isProtectedItem(slot.getStack())) {
                        if (slot.getStack().getItem() instanceof BlockItem) {
                            if (getBlockIndex() > blocks.get()) {
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

            if (timer.passedMS(delay.get())) {
                for (Slot slot : protectedSlots) {
                    if (slot.id >= 36 && slot.id <= 44) {
                        int hotbarIndex = slot.id - 36;
                        for (int i = 9; i < 36; i++) {
                            Slot backpackSlot = mc.player.playerScreenHandler.slots.get(i);
                            if (!backpackSlot.hasStack()) {
                                click(backpackSlot, hotbarIndex, SlotActionType.SWAP);
                                break;
                            }
                        }
                        timer.reset();
                        return;
                    }
                }
            }

            if (timer.passedMS(delay.get())) {
                for (int i = 0; i < bestArmorSlots.length; i++) {
                    Slot slot = bestArmorSlots[i];
                    if (slot != null) {
                        if (!isArmorEquipped(i, slot)) {
                            if (mc.player.playerScreenHandler != mc.player.currentScreenHandler) {
                                return;
                            }
                            int armorSlotId = getArmorSlotId(i);
                            if (armorSlotId >= 0 && slot.id != armorSlotId) {
                                swapSlots(slot.id, armorSlotId);
                            }
                            timer.reset();
                            return;
                        }
                    }
                }
            }

            if (timer.passedMS(delay.get())) {
                if (bestSwordSlot != null && bestSwordSlot.getStack().getItem() instanceof SwordItem) {
                    int targetSlotId = 36 + slotSword.get() - 1;
                    if (bestSwordSlot.id != targetSlotId) {
                        click(bestSwordSlot, slotSword.get() - 1, SlotActionType.SWAP);
                        timer.reset();
                        return;
                    }
                }

                if (bestBlockItem != null && bestBlockSlot != null && bestBlockSlot.getStack().getItem() instanceof BlockItem) {
                    int targetSlotId = 36 + slotBlock.get() - 1;
                    Slot targetSlot = mc.player.playerScreenHandler.getSlot(targetSlotId);
                    if (targetSlot.hasStack() && targetSlot.getStack().getItem() == bestBlockItem) {
                        Slot mergeSource = findMergeSource(blockSlots, bestBlockItem, targetSlotId);
                        if (mergeSource != null && targetSlot.getStack().getCount() < targetSlot.getStack().getMaxCount()) {
                            mergeStacks(mergeSource, targetSlot);
                            timer.reset();
                            return;
                        }
                    }
                    if (bestBlockSlot.id != targetSlotId && isBlockPlaceable(bestBlockSlot.getStack())) {
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
            }

            if (timer.passedMS(delay.get())) {
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
        return (item == Items.END_CRYSTAL) || (item == Items.TOTEM_OF_UNDYING);
    }

    private void manageOffhand() {
        OffhandMode mode = offHandMode.get();
        if (mode == OffhandMode.None) return;
        if (mc.player.playerScreenHandler != mc.player.currentScreenHandler) return;

        Slot offhandSlot = getOffhandSlot();
        if (offhandSlot == null) return;

        ItemStack offhandStack = offhandSlot.getStack();

        if (mode == OffhandMode.Gapple) {
            if (!isGapple(offhandStack.getItem())) {
                Slot bestGappleSlot = findBestGappleSlot();
                if (bestGappleSlot != null && bestGappleSlot.id != offhandSlot.id) {
                    putItemInSlotOFF(bestGappleSlot, offhandSlot);
                    timer.reset();
                }
            }
        } else if (mode == OffhandMode.Throwable) {
            if (!isThrowableItem(offhandStack)) {
                Slot bestThrowableSlot = findBestThrowableSlot();
                if (bestThrowableSlot != null && bestThrowableSlot.id != offhandSlot.id) {
                    putItemInSlotOFF(bestThrowableSlot, offhandSlot);
                    timer.reset();
                }
            }
        }
    }

    private Slot findBestGappleSlot() {
        Slot best = null;
        for (Slot slot : mc.player.playerScreenHandler.slots) {
            if (!slot.hasStack()) continue;
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
            if (slot.getStack().getCount() > best.getStack().getCount()) {
                best = slot;
            }
        }
        return best;
    }

    private Slot findBestThrowableSlot() {
        Slot best = null;
        for (Slot slot : mc.player.playerScreenHandler.slots) {
            if (!slot.hasStack()) continue;
            if (!isThrowableItem(slot.getStack())) continue;
            if (best == null) {
                best = slot;
                continue;
            }
            int priority = getThrowablePriority(slot.getStack());
            int bestPriority = getThrowablePriority(best.getStack());
            if (priority > bestPriority) {
                best = slot;
                continue;
            }
            if (priority == bestPriority && slot.getStack().getCount() > best.getStack().getCount()) {
                best = slot;
            }
        }
        return best;
    }

    private void putItemInSlotOFF(Slot sourceSlot, Slot offhandSlot) {
        if (sourceSlot == null || offhandSlot == null) return;
        swapSlots(sourceSlot.id, offhandSlot.id);
    }

    private boolean isGapple(Item item) {
        return item == Items.GOLDEN_APPLE || item == Items.ENCHANTED_GOLDEN_APPLE;
    }

    private Slot getOffhandSlot() {
        int offhandId = SlotUtil.indexToId(40);
        if (offhandId < 0) return null;
        if (mc.player.playerScreenHandler.slots.size() <= offhandId) return null;
        return mc.player.playerScreenHandler.getSlot(offhandId);
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

    private Item getBestBlockItem(Map<Item, Integer> blockTotals) {
        if (blockTotals.isEmpty()) return null;
        Item bestItem = null;
        int bestCount = 0;
        for (Map.Entry<Item, Integer> entry : blockTotals.entrySet()) {
            int count = entry.getValue();
            if (count > bestCount) {
                bestCount = count;
                bestItem = entry.getKey();
            }
        }
        return bestItem;
    }

    private Slot findBestBlockSlot(List<Slot> blockSlots, Item item) {
        if (item == null) return null;
        Slot best = null;
        int bestCount = -1;
        for (Slot slot : blockSlots) {
            if (slot == null || !slot.hasStack()) continue;
            ItemStack stack = slot.getStack();
            if (stack.getItem() != item) continue;
            int count = stack.getCount();
            if (count > bestCount) {
                bestCount = count;
                best = slot;
            }
        }
        return best;
    }

    private Slot findMergeSource(List<Slot> blockSlots, Item item, int targetSlotId) {
        if (item == null) return null;
        Slot best = null;
        int bestCount = -1;
        for (Slot slot : blockSlots) {
            if (slot == null || !slot.hasStack() || slot.id == targetSlotId) continue;
            ItemStack stack = slot.getStack();
            if (stack.getItem() != item) continue;
            int count = stack.getCount();
            if (count > bestCount) {
                bestCount = count;
                best = slot;
            }
        }
        return best;
    }

    private void mergeStacks(Slot sourceSlot, Slot targetSlot) {
        if (sourceSlot == null || targetSlot == null) return;
        if (sourceSlot.id == targetSlot.id) return;
        if (!sourceSlot.hasStack() || !targetSlot.hasStack()) return;
        ItemStack sourceStack = sourceSlot.getStack();
        ItemStack targetStack = targetSlot.getStack();
        if (sourceStack.getItem() != targetStack.getItem()) return;
        if (targetStack.getCount() >= targetStack.getMaxCount()) return;
        int syncId = mc.player.currentScreenHandler.syncId;
        mc.interactionManager.clickSlot(syncId, sourceSlot.id, 0, SlotActionType.PICKUP, mc.player);
        mc.interactionManager.clickSlot(syncId, targetSlot.id, 0, SlotActionType.PICKUP, mc.player);
        if (!mc.player.currentScreenHandler.getCursorStack().isEmpty()) {
            mc.interactionManager.clickSlot(syncId, sourceSlot.id, 0, SlotActionType.PICKUP, mc.player);
        }
    }

    private void click(Slot slot, int button, SlotActionType type) {
        mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, slot.id, button, type, mc.player);
        if (swing.get() && type == SlotActionType.THROW) {
            mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
        }
    }

    private void swapSlots(int fromId, int toId) {
        if (fromId == toId) return;
        int syncId = mc.player.currentScreenHandler.syncId;
        mc.interactionManager.clickSlot(syncId, fromId, 0, SlotActionType.PICKUP, mc.player);
        mc.interactionManager.clickSlot(syncId, toId, 0, SlotActionType.PICKUP, mc.player);
        if (!mc.player.currentScreenHandler.getCursorStack().isEmpty()) {
            mc.interactionManager.clickSlot(syncId, fromId, 0, SlotActionType.PICKUP, mc.player);
        }
    }

    private int getArmorSlotId(int index) {
        return switch (index) {
            case 0 -> 5;
            case 1 -> 6;
            case 2 -> 7;
            case 3 -> 8;
            default -> -1;
        };
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

    private double getEquippedArmorScoreByIndex(int index) {
        return switch (index) {
            case 0 -> getArmorScore(mc.player.getEquippedStack(EquipmentSlot.HEAD));
            case 1 -> getArmorScore(mc.player.getEquippedStack(EquipmentSlot.CHEST));
            case 2 -> getArmorScore(mc.player.getEquippedStack(EquipmentSlot.LEGS));
            case 3 -> getArmorScore(mc.player.getEquippedStack(EquipmentSlot.FEET));
            default -> 0.0;
        };
    }

    private boolean isBetterOrEqualArmor(ItemStack equipped, ItemStack candidate) {
        double equippedScore = getArmorScore(equipped);
        double candidateScore = getArmorScore(candidate);
        return equippedScore >= candidateScore;
    }

    private static final Item[] BAD_BLOCK_ITEMS = {
            Items.LADDER,
            Items.CHEST,
            Items.TORCH,
            Items.TORCH,
            Items.REDSTONE_TORCH,
            Items.FLOWER_POT,
            Items.GLASS_PANE,
            Items.IRON_BARS,
            Items.VINE,
            Items.OAK_FENCE,
            Items.SPRUCE_FENCE,
            Items.BIRCH_FENCE,
            Items.JUNGLE_FENCE,
            Items.ACACIA_FENCE,
            Items.DARK_OAK_FENCE,
            Items.WARPED_FENCE,
            Items.CRIMSON_FENCE,
            Items.NETHER_BRICK_FENCE,
            Items.COBBLESTONE_WALL,
            Items.MOSSY_COBBLESTONE_WALL,
            Items.BRICK_WALL,
            Items.PRISMARINE_WALL,
            Items.RED_SANDSTONE_WALL,
            Items.SANDSTONE_WALL,
            Items.STONE_BRICK_WALL,
            Items.NETHER_BRICK_WALL,
            Items.RED_NETHER_BRICK_WALL,
            Items.ANDESITE_WALL,
            Items.CACTUS,
            Items.DIORITE_WALL,
            Items.GRANITE_WALL,
            Items.END_ROD,
            Items.LILY_PAD,
            Items.CAULDRON,
            Items.LECTERN,
            Items.STONE_SLAB,
            Items.COBBLESTONE_SLAB,
            Items.STONE_BRICK_SLAB,
            Items.SANDSTONE_SLAB,
            Items.RED_SANDSTONE_SLAB,
            Items.BRICK_SLAB,
            Items.QUARTZ_SLAB,
            Items.OAK_SLAB,
            Items.SPRUCE_SLAB,
            Items.BIRCH_SLAB,
            Items.JUNGLE_SLAB,
            Items.ACACIA_SLAB,
            Items.DARK_OAK_SLAB,
            Items.PURPUR_SLAB,
            Items.NETHER_BRICK_SLAB,
            Items.RED_NETHER_BRICK_SLAB,
            Items.PRISMARINE_SLAB,
            Items.PRISMARINE_BRICK_SLAB,
            Items.DARK_PRISMARINE_SLAB,
            Items.CAMPFIRE,
            Items.SOUL_CAMPFIRE,
            Items.WHITE_BED,
            Items.ORANGE_BED,
            Items.MAGENTA_BED,
            Items.LIGHT_BLUE_BED,
            Items.YELLOW_BED,
            Items.LIME_BED,
            Items.PINK_BED,
            Items.GRAY_BED,
            Items.LIGHT_GRAY_BED,
            Items.CYAN_BED,
            Items.PURPLE_BED,
            Items.BLUE_BED,
            Items.BROWN_BED,
            Items.GREEN_BED,
            Items.RED_BED,
            Items.BLACK_BED,
            Items.SWEET_BERRIES,
            Items.CAKE,
            Items.CARVED_PUMPKIN,
            Items.JACK_O_LANTERN,
            Items.BELL,
            Items.COMPOSTER,
            Items.SCAFFOLDING,
            Items.BARREL,
            Items.BEE_NEST,
            Items.BEEHIVE,
            Items.LOOM,
            Items.SMOKER,
            Items.BLAST_FURNACE,
            Items.CARTOGRAPHY_TABLE,
            Items.FLETCHING_TABLE,
            Items.GRINDSTONE,
            Items.SMITHING_TABLE,
            Items.STONECUTTER,
            Items.COBWEB,
            Items.SPAWNER,
            Items.CHEST_MINECART,
            Items.FURNACE_MINECART,
            Items.HOPPER_MINECART,
            Items.TNT_MINECART,
            Items.BEEHIVE,
            Items.BEE_NEST,
            Items.CARROT,
            Items.POTATO,
            Items.WHEAT,
            Items.BEETROOT,
            Items.WHEAT_SEEDS
    };

    public int getBlockIndex() {
        int blockCount = 0;
        for (int slotIndex = 9; slotIndex < 45; slotIndex++) {
            ItemStack stack = mc.player.playerScreenHandler.getSlot(slotIndex).getStack();
            if (stack.getItem() instanceof BlockItem) {
                blockCount += stack.getCount();
            }
        }
        return blockCount;
    }

    public boolean isInventoryFull() {
        PlayerInventory inventory = mc.player.getInventory();
        for (int i = 0; i < inventory.main.size(); i++) {
            if (inventory.main.get(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private boolean isBlockPlaceable(ItemStack stack) {
        if (!(stack.getItem() instanceof BlockItem)) return false;

        BlockItem blockItem = (BlockItem) stack.getItem();
        Block block = blockItem.getBlock();

        if (!block.getDefaultState().isOpaque() ||
                !block.getDefaultState().isSolidBlock(mc.world, mc.player.getBlockPos())) {
            return false;
        }

        if (block instanceof CropBlock) return false;

        for (Item badItem : BAD_BLOCK_ITEMS) {
            if (stack.getItem() == badItem) {
                return false;
            }
        }

        return true;
    }

    private double getSwordScore(ItemStack stack) {
        double baseDamage = getBaseDamage(stack);
        int sharpness = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.SHARPNESS);
        int smite = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.SMITE);
        int bane = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.BANE_OF_ARTHROPODS);
        int fireAspect = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.FIRE_ASPECT);
        int knockback = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.KNOCKBACK);
        int sweeping = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.SWEEPING_EDGE);
        int unbreaking = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.UNBREAKING);

        double enchantDamage = Math.max(sharpness, Math.max(smite, bane));
        return baseDamage + enchantDamage + (fireAspect * 0.5) + (knockback * 0.2) + (sweeping * 0.3) + (unbreaking * 0.05);
    }

    private double getAxeScore(ItemStack stack) {
        double baseDamage = getBaseDamage(stack);
        int sharpness = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.SHARPNESS);
        int efficiency = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.EFFICIENCY);
        int unbreaking = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.UNBREAKING);
        return baseDamage + (sharpness * 0.8) + (efficiency * 0.3) + (unbreaking * 0.05);
    }

    private double getPickaxeScore(ItemStack stack) {
        double baseDamage = getBaseDamage(stack);
        int efficiency = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.EFFICIENCY);
        int fortune = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.FORTUNE);
        int silkTouch = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.SILK_TOUCH);
        int unbreaking = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.UNBREAKING);
        return baseDamage + (efficiency * 0.6) + (fortune * 0.3) + (silkTouch > 0 ? 0.5 : 0.0) + (unbreaking * 0.05);
    }

    private double getBowScore(ItemStack stack) {
        int power = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.POWER);
        int punch = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.PUNCH);
        int flame = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.FLAME);
        int infinity = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.INFINITY);
        int unbreaking = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.UNBREAKING);
        return (power * 2.0) + (punch * 0.8) + (flame * 1.0) + (infinity > 0 ? 1.0 : 0.0) + (unbreaking * 0.05);
    }

    private double getFishingRodScore(ItemStack stack) {
        int luck = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.LUCK_OF_THE_SEA);
        int lure = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.LURE);
        int unbreaking = EnchantmentUtil.getEnchantmentLevel(stack, Enchantments.UNBREAKING);
        return (luck * 1.5) + (lure * 1.0) + (unbreaking * 0.05);
    }

    private int getPearlScore(ItemStack stack) {
        return stack.getCount();
    }

    private double getArmorScore(ItemStack armor) {
        if (armor.isEmpty()) return 0.0;
        double armorValue = 0.0;
        double toughnessValue = 0.0;

        AttributeModifiersComponent modifiers = armor.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
        if (modifiers != null) {
            for (AttributeModifiersComponent.Entry entry : modifiers.modifiers()) {
                if (entry.attribute().equals(EntityAttributes.ARMOR)) {
                    armorValue += entry.modifier().value();
                } else if (entry.attribute().equals(EntityAttributes.ARMOR_TOUGHNESS)) {
                    toughnessValue += entry.modifier().value();
                }
            }
        }

        int protection = EnchantmentUtil.getEnchantmentLevel(armor, Enchantments.PROTECTION);
        return armorValue + toughnessValue + protection;
    }

    private double getFoodScore(ItemStack stack) {
        FoodComponent food = stack.get(DataComponentTypes.FOOD);
        if (food == null) return 0.0;
        double score = food.nutrition() + (food.saturation() * 2.0);
        if (stack.getItem() == Items.ENCHANTED_GOLDEN_APPLE) {
            score += 1000.0;
        } else if (stack.getItem() == Items.GOLDEN_APPLE) {
            score += 200.0;
        }
        return score;
    }

    private double getBaseDamage(ItemStack weapon) {
        double baseDamage = 0.0;

        AttributeModifiersComponent modifiers = weapon.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
        if (modifiers != null) {
            for (AttributeModifiersComponent.Entry entry : modifiers.modifiers()) {
                if (entry.attribute().equals(EntityAttributes.ATTACK_DAMAGE)) {
                    baseDamage += entry.modifier().value();
                }
            }
        }

        return baseDamage;
    }
}
