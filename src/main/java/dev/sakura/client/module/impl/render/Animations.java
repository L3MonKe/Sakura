package dev.sakura.client.module.impl.render;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.entity.SwingSpeedEvent;
import dev.sakura.client.event.impl.render.item.HeldItemRendererEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.combat.KillAura;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class Animations extends Module {
    public Animations() {
        super("Animations", "物品动画", Category.Render);
    }

    private final BoolValue onlyWeapon = new BoolValue("OnlyWeapon", "仅武器", true);
    private final BoolValue rightClick = new BoolValue("RightClick", "右键触发", true);

    private final BoolValue mainHand = new BoolValue("MainHand", "主手", false);
    private final NumberValue<Double> mainHandItemScale = new NumberValue<>("ItemScale", "缩放(Z)", 0.0, -5.0, 5.0, 0.05, mainHand::get);
    private final NumberValue<Double> mainHandX = new NumberValue<>("X", "X", 0.0, -5.0, 5.0, 0.05, mainHand::get);
    private final NumberValue<Double> mainHandY = new NumberValue<>("Y", "Y", 0.0, -5.0, 5.0, 0.05, mainHand::get);
    private final NumberValue<Double> mainHandPositiveRotationX = new NumberValue<>("PositiveRotationX", "旋转X(+)", 0.0, -50.0, 50.0, 1.0, mainHand::get);
    private final NumberValue<Double> mainHandPositiveRotationY = new NumberValue<>("PositiveRotationY", "旋转Y(+)", 0.0, -50.0, 50.0, 1.0, mainHand::get);
    private final NumberValue<Double> mainHandPositiveRotationZ = new NumberValue<>("PositiveRotationZ", "旋转Z(+)", 0.0, -50.0, 50.0, 1.0, mainHand::get);

    private final BoolValue offHand = new BoolValue("OffHand", "副手", false);
    private final NumberValue<Double> offHandItemScale = new NumberValue<>("ItemScale", "缩放(Z)", 0.0, -5.0, 5.0, 0.05, offHand::get);
    private final NumberValue<Double> offHandX = new NumberValue<>("X", "X", 0.0, -1.0, 1.0, 0.01, offHand::get);
    private final NumberValue<Double> offHandY = new NumberValue<>("Y", "Y", 0.0, -1.0, 1.0, 0.01, offHand::get);
    private final NumberValue<Double> offHandPositiveRotationX = new NumberValue<>("PositiveRotationX", "旋转X(+)", 0.0, -50.0, 50.0, 1.0, offHand::get);
    private final NumberValue<Double> offHandPositiveRotationY = new NumberValue<>("PositiveRotationY", "旋转Y(+)", 0.0, -50.0, 50.0, 1.0, offHand::get);
    private final NumberValue<Double> offHandPositiveRotationZ = new NumberValue<>("PositiveRotationZ", "旋转Z(+)", 0.0, -50.0, 50.0, 1.0, offHand::get);

    private final BoolValue blockingParams = new BoolValue("BlockingParams", "格挡参数", false);
    private final NumberValue<Double> blockingItemScale = new NumberValue<>("BlockingItemScale", "格挡-缩放(Z)", 0.0, -5.0, 5.0, 0.05, blockingParams::get);
    private final NumberValue<Double> blockingX = new NumberValue<>("BlockingX", "格挡-X", 0.0, -5.0, 5.0, 0.05, blockingParams::get);
    private final NumberValue<Double> blockingY = new NumberValue<>("BlockingY", "格挡-Y", 0.0, -5.0, 5.0, 0.05, blockingParams::get);
    private final NumberValue<Double> blockingRotationX = new NumberValue<>("BlockingRotationX", "格挡-旋转X", 0.0, -50.0, 50.0, 1.0, blockingParams::get);
    private final NumberValue<Double> blockingRotationY = new NumberValue<>("BlockingRotationY", "格挡-旋转Y", 0.0, -50.0, 50.0, 1.0, blockingParams::get);
    private final NumberValue<Double> blockingRotationZ = new NumberValue<>("BlockingRotationZ", "格挡-旋转Z", 0.0, -50.0, 50.0, 1.0, blockingParams::get);

    private final EnumValue<SwingMode> swingMode = new EnumValue<>("SwingMode", "挥手模式", SwingMode.VANILLA);
    private final NumberValue<Integer> swingDuration = new NumberValue<>("SwingDuration", "挥手时长", 6, 1, 20, 1);

    private final EnumValue<BlockingAnimation> blockingAnimation = new EnumValue<>("BlockingAnimation", "格挡动画", BlockingAnimation.V1_7);
    private final NumberValue<Double> oneSevenTranslateY = new NumberValue<>("Y", "1.7-Y", 0.1, 0.05, 0.3, 0.01, () -> blockingAnimation.is(BlockingAnimation.V1_7));
    private final NumberValue<Double> oneSevenSwingScale = new NumberValue<>("SwingScale", "1.7-挥手缩放", 0.9, 0.1, 1.0, 0.01, () -> blockingAnimation.is(BlockingAnimation.V1_7));

    private final NumberValue<Double> spinSpeed = new NumberValue<>("SpinSpeed", "Spin-速度", 1.0, 0.1, 20.0, 0.1, () -> blockingAnimation.is(BlockingAnimation.SPIN));
    private final NumberValue<Double> spinRange = new NumberValue<>("SpinRange", "Spin-范围", 1.0, 0.0, 5.0, 0.1, () -> blockingAnimation.is(BlockingAnimation.SPIN));
    private final NumberValue<Double> spinX = new NumberValue<>("SpinX", "Spin-旋转X", 0.0, -180.0, 180.0, 1.0, () -> blockingAnimation.is(BlockingAnimation.SPIN));
    private final NumberValue<Double> spinY = new NumberValue<>("SpinY", "Spin-旋转Y", 0.0, -180.0, 180.0, 1.0, () -> blockingAnimation.is(BlockingAnimation.SPIN));
    private final NumberValue<Double> spinZ = new NumberValue<>("SpinZ", "Spin-旋转Z", 0.0, -180.0, 180.0, 1.0, () -> blockingAnimation.is(BlockingAnimation.SPIN));

    private enum BlockingAnimation {
        V1_7,
        PUSHDOWN,
        EXHIBITION,
        SPIN
    }

    private enum SwingMode {
        VANILLA,
        CURRENT
    }

    private boolean isWeapon(ItemStack stack) {
        return stack.isIn(ItemTags.SWORDS) || stack.isIn(ItemTags.AXES);
    }

    public boolean shouldAnimate() {
        if (!isEnabled() || nullCheck()) return false;

        return mc.player == null || !onlyWeapon.get() || isWeapon(mc.player.getMainHandStack());
    }

    private boolean isBlocking() {
        if (rightClick.get()) {
            KillAura killAura = Sakura.MODULES.getModule(KillAura.class);
            if (killAura.isEnabled() && killAura.isAutoBlock() && killAura.getCurrentTarget() != null) return true;
            if (mc.player != null) {
                return mc.options.useKey.isPressed() || mc.player.isUsingItem();
            }
        }

        return true;
    }

    @EventHandler
    public void onSwingSpeed(SwingSpeedEvent event) {
        if (!isEnabled()) return;

        event.setCancelled(true);
        event.setSwingSpeed(swingDuration.get());
        event.setSelfOnly(true);
    }

    public void renderFirstPersonItemCustom(AbstractClientPlayerEntity player, float tickProgress, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, OrderedRenderCommandQueue orderedRenderCommandQueue, int light) {
        if (hand != Hand.MAIN_HAND) return;
        if (item.isEmpty()) return;
        Arm arm = player.getMainArm();

        matrices.push();
        applyViewModelTransformations(matrices, arm);
        applyEquipOffset(matrices, arm, equipProgress);
        
        boolean blocking = isBlocking();
        
        if (blockingAnimation.is(BlockingAnimation.SPIN)) {
            KillAura killAura = Sakura.MODULES.getModule(KillAura.class);
            if (killAura.isEnabled() && killAura.getCurrentTarget() != null) {
                blocking = true;
            }
        }

        if (blocking) {
            applyBlockingAnimation(matrices, arm, equipProgress, swingProgress);
        } else {
            if (swingMode.is(SwingMode.VANILLA)) {
                applySwingOffset(matrices, arm, swingProgress);
            } else {
                applyCurrentSwingOffset(matrices, arm, swingProgress);
            }
        }

        HeldItemRendererEvent event = new HeldItemRendererEvent(hand, item, equipProgress, matrices);
        Sakura.EVENT_BUS.post(event);
        renderItem(player, item, arm == Arm.RIGHT ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND, matrices, orderedRenderCommandQueue, light);
        matrices.pop();
    }

    private void applyViewModelTransformations(MatrixStack matrices, Arm arm) {
        if (arm == Arm.RIGHT) {
            if (isBlocking() && blockingParams.get()) {
                applyTransformations(
                        matrices,
                        blockingX.get(),
                        blockingY.get(),
                        blockingItemScale.get(),
                        blockingRotationX.get(),
                        blockingRotationY.get(),
                        blockingRotationZ.get()
                );
            } else if (mainHand.get()) {
                applyTransformations(
                        matrices,
                        mainHandX.get(),
                        mainHandY.get(),
                        mainHandItemScale.get(),
                        mainHandPositiveRotationX.get(),
                        mainHandPositiveRotationY.get(),
                        mainHandPositiveRotationZ.get()
                );
            }
        } else {
            if (offHand.get()) {
                applyTransformations(
                        matrices,
                        offHandX.get(),
                        offHandY.get(),
                        offHandItemScale.get(),
                        offHandPositiveRotationX.get(),
                        offHandPositiveRotationY.get(),
                        offHandPositiveRotationZ.get()
                );
            }
        }
    }

    private void applyTransformations(MatrixStack matrices, double translateX, double translateY, double translateZ, double rotateX, double rotateY, double rotateZ) {
        matrices.translate(translateX, translateY, translateZ);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((float) rotateX));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) rotateY));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float) rotateZ));
    }

    private void applyEquipOffset(MatrixStack matrices, Arm arm, float equipProgress) {
        int i = arm == Arm.RIGHT ? 1 : -1;
        matrices.translate((float) i * 0.56F, -0.52F + equipProgress * -0.6F, -0.72F);
    }

    private void applySwingOffset(MatrixStack matrices, Arm arm, float swingProgress) {
        int i = arm == Arm.RIGHT ? 1 : -1;
        float f = MathHelper.sin(swingProgress * swingProgress * 3.1415927F);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) i * (45.0F + f * -20.0F)));
        float g = MathHelper.sin(MathHelper.sqrt(swingProgress) * 3.1415927F);
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float) i * g * -20.0F));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(g * -80.0F));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) i * -45.0F));
    }

    private void applyCurrentSwingOffset(MatrixStack matrices, Arm arm, float swingProgress) {
        int i = arm == Arm.RIGHT ? 1 : -1;
        float f = MathHelper.sin(swingProgress * swingProgress * 3.1415927F);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) i * (45.0F + f * -20.0F)));
        float g = MathHelper.sin(MathHelper.sqrt(swingProgress) * 3.1415927F);
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float) i * g * -20.0F));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(g * -80.0F));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) i * -45.0F));
    }

    private void applyBlockingAnimation(MatrixStack matrices, Arm arm, float equipProgress, float swingProgress) {
        if (blockingAnimation.is(BlockingAnimation.V1_7)) {
            oneSevenTransform(matrices, arm, equipProgress, swingProgress);
        } else if (blockingAnimation.is(BlockingAnimation.PUSHDOWN)) {
            pushdownTransform(matrices, arm, equipProgress, swingProgress);
        } else if (blockingAnimation.is(BlockingAnimation.EXHIBITION)) {
            exhibitionTransform(matrices, arm, equipProgress, swingProgress);
        } else if (blockingAnimation.is(BlockingAnimation.SPIN)) {
            spinTransform(matrices, arm, equipProgress, swingProgress);
        }
    }

    private void oneSevenTransform(MatrixStack matrices, Arm arm, float equipProgress, float swingProgress) {
        matrices.translate(arm == Arm.RIGHT ? -0.1F : 0.1F, oneSevenTranslateY.get().floatValue(), 0.0F);
        applySwingOffset(matrices, arm, swingProgress * oneSevenSwingScale.get().floatValue());
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-102.25F));
        matrices.multiply((arm == Arm.RIGHT ? RotationAxis.POSITIVE_Y : RotationAxis.NEGATIVE_Y).rotationDegrees(13.365F));
        matrices.multiply((arm == Arm.RIGHT ? RotationAxis.POSITIVE_Z : RotationAxis.NEGATIVE_Z).rotationDegrees(78.05F));
    }

    private void pushdownTransform(MatrixStack matrices, Arm arm, float equipProgress, float swingProgress) {
        matrices.translate(arm == Arm.RIGHT ? -0.1F : 0.1F, 0.1F, 0.0F);
        float g = MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((arm == Arm.RIGHT ? 1 : -1) * g * 10.0F));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(g * -35.0F));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-102.25F));
        matrices.multiply((arm == Arm.RIGHT ? RotationAxis.POSITIVE_Y : RotationAxis.NEGATIVE_Y).rotationDegrees(13.365F));
        matrices.multiply((arm == Arm.RIGHT ? RotationAxis.POSITIVE_Z : RotationAxis.NEGATIVE_Z).rotationDegrees(78.05F));
    }

    private void exhibitionTransform(MatrixStack matrices, Arm arm, float equipProgress, float swingProgress) {
        matrices.translate(arm == Arm.RIGHT ? -0.1F : 0.1F, 0.15F, 0.0F);
        applySwingOffset(matrices, arm, swingProgress);
        matrices.translate(arm == Arm.RIGHT ? -0.5F : 0.5F, 0.2F, 0.0F);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(arm == Arm.RIGHT ? 30.0F : -30.0F));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-80.0F));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(arm == Arm.RIGHT ? 60.0F : -60.0F));
    }

    private void spinTransform(MatrixStack matrices, Arm arm, float equipProgress, float swingProgress) {
        matrices.translate(0, 0.2, -spinRange.get());
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) (System.currentTimeMillis() * spinSpeed.get() % 360)));
        matrices.translate(0, 0, spinRange.get());
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(spinX.get().floatValue()));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(spinY.get().floatValue()));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(spinZ.get().floatValue()));
    }

    private void renderItem(LivingEntity entity, ItemStack stack, ItemDisplayContext renderMode, MatrixStack matrices, OrderedRenderCommandQueue orderedRenderCommandQueue, int light) {
        if (stack.isEmpty()) return;
        ItemRenderState itemRenderState = new ItemRenderState();
        mc.getItemModelManager().clearAndUpdate(itemRenderState, stack, renderMode, entity.getEntityWorld(), entity, entity.getId() + renderMode.ordinal());
        itemRenderState.render(matrices, orderedRenderCommandQueue, light, OverlayTexture.DEFAULT_UV, 0);
    }
}
