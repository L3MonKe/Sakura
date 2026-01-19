package dev.lemonclient.client.mixin.input;

import dev.lemonclient.client.LemonClient;
import dev.lemonclient.client.events.input.MouseButtonEvent;
import dev.lemonclient.client.events.misc.KeyAction;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MixinMouse {
    @Inject(method = "onMouseButton", at = @At("HEAD"), cancellable = true)
    private void onMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
        if (LemonClient.EVENT_BUS.post(new MouseButtonEvent(button, KeyAction.from(action))).isCancelled()) {
            ci.cancel();
        }
    }
}
