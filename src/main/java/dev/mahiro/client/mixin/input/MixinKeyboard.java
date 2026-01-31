package dev.mahiro.client.mixin.input;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.events.key.KeyEvent;
import dev.mahiro.client.events.type.KeyAction;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class MixinKeyboard {
    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (Mahiro.EVENT_BUS.post(new KeyEvent(key, modifiers, KeyAction.from(action))).isCancelled()) {
            ci.cancel();
        }
    }
}
