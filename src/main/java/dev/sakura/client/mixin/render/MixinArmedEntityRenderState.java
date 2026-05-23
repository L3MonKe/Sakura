package dev.sakura.client.mixin.render;

import dev.sakura.client.utils.player.ItemSpoofUtils;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.sakura.client.Sakura.mc;

@Mixin(ArmedEntityRenderState.class)
public class MixinArmedEntityRenderState {

    @Inject(method = "updateRenderState", at = @At("HEAD"), cancellable = true)
    private static void onUpdateRenderState(LivingEntity entity, ArmedEntityRenderState state, ItemModelManager itemModelManager, float tickProgress, CallbackInfo ci) {
        if (entity == mc.player) {
            state.mainArm = entity.getMainArm();
            ItemStack mainStack = entity.getMainHandStack();
            state.swingAnimationType = mainStack.getSwingAnimation().type();
            state.handSwingProgress = entity.getHandSwingProgress(tickProgress);
            itemModelManager.updateForLivingEntity(state.rightHandItemState, getStackInArm(Arm.RIGHT), ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, entity);
            itemModelManager.updateForLivingEntity(state.leftHandItemState, getStackInArm(Arm.LEFT), ItemDisplayContext.THIRD_PERSON_LEFT_HAND, entity);
            state.leftHandItem = getStackInArm(Arm.LEFT).copy();
            state.rightHandItem = getStackInArm(Arm.RIGHT).copy();
            ci.cancel();
        }
    }

    @Unique
    private static ItemStack getStackInArm(Arm arm) {
        if (mc.player != null) {
            return mc.player.getMainArm() == arm ? ItemSpoofUtils.getSpoofedStack() : mc.player.getOffHandStack();
        }
        return ItemStack.EMPTY;
    }
}
