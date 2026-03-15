package dev.sakura.client.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.sakura.client.Sakura;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.event.impl.input.ClickEvent;
import dev.sakura.client.gui.mainmenu.NewMainMenuScreen;
import dev.sakura.client.mixin.accessor.IKeyBinding;
import dev.sakura.client.module.ModuleManager;
import dev.sakura.client.module.impl.movement.GuiMove;
import dev.sakura.client.utils.render.NewMenuMusicController;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Inject(method = "<init>(Lnet/minecraft/client/RunArgs;)V", at = @At("TAIL"))
    private void onInit(RunArgs args, CallbackInfo ci) {
        Sakura.init((MinecraftClient) (Object) this);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onPreTick(CallbackInfo info) {
        Sakura.EVENT_BUS.post(new TickEvent.Pre());
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void onPostTick(CallbackInfo info) {
        Sakura.EVENT_BUS.post(new TickEvent.Post());
        NewMenuMusicController.tick();
    }

    @Inject(method = "handleInputEvents", at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z", ordinal = 0, shift = At.Shift.BEFORE)}, cancellable = true)
    private void onHandleInputEvents(CallbackInfo ci) {
        ClickEvent event = Sakura.EVENT_BUS.post(new ClickEvent());
        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method = "setScreen", at = @At("HEAD"))
    private void onSetScreen(Screen screen, CallbackInfo ci) {
        MinecraftClient client = (MinecraftClient) (Object) this;
        if (client.world != null || Sakura.CONFIG == null || !Sakura.CONFIG.getClientConfig().useNewMainMenu) {
            return;
        }
        if (!(screen instanceof NewMainMenuScreen) || client.currentScreen == null || client.currentScreen instanceof NewMainMenuScreen) {
            return;
        }
        NewMainMenuScreen.requestReturnCancelOnce();
        client.getSoundManager().stopSounds(Identifier.of("minecraft", "ui.button.click"), null);
    }

    @WrapOperation(method = "setScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;unpressAll()V"))
    private void onSetScreenKeyBindingUnpressAll(Operation<Void> operation) {
        ModuleManager moduleManager = Sakura.MODULES;
        if (moduleManager == null) {
            operation.call();
            return;
        }

        GuiMove guiMove = moduleManager.getModule(GuiMove.class);
        if (guiMove == null || !guiMove.isEnabled() || guiMove.skip()) {
            operation.call();
            return;
        }

        MinecraftClient mc = (MinecraftClient) (Object) this;
        GameOptions options = mc.options;

        for (KeyBinding kb : IKeyBinding.getKeysById().values()) {
            if (kb == options.forwardKey) continue;
            if (kb == options.leftKey) continue;
            if (kb == options.rightKey) continue;
            if (kb == options.backKey) continue;
            if (guiMove.sneak.get() && kb == options.sneakKey) continue;
            if (guiMove.sprint.get() && kb == options.sprintKey) continue;
            if (guiMove.jump.get() && kb == options.jumpKey) continue;
            ((IKeyBinding) kb).invokeReset();
        }
    }
}
