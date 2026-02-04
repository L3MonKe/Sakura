package dev.mahiro.client.mixin.render;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.systems.RenderPass;
import dev.mahiro.client.shaders.BlurProgram;
import net.minecraft.client.gl.PostEffectPass;
import net.minecraft.client.util.Handle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(PostEffectPass.class)
public class MixinPostEffectPass {
    @Inject(method = "method_67884", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderPass;setUniform(Ljava/lang/String;Lcom/mojang/blaze3d/buffers/GpuBuffer;)V", ordinal = 0))
    private void hookLambda(Handle handle, GpuBufferSlice gpuBufferSlice, Map map, CallbackInfo ci, @Local RenderPass renderPass) {
        if (BlurProgram.CUSTOM_UNIFORM.isUsed()) {
            renderPass.setUniform("Globals", BlurProgram.CUSTOM_UNIFORM.getBuffer());
        }
    }
}
