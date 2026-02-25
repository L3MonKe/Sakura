package dev.sakura.client.utils.combat;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.BlockView;
import net.minecraft.world.Difficulty;
import net.minecraft.world.RaycastContext;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.Objects;

import static dev.sakura.client.Sakura.mc;

public class DamageUtil {
    public static float calculateCrystalDamage(LivingEntity entity, Vec3d pos) {
        return calculateCrystalDamage(entity, entity.getBoundingBox(), pos, null, false);
    }

    public static float calculateCrystalDamage(LivingEntity entity, Box box, Vec3d pos, BlockPos ignorePos, boolean ignoreTerrain) {
        return (float) explosionDamage(entity, box, pos, ignorePos, null, ignoreTerrain, 6);
    }

    public static float calculateAnchorDamage(LivingEntity entity, BlockPos pos) {
        return calculateAnchorDamage(entity, entity.getBoundingBox(), pos, pos, false);
    }

    public static float calculateAnchorDamage(LivingEntity entity, Box box, BlockPos pos, BlockPos ignorePos, boolean ignoreTerrain) {
        return (float) explosionDamage(entity, box, Vec3d.ofCenter(pos), ignorePos, null, ignoreTerrain, 5);
    }

    private static double explosionDamage(LivingEntity entity, Box box, Vec3d pos, BlockPos ignorePos, BlockPos obbyPos, boolean ignoreTerrain, double strength) {
        if (box == null) return 0;

        double damage = getBaseDamage(box, pos, ignorePos, obbyPos, ignoreTerrain, strength);

        damage = difficultyDamage(damage);
        damage = applyArmor(entity, damage);
        damage = applyResistance(entity, damage);
        damage = applyProtection(entity, damage, true);

        return damage;
    }

    public static double getBaseDamage(Box box, Vec3d pos, BlockPos ignorePos, BlockPos obbyPos, boolean ignoreTerrain, double strength) {
        double q = strength * 2;
        // 脚下位置
        Vec3d feet = new Vec3d((box.minX + box.maxX) / 2.0, box.minY, (box.minZ + box.maxZ) / 2.0);
        double dist = feet.distanceTo(pos) / q;

        if (dist > 1.0) return 0;

        double aa = getExposure(pos, box, ignorePos, obbyPos, ignoreTerrain);
        double ab = (1.0 - dist) * aa;

        return (int) ((ab * ab + ab) * 3.5 * q + 1.0);
    }

    public static double difficultyDamage(double damage) {
        if (mc.world == null) return damage;
        Difficulty difficulty = mc.world.getDifficulty();
        if (difficulty == Difficulty.PEACEFUL) return 0;
        if (difficulty == Difficulty.EASY) return Math.min(damage / 2 + 1, damage);
        if (difficulty == Difficulty.HARD) return damage * 1.5;

        return damage;
    }

    public static double applyArmor(LivingEntity entity, double damage) {
        double armor = entity.getArmor();
        double f = 2 + entity.getAttributeValue(EntityAttributes.ARMOR_TOUGHNESS) / 4;

        return damage * (1 - MathHelper.clamp(armor - damage / f, armor * 0.2, 20) / 25);
    }

    public static double applyResistance(LivingEntity entity, double damage) {
        if (entity.hasStatusEffect(StatusEffects.RESISTANCE)) {
            int amplifier = Objects.requireNonNull(entity.getStatusEffect(StatusEffects.RESISTANCE)).getAmplifier();
            return Math.max(damage * (25 - (amplifier + 1) * 5) / 25, 0);
        }
        return damage;
    }

    public static double applyProtection(LivingEntity entity, double damage, boolean explosions) {
        int i = getProtectionAmount(entity.getArmorItems(), explosions);
        if (i > 0)
            damage *= (1 - MathHelper.clamp(i, 0f, 20f) / 25);

        return damage;
    }

    public static int getProtectionAmount(Iterable<ItemStack> equipment, boolean explosion) {
        MutableInt mint = new MutableInt();

        for (ItemStack stack : equipment) {
            if (stack.isEmpty()) continue;

            ItemEnchantmentsComponent enchantments = stack.get(DataComponentTypes.ENCHANTMENTS);
            if (enchantments == null) continue;

            enchantments.getEnchantments().forEach(entry -> {
                int level = enchantments.getLevel(entry);
                if (entry.matchesKey(Enchantments.PROTECTION))
                    mint.add(level);
                else if (explosion && entry.matchesKey(Enchantments.BLAST_PROTECTION))
                    mint.add(level * 2);
            });
        }

        return mint.intValue();
    }

    public static double getExposure(Vec3d source, Box box, BlockPos ignorePos, BlockPos obbyPos, boolean ignoreTerrain) {
        double lx = box.getLengthX();
        double ly = box.getLengthY();
        double lz = box.getLengthZ();

        double deltaX = 1 / (lx * 2 + 1);
        double deltaY = 1 / (ly * 2 + 1);
        double deltaZ = 1 / (lz * 2 + 1);

        double offsetX = (1 - Math.floor(1 / deltaX) * deltaX) / 2;
        double offsetZ = (1 - Math.floor(1 / deltaZ) * deltaZ) / 2;

        double stepX = deltaX * lx;
        double stepY = deltaY * ly;
        double stepZ = deltaZ * lz;

        if (stepX < 0 || stepY < 0 || stepZ < 0) return 0;

        float i = 0;
        float j = 0;

        for (double x = box.minX + offsetX, maxX = box.maxX + offsetX; x <= maxX; x += stepX) {
            for (double y = box.minY; y <= box.maxY; y += stepY) {
                for (double z = box.minZ + offsetZ, maxZ = box.maxZ + offsetZ; z <= maxZ; z += stepZ) {
                    Vec3d vec3d = new Vec3d(x, y, z);
                    if (raycast(source, vec3d, ignorePos, obbyPos, ignoreTerrain).getType() == HitResult.Type.MISS) ++i;
                    ++j;
                }
            }
        }

        return i / j;
    }

    private static BlockHitResult raycast(Vec3d start, Vec3d end, BlockPos ignorePos, BlockPos obbyPos, boolean ignoreTerrain) {
        RaycastContext context = new RaycastContext(start, end, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, mc.player);

        return BlockView.raycast(context.getStart(), context.getEnd(), context, (contextx, pos) -> {
            BlockState blockState;

            if (pos.equals(obbyPos))
                blockState = Blocks.OBSIDIAN.getDefaultState();
            else if (pos.equals(ignorePos))
                blockState = Blocks.AIR.getDefaultState();
            else {
                BlockState state = mc.world.getBlockState(pos);
                if (ignoreTerrain && state.getBlock().getBlastResistance() < 600) {
                    blockState = Blocks.AIR.getDefaultState();
                } else {
                    blockState = state;
                }
            }

            Vec3d vec3d = contextx.getStart();
            Vec3d vec3d2 = contextx.getEnd();

            return mc.world.raycastBlock(vec3d, vec3d2, pos, contextx.getBlockShape(blockState, mc.world, pos), blockState);
        }, (contextx) -> {
            Vec3d vec3d = contextx.getStart().subtract(contextx.getEnd());
            return BlockHitResult.createMissed(contextx.getEnd(), Direction.getFacing(vec3d.x, vec3d.y, vec3d.z), BlockPos.ofFloored(contextx.getEnd()));
        });
    }
}
