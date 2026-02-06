package dev.sakura.client.mixin.render;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.render.NoRender;
import net.minecraft.client.render.fog.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FogRenderer.class)
public class MixinBackgroundRenderer {
    @ModifyExpressionValue(method = "getFogBuffer", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/fog/FogRenderer;fogEnabled:Z"))
    private boolean modifyFogEnabled(boolean original) {
        if (Sakura.MODULES == null) return original;
        return original && !Sakura.MODULES.getModule(NoRender.class).noFog();
    }
}