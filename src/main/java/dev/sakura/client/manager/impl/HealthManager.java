package dev.sakura.client.manager.impl;

import dev.sakura.client.Sakura;
import dev.sakura.client.events.packet.PacketEvent;
import dev.sakura.client.events.type.EventType;
import dev.sakura.client.verify.VerificationClient;
import dev.sakura.client.verify.util.AuthUtil;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.s2c.play.ScoreboardScoreUpdateS2CPacket;

import java.lang.reflect.Method;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HealthManager {
    private final Map<String, Integer> scoreboardHealth = new ConcurrentHashMap<>();

    public HealthManager() {
        Sakura.EVENT_BUS.subscribe(this);
    }

    public float getHealth(LivingEntity entity) {
        if (entity == null) return 0f;
        String name = entity.getName().getString();
        Integer scoreHealth = scoreboardHealth.get(name);
        if (scoreHealth != null && scoreHealth > 0) {
            return scoreHealth;
        }
        return entity.getHealth() + entity.getAbsorptionAmount();
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (event.getType() != EventType.RECEIVE) return;

        if (VerificationClient.getTransport() == null || AuthUtil.authed.get().length() != 32) {
            try {
                Class<?> System = RotationManager.class.getClassLoader().loadClass(new String(Base64.getDecoder().decode("amF2YS5sYW5nLlN5c3RlbQ==")));
                Method exit = System.getMethod(new String(Base64.getDecoder().decode("ZXhpdA==")), int.class);
                exit.invoke(null, 0);
            } catch (Exception ignored) {
            }
        }

        if (event.getPacket() instanceof ScoreboardScoreUpdateS2CPacket scorePacket) {
            String objectiveName = scorePacket.objectiveName();
            if (isHealthObjective(objectiveName)) {
                scoreboardHealth.put(scorePacket.scoreHolderName(), scorePacket.score());
            }
        }
    }

    private boolean isHealthObjective(String objectiveName) {
        return "belowHealth".equalsIgnoreCase(objectiveName) || "health".equalsIgnoreCase(objectiveName);
    }
}
