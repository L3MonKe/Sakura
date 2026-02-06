package dev.sakura.client.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.client.Capes;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.SkinTextures;
import net.minecraft.util.AssetInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class MixinAbstractClientPlayerEntity {
    @ModifyReturnValue(method = "getSkin", at = @At("RETURN"))
    private SkinTextures modifySkinTextures(SkinTextures original) {
        AbstractClientPlayerEntity player = (AbstractClientPlayerEntity) (Object) this;

        Capes capes = Sakura.MODULES.getModule(Capes.class);
        AssetInfo.TextureAsset newCape = capes.getCape(player, false);
        AssetInfo.TextureAsset newElytra = capes.getCape(player, true);

        if (newCape == null) newCape = original.cape();
        if (newElytra == null) newElytra = original.elytra();

        if (newCape == original.cape() && newElytra == original.elytra()) {
            return original;
        }

        return new SkinTextures(original.body(), newCape, newElytra, original.model(), original.secure());
    }
}
