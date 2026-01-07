package dev.sakura.client.module.impl.combat;

import dev.sakura.client.events.EventType;
import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.events.packet.PacketEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.player.InvUtil;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec3d;

public class AutoTotem extends Module {
    public static AutoTotem INSTANCE;

    private final EnumValue<OffhandItem> item = new EnumValue<>("Item", "物品", OffhandItem.TOTEM);
    private final NumberValue<Double> health = new NumberValue<>("Health", "血量", 14.0, 0.0, 20.0, 0.5);
    private final BoolValue offhandGapple = new BoolValue("OffhandGapple", "副手金苹果", true);
    private final BoolValue crapple = new BoolValue("Crapple", "普通金苹果", true, offhandGapple::get);
    private final BoolValue lethal = new BoolValue("Lethal", "致命保护", false, () -> item.get() != OffhandItem.TOTEM);
    private final BoolValue mainhandTotem = new BoolValue("MainhandTotem", "主手图腾", false);
    private final NumberValue<Integer> totemSlot = new NumberValue<>("TotemSlot", "图腾槽位", 1, 1, 9, 1, mainhandTotem::get);
    private final BoolValue alternative = new BoolValue("Alternative", "替代模式", false);

    private int lastHotbarSlot = -1;
    private Item lastHotbarItem;
    private Item offhandItem;
    private boolean replacing;
    private long replaceTime;
    private final TimerUtil mainhandSwapTimer = new TimerUtil();
    private boolean totemInMainhand;

    public AutoTotem() {
        super("AutoTotem", "自动图腾", Category.Combat);
        INSTANCE = this;
    }

    @Override
    protected void onEnable() {
        mainhandSwapTimer.reset();
    }

    @Override
    protected void onDisable() {
        lastHotbarSlot = -1;
        lastHotbarItem = null;
        offhandItem = null;
        totemInMainhand = false;
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck() || mc.interactionManager == null) return;

        setSuffix(String.valueOf(countTotems()));

        if (mainhandTotem.get() && mainhandSwapTimer.delay(4.0f)) {
            handleMainhandTotem();
        } else {
            totemInMainhand = false;
        }

        offhandItem = item.get().getItem();
        if (checkLethal()) {
            offhandItem = Items.TOTEM_OF_UNDYING;
        } else {
            Item mainHandItem = mc.player.getMainHandStack().getItem();
            if (offhandGapple.get() && mc.options.useKey.isPressed()
                    && (mainHandItem instanceof SwordItem || mainHandItem instanceof TridentItem || mainHandItem instanceof AxeItem)
                    && getPlayerHealth() >= health.get()) {
                if (mc.crosshairTarget instanceof BlockHitResult result) {
                    BlockState interactBlock = mc.world.getBlockState(result.getBlockPos());
                    if (!isSneakBlock(interactBlock)) {
                        offhandItem = getGoldenAppleType();
                    }
                } else {
                    offhandItem = getGoldenAppleType();
                }
            }
        }

        if (mc.player.getOffHandStack().getItem() == offhandItem) return;

        int n = 35;
        if (lastHotbarSlot != -1 && lastHotbarItem != null) {
            ItemStack stack = mc.player.getInventory().getStack(lastHotbarSlot);
            if (stack.getItem().equals(offhandItem) && lastHotbarItem.equals(mc.player.getOffHandStack().getItem())) {
                int tmp = lastHotbarSlot;
                lastHotbarSlot = -1;
                lastHotbarItem = null;
                n = tmp;
            }
        }

        while (n >= 0) {
            if (mc.player.getInventory().getStack(n).getItem() == offhandItem) {
                if (n < 9) {
                    lastHotbarItem = offhandItem;
                    lastHotbarSlot = n;
                }
                int slot = n < 9 ? n + 36 : n;
                replacing = true;
                if (alternative.get()) {
                    mc.interactionManager.clickSlot(0, slot, 40, SlotActionType.SWAP, mc.player);
                    replacing = false;
                } else {
                    if (mc.player.currentScreenHandler.getCursorStack().getItem() != offhandItem) {
                        mc.interactionManager.clickSlot(0, slot, 0, SlotActionType.PICKUP, mc.player);
                    }
                    if (mc.player.currentScreenHandler.getCursorStack().getItem() == offhandItem) {
                        mc.interactionManager.clickSlot(0, 45, 0, SlotActionType.PICKUP, mc.player);
                    }
                    replacing = false;
                    if (!mc.player.currentScreenHandler.getCursorStack().isEmpty() && mc.player.getOffHandStack().getItem() == offhandItem) {
                        mc.interactionManager.clickSlot(0, slot, 0, SlotActionType.PICKUP, mc.player);
                        return;
                    }
                }
            }
            n--;
        }
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (nullCheck()) return;
        if (event.getType() != EventType.RECEIVE) return;

        if (event.getPacket() instanceof ScreenHandlerSlotUpdateS2CPacket packet
                && packet.getSlot() == 45 && offhandItem == Items.TOTEM_OF_UNDYING) {
            if (mc.player.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING || !packet.getStack().isEmpty()) {
                return;
            }
            replaceTime = System.currentTimeMillis();
        }
    }

    private void handleMainhandTotem() {
        int totemSlotIndex = totemSlot.get() - 1;
        ItemStack totemSlotStack = mc.player.getInventory().getStack(totemSlotIndex);
        int adjustedSlot = totemSlotIndex + 36;

        if (totemSlotStack.getItem() != Items.TOTEM_OF_UNDYING) {
            for (int i = 35; i >= 0; i--) {
                if (mc.player.getInventory().getStack(i).getItem() == Items.TOTEM_OF_UNDYING) {
                    int slot = i < 9 ? i + 36 : i;
                    replacing = true;
                    if (alternative.get()) {
                        mc.interactionManager.clickSlot(0, slot, adjustedSlot, SlotActionType.SWAP, mc.player);
                    } else {
                        if (mc.player.currentScreenHandler.getCursorStack().getItem() != Items.TOTEM_OF_UNDYING) {
                            mc.interactionManager.clickSlot(0, slot, 0, SlotActionType.PICKUP, mc.player);
                        }
                        if (mc.player.currentScreenHandler.getCursorStack().getItem() == Items.TOTEM_OF_UNDYING) {
                            mc.interactionManager.clickSlot(0, adjustedSlot, 0, SlotActionType.PICKUP, mc.player);
                        }
                        if (!mc.player.currentScreenHandler.getCursorStack().isEmpty() && mc.player.getOffHandStack().getItem() == Items.TOTEM_OF_UNDYING) {
                            mc.interactionManager.clickSlot(0, slot, 0, SlotActionType.PICKUP, mc.player);
                        }
                    }
                    replacing = false;
                    break;
                }
            }
        }

        totemInMainhand = checkMainhandTotem();
        if (totemInMainhand) {
            for (int i = 0; i < 9; i++) {
                ItemStack stack = mc.player.getInventory().getStack(i);
                if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
                    InvUtil.swap(i, false);
                    break;
                }
            }
        }
    }

    private boolean checkLethal() {
        double playerHealth = getPlayerHealth();
        if (playerHealth <= health.get()) return true;
        if (lethal.get() && checkLethalCrystal(playerHealth)) return true;
        float fallDamage = computeFallDamage(mc.player.fallDistance);
        return fallDamage + 0.5f > mc.player.getHealth();
    }

    private boolean checkLethalCrystal(double playerHealth) {
        for (Entity e : mc.world.getEntities()) {
            if (e == null || !e.isAlive() || !(e instanceof EndCrystalEntity crystal)) continue;
            if (mc.player.squaredDistanceTo(e) > 144.0) continue;
            double potential = calculateCrystalDamage(crystal.getPos());
            if (playerHealth + 0.5 > potential) continue;
            return true;
        }
        return false;
    }

    private double calculateCrystalDamage(Vec3d crystalPos) {
        double distance = mc.player.getPos().distanceTo(crystalPos);
        if (distance > 12.0) return 0.0;
        double exposure = 1.0 - (distance / 12.0);
        double damage = (exposure * exposure + exposure) / 2.0 * 7.0 * 12.0 + 1.0;
        int protection = 0;
        for (ItemStack armor : mc.player.getArmorItems()) {
            if (armor.isEmpty()) continue;
            protection += 2;
        }
        damage = damage * (1.0 - (protection * 0.04));
        return damage;
    }

    private float computeFallDamage(float fallDistance) {
        if (mc.player.hasStatusEffect(StatusEffects.SLOW_FALLING)) return 0;
        float damage = fallDistance - 3.0f;
        if (mc.player.hasStatusEffect(StatusEffects.JUMP_BOOST)) {
            damage -= mc.player.getStatusEffect(StatusEffects.JUMP_BOOST).getAmplifier() + 1;
        }
        return Math.max(0, damage);
    }

    private Item getGoldenAppleType() {
        if (crapple.get() && hasItem(Items.GOLDEN_APPLE)
                && (mc.player.hasStatusEffect(StatusEffects.ABSORPTION) || !hasItem(Items.ENCHANTED_GOLDEN_APPLE))) {
            return Items.GOLDEN_APPLE;
        }
        return Items.ENCHANTED_GOLDEN_APPLE;
    }

    private boolean checkMainhandTotem() {
        if (mc.player.getMainHandStack().getItem() == Items.TOTEM_OF_UNDYING) return false;
        return checkLethalCrystal(getPlayerHealth());
    }

    private double getPlayerHealth() {
        return mc.player.getHealth() + mc.player.getAbsorptionAmount();
    }

    private int countTotems() {
        return InvUtil.find(Items.TOTEM_OF_UNDYING).count();
    }

    private boolean hasItem(Item item) {
        for (int i = 0; i < 36; i++) {
            if (mc.player.getInventory().getStack(i).getItem() == item) return true;
        }
        return false;
    }

    private boolean isSneakBlock(BlockState state) {
        return state.getBlock() instanceof net.minecraft.block.CraftingTableBlock
                || state.getBlock() instanceof net.minecraft.block.ChestBlock
                || state.getBlock() instanceof net.minecraft.block.EnderChestBlock
                || state.getBlock() instanceof net.minecraft.block.AnvilBlock
                || state.getBlock() instanceof net.minecraft.block.ShulkerBoxBlock
                || state.getBlock() instanceof net.minecraft.block.EnchantingTableBlock
                || state.getBlock() instanceof net.minecraft.block.FurnaceBlock
                || state.getBlock() instanceof net.minecraft.block.BrewingStandBlock
                || state.getBlock() instanceof net.minecraft.block.BeaconBlock
                || state.getBlock() instanceof net.minecraft.block.BedBlock;
    }

    public boolean isTotemInMainhand() {
        return totemInMainhand;
    }

    public boolean isReplacing() {
        return replacing;
    }

    public enum OffhandItem {
        TOTEM(Items.TOTEM_OF_UNDYING),
        GAPPLE(Items.ENCHANTED_GOLDEN_APPLE),
        CRYSTAL(Items.END_CRYSTAL);

        private final Item item;

        OffhandItem(Item item) {
            this.item = item;
        }

        public Item getItem() {
            return item;
        }
    }
}