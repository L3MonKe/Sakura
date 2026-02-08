package dev.sakura.client.events.entity;

import dev.sakura.client.events.Cancellable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public class AttackEntityEvent extends Cancellable {
    private final PlayerEntity player;
    private final Entity entity;

    public AttackEntityEvent(PlayerEntity player, Entity entity) {
        this.player = player;
        this.entity = entity;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public Entity getEntity() {
        return entity;
    }
}
