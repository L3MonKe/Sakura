package dev.sakura.client.utils.combat;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class CombatUtil {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static List<PlayerEntity> getEnemies(double range) {
        List<PlayerEntity> list = new ArrayList<>();
        if (mc.world == null) return list;
        for (PlayerEntity player : mc.world.getPlayers()) {
            if (!isValid(player, range)) continue;
            list.add(player);
        }
        return list;
    }

    public static boolean isValid(Entity entity, double range) {
        if (mc.player == null) return false;
        boolean invalid = entity == null
                || !entity.isAlive()
                || entity.equals(mc.player)
                || mc.player.getPos().distanceTo(entity.getPos()) > range;
        return !invalid;
    }

    public static PlayerEntity getClosestEnemy(double distance) {
        PlayerEntity closest = null;
        if (mc.player == null) return null;

        for (PlayerEntity player : getEnemies(distance)) {
            if (closest == null) {
                closest = player;
                continue;
            }
            if (!(mc.player.squaredDistanceTo(player.getPos()) < mc.player.squaredDistanceTo(closest.getPos())))
                continue;
            closest = player;
        }
        return closest;
    }

    public static void attackCrystal(BlockPos pos, boolean rotate, boolean swing) {
        if (mc.world == null || mc.player == null) return;
        Box box = new Box(pos);
        for (Entity entity : mc.world.getOtherEntities(null, box)) {
            if (entity instanceof EndCrystalEntity crystal) {
                attackEntity(crystal, swing);
                break;
            }
        }
    }

    public static void attackEntity(Entity entity, boolean swing) {
        if (mc.player == null || mc.getNetworkHandler() == null) return;
        mc.getNetworkHandler().sendPacket(PlayerInteractEntityC2SPacket.attack(entity, mc.player.isSneaking()));
        if (swing) {
            mc.player.swingHand(Hand.MAIN_HAND);
        }
    }

    public static Vec3d getEntityPosVec(PlayerEntity entity, int ticks) {
        if (ticks <= 0) {
            return entity.getPos();
        }
        return entity.getPos().add(getMotionVec(entity, ticks, true));
    }

    public static Vec3d getMotionVec(Entity entity, float ticks, boolean collision) {
        if (mc.world == null) return Vec3d.ZERO;
        double dX = entity.getX() - entity.prevX;
        double dY = entity.getY() - entity.prevY;
        double dZ = entity.getZ() - entity.prevZ;
        double entityMotionPosX = 0;
        double entityMotionPosY = 0;
        double entityMotionPosZ = 0;
        if (collision) {
            for (double i = 1; i <= ticks; i = i + 0.5) {
                if (!mc.world.canCollide(entity, entity.getBoundingBox().offset(new Vec3d(dX * i, dY * i, dZ * i)))) {
                    entityMotionPosX = dX * i;
                    entityMotionPosY = dY * i;
                    entityMotionPosZ = dZ * i;
                } else {
                    break;
                }
            }
        } else {
            entityMotionPosX = dX * ticks;
            entityMotionPosY = dY * ticks;
            entityMotionPosZ = dZ * ticks;
        }
        return new Vec3d(entityMotionPosX, entityMotionPosY, entityMotionPosZ);
    }
}