package dev.mahiro.client.mixin.render;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.mahiro.client.Mahiro;
import dev.mahiro.client.module.impl.render.NoRender;
import net.minecraft.client.render.fog.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FogRenderer.class)
public class MixinBackgroundRenderer {
    @ModifyExpressionValue(method = "getFogBuffer", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/fog/FogRenderer;fogEnabled:Z"))
    private boolean modifyFogEnabled(boolean original) {
        if (Mahiro.MODULES == null) return original;
        return original && !Mahiro.MODULES.getModule(NoRender.class).noFog();
    }
}