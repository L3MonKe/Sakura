package dev.mzc.client.module.impl.player;

import dev.mzc.client.utils.combat.DamageUtil;
import com.mojang.authlib.GameProfile;
import dev.mzc.client.Sakura;
import dev.mzc.client.events.EventType;
import dev.mzc.client.events.entity.AttackEvent;
import dev.mzc.client.events.packet.PacketEvent;
import dev.mzc.client.events.player.MotionEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.StringValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.attribute.EntityAttributes;
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
        this.setType(ModuleType.Safe);
    }

    private final StringValue name = new StringValue("Name", "名字", "Bot");
    private final BoolValue copyInv = new BoolValue("CopyInv", "复制背包", true);
    private final BoolValue damage = new BoolValue("Damage", "伤害", true);
    private final BoolValue record = new BoolValue("Record", "记录", false);
    private final BoolValue play = new BoolValue("Play", "播放", false);
    private final BoolValue gapple = new BoolValue("Gapple", "模拟金苹果", true);

    private final BoolValue autoTotem = new BoolValue("AutoTotem", "自动图腾", true);
    public static OtherClientPlayerEntity fakePlayer;
    private final List<PlayerState> positions = new ArrayList<>();

    private int movementTick, deathTime, regenTick;
    @Override
    protected void onEnable() {
        if (nullCheck()) {
            setState(false);
            return;
        }
        
        regenTick = 0;

        fakePlayer = new OtherClientPlayerEntity(mc.world, new GameProfile(UUID.fromString("11451466-6666-6666-6666-666666666600"), name.get())) {
            @Override
            public boolean isOnGround() {
                return true;
            }
        };

        if (copyInv.get()) fakePlayer.getInventory().clone(mc.player.getInventory());
        fakePlayer.setId(-1919810);
        mc.world.addEntity(fakePlayer);
        fakePlayer.copyPositionAndRotation(mc.player);
        fakePlayer.bodyYaw = mc.player.bodyYaw;
        fakePlayer.headYaw = mc.player.headYaw;
        if (gapple.get()) {
            fakePlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 9999, 2));
            fakePlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 9999, 3));
            fakePlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 9999, 1));
        }

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
            
            // Simulate regeneration
            if (fakePlayer.hasStatusEffect(StatusEffects.REGENERATION) && fakePlayer.getHealth() < fakePlayer.getMaxHealth()) {
                regenTick++;
                int amplifier = fakePlayer.getStatusEffect(StatusEffects.REGENERATION).getAmplifier();
                // Regen I (0): 50 ticks, Regen II (1): 25 ticks, Regen III (2): 12 ticks
                int ticks = 50 >> amplifier;
                if (ticks > 0 && regenTick % ticks == 0) {
                    fakePlayer.heal(1.0f);
                }
            } else {
                regenTick = 0;
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

        if (gapple.get()) fakePlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 9999, 2));

        if (damage.get() && fakePlayer.hurtTime == 0 && event.getPacket() instanceof ExplosionS2CPacket explosion) {
            Vec3d center = explosion.center();
            double distance = center.distanceTo(fakePlayer.getPos());

            if (distance > 12) return;

            float dmg = DamageUtil.calculateCrystalDamage(fakePlayer, center);

            fakePlayer.onDamaged(mc.world.getDamageSources().generic());

            handleDamage(dmg);
        }
    }

    @EventHandler
    private void onAttack(AttackEvent event) {
        if (fakePlayer == null || event.getTargetEntity() != fakePlayer) return;
        if (!damage.get()) return;

        fakePlayer.onDamaged(mc.world.getDamageSources().playerAttack(mc.player));

        float dmg = (float) DamageUtil.getAttackDamage(mc.player.getMainHandStack(), mc.player);
        
        float cooldown = mc.player.getAttackCooldownProgress(0.5f);
        dmg *= (0.2f + cooldown * cooldown * 0.8f);

        if (dmg <= 0) return;

        boolean critical = cooldown > 0.9f && mc.player.fallDistance > 0.0F && !mc.player.isOnGround() && !mc.player.isClimbing() && !mc.player.isTouchingWater() && !mc.player.hasStatusEffect(StatusEffects.BLINDNESS) && !mc.player.isRiding() && !mc.player.isSprinting();
        if (critical) dmg *= 1.5F;
        
        // Apply Armor
        dmg = (float) DamageUtil.applyArmor(fakePlayer, dmg);
        // Apply Resistance
        dmg = (float) DamageUtil.applyResistance(fakePlayer, dmg);
        // Apply Protection Enchantments
        dmg = (float) DamageUtil.applyProtection(fakePlayer, dmg, false);

        handleDamage(dmg);
    }

    private void handleDamage(float dmg) {
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
                Sakura.EVENT_BUS.post(new PacketEvent(EventType.RECEIVE, packet));
                if (mc.getNetworkHandler() != null) packet.apply(mc.getNetworkHandler());
            }
        }
    }

    private record PlayerState(double x, double y, double z, float yaw, float pitch) {
    }
}