package dev.sakura.client.events.entity;

import net.minecraft.entity.Entity;

public class AttackEvent {
    private final Entity targetEntity;

    public AttackEvent(Entity targetEntity) {
        this.targetEntity = targetEntity;
    }

    public Entity getTargetEntity() {
        return targetEntity;
    }
}
