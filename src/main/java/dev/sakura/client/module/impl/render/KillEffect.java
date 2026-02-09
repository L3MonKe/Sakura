package dev.sakura.client.module.impl.render;

import dev.sakura.client.events.packet.PacketEvent;
import dev.sakura.client.events.type.EventType;
import dev.sakura.client.mixin.accessor.IPlayerInteractEntityC2SPacket;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class KillEffect extends Module {

    public enum Mode {
        Blood,
        Lightning
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Blood);
    private final BoolValue playersOnly = new BoolValue("PlayersOnly", "仅玩家", true);

    private final Map<Integer, Long> recentAttacks = new ConcurrentHashMap<>();

    public KillEffect() {
        super("KillEffect", "击杀特效", Category.Render);
    }

    @Override
    protected void onEnable() {
        recentAttacks.clear();
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (mc.world == null || mc.player == null) return;

        if (event.getType() == EventType.SEND && event.getPacket() instanceof PlayerInteractEntityC2SPacket packet) {
            int entityId = ((IPlayerInteractEntityC2SPacket) packet).getEntityId();
            recentAttacks.put(entityId, System.currentTimeMillis());

            // Cleanup old entries
            long now = System.currentTimeMillis();
            recentAttacks.entrySet().removeIf(entry -> now - entry.getValue() > 5000);
        }

        if (event.getType() == EventType.RECEIVE && event.getPacket() instanceof EntityStatusS2CPacket packet) {
            if (packet.getStatus() == 3) { // Death status
                Entity entity = packet.getEntity(mc.world);
                if (entity != null) {
                    if (playersOnly.get() && !(entity instanceof PlayerEntity)) return;
                    if (entity == mc.player) return;

                    Long time = recentAttacks.get(entity.getId());
                    if (time != null) {
                        if (System.currentTimeMillis() - time < 5000) {
                            triggerEffect(entity);
                            recentAttacks.remove(entity.getId());
                        }
                    }
                }
            }
        }
    }

    private void triggerEffect(Entity entity) {
        Vec3d pos = entity.getEntityPos();
        switch (mode.get()) {
            case Blood -> {
                // Redstone block break particles
                for (int i = 0; i < 200; i++) {
                    mc.world.addParticleClient(
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
                mc.world.playSoundClient(pos.x, pos.y, pos.z, SoundEvents.BLOCK_STONE_PLACE, SoundCategory.PLAYERS, 10.0f, 1.0f, false);
            }
            case Lightning -> {
                // Lightning visual
                LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, mc.world);
                lightning.setPosition(pos);
                mc.world.addEntity(lightning);

                // Lightning sound
                mc.world.playSoundClient(pos.x, pos.y, pos.z, SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.WEATHER, 1.0f, 1.0f, false);
            }
        }
    }
}
