package dev.mahiro.client.interfaces;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public interface IEntityRenderState {
    Entity getEntity();

    void setEntity(Entity entity);
}
