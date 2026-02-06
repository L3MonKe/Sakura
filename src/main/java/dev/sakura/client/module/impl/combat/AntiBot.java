package dev.sakura.client.module.impl.combat;

import com.mojang.authlib.GameProfile;
import dev.sakura.client.Sakura;
import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.events.packet.PacketEvent;
import dev.sakura.client.events.player.PlayerTickEvent;
import dev.sakura.client.events.type.EventType;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.packet.s2c.play.EntitiesDestroyS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.world.GameMode;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AntiBot extends Module {
    public AntiBot() {
        super("AntiBot", "防假人", Category.Combat);
    }

    private final NumberValue<Double> respawnTimeValue = new NumberValue<>("Respawn Time", "重生时间", 2500.0, 0.0, 10000.0, 100.0);

    private static final Map<UUID, String> uuidDisplayNames = new ConcurrentHashMap<>();
    //private static final Map<Integer, String> entityIdDisplayNames = new ConcurrentHashMap<>();
    private static final Map<UUID, Long> uuids = new ConcurrentHashMap<>();
    private static final Set<Integer> ids = new HashSet<>();
    private static final Map<UUID, Long> respawnTime = new ConcurrentHashMap<>();

    public static boolean isBedWarsBot(Entity entity) {
        AntiBot module = Sakura.MODULES.getModule(AntiBot.class);
        if (module.respawnTimeValue.get() < 1.0F) {
            return false;
        } else {
            return respawnTime.containsKey(entity.getUuid()) && (float) (System.currentTimeMillis() - respawnTime.get(entity.getUuid())) < module.respawnTimeValue.get();
        }
    }

    public static boolean isBot(Entity entity) {
        return (ids.contains(entity.getId()) || !MinecraftClient.getInstance().getNetworkHandler().getPlayerUuids().contains(entity.getUuid())) && Sakura.MODULES.getModule(AntiBot.class).isEnabled();
    }

    @EventHandler
    public void bedWarsBot(PacketEvent e) {
        if (nullCheck()) return;

        if (e.getType() == EventType.RECEIVE) {
            if (e.getPacket() instanceof PlayerListS2CPacket packet) {
                if (packet.getActions().contains(PlayerListS2CPacket.Action.ADD_PLAYER)) {
                    for (PlayerListS2CPacket.Entry entry : packet.getEntries()) {
                        GameProfile profile = entry.profile();
                        if (profile == null) return;
                        UUID id = profile.id();
                        respawnTime.put(id, System.currentTimeMillis());
                    }
                }
            } else if (e.getPacket() instanceof EntityAnimationS2CPacket packet) {
                Entity entity = mc.world.getEntityById(packet.getEntityId());
                if (entity != null && packet.getAnimationId() == 0) {
                    respawnTime.remove(entity.getUuid());
                }
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerTickEvent event) {
        if (mc.player.age <= 1) {
            uuidDisplayNames.clear();
            //entityIdDisplayNames.clear();
            ids.clear();
            uuids.clear();
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        for (Map.Entry<UUID, Long> entry : uuids.entrySet()) {
            if (System.currentTimeMillis() - entry.getValue() > 500L) {
                uuids.remove(entry.getKey());
            }
        }
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (event.getType() == EventType.RECEIVE) {
            if (event.getPacket() instanceof PlayerListS2CPacket packet) {
                if (packet.getActions().contains(PlayerListS2CPacket.Action.ADD_PLAYER)) {
                    for (PlayerListS2CPacket.Entry entry : packet.getEntries()) {
                        if (entry.profile() != null && entry.displayName() != null && entry.displayName().getSiblings().isEmpty() && entry.gameMode() == GameMode.SURVIVAL) {
                            UUID uuid = entry.profile().id();
                            uuids.put(uuid, System.currentTimeMillis());
                            uuidDisplayNames.put(uuid, entry.displayName().getString());
                        }
                    }
                }
            } else if (event.getPacket() instanceof EntitySpawnS2CPacket packet && packet.getEntityType() == EntityType.PLAYER) {
                UUID playerId = packet.getUuid();
                if (uuids.containsKey(playerId)) {
                    String displayName = uuidDisplayNames.get(playerId);
                    //entityIdDisplayNames.put(packet.getEntityId(), displayName);
                    uuids.remove(playerId);
                    ids.add(packet.getEntityId());
                }
            } else if (event.getPacket() instanceof EntitiesDestroyS2CPacket packet) {

                for (Integer entityId : packet.getEntityIds()) {
                    if (ids.contains(entityId)) {
                        //String displayName = entityIdDisplayNames.get(entityId);
                        ids.remove(entityId);
                    }
                }
            }
        }
    }
}
