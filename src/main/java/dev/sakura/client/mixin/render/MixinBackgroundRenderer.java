package dev.sakura.client.mixin.render;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.render.NoRender;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Fog;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BackgroundRenderer.class)
public class MixinBackgroundRenderer {
    @ModifyReturnValue(method = "applyFog", at = @At("RETURN"))
    private static Fog onApplyFog(Fog original) {
        NoRender noRender = Sakura.MODULES.getModule(NoRender.class);
        if (noRender.noFog() || noRender.noBlindness() || noRender.noDarkness()) {
            return Fog.DUMMY;
        }
        return original;
    }
}