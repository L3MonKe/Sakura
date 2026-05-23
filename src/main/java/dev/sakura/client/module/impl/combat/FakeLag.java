package dev.sakura.client.module.impl.combat;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.impl.player.MotionEvent;
import dev.sakura.client.event.impl.render.WorldLoadEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.movement.Scaffold;
import dev.sakura.client.utils.client.ChatUtil;
import dev.sakura.client.utils.math.VecCalculation;
import dev.sakura.client.utils.player.BlinkUtils;
import dev.sakura.client.utils.player.ItemUtils;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.UUID;

public class FakeLag extends Module {
    public static NumberValue<Double> time = new NumberValue<>("PulseInterval(ms)", "脉冲间隔(ms)", 350.0, 1.0, 1000.0, 1.0);
    public static NumberValue<Double> pulseRange = new NumberValue<>("PulseDistance", "脉冲距离", 40.0, 0.0, 80.0, 1.0);
    public static NumberValue<Double> lagRange = new NumberValue<>("StartLagRange", "开始延迟范围", 12.0, 0.0, 20.0, 1.0);
    public static NumberValue<Double> distance = new NumberValue<>("Distance", "距离", 4.0, 1.0, 7.0, 0.01);
    public static NumberValue<Double> cancelTime = new NumberValue<>("NoWorkWhenBeingHitTime(ms)", "被击中暂停时间(ms)", 1000.0, 0.0, 2000.0, 10.0);

    public static boolean cantWork;
    public static boolean lagging;
    public static TimerUtil timer = new TimerUtil();
    public static TimerUtil delayTimer = new TimerUtil();
    public static OtherClientPlayerEntity clonePlayer;

    public FakeLag() {
        super("FakeLag", "假延迟", Category.Combat);
    }

    @Override
    public void onEnable() {
        if (mc.world == null || mc.player == null) return;

        if (mc.isInSingleplayer()) {
            setState(false);
            ChatUtil.clientMessage("You can't use FakeLag in singleplayer!");
            return;
        }

        timer.reset();
        BlinkUtils.startBlink();
        lagging = true;

        ChatUtil.clientMessage("Lagging...");
        OtherClientPlayerEntity clone = new OtherClientPlayerEntity(mc.world, mc.player.getGameProfile());
        clone.headYaw = mc.player.headYaw;
        clone.copyPositionAndRotation(mc.player);
        clone.setSprinting(mc.player.isSprinting());
        clone.setUuid(UUID.randomUUID());
        clonePlayer = clone;
    }

    @Override
    public void onDisable() {
        if (mc.world == null) return;
        if (clonePlayer != null) clonePlayer = null;

        timer.reset();
        BlinkUtils.stopBlink();
        lagging = false;
    }

    @EventHandler
    public void onWorld(WorldLoadEvent event) {
        setState(false);
        onDisable();
    }

    @EventHandler
    public void onMotion(MotionEvent event) {
        if (event.getType() != EventType.PRE) return;
        if (mc.world == null || mc.player == null) return;

        if (cantWork) {
            if (delayTimer.passedMillise(cancelTime.get())) {
                cantWork = false;
                ChatUtil.clientMessage("Start lagging now!");
            }
            return;
        }

        if (projectileIncoming(clonePlayer, 15) && lagging) {
            BlinkUtils.releaseTick(clonePlayer);
            ChatUtil.clientMessage("Release 1 tick");
        }

        if (Sakura.MODULES.getModule(KillAura.class).getCurrentTarget() != null) {
            if (lagging) {
                BlinkUtils.stopBlink();
                ChatUtil.clientMessage("Stop to attack");
                clonePlayer.headYaw = mc.player.headYaw;
                clonePlayer.copyPositionAndRotation(mc.player);
                clonePlayer.setSprinting(mc.player.isSprinting());
                timer.reset();
                lagging = false;
            }
            return;
        }

        if (mc.currentScreen instanceof GenericContainerScreen || mc.currentScreen instanceof InventoryScreen) {
            if (lagging) {
                BlinkUtils.stopBlink();
                clonePlayer.headYaw = mc.player.headYaw;
                clonePlayer.copyPositionAndRotation(mc.player);
                clonePlayer.setSprinting(mc.player.isSprinting());
                timer.reset();
                lagging = false;
            }
            return;
        }

        if (Sakura.MODULES.getModule(Scaffold.class).isEnabled()) {
            if (lagging) {
                BlinkUtils.stopBlink();
                ChatUtil.clientMessage("Stop to scaffold");
                clonePlayer.headYaw = mc.player.headYaw;
                clonePlayer.copyPositionAndRotation(mc.player);
                clonePlayer.setSprinting(mc.player.isSprinting());
                timer.reset();
                lagging = false;
            }
            return;
        }
        if (mc.player.isUsingItem() && ItemUtils.isConsumable(mc.player.getMainHandStack()) || mc.player.getOffHandStack().getItem() == Items.SNOWBALL || mc.player.getOffHandStack().getItem() == Items.EGG) {
            if (lagging) {
                BlinkUtils.stopBlink();
                ChatUtil.clientMessage("UsingItem -> Release");
                clonePlayer.headYaw = mc.player.headYaw;
                clonePlayer.copyPositionAndRotation(mc.player);
                clonePlayer.setSprinting(mc.player.isSprinting());
                timer.reset();
                lagging = false;
            }
            return;
        }

        for (PlayerEntity target : mc.world.getPlayers()) {
            if (target == mc.player || target == clonePlayer) continue;
            if (VecCalculation.getDistanceToEntityBox(mc.player, target) <= distance.get() || (VecCalculation.getDistanceToEntityBox(clonePlayer, target) <= distance.get()) && lagging) {
                if (lagging) {
                    ChatUtil.clientMessage("Too close to stop");
                    BlinkUtils.stopBlink();
                    clonePlayer.headYaw = mc.player.headYaw;
                    clonePlayer.copyPositionAndRotation(mc.player);
                    clonePlayer.setSprinting(mc.player.isSprinting());
                    timer.reset();
                    lagging = false;
                }
                return;
            }
        }

        if (timer.passedMillise(time.get())) {
            BlinkUtils.stopBlink();
            lagging = false;
            clonePlayer.headYaw = mc.player.headYaw;
            clonePlayer.copyPositionAndRotation(mc.player);
            clonePlayer.setSprinting(mc.player.isSprinting());
            BlinkUtils.startBlink();
            lagging = true;
            timer.reset();
        }
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (mc.player == null || mc.world == null) return;
        if (event.getPacket() instanceof EntityVelocityUpdateS2CPacket packet) {
            if (packet.getEntityId() == mc.player.getId()) {
                if (lagging) {
                    ChatUtil.clientMessage("Being hit! DelayLagging");
                    BlinkUtils.stopBlink();
                    lagging = false;
                    delayTimer.reset();
                    cantWork = true;
                }
            }
        }
        if (event.getPacket() instanceof PlayerPositionLookS2CPacket) {
            ChatUtil.clientMessage("Receive Flag, Disable module.");
            setState(false);
        }
    }

