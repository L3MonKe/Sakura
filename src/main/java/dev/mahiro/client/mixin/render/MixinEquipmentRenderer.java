package dev.mahiro.client.mixin.render;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.mahiro.client.Mahiro;
import dev.mahiro.client.interfaces.IEntityRenderState;
import dev.mahiro.client.module.impl.render.Chams;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import static dev.mahiro.client.Mahiro.mc;

@Mixin(EquipmentRenderer.class)
public class MixinEquipmentRenderer {
    @WrapOperation(method = "render(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;ILnet/minecraft/util/Identifier;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayers;armorCutoutNoCull(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
    private RenderLayer redirectArmorLayer(Identifier texture, Operation<RenderLayer> original, EquipmentModel.LayerType layerType, RegistryKey<EquipmentAsset> assetKey, Model<?> model, Object state, ItemStack stack) {
        if (state instanceof EntityRenderState entityState && ((IEntityRenderState) entityState).getEntity() instanceof PlayerEntity player && player != mc.player) {
            final Chams chamsModule = Mahiro.MODULES.getModule(Chams.class);
            if (chamsModule.isEnabled()) {
                Identifier targetTexture = chamsModule.shouldKeepTextures() ? texture : Identifier.of("mahiro", "textures/blank.png");
                return Chams.getChamsLayer(targetTexture);
            }
        }
        return original.call(texture);
    }

    @ModifyArgs(method = "render(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;ILnet/minecraft/util/Identifier;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/command/RenderCommandQueue;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/RenderLayer;IIILnet/minecraft/client/texture/Sprite;ILnet/minecraft/client/render/command/ModelCommandRenderer$CrumblingOverlayCommand;)V"))
    private void modifySubmitModelArgs(Args args) {
        Object stateObj = args.get(1);
        if (!(stateObj instanceof EntityRenderState entityState)) return;

        if (((IEntityRenderState) entityState).getEntity() instanceof PlayerEntity player && player != mc.player) {
            final Chams chamsModule = Mahiro.MODULES.getModule(Chams.class);
            if (chamsModule.isEnabled() && chamsModule.isColorOverlay()) {
                // index 6 is color
                args.set(6, chamsModule.getRGBAColor());
            }
        }
    }
}
