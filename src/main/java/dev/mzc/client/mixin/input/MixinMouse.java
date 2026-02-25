package dev.mzc.client.mixin.input;

import dev.mzc.client.Sakura;
import dev.mzc.client.events.input.MouseButtonEvent;
import dev.mzc.client.events.misc.KeyAction;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MixinMouse {
    @Inject(method = "onMouseButton", at = @At("HEAD"), cancellable = true)
    private void onMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
        if (Sakura.EVENT_BUS.post(new MouseButtonEvent(button, KeyAction.from(action))).isCancelled()) {
            ci.cancel();
        }
    }
}
