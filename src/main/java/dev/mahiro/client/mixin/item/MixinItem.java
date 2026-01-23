package dev.mahiro.client.mixin.item;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.module.impl.render.OldHitting;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.consume.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class MixinItem {
    @Inject(method = "getUseAction", at = @At("HEAD"), cancellable = true)
    private void onUseAction(ItemStack stack, CallbackInfoReturnable<UseAction> cir) {
        if (((Item) (Object) this) instanceof SwordItem && Mahiro.MODULES.getModule(OldHitting.class).isEnabled()) {
            cir.setReturnValue(UseAction.BLOCK);
        }
    }

    @Inject(method = "getMaxUseTime", at = @At("HEAD"), cancellable = true)
    private void onMaxUseTime(ItemStack stack, LivingEntity user, CallbackInfoReturnable<Integer> cir) {
        if (((Item) (Object) this) instanceof SwordItem && Mahiro.MODULES.getModule(OldHitting.class).isEnabled()) {
            cir.setReturnValue(72000);
        }
    }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void onUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (((Item) (Object) this) instanceof SwordItem && Mahiro.MODULES.getModule(OldHitting.class).isEnabled()) {
            user.setCurrentHand(hand);
            cir.setReturnValue(ActionResult.CONSUME);
        }
    }
}
