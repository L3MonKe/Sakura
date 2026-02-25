package dev.sakura.client.mixin.render;


import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.render.Atmosphere;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientWorld.Properties.class)
public class MixinClientWorldProperties {
    @Inject(method = "getTimeOfDay", at = @At("HEAD"), cancellable = true)
    private void getTimeOfDay(CallbackInfoReturnable<Long> info) {
        if (Sakura.MODULES.getModule(Atmosphere.class).isEnabled() && Sakura.MODULES.getModule(Atmosphere.class).modifyTime.get()) {
            info.setReturnValue(Sakura.MODULES.getModule(Atmosphere.class).time.get().longValue() * 100L);
        }
    }
}
