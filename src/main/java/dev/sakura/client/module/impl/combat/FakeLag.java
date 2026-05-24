package dev.sakura.client.module.impl.combat;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.impl.player.MotionEvent;
import dev.sakura.client.event.impl.render.Render3DEvent;
import dev.sakura.client.event.impl.render.WorldLoadEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.movement.Scaffold;
import dev.sakura.client.utils.client.ChatUtil;
import dev.sakura.client.utils.math.VecCalculation;
import dev.sakura.client.utils.player.BlinkUtils;
import dev.sakura.client.utils.player.ItemUtils;
import dev.sakura.client.utils.render.Render3DUtil;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.awt.*;
import java.util.Optional;
import java.util.UUID;

public class FakeLag extends Module {

    public enum LagMode {
        Instant,
        SlowRelease
    }

    public static EnumValue<LagMode> lagMode = new EnumValue<>("Mode", "模式", LagMode.Instant);
    public static BoolValue debug = new BoolValue("Debug", "调试", false);
    public static NumberValue<Double> time = new NumberValue<>("PulseInterval(ms)", "脉冲间隔(ms)", 350.0, 1.0, 1000.0, 1.0);
    public static NumberValue<Double> releaseInterval = new NumberValue<>("ReleaseInterval(ms)", "释放间隔(ms)", 50.0, 10.0, 500.0, 10.0, () -> lagMode.is(LagMode.SlowRelease));
    public static NumberValue<Double> pulseRange = new NumberValue<>("PulseDistance", "脉冲距离", 40.0, 0.0, 80.0, 1.0);
    public static NumberValue<Double> lagRange = new NumberValue<>("StartLagRange", "开始延迟范围", 12.0, 0.0, 20.0, 1.0);
    public static NumberValue<Double> distance = new NumberValue<>("Distance", "距离", 4.0, 1.0, 7.0, 0.01);
    public static NumberValue<Double> cancelTime = new NumberValue<>("NoWorkWhenBeingHitTime(ms)", "被击中暂停时间(ms)", 1000.0, 0.0, 2000.0, 10.0);

    public static boolean cantWork;
    public static boolean lagging;
    public static boolean releasing;
    public static TimerUtil timer = new TimerUtil();
    public static TimerUtil delayTimer = new TimerUtil();
    public static TimerUtil releaseTimer = new TimerUtil();
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
        releaseTimer.reset();
        BlinkUtils.startBlink();
        lagging = true;
        releasing = false;

        debugMsg("Lagging...");
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
        if (clonePlayer != null) {
            clonePlayer = null;
        }

