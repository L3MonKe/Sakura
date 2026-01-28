package dev.mahiro.client.mixin.render;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.events.render.item.RenderItemEntityEvent;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.render.entity.state.ItemEntityRenderState;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.mahiro.client.Mahiro.mc;

@Mixin(ItemEntityRenderer.class)
public class MixinItemEntityRenderer {
    @Shadow
    @Final
    private ItemModelManager itemModelManager;

    @Inject(method = "render(Lnet/minecraft/client/render/entity/state/ItemEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"), cancellable = true)
    private void renderStack(ItemEntityRenderState itemEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        RenderItemEntityEvent event = Mahiro.EVENT_BUS.post(RenderItemEntityEvent.get(itemEntityRenderState, mc.getRenderTickCounter().getTickDelta(true), matrixStack, vertexConsumerProvider, i, mc.getItemRenderer()));
        if (event.isCancelled()) ci.cancel();
    }
}
