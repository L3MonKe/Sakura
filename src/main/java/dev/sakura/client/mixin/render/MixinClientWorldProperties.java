package dev.sakura.client.mixin.render;


import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.render.WorldTweaks;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientWorld.Properties.class)
public class MixinClientWorldProperties {
    @Inject(method = "getTimeOfDay", at = @At("RETURN"), cancellable = true)
    private void getTimeOfDay(CallbackInfoReturnable<Long> info) {
        WorldTweaks module = Sakura.MODULES.getModule(WorldTweaks.class);
        if (module.isEnabled() && module.modifyTime.get()) {
            long day = info.getReturnValue() / 24000L;
            long t = module.time.get().longValue() % 24000L;
            if (t < 0L) t += 24000L;
            info.setReturnValue(day * 24000L + t);
        }
    }
}
