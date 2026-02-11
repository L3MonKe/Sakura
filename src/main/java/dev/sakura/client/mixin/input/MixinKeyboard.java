package dev.sakura.client.mixin.input;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.impl.key.KeyEvent;
import dev.sakura.client.event.type.KeyAction;
import net.minecraft.client.Keyboard;
import net.minecraft.client.input.KeyInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class MixinKeyboard {
    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    public void onKey(long window, int action, KeyInput input, CallbackInfo ci) {
        if (Sakura.EVENT_BUS.post(new KeyEvent(input.key(), input.modifiers(), KeyAction.from(action))).isCancelled()) {
            ci.cancel();
        }
    }
}
