package dev.sakura.client.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.client.Capes;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class MixinAbstractClientPlayerEntity {
    @ModifyReturnValue(method = "getSkinTextures", at = @At("RETURN"))
    private SkinTextures modifySkinTextures(SkinTextures original) {
        Capes capes = Sakura.MODULES.getModule(Capes.class);
        if (capes == null) return original;

        AbstractClientPlayerEntity player = (AbstractClientPlayerEntity) (Object) this;

        Identifier newCape = capes.getCape(player, false);
        Identifier newElytra = capes.getCape(player, true);

        if (newCape == null) newCape = original.capeTexture();
        if (newElytra == null) newElytra = original.elytraTexture();

        if (newCape == original.capeTexture() && newElytra == original.elytraTexture()) {
            return original;
        }

        return new SkinTextures(
                original.texture(),
                original.textureUrl(),
                newCape,
                newElytra,
                original.model(),
                original.secure()
        );
    }
}
