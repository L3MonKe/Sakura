package dev.sakura.client.utils.player;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;

import java.util.function.Predicate;

import static dev.sakura.client.Sakura.mc;

public class InvUtil {
    public static int previousSlot = -1;
    public static int[] invSlots;

    public static int getEnchantmentLevel(ItemStack stack, RegistryKey<Enchantment> enchantment) {
        if (stack.isEmpty()) return 0;
        return mc.world.getRegistryManager()
                .getOrThrow(RegistryKeys.ENCHANTMENT)
                .getOptional(enchantment)
                .map(enchantmentReference -> EnchantmentHelper.getLevel(enchantmentReference, stack)).orElse(0);
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

    public static int getBlockIndex() {
        int blockCount = 0;
        for (int slotIndex = 9; slotIndex < 45; slotIndex++) {
            ItemStack stack = mc.player.currentScreenHandler.getSlot(slotIndex).getStack();
            if (stack.getItem() instanceof BlockItem) {
                blockCount += stack.getCount();
            }
        }
        return blockCount;
    }

    public static boolean isInventoryFull() {
        PlayerInventory inventory = mc.player.getInventory();
        for (int i = 0; i < inventory.main.size(); i++) {
            if (inventory.main.get(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isBlockPlaceable(ItemStack stack) {
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

    public static double getDamage(ItemStack weapon) {
        double sharpness = 0.5 * weapon.getEnchantments().getSize() + 0.5;
        return getBaseDamage(weapon) + sharpness;
    }

    public static double getBaseDamage(ItemStack weapon) {
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

    public static boolean testInMainHand(Predicate<ItemStack> predicate) {
        return predicate.test(mc.player.getMainHandStack());
    }

    public static boolean testInMainHand(Item... items) {
        return testInMainHand(itemStack -> {
            for (var item : items) if (itemStack.isOf(item)) return true;
            return false;
        });
    }

    public static boolean testInOffHand(Predicate<ItemStack> predicate) {
        return predicate.test(mc.player.getOffHandStack());
    }

    public static boolean testInOffHand(Item... items) {
        return testInOffHand(itemStack -> {
            for (var item : items) if (itemStack.isOf(item)) return true;
            return false;
        });
    }

    public static boolean testInHands(Predicate<ItemStack> predicate) {
        return testInMainHand(predicate) || testInOffHand(predicate);
    }

    public static boolean testInHands(Item... items) {
        return testInMainHand(items) || testInOffHand(items);
    }

    public static boolean testInHotbar(Predicate<ItemStack> predicate) {
        if (testInHands(predicate)) return true;

        for (int i = SlotUtil.HOTBAR_START; i <= SlotUtil.HOTBAR_END; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (predicate.test(stack)) return true;
        }

        return false;
    }

    public static boolean testInHotbar(Item... items) {
        return testInHotbar(itemStack -> {
            for (var item : items) if (itemStack.isOf(item)) return true;
            return false;
        });
    }

    public static FindItemResult findEmpty() {
        return find(ItemStack::isEmpty);
    }

    public static FindItemResult findInHotbar(Item... items) {
        return findInHotbar(itemStack -> {
            for (Item item : items) {
                if (itemStack.getItem() == item) return true;
            }
            return false;
        });
    }

    public static FindItemResult findInHotbar(Predicate<ItemStack> isGood) {
        if (testInOffHand(isGood)) {
            return new FindItemResult(SlotUtil.OFFHAND, mc.player.getOffHandStack().getCount());
        }

        if (testInMainHand(isGood)) {
            return new FindItemResult(mc.player.getInventory().selectedSlot, mc.player.getMainHandStack().getCount());
        }

        return find(isGood, 0, 8);
    }

    public static FindItemResult find(Item... items) {
        return find(itemStack -> {
            for (Item item : items) {
                if (itemStack.getItem() == item) return true;
            }
            return false;
        });
    }

    public static FindItemResult find(Predicate<ItemStack> isGood) {
        if (mc.player == null) return new FindItemResult(0, 0);
        return find(isGood, 0, mc.player.getInventory().size());
    }

    public static FindItemResult find(Predicate<ItemStack> isGood, int start, int end) {
        if (mc.player == null) return new FindItemResult(0, 0);

        int slot = -1, count = 0;

        for (int i = start; i <= end; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);

            if (isGood.test(stack)) {
                if (slot == -1) slot = i;
                count += stack.getCount();
            }
        }

        return new FindItemResult(slot, count);
    }

    public static FindItemResult findFastestTool(BlockState state, Boolean inv) {
        float bestScore = 1;
        int slot = -1;

        for (int i = 0; i < (inv ? mc.player.getInventory().size() : 9); i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (!stack.isSuitableFor(state)) continue;

            float score = stack.getMiningSpeedMultiplier(state);
            if (score > bestScore) {
                bestScore = score;
                slot = i;
            }
        }

        return new FindItemResult(slot, 1);
    }

    public static boolean swap(int slot, boolean swapBack) {
        if (slot == SlotUtil.OFFHAND) return true;
        if (slot < 0 || slot > 8) return false;
        if (swapBack && previousSlot == -1) previousSlot = mc.player.getInventory().selectedSlot;
        else if (!swapBack) previousSlot = -1;

        mc.player.getInventory().setSelectedSlot(slot);
        mc.interactionManager.syncSelectedSlot();
        return true;
    }

    public static boolean swapBack() {
        if (previousSlot == -1) return false;

        boolean return_ = swap(previousSlot, false);
        previousSlot = -1;
        return return_;
    }

    public static boolean invSwap(int slot) {
        if (slot >= 0) {
            int containerSlot = slot;
            if (slot < 9) containerSlot += 36;
            else if (slot == 40) containerSlot = 45;

            ScreenHandler handler = mc.player.currentScreenHandler;
            int selectedSlot = mc.player.getInventory().selectedSlot;

            mc.interactionManager.clickSlot(handler.syncId, containerSlot, selectedSlot, SlotActionType.SWAP, mc.player);

            invSlots = new int[]{containerSlot, selectedSlot};
            return true;
        }
        return false;
    }

    public static void invSwapBack() {
        if (invSlots == null || invSlots.length < 2) return;
        ScreenHandler handler = mc.player.currentScreenHandler;

        mc.interactionManager.clickSlot(handler.syncId, invSlots[0], invSlots[1], SlotActionType.SWAP, mc.player);
    }

    public static void moveItem(int fromIdx, int toIdx) {
        int containerSlot = fromIdx;
        if (fromIdx < 9) containerSlot += 36;
        else if (fromIdx == 40) containerSlot = 45;

        if (fromIdx < 9) {
            mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, toIdx, fromIdx, SlotActionType.SWAP, mc.player);
        } else {
            mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, containerSlot, 0, SlotActionType.PICKUP, mc.player);
            mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, toIdx, 0, SlotActionType.PICKUP, mc.player);
            mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, containerSlot, 0, SlotActionType.PICKUP, mc.player);
        }
    }

    public static void dropHand() {
        if (!mc.player.currentScreenHandler.getCursorStack().isEmpty())
            mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, ScreenHandler.EMPTY_SPACE_SLOT_INDEX, 0, SlotActionType.PICKUP, mc.player);
    }
}
