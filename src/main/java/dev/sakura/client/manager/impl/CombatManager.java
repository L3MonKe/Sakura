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
import net.minecraft.util.math.Vec3d;

import java.awt.*;
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

    public boolean isEnemy(LivingEntity entity, double range) {
        Targets targetSetting = Sakura.MODULES.getModule(Targets.class);
        if (isInvalid(entity, range)) {
            return false;
        }
        return switch (entity) {
            case AnimalEntity animalEntity when targetSetting.targets.isEnabled("Animals") -> true;
            case PassiveEntity passiveEntity when targetSetting.targets.isEnabled("Passive") -> true;
            case MobEntity mobEntity when targetSetting.targets.isEnabled("Mobs") -> true;
            case PlayerEntity player when targetSetting.targets.isEnabled("Player") ->
                    !AntiBot.isBot(player) || !Teams.isSameTeam(player);
            default -> false;
        };
    }

    public boolean isInvalid(LivingEntity entity, double range) {
        Targets targetSetting = Sakura.MODULES.getModule(Targets.class);
        if (Vec3d.of(mc.player.getBlockPos()).distanceTo(Vec3d.of(entity.getBlockPos())) > range) {
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