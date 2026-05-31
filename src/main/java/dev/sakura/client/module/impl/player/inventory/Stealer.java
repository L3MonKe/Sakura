package dev.sakura.client.module.impl.player.inventory;

import com.google.common.collect.Lists;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.mixin.accessor.IAbstractFurnaceScreenHandler;
import dev.sakura.client.mixin.accessor.IBrewingStandScreenHandler;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.player.EnchantmentUtil;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.MultiBoolValue;
import dev.sakura.client.values.impl.NumberValue;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.*;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.screen.sync.ItemStackHash;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Stealer extends Module {

    private enum PickMode {
        ClickSlot,
        Packet
    }

    public Stealer() {
        super("Stealer", "箱子小偷", Category.Player);
    }

    // private final EnumValue<PickMode> pickMode = new EnumValue<>("PickMode", "拾取模式", PickMode.ClickSlot);
    private final NumberValue<Integer> openDelay = new NumberValue<>("OpenDelay", "打开延迟", 50, 0, 1000, 10);
    private final NumberValue<Integer> stealDelay = new NumberValue<>("StealDelay", "偷取延迟", 0, 0, 1000, 10);
    private final NumberValue<Integer> closeDelay = new NumberValue<>("CloseDelay", "关闭延迟", 50, 0, 1000, 10);
    // private final MultiBoolValue container = new MultiBoolValue("Interactive Container", "交互容器", List.of(
    //         new BoolValue("Chest", "箱子", true),
    //         new BoolValue("Furnace", "熔炉", true),
    //         new BoolValue("BlastFurnace", "高炉", false),
    //         new BoolValue("SmokerFurnace", "烟熏炉", false),
    //         new BoolValue("BrewingStand", "酿造台", false)
    // ));
    private final BoolValue randomiseTakingItem = new BoolValue("RandTake", "随机拿取", false);
    // private final MultiBoolValue customisedItems = new MultiBoolValue("CustomisedItems", "自定义物品", List.of(
    //         new BoolValue("Slime Ball", "粘液球", true),
    //         new BoolValue("Fire Charge", "火焰弹", true),
    //         new BoolValue("Totem Of Undying", "不死图腾", true),
    //         new BoolValue("Golden Apple", "金苹果", true),
    //         new BoolValue("Enchanted Golden Apple", "附魔金苹果", true),
    //         new BoolValue("Fishing Rod", "钓鱼竿", true),
    //         new BoolValue("Snow Ball", "雪球", true),
    //         new BoolValue("Egg", "鸡蛋", true),
    //         new BoolValue("Coal", "煤炭", true),
    //         new BoolValue("Iron Ingot", "铁锭", true),
    //         new BoolValue("Gold Ingot", "金锭", true),
    //         new BoolValue("Diamond", "钻石", true),
    //         new BoolValue("Emerald", "绿宝石", true),
    //         new BoolValue("Nether Star", "下界之星", true),
    //         new BoolValue("Bucket", "桶", true),
    //         new BoolValue("Water Bucket", "水桶", true),
    //         new BoolValue("Lava Bucket", "岩浆桶", true),
    //         new BoolValue("Milk Bucket", "牛奶桶", true),
    //         new BoolValue("Ender Pearl", "末影珍珠", true),
    //         new BoolValue("Arrow", "箭矢", true),
    //         new BoolValue("Shears", "剪刀", true),
    //         new BoolValue("Compass", "指南针", true),
    //         new BoolValue("Stick", "木棍", true),
    //         new BoolValue("Experience Bottle", "经验瓶", true),
    //         new BoolValue("Elytra", "鞘翅", true),
    //         new BoolValue("Book", "书", true),
    //         new BoolValue("Enchanted Book", "附魔书", true),
    //         new BoolValue("Shield", "盾牌", true),
    //         new BoolValue("End Crystal", "末影水晶", true),
    //         new BoolValue("Consumable", "可食用", true)
    // ));
    private final BoolValue disableInLobby = new BoolValue("DisableInLobby", "大厅中禁用", false);

    private final List<Integer> slots = new ArrayList<>();
    private final TimerUtil openTimer = new TimerUtil();
    private final TimerUtil stealTimer = new TimerUtil();
    private final TimerUtil closeTimer = new TimerUtil();
    private boolean stealing = false;
    private boolean opened = false;
    private boolean randomised = false;
    private boolean canSteal = false;

    @Override
    public void onEnable() {
        opened = false;
    }

    public boolean isWorking() {
        return stealing;
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (mc.player == null || mc.world == null) return;

        canSteal = false;
        if (disableInLobby.get() && isInLobby()) {
            return;
        }

        ScreenHandler screenHandler = mc.player.currentScreenHandler;
        if (!isVanillaChest(mc.currentScreen)) return;

        if (inScreenHandler(screenHandler)) {
            if (!opened) {
                openTimer.reset();
                stealTimer.reset();
                closeTimer.reset();
                randomised = false;
                opened = true;
                stealing = true;
                return;
            }

            if (!openTimer.passedMillise(openDelay.get())) {
                return;
            }

            Inventory inventory;
            switch (screenHandler) {
                case GenericContainerScreenHandler handler -> inventory = handler.getInventory();
                case AbstractFurnaceScreenHandler handler -> inventory = ((IAbstractFurnaceScreenHandler) handler).getInventory();
                case BrewingStandScreenHandler handler -> inventory = ((IBrewingStandScreenHandler) handler).getInventory();
                default -> {
                    return;
                }
            }

            if (!randomised || !randomiseTakingItem.get()) {
                slots.clear();
                for (int slot = 0; slot < inventory.size(); slot++) {
                    slots.add(slot);
                }
            }

            if (randomiseTakingItem.get() && !randomised) {
                Collections.shuffle(slots);
                randomised = true;
            }

            for (int slotID = 0; slotID < inventory.size(); slotID++) {
                Slot slot = screenHandler.getSlot(slotID);

                if (!slot.hasStack()) {
                    continue;
                }

                if (playerInventoryHasEmptySlot()) {
                    canSteal = true;
                }
            }

            if (canSteal) {
                takeItems(screenHandler);
                closeTimer.reset();
            }

            if (!canSteal) {
                if (closeTimer.passedMillise(closeDelay.get()) || closeDelay.get() == 0) {
                    if (mc.player.currentScreenHandler != null) {
                        mc.player.closeHandledScreen();
                        stealing = false;
                    }
                }
            }
            return;
        } else {
            randomised = false;
        }

        opened = false;
        openTimer.reset();
        stealTimer.reset();
        closeTimer.reset();
    }

    private void takeItems(ScreenHandler screenHandler) {
        if (mc.interactionManager == null || mc.player == null || mc.getNetworkHandler() == null) return;

        int delay = stealDelay.get();

        Inventory inventory;
        switch (screenHandler) {
            case GenericContainerScreenHandler handler -> inventory = handler.getInventory();
            case AbstractFurnaceScreenHandler handler -> inventory = ((IAbstractFurnaceScreenHandler) handler).getInventory();
            case BrewingStandScreenHandler handler -> inventory = ((IBrewingStandScreenHandler) handler).getInventory();
            default -> {
                return;
            }
        }

        for (int slotID = 0; slotID < inventory.size(); slotID++) {
            int currentSlot = slots.get(slotID);

            Slot slot = screenHandler.getSlot(currentSlot);

            if (!slot.hasStack()) {
                continue;
            }

            if (playerInventoryHasEmptySlot()) {
                if (stealTimer.passedMillise(delay) || delay == 0) {
                    mc.interactionManager.clickSlot(screenHandler.syncId, currentSlot, 0, SlotActionType.QUICK_MOVE, mc.player);
                    stealTimer.reset();
                }
            }
        }
    }

    private boolean inScreenHandler(ScreenHandler screenHandler) {
        if (screenHandler instanceof GenericContainerScreenHandler) {
            return true;
        }
        if (screenHandler instanceof FurnaceScreenHandler) {
            return true;
        }
        return false;
    }

    private boolean isVanillaChest(Screen scr) {
        if (scr == null) return false;
        Text titleText = scr.getTitle();
        String formattedName = titleText.getString().toLowerCase().trim();
        String vanillaChestName = Text.translatable("container.chest").getString().toLowerCase().trim();
        return formattedName.equals(vanillaChestName)
                || formattedName.equalsIgnoreCase("low")
                || formattedName.equalsIgnoreCase("chest");
    }

    private boolean isInLobby() {
        if (mc.world == null) return true;
        Iterable<Entity> entities = mc.world.getEntities();
        for (Entity entity : entities) {
            if (entity != null && entity.getName().getString().contains("\u00a7e\u00a7lCLICK TO PLAY")) {
                return true;
            }
        }
        return mc.player.getInventory().getStack(8) != null && mc.player.getInventory().getStack(8).getItem() == Items.NETHER_STAR && mc.player.getInventory().getStack(0) != null && mc.player.getInventory().getStack(0).getItem() == Items.COMPASS;
    }

    private boolean playerInventoryHasEmptySlot() {
        for (int slotID = 0; slotID < mc.player.getInventory().size(); ++slotID) {
            if (slotID == 39 || slotID == 38 || slotID == 37 || slotID == 36 || slotID == 40)
                continue;

            ItemStack itemStack = mc.player.getInventory().getStack(slotID);

            if (itemStack == null) return true;
            if (itemStack.isEmpty()) return true;
        }

        return false;
    }

    private boolean isContainerUsefulItem(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.isEmpty()) return false;

        Item item = itemStack.getItem();

        if (InvHelper.isArmor(itemStack)) {
            var equippable = itemStack.get(DataComponentTypes.EQUIPPABLE);
            if (equippable == null) return false;
            return InvHelper.getProtection(itemStack) > InvHelper.getBestArmorScore(equippable.slot());
        }
        if (item instanceof CrossbowItem) return InvHelper.getCrossbowScore(itemStack) > InvHelper.getBestCrossbowScore();
        if (item instanceof BowItem) return true;
        if (item instanceof AxeItem) return getAxeScore(itemStack) > InvHelper.getBestAxeScore();
        if (itemStack.isIn(ItemTags.PICKAXES)) return InvHelper.getToolScore(itemStack) > InvHelper.getBestPickaxeScore();
        if (item instanceof ShovelItem) return InvHelper.getBestShovel() == itemStack;
        if (item instanceof HoeItem) return true;
        if (itemStack.isIn(ItemTags.SWORDS)) return InvHelper.getSwordDamage(itemStack) > InvHelper.getBestSwordDamage();

        if (item instanceof PotionItem) return true;
        if (item instanceof PlayerHeadItem) return true;

        if (item instanceof BlockItem) {
            Block block = Block.getBlockFromItem(item);
            if (block instanceof AirBlock) return false;
            if (block instanceof StairsBlock) return false;
            if (block instanceof SlabBlock) return false;
            if (block instanceof FenceBlock) return false;
            if (block instanceof FenceGateBlock) return false;
            if (block instanceof DoorBlock) return false;
            if (block instanceof TrapdoorBlock) return false;
            if (block instanceof PressurePlateBlock) return false;
            if (block instanceof ButtonBlock) return false;
            if (block instanceof WallBlock) return false;
            if (block instanceof CarpetBlock) return false;
            if (block instanceof ConcretePowderBlock) return false;
            if (block instanceof TintedGlassBlock) return false;
            if (block instanceof StainedGlassBlock) return false;
            if (block instanceof StainedGlassPaneBlock) return false;
            if (block instanceof BannerBlock) return false;
            if (block instanceof LeavesBlock) return false;
            if (block instanceof SaplingBlock) return false;
            if (block instanceof MushroomBlock) return false;
            if (block instanceof MushroomPlantBlock) return false;
            if (block instanceof FlowerBlock) return false;
            if (block instanceof ComposterBlock) return false;
            if (block instanceof NoteBlock) return false;
            if (block instanceof JukeboxBlock) return false;
            if (block instanceof SignBlock) return false;
            if (block == Blocks.IRON_BARS) return false;
            if (block == Blocks.IRON_CHAIN) return false;
            if (block == Blocks.GLASS_PANE) return false;
            if (block == Blocks.DIRT_PATH) return false;
            if (block == Blocks.FARMLAND) return false;
            if (block == Blocks.SNOW) return false;
            if (block == Blocks.AMETHYST_CLUSTER) return false;
            if (block == Blocks.MANGROVE_ROOTS) return false;
            if (block == Blocks.SUGAR_CANE) return false;
            if (block == Blocks.CACTUS) return false;
            if (block == Blocks.LILY_PAD) return false;

            return true;
        }

        if (itemStack.getItem() == Items.SLIME_BALL) return true;
        if (itemStack.getItem() == Items.FIRE_CHARGE)
            return true;
        if (itemStack.getItem() == Items.TOTEM_OF_UNDYING)
            return true;
        if (itemStack.getItem() == Items.GOLDEN_APPLE)
            return true;
        if (itemStack.getItem() == Items.ENCHANTED_GOLDEN_APPLE)
            return true;
        if (itemStack.getItem() == Items.FISHING_ROD)
            return true;
        if (itemStack.getItem() == Items.SNOWBALL) return true;
        if (itemStack.getItem() == Items.EGG) return true;
        if (itemStack.getItem() == Items.COAL) return true;
        if (itemStack.getItem() == Items.IRON_INGOT) return true;
        if (itemStack.getItem() == Items.GOLD_INGOT) return true;
        if (itemStack.getItem() == Items.DIAMOND) return true;
        if (itemStack.getItem() == Items.EMERALD) return true;
        if (itemStack.getItem() == Items.NETHER_STAR)
            return true;
        if (itemStack.getItem() == Items.BUCKET) return true;
        if (itemStack.getItem() == Items.WATER_BUCKET)
            return true;
        if (itemStack.getItem() == Items.LAVA_BUCKET)
            return true;
        if (itemStack.getItem() == Items.MILK_BUCKET)
            return true;
        if (itemStack.getItem() == Items.ENDER_PEARL)
            return true;
        if (itemStack.getItem() == Items.ARROW) return true;
        if (itemStack.getItem() == Items.SHEARS) return true;
        if (itemStack.getItem() == Items.COMPASS) return true;
        if (itemStack.getItem() == Items.STICK) return true;
        if (itemStack.getItem() == Items.EXPERIENCE_BOTTLE)
            return true;
        if (itemStack.getItem() == Items.ELYTRA) return true;
        if (itemStack.getItem() == Items.BOOK) return true;
        if (itemStack.getItem() == Items.ENCHANTED_BOOK)
            return true;
        if (itemStack.getItem() == Items.SHIELD) return true;
        if (itemStack.getItem() == Items.END_CRYSTAL)
            return true;
        if (itemStack.getItem().getComponents().contains(DataComponentTypes.FOOD)) {
            return true;
        }

        return false;
    }

    private float getAxeScore(ItemStack stack) {
        float valence = InvHelper.getToolScore(stack);

        if (InvHelper.isGodItem(stack)) {
            return valence * 1000;
        }

        int itemEnchantmentLevel = EnchantmentUtil.getEnchantmentLevel(stack, net.minecraft.enchantment.Enchantments.SHARPNESS);

        if (itemEnchantmentLevel > 0) {
            valence += 0.5f * itemEnchantmentLevel + 0.5f;
        }

        return valence;
    }
}
