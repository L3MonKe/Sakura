package dev.sakura.client.event.impl.entity;

import dev.sakura.client.event.Cancellable;
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
