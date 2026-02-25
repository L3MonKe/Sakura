package dev.sakura.client.mixin.render;

import dev.sakura.client.Sakura;
import dev.sakura.client.events.render.item.EatTransformationEvent;
import dev.sakura.client.events.render.item.HeldItemRendererEvent;
import dev.sakura.client.events.render.item.RenderSwingAnimationEvent;
import dev.sakura.client.events.render.item.UpdateHeldItemsEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.sakura.client.Sakura.mc;

@Mixin(HeldItemRenderer.class)
public class MixinHeldItemRenderer {

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    private ItemStack mainHand;

    @Shadow
    private ItemStack offHand;

    @Shadow
    private float equipProgressMainHand;

    @Shadow
    private float equipProgressOffHand;

    @Shadow
    private float prevEquipProgressMainHand;

    @Shadow
    private float prevEquipProgressOffHand;

    @Inject(method = "applyEatOrDrinkTransformation", at = @At(value = "HEAD"), cancellable = true)
    private void hookApplyEatOrDrinkTransformation(MatrixStack matrices, float tickDelta, Arm arm, ItemStack stack, PlayerEntity player, CallbackInfo ci) {
        ci.cancel();
        float h;
        float f = (float) this.client.player.getItemUseTimeLeft() - tickDelta + 1.0f;
        float g = f / (float) stack.getMaxUseTime(mc.player);
        if (g < 0.8f) {
            h = MathHelper.abs(MathHelper.cos(f / 4.0f * (float) Math.PI) * 0.1f);
            EatTransformationEvent eatTransformationEvent = new EatTransformationEvent();
            Sakura.EVENT_BUS.post(eatTransformationEvent);
            matrices.translate(0.0f, eatTransformationEvent.isCancelled() ? h * eatTransformationEvent.getFactor() : h, 0.0f);
        }
        h = 1.0f - (float) Math.pow(g, 27.0);
        int i = arm == Arm.RIGHT ? 1 : -1;
        matrices.translate(h * 0.6f * (float) i, h * -0.5f, h * 0.0f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) i * h * 90.0f));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(h * 10.0f));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float) i * h * 30.0f));
    }

    @ModifyArg(method = "updateHeldItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(FFF)F", ordinal = 2), index = 0)
    private float hookEquipProgressMainhand(float value) {
        RenderSwingAnimationEvent renderSwingAnimation = new RenderSwingAnimationEvent();
        Sakura.EVENT_BUS.post(renderSwingAnimation);
        float f = mc.player.getAttackCooldownProgress(1.0f);
        float modified = renderSwingAnimation.isCancelled() ? 1.0f : f * f * f;
        return (ItemStack.areEqual(mainHand, mc.player.getMainHandStack()) ? modified : 0.0f) - equipProgressMainHand;
    }

    @Inject(method = "updateHeldItems", at = @At(value = "HEAD"), cancellable = true)
    private void hookUpdateHeldItems(CallbackInfo ci) {
        ItemStack itemStack = mc.player.getMainHandStack();
        ItemStack itemStack2 = mc.player.getOffHandStack();
        UpdateHeldItemsEvent updateHeldItemsEvent = new UpdateHeldItemsEvent();
        Sakura.EVENT_BUS.post(updateHeldItemsEvent);
        if (updateHeldItemsEvent.isCancelled()) {
            ci.cancel();
            equipProgressMainHand = 1.0f;
            equipProgressOffHand = 1.0f;
            prevEquipProgressMainHand = 1.0f;
            prevEquipProgressOffHand = 1.0f;
            mainHand = itemStack;
            offHand = itemStack2;
        }
    }

    @Inject(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;push()V", shift = At.Shift.AFTER))
    private void hookRenderFirstPersonItem(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        HeldItemRendererEvent event = new HeldItemRendererEvent(matrices, hand);
        Sakura.EVENT_BUS.post(event);
    }
}