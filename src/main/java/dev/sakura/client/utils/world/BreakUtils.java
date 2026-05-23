package dev.sakura.client.utils.world;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.player.AutoTool;
import net.minecraft.block.BlockState;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.Objects;

import static dev.sakura.client.Sakura.mc;

public class BreakUtils {
    public static float calcBlockBreakingDelta(BlockPos blockPos, BlockView world, BlockPos pos) {
        return calcBlockBreakingDelta(Objects.requireNonNull(mc.world).getBlockState(blockPos), world, pos);
    }

    public static float calcBlockBreakingDelta(BlockState state, BlockView world, BlockPos pos) {
        float f = state.getHardness(world, pos);
        if (f == -1.0F) {
            return 0.0F;
        } else {
            int i = canHarvest(state, Sakura.MODULES.getModule(AutoTool.class).isEnabled()) ? 30 : 100;
            return getBlockBreakingSpeed(state) / f / (float) i;
        }
    }

    public static float getInventoryBlockBreakingSpeed(BlockState block, boolean autoToolEnabled) {
        if (!autoToolEnabled) {
            return Objects.requireNonNull(mc.player).getInventory().getStack(mc.player.getInventory().getSelectedSlot()).getMiningSpeedMultiplier(block);
        } else {
            float bestSpeed = 1.0f;
            for (int i = 0; i <= 8; i++) {
                ItemStack stack = Objects.requireNonNull(mc.player).getInventory().getStack(i);
                if (stack.isEmpty()) continue;

                float speed = stack.getMiningSpeedMultiplier(block);
                if (speed > bestSpeed) {
                    bestSpeed = speed;
                }
            }
            return bestSpeed;
        }
    }

    public static float getBlockBreakingSpeed(BlockState block) {
        float f = getInventoryBlockBreakingSpeed(block, Sakura.MODULES.getModule(AutoTool.class).isEnabled());

        if (f > 1.0F) {
            f += (float) Objects.requireNonNull(mc.player).getAttributeValue(EntityAttributes.MINING_EFFICIENCY);
        }

        if (StatusEffectUtil.hasHaste(Objects.requireNonNull(mc.player))) {
            f *= 1.0F + (float) (StatusEffectUtil.getHasteAmplifier(mc.player) + 1) * 0.2F;
        }

        if (mc.player.hasStatusEffect(StatusEffects.MINING_FATIGUE)) {
            float var10000;
            switch (Objects.requireNonNull(mc.player.getStatusEffect(StatusEffects.MINING_FATIGUE)).getAmplifier()) {
                case 0 -> var10000 = 0.3F;
                case 1 -> var10000 = 0.09F;
                case 2 -> var10000 = 0.0027F;
                default -> var10000 = 8.1E-4F;
            }

            float g = var10000;
            f *= g;
        }

        f *= (float) mc.player.getAttributeValue(EntityAttributes.BLOCK_BREAK_SPEED);
        if (mc.player.isSubmergedIn(FluidTags.WATER)) {
            f *= (float) mc.player.getAttributeInstance(EntityAttributes.SUBMERGED_MINING_SPEED).getValue();
        }

        if (!mc.player.isOnGround()) {
            f /= 5.0F;
        }

        return f;
    }

    public static boolean canHarvest(BlockState state, boolean autoToolEnabled) {
        boolean suitAble;

        if (!autoToolEnabled) {
            suitAble = Objects.requireNonNull(mc.player).getMainHandStack().isSuitableFor(state);
        } else {
            suitAble = isAnyHotbarItemSuitable(state);
        }

        return !state.isToolRequired() || suitAble;
    }

    private static boolean isAnyHotbarItemSuitable(BlockState state) {
        if (mc.player == null) return false;

        for (int i = 0; i <= 8; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (!stack.isEmpty() && stack.isSuitableFor(state)) {
                return true;
            }
        }

        return false;
    }
}
