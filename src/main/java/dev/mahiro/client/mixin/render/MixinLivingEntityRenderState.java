package dev.mahiro.client.mixin.render;

import dev.mahiro.client.interfaces.IEntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntityRenderState.class)
public class MixinLivingEntityRenderState implements IEntityRenderState {
    @Unique
    private LivingEntity entity;

    @Override
    public LivingEntity getEntity() {
        return entity;
    }

    @Override
    public void setEntity(final Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            this.entity = livingEntity;
        } else {
            this.entity = null;
        }
    }
}
