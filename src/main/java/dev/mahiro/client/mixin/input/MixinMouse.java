package dev.mahiro.client.mixin.input;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.events.input.MouseButtonEvent;
import dev.mahiro.client.events.type.KeyAction;
import net.minecraft.client.Mouse;
import net.minecraft.client.input.MouseInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MixinMouse {
    @Inject(method = "onMouseButton", at = @At("HEAD"), cancellable = true)
    private void onMouseButton(long window, MouseInput input, int action, CallbackInfo ci) {
        if (Mahiro.EVENT_BUS.post(new MouseButtonEvent(input.button(), KeyAction.from(action))).isCancelled()) {
            ci.cancel();
        }
    }
}
