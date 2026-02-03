package dev.mahiro.client.module.impl.player.inventory;

import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.utils.math.MathUtil;
import dev.mahiro.client.utils.time.TimerUtil;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.*;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Stealer extends Module {
    public Stealer() {
        super("Stealer", "箱子小偷", Category.Player);
    }

    private final BoolValue pickEnderChest = new BoolValue("Ender Chest", "末影箱", false);
    private final NumberValue<Integer> minDelay = new NumberValue<>("Min Delay", "最小延迟", 90, 0, 150, 5);
    private final NumberValue<Integer> maxDelay = new NumberValue<>("Max Delay", "最大延迟", 110, 0, 150, 5);

    private Screen lastTickScreen;
    private final TimerUtil timer = new TimerUtil();

    public boolean isWorking() {
        return !timer.delay(3);
    }

    public static boolean isItemUseful(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        } else if (InvHelper.isGodItem(stack) || InvHelper.isSharpnessAxe(stack)) {
            return true;
        } else if (stack.getItem() instanceof ArmorItem) {
            float protection = InvHelper.getProtection(stack);
            var equippable = stack.get(DataComponentTypes.EQUIPPABLE);
            if (equippable == null) return false;
            float bestArmor = InvHelper.getBestArmorScore(equippable.slot());
            return !(protection <= bestArmor);
        } else if (stack.isIn(ItemTags.SWORDS)) {
            float damage = InvHelper.getSwordDamage(stack);
            float bestDamage = InvHelper.getBestSwordDamage();
            return !(damage <= bestDamage);
        } else if (stack.isIn(ItemTags.PICKAXES)) {
            float score = InvHelper.getToolScore(stack);
            float bestScore = InvHelper.getBestPickaxeScore();
            return !(score <= bestScore);
        } else if (stack.getItem() instanceof AxeItem) {
            float score = InvHelper.getToolScore(stack);
            float bestScore = InvHelper.getBestAxeScore();
            return !(score <= bestScore);
        } else if (stack.getItem() instanceof ShovelItem) {
            float score = InvHelper.getToolScore(stack);
            float bestScore = InvHelper.getBestShovelScore();
            return !(score <= bestScore);
        } else if (stack.getItem() instanceof CrossbowItem) {
            float score = InvHelper.getCrossbowScore(stack);
            float bestScore = InvHelper.getBestCrossbowScore();
            return !(score <= bestScore);
        } else if (stack.getItem() instanceof BowItem && InvHelper.isPunchBow(stack)) {
            float score = InvHelper.getPunchBowScore(stack);
            float bestScore = InvHelper.getBestPunchBowScore();
            return !(score <= bestScore);
        } else if (stack.getItem() instanceof BowItem && InvHelper.isPowerBow(stack)) {
            float score = InvHelper.getPowerBowScore(stack);
            float bestScore = InvHelper.getBestPowerBowScore();
            return !(score <= bestScore);
        } else if (stack.getItem() == Items.COMPASS) {
            return !InvHelper.hasItem(stack.getItem());
        } else if (stack.getItem() == Items.WATER_BUCKET && InvHelper.getItemCount(Items.WATER_BUCKET) >= InvManager.getWaterBucketCount()) {
            return false;
        } else if (stack.getItem() == Items.LAVA_BUCKET && InvHelper.getItemCount(Items.LAVA_BUCKET) >= InvManager.getLavaBucketCount()) {
            return false;
        } else if (stack.getItem() instanceof BlockItem
                && InvHelper.isValidStack(stack)
                && InvHelper.getBlockCountInInventory() + stack.getCount() >= InvManager.getMaxBlockSize()) {
            return false;
        } else if (stack.getItem() == Items.ARROW && InvHelper.getItemCount(Items.ARROW) + stack.getCount() >= InvManager.getMaxArrowSize()) {
            return false;
        } else if (stack.getItem() instanceof FishingRodItem && InvHelper.getItemCount(Items.FISHING_ROD) >= 1) {
            return false;
        } else if (stack.getItem() != Items.SNOWBALL && stack.getItem() != Items.EGG
                || InvHelper.getItemCount(Items.SNOWBALL) + InvHelper.getItemCount(Items.EGG) + stack.getCount() < InvManager.getMaxProjectileSize()
                && InvManager.shouldKeepProjectile()) {
            return !stack.contains(DataComponentTypes.CUSTOM_NAME) && InvHelper.isCommonItemUseful(stack);
        } else {
            return false;
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre e) {
        Screen currentScreen = mc.currentScreen;
        if (currentScreen instanceof GenericContainerScreen container) {
            GenericContainerScreenHandler menu = container.getScreenHandler();
            if (currentScreen != this.lastTickScreen) {
                timer.reset();
            } else {
                String chestTitle = container.getTitle().getString();
                String chest = Text.translatable("container.chest").getString();
                String largeChest = Text.translatable("container.chestDouble").getString();
                String enderChest = Text.translatable("container.enderchest").getString();
                if (chestTitle.equals(chest)
                        || chestTitle.equals(largeChest)
                        || chestTitle.equals("Chest")
                        || this.pickEnderChest.get() && chestTitle.equals(enderChest)) {
                    if (this.isChestEmpty(menu) && timer.passedMS(MathUtil.getRandom(this.minDelay.get(), this.maxDelay.get()))) {
                        mc.player.closeHandledScreen();
                    } else {
                        List<Integer> slots = IntStream.range(0, menu.getRows() * 9).boxed().collect(Collectors.toList());
                        Collections.shuffle(slots);

                        for (Integer pSlotId : slots) {
                            ItemStack stack = menu.getSlot(pSlotId).getStack();
                            if (isItemUseful(stack) && this.isBestItemInChest(menu, stack) && timer.passedMS(MathUtil.getRandom(this.minDelay.get(), this.maxDelay.get()))) {
                                mc.interactionManager.clickSlot(menu.syncId, pSlotId, 0, SlotActionType.QUICK_MOVE, mc.player);
                                timer.reset();
                                break;
                            }
                        }
                    }
                }
            }

            this.lastTickScreen = currentScreen;
        }
    }

    private boolean isBestItemInChest(GenericContainerScreenHandler menu, ItemStack stack) {
        if (!InvHelper.isGodItem(stack) && !InvHelper.isSharpnessAxe(stack)) {
            for (int i = 0; i < menu.getRows() * 9; i++) {
                ItemStack checkStack = menu.getSlot(i).getStack();
                if (stack.getItem() instanceof ArmorItem && checkStack.getItem() instanceof ArmorItem) {
                    var stackEquippable = stack.get(DataComponentTypes.EQUIPPABLE);
                    var checkEquippable = checkStack.get(DataComponentTypes.EQUIPPABLE);
                    if (stackEquippable != null && checkEquippable != null
                            && stackEquippable.slot() == checkEquippable.slot()
                            && InvHelper.getProtection(checkStack) > InvHelper.getProtection(stack)) {
                        return false;
                    }
                } else if (stack.isIn(ItemTags.SWORDS) && checkStack.isIn(ItemTags.SWORDS)) {
                    if (InvHelper.getSwordDamage(checkStack) > InvHelper.getSwordDamage(stack)) {
                        return false;
                    }
                } else if (stack.isIn(ItemTags.PICKAXES) && checkStack.isIn(ItemTags.PICKAXES)) {
                    if (InvHelper.getToolScore(checkStack) > InvHelper.getToolScore(stack)) {
                        return false;
                    }
                } else if (stack.getItem() instanceof AxeItem && checkStack.getItem() instanceof AxeItem) {
                    if (InvHelper.getToolScore(checkStack) > InvHelper.getToolScore(stack)) {
                        return false;
                    }
                } else if (stack.getItem() instanceof ShovelItem
                        && checkStack.getItem() instanceof ShovelItem
                        && InvHelper.getToolScore(checkStack) > InvHelper.getToolScore(stack)) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    private boolean isChestEmpty(GenericContainerScreenHandler menu) {
        for (int i = 0; i < menu.getRows() * 9; i++) {
            ItemStack item = menu.getSlot(i).getStack();
            if (!item.isEmpty() && isItemUseful(item) && this.isBestItemInChest(menu, item)) {
                return false;
            }
        }

        return true;
    }
}
