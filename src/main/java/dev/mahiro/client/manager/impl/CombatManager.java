package dev.mahiro.client.manager.impl;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.manager.Managers;
import dev.mahiro.client.module.impl.client.Targets;
import dev.mahiro.client.module.impl.client.Teams;
import dev.mahiro.client.module.impl.combat.AntiBot;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static dev.mahiro.client.Mahiro.mc;

public class CombatManager extends Managers {
    public List<LivingEntity> getEntities(double range) {
        List<LivingEntity> list = new ArrayList<>();
        for (Entity entity : mc.world.getEntities()) {
            if (entity instanceof LivingEntity livingEntity) {
                if (isEnemy(livingEntity, range)) {
                    list.add(livingEntity);
                }
            }
        }
        return list;
    }

    public LivingEntity getClosestEnemy(double distance) {
        return getClosestEnemy(distance, Mahiro.MODULES.getModule(Targets.class).targetBy.get());
    }

    public LivingEntity getClosestEnemy(double distance, TargetBy targetBy) {
        LivingEntity target = null;
        switch (targetBy) {
            case FOV -> target = getTargetByFOV(distance);
            case Health -> target = getTargetByHealth(distance);
            case Distance -> target = getNearestTarget(distance);
        }
        return target;
    }

    public boolean isEnemy(LivingEntity entity, double range) {
        Targets targetSetting = Mahiro.MODULES.getModule(Targets.class);
        if (isInvalid(entity, range)) {
            return false;
        }
        return switch (entity) {
            case MobEntity mobEntity when targetSetting.targets.isEnabled("Mobs") -> true;
            case AnimalEntity animalEntity when targetSetting.targets.isEnabled("Animals") -> true;
            case PassiveEntity passiveEntity when targetSetting.targets.isEnabled("Passive") -> true;
            case PlayerEntity player when targetSetting.targets.isEnabled("Player") -> {
                Teams teams = Mahiro.MODULES.getModule(Teams.class);
                if (teams.isEnabled()) {
                    int myColor = getLeatherArmorColor(mc.player);
                    int theirColor = getLeatherArmorColor(player);
                    if (myColor != -1 && theirColor != -1) {
                        yield myColor != theirColor;
                    }
                }
                yield !AntiBot.isBot(player);
            }
            default -> false;
        };
    }

    public boolean isInvalid(LivingEntity entity, double range) {
        Targets targetSetting = Mahiro.MODULES.getModule(Targets.class);
        if (mc.player.getPos().distanceTo(entity.getPos()) > range) {
            return true;
        }
        if (entity.isInvisible() && targetSetting.check.isEnabled("Invisible")) {
            return true;
        }
        if (entity.isDead() && targetSetting.check.isEnabled("Dead")) {
            return true;
        }
        return entity == mc.player;
    }

    private int getLeatherArmorColor(PlayerEntity player) {
        for (ItemStack stack : player.getArmorItems()) {
            if (stack.isEmpty()) continue;
            DyedColorComponent dyed = stack.get(DataComponentTypes.DYED_COLOR);
            if (dyed != null) {
                return dyed.rgb();
            }
        }
        return -1;
    }

    public LivingEntity getNearestTarget(double range) {
        return getEntities(range).stream().min(Comparator.comparing(t -> mc.player.distanceTo(t))).orElse(null);
    }

    public LivingEntity getTargetByHealth(double range) {
        return getEntities(range).stream().min(Comparator.comparing(t -> (t.getHealth() + t.getAbsorptionAmount()))).orElse(null);
    }

    public LivingEntity getTargetByFOV(double range) {
        return getEntities(range).stream().min(Comparator.comparing(this::getFOVAngle)).orElse(null);
    }

    public LivingEntity getTargetByFOV(double range, double fov) {
        return getEntities(range).stream()
                .filter(entityPlayer -> getFOVAngle(entityPlayer) < fov)
                .min(Comparator.comparing(this::getFOVAngle)).orElse(null);
    }

    private double getFOVAngle(LivingEntity e) {
        double yaw = MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(e.getZ() - mc.player.getZ(), e.getX() - mc.player.getX())) - 90.0);
        return Math.abs(yaw - MathHelper.wrapDegrees(mc.player.getYaw()));
    }

    public enum TargetBy {
        Distance,
        FOV,
        Health
    }
}