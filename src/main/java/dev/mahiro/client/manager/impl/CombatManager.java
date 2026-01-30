package dev.mahiro.client.manager.impl;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.manager.Managers;
import dev.mahiro.client.module.impl.client.Targets;
import dev.mahiro.client.module.impl.client.Teams;
import dev.mahiro.client.module.impl.combat.AntiBot;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static dev.mahiro.client.Mahiro.mc;

public class CombatManager extends Managers {
    public List<Entity> getEntities(double range) {
        List<Entity> list = new ArrayList<>();
        for (Entity entity : mc.world.getEntities()) {
            if (isEnemy(entity, range)) {
                list.add(entity);
            }
        }
        return list;
    }

    public Entity getClosestEnemy(double distance) {
        Entity closest = null;
        for (Entity entity : getEntities(distance)) {
            if (closest == null) {
                closest = entity;
                continue;
            }
            if (!(mc.player.squaredDistanceTo(entity.getPos()) < mc.player.squaredDistanceTo(closest))) continue;
            closest = entity;
        }
        return closest;
    }

    public boolean isEnemy(Entity entity, double range) {
        Targets targets = Mahiro.MODULES.getModule(Targets.class);
        if (mc.player.getPos().distanceTo(entity.getPos()) > range) {
            return false;
        }
        return switch (entity) {
            case MobEntity mobEntity when targets.mobs.get() -> true;
            case AnimalEntity animalEntity when targets.animals.get() -> true;
            case PlayerEntity player when targets.player.get() -> {
                Teams teams = Mahiro.MODULES.getModule(Teams.class);
                if (teams.isEnabled()) {
                    int myColor = getLeatherArmorColor(mc.player);
                    int theirColor = getLeatherArmorColor(player);
                    if (myColor != -1 && theirColor != -1) {
                        yield myColor == theirColor;
                    }
                }
                yield !AntiBot.isBot(player);
            }
            default -> entity != mc.player;
        };
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
}