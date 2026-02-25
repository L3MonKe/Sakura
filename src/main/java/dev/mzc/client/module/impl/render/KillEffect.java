package dev.mzc.client.module.impl.render;

import dev.mzc.client.events.packet.PacketEvent;
import dev.mzc.client.events.EventType;
import dev.mzc.client.mixin.accessor.IPlayerInteractEntityC2SPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.World;
import java.lang.reflect.Field;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import meteordevelopment.orbit.EventHandler;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.util.math.Vec3d;

public class KillEffect extends Module {
    public enum Mode {
        Lightning("闪电"),
        Fire("火焰"),
        Totem("图腾"),
        Heart("爱心"),
        Cloud("云雾"),
        Experience("经验");

        private final String cnName;

        Mode(String cnName) {
            this.cnName = cnName;
        }

        public String getCnName() {
            return cnName;
        }
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Lightning);
    private final NumberValue<Integer> orbCount = new NumberValue<>("OrbCount", "经验球数量", 5, 1, 30, 1, () -> mode.get() == Mode.Experience);
    private final BoolValue sound = new BoolValue("Sound", "声音", true);
    private final BoolValue players = new BoolValue("Players", "玩家", true);
    private final BoolValue mobs = new BoolValue("Mobs", "怪物", true);
    private final BoolValue animals = new BoolValue("Animals", "动物", false);

    private Entity lastAttackedEntity;
    private long lastAttackTime;

    public KillEffect() {
        super("KillEffect", "击杀特效", Category.Render);
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (mc.world == null || mc.player == null) return;

        // Track attacks
        if (event.getType() == EventType.SEND && event.getPacket() instanceof PlayerInteractEntityC2SPacket packet) {
            int id = ((IPlayerInteractEntityC2SPacket) packet).getEntityId();
            Entity entity = mc.world.getEntityById(id);
            if (entity != null) {
                lastAttackedEntity = entity;
                lastAttackTime = System.currentTimeMillis();
            }
        }

        // Detect kills
        if (event.getType() == EventType.RECEIVE && event.getPacket() instanceof EntityStatusS2CPacket packet) {
            if (packet.getStatus() == 3) { // Death status
                Entity entity = packet.getEntity(mc.world);
                if (entity == null) return;

                if (shouldTrigger(entity)) {
                    triggerEffect(entity);
                }
            }
        }
    }

    private boolean shouldTrigger(Entity entity) {
        if (entity == mc.player) return false;

        // Check if it's the target we attacked
        boolean isTarget = (entity == lastAttackedEntity || (lastAttackedEntity != null && entity.getId() == lastAttackedEntity.getId()));

        // Check for End Crystal explosion kills (if we attacked a crystal and it killed a player)
        if (!isTarget && lastAttackedEntity instanceof EndCrystalEntity && entity instanceof LivingEntity) {
            if (entity.distanceTo(lastAttackedEntity) <= 12.0) {
                isTarget = true;
            }
        }

        // Check time window (5 seconds)
        if (isTarget && System.currentTimeMillis() - lastAttackTime < 5000) {
            if (entity instanceof PlayerEntity) {
                return players.get();
            } else if (entity instanceof Monster) {
                return mobs.get();
            } else {
                return animals.get();
            }
        }
        
        return false;
    }

