package dev.sakura.client.module.impl.combat;

import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;

public class SafeHotbar extends Module {

    private final NumberValue<Double> healthThreshold = new NumberValue<>("Health", "生命值阈值", 10.0, 0.0, 36.0, 0.5);
    private final NumberValue<Double> switchDelay = new NumberValue<>("Switch Delay", "切换延迟", 50.0, 0.0, 1000.0, 1.0);

    private final BoolValue checkCrystal = new BoolValue("Check Crystal", "检查水晶", true);
    private final NumberValue<Double> crystalRange = new NumberValue<>("Crystal Range", "水晶范围", 6.0, 0.0, 10.0, 0.1);

    private final BoolValue checkAnchor = new BoolValue("Check Anchor", "检查锚点", true);
    private final NumberValue<Double> anchorRange = new NumberValue<>("Anchor Range", "锚点范围", 6.0, 0.0, 10.0, 0.1);
    private final BoolValue onlyChargedAnchor = new BoolValue("Only Charged", "仅充能锚点", false);
    private final BoolValue checkObsidian = new BoolValue("Check Obsidian", "检查黑曜石遮挡", true);

    private final BoolValue antiFall = new BoolValue("Anti Fall", "防摔落", true);
    private final BoolValue offhandPop = new BoolValue("Offhand Pop", "副手炸腾切换", false);

    private final TimerUtil timerUtil = new TimerUtil();
    private Item previousOffhandItem = Items.AIR;

    public SafeHotbar() {
        super("SafeHotbar", "自动切图腾 (Ghost)", Category.Combat);
    }

    @Override
    public void onEnable() {
        timerUtil.reset();
        previousOffhandItem = mc.player != null ? mc.player.getOffHandStack().getItem() : Items.AIR;
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        checkEndCrystal();
        checkAnchor();
        checkHealth();
        checkFall();
        checkOffhandPop();
    }

    private void switchToTotem() {
        if (!timerUtil.passedMillise(switchDelay.get())) return;

        for (int i = 0; i < 9; i++) {
            if (mc.player.getInventory().getStack(i).getItem() == Items.TOTEM_OF_UNDYING) {
                if (mc.player.getInventory().getSelectedSlot() != i) {
                    mc.player.getInventory().setSelectedSlot(i);
                    timerUtil.reset();
                }
                return;
            }
        }
    }

    private void checkEndCrystal() {
        if (!checkCrystal.get()) return;

        double range = crystalRange.get();
        double pY = mc.player.getY();

        for (Entity entity : mc.world.getEntities()) {
            if (!(entity instanceof EndCrystalEntity)) continue;
            if (mc.player.distanceTo(entity) > range) continue;

            double yDiff = entity.getY() - pY;
            if (Math.abs(yDiff) <= range) {
                switchToTotem();
                return;
            }
        }
    }

    private void checkAnchor() {
        if (!checkAnchor.get()) return;

        double range = anchorRange.get();
        BlockPos pPos = mc.player.getBlockPos();
        int r = (int) Math.ceil(range);

        for (int x = -r; x <= r; x++) {
            for (int y = -r; y <= r; y++) {
                for (int z = -r; z <= r; z++) {
                    BlockPos pos = pPos.add(x, y, z);
                    if (Math.sqrt(pPos.getSquaredDistance(pos)) > range) continue;

                    BlockState state = mc.world.getBlockState(pos);
                    if (state.getBlock() != Blocks.RESPAWN_ANCHOR) continue;
                    if (onlyChargedAnchor.get() && state.get(RespawnAnchorBlock.CHARGES) == 0) continue;

                    if (checkObsidian.get() && isProtectedByObsidian(pos)) continue;

                    switchToTotem();
                    return;
                }
            }
        }
    }

    private boolean isProtectedByObsidian(BlockPos anchorPos) {
        return false;
    }

    private void checkHealth() {
        if (mc.player.getHealth() + mc.player.getAbsorptionAmount() <= healthThreshold.get()) {
            switchToTotem();
        }
    }

    private void checkFall() {
        if (!antiFall.get()) return;
        if (mc.player.isOnGround()) return;

        if (mc.player.fallDistance > 3.0f) {
            float damage = (float) (mc.player.fallDistance - 3.0f);
            if (damage >= mc.player.getHealth() + mc.player.getAbsorptionAmount()) {
                switchToTotem();
            }
        }
    }

    private void checkOffhandPop() {
        if (!offhandPop.get()) return;

        ItemStack currentOffhand = mc.player.getOffHandStack();
        if (previousOffhandItem == Items.TOTEM_OF_UNDYING && currentOffhand.isEmpty()) {
            switchToTotem();
        }
        previousOffhandItem = currentOffhand.getItem();
    }
}
