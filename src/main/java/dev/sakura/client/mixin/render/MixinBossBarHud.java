package dev.sakura.client.mixin.render;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.render.NoRender;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.BossBarHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BossBarHud.class)
public class MixinBossBarHud {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(DrawContext context, CallbackInfo ci) {
        if (Sakura.MODULES.getModule(NoRender.class).noBossBar()) ci.cancel();
    }
}
