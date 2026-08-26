package dev.sakura.client.manager.impl;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.type.EventType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.s2c.play.ScoreboardScoreUpdateS2CPacket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HealthManager {
    private final Map<String, Integer> scoreboardHealth = new ConcurrentHashMap<>();

    public HealthManager() {
        Sakura.EVENT_BUS.subscribe(this);
    }

    public float getHealth(Entity entity) {
        if (entity == null) return 0f;
        if (entity instanceof LivingEntity livingEntity) {
            String name = livingEntity.getName().getString();
            Integer scoreHealth = scoreboardHealth.get(name);
            if (scoreHealth != null && scoreHealth > 0) {
                return scoreHealth;
            }
            return livingEntity.getHealth() + livingEntity.getAbsorptionAmount();
        }
        return 0f;
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
