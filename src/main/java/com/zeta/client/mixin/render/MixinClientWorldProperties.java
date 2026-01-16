package com.zeta.client.mixin.render;


import com.zeta.client.Zeta;
import com.zeta.client.module.impl.render.Atmosphere;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientWorld.Properties.class)
public class MixinClientWorldProperties {
    @Inject(method = "getTimeOfDay", at = @At("HEAD"), cancellable = true)
    private void getTimeOfDay(CallbackInfoReturnable<Long> info) {
        if (Zeta.MODULES.getModule(Atmosphere.class).isEnabled() && Zeta.MODULES.getModule(Atmosphere.class).modifyTime.get()) {
            info.setReturnValue(Zeta.MODULES.getModule(Atmosphere.class).time.get().longValue() * 100L);
        }
    }
}
