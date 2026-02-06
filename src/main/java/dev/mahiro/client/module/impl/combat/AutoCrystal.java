package dev.mahiro.client.module.impl.combat;

import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.utils.time.TimerUtil;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.EnumValue;
import dev.mahiro.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class AutoCrystal extends Module {

    private final EnumValue<PlacementMode> placementMode = new EnumValue<>("Placement Mode", "放置模式", PlacementMode.RClick);
    private final NumberValue<Double> placeCooldown = new NumberValue<>("Place Cooldown", "放置冷却", 50.0, 0.0, 1000.0, 1.0);
    private final NumberValue<Double> popCooldown = new NumberValue<>("Pop Cooldown", "炸水晶冷却", 0.0, 0.0, 1000.0, 1.0);
    private final BoolValue onlyOwnCrystal = new BoolValue("Only Own Crystal", "仅炸自己", false);
    private final BoolValue preserveItems = new BoolValue("No Loot Pop", "保护掉落物", true);
    private final NumberValue<Double> lootProtectRadiusX = new NumberValue<>("Protect X", "保护半径X", 8.0, 0.0, 16.0, 0.1);
    private final NumberValue<Double> lootProtectRadiusY = new NumberValue<>("Protect Y", "保护半径Y", 8.0, 0.0, 16.0, 0.1);
    private final NumberValue<Double> lootProtectRadiusZ = new NumberValue<>("Protect Z", "保护半径Z", 8.0, 0.0, 16.0, 0.1);
    private final EnumValue<RandomizationMode> randomization = new EnumValue<>("Randomization", "随机化", RandomizationMode.None);

    private final TimerUtil timerUtil = new TimerUtil();
    private final Random random = new Random();
    private boolean playerPlacedCrystal = false;

    public AutoCrystal() {
        super("AutoCrystal", "自动水晶 (Ghost)", Category.Combat);
    }

    @Override
    public void onEnable() {
        timerUtil.reset();
        playerPlacedCrystal = false;
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;
        endCrystalTrigger();
        trackPlacedCrystals();
        placeCrystal();
    }

    private void endCrystalTrigger() {
        Entity target = raycastEndCrystal(5);
        if (target == null)
            return;

        if (onlyOwnCrystal.get() && !playerPlacedCrystal)
            return;

        if (preserveItems.get() && itemNearby(target, 6))
            return;

        if (timerUtil.passedMS(getCooldownValueWithRandomization(popCooldown.get()))) {
            mc.interactionManager.attackEntity(mc.player, target);
            mc.player.swingHand(Hand.MAIN_HAND);
            timerUtil.reset();
            playerPlacedCrystal = false;
        }
    }

    private double getCooldownValueWithRandomization(double baseValue) {
        int randomizationValue = 0;
        switch (randomization.get()) {
            case Small:
                if (random.nextBoolean()) randomizationValue = random.nextInt(11) - 5; // -5 to 5
                break;
            case Medium:
                if (random.nextBoolean()) randomizationValue = random.nextInt(31) - 15; // -15 to 15
                break;
            case Large:
                if (random.nextBoolean()) randomizationValue = random.nextInt(51) - 25; // -25 to 25
                break;
            case None:
            default:
                break;
        }
        return Math.max(0, baseValue + randomizationValue);
    }

    private void trackPlacedCrystals() {
        if (mc.player.getMainHandStack().getItem() == Items.END_CRYSTAL && mc.options.useKey.isPressed()) {
            playerPlacedCrystal = true;
        }
    }

    private Entity raycastEndCrystal(double range) {
        Vec3d cameraPos = Vec3d.of(mc.gameRenderer.getCamera().getBlockPos());
        Vec3d viewVector = mc.player.getRotationVecClient();
        Vec3d extendedPoint = cameraPos.add(viewVector.x * range, viewVector.y * range, viewVector.z * range);

        for (Entity entity : mc.world.getEntities()) {
            if (entity instanceof EndCrystalEntity) {
                if (entity.getBoundingBox().expand(0.3).intersects(cameraPos, extendedPoint)) {
                    return entity;
                }
            }
        }
        return null;
    }

    private boolean itemNearby(Entity entity, double range) {
        Box boundingBox = new Box(
                entity.getBlockPos().getX() - lootProtectRadiusX.get(),
                entity.getBlockPos().getY() - lootProtectRadiusY.get(),
                entity.getBlockPos().getZ() - lootProtectRadiusZ.get(),
                entity.getBlockPos().getX() + lootProtectRadiusX.get(),
                entity.getBlockPos().getY() + lootProtectRadiusY.get(),
                entity.getBlockPos().getZ() + lootProtectRadiusZ.get()
        );

        for (Entity nearbyEntity : mc.world.getOtherEntities(null, boundingBox)) {
            if (nearbyEntity instanceof ItemEntity) {
                if (isPreciousItem(((ItemEntity) nearbyEntity).getStack().getItem())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isPreciousItem(Item item) {
        return item == Items.DIAMOND ||
                item == Items.DIAMOND_BLOCK ||
                item == Items.DIAMOND_SWORD ||
                item == Items.DIAMOND_PICKAXE ||
                item == Items.DIAMOND_AXE ||
                item == Items.DIAMOND_SHOVEL ||
                item == Items.DIAMOND_HOE ||
                item == Items.DIAMOND_HELMET ||
                item == Items.DIAMOND_CHESTPLATE ||
                item == Items.DIAMOND_LEGGINGS ||
                item == Items.DIAMOND_BOOTS ||
                item == Items.NETHERITE_INGOT ||
                item == Items.NETHERITE_BLOCK ||
                item == Items.NETHERITE_SWORD ||
                item == Items.NETHERITE_PICKAXE ||
                item == Items.NETHERITE_AXE ||
                item == Items.NETHERITE_SHOVEL ||
                item == Items.NETHERITE_HOE ||
                item == Items.NETHERITE_HELMET ||
                item == Items.NETHERITE_CHESTPLATE ||
                item == Items.NETHERITE_LEGGINGS ||
                item == Items.NETHERITE_BOOTS;
    }

    private void placeCrystal() {
        if (placementMode.is(PlacementMode.RClick) && mc.options.useKey.isPressed() && mc.player.getInventory().getSelectedSlot() == getCrystalSlot() && (timerUtil.passedMS(getCooldownValueWithRandomization(placeCooldown.get())))) {
            placeBlock();
            timerUtil.reset();
        } else if (placementMode.is(PlacementMode.Look) && isObsidianOrBedrockInCrosshair() && mc.player.getInventory().getSelectedSlot() == getCrystalSlot() && (timerUtil.passedMS(getCooldownValueWithRandomization(placeCooldown.get())))) {
            placeBlock();
            timerUtil.reset();
        }
    }

    private boolean isObsidianOrBedrockInCrosshair() {
        if (mc.crosshairTarget != null && mc.crosshairTarget.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult) mc.crosshairTarget).getBlockPos();
            BlockState blockState = mc.world.getBlockState(blockPos);
            return blockState.getBlock() == Blocks.OBSIDIAN || blockState.getBlock() == Blocks.BEDROCK;
        }
        return false;
    }

    private void placeBlock() {
        if (mc.crosshairTarget == null || mc.crosshairTarget.getType() != HitResult.Type.BLOCK) return;
        BlockHitResult hitResult = (BlockHitResult) mc.crosshairTarget;
        mc.player.swingHand(mc.player.getActiveHand());
        mc.getNetworkHandler().sendPacket(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, hitResult, 0));
    }

    private int getCrystalSlot() {
        for (int i = 0; i <= 8; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.getItem() == Items.END_CRYSTAL) {
                return i;
            }
        }
        return -1;
    }

    private enum PlacementMode {
        RClick, Look
    }

    private enum RandomizationMode {
        None, Small, Medium, Large
    }
}
