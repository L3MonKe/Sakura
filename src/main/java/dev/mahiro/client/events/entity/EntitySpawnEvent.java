package dev.mahiro.client.events.entity;

import dev.mahiro.client.events.Cancellable;
import net.minecraft.entity.Entity;

public class EntitySpawnEvent extends Cancellable {
    private final Entity entity;

    public EntitySpawnEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}