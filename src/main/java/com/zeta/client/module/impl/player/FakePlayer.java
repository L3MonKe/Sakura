package com.zeta.client.module.impl.player;

import com.mojang.authlib.GameProfile;
import com.zeta.client.Zeta;
import com.zeta.client.events.EventType;
import com.zeta.client.events.packet.PacketEvent;
import com.zeta.client.events.player.MotionEvent;
import com.zeta.client.module.Category;
import com.zeta.client.module.Module;
import com.zeta.client.values.impl.BoolValue;
import com.zeta.client.values.impl.StringValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FakePlayer extends Module {
    public FakePlayer() {
        super("FakePlayer", "假人", Category.Player);
    }

    private final StringValue name = new StringValue("Name", "名字", "Bot");
    private final BoolValue damage = new BoolValue("Damage", "伤害", true);
    private final BoolValue record = new BoolValue("Record", "记录", false);
    private final BoolValue play = new BoolValue("Play", "播放", false);
    private final BoolValue autoTotem = new BoolValue("AutoTotem", "自动图腾", false);

    public static OtherClientPlayerEntity fakePlayer;
    private final List<PlayerState> positions = new ArrayList<>();
    private int movementTick, deathTime;

    @Override
    protected void onEnable() {
        if (nullCheck()) {
            setState(false);
            return;
        }

        fakePlayer = new OtherClientPlayerEntity(mc.world, new GameProfile(UUID.fromString("11451466-6666-6666-6666-666666666600"), name.get())) {
            @Override
            public boolean isOnGround() {
                return true;
            }
        };

        fakePlayer.getInventory().clone(mc.player.getInventory());
        fakePlayer.setId(-1919810);
        mc.world.addEntity(fakePlayer);
        fakePlayer.copyPositionAndRotation(mc.player);
        fakePlayer.bodyYaw = mc.player.bodyYaw;
        fakePlayer.headYaw = mc.player.headYaw;
        fakePlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 9999, 2));
        fakePlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 9999, 3));
        fakePlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 9999, 1));

        setSuffix(name.get());
    }

    @Override
    protected void onDisable() {
        if (fakePlayer == null) return;
        fakePlayer.setRemoved(Entity.RemovalReason.DISCARDED);
        fakePlayer.onRemoved();
        fakePlayer = null;
        positions.clear();
        deathTime = 0;
    }

    @EventHandler
    private void onMotion(MotionEvent event) {
        if (record.get()) {
            positions.add(new PlayerState(mc.player.getX(), mc.player.getY(), mc.player.getZ(), mc.player.getYaw(), mc.player.getPitch()));
            return;
        }

        if (fakePlayer != null) {
            if (play.get() && !positions.isEmpty()) {
                movementTick++;
                if (movementTick >= positions.size()) {
                    movementTick = 0;
                    return;
                }

                PlayerState p = positions.get(movementTick);
                fakePlayer.setYaw(p.yaw);
                fakePlayer.setPitch(p.pitch);
                fakePlayer.setHeadYaw(p.yaw);

                fakePlayer.updateTrackedPosition(p.x, p.y, p.z);
                fakePlayer.updateTrackedPositionAndAngles(p.x, p.y, p.z, p.yaw, p.pitch, 3);
            } else movementTick = 0;

            if (fakePlayer.isDead()) {
                deathTime++;
                if (deathTime > 10) setState(false);
            }
        }
    }

    @EventHandler
    private void onPacket(PacketEvent event) {
        if (nullCheck()) return;
        if (event.getType() != EventType.RECEIVE) return;
        if (fakePlayer == null) return;

        if (!(fakePlayer.isAlive() && fakePlayer.clientWorld == mc.world)) {
            setState(false);
            return;
        }

        if (autoTotem.get() && fakePlayer.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING) {
            fakePlayer.setStackInHand(Hand.OFF_HAND, new ItemStack(Items.TOTEM_OF_UNDYING));
        }

        fakePlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 9999, 2));

        if (damage.get() && fakePlayer.hurtTime == 0 && event.getPacket() instanceof ExplosionS2CPacket explosion) {
            Vec3d center = explosion.center();
            double distance = center.distanceTo(fakePlayer.getPos());

            if (distance > 12) return;

            float dmg = calculateSimpleDamage(distance);

            fakePlayer.onDamaged(mc.world.getDamageSources().generic());

            if (fakePlayer.getAbsorptionAmount() >= dmg) {
                fakePlayer.setAbsorptionAmount(fakePlayer.getAbsorptionAmount() - dmg);
            } else {
                float remaining = dmg - fakePlayer.getAbsorptionAmount();
                fakePlayer.setAbsorptionAmount(0);
                fakePlayer.setHealth(fakePlayer.getHealth() - remaining);
            }

            if (fakePlayer.getHealth() <= 0) {
                if (fakePlayer.getOffHandStack().getItem() == Items.TOTEM_OF_UNDYING) {
                    fakePlayer.setHealth(1f);
                    fakePlayer.setAbsorptionAmount(8f);
                    fakePlayer.clearStatusEffects();
                    fakePlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
                    fakePlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
                    fakePlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
                    fakePlayer.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY);

                    EntityStatusS2CPacket packet = new EntityStatusS2CPacket(fakePlayer, EntityStatuses.USE_TOTEM_OF_UNDYING);
                    Zeta.EVENT_BUS.post(new PacketEvent(EventType.RECEIVE, packet));
                    packet.apply(mc.getNetworkHandler());
                }
            }
        }
    }

    private float calculateSimpleDamage(double distance) {
        float power = 6.0f;
        double impact = (1.0 - (distance / (power * 2.0))) * 0.5;
        if (impact < 0) impact = 0;
        float baseDamage = (float) ((impact * impact + impact) / 2.0 * 7.0 * (power * 2.0) + 1.0);
        int resistance = 0;
        if (fakePlayer.hasStatusEffect(StatusEffects.RESISTANCE)) {
            var effect = fakePlayer.getStatusEffect(StatusEffects.RESISTANCE);
            if (effect != null) {
                resistance = effect.getAmplifier() + 1;
            }
        }
        baseDamage = baseDamage * (1 - (resistance * 0.2f));
        return Math.max(0, baseDamage * 0.5f);
    }

    private record PlayerState(double x, double y, double z, float yaw, float pitch) {
    }
}