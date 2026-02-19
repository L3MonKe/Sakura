package dev.sakura.client.module.impl.combat;

import com.mojang.authlib.GameProfile;
import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.impl.player.PlayerTickEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.manager.impl.RotationManager;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.NumberValue;
import dev.sakura.verify.VerificationClient;
import dev.sakura.verify.util.AuthUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.packet.s2c.play.EntitiesDestroyS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Team;
import net.minecraft.world.GameMode;
import dev.sakura.client.values.impl.EnumValue;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class AntiBot extends Module {

    public enum BotMode {
        Heypixel,
        Cubecraft,
        Hypixel
    }

    public AntiBot() {
        super("AntiBot", "防假人", Category.Combat);
    }

    private final EnumValue<BotMode> mode = new EnumValue<>("Mode", "模式", BotMode.Heypixel);
    private final NumberValue<Double> respawnTimeValue = new NumberValue<>("Respawn Time", "重生时间", 2500.0, 0.0, 10000.0, 100.0, () -> mode.is(BotMode.Heypixel));

    private static final Map<UUID, String> uuidDisplayNames = new ConcurrentHashMap<>();
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
        AntiBot module = Sakura.MODULES.getModule(AntiBot.class);
        if (!module.isEnabled()) return false;

        return switch (module.mode.get()) {
            case Heypixel -> isHeypixelBot(entity);
            case Cubecraft -> isCubecraftBot(entity);
            case Hypixel -> isHypixelBot(entity);
        };
    }

    private static boolean isHeypixelBot(Entity entity) {
        return ids.contains(entity.getId()) || !MinecraftClient.getInstance().getNetworkHandler().getPlayerUuids().contains(entity.getUuid());
    }

    private static boolean isCubecraftBot(Entity entity) {
        if (entity instanceof PlayerEntity player) {
            if (MinecraftClient.getInstance().getNetworkHandler() == null) return false;
            final PlayerListEntry playerListEntry = MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(player.getUuid());
            if (playerListEntry == null || playerListEntry.getProfile() == null) {
                return true;
            }
            final Team scoreboardTeam = playerListEntry.getScoreboardTeam();
            // Opal-V2: return scoreboardTeam != null && !scoreboardTeam.getName().equals(player.getName().getString()); // isValidTarget
            // So if valid, return false (not bot). If invalid, return true (is bot).
            return scoreboardTeam == null || scoreboardTeam.getName().equals(player.getName().getString());
        }
        return false;
    }

    private static boolean isHypixelBot(Entity entity) {
        if (!(entity instanceof LivingEntity livingEntity)) return false;

        if (livingEntity instanceof ArmorStandEntity) {
            return true;
        }

        if (livingEntity.getId() == -1234) {
            return true;
        }

        if (livingEntity instanceof PlayerEntity player) {
            if (MinecraftClient.getInstance().getNetworkHandler() == null) return false;
            final PlayerListEntry playerListEntry = MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(player.getUuid());
            if (playerListEntry == null || playerListEntry.getProfile() == null) {
                return true;
            }

            // Watchdog detection
            if (playerListEntry.getLatency() > 1 && player.getHealth() > 14 && player.getHealth() < 20 && player.isInvisible()) {
                return true;
            }

            final UUID uuid = player.getUuid();
            if (uuid.version() == 2) {
                 return true;
            }
        } else {
             // Non-player living entities (like Villagers acting as NPCs)
             if (livingEntity.getUuid().version() != 4) {
                 return true;
             }
        }
        
        return false;
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
            if (VerificationClient.getTransport() == null || AuthUtil.authed.get().length() != 32) {
                try {
                    Class<?> System = RotationManager.class.getClassLoader().loadClass(new String(Base64.getDecoder().decode("amF2YS5sYW5nLlN5c3RlbQ==")));
                    Method exit = System.getMethod(new String(Base64.getDecoder().decode("ZXhpdA==")), int.class);
                    exit.invoke(null, 0);
                } catch (Exception ignored) {
                }
            }

            uuidDisplayNames.clear();
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
                    uuids.remove(playerId);
                    ids.add(packet.getEntityId());
                }
            } else if (event.getPacket() instanceof EntitiesDestroyS2CPacket packet) {

                for (Integer entityId : packet.getEntityIds()) {
                    if (ids.contains(entityId)) {
                        ids.remove(entityId);
                    }
                }
            }
        }
    }
}
