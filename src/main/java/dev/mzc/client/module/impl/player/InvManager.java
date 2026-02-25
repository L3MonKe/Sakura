package dev.mzc.client.module.impl.player;

import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.utils.player.InvUtil;
import dev.mzc.client.utils.player.MovementUtil;
import dev.mzc.client.utils.time.TimerUtil;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class InvManager extends Module {
    public InvManager() {
        super("InvManager", "背包管理", Category.Player);
        this.setType(ModuleType.Safe);
    }

    public enum Mode {
        InvOpen("打开背包"),
        NoMove("静止"),
        Always("总是");

        private final String cnName;

        Mode(String cnName) {
            this.cnName = cnName;
        }
    }

    public enum OffhandMode {
        Gapple("金苹果"),
        Throwable("投掷物"),
        None("无");

        private final String cnName;

        OffhandMode(String cnName) {
            this.cnName = cnName;
        }
    }

    public enum InteractionMode {
        Instant("瞬间"),
        Simulate("模拟玩家");

        private final String cnName;

        InteractionMode(String cnName) {
            this.cnName = cnName;
        }
    }

    public final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.InvOpen);
    public final EnumValue<OffhandMode> offHandMode = new EnumValue<>("Offhand Mode", "副手模式", OffhandMode.None);
    public final EnumValue<InteractionMode> interactionMode = new EnumValue<>("Interaction Mode", "交互模式", InteractionMode.Instant);
    private final BoolValue swing = new BoolValue("Swing", "挥手动画", true);
    public final BoolValue autoArmor = new BoolValue("Auto Armor", "自动穿甲", true);
    private final BoolValue keepTNT = new BoolValue("Keep TNT", "保留TNT", true);
    public final NumberValue<Integer> delay = new NumberValue<>("Delay", "延迟", 1, 1, 250, 1);
    public final NumberValue<Integer> blocks = new NumberValue<>("Blocks", "方块阈值", 128, 16, 512, 1);
    public final NumberValue<Integer> arrows = new NumberValue<>("Arrows", "箭矢阈值", 64, 0, 256, 1);

    public final NumberValue<Integer> slotSword = new NumberValue<>("Sword Slot", "剑槽位", 1, 0, 9, 1);
    public final NumberValue<Integer> slotBlock = new NumberValue<>("Block Slot", "方块槽位", 2, 0, 9, 1);
    public final NumberValue<Integer> slotFood = new NumberValue<>("Food Slot", "食物槽位", 3, 0, 9, 1);
    public final NumberValue<Integer> slotPearl = new NumberValue<>("Pearl Slot", "珍珠槽位", 4, 0, 9, 1);
    public final NumberValue<Integer> slotAxe = new NumberValue<>("Axe Slot", "斧子槽位", 5, 0, 9, 1);
    public final NumberValue<Integer> slotPickaxe = new NumberValue<>("Pickaxe Slot", "镐子槽位", 6, 0, 9, 1);
    public final NumberValue<Integer> slotBucket = new NumberValue<>("Bucket Slot", "水桶槽位", 7, 0, 9, 1);
    public final NumberValue<Integer> slotBow = new NumberValue<>("Bow Slot", "弓槽位", 8, 0, 9, 1);
    public final NumberValue<Integer> slotFishingRod = new NumberValue<>("FishingRod Slot", "鱼竿槽位", 9, 0, 9, 1);
    public final NumberValue<Integer> slotPot = new NumberValue<>("Pot Slot", "药水槽位", 0, 0, 9, 1);

    private final TimerUtil timer = new TimerUtil();

    private long nextDelayMs = 0L;
    private final List<Slot> gappleStackSlots = new ArrayList<>();
    private final List<Slot> throwableStackSlots = new ArrayList<>();
    private final List<Slot> protectedSlots = new ArrayList<>();

    private Slot currentBestBlockSlot = null;
    private Slot backupBlockSlot = null; // Backup block slot (different type from best)
    
    // For HUD
    public int pendingActions = 0;
    public long currentDelay = 0;
    public TimerUtil getTimer() { return timer; }

    public boolean shouldSort() {
        return (mode.is(Mode.InvOpen) && mc.currentScreen instanceof InventoryScreen)
                || (mode.is(Mode.NoMove) && !MovementUtil.isMoving())
                || mode.is(Mode.Always);
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        // Prevent running in other containers (like Furnace, Chest) because slot IDs mismatch
        // between playerScreenHandler and the current container's handler, causing crashes.
        if (mc.currentScreen instanceof HandledScreen && !(mc.currentScreen instanceof InventoryScreen)) {
            return;
        }

        boolean shouldRun =
                (mode.is(Mode.InvOpen) && mc.currentScreen instanceof InventoryScreen)
                        || (mode.is(Mode.NoMove) && !MovementUtil.isMoving())
                        || mode.is(Mode.Always);
        
        if (!shouldRun) {
            timer.reset();
            nextDelayMs = 0L;
            currentBestBlockSlot = null;
            backupBlockSlot = null;
            pendingActions = 0;
            return;
        }
        
        // Reset pending actions before calculation
        pendingActions = 0;

        List<Slot> unnecessarySlots = new ArrayList<>();

        gappleStackSlots.clear();
        throwableStackSlots.clear();
        protectedSlots.clear();

        Slot bestSwordSlot = null;
        Slot bestBlockSlot = null;
        
        Slot localBackupBlockSlot = null; 
        Slot bestFoodSlot = null;
        Slot backupFoodSlot = null; // Backup food slot (non-gapple if best is gapple)
        Slot[] bestArmorSlots = new Slot[4]; // 4 个槽位，分别对应头盔、胸甲、裤子和靴子


        Slot bestBowSlot = null;
        Slot bestEnderPearlSlot = null;
        Slot bestFishingRodSlot = null;
        Slot bestAxeSlot = null;
        Slot bestPickaxeSlot = null;
        Slot bestBucketSlot = null;
        Slot bestLavaBucketSlot = null;
        Slot bestPotSlot = null;

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
            } else if (isArmor(stack) || stack.isOf(Items.ELYTRA)) {
                EquipmentSlot equipmentSlot = getArmorSlot(stack);
                int targetSlot = -1;
                if (equipmentSlot != null) {
                    targetSlot = switch (equipmentSlot) {
                        case HEAD -> 0;
                        case CHEST -> 1;
                        case LEGS -> 2;
                        case FEET -> 3;
                        default -> -1;
                    };
                }

                if (targetSlot < 0 || targetSlot >= bestArmorSlots.length) {
                    if (isArmor(stack)) unnecessarySlots.add(slot);
                    continue;
                }

                Slot bestArmorSlot = bestArmorSlots[targetSlot];

                if (bestArmorSlot == null || getProtection(stack) > getProtection(bestArmorSlot.getStack())) {
                    unnecessarySlots.add(bestArmorSlot);
                    bestArmorSlots[targetSlot] = slot;
                } else {
                    unnecessarySlots.add(slot);
                }
            } else if (item instanceof BlockItem && InvUtil.isBlockPlaceable(item.getDefaultStack())) {
                if (bestBlockSlot == null) {
                    bestBlockSlot = slot;
                } else {
                    boolean newIsBetter = isBestBlock(bestBlockSlot, slot);
                    Slot winner = newIsBetter ? slot : bestBlockSlot;
                    Slot loser = newIsBetter ? bestBlockSlot : slot;

                    if (newIsBetter) {
                        bestBlockSlot = winner;
                        if (loser.getStack().getItem() != winner.getStack().getItem()) {
                            if (localBackupBlockSlot == null) {
                                localBackupBlockSlot = loser;
                            } else {
                                if (isBestBlock(localBackupBlockSlot, loser)) {
                                    unnecessarySlots.add(localBackupBlockSlot);
                                    localBackupBlockSlot = loser;
                                } else {
                                    unnecessarySlots.add(loser);
                                }
                            }
                        } else {
                            unnecessarySlots.add(loser);
                        }

                        if (localBackupBlockSlot != null && localBackupBlockSlot.getStack().getItem() == bestBlockSlot.getStack().getItem()) {
                            unnecessarySlots.add(localBackupBlockSlot);
                            localBackupBlockSlot = null;
                        }
                    } else {
                        if (loser.getStack().getItem() != bestBlockSlot.getStack().getItem()) {
                            if (localBackupBlockSlot == null) {
                                localBackupBlockSlot = loser;
                            } else {
                                if (isBestBlock(localBackupBlockSlot, loser)) {
                                    unnecessarySlots.add(localBackupBlockSlot);
                                    localBackupBlockSlot = loser;
                                } else {
                                    unnecessarySlots.add(loser);
                                }
                            }
                        } else {
                            unnecessarySlots.add(loser);
                        }
                    }
                }
            } else if (item == Items.POTION || item == Items.SPLASH_POTION) {
                int score = getPotionScore(stack);
                if (score > 0) {
                    if (bestPotSlot == null || score > getPotionScore(bestPotSlot.getStack())) {
                        bestPotSlot = slot;
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
                if (bestFoodSlot == null) {
                    bestFoodSlot = slot;
                } else {
                    if (isBestFood(bestFoodSlot, slot)) {
                        if (isGapple(slot.getStack().getItem()) && !isGapple(bestFoodSlot.getStack().getItem())) {
                            if (backupFoodSlot == null) {
                                backupFoodSlot = bestFoodSlot;
                            } else if (isBestFood(backupFoodSlot, bestFoodSlot)) {
                                unnecessarySlots.add(backupFoodSlot);
                                backupFoodSlot = bestFoodSlot;
                            } else {
                                unnecessarySlots.add(bestFoodSlot);
                            }
                            bestFoodSlot = slot;
                        } else {
                            if (backupFoodSlot == null && !isGapple(bestFoodSlot.getStack().getItem())) {
                                unnecessarySlots.add(bestFoodSlot);
                            } else if (backupFoodSlot != null && !isGapple(bestFoodSlot.getStack().getItem())) {
                                if (isBestFood(backupFoodSlot, bestFoodSlot)) {
                                     unnecessarySlots.add(backupFoodSlot);
                                     backupFoodSlot = bestFoodSlot;
                                } else {
                                     unnecessarySlots.add(bestFoodSlot);
                                }
                            } else {
                                unnecessarySlots.add(bestFoodSlot);
                            }
                            bestFoodSlot = slot;
                        }
                    } else {
                        // New slot is NOT better than current best.
                        // But maybe it can be a backup?
                        // Only if Best is Gapple, and New is NOT Gapple.
                        if (isGapple(bestFoodSlot.getStack().getItem()) && !isGapple(slot.getStack().getItem())) {
                             if (backupFoodSlot == null) {
                                 backupFoodSlot = slot;
                             } else {
                                 if (isBestFood(backupFoodSlot, slot)) {
                                     unnecessarySlots.add(backupFoodSlot);
                                     backupFoodSlot = slot;
                                 } else {
                                     unnecessarySlots.add(slot);
                                 }
                             }
                        } else {
                            unnecessarySlots.add(slot);
                        }
                    }
                }
            } else if (item == Items.WATER_BUCKET) {
                if (bestBucketSlot == null) {
                    bestBucketSlot = slot;
                } else {
                    unnecessarySlots.add(slot);
                }
            } else if (item == Items.LAVA_BUCKET) {
                if (bestLavaBucketSlot == null) {
                    bestLavaBucketSlot = slot;
                } else {
                    unnecessarySlots.add(slot);
                }
            } else if (item == Items.ARROW) {
                if (InvUtil.getItemCount(Items.ARROW) > arrows.get()) {
                    unnecessarySlots.add(slot);
                }
            }
        }

        if (currentBestBlockSlot != null && (currentBestBlockSlot.id < 9 || currentBestBlockSlot.id > 44
                || !currentBestBlockSlot.hasStack()
                || !(currentBestBlockSlot.getStack().getItem() instanceof BlockItem))) {
            currentBestBlockSlot = null;
        }
        
        // Update fields for persistence / other methods
        this.backupBlockSlot = localBackupBlockSlot;
        // We don't necessarily need to persist bestPotSlot unless shouldDrop uses it.

        long emptyBackpackSlots = mc.player.playerScreenHandler.slots.stream()
                .filter(s -> s.id >= 9 && s.id < 36 && !s.hasStack())
                .count();

        long protectedInHotbar = protectedSlots.stream()
                .filter(this::shouldMoveProtected)
                .count();

        pendingActions = (int) unnecessarySlots.stream().filter(this::shouldDrop).count() +
                (int) Math.min(protectedInHotbar, emptyBackpackSlots) +
                getPendingSwapCount(bestSwordSlot, bestBlockSlot, bestFoodSlot, backupFoodSlot, bestEnderPearlSlot, bestFishingRodSlot, bestBowSlot, bestAxeSlot, bestPickaxeSlot, bestBucketSlot, bestPotSlot) +
                getPendingOffhandCount() +
                getPendingArmorCount(bestArmorSlots);

        if (!readyToAct()) return;

        if (autoArmor.get() && tryEquipArmor(bestArmorSlots)) return;
        if (tryDropUnnecessary(unnecessarySlots)) return;
        if (tryMoveProtected()) return;
        if (tryCraftPlanks()) return;
        if (trySwapHotbar(bestSwordSlot, bestBlockSlot, bestFoodSlot, backupFoodSlot, bestEnderPearlSlot, bestFishingRodSlot, bestBowSlot, bestAxeSlot, bestPickaxeSlot, bestBucketSlot, bestPotSlot))
            return;
        if (manageOffhand()) return;
    }

    private boolean readyToAct() {
        if (nextDelayMs <= 0L) {
            nextDelayMs = randomDelayMs();
            currentDelay = nextDelayMs;
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

            // 不再阻止丢弃方块，阈值不参与决定是否移动/丢弃

            click(slot, 1, SlotActionType.THROW);
            markActed();
            return true;
        }
        return false;
    }

    private boolean tryEquipArmor(Slot[] bestArmorSlots) {
        for (int i = 0; i < bestArmorSlots.length; i++) {
            Slot slot = bestArmorSlots[i];
            if (slot != null && !isArmorEquipped(i, slot)) {
                // Determine target slot ID
                // Armor slots in InventoryScreen are 5, 6, 7, 8 (Head, Chest, Legs, Feet)
                // i=0 -> Head -> 5
                // i=1 -> Chest -> 6
                // i=2 -> Legs -> 7
                // i=3 -> Feet -> 8
                int targetSlotId = 5 + i;
                
                // If the target slot is not empty, we might need to swap or move current item out first?
                // But typically shift-clicking or simple click handles it.
                // AutoArmor usually does:
                // If target slot is empty -> shift click from inventory
                // If target slot has worse armor -> drop worse armor or move it to inventory
                
                // Let's use simple logic: Pick up best armor and click on target slot
                
                click(slot, 0, SlotActionType.PICKUP);
                click(mc.player.playerScreenHandler.getSlot(targetSlotId), 0, SlotActionType.PICKUP);
                
                // If we are holding something now (the old armor), we should put it in the slot where the new armor came from
                if (!mc.player.currentScreenHandler.getCursorStack().isEmpty()) {
                     click(slot, 0, SlotActionType.PICKUP);
                }
                
                markActed();
                return true; // Perform one action per tick
            }
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

    private int getBlockPriority(ItemStack stack) {
        Item item = stack.getItem();
        if (item == Items.COBBLESTONE || item == Items.STONE || item == Items.OAK_PLANKS ||
            item == Items.SPRUCE_PLANKS || item == Items.BIRCH_PLANKS || item == Items.JUNGLE_PLANKS ||
            item == Items.ACACIA_PLANKS || item == Items.DARK_OAK_PLANKS || item == Items.MANGROVE_PLANKS ||
            item == Items.CHERRY_PLANKS || item == Items.BAMBOO_PLANKS || item == Items.CRIMSON_PLANKS ||
            item == Items.WARPED_PLANKS || item == Items.NETHERRACK || item == Items.OBSIDIAN ||
            item == Items.DIRT || item == Items.GRASS_BLOCK || item == Items.OAK_LOG ||
            item == Items.SPRUCE_LOG || item == Items.BIRCH_LOG || item == Items.JUNGLE_LOG ||
            item == Items.ACACIA_LOG || item == Items.DARK_OAK_LOG || item == Items.MANGROVE_LOG ||
            item == Items.CHERRY_LOG || item == Items.GLASS || item == Items.TINTED_GLASS ||
            item == Items.WHITE_STAINED_GLASS || item == Items.ORANGE_STAINED_GLASS ||
            item == Items.MAGENTA_STAINED_GLASS || item == Items.LIGHT_BLUE_STAINED_GLASS ||
            item == Items.YELLOW_STAINED_GLASS || item == Items.LIME_STAINED_GLASS ||
            item == Items.PINK_STAINED_GLASS || item == Items.GRAY_STAINED_GLASS ||
            item == Items.LIGHT_GRAY_STAINED_GLASS || item == Items.CYAN_STAINED_GLASS ||
            item == Items.PURPLE_STAINED_GLASS || item == Items.BLUE_STAINED_GLASS ||
            item == Items.BROWN_STAINED_GLASS || item == Items.GREEN_STAINED_GLASS ||
            item == Items.RED_STAINED_GLASS || item == Items.BLACK_STAINED_GLASS) {
            return 2;
        }
        if (item == Items.SAND || item == Items.RED_SAND || item == Items.GRAVEL) {
            return -1;
        }
        return 0;
    }

    private boolean isBestBlock(Slot currentBest, Slot candidate) {
        if (currentBest == null) return true;
        
        int currentPriority = getBlockPriority(currentBest.getStack());
        int candidatePriority = getBlockPriority(candidate.getStack());
        
        if (candidatePriority > currentPriority) return true;
        if (candidatePriority < currentPriority) return false;
        
        int currentCount = currentBest.getStack().getCount();
        int candidateCount = candidate.getStack().getCount();
        
        return candidateCount > currentCount;
    }

    private int getFoodPriority(ItemStack stack) {
        Item item = stack.getItem();
        if (isGapple(item)) return 5;
        if (item == Items.GOLDEN_CARROT) return 4;
        if (item == Items.COOKED_BEEF || item == Items.COOKED_PORKCHOP || item == Items.COOKED_MUTTON || 
            item == Items.COOKED_CHICKEN || item == Items.COOKED_RABBIT || item == Items.COOKED_SALMON || 
            item == Items.COOKED_COD) return 3;
        if (item == Items.BREAD) return 2;
        if (item == Items.APPLE) return 1;
        return 0;
    }

    private boolean isBestFood(Slot currentBest, Slot candidate) {
        if (currentBest == null) return true;
        
        int currentPriority = getFoodPriority(currentBest.getStack());
        int candidatePriority = getFoodPriority(candidate.getStack());
        
        if (candidatePriority > currentPriority) return true;
        if (candidatePriority < currentPriority) return false;
        
        int currentCount = currentBest.getStack().getCount();
        int candidateCount = candidate.getStack().getCount();
        
        return candidateCount > currentCount;
    }

    private boolean tryCraftPlanks() {
        // Only run if inventory open or mode allows
        if (!(mc.currentScreen instanceof InventoryScreen)) return false;

        for (int i = 9; i < 45; i++) {
            Slot slot = mc.player.playerScreenHandler.getSlot(i);
            if (!slot.hasStack()) continue;
            
            ItemStack stack = slot.getStack();
            if (isLog(stack.getItem())) {
                // Check if 2x2 crafting grid is empty (slots 1,2,3,4)
                boolean gridEmpty = true;
                for (int j = 1; j <= 4; j++) {
                    if (mc.player.playerScreenHandler.getSlot(j).hasStack()) {
                        gridEmpty = false;
                        break;
                    }
                }
                
                if (gridEmpty) {
                    // 1. Pick up the whole log stack
                    click(slot, 0, SlotActionType.PICKUP);
                    
                    // 2. Place the whole stack in crafting slot 1 (index 1)
                    // Use left click (button 0) to place all
                    click(mc.player.playerScreenHandler.getSlot(1), 0, SlotActionType.PICKUP); 
                    
                    // 3. Shift-click the output (slot 0)
                    // Slot 0 is the output slot of the crafting grid in InventoryScreen
                    click(mc.player.playerScreenHandler.getSlot(0), 0, SlotActionType.QUICK_MOVE);
                    
                    // 4. If there are remaining logs in crafting slot 1 (e.g. inventory full), pick them up
                    if (mc.player.playerScreenHandler.getSlot(1).hasStack()) {
                         click(mc.player.playerScreenHandler.getSlot(1), 0, SlotActionType.PICKUP);
                         
                         // 5. Put them back to original slot (or any empty slot)
                         // Since we picked up from 'slot' in step 1, 'slot' should be empty now unless swapped.
                         click(slot, 0, SlotActionType.PICKUP);
                    }

                    markActed();
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean isLog(Item item) {
        return item.getDefaultStack().isIn(ItemTags.LOGS);
    }

    private boolean trySwapHotbar(
            Slot bestSwordSlot,
            Slot bestBlockSlot,
            Slot bestFoodSlot,
            Slot backupFoodSlot,
            Slot bestEnderPearlSlot,
            Slot bestFishingRodSlot,
            Slot bestBowSlot,
            Slot bestAxeSlot,
            Slot bestPickaxeSlot,
            Slot bestBucketSlot,
            Slot bestPotSlot
    ) {
        int swordIndex = slotSword.get() - 1;
        if (swordIndex >= 0 && swordIndex < 9) {
            if (bestSwordSlot != null && bestSwordSlot.getStack().getItem() instanceof SwordItem) {
                if (swap(bestSwordSlot, swordIndex)) return true;
            } else if (bestAxeSlot != null && bestAxeSlot.getStack().getItem() instanceof AxeItem) {
                // No sword found, put axe in sword slot
                if (swap(bestAxeSlot, swordIndex)) return true;
            }
        }

        int blockIndex = slotBlock.get() - 1;
        if (bestBlockSlot != null && blockIndex >= 0 && blockIndex < 9 && bestBlockSlot.getStack().getItem() instanceof BlockItem) {
            if (bestBlockSlot.id != 36 + blockIndex && InvUtil.isBlockPlaceable(bestBlockSlot.getStack())) {
                if (swap(bestBlockSlot, blockIndex)) return true;
            }
        }

        int foodIndex = slotFood.get() - 1;
        if (bestFoodSlot != null && foodIndex >= 0 && foodIndex < 9 && bestFoodSlot.getStack().getItem().getComponents().contains(DataComponentTypes.FOOD)) {
            // Priority: Gapple > Other
            // If bestFood is Gapple, put it in slot.
            if (swap(bestFoodSlot, foodIndex)) return true;
        }

        // Backup food logic:
        // If we have a backup food (meaning best is Gapple, and backup is Meat/Bread), 
        // we should keep it in inventory (not hotbar), BUT we must ensure it's NOT thrown away.
        // The logic already removed it from unnecessarySlots, so it won't be dropped.
        // Do we need to move it to a specific place?
        // User said: "Gapple in hotbar, Beef in inventory".
        // It's already in inventory (if not hotbar).
        // If it happens to be in hotbar (e.g. slot 2), but we want it in inventory (slots 9-35),
        // we might want to move it out of hotbar if hotbar is full of other things?
        // But InvManager usually only manages specific hotbar slots.
        // If backup food is in a hotbar slot that is NOT assigned to anything else, it's fine.
        // If it's in a slot assigned to Sword, it will be swapped out.
        // So we don't need explicit move logic unless we want to force it into backpack.
        // Let's leave it as is, just ensuring it's not dropped (which is done by not adding to unnecessarySlots).

        int pearlIndex = slotPearl.get() - 1;
        if (bestEnderPearlSlot != null && pearlIndex >= 0 && pearlIndex < 9 && bestEnderPearlSlot.getStack().getItem() instanceof EnderPearlItem) {
            if (swap(bestEnderPearlSlot, pearlIndex)) return true;
        }

        int rodIndex = slotFishingRod.get() - 1;
        if (bestFishingRodSlot != null && rodIndex >= 0 && rodIndex < 9 && bestFishingRodSlot.getStack().getItem() instanceof FishingRodItem) {
            if (swap(bestFishingRodSlot, rodIndex)) return true;
        }

        int bowIndex = slotBow.get() - 1;
        if (bestBowSlot != null && bowIndex >= 0 && bowIndex < 9 && bestBowSlot.getStack().getItem() instanceof BowItem) {
            if (swap(bestBowSlot, bowIndex)) return true;
        }

        int axeIndex = slotAxe.get() - 1;
        if (bestAxeSlot != null && axeIndex >= 0 && axeIndex < 9 && bestAxeSlot.getStack().getItem() instanceof AxeItem) {
            // Only move to axe slot if we didn't already use it for sword slot substitution
            boolean usedForSword = (bestSwordSlot == null && bestAxeSlot != null);
            if (!usedForSword) {
                 if (swap(bestAxeSlot, axeIndex)) return true;
            }
        }

        int pickaxeIndex = slotPickaxe.get() - 1;
        if (bestPickaxeSlot != null && pickaxeIndex >= 0 && pickaxeIndex < 9 && bestPickaxeSlot.getStack().getItem() instanceof PickaxeItem) {
            if (swap(bestPickaxeSlot, pickaxeIndex)) return true;
        }

        int bucketIndex = slotBucket.get() - 1;
        if (bestBucketSlot != null && bucketIndex >= 0 && bucketIndex < 9 && bestBucketSlot.getStack().getItem() == Items.WATER_BUCKET) {
            if (swap(bestBucketSlot, bucketIndex)) return true;
        }

        int potIndex = slotPot.get() - 1;
        if (bestPotSlot != null && potIndex >= 0 && potIndex < 9) {
            if (swap(bestPotSlot, potIndex)) return true;
        }

        return false;
    }

    private boolean swap(Slot sourceSlot, int hotbarIndex) {
        int targetSlotId = 36 + hotbarIndex;
        if (sourceSlot.id == targetSlotId) return false;

        if (interactionMode.is(InteractionMode.Simulate)) {
            click(sourceSlot, 0, SlotActionType.PICKUP);
            click(mc.player.playerScreenHandler.getSlot(targetSlotId), 0, SlotActionType.PICKUP);
            if (!mc.player.currentScreenHandler.getCursorStack().isEmpty()) {
                click(sourceSlot, 0, SlotActionType.PICKUP);
            }
        } else {
            click(sourceSlot, hotbarIndex, SlotActionType.SWAP);
        }
        markActed();
        return true;
    }

    private boolean isProtectedItem(ItemStack stack) {
        Item item = stack.getItem();
        return (item == Items.END_CRYSTAL) ||
                (item == Items.TOTEM_OF_UNDYING);
    }

    private boolean shouldDrop(Slot slot) {
        if (slot == null || !slot.hasStack()) return false;
        if (slot.id < 9 || slot.id > 44) return false;
        if (isProtectedItem(slot.getStack())) return false;

        if (slot.getStack().getItem() instanceof BlockItem) {
            // Check if it's sand/gravel and we have enough blocks
            int priority = getBlockPriority(slot.getStack());
            if (priority == -1) return false; // Never drop sand/gravel automatically, just keep them as backup
            
            return InvUtil.getBlockIndex() > blocks.get();
        }

        if (slot.getStack().getItem() == Items.ARROW) {
            return InvUtil.getItemCount(Items.ARROW) > arrows.get();
        }

        return true;
    }

    private boolean shouldMoveProtected(Slot slot) {
        return slot.id >= 36 && slot.id <= 44;
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

    private int getPendingSwapCount(
            Slot bestSwordSlot,
            Slot bestBlockSlot,
            Slot bestFoodSlot,
            Slot backupFoodSlot,
            Slot bestEnderPearlSlot,
            Slot bestFishingRodSlot,
            Slot bestBowSlot,
            Slot bestAxeSlot,
            Slot bestPickaxeSlot,
            Slot bestBucketSlot,
            Slot bestPotSlot
    ) {
        int count = 0;
        int swordIndex = slotSword.get() - 1;
        if (bestSwordSlot != null && swordIndex >= 0 && swordIndex < 9 && bestSwordSlot.getStack().getItem() instanceof SwordItem) {
            int targetSlotId = 36 + swordIndex;
            if (bestSwordSlot.id != targetSlotId) count++;
        }

        int blockIndex = slotBlock.get() - 1;
        if (bestBlockSlot != null && blockIndex >= 0 && blockIndex < 9 && bestBlockSlot.getStack().getItem() instanceof BlockItem) {
            int targetSlotId = 36 + blockIndex;
            if (bestBlockSlot.id != targetSlotId && InvUtil.isBlockPlaceable(bestBlockSlot.getStack())) count++;
        }

        int foodIndex = slotFood.get() - 1;
        if (bestFoodSlot != null && foodIndex >= 0 && foodIndex < 9 && bestFoodSlot.getStack().getItem().getComponents().contains(DataComponentTypes.FOOD)) {
            int targetSlotId = 36 + foodIndex;
            if (bestFoodSlot.id != targetSlotId) count++;
        }
        
        // backupFoodSlot is kept in inventory, so no swap action is counted for it typically,
        // unless we enforced it to be in a specific slot (which we don't currently).

        int pearlIndex = slotPearl.get() - 1;
        if (bestEnderPearlSlot != null && pearlIndex >= 0 && pearlIndex < 9 && bestEnderPearlSlot.getStack().getItem() instanceof EnderPearlItem) {
            int targetSlotId = 36 + pearlIndex;
            if (bestEnderPearlSlot.id != targetSlotId) count++;
        }

        int rodIndex = slotFishingRod.get() - 1;
        if (bestFishingRodSlot != null && rodIndex >= 0 && rodIndex < 9 && bestFishingRodSlot.getStack().getItem() instanceof FishingRodItem) {
            int targetSlotId = 36 + rodIndex;
            if (bestFishingRodSlot.id != targetSlotId) count++;
        }

        int bowIndex = slotBow.get() - 1;
        if (bestBowSlot != null && bowIndex >= 0 && bowIndex < 9 && bestBowSlot.getStack().getItem() instanceof BowItem) {
            int targetSlotId = 36 + bowIndex;
            if (bestBowSlot.id != targetSlotId) count++;
        }

        int axeIndex = slotAxe.get() - 1;
        if (bestAxeSlot != null && axeIndex >= 0 && axeIndex < 9 && bestAxeSlot.getStack().getItem() instanceof AxeItem) {
            int targetSlotId = 36 + axeIndex;
            if (bestAxeSlot.id != targetSlotId) count++;
        }

        int pickaxeIndex = slotPickaxe.get() - 1;
        if (bestPickaxeSlot != null && pickaxeIndex >= 0 && pickaxeIndex < 9 && bestPickaxeSlot.getStack().getItem() instanceof PickaxeItem) {
            int targetSlotId = 36 + pickaxeIndex;
            if (bestPickaxeSlot.id != targetSlotId) count++;
        }

        int bucketIndex = slotBucket.get() - 1;
        if (bestBucketSlot != null && bucketIndex >= 0 && bucketIndex < 9 && bestBucketSlot.getStack().getItem() == Items.WATER_BUCKET) {
            int targetSlotId = 36 + bucketIndex;
            if (bestBucketSlot.id != targetSlotId) count++;
        }

        int potIndex = slotPot.get() - 1;
        if (bestPotSlot != null && potIndex >= 0 && potIndex < 9) {
            int targetSlotId = 36 + potIndex;
            if (bestPotSlot.id != targetSlotId) count++;
        }
        return count;
    }

    private int getPendingArmorCount(Slot[] bestArmorSlots) {
        if (!autoArmor.get()) return 0;
        int count = 0;
        for (int i = 0; i < bestArmorSlots.length; i++) {
            Slot slot = bestArmorSlots[i];
            if (slot != null && !isArmorEquipped(i, slot)) {
                count++;
            }
        }
        return count;
    }

    private int getPendingOffhandCount() {
        OffhandMode mode = offHandMode.get();
        if (mode == OffhandMode.None) return 0;

        Slot offhandSlot = getOffhandSlot();
        if (offhandSlot == null) return 0;

        ItemStack offhandStack = offhandSlot.getStack();

        if (mode == OffhandMode.Gapple) {
            if (!isGapple(offhandStack.getItem())) {
                Slot bestGappleSlot = findBestGappleSlot();
                if (bestGappleSlot != null && bestGappleSlot.id != 45) {
                    return 1;
                }
            }
        } else if (mode == OffhandMode.Throwable) {
            if (!isThrowableItem(offhandStack)) {
                Slot bestThrowableSlot = findBestThrowableSlot();
                if (bestThrowableSlot != null && bestThrowableSlot.id != 45) {
                    return 1;
                }
            }
        }
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

    private boolean isArmor(ItemStack stack) {
        return stack.isIn(ItemTags.FOOT_ARMOR) || stack.isIn(ItemTags.LEG_ARMOR)
                || stack.isIn(ItemTags.CHEST_ARMOR) || stack.isIn(ItemTags.HEAD_ARMOR);
    }

    private EquipmentSlot getArmorSlot(ItemStack stack) {
        if (stack.isOf(Items.ELYTRA)) return EquipmentSlot.CHEST;
        if (stack.isIn(ItemTags.FOOT_ARMOR)) return EquipmentSlot.FEET;
        if (stack.isIn(ItemTags.LEG_ARMOR)) return EquipmentSlot.LEGS;
        if (stack.isIn(ItemTags.CHEST_ARMOR)) return EquipmentSlot.CHEST;
        if (stack.isIn(ItemTags.HEAD_ARMOR)) return EquipmentSlot.HEAD;
        return null;
    }

    private boolean isElytraUsable(ItemStack stack) {
        if (!stack.isOf(Items.ELYTRA)) return false;
        return stack.getDamage() < stack.getMaxDamage() - 1;
    }

    private int getProtection(ItemStack stack) {
        if (stack.isEmpty()) return -1;

        if (stack.isOf(Items.ELYTRA)) {
            if (!isElytraUsable(stack)) return 0;
            return 1;
        }

        if (!isArmor(stack)) return 0;

        int prot = 0;

        AttributeModifiersComponent attrComp = stack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
        if (attrComp != null) {
            for (var entry : attrComp.modifiers()) {
                if (entry.attribute().value() == EntityAttributes.ARMOR.value()) {
                    prot += (int) entry.modifier().value();
                }
            }
        }

        if (stack.hasEnchantments() && mc.world != null) {
            var registry = mc.world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);
            var protectionEntry = registry.getOptional(Enchantments.PROTECTION);
            if (protectionEntry.isPresent()) {
                prot += EnchantmentHelper.getLevel(protectionEntry.get(), stack);
            }
        }

        return prot;
    }

    private boolean isBetterOrEqualArmor(ItemStack equipped, ItemStack candidate) {
        int equippedProt = getProtection(equipped);
        int candidateProt = getProtection(candidate);
        
        if (equippedProt == -1 && candidateProt != -1) return false;
        if (equippedProt != -1 && candidateProt == -1) return true;
        
        return equippedProt >= candidateProt;
    }

    private int getPotionScore(ItemStack stack) {
        if (!stack.contains(DataComponentTypes.POTION_CONTENTS)) return -1;
        PotionContentsComponent contents = stack.get(DataComponentTypes.POTION_CONTENTS);
        if (contents == null) return -1;

        boolean isSplash = stack.getItem() == Items.SPLASH_POTION;
        int score = 0;
        boolean hasUsefulEffect = false;

        for (StatusEffectInstance effect : contents.getEffects()) {
            var type = effect.getEffectType();
            if (type == StatusEffects.STRENGTH) {
                if (!mc.player.hasStatusEffect(StatusEffects.STRENGTH)) {
                    score += 300;
                    hasUsefulEffect = true;
                }
            } else if (type == StatusEffects.SPEED) {
                if (!mc.player.hasStatusEffect(StatusEffects.SPEED)) {
                    score += 200;
                    hasUsefulEffect = true;
                }
            } else if (type == StatusEffects.REGENERATION) {
                 if (!mc.player.hasStatusEffect(StatusEffects.REGENERATION)) {
                    score += 100;
                    hasUsefulEffect = true;
                }
            } else if (type == StatusEffects.FIRE_RESISTANCE) {
                if (!mc.player.hasStatusEffect(StatusEffects.FIRE_RESISTANCE)) {
                    score += 150;
                    hasUsefulEffect = true;
                }
            }
        }

        if (!hasUsefulEffect) return -1;

        if (isSplash) score += 1000;
        
        return score;
    }

    @Override
    protected void onDisable() {
        nextDelayMs = 0L;
        currentBestBlockSlot = null;
        backupBlockSlot = null;
        gappleStackSlots.clear();
        throwableStackSlots.clear();
    }
}
