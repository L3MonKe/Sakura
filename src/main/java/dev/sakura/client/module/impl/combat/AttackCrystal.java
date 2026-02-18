package dev.sakura.client.module.impl.combat;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.event.impl.input.ClickEvent;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.rotation.RaytraceUtil;
import dev.sakura.client.utils.rotation.Rotation;
import dev.sakura.client.utils.rotation.RotationUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;

import java.util.List;

public class AttackCrystal extends Module {
    public AttackCrystal() {
        super("AttackCrystal", "水晶光环", Category.Combat);
    }

    public Entity entity;

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        double range = 4.0;
        double rangeSq = range * range;

        Entity closestCrystal = null;
        double closestDistSq = Double.MAX_VALUE;

        Box searchBox = mc.player.getBoundingBox().expand(range);
        List<EndCrystalEntity> crystals = mc.world.getNonSpectatingEntities(EndCrystalEntity.class, searchBox);

        for (EndCrystalEntity crystal : crystals) {
            if (!crystal.isAlive()) continue;

            double distSq = mc.player.squaredDistanceTo(crystal);

            if (distSq < closestDistSq && distSq <= rangeSq) {
                closestDistSq = distSq;
                closestCrystal = crystal;
            }
        }

        if (closestCrystal != null) {
            Rotation targetRot = RotationUtil.calculate(closestCrystal);
            Entity finalClosestCrystal = closestCrystal;
            HitResult hitResult = RaytraceUtil.rayTraceEntity(3.0, targetRot, entity -> entity == finalClosestCrystal);
            if (hitResult instanceof EntityHitResult entityHitResult && entityHitResult.getEntity().equals(closestCrystal)) {
                Managers.ROTATION.setRotations(targetRot, 10);
                this.entity = closestCrystal;
            }
        }
    }

    @EventHandler
    public void onClick(ClickEvent event) {
        if (entity == null) return;
        HitResult hitResult = mc.crosshairTarget;
        if (hitResult instanceof EntityHitResult entityHitResult && entityHitResult.getEntity().equals(entity)) {
            mc.getNetworkHandler().sendPacket(PlayerInteractEntityC2SPacket.attack(entity, false));
            mc.player.swingHand(Hand.MAIN_HAND);
            entity = null;
        }
    }
}