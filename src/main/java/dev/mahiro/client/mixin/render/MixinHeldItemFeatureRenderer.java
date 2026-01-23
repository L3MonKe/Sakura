package dev.mahiro.client.mixin.render;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.module.impl.render.OldHitting;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemFeatureRenderer.class)
public class MixinHeldItemFeatureRenderer {
    @Inject(method = "renderItem", at = @At("HEAD"), cancellable = true)
    private void onRender(ArmedEntityRenderState entityState, ItemRenderState itemState, Arm arm, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (arm == Arm.LEFT) {
            OldHitting oldHitting = Mahiro.MODULES.getModule(OldHitting.class);

            if (oldHitting.isEnabled() && !oldHitting.visibleOffHand.get()) {
                ci.cancel();
            }
        }
    }
}