    public boolean projectileIncoming(PlayerEntity player, double maxCheckDistance) {
        if (player == null || player.getEntityWorld() == null) return false;

        World world = player.getEntityWorld();
        Vec3d playerPos = player.getEntityPos();

        Box expandedPlayerBox = player.getBoundingBox().expand(0.2);

        for (FishingBobberEntity arrow : world.getEntitiesByClass(
                FishingBobberEntity.class,
                player.getBoundingBox().expand(maxCheckDistance),
                a -> !a.isOnGround()
        )) {
            Vec3d arrowPos = arrow.getEntityPos();
            Vec3d arrowVel = arrow.getVelocity();

            if (arrowVel.lengthSquared() < 0.0001) continue;

            Vec3d dirToPlayer = playerPos.subtract(arrowPos).normalize();

            if (arrowVel.normalize().dotProduct(dirToPlayer) <= 0) {
                continue;
            }

            Vec3d nextArrowPos = arrowPos.add(arrowVel);

            Optional<Vec3d> collide = expandedPlayerBox.raycast(arrowPos, nextArrowPos);

            if (collide.isPresent()) {
                return true;
            }
        }
        for (ArrowEntity arrow : world.getEntitiesByClass(
                ArrowEntity.class,
                player.getBoundingBox().expand(maxCheckDistance),
                a -> !a.isOnGround()
        )) {
            Vec3d arrowPos = arrow.getEntityPos();
            Vec3d arrowVel = arrow.getVelocity();

            if (arrowVel.lengthSquared() < 0.0001) continue;

            Vec3d dirToPlayer = playerPos.subtract(arrowPos).normalize();

            if (arrowVel.normalize().dotProduct(dirToPlayer) <= 0) {
                continue;
            }

            Vec3d nextArrowPos = arrowPos.add(arrowVel);

            Optional<Vec3d> collide = expandedPlayerBox.raycast(arrowPos, nextArrowPos);

            if (collide.isPresent()) {
                return true;
            }
        }
        for (SnowballEntity arrow : world.getEntitiesByClass(
                SnowballEntity.class,
                player.getBoundingBox().expand(maxCheckDistance),
                a -> !a.isOnGround()
        )) {
            Vec3d arrowPos = arrow.getEntityPos();
            Vec3d arrowVel = arrow.getVelocity();

            if (arrowVel.lengthSquared() < 0.0001) continue;

            Vec3d dirToPlayer = playerPos.subtract(arrowPos).normalize();

            if (arrowVel.normalize().dotProduct(dirToPlayer) <= 0) {
                continue;
            }

            Vec3d nextArrowPos = arrowPos.add(arrowVel);

            Optional<Vec3d> collide = expandedPlayerBox.raycast(arrowPos, nextArrowPos);

            if (collide.isPresent()) {
                return true;
            }
        }
        for (EggEntity arrow : world.getEntitiesByClass(
                EggEntity.class,
                player.getBoundingBox().expand(maxCheckDistance),
                a -> !a.isOnGround()
        )) {
            Vec3d arrowPos = arrow.getEntityPos();
            Vec3d arrowVel = arrow.getVelocity();

            if (arrowVel.lengthSquared() < 0.0001) continue;

            Vec3d dirToPlayer = playerPos.subtract(arrowPos).normalize();

            if (arrowVel.normalize().dotProduct(dirToPlayer) <= 0) {
                continue;
            }

            Vec3d nextArrowPos = arrowPos.add(arrowVel);

            Optional<Vec3d> collide = expandedPlayerBox.raycast(arrowPos, nextArrowPos);

            if (collide.isPresent()) {
                return true;
            }
        }

        return false;
    }

}
