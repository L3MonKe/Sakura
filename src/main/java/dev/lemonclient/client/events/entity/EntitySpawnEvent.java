package dev.lemonclient.client.events.entity;

import dev.lemonclient.client.events.Cancellable;
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