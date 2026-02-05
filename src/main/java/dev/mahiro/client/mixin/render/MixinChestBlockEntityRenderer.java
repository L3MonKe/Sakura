package dev.mahiro.client.mixin.render;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.module.impl.render.ChestESP;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Function;

@Mixin(ChestBlockEntityRenderer.class)
public class MixinChestBlockEntityRenderer {
    @Redirect(method = "render(Lnet/minecraft/client/render/block/entity/state/ChestBlockEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/SpriteIdentifier;getRenderLayer(Ljava/util/function/Function;)Lnet/minecraft/client/render/RenderLayer;"))
    private RenderLayer hookGetRenderLayer(SpriteIdentifier spriteIdentifier, Function<net.minecraft.util.Identifier, RenderLayer> layerFactory) {
        ChestESP chestESP = Mahiro.MODULES.getModule(ChestESP.class);
        if (chestESP != null && chestESP.isEnabled() && chestESP.isChamsEnabled() && chestESP.isThroughWalls()) {
            return ChestESP.chestChams(spriteIdentifier.getAtlasId());
        }
        return layerFactory.apply(spriteIdentifier.getAtlasId());
    }

    @Redirect(method = "render(Lnet/minecraft/client/render/block/entity/state/ChestBlockEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/RenderLayer;IIILnet/minecraft/client/texture/Sprite;ILnet/minecraft/client/render/command/ModelCommandRenderer$CrumblingOverlayCommand;)V"))
    private void hookSubmitModel(OrderedRenderCommandQueue queue, Model<?> model, Object state, MatrixStack matrices, RenderLayer layer, int light, int overlay, int tintedColor, Sprite sprite, int outlineColor, ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlay) {
        ChestESP chestESP = Mahiro.MODULES.getModule(ChestESP.class);
        int finalTint = tintedColor;
        if (chestESP != null && chestESP.isEnabled() && chestESP.isChamsEnabled()) {
            int tint = chestESP.getChamsTintColor();
            if (tint != -1) {
                finalTint = tint;
            }
        }
        queue.submitModel((Model<? super Object>) model, state, matrices, layer, light, overlay, finalTint, sprite, outlineColor, crumblingOverlay);
    }
}
