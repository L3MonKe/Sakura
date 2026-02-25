package dev.sakura.client.mixin.client;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.render.CameraClip;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Perspective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameOptions.class)
public class MixinGameOptions {
    @Shadow
    private Perspective perspective;

    @Inject(method = "setPerspective", at = @At("HEAD"), cancellable = true)
    private void onSetPerspective(Perspective perspective, CallbackInfo ci) {
        CameraClip cameraClip = Sakura.MODULES.getModule(CameraClip.class);

        if (cameraClip.isEnabled() && cameraClip.disableFirstPers.get()) {
            if (perspective == Perspective.THIRD_PERSON_FRONT) {
                if (this.perspective == Perspective.FIRST_PERSON) {
                    this.perspective = Perspective.THIRD_PERSON_BACK;
                    ci.cancel();
                } else if (this.perspective == Perspective.THIRD_PERSON_BACK) {
                    this.perspective = Perspective.FIRST_PERSON;
                    ci.cancel();
                }
            }
        }
    }
}
