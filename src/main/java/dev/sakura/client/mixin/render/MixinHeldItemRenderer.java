package dev.sakura.client.mixin.render;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.impl.render.item.HeldItemRendererEvent;
import dev.sakura.client.event.impl.render.item.UpdateHeldItemEvent;
import dev.sakura.client.interfaces.IHeldItemRenderer;
import dev.sakura.client.module.impl.render.Animations;
import dev.sakura.client.module.impl.render.Chams;
import dev.sakura.client.module.impl.render.ViewModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.sakura.client.Sakura.mc;

@Mixin(HeldItemRenderer.class)
public abstract class MixinHeldItemRenderer implements IHeldItemRenderer {
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

    @Override
    public float getEquippedProgressMainHand() {
        return equipProgressMainHand;
    }

    @Override
    public void setEquippedProgressMainHand(float mainHand) {
        this.equipProgressMainHand = mainHand;
    }

    @Override
    public float getEquippedProgressOffHand() {
        return equipProgressOffHand;
    }

    @Override
    public void setEquippedProgressOffHand(float offHand) {
        this.equipProgressOffHand = offHand;
    }

    @Override
    public void setItemStackMainHand(ItemStack stack) {
        this.mainHand = stack;
    }

    @Override
    public void setItemStackOffHand(ItemStack stack) {
        this.offHand = stack;
    }

    @ModifyVariable(method = "renderFirstPersonItem", at = @At("HEAD"), argsOnly = true)
    private ItemStack modifyRenderItem(ItemStack stack, AbstractClientPlayerEntity player, float tickProgress, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, OrderedRenderCommandQueue orderedRenderCommandQueue, int light) {
        if (mc.player == null || mc.world == null) return stack;

        HeldItemRendererEvent event = new HeldItemRendererEvent(hand, stack, 0, new MatrixStack());
        Sakura.EVENT_BUS.post(event);

        return event.getItem();
    }

    @Redirect(method = "updateHeldItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getMainHandStack()Lnet/minecraft/item/ItemStack;"))
    public ItemStack hookMainHand(ClientPlayerEntity player) {
        UpdateHeldItemEvent event = new UpdateHeldItemEvent(Hand.MAIN_HAND, player.getMainHandStack());
        if (player == mc.player) {
            Sakura.EVENT_BUS.post(event);
        }
        return event.getItem();
    }

    @Redirect(method = "updateHeldItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getOffHandStack()Lnet/minecraft/item/ItemStack;"))
    public ItemStack hookOffHand(ClientPlayerEntity player) {
        UpdateHeldItemEvent event = new UpdateHeldItemEvent(Hand.OFF_HAND, player.getOffHandStack());
        if (player == mc.player) {
            Sakura.EVENT_BUS.post(event);
        }
        return event.getItem();
    }

    @Inject(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;push()V", shift = At.Shift.AFTER), cancellable = true)
    private void onRenderItem(AbstractClientPlayerEntity player, float tickProgress, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, OrderedRenderCommandQueue orderedRenderCommandQueue, int light, CallbackInfo ci) {
        if (mc.player == null || mc.world == null) return;
        HeldItemRendererEvent event = new HeldItemRendererEvent(hand, item, equipProgress, matrices);
        Sakura.EVENT_BUS.post(event);
        if (event.isCancelled()) ci.cancel();

        if (event.getItem() != item) {
        }
    }

    @Inject(method = "renderFirstPersonItem", at = @At(value = "RETURN"))
    private void onRenderItemPost(AbstractClientPlayerEntity player, float tickProgress, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, OrderedRenderCommandQueue orderedRenderCommandQueue, int light, CallbackInfo ci) {
        Chams chams = Sakura.MODULES.getModule(Chams.class);
        if (chams.isEnabled() && chams.handItems.get()) {
            // TODO: idk how to do it in 2k26
            //RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        }
    }

    @Inject(method = "renderFirstPersonItem", at = @At(value = "HEAD"), cancellable = true)
    private void onRenderItemHook(AbstractClientPlayerEntity player, float tickProgress, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, OrderedRenderCommandQueue orderedRenderCommandQueue, int light, CallbackInfo ci) {
        Animations animations = Sakura.MODULES.getModule(Animations.class);
        if (animations != null && animations.shouldAnimate() && !(item.isEmpty()) && !(item.getItem() instanceof FilledMapItem)) {
            ci.cancel();
            animations.renderFirstPersonItemCustom(player, tickProgress, pitch, hand, swingProgress, item, equipProgress, matrices, orderedRenderCommandQueue, light);
        }
    }


    private void applyEatOrDrinkTransformationCustom(MatrixStack matrices, float tickDelta, Arm arm, @NotNull ItemStack stack) {
        float f = (float) mc.player.getItemUseTimeLeft() - tickDelta + 1.0F;
        float g = f / (float) stack.getMaxUseTime(mc.player);
        float h;
        if (g < 0.8F) {
            h = MathHelper.abs(MathHelper.cos(f / 4.0F * 3.1415927F) * 0.005F);
            matrices.translate(0.0F, h, 0.0F);
        }
        h = 1.0F - (float) Math.pow(g, 27.0);
        int i = arm == Arm.RIGHT ? 1 : -1;

        ViewModel viewModel = Sakura.MODULES.getModule(ViewModel.class);
        matrices.translate(h * 0.6F * (float) i * viewModel.eatX.get(), h * -0.5F * viewModel.eatY.get(), h * 0.0F);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) i * h * 90.0F));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(h * 10.0F));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float) i * h * 30.0F));
    }

    @Inject(method = "applyEatOrDrinkTransformation", at = @At(value = "HEAD"), cancellable = true)
    private void applyEatOrDrinkTransformationHook(MatrixStack matrices, float tickDelta, Arm arm, ItemStack stack, PlayerEntity player, CallbackInfo ci) {
        Animations animations = Sakura.MODULES.getModule(Animations.class);
        if (animations.isEnabled() && animations.shouldAnimate()) {
            applyEatOrDrinkTransformationCustom(matrices, tickDelta, arm, stack);
            ci.cancel();
        }
    }
}
