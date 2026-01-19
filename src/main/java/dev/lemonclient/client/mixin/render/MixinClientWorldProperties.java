package dev.lemonclient.client.mixin.render;


import dev.lemonclient.client.LemonClient;
import dev.lemonclient.client.module.impl.render.Atmosphere;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientWorld.Properties.class)
public class MixinClientWorldProperties {
    @Inject(method = "getTimeOfDay", at = @At("HEAD"), cancellable = true)
    private void getTimeOfDay(CallbackInfoReturnable<Long> info) {
        if (LemonClient.MODULES.getModule(Atmosphere.class).isEnabled() && LemonClient.MODULES.getModule(Atmosphere.class).modifyTime.get()) {
            info.setReturnValue(LemonClient.MODULES.getModule(Atmosphere.class).time.get().longValue() * 100L);
        }
    }
}
