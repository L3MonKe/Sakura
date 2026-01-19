package dev.lemonclient.client.events.entity;

import dev.lemonclient.client.events.Cancellable;
import net.minecraft.entity.Entity;

public class BlockPushEvent extends Cancellable {
    private final Entity entity;

    public BlockPushEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
