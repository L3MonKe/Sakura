package dev.sakura.client.mixin.input;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.impl.input.MouseButtonEvent;
import dev.sakura.client.event.type.KeyAction;
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
        if (Sakura.EVENT_BUS.post(new MouseButtonEvent(input.button(), KeyAction.from(action))).isCancelled()) {
            ci.cancel();
        }
    }
}
