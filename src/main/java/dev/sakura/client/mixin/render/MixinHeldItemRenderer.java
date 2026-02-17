package dev.sakura.client.mixin.render;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.impl.render.item.HeldItemRendererEvent;
import dev.sakura.client.event.impl.render.item.UpdateHeldItemEvent;
import dev.sakura.client.interfaces.IHeldItemRenderer;
import dev.sakura.client.module.impl.render.Animations;
import dev.sakura.client.module.impl.render.Chams;
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
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
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

    @Unique
    private float cachedSwingProgress;

    @Unique
    private float cachedEquipProgress;

    @Unique
    private Hand cachedHand;

    @Shadow
    private void applyEquipOffset(MatrixStack matrices, Arm arm, float equipProgress) {
    }

    @Shadow
    private void applyEatOrDrinkTransformation(MatrixStack matrices, float tickDelta, Arm arm, ItemStack stack, PlayerEntity player) {
    }

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

    @Inject(method = "renderFirstPersonItem", at = @At("HEAD"))
    private void hookRenderFirstPersonItemHead(AbstractClientPlayerEntity player, float tickProgress, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, OrderedRenderCommandQueue orderedRenderCommandQueue, int light, CallbackInfo ci) {
        cachedSwingProgress = swingProgress;
        cachedEquipProgress = equipProgress;
        cachedHand = hand;
    }

    @Inject(method = "renderFirstPersonItem", at = @At(value = "HEAD"), cancellable = true)
    private void onRenderItemHook(AbstractClientPlayerEntity player, float tickProgress, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, OrderedRenderCommandQueue orderedRenderCommandQueue, int light, CallbackInfo ci) {
        Animations animations = Sakura.MODULES.getModule(Animations.class);
        if (animations != null && hand == Hand.MAIN_HAND && animations.shouldAnimate() && !(item.isEmpty()) && !(item.getItem() instanceof FilledMapItem)) {
            ci.cancel();
            animations.renderFirstPersonItemCustom(player, tickProgress, pitch, hand, swingProgress, item, equipProgress, matrices, orderedRenderCommandQueue, light);
        }
    }

    @Redirect(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;applyEatOrDrinkTransformation(Lnet/minecraft/client/util/math/MatrixStack;FLnet/minecraft/util/Arm;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/PlayerEntity;)V"))
    private void redirectApplyEatOrDrinkTransformation(HeldItemRenderer instance, MatrixStack matrices, float tickDelta, Arm arm, ItemStack item, PlayerEntity player) {
        Animations animations = Sakura.MODULES.getModule(Animations.class);
        if (animations != null && animations.isEnabled() && animations.shouldAnimate() && cachedHand == Hand.MAIN_HAND) {
            applyEatOrDrinkTransformation(matrices, tickDelta, arm, item, player);
            return;
        }

        applyEatOrDrinkTransformation(matrices, tickDelta, arm, item, player);
    }

    private void doSwingAnimation(MatrixStack matrices, float swingProgress) {
        float f = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
        float f1 = MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(f * -20.0f));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(f1 * -20.0f));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(f1 * -80.0f));
    }
}
