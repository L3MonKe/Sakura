package dev.sakura.client.mixin.render;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.impl.render.item.HeldItemRendererEvent;
import dev.sakura.client.event.impl.render.item.UpdateHeldItemEvent;
import dev.sakura.client.interfaces.IHeldItemRenderer;
import dev.sakura.client.module.impl.render.Animations;
import dev.sakura.client.utils.player.ItemSpoofUtils;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
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
    private ItemStack mainHand;

    @Shadow
    private ItemStack offHand;

    @Shadow
    private float equipProgressMainHand;

    @Shadow
    private float equipProgressOffHand;

    @Unique
    private Hand cachedHand;

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

    @ModifyVariable(method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemDisplayContext;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;I)V", at = @At("HEAD"), argsOnly = true)
    private ItemStack modifyRenderItemStack(ItemStack stack, LivingEntity entity) {
        if (this.client.player != null && entity == this.client.player) {
            if (stack == this.client.player.getMainHandStack()) {
                ItemStack spoofedStack = ItemSpoofUtils.getSpoofedStack();
                if (spoofedStack != null) {
                    stack = spoofedStack;
                }
            }
        }
        return stack;
    }

    @Shadow
    private net.minecraft.client.MinecraftClient client;

    @ModifyVariable(method = "renderFirstPersonItem", at = @At("HEAD"), argsOnly = true)
    private ItemStack modifyRenderItem(ItemStack stack, AbstractClientPlayerEntity player, float tickProgress, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, OrderedRenderCommandQueue orderedRenderCommandQueue, int light) {
        if (hand == Hand.MAIN_HAND) {
            ItemStack spoofedStack = ItemSpoofUtils.getSpoofedStack();
            if (spoofedStack != null) {
                stack = spoofedStack;
            }
        }

        HeldItemRendererEvent event = new HeldItemRendererEvent(hand, stack, 0, new MatrixStack());
        Sakura.EVENT_BUS.post(event);

        return event.getItem();
    }

    @Redirect(method = "updateHeldItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getMainHandStack()Lnet/minecraft/item/ItemStack;"))
    public ItemStack hookMainHand(ClientPlayerEntity player) {
        ItemStack spoofedStack = ItemSpoofUtils.getSpoofedStack();
        ItemStack original = player.getMainHandStack();
        UpdateHeldItemEvent event = new UpdateHeldItemEvent(Hand.MAIN_HAND, spoofedStack != null ? spoofedStack : original);
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
        HeldItemRendererEvent event = new HeldItemRendererEvent(hand, item, equipProgress, matrices);
        Sakura.EVENT_BUS.post(event);
        if (event.isCancelled()) ci.cancel();
    }

    @Inject(method = "renderFirstPersonItem", at = @At("HEAD"))
    private void hookRenderFirstPersonItemHead(AbstractClientPlayerEntity player, float tickProgress, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, OrderedRenderCommandQueue orderedRenderCommandQueue, int light, CallbackInfo ci) {
        cachedHand = hand;
    }

    @Inject(method = "renderFirstPersonItem", at = @At(value = "HEAD"), cancellable = true)
    private void onRenderItemHook(AbstractClientPlayerEntity player, float tickProgress, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, OrderedRenderCommandQueue orderedRenderCommandQueue, int light, CallbackInfo ci) {
        Animations animations = Sakura.MODULES.getModule(Animations.class);
        if (hand == Hand.MAIN_HAND && animations.shouldAnimate() && !(item.isEmpty()) && !(item.getItem() instanceof FilledMapItem)) {
            ci.cancel();
            animations.renderFirstPersonItemCustom(player, tickProgress, pitch, hand, swingProgress, item, equipProgress, matrices, orderedRenderCommandQueue, light);
        }
    }

    @Redirect(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;applyEatOrDrinkTransformation(Lnet/minecraft/client/util/math/MatrixStack;FLnet/minecraft/util/Arm;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/PlayerEntity;)V"))
    private void redirectApplyEatOrDrinkTransformation(HeldItemRenderer instance, MatrixStack matrices, float tickDelta, Arm arm, ItemStack item, PlayerEntity player) {
        Animations animations = Sakura.MODULES.getModule(Animations.class);
        if (animations.isEnabled() && animations.shouldAnimate() && cachedHand == Hand.MAIN_HAND) {
            applyEatOrDrinkTransformation(matrices, tickDelta, arm, item, player);
            return;
        }

        applyEatOrDrinkTransformation(matrices, tickDelta, arm, item, player);
    }
}
