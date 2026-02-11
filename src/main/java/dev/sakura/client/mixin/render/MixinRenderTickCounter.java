package dev.sakura.client.mixin.render;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.impl.client.TimerEvent;
import dev.sakura.client.module.impl.player.TimerModule;
import net.minecraft.client.render.RenderTickCounter;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderTickCounter.Dynamic.class)
public class MixinRenderTickCounter {
    @Shadow
    private float dynamicDeltaTicks;

    @Inject(method = "beginRenderTick(J)I", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/RenderTickCounter$Dynamic;dynamicDeltaTicks:F", opcode = Opcodes.PUTFIELD, ordinal = 0, shift = At.Shift.AFTER))
    public void onBeginRenderTick(long long_1, CallbackInfoReturnable<Integer> cir) {
        TimerEvent event = new TimerEvent();
        Sakura.EVENT_BUS.post(event);
        TimerModule timer = Sakura.MODULES.getModule(TimerModule.class);
        if (!event.isCancelled()) {
            if (event.isModified()) {
                dynamicDeltaTicks *= event.get();
            } else {
                dynamicDeltaTicks *= timer.getTimerSpeed();
            }
        }
    }
}
