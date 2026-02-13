package dev.sakura.client.utils.world;


import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.DamageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dev.sakura.client.Sakura.mc;
import static net.minecraft.world.GameMode.CREATIVE;

public class DamageUtils {
    private static final Map<String, Integer> PROTECTION_MAP = new HashMap<>() {{
        put("protection", 1);
        put("blast_protection", 2);
        put("projectile_protection", 1);
        put("feather_falling", 1);
        put("fire_protection", 1);
    }};
    public static final float CRYSTAL_POWER = 6.0f;
    public static final float CRYSTAL_RANGE = CRYSTAL_POWER; // 或者 CRYSTAL_DIAMETER / 2.0f

    // ... 其他已有的方法 ...

    /**
     * 新增的重载，用于根据预测的实体中心点计算水晶伤害
     *
     * @param entity                目标实体
     * @param predictedEntityCenter 预测的实体脚底中心点
     * @param crystalAnchorPos      水晶依附的方块位置
     * @param exception             在光线追踪中需要忽略的方块 (通常是正在挖掘的方块)
     * @param ignoreTerrain         是否忽略可破坏地形的阻挡
     * @return 计算出的伤害值
     */
    public static float getCrystalDamage(Entity entity, Vec3d predictedEntityCenter, BlockPos crystalAnchorPos, BlockPos exception, boolean ignoreTerrain) {
        if (!(entity instanceof LivingEntity livingEntity)) return 0f;

        // 根据预测的中心和实体的大小构建一个预测的包围盒
        double entityWidth = livingEntity.getWidth();
        double entityHeight = livingEntity.getHeight();

        // 假设 predictedEntityCenter 是脚底中心
        Box predictedBox = new Box(
                predictedEntityCenter.x - entityWidth / 2.0,
                predictedEntityCenter.y, // 脚底 Y
                predictedEntityCenter.z - entityWidth / 2.0,
                predictedEntityCenter.x + entityWidth / 2.0,
                predictedEntityCenter.y + entityHeight, // 头部 Y
                predictedEntityCenter.z + entityWidth / 2.0
        );

        // 水晶爆炸中心是在 anchorBlock 的上方中心
        // 水晶实体本身位于 anchorBlock.up()，其爆炸通常以水晶实体为中心。
        // Vec3d.ofCenter(crystalAnchorPos.up()) 更准确地表示水晶实体中心
        Vec3d explosionSource = Vec3d.ofCenter(crystalAnchorPos.up());
        return getDamage(entity, predictedBox, explosionSource, CRYSTAL_POWER, exception, ignoreTerrain);
    }

    public static float getCrystalDamage(Entity entity, Box box, EndCrystalEntity crystal, boolean ignoreTerrain) {
        return getDamage(entity, box, Vec3d.ofCenter(crystal.getBlockPos(), 0), 6.0f, null, ignoreTerrain);
    }

    public static float getCrystalDamage(Entity entity, Vec3d explosionPos, boolean ignoreTerrain) {
        // 用实体自身的包围盒
        Box box = entity.getBoundingBox();
        float power = 6.0f;      // 水晶默认爆炸威力
        BlockPos exception = null;
        return getDamage(entity, box, explosionPos, power, exception, ignoreTerrain);
    }

    public static float getCrystalDamage(Entity entity, Box box, BlockPos position, BlockPos exception, boolean ignoreTerrain) {
        return getDamage(entity, box, Vec3d.ofCenter(position, 1), 6.0f, exception, ignoreTerrain);
    }

    public static float getCrystalDamage(Entity entity, Box box, Vec3d explosionPos, boolean ignoreTerrain) {
        return getDamage(entity, box, explosionPos, 6.0f, null, ignoreTerrain);
    }

    public static float getCrystalDamage(Entity entity, Vec3d predictedPos, Vec3d crystalPos, boolean ignoreTerrain) {
        // 使用预测位置构建包围盒
        double entityWidth = entity instanceof LivingEntity ? ((LivingEntity) entity).getWidth() : 0.6;
        double entityHeight = entity instanceof LivingEntity ? ((LivingEntity) entity).getHeight() : 1.8;

        Box predictedBox = new Box(
                predictedPos.x - entityWidth / 2.0,
                predictedPos.y,
                predictedPos.z - entityWidth / 2.0,
                predictedPos.x + entityWidth / 2.0,
                predictedPos.y + entityHeight,
                predictedPos.z + entityWidth / 2.0
        );

        return getDamage(entity, predictedBox, crystalPos, 6.0f, null, ignoreTerrain);
    }

