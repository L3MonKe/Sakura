package dev.sakura.client.mixin.render;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.render.ChestESP;
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
        ChestESP chestESP = Sakura.MODULES.getModule(ChestESP.class);
        if (chestESP.isEnabled() && (chestESP.isChamsEnabled() || chestESP.isGlowEnabled()) && chestESP.isThroughWalls()) {
            return ChestESP.chestChams(spriteIdentifier.getAtlasId());
        }
        return layerFactory.apply(spriteIdentifier.getAtlasId());
    }

    @Redirect(method = "render(Lnet/minecraft/client/render/block/entity/state/ChestBlockEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/RenderLayer;IIILnet/minecraft/client/texture/Sprite;ILnet/minecraft/client/render/command/ModelCommandRenderer$CrumblingOverlayCommand;)V"))
    private void hookSubmitModel(OrderedRenderCommandQueue queue, Model<?> model, Object state, MatrixStack matrices, RenderLayer layer, int light, int overlay, int tintedColor, Sprite sprite, int outlineColor, ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlay) {
        ChestESP chestESP = Sakura.MODULES.getModule(ChestESP.class);
        int finalTint = tintedColor;
        int finalOutlineColor = outlineColor;

        if (chestESP.isEnabled()) {
            if (chestESP.isChamsEnabled()) {
                int tint = chestESP.getChamsTintColor();
                if (tint != -1) {
                    finalTint = tint;
                }
            }
            if (chestESP.isGlowEnabled()) {
                finalOutlineColor = -1; // Force outline color to trigger glowing render pass if vanilla logic uses it, but actually we need to make sure the RenderLayer supports outlining or that we are rendering to the outline buffer.
                // In vanilla 1.21, "glowing" is handled by the WorldRenderer checking for the glowing flag on entities.
                // For BlockEntities, it's less direct.
                // However, `submitModel` has an `outlineColor` parameter.
                // If we set it to something other than 0 (or whatever default is), it might render to the outline buffer?
                // Actually, outlineColor in `submitModel` corresponds to the color of the outline if outlining is active.
                
                // But simply setting this int might not be enough if the render layer isn't set up for outlining.
                // The ChestESP.chestChams layer uses `.outlineMode(RenderSetup.OutlineMode.AFFECTS_OUTLINE)`.
                // So if we are using that layer, setting outlineColor might work.
                
                // Wait, `outlineColor` is usually packed int.
                // If we want it to be "glowing" in the sense of the EntityGlow effect, we need it to be rendered into the entityOutlineFramebuffer.
                // This usually happens if the RenderLayer has `OutlineMode.AFFECTS_OUTLINE` AND the renderer decides to render it there.
            }
        }
        queue.submitModel((Model<? super Object>) model, state, matrices, layer, light, overlay, finalTint, sprite, finalOutlineColor, crumblingOverlay);
    }
}
