package dev.mahiro.client.module.impl.render;

import dev.mahiro.client.events.packet.PacketEvent;
import dev.mahiro.client.events.type.EventType;
import dev.mahiro.client.mixin.accessor.IPlayerInteractEntityC2SPacket;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.values.impl.EnumValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

public class KillEffect extends Module {

    public enum Mode {
        Blood,
        Lightning
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Blood);

    private int lastTargetId = -1;
    private long lastAttackTime;

    public KillEffect() {
        super("KillEffect", "击杀特效", Category.Render);
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (mc.world == null || mc.player == null) return;

        if (event.getType() == EventType.SEND && event.getPacket() instanceof PlayerInteractEntityC2SPacket packet) {
            lastTargetId = ((IPlayerInteractEntityC2SPacket) packet).getEntityId();
            lastAttackTime = System.currentTimeMillis();
        }
        
        if (event.getType() == EventType.RECEIVE && event.getPacket() instanceof EntityStatusS2CPacket packet) {
            if (packet.getStatus() == 3) { // Death status
                Entity entity = packet.getEntity(mc.world);
                if (entity != null && entity.getId() == lastTargetId) {
                     if (entity instanceof PlayerEntity && entity != mc.player) {
                         if (System.currentTimeMillis() - lastAttackTime < 5000) { // 增加到5秒
                             triggerEffect(entity);
                             lastTargetId = -1;
                         }
                     }
                }
            }
        }
    }

    private void triggerEffect(Entity entity) {
        Vec3d pos = entity.getPos();
        switch (mode.get()) {
            case Blood -> {
                // Redstone block break particles
                for (int i = 0; i < 200; i++) {
                    mc.world.addParticle(
                        new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.REDSTONE_BLOCK.getDefaultState()),
                        pos.x + (Math.random() - 0.5),
                        pos.y + Math.random() * 2,
                        pos.z + (Math.random() - 0.5),
                        (Math.random() - 0.5) * 0.2,
                        Math.random() * 0.2,
                        (Math.random() - 0.5) * 0.2
                    );
                }
                // Stone place sound
                mc.world.playSound(pos.x, pos.y, pos.z, SoundEvents.BLOCK_STONE_PLACE, SoundCategory.PLAYERS, 10.0f, 1.0f, false);
            }
            case Lightning -> {
                // Lightning visual
                LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, mc.world);
                lightning.setPosition(pos);
                mc.world.addEntity(lightning);
                
                // Lightning sound
                mc.world.playSound(pos.x, pos.y, pos.z, SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.WEATHER, 1.0f, 1.0f, false);
            }
        }
    }
}