    public static float getDamage(Entity entity, Box box, Vec3d vec3d, float power, BlockPos exception, boolean ignoreTerrain) {
        if (mc.world == null) return 0.0f; // 添加空检查
        if (mc.world.getDifficulty() == Difficulty.PEACEFUL) return 0.0f;
        if (!(entity instanceof net.minecraft.client.network.OtherClientPlayerEntity) && entity instanceof PlayerEntity player && getGameMode(player) == GameMode.CREATIVE)
            return 0.0f;

        float diameter = power * 2.0f;

        if (entity == null) return 0.0f;

        double distance = Math.sqrt(box != null ? box.getCenter().add(0, -0.9, 0).squaredDistanceTo(vec3d) : entity.getEntityPos().squaredDistanceTo(vec3d)) / diameter;
        if (distance > 1.0) return 0.0f;

        double exposure = (1.0 - distance) * getExposure(vec3d, entity.getBoundingBox(), exception, ignoreTerrain);
        float damage = (int) ((exposure * exposure + exposure) / 2.0 * 7.0 * diameter + 1.0);

        if (damage <= 0.0f) return 0.0f;

        if (entity instanceof LivingEntity livingEntity) {
            // 难度伤害调整
            damage = switch (mc.world.getDifficulty()) {
                case EASY -> Math.min(damage / 2.0f + 1.0f, damage);
                case HARD -> damage * 3.0f / 2.0f;
                default -> damage; // NORMAL or unhandled
            };

            // 盔甲和韧性减伤 (DamageUtil.getDamageLeft)
            // livingEntity.getArmor() 返回的是int，可以直接用
            damage = DamageUtil.getDamageLeft(
                    livingEntity,
                    damage,
                    mc.world.getDamageSources().explosion(null, null), // 1.19.3+ explosion 通常需要两个参数 (explosion, attacker)
                    (float) livingEntity.getArmor(),
                    (float) livingEntity.getAttributeInstance(EntityAttributes.ARMOR_TOUGHNESS).getValue()
            );


            if (livingEntity.hasStatusEffect(StatusEffects.RESISTANCE)) {
                damage *= (1.0f - (livingEntity.getStatusEffect(StatusEffects.RESISTANCE).getAmplifier() + 1) * 0.2f);
            }

            List<ItemStack> armorItems = new ArrayList<>();
            armorItems.add(livingEntity.getEquippedStack(EquipmentSlot.HEAD));
            armorItems.add(livingEntity.getEquippedStack(EquipmentSlot.CHEST));
            armorItems.add(livingEntity.getEquippedStack(EquipmentSlot.LEGS));
            armorItems.add(livingEntity.getEquippedStack(EquipmentSlot.FEET));

            damage = DamageUtil.getInflictedDamage(damage, getProtectionAmount(armorItems));
        }

        return Math.max(damage, 0.0f);
    }

    public static int getProtectionAmount(Iterable<ItemStack> armor) {
        int x = 0;
        for (ItemStack stack : armor) {
            x += getProtectionAmount(stack);
        }

        return x;
    }

    public static int getProtectionAmount(ItemStack armor) {
        int x = 0;
        ItemEnchantmentsComponent enchantments = EnchantmentHelper.getEnchantments(armor);
        for (RegistryEntry<Enchantment> enchantment : enchantments.getEnchantments()) {
            String id = enchantment.getIdAsString().replace("minecraft:", "");
            if (PROTECTION_MAP.containsKey(id)) {
                x += enchantments.getLevel(enchantment) * PROTECTION_MAP.get(id);
                break;
            }
        }

        return x;
    }

    private static float getExposure(Vec3d source, Box box, BlockPos exception, boolean ignoreTerrain) {
        int hitCount = 0;
        int count = 0;

        for (double k = 0.0; k <= 1.0; k += 0.4545454446934474) {
            for (double l = 0.0; l <= 1.0; l += 0.21739130885479366) {
                for (double m = 0.0; m <= 1.0; m += 0.4545454446934474) {
                    Vec3d vec3d = new Vec3d(MathHelper.lerp(k, box.minX, box.maxX) + 0.045454555306552624, MathHelper.lerp(l, box.minY, box.maxY), MathHelper.lerp(m, box.minZ, box.maxZ) + 0.045454555306552624);
                    if (raycast(vec3d, source, exception, ignoreTerrain) == HitResult.Type.MISS) ++hitCount;
                    ++count;
                }
            }
        }

        return (float) hitCount / (float) count;
    }

    private static HitResult.Type raycast(Vec3d start, Vec3d end, BlockPos exception, boolean ignoreTerrain) {
        return BlockView.raycast(start, end, null, (innerContext, blockPos) -> {
            BlockState blockState;
            if (blockPos.equals(exception)) {
                blockState = Blocks.AIR.getDefaultState();
            } else {
                blockState = mc.world.getBlockState(blockPos);
                if (blockState.getBlock().getBlastResistance() < 600 && ignoreTerrain)
                    blockState = Blocks.AIR.getDefaultState();
            }

            BlockHitResult hitResult = blockState.getCollisionShape(mc.world, blockPos).raycast(start, end, blockPos);
            return hitResult == null ? null : hitResult.getType();
        }, (innerContext) -> HitResult.Type.MISS);
    }


    public static GameMode getGameMode(PlayerEntity player) {
        PlayerListEntry playerListEntry = mc.getNetworkHandler().getPlayerListEntry(player.getUuid());
        return playerListEntry == null ? CREATIVE : playerListEntry.getGameMode();
    }
}
