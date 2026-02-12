package dev.sakura.client.mixin.render;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.hud.HotbarHud;
import net.minecraft.client.gui.hud.bar.Bar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Bar.class)
public interface MixinBar {
    @Unique
    private static int getVanillaHudYOffset() {
        HotbarHud hotbarHud = Sakura.MODULES.getModule(HotbarHud.class);
        if (hotbarHud.isEnabled()) hotbarHud.getVanillaHudYOffset();
        return 0;
    }

    @ModifyExpressionValue(method = "getCenterY", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;getScaledHeight()I"))
    private int hookGetCenterY(int original) {
        return original - getVanillaHudYOffset();
    }

    @ModifyExpressionValue(method = "drawExperienceLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;getScaledWindowHeight()I"))
    private static int hookDrawExperienceLevel(int original) {
        return original - getVanillaHudYOffset();
    }
}

