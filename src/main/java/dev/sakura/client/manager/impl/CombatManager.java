package dev.sakura.client.manager.impl;

import dev.sakura.client.Sakura;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.module.impl.client.Targets;
import dev.sakura.client.module.impl.client.Teams;
import dev.sakura.client.module.impl.combat.AntiBot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static dev.sakura.client.Sakura.mc;

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

    public List<LivingEntity> getEntities(double minRange, double maxRange) {
        List<LivingEntity> list = new ArrayList<>();
        for (Entity entity : mc.world.getEntities()) {
            if (entity instanceof LivingEntity livingEntity) {
                if (isEnemy(livingEntity, minRange, maxRange)) {
                    list.add(livingEntity);
                }
            }
        }
        return list;
    }

    public LivingEntity getClosestEnemy(double range) {
        return getClosestEnemy(range, Sakura.MODULES.getModule(Targets.class).targetBy.get());
    }

    public LivingEntity getClosestEnemy(double range, TargetBy targetBy) {
        LivingEntity target = null;
        switch (targetBy) {
            case FOV -> target = getTargetByFOV(range);
            case Health -> target = getTargetByHealth(range);
            case Distance -> target = getNearestTarget(range);
        }
        return target;
    }

    public LivingEntity getClosestEnemy(double minRange, double maxRange) {
        return getClosestEnemy(minRange, maxRange, Sakura.MODULES.getModule(Targets.class).targetBy.get());
    }

    public LivingEntity getClosestEnemy(double minRange, double maxRange, TargetBy targetBy) {
        LivingEntity target = null;
        switch (targetBy) {
            case FOV -> target = getTargetByFOVRange(minRange, maxRange);
            case Health -> target = getTargetByHealth(minRange, maxRange);
            case Distance -> target = getNearestTarget(minRange, maxRange);
        }
        return target;
    }

    public boolean isEnemy(LivingEntity entity, double range) {
        return isEnemy(entity, range, range);
    }

    public boolean isEnemy(LivingEntity entity, double minRange, double maxRange) {
        Targets targetSetting = Sakura.MODULES.getModule(Targets.class);
        if (isInvalid(entity, minRange, maxRange)) {
            return false;
        }
        return switch (entity) {
            case AnimalEntity ignored when targetSetting.targets.isEnabled("Animals") -> true;
            case PassiveEntity ignored when targetSetting.targets.isEnabled("Passive") -> true;
            case MobEntity ignored when targetSetting.targets.isEnabled("Mobs") -> true;
            case PlayerEntity player when targetSetting.targets.isEnabled("Player") ->
                    !AntiBot.isBot(player) && !Teams.isSameTeam(player);
            default -> false;
        };
    }

    public boolean isInvalid(LivingEntity entity, double range) {
        return isInvalid(entity, range, range);
    }

    public boolean isInvalid(LivingEntity entity, double minRange, double maxRange) {
        Targets targetSetting = Sakura.MODULES.getModule(Targets.class);
        double min = Math.min(minRange, maxRange);
        double max = Math.max(minRange, maxRange);
        if (min == max) {
            min = 0.0;
        }
        double distance = mc.player.distanceTo(entity);
        if (distance > max || distance < min) {
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

    public LivingEntity getNearestTarget(double range) {
        return getEntities(range).stream().min(Comparator.comparing(t -> mc.player.distanceTo(t))).orElse(null);
    }

    public LivingEntity getTargetByHealth(double range) {
        return getEntities(range).stream().min(Comparator.comparing(t -> (Managers.HEALTH.getHealth(t)))).orElse(null);
    }

    public LivingEntity getTargetByFOV(double range) {
        return getEntities(range).stream().min(Comparator.comparing(this::getFOVAngle)).orElse(null);
    }

    public LivingEntity getTargetByFOV(double range, double fov) {
        return getEntities(range).stream().filter(entityPlayer -> getFOVAngle(entityPlayer) < fov).min(Comparator.comparing(this::getFOVAngle)).orElse(null);
    }

    public LivingEntity getNearestTarget(double minRange, double maxRange) {
        return getEntities(minRange, maxRange).stream().min(Comparator.comparing(t -> mc.player.distanceTo(t))).orElse(null);
    }

    public LivingEntity getTargetByHealth(double minRange, double maxRange) {
        return getEntities(minRange, maxRange).stream().min(Comparator.comparing(t -> Managers.HEALTH.getHealth(t))).orElse(null);
    }

    public LivingEntity getTargetByFOVRange(double minRange, double maxRange) {
        return getEntities(minRange, maxRange).stream().min(Comparator.comparing(this::getFOVAngle)).orElse(null);
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
