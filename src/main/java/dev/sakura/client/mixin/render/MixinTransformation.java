package dev.sakura.client.mixin.render;

import dev.sakura.client.Sakura;
import dev.sakura.client.events.render.item.ApplyTransformationEvent;
import net.minecraft.client.render.model.json.Transformation;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Transformation.class)
public class MixinTransformation {
    @Inject(method = "apply", at = @At("HEAD"), cancellable = true)
    private void onApply(boolean leftHanded, MatrixStack.Entry entry, CallbackInfo ci) {
        ApplyTransformationEvent event = Sakura.EVENT_BUS.post(new ApplyTransformationEvent((Transformation) (Object) this, leftHanded));
        if (event.isCancelled()) ci.cancel();
    }
}
