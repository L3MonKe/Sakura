package dev.mahiro.client.mixin.render;


import dev.mahiro.client.Mahiro;
import dev.mahiro.client.module.impl.render.Atmosphere;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientWorld.Properties.class)
public class MixinClientWorldProperties {
    @Inject(method = "getTimeOfDay", at = @At("HEAD"), cancellable = true)
    private void getTimeOfDay(CallbackInfoReturnable<Long> info) {
        if (Mahiro.MODULES.getModule(Atmosphere.class).isEnabled() && Mahiro.MODULES.getModule(Atmosphere.class).modifyTime.get()) {
            info.setReturnValue(Mahiro.MODULES.getModule(Atmosphere.class).time.get().longValue() * 100L);
        }
    }
}
