package dev.sakura.client.mixin.input;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.impl.input.MouseClickEvent;
import dev.sakura.client.event.type.KeyAction;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.Click;
import net.minecraft.client.input.MouseInput;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public abstract class MixinMouse {
    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    public abstract double getScaledX(Window window);

    @Shadow
    public abstract double getScaledY(Window window);

    @Inject(method = "onMouseButton", at = @At("HEAD"), cancellable = true)
    private void onMouseButton(long window, MouseInput mouseInput, int action, CallbackInfo ci) {
        Click click = new Click(getScaledX(client.getWindow()), getScaledY(client.getWindow()), mouseInput);
        if (Sakura.EVENT_BUS.post(new MouseClickEvent(click, KeyAction.from(action))).isCancelled()) {
            ci.cancel();
        }
    }
}
