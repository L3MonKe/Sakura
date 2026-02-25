package dev.sakura.client.events.entity;

import dev.sakura.client.events.Cancellable;
import net.minecraft.entity.Entity;

public class EntityPushEvent extends Cancellable {
    private final Entity entity;
    private final Entity pushedBy;

    public EntityPushEvent(Entity entity, Entity pushedBy) {
        this.entity = entity;
        this.pushedBy = pushedBy;
    }

    public Entity getEntity() {
        return entity;
    }

    public Entity getPushedBy() {
        return pushedBy;
    }
}
