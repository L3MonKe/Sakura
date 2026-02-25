package dev.mzc.client.mixin.render;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.mzc.client.Sakura;
import dev.mzc.client.events.render.Render3DEvent;
import dev.mzc.client.module.impl.render.BlockOutline;
import dev.mzc.client.module.impl.render.NoRender;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import dev.mzc.client.utils.render.MSAAFramebuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.ObjectAllocator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.mzc.client.utils.render.Render3DUtil;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer {
    @Inject(method = "render", at = @At(value = "RETURN"))
    private void hookRender(ObjectAllocator allocator, RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, Matrix4f positionMatrix, Matrix4f projectionMatrix, CallbackInfo ci) {
        Render3DUtil.updateMatrices(projectionMatrix, positionMatrix);

        MatrixStack matrixStack = new MatrixStack();
        RenderSystem.getModelViewStack().pushMatrix().mul(matrixStack.peek().getPositionMatrix());
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw() + 180.0f));

        if (!Sakura.UI_HIDDEN) {
            MSAAFramebuffer.use(() -> Sakura.EVENT_BUS.post(new Render3DEvent(matrixStack, tickCounter.getTickDelta(true))));
        }

        RenderSystem.getModelViewStack().popMatrix();
    }

    @Inject(method = "renderWeather", at = @At("HEAD"), cancellable = true)
    private void onRenderWeather(FrameGraphBuilder frameGraphBuilder, Vec3d pos, float tickDelta, Fog fog, CallbackInfo ci) {
        NoRender noRender = Sakura.MODULES.getModule(NoRender.class);
        if (noRender.noWeather()) ci.cancel();
    }

    @ModifyVariable(method = "render", at = @At("HEAD"), argsOnly = true)
    private boolean onRenderBlockOutline(boolean renderBlockOutline) {
        BlockOutline blockOutline = Sakura.MODULES.getModule(BlockOutline.class);
        if (blockOutline != null && blockOutline.isEnabled()) {
            return false;
        }
        return renderBlockOutline;
    }
}
