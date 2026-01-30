package dev.mahiro.client.interfaces;

import net.minecraft.entity.Entity;

public interface IEntityRenderState {
    Entity getEntity();

    void setEntity(Entity entity);
}