    private void triggerEffect(Entity entity) {
        if (mode.is(Mode.Lightning)) {
            LightningEntity lightningEntity = new LightningEntity(EntityType.LIGHTNING_BOLT, mc.world);
            lightningEntity.setPosition(entity.getPos());
            mc.world.addEntity(lightningEntity);
        } else if (mode.is(Mode.Fire)) {
            for (int i = 0; i < 20; i++) {
                mc.world.addParticle(ParticleTypes.FLAME, entity.getX(), entity.getY() + entity.getHeight() / 2, entity.getZ(), (Math.random() - 0.5) * 0.5, (Math.random() - 0.5) * 0.5, (Math.random() - 0.5) * 0.5);
            }
        } else if (mode.is(Mode.Totem)) {
            mc.particleManager.addEmitter(entity, ParticleTypes.TOTEM_OF_UNDYING, 30);
        } else if (mode.is(Mode.Heart)) {
            float width = entity.getWidth();
            float height = entity.getHeight();
            for (int i = 0; i < 10; i++) {
                mc.world.addParticle(ParticleTypes.HEART, 
                    entity.getX() + (Math.random() - 0.5) * width, 
                    entity.getY() + Math.random() * height, 
                    entity.getZ() + (Math.random() - 0.5) * width, 
                    (Math.random() - 0.5) * 0.5, Math.random() * 0.5, (Math.random() - 0.5) * 0.5);
            }
        } else if (mode.is(Mode.Cloud)) {
            for (int i = 0; i < 20; i++) {
                mc.world.addParticle(ParticleTypes.CLOUD, entity.getX(), entity.getY() + entity.getHeight() / 2, entity.getZ(), (Math.random() - 0.5) * 0.2, (Math.random() - 0.5) * 0.2, (Math.random() - 0.5) * 0.2);
            }
        } else if (mode.is(Mode.Experience)) {
            // Spawn client-side experience orb entities to simulate the real effect
            int count = orbCount.get();
            for (int i = 0; i < count; i++) {
                double offsetX = (Math.random() - 0.5) * 0.5;
                double offsetZ = (Math.random() - 0.5) * 0.5;
                ExperienceOrbEntity orb = new ClientExperienceOrbEntity(mc.world, entity.getX() + offsetX, entity.getY(), entity.getZ() + offsetZ, 20); // 20 XP per orb
                
                // Add some random velocity to spread them out
                orb.setVelocity((Math.random() - 0.5) * 0.5, Math.random() * 0.5, (Math.random() - 0.5) * 0.5);
                mc.world.addEntity(orb);
            }
            
            // Spawn extra particles for burst effect
            for (int i = 0; i < 20; i++) {
                mc.world.addParticle(ParticleTypes.HAPPY_VILLAGER, 
                    entity.getX() + (Math.random() - 0.5) * 1.0, 
                    entity.getY() + Math.random() * 1.5, 
                    entity.getZ() + (Math.random() - 0.5) * 1.0, 
                    (Math.random() - 0.5) * 0.1, 0.1, (Math.random() - 0.5) * 0.1);
            }
        }

        if (sound.get()) {
            if (mode.is(Mode.Lightning)) {
                mc.world.playSound(mc.player, entity.getBlockPos(), SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.WEATHER, 1.0f, 1.0f);
            } else if (mode.is(Mode.Fire)) {
                mc.world.playSound(mc.player, entity.getBlockPos(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.WEATHER, 1.0f, 1.0f);
            } else if (mode.is(Mode.Totem)) {
                mc.world.playSound(mc.player, entity.getBlockPos(), SoundEvents.ITEM_TOTEM_USE, SoundCategory.WEATHER, 1.0f, 1.0f);
            }
            // Experience sound is handled by the orb entities themselves when picked up
        }
    }

    private static class ClientExperienceOrbEntity extends ExperienceOrbEntity {
        public ClientExperienceOrbEntity(World world, double x, double y, double z, int amount) {
            super(world, x, y, z, amount);
            // Set pickupDelay to 0 immediately via reflection
            try {
                Field pickupDelayField;
                try {
                    pickupDelayField = ExperienceOrbEntity.class.getDeclaredField("pickupDelay");
                } catch (NoSuchFieldException e) {
                    pickupDelayField = ExperienceOrbEntity.class.getDeclaredField("field_7222"); // Intermediary mapping
                }
                pickupDelayField.setAccessible(true);
                pickupDelayField.setInt(this, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void tick() {
            super.tick();
            // Client-side pickup simulation logic
            if (this.getWorld().isClient) {
                PlayerEntity player = MinecraftClient.getInstance().player;
                if (player != null && player.getBoundingBox().intersects(this.getBoundingBox())) {
                    this.onPlayerCollision(player);
                }
            }
        }

        @Override
        public void onPlayerCollision(PlayerEntity player) {
            if (this.getWorld().isClient) {
                // Manually trigger pickup effects and removal on client side
                this.getWorld().playSound(player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.1F, (this.random.nextFloat() - this.random.nextFloat()) * 0.35F + 0.9F, false);
                this.discard();
            } else {
                super.onPlayerCollision(player);
            }
        }
    }
}

