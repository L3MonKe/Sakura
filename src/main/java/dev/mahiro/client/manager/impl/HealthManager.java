package dev.mahiro.client.manager.impl;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.events.packet.PacketEvent;
import dev.mahiro.client.events.type.EventType;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.s2c.play.ScoreboardScoreUpdateS2CPacket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HealthManager {
    private final Map<String, Integer> scoreboardHealth = new ConcurrentHashMap<>();

    public HealthManager() {
        Mahiro.EVENT_BUS.subscribe(this);
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