        timer.reset();
        BlinkUtils.stopBlink();
        lagging = false;
        releasing = false;
    }

    @EventHandler
    public void onWorld(WorldLoadEvent event) {
        setState(false);
        onDisable();
    }

    @EventHandler
    public void onRender3D(Render3DEvent event) {
        if (clonePlayer == null || (!lagging && !releasing)) return;
        double dist = mc.player.getEntityPos().distanceTo(clonePlayer.getEntityPos());
        double fadeStart = 0.5;
        int alpha;
        if (dist <= fadeStart) {
            alpha = 0;
        } else if (dist <= fadeStart + 2.0) {
            alpha = (int) (60 * ((dist - fadeStart) / 2.0));
        } else {
            alpha = 60;
        }
        if (alpha <= 0) return;
        Box box = clonePlayer.getBoundingBox();
        Render3DUtil.drawFilledBox(event.getMatrices(), box, new Color(255, 255, 255, alpha));
    }

    private void debugMsg(String msg) {
        if (debug.get()) ChatUtil.clientMessage(msg);
    }

    private boolean isUsingConsumable() {
        if (!mc.player.isUsingItem()) return false;
        ItemStack mainHand = mc.player.getMainHandStack();
        ItemStack offHand = mc.player.getOffHandStack();
        return ItemUtils.isConsumable(mainHand) || ItemUtils.isConsumable(offHand)
                || offHand.getItem() == Items.SNOWBALL || offHand.getItem() == Items.EGG
                || mainHand.getItem() == Items.SNOWBALL || mainHand.getItem() == Items.EGG;
    }

    private boolean shouldStop() {
        if (Sakura.MODULES.getModule(KillAura.class).getCurrentTarget() != null) return true;
        if (mc.currentScreen != null) return true;
        if (Sakura.MODULES.getModule(Scaffold.class).isEnabled()) return true;
        if (isUsingConsumable()) return true;
        for (PlayerEntity target : mc.world.getPlayers()) {
            if (target == mc.player || target == clonePlayer) continue;
            if (VecCalculation.getDistanceToEntityBox(mc.player, target) <= distance.get()
                    || (clonePlayer != null && VecCalculation.getDistanceToEntityBox(clonePlayer, target) <= distance.get())) {
                return true;
            }
        }
        return false;
    }

    private void stopLagging(String reason) {
        BlinkUtils.stopBlink();
        releasing = false;
        if (clonePlayer != null) {
            clonePlayer.headYaw = mc.player.headYaw;
            clonePlayer.copyPositionAndRotation(mc.player);
            clonePlayer.setSprinting(mc.player.isSprinting());
        }
        timer.reset();
        lagging = false;
        if (!reason.isEmpty()) {
            debugMsg(reason);
        }
    }

    @EventHandler
    public void onMotion(MotionEvent event) {
        if (event.getType() != EventType.PRE) return;
        if (mc.world == null || mc.player == null) return;

        if (cantWork) {
            if (delayTimer.passedMillise(cancelTime.get())) {
                cantWork = false;
                debugMsg("Start lagging now!");
            }
            return;
        }

        if (releasing) {
            if (shouldStop()) {
                stopLagging("Stop releasing");
                return;
            }
            if (releaseTimer.passedMillise(releaseInterval.get())) {
                BlinkUtils.releaseTick(clonePlayer);
                releaseTimer.reset();
                if (!BlinkUtils.blinking || BlinkUtils.isClientPacketsEmpty()) {
                    BlinkUtils.stopBlink();
                    releasing = false;
                    clonePlayer.headYaw = mc.player.headYaw;
                    clonePlayer.copyPositionAndRotation(mc.player);
                    clonePlayer.setSprinting(mc.player.isSprinting());
                    BlinkUtils.startBlink();
                    lagging = true;
                    timer.reset();
                    debugMsg("Lagging...");
                }
            }
            return;
        }

        if (projectileIncoming(clonePlayer, 15) && lagging) {
            BlinkUtils.releaseTick(clonePlayer);
            debugMsg("Release 1 tick");
        }

        if (shouldStop()) {
            if (lagging) {
                if (Sakura.MODULES.getModule(KillAura.class).getCurrentTarget() != null) {
                    stopLagging("Stop to attack");
                } else if (mc.currentScreen != null) {
                    stopLagging("");
                } else if (Sakura.MODULES.getModule(Scaffold.class).isEnabled()) {
                    stopLagging("Stop to scaffold");
                } else if (isUsingConsumable()) {
                    stopLagging("UsingItem -> Release");
                } else {
                    stopLagging("Too close to stop");
                }
            }
            return;
        }

        if (timer.passedMillise(time.get())) {
            if (lagMode.is(LagMode.Instant)) {
                BlinkUtils.stopBlink();
                lagging = false;
                clonePlayer.headYaw = mc.player.headYaw;
                clonePlayer.copyPositionAndRotation(mc.player);
                clonePlayer.setSprinting(mc.player.isSprinting());
                BlinkUtils.startBlink();
                lagging = true;
                timer.reset();
            } else if (lagMode.is(LagMode.SlowRelease)) {
                lagging = false;
                releasing = true;
                releaseTimer.reset();
                debugMsg("Slow releasing...");
            }
        }
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (mc.player == null || mc.world == null) return;
        if (event.getPacket() instanceof EntityVelocityUpdateS2CPacket packet) {
            if (packet.getEntityId() == mc.player.getId()) {
                if (lagging || releasing) {
                    debugMsg("Being hit! DelayLagging");
                    BlinkUtils.stopBlink();
                    lagging = false;
                    releasing = false;
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
