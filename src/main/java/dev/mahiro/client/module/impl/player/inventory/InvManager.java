package dev.mahiro.client.module.impl.player.inventory;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.events.packet.PacketEvent;
import dev.mahiro.client.events.type.EventType;
import dev.mahiro.client.gui.clickgui.ClickGuiScreen;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.module.impl.movement.Scaffold;
import dev.mahiro.client.utils.client.ChatUtil;
import dev.mahiro.client.utils.math.MathUtil;
import dev.mahiro.client.utils.player.MoveUtil;
import dev.mahiro.client.utils.time.TimerUtil;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.EnumValue;
import dev.mahiro.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.*;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.slot.SlotActionType;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InvManager extends Module {
    public InvManager() {
        super("InvManager", "背包管理", Category.Player);
    }

    private enum OffhandItemMode {
        None,
        GoldenApple,
        Projectile,
        FishingRod,
        Block
    }

    private enum BowPriority {
        Crossbow,
        PowerBow,
        PunchBow
    }

    private final NumberValue<Double> minDelay = new NumberValue<>("Min Delay", "最小延迟", 90.0, 0.0, 150.0, 5.0);
    private final NumberValue<Double> maxDelay = new NumberValue<>("Max Delay", "最大延迟", 110.0, 0.0, 150.0, 5.0);
    private final EnumValue<OffhandItemMode> offhandItems = new EnumValue<>("Offhand Items", "副手物品", OffhandItemMode.Projectile);
    private final BoolValue autoArmor = new BoolValue("Auto Armor", "自动穿甲", true);
    private final BoolValue inventoryOnly = new BoolValue("Inventory Only", "仅背包界面", true);
    private final BoolValue switchSword = new BoolValue("Switch Sword", "切换剑", true);
    private final NumberValue<Integer> swordSlot = new NumberValue<>("Sword Slot", "剑槽位", 1, 1, 9, 1, switchSword::get);
    private final BoolValue switchBlock = new BoolValue("Switch Block", "切换方块", true, () -> !offhandItems.is(OffhandItemMode.Block));
    private final NumberValue<Integer> blockSlot = new NumberValue<>("Block Slot", "方块槽位", 9, 1, 9, 1, () -> switchBlock.get() && !offhandItems.is(OffhandItemMode.Block));
    private final NumberValue<Integer> maxBlockSize = new NumberValue<>("Max Block Size", "最大方块数量", 256, 64, 512, 64, switchBlock::get);
    private final BoolValue switchPickaxe = new BoolValue("Switch Pickaxe", "切换镐子", true);
    private final NumberValue<Integer> pickaxeSlot = new NumberValue<>("Pickaxe Slot", "镐子槽位", 3, 1, 9, 1, switchPickaxe::get);
    private final BoolValue switchAxe = new BoolValue("Switch Axe", "切换斧头", true);
    private final NumberValue<Integer> axeSlot = new NumberValue<>("Axe Slot", "斧头槽位", 4, 1, 9, 1, switchAxe::get);
    private final BoolValue switchBow = new BoolValue("Switch Bow or Crossbow", "切换弓/弩", true);
    private final NumberValue<Integer> bowSlot = new NumberValue<>("Bow Slot", "弓槽位", 5, 1, 9, 1, switchBow::get);
    private final EnumValue<BowPriority> preferBow = new EnumValue<>("Bow Priority", "弓优先级", BowPriority.Crossbow, switchBow::get);
    private final NumberValue<Integer> maxArrowSize = new NumberValue<>("Max Arrow Size", "最大箭矢数量", 256, 64, 512, 64, switchBow::get);
    private final BoolValue switchWaterBucket = new BoolValue("Switch Water Bucket", "切换水桶", true);
    private final NumberValue<Integer> waterBucketSlot = new NumberValue<>("Water Bucket Slot", "水桶槽位", 6, 1, 9, 1, switchWaterBucket::get);
    private final BoolValue switchEnderPearl = new BoolValue("Switch Ender Pearl", "切换末影珍珠", true);
    private final NumberValue<Integer> enderPearlSlot = new NumberValue<>("Ender Pearl Slot", "末影珍珠槽位", 7, 1, 9, 1, switchEnderPearl::get);
    private final BoolValue switchFireball = new BoolValue("Switch Fireball", "切换火球", true);
    private final NumberValue<Integer> fireballSlot = new NumberValue<>("Fireball Slot", "火球槽位", 8, 1, 9, 1, switchFireball::get);
    private final BoolValue switchGoldenApple = new BoolValue("Switch Golden Apple", "切换金苹果", true, () -> !offhandItems.is(OffhandItemMode.GoldenApple));
    private final NumberValue<Integer> goldenAppleSlot = new NumberValue<>("Golden Apple Slot", "金苹果槽位", 2, 1, 9, 1, () -> switchGoldenApple.get() && !offhandItems.is(OffhandItemMode.GoldenApple));
    private final BoolValue throwItems = new BoolValue("Throw Items", "丢弃物品", true);
    private final NumberValue<Integer> waterBucketCount = new NumberValue<>("Keep Water Buckets", "保留水桶数量", 1, 0, 5, 1, throwItems::get);
    private final NumberValue<Integer> lavaBucketCount = new NumberValue<>("Keep Lava Buckets", "保留岩浆桶数量", 1, 0, 5, 1, throwItems::get);
    private final BoolValue keepProjectile = new BoolValue("Keep Eggs & Snowballs", "保留鸡蛋与雪球", true);
    private final BoolValue switchProjectile = new BoolValue("Switch Eggs & Snowballs", "切换鸡蛋与雪球", true, () -> keepProjectile.get() && !offhandItems.is(OffhandItemMode.Projectile));
    private final NumberValue<Integer> projectileSlot = new NumberValue<>("Eggs & Snowballs Slot", "鸡蛋与雪球槽位", 9, 1, 9, 1, () -> switchProjectile.get() && keepProjectile.get() && !offhandItems.is(OffhandItemMode.Projectile));
    private final NumberValue<Integer> maxProjectileSize = new NumberValue<>("Max Eggs & Snowballs Size", "最大鸡蛋与雪球数量", 64, 16, 256, 16, keepProjectile::get);
    private final BoolValue switchRod = new BoolValue("Switch Rod", "切换鱼竿", false, () -> !offhandItems.is(OffhandItemMode.FishingRod));
    private final NumberValue<Integer> rodSlot = new NumberValue<>("Rod Slot", "鱼竿槽位", 9, 1, 9, 1, () -> switchRod.get() && !offhandItems.is(OffhandItemMode.FishingRod));

    private int noMoveTicks = 0;
    private boolean clickOffHand = false;
    private boolean inventoryOpen = false;
    private final TimerUtil timer = new TimerUtil();

    public static int getMaxBlockSize() {
        return Mahiro.MODULES.getModule(InvManager.class).maxBlockSize.get();
    }

    public static boolean shouldKeepProjectile() {
        return Mahiro.MODULES.getModule(InvManager.class).keepProjectile.get();
    }

    public static int getMaxProjectileSize() {
        return Mahiro.MODULES.getModule(InvManager.class).maxProjectileSize.get();
    }

    public static int getMaxArrowSize() {
        return Mahiro.MODULES.getModule(InvManager.class).maxArrowSize.get();
    }

    public static int getWaterBucketCount() {
        return Mahiro.MODULES.getModule(InvManager.class).waterBucketCount.get();
    }

    public static int getLavaBucketCount() {
        return Mahiro.MODULES.getModule(InvManager.class).lavaBucketCount.get();
    }

    public boolean isItemUseful(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        } else if (InvHelper.isGodItem(stack)) {
            return true;
        } else if (stack.getName().getString().contains("点击使用")) {
            return true;
        } else if (stack.getItem() instanceof ArmorItem) {
            float protection = InvHelper.getProtection(stack);
            var equippable = stack.get(DataComponentTypes.EQUIPPABLE);
            if (equippable == null) return false;
            if (InvHelper.getCurrentArmorScore(equippable.slot()) >= protection) {
                return false;
            } else {
                float bestArmor = InvHelper.getBestArmorScore(equippable.slot());
                return !(protection < bestArmor);
            }
        } else if (stack.isIn(ItemTags.SWORDS)) {
            return InvHelper.getBestSword() == stack;
        } else if (stack.isIn(ItemTags.PICKAXES)) {
            return InvHelper.getBestPickaxe() == stack;
        } else if (stack.getItem() instanceof AxeItem && !InvHelper.isSharpnessAxe(stack)) {
            return InvHelper.getBestAxe() == stack;
        } else if (stack.getItem() instanceof ShovelItem) {
            return InvHelper.getBestShovel() == stack;
        } else if (stack.getItem() instanceof CrossbowItem) {
            return InvHelper.getBestCrossbow() == stack;
        } else if (stack.getItem() instanceof BowItem && InvHelper.isPunchBow(stack)) {
            return InvHelper.getBestPunchBow() == stack;
        } else if (stack.getItem() instanceof BowItem && InvHelper.isPowerBow(stack)) {
            return InvHelper.getBestPowerBow() == stack;
        } else if (stack.getItem() instanceof BowItem && InvHelper.getItemCount(Items.BOW) > 1) {
            return false;
        } else if (stack.getItem() == Items.WATER_BUCKET && InvHelper.getItemCount(Items.WATER_BUCKET) > getWaterBucketCount()) {
            return false;
        } else if (stack.getItem() == Items.LAVA_BUCKET && InvHelper.getItemCount(Items.LAVA_BUCKET) > getLavaBucketCount()) {
            return false;
        } else if (stack.getItem() instanceof FishingRodItem && InvHelper.getItemCount(Items.FISHING_ROD) > 1) {
            return false;
        } else if ((stack.getItem() == Items.SNOWBALL || stack.getItem() == Items.EGG) && !shouldKeepProjectile()) {
            return false;
        } else {
            return !(stack.getItem().getComponents().contains(DataComponentTypes.CUSTOM_NAME)) && InvHelper.isCommonItemUseful(stack);
        }
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (nullCheck()) return;

        if (event.getType() == EventType.SEND) {
            if (event.getPacket() instanceof CloseHandledScreenC2SPacket) {
                this.inventoryOpen = false;
            }

            if (this.inventoryOpen && !this.inventoryOnly.get()) {
                if (event.getPacket() instanceof PlayerMoveC2SPacket) {
                    if (MoveUtil.isMoving()) {
                        mc.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(mc.player.playerScreenHandler.syncId));
                    }
                } else if (event.getPacket() instanceof PlayerInteractBlockC2SPacket
                        || event.getPacket() instanceof PlayerInteractItemC2SPacket
                        || event.getPacket() instanceof PlayerInteractEntityC2SPacket
                        || event.getPacket() instanceof PlayerActionC2SPacket) {
                    mc.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(mc.player.playerScreenHandler.syncId));
                }
            }
        }
    }

    private boolean checkConfig() {
        List<Pair<BoolValue, NumberValue<Integer>>> pairs = new ArrayList<>();
        if (!this.keepProjectile.get()) {
            this.switchProjectile.set(false);
        }

        pairs.add(Pair.of(this.switchSword, this.swordSlot));
        pairs.add(Pair.of(this.switchPickaxe, this.pickaxeSlot));
        pairs.add(Pair.of(this.switchAxe, this.axeSlot));
        pairs.add(Pair.of(this.switchBow, this.bowSlot));
        pairs.add(Pair.of(this.switchWaterBucket, this.waterBucketSlot));
        pairs.add(Pair.of(this.switchEnderPearl, this.enderPearlSlot));
        pairs.add(Pair.of(this.switchFireball, this.fireballSlot));
        if (!this.offhandItems.is(OffhandItemMode.GoldenApple)) {
            pairs.add(Pair.of(this.switchGoldenApple, this.goldenAppleSlot));
        }

        if (!this.offhandItems.is(OffhandItemMode.Projectile)) {
            pairs.add(Pair.of(this.switchProjectile, this.projectileSlot));
        }

        if (!this.offhandItems.is(OffhandItemMode.FishingRod)) {
            pairs.add(Pair.of(this.switchRod, this.rodSlot));
        }

        if (!this.offhandItems.is(OffhandItemMode.Block)) {
            pairs.add(Pair.of(this.switchBlock, this.blockSlot));
        }

        Set<Integer> usedSlot = new HashSet<>();

        for (Pair<BoolValue, NumberValue<Integer>> pair : pairs) {
            if (pair.getKey().get()) {
                int targetSlot = (int) (pair.getValue().get() - 1.0F);
                if (usedSlot.contains(targetSlot)) {
                    return false;
                }

                usedSlot.add(targetSlot);
            }
        }

        return true;
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (!(mc.currentScreen instanceof ClickGuiScreen) && !this.checkConfig()) {
            ChatUtil.addChatMessage("Duplicate slot config in Inventory Manager! Please check your config!");
            this.toggle();
            return;
        }

        if (InvHelper.shouldDisableFeatures()) {
            return;
        }

        if (MoveUtil.isMoving()) {
            this.noMoveTicks = 0;
        } else {
            this.noMoveTicks++;
        }

        if (Mahiro.MODULES.getModule(Stealer.class).isWorking()
                || Mahiro.MODULES.getModule(Scaffold.class).isEnabled()
                || (this.inventoryOnly.get() ? !(mc.currentScreen instanceof InventoryScreen) : this.noMoveTicks <= 1)) {
            this.clickOffHand = false;
            return;
        }

        if (mc.currentScreen instanceof HandledScreen<?> container && container.getScreenHandler().syncId != mc.player.playerScreenHandler.syncId) {
            return;
        }

        if (this.autoArmor.get()) {
            for (int i = 0; i < mc.player.getInventory().armor.size(); i++) {
                ItemStack stack = mc.player.getInventory().armor.get(i);
                if (stack.getItem() instanceof ArmorItem item) {
                    var equipment = stack.get(DataComponentTypes.EQUIPPABLE);
                    if (equipment == null) return;

                    if (!stack.isEmpty() && timer.passedMS(MathUtil.getRandom(this.minDelay.get(), this.maxDelay.get())) && InvHelper.getBestArmorScore(equipment.slot()) > InvHelper.getProtection(stack)) {
                        mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, 4 + (4 - i), 1, SlotActionType.THROW, mc.player);
                        this.inventoryOpen = true;
                        timer.reset();
                    }
                }
            }

            for (int ix = 0; ix < mc.player.getInventory().getMainStacks().size(); ix++) {
                ItemStack stack = mc.player.getInventory().getMainStacks().get(ix);
                if (!stack.isEmpty() && stack.getItem() instanceof ArmorItem) {
                    float currentItemScore = InvHelper.getProtection(stack);
                    var equipment = stack.get(DataComponentTypes.EQUIPPABLE);
                    if (equipment == null) return;

                    boolean isBestItem = InvHelper.getBestArmorScore(equipment.slot()) == currentItemScore;
                    boolean isBetterItem = InvHelper.getCurrentArmorScore(equipment.slot()) < currentItemScore;
                    if (isBestItem && isBetterItem && timer.passedMS(MathUtil.getRandom(minDelay.get(), maxDelay.get()))) {
                        if (ix < 9) {
                            mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, ix + 36, 0, SlotActionType.QUICK_MOVE, mc.player);
                        } else {
                            mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, ix, 0, SlotActionType.QUICK_MOVE, mc.player);
                        }

                        this.inventoryOpen = true;
                        timer.reset();
                    }
                }
            }
        }

        if (this.clickOffHand && timer.passedMS(MathUtil.getRandom(minDelay.get(), maxDelay.get()))) {
            mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, 45, 0, SlotActionType.PICKUP, mc.player);
            this.inventoryOpen = true;
            this.clickOffHand = false;
            timer.reset();
        }

        if (this.offhandItems.is(OffhandItemMode.GoldenApple)) {
            ItemStack offHand = mc.player.getInventory().offHand.get(0);
            int slot = InvHelper.getItemSlot(Items.GOLDEN_APPLE);
            if (slot != -1 && timer.passedMS(MathUtil.getRandom(minDelay.get(), maxDelay.get()))) {
                if (offHand.getItem() == Items.GOLDEN_APPLE) {
                    ItemStack goldenAppleStack = mc.player.getInventory().getMainStacks().get(slot);
                    if (offHand.getCount() + goldenAppleStack.getCount() <= 64) {
                        if (slot < 9) {
                            mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, slot + 36, 0, SlotActionType.PICKUP, mc.player);
                        } else {
                            mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, slot, 0, SlotActionType.PICKUP, mc.player);
                        }

                        this.inventoryOpen = true;
                        this.clickOffHand = true;
                        timer.reset();
                    }
                } else {
                    this.swapOffHand(slot);
                }
            }
        } else if (this.offhandItems.is(OffhandItemMode.Projectile)) {
            ItemStack offHand = mc.player.getInventory().offHand.get(0);
            ItemStack bestProjectile = InvHelper.getBestProjectile();
            if (bestProjectile != null) {
                int slot = InvHelper.getItemStackSlot(bestProjectile);
                boolean shouldSwap = false;
                if (offHand.getItem() != Items.EGG && offHand.getItem() != Items.SNOWBALL) {
                    shouldSwap = true;
                } else if (offHand.getCount() < bestProjectile.getCount()) {
                    shouldSwap = true;
                }

                if (shouldSwap && slot != -1 && timer.passedMS(MathUtil.getRandom(minDelay.get(), maxDelay.get()))) {
                    this.swapOffHand(slot);
                }
            }
        } else if (this.offhandItems.is(OffhandItemMode.FishingRod)) {
            ItemStack offHand = mc.player.getInventory().offHand.get(0);
            int slotx = InvHelper.getItemSlot(Items.FISHING_ROD);
            if (slotx != -1 && timer.passedMS(MathUtil.getRandom(minDelay.get(), maxDelay.get())) && offHand.getItem() != Items.FISHING_ROD) {
                this.swapOffHand(slotx);
            }
        } else if (this.offhandItems.is(OffhandItemMode.Block)) {
            ItemStack offHand = mc.player.getInventory().offHand.get(0);
            ItemStack bestBlock = InvHelper.getBestBlock();
            if (bestBlock != null) {
                int slotx = InvHelper.getItemStackSlot(bestBlock);
                boolean shouldSwapx = false;
                if (InvHelper.isValidStack(offHand)) {
                    if (offHand.getCount() < bestBlock.getCount()) {
                        shouldSwapx = true;
                    }
                } else {
                    shouldSwapx = true;
                }

                if (shouldSwapx && slotx != -1 && timer.passedMS(MathUtil.getRandom(minDelay.get(), maxDelay.get()))) {
                    this.swapOffHand(slotx);
                }
            }
        }

        if (this.switchGoldenApple.get() && !this.offhandItems.is(OffhandItemMode.GoldenApple)) {
            this.swapItem((int) (this.goldenAppleSlot.get() - 1.0F), Items.GOLDEN_APPLE);
        }

        if (this.switchBlock.get()) {
            int blockSlot = (int) (this.blockSlot.get() - 1.0F);
            ItemStack currentBlock = mc.player.getInventory().getMainStacks().get(blockSlot);
            ItemStack bestBlock = InvHelper.getBestBlock();
            if (bestBlock != null
                    && (bestBlock.getCount() > currentBlock.getCount() || !InvHelper.isValidStack(currentBlock))
                    && !this.offhandItems.is(OffhandItemMode.Block)) {
                this.swapItem(blockSlot, bestBlock);
            }

            if ((float) InvHelper.getBlockCountInInventory() > this.maxBlockSize.get()) {
                ItemStack worstBlock = InvHelper.getWorstBlock();
                this.throwItem(worstBlock);
            }
        }

        if (this.switchSword.get()) {
            int slotxx = (int) (this.swordSlot.get() - 1.0F);
            ItemStack currentSword = mc.player.getInventory().getMainStacks().get(slotxx);
            ItemStack bestSword = InvHelper.getBestSword();
            ItemStack bestShapeAxe = InvHelper.getBestShapeAxe();
            if (InvHelper.getAxeDamage(bestShapeAxe) > InvHelper.getSwordDamage(bestSword)) {
                bestSword = bestShapeAxe;
            }

            if (bestSword != null) {
                float currentDamage = currentSword.isIn(ItemTags.SWORDS)
                        ? InvHelper.getSwordDamage(currentSword)
                        : InvHelper.getAxeDamage(currentSword);
                float bestWeaponDamage = bestSword.isIn(ItemTags.SWORDS)
                        ? InvHelper.getSwordDamage(bestSword)
                        : InvHelper.getAxeDamage(bestSword);
                if (bestWeaponDamage > currentDamage) {
                    this.swapItem(slotxx, bestSword);
                }
            }
        }

        if (this.switchPickaxe.get()) {
            int slotxxx = (int) (this.pickaxeSlot.get() - 1.0F);
            ItemStack bestPickaxe = InvHelper.getBestPickaxe();
            ItemStack currentPickaxe = mc.player.getInventory().getMainStacks().get(slotxxx);
            if (bestPickaxe != null
                    && bestPickaxe.isIn(ItemTags.PICKAXES)
                    && (InvHelper.getToolScore(bestPickaxe) > InvHelper.getToolScore(currentPickaxe) || !currentPickaxe.isIn(ItemTags.PICKAXES))
            ) {
                this.swapItem(slotxxx, bestPickaxe);
            }
        }

        if (this.switchAxe.get()) {
            int slotxxx = (int) (this.axeSlot.get() - 1.0F);
            ItemStack bestAxe = InvHelper.getBestAxe();
            ItemStack currentAxe = mc.player.getInventory().getMainStacks().get(slotxxx);
            if (bestAxe != null
                    && bestAxe.getItem() instanceof AxeItem
                    && (InvHelper.getToolScore(bestAxe) > InvHelper.getToolScore(currentAxe) || !(currentAxe.getItem() instanceof AxeItem))) {
                this.swapItem(slotxxx, bestAxe);
            }
        }

        if (this.switchRod.get() && !this.offhandItems.is(OffhandItemMode.FishingRod)) {
            int slotxxx = (int) (this.rodSlot.get() - 1.0F);
            ItemStack bestRod = InvHelper.getFishingRod();
            ItemStack currentRod = mc.player.getInventory().getMainStacks().get(slotxxx);
            if (!(currentRod.getItem() instanceof FishingRodItem)) {
                this.swapItem(slotxxx, bestRod);
            }
        }

        if (this.switchBow.get()) {
            int slotxxx = (int) (this.bowSlot.get() - 1.0F);
            ItemStack currentBow = mc.player.getInventory().getMainStacks().get(slotxxx);
            ItemStack bestBow;
            float bestBowScore;
            float currentBowScore;
            if (this.preferBow.is(BowPriority.Crossbow)) {
                bestBow = InvHelper.getBestCrossbow();
                bestBowScore = InvHelper.getCrossbowScore(bestBow);
                currentBowScore = InvHelper.getCrossbowScore(currentBow);
            } else if (this.preferBow.is(BowPriority.PowerBow)) {
                bestBow = InvHelper.getBestPowerBow();
                bestBowScore = InvHelper.getPowerBowScore(bestBow);
                currentBowScore = InvHelper.getPowerBowScore(currentBow);
            } else {
                bestBow = InvHelper.getBestPunchBow();
                bestBowScore = InvHelper.getPunchBowScore(bestBow);
                currentBowScore = InvHelper.getPunchBowScore(currentBow);
            }

            if (bestBow == null) {
                bestBow = InvHelper.getBestCrossbow();
                bestBowScore = InvHelper.getCrossbowScore(bestBow);
                currentBowScore = InvHelper.getCrossbowScore(currentBow);
            }

            if (bestBow == null) {
                bestBow = InvHelper.getBestPowerBow();
                bestBowScore = InvHelper.getPowerBowScore(bestBow);
                currentBowScore = InvHelper.getPowerBowScore(currentBow);
            }

            if (bestBow == null) {
                bestBow = InvHelper.getBestPunchBow();
                bestBowScore = InvHelper.getPunchBowScore(bestBow);
                currentBowScore = InvHelper.getPunchBowScore(currentBow);
            }

            if (bestBow != null && bestBowScore > currentBowScore) {
                this.swapItem(slotxxx, bestBow);
            }

            if ((float) InvHelper.getItemCount(Items.ARROW) > this.maxArrowSize.get()) {
                ItemStack worstArrow = InvHelper.getWorstArrow();
                this.throwItem(worstArrow);
            }
        }

        if (this.switchEnderPearl.get()) {
            this.swapItem((int) (this.enderPearlSlot.get() - 1.0F), Items.ENDER_PEARL);
        }

        if (this.switchWaterBucket.get()) {
            this.swapItem((int) (this.waterBucketSlot.get() - 1.0F), Items.WATER_BUCKET);
        }

        if (this.switchFireball.get()) {
            this.swapItem((int) (this.fireballSlot.get() - 1.0F), Items.FIRE_CHARGE);
        }

        if (this.keepProjectile.get()) {
            if ((float) (InvHelper.getItemCount(Items.EGG) + InvHelper.getItemCount(Items.SNOWBALL)) > this.maxProjectileSize.get()) {
                ItemStack worstProjectile = InvHelper.getWorstProjectile();
                this.throwItem(worstProjectile);
            }

            if (this.switchProjectile.get() && !this.offhandItems.is(OffhandItemMode.Projectile)) {
                int projectileSlot = (int) (this.projectileSlot.get() - 1.0F);
                if (InvHelper.getItemCount(Items.EGG) > 0) {
                    this.swapItem(projectileSlot, Items.EGG);
                } else if (InvHelper.getItemCount(Items.SNOWBALL) > 0) {
                    this.swapItem(projectileSlot, Items.SNOWBALL);
                }
            }
        }

        if (this.throwItems.get()) {
            List<Integer> slots = IntStream.range(0, mc.player.getInventory().getMainStacks().size()).boxed().collect(Collectors.toList());
            Collections.shuffle(slots);

            for (Integer slotxxxx : slots) {
                ItemStack stack = mc.player.getInventory().getMainStacks().get(slotxxxx);
                if (!stack.isEmpty() && !this.isItemUseful(stack)) {
                    this.throwItem(stack);
                }
            }
        }
    }

    private void swapOffHand(int slot) {
        if (slot < 9) {
            mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, slot + 36, 40, SlotActionType.SWAP, mc.player);
        } else {
            mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, slot, 40, SlotActionType.SWAP, mc.player);
        }

        this.inventoryOpen = true;
        timer.reset();
    }

    private void throwItem(ItemStack item) {
        if (InvHelper.isItemValid(item) && timer.passedMS(MathUtil.getRandom(this.minDelay.get(), this.maxDelay.get()))) {
            int itemSlot = InvHelper.getItemStackSlot(item);
            if (itemSlot != -1) {
                if (itemSlot < 9) {
                    mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, itemSlot + 36, 1, SlotActionType.THROW, mc.player);
                } else {
                    mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, itemSlot, 1, SlotActionType.THROW, mc.player);
                }

                this.inventoryOpen = true;
                timer.reset();
            }
        }
    }

    private void swapItem(int targetSlot, ItemStack bestItem) {
        ItemStack currentSlot = mc.player.getInventory().getMainStacks().get(targetSlot);
        if (InvHelper.isItemValid(currentSlot) && bestItem != currentSlot && timer.passedMS(MathUtil.getRandom(this.minDelay.get(), this.maxDelay.get()))) {
            int bestItemSlot = InvHelper.getItemStackSlot(bestItem);
            if (bestItemSlot != -1) {
                if (bestItemSlot < 9) {
                    mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, bestItemSlot + 36, targetSlot, SlotActionType.SWAP, mc.player);
                } else {
                    mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, bestItemSlot, targetSlot, SlotActionType.SWAP, mc.player);
                }

                this.inventoryOpen = true;
                timer.reset();
            }
        }
    }

    private void swapItem(int targetSlot, Item item) {
        ItemStack currentSlot = mc.player.getInventory().getMainStacks().get(targetSlot);
        if (InvHelper.isItemValid(currentSlot) && timer.passedMS(MathUtil.getRandom(this.minDelay.get(), this.maxDelay.get()))) {
            int bestItemSlot = InvHelper.getItemSlot(item);
            if (bestItemSlot != -1) {
                ItemStack bestItemStack = mc.player.getInventory().main.get(bestItemSlot);
                if (currentSlot.getItem() != item || currentSlot.getCount() < bestItemStack.getCount()) {
                    if (bestItemSlot < 9) {
                        mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, bestItemSlot + 36, targetSlot, SlotActionType.SWAP, mc.player);
                    } else {
                        mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, bestItemSlot, targetSlot, SlotActionType.SWAP, mc.player);
                    }

                    this.inventoryOpen = true;
                    timer.reset();
                }
            }
        }
    }
}
