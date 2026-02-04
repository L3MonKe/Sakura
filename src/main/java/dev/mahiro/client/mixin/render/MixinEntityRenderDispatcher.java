package dev.mahiro.client.mixin.render;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.mahiro.client.interfaces.IEntityRenderState;
import net.minecraft.client.render.entity.EntityRenderManager;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityRenderManager.class)
public class MixinEntityRenderDispatcher {
    @ModifyReturnValue(method = "getAndUpdateRenderState", at = @At("RETURN"))
    private <E extends Entity> EntityRenderState hookGetAndUpdateRenderState(EntityRenderState state, E entity, float tickProgress) {
        if (state instanceof IEntityRenderState entityRenderState) {
            entityRenderState.setEntity(entity);
        }
        return state;
    }
}
