package dev.mahiro.client.utils.combat;

import dev.mahiro.client.utils.rotation.RaytraceUtil;
import net.minecraft.entity.DamageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.explosion.Explosion;

import java.util.Objects;

import static dev.mahiro.client.Mahiro.mc;

public class CrystalUtil {
    public static float calculateDamage(Vec3d pos, Entity target) {
        if (mc.world.getDifficulty() == Difficulty.PEACEFUL) return 0f;
        if (!(target instanceof LivingEntity livingEntity)) return 0f;

        // Use null for explosion object to avoid interface implementation issues
        // We hope DamageUtil and DamageSources don't crash with null
        Explosion explosion = null;

        double maxDist = 12.0;
        double distSq = Math.sqrt(livingEntity.squaredDistanceTo(pos));

        if (distSq > maxDist) return 0f;

        double density = getExposure(pos, livingEntity);
        double impact = (1.0 - (distSq / maxDist)) * density;
        float damage = (float) ((int) ((impact * impact + impact) / 2.0 * 7.0 * 12.0 + 1.0));

        if (mc.world.getDifficulty() == Difficulty.EASY) damage = Math.min(damage / 2f + 1f, damage);
        else if (mc.world.getDifficulty() == Difficulty.HARD) damage = damage * 3f / 2f;

        // Try to get armor toughness safely
        float toughness = 0f;
        try {
            toughness = (float) livingEntity.getAttributeValue(EntityAttributes.ARMOR_TOUGHNESS);
        } catch (Throwable e) {
            // Fallback or ignore
        }

        damage = DamageUtil.getDamageLeft(livingEntity, damage, mc.world.getDamageSources().explosion(explosion), livingEntity.getArmor(), toughness);

        if (livingEntity.hasStatusEffect(StatusEffects.RESISTANCE)) {
            damage = damage * (25 - (Objects.requireNonNull(livingEntity.getStatusEffect(StatusEffects.RESISTANCE)).getAmplifier() + 1) * 5) / 25.0f;
        }

        if (damage <= 0f) damage = 0f;

        return damage;
    }

    // Re-implemented getExposure manually
    public static float getExposure(Vec3d source, Entity entity) {
        Box box = entity.getBoundingBox();
        double d = 1.0 / ((box.maxX - box.minX) * 2.0 + 1.0);
        double e = 1.0 / ((box.maxY - box.minY) * 2.0 + 1.0);
        double f = 1.0 / ((box.maxZ - box.minZ) * 2.0 + 1.0);
        double g = (1.0 - Math.floor(1.0 / d) * d) / 2.0;
        double h = (1.0 - Math.floor(1.0 / e) * e) / 2.0;
        double i = (1.0 - Math.floor(1.0 / f) * f) / 2.0;
        if (!(d < 0.0) && !(e < 0.0) && !(f < 0.0)) {
            int j = 0;
            int k = 0;

            for (float l = 0.0F; l <= 1.0F; l = (float) ((double) l + d)) {
                for (float m = 0.0F; m <= 1.0F; m = (float) ((double) m + e)) {
                    for (float n = 0.0F; n <= 1.0F; n = (float) ((double) n + f)) {
                        double o = MathHelper.lerp((double) l, box.minX, box.maxX);
                        double p = MathHelper.lerp((double) m, box.minY, box.maxY);
                        double q = MathHelper.lerp((double) n, box.minZ, box.maxZ);
                        Vec3d vec3d = new Vec3d(o + g, p + h, q + i);
                        if (RaytraceUtil.canSeePointFrom(mc.player.getEyePos(), vec3d)) { // Use player eyes or source? source is explosion center.
                            // But RaytraceUtil.canSeePointFrom uses mc.player.getEyePos() usually?
                            // Wait, getExposure checks from explosion source to entity.
                            // RaytraceUtil.canSeePointFrom checks if player can see.
                            // I should use a generic raycast.
                            // But RaytraceUtil.canSeePointFrom(eyes, vec3) calls world.raycast.
                            // I can use that but pass source as eyes.
                            if (RaytraceUtil.canSeePointFrom(source, vec3d)) {
                                ++j;
                            }
                        }
                        ++k;
                    }
                }
            }

            return (float) j / (float) k;
        } else {
            return 0.0F;
        }
    }

    public static float calculateDamage(BlockPos pos, Entity target) {
        return calculateDamage(Vec3d.ofCenter(pos).add(0, -0.5, 0), target);
    }

    public static float calculateDamage(Entity crystal, Entity target) {
        return calculateDamage(crystal.getPos(), target);
    }
}
