package dev.mahiro.client.interfaces;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public interface IEntityRenderState {
    LivingEntity getEntity();

    void setEntity(final Entity entity);
}
