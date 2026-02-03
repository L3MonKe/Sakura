package dev.mahiro.client.module.impl.render;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.events.entity.LimbAnimationEvent;
import dev.mahiro.client.events.entity.SwingSpeedEvent;
import dev.mahiro.client.events.entity.UpdateServerPositionEvent;
import dev.mahiro.client.events.packet.PacketEvent;
import dev.mahiro.client.events.player.PlayerTickEvent;
import dev.mahiro.client.events.render.item.EatTransformationEvent;
import dev.mahiro.client.events.render.item.HeldItemRendererEvent;
import dev.mahiro.client.events.render.item.RenderSwingAnimationEvent;
import dev.mahiro.client.events.type.EventType;
import dev.mahiro.client.interfaces.IHeldItemRenderer;
import dev.mahiro.client.mixin.accessor.IAccessorBundlePacket;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.module.impl.combat.KillAura;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.EnumValue;
import dev.mahiro.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.s2c.play.BundleS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Animations extends Module {
    public Animations() {
        super("Animations", "格挡动画", Category.Render);
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Default);
    private final BoolValue onlySword = new BoolValue("Only Sword", "仅持剑", true);
    private final BoolValue onlyAura = new BoolValue("Only Aura", "仅光环时启用", true);
    private final BoolValue noOffhand = new BoolValue("No Offhand", "隐藏副手", false);
    public final BoolValue oldAnimationsM = new BoolValue("Disable Swap Main", "禁用主手切换动画", true);
    public final BoolValue oldAnimationsOff = new BoolValue("Disable Swap Off", "禁用副手切换动画", true);
    private final BoolValue oldSwingConfig = new BoolValue("Old Swing Animation", "旧版挥手", false);
    private final BoolValue swingSpeedConfig = new BoolValue("Swing Speed", "挥手速度", false);
    private final NumberValue<Integer> swingFactorConfig = new NumberValue<>("Swing Factor", "挥手因子", 6, 1, 20, 1, swingSpeedConfig::get);
    private final BoolValue selfOnlyConfig = new BoolValue("Self Only", "仅自己", true, () -> false);
    private final BoolValue eatTransformConfig = new BoolValue("Eat Transform", "食用变换", false);
    private final NumberValue<Double> eatTransformFactorConfig = new NumberValue<>("Eat Factor", "食物变换因子", 1.0, 0.0, 1.0, 0.1, eatTransformConfig::get);
    private final BoolValue limbSwing = new BoolValue("No Limb Swing", "无肢体摆动", false);
    private final BoolValue interpolationConfig = new BoolValue("No Interpolation", "无插值", false, limbSwing::get);

    public boolean flip;

    private enum Mode {
        Normal, Default, One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve, Thirteen, Fourteen
    }

    public boolean shouldAnimate() {
        if (mc.player.isUsingItem()) return false;
        if (onlySword.get() && !(mc.player.getMainHandStack().isIn(ItemTags.SWORDS))) return false;
        if (Mahiro.MODULES.getModule(KillAura.class).isEnabled() && Mahiro.MODULES.getModule(KillAura.class).getCurrentTarget() != null && Mahiro.MODULES.getModule(KillAura.class).isAutoBlock())
            return true;
        return false;
    }

    public boolean shouldChangeAnimationDuration() {
        return isEnabled() && (!onlyAura.get() || (Mahiro.MODULES.getModule(KillAura.class).isEnabled() && Mahiro.MODULES.getModule(KillAura.class).getCurrentTarget() != null));
    }

    @EventHandler
    public void onTick(PlayerTickEvent event) {
        if (nullCheck()) return;

        if (oldAnimationsM.get() && ((IHeldItemRenderer) mc.getEntityRenderDispatcher().getHeldItemRenderer()).getEquippedProgressMainHand() <= 1f) {
            ((IHeldItemRenderer) mc.getEntityRenderDispatcher().getHeldItemRenderer()).setEquippedProgressMainHand(1f);
            ((IHeldItemRenderer) mc.getEntityRenderDispatcher().getHeldItemRenderer()).setItemStackMainHand(mc.player.getMainHandStack());
        }

        if (oldAnimationsOff.get() && ((IHeldItemRenderer) mc.getEntityRenderDispatcher().getHeldItemRenderer()).getEquippedProgressOffHand() <= 1f) {
            ((IHeldItemRenderer) mc.getEntityRenderDispatcher().getHeldItemRenderer()).setEquippedProgressOffHand(1f);
            ((IHeldItemRenderer) mc.getEntityRenderDispatcher().getHeldItemRenderer()).setItemStackOffHand(mc.player.getOffHandStack());
        }
    }

    @EventHandler
    public void onPacketSend(PacketEvent event) {
        if (event.getType() == EventType.SEND && event.getPacket() instanceof HandSwingC2SPacket) {
            flip = !flip;
        }
    }

    @EventHandler
    public void onSwingSpeed(SwingSpeedEvent event) {
        if (swingSpeedConfig.get()) {
            event.setCancelled(true);
            event.setSwingSpeed(swingFactorConfig.get());
            event.setSelfOnly(selfOnlyConfig.get());
        }
    }

    @EventHandler
    public void onEatTransformation(EatTransformationEvent event) {
        if (eatTransformConfig.get()) {
            event.setCancelled(true);
            event.setFactor(eatTransformFactorConfig.get().floatValue());
        }
    }

    @EventHandler
    public void onLimbAnimation(LimbAnimationEvent event) {
        if (limbSwing.get()) {
            event.setCancelled(true);
            event.setSpeed(0.0f);
        }
    }

    @EventHandler
    public void onUpdateServerPosition(UpdateServerPositionEvent event) {
        if (limbSwing.get() && interpolationConfig.get()) {
            event.getLivingEntity().setPos(event.getX(), event.getY(), event.getZ());
            event.getLivingEntity().setYaw(event.getYaw());
            event.getLivingEntity().setPitch(event.getPitch());
        }
    }

    @EventHandler
    public void onRenderSwing(RenderSwingAnimationEvent event) {
        if (oldSwingConfig.get()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPacketInbound(PacketEvent event) {
        if (mc.player == null || event.getType() != EventType.RECEIVE) return;

        if (event.getPacket() instanceof BundleS2CPacket packet) {
            List<Packet<?>> packets = new ArrayList<>();
            for (Packet<?> packet1 : packet.getPackets()) {
                if (packet1 instanceof EntityAnimationS2CPacket packet2 && oldSwingConfig.get()
                        && packet2.getEntityId() == mc.player.getId()
                        && (packet2.getAnimationId() == EntityAnimationS2CPacket.SWING_MAIN_HAND || packet2.getAnimationId() == EntityAnimationS2CPacket.SWING_OFF_HAND)) {
                    continue;
                }
                packets.add(packet1);
            }
            ((IAccessorBundlePacket) packet).setIterable(packets);
        } else if (event.getPacket() instanceof EntityAnimationS2CPacket packet && oldSwingConfig.get()
                && packet.getEntityId() == mc.player.getId()
                && (packet.getAnimationId() == EntityAnimationS2CPacket.SWING_MAIN_HAND || packet.getAnimationId() == EntityAnimationS2CPacket.SWING_OFF_HAND)) {
            event.setCancelled(true);
        }
    }

    private void renderSwordAnimation(MatrixStack matrices, float f, float swingProgress, float equipProgress, Arm arm) {
        ViewModel viewModel = Mahiro.MODULES.getModule(ViewModel.class);
        if (arm == Arm.LEFT && (mode.get() == Mode.Eleven || mode.get() == Mode.Ten || mode.get() == Mode.Nine || mode.get() == Mode.Three || mode.get() == Mode.Thirteen || mode.get() == Mode.Fourteen)) {
            applyEquipOffset(matrices, arm, equipProgress);
            matrices.translate(-viewModel.mainX.get(), viewModel.mainY.get(), viewModel.mainZ.get());
            applySwingOffset(matrices, arm, swingProgress);
            matrices.translate(viewModel.mainX.get(), -viewModel.mainY.get(), -viewModel.mainZ.get());
            return;
        }


        switch (mode.get()) {
            case Default -> {
                applyEquipOffset(matrices, arm, equipProgress);
                translateToViewModelOff(matrices);
                applySwingOffset(matrices, arm, swingProgress);
                translateBacklOff(matrices);
            }
            case One -> {
                float n = -0.4F * MathHelper.sin(MathHelper.sqrt(swingProgress) * 3.1415927F);
                applyEquipOffset(matrices, arm, n);
                int i = arm == Arm.RIGHT ? 1 : -1;
                translateToViewModel(matrices);
                float f1 = MathHelper.sin(swingProgress * swingProgress * 3.1415927F);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) i * (45.0F + f1 * -20.0F)));
                float g = MathHelper.sin(MathHelper.sqrt(swingProgress) * 3.1415927F);
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float) i * g * -20.0F));
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(g * 0.0F));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) i * -45.0F));
                translateBack(matrices);
            }
            case Two ->
                    applyEquipOffset(matrices, arm, 0.2F * MathHelper.sin(MathHelper.sqrt(swingProgress) * 6.2831855F));
            case Three -> {
                float n = -0.4F * MathHelper.sin(MathHelper.sqrt(swingProgress) * 3.1415927F);
                float g = MathHelper.sin(MathHelper.sqrt(swingProgress) * 3.1415927F);
                applyEquipOffset(matrices, arm, n);
                int i = arm == Arm.RIGHT ? 1 : -1;
                translateToViewModel(matrices);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) i * (45.0F + f * -20.0F)));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float) i * g * -70.0F));
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-70f));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) i * -45.0F));
                translateBack(matrices);
            }
            case Four -> {
                applyEquipOffset(matrices, arm, 0);
                translateToViewModel(matrices);
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(swingProgress > 0 ? -MathHelper.sin(swingProgress * 13f) * 37f : 0));
                translateBack(matrices);
            }
            case Five -> {
                applyEquipOffset(matrices, arm, 0);
                int i = arm == Arm.RIGHT ? 1 : -1;
                float g = MathHelper.sin(MathHelper.sqrt(swingProgress) * 3.1415927F);
                translateToViewModel(matrices);
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float) i * g * -20.0F));
                translateBack(matrices);
            }
            case Six -> {
                applyEquipOffset(matrices, arm, equipProgress);
                translateToViewModel(matrices);
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(swingProgress * (flip ? 360.0F : -360)));
                translateBack(matrices);
            }
            case Eight -> {
                applyEquipOffset(matrices, arm, equipProgress);
                translateToViewModel(matrices);
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(swingProgress * -360));
                translateBack(matrices);
            }
            case Seven -> {
                applyEquipOffset(matrices, arm, equipProgress);
                float a = -MathHelper.sin(swingProgress * 3f) / 2f + 1f;
                matrices.scale(a, a, a);
            }
            case Nine -> {
                float g = MathHelper.sin(MathHelper.sqrt(swingProgress) * 3.1415927F);
                applyEquipOffset(matrices, arm, 0);
                translateToViewModel(matrices);
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(50f));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-30f * (1f - g) - 30f));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(110f));
                translateBack(matrices);
            }
            case Ten -> {
                float g = MathHelper.sin(MathHelper.sqrt(swingProgress) * 3.1415927F);
                matrices.translate(0, 0, 0);
                applyEquipOffset(matrices, arm, 0);
                translateToViewModel(matrices);
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(50f));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-60f * g - 50));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(110f));
                translateBack(matrices);
            }
            case Eleven -> {
                float g = MathHelper.sin(MathHelper.sqrt(swingProgress) * 3.1415927F);
                applyEquipOffset(matrices, arm, 0);
                translateToViewModel(matrices);
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(50f));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-60f));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(110f + 20f * g));
                translateBack(matrices);
            }
            case Twelve -> {
                float g = MathHelper.sin(MathHelper.sqrt(swingProgress) * 3.1415927F);
                applyEquipOffset(matrices, arm, 0);
                matrices.translate(0, 0, -g / 4f);
                translateToViewModel(matrices);
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-120f));
                translateBack(matrices);
            }
            case Thirteen -> {
                float g = MathHelper.sin(MathHelper.sqrt(swingProgress) * 3.1415927F);
                applyEquipOffset(matrices, arm, 0);
                translateToViewModel(matrices);
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-MathHelper.sin(swingProgress * 3f) * 60f));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-60f * g));
                translateBack(matrices);
            }
            case Fourteen -> {
                if (swingProgress > 0) {
                    float g = MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
                    matrices.translate(0.56F, equipProgress * -0.2f - 0.5F, -0.7F);

                    translateToViewModel(matrices);
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45));
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(g * -85.0F));

                    if (viewModel.isEnabled())
                        matrices.translate(-0.1F * viewModel.scaleMainX.get(), 0.28F * viewModel.scaleMainX.get(), 0.2F * viewModel.scaleMainX.get());
                    else
                        matrices.translate(-0.1F, 0.28F, 0.2F);

                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-85.0F));
                    translateBack(matrices);
                } else {
                    float n = -0.4f * MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
                    float m = 0.2f * MathHelper.sin(MathHelper.sqrt(swingProgress) * ((float) Math.PI * 2));
                    float f1 = -0.2f * MathHelper.sin(swingProgress * (float) Math.PI);
                    matrices.translate(n, m, f1);
                    applyEquipOffset(matrices, arm, equipProgress);
                    applySwingOffset(matrices, arm, swingProgress);
                }
            }
        }
    }


    public void renderFirstPersonItemCustom(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (noOffhand.get() && hand == Hand.OFF_HAND && shouldAnimate()) return;
        if (!player.isUsingSpyglass()) {
            boolean bl = hand == Hand.MAIN_HAND;
            Arm arm = bl ? player.getMainArm() : player.getMainArm().getOpposite();
            matrices.push();

            boolean bl2;
            float f = 0;
            float g;
            float h;
            float j;
            if (item.isOf(Items.CROSSBOW)) {
                bl2 = CrossbowItem.isCharged(item);
                boolean bl3 = arm == Arm.RIGHT;
                int i = bl3 ? 1 : -1;
                if (player.isUsingItem() && player.getItemUseTimeLeft() > 0 && player.getActiveHand() == hand) {
                    applyEquipOffset(matrices, arm, equipProgress);
                    matrices.translate((float) i * -0.4785682F, -0.094387F, 0.05731531F);
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-11.935F));
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) i * 65.3F));
                    matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float) i * -9.785F));
                    f = (float) item.getMaxUseTime(mc.player) - ((float) mc.player.getItemUseTimeLeft() - tickDelta + 1.0F);
                    g = f / (float) CrossbowItem.getPullTime(item, mc.player);
                    if (g > 1.0F) {
                        g = 1.0F;
                    }

                    if (g > 0.1F) {
                        h = MathHelper.sin((f - 0.1F) * 1.3F);
                        j = g - 0.1F;
                        float k = h * j;
                        matrices.translate(k * 0.0F, k * 0.004F, k * 0.0F);
                    }

                    matrices.translate(g * 0.0F, g * 0.0F, g * 0.04F);
                    matrices.scale(1.0F, 1.0F, 1.0F + g * 0.2F);
                    matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees((float) i * 45.0F));
                } else {
                    f = -0.4F * MathHelper.sin(MathHelper.sqrt(swingProgress) * 3.1415927F);
                    g = 0.2F * MathHelper.sin(MathHelper.sqrt(swingProgress) * 6.2831855F);
                    h = -0.2F * MathHelper.sin(swingProgress * 3.1415927F);
                    matrices.translate((float) i * f, g, h);
                    applyEquipOffset(matrices, arm, equipProgress);
                    applySwingOffset(matrices, arm, swingProgress);
                    if (bl2 && swingProgress < 0.001F && bl) {
                        matrices.translate((float) i * -0.641864F, 0.0F, 0.0F);
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) i * 10.0F));
                    }
                }

                HeldItemRendererEvent event = new HeldItemRendererEvent(hand, item, equipProgress, matrices);
                Mahiro.EVENT_BUS.post(event);
                renderItem(player, item, bl3 ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND, !bl3, matrices, vertexConsumers, light);
            } else {
                bl2 = arm == Arm.RIGHT;
                int l;
                float m = 0;
                if (player.isUsingItem() && player.getItemUseTimeLeft() > 0 && player.getActiveHand() == hand) {
                    l = bl2 ? 1 : -1;
                    switch (item.getUseAction()) {
                        case NONE, BLOCK -> applyEquipOffset(matrices, arm, equipProgress);
                        case EAT, DRINK -> {
                            applyEatOrDrinkTransformationCustom(matrices, tickDelta, arm, item);
                            applyEquipOffset(matrices, arm, equipProgress);
                        }
                        case BOW -> {
                            applyEquipOffset(matrices, arm, equipProgress);
                            matrices.translate((float) l * -0.2785682F, 0.18344387F, 0.15731531F);
                            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-13.935F));
                            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) l * 35.3F));
                            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float) l * -9.785F));
                            m = (float) item.getMaxUseTime(mc.player) - ((float) mc.player.getItemUseTimeLeft() - tickDelta + 1.0F);
                            f = m / 20.0F;
                            f = (f * f + f * 2.0F) / 3.0F;
                            if (f > 1.0F) {
                                f = 1.0F;
                            }
                            if (f > 0.1F) {
                                g = MathHelper.sin((m - 0.1F) * 1.3F);
                                h = f - 0.1F;
                                j = g * h;
                                matrices.translate(j * 0.0F, j * 0.004F, j * 0.0F);
                            }
                            matrices.translate(f * 0.0F, f * 0.0F, f * 0.04F);
                            matrices.scale(1.0F, 1.0F, 1.0F + f * 0.2F);
                            matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees((float) l * 45.0F));
                        }
                        case SPEAR -> {
                            applyEquipOffset(matrices, arm, equipProgress);
                            matrices.translate((float) l * -0.5F, 0.7F, 0.1F);
                            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-55.0F));
                            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) l * 35.3F));
                            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float) l * -9.785F));
                            m = (float) item.getMaxUseTime(mc.player) - ((float) mc.player.getItemUseTimeLeft() - tickDelta + 1.0F);
                            f = m / 10.0F;
                            if (f > 1.0F) {
                                f = 1.0F;
                            }
                            if (f > 0.1F) {
                                g = MathHelper.sin((m - 0.1F) * 1.3F);
                                h = f - 0.1F;
                                j = g * h;
                                matrices.translate(j * 0.0F, j * 0.004F, j * 0.0F);
                            }
                            matrices.translate(0.0F, 0.0F, f * 0.2F);
                            matrices.scale(1.0F, 1.0F, 1.0F + f * 0.2F);
                            matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees((float) l * 45.0F));
                        }
                        case BRUSH -> applyBrushTransformation(matrices, tickDelta, arm, item, equipProgress);
                    }
                } else if (player.isUsingRiptide()) {
                    applyEquipOffset(matrices, arm, equipProgress);
                    l = bl2 ? 1 : -1;
                    matrices.translate((float) l * -0.4F, 0.8F, 0.3F);
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) l * 65.0F));
                    matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float) l * -85.0F));
                } else {
                    renderSwordAnimation(matrices, f, swingProgress, equipProgress, arm);
                }

                HeldItemRendererEvent event = new HeldItemRendererEvent(hand, item, equipProgress, matrices);
                Mahiro.EVENT_BUS.post(event);
                renderItem(player, item, bl2 ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND, !bl2, matrices, vertexConsumers, light);
            }
            matrices.pop();
        }
    }

    private void applyBrushTransformation(MatrixStack matrices, float tickDelta, Arm arm, @NotNull ItemStack stack, float equipProgress) {
        applyEquipOffset(matrices, arm, equipProgress);
        float f = (float) mc.player.getItemUseTimeLeft() - tickDelta + 1.0F;
        float g = 1.0F - f / (float) stack.getMaxUseTime(mc.player);
        float m = -15.0F + 75.0F * MathHelper.cos(g * 45.0F * 3.1415927F);

        if (arm != Arm.RIGHT) {
            matrices.translate(0.1, 0.83, 0.35);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-80.0F));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90.0F));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(m));
            matrices.translate(-0.3, 0.22, 0.35);
        } else {
            matrices.translate(-0.25, 0.22, 0.35);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-80.0F));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0F));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(0.0F));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(m));
        }
    }

    private void applyEquipOffset(@NotNull MatrixStack matrices, Arm arm, float equipProgress) {
        int i = arm == Arm.RIGHT ? 1 : -1;
        matrices.translate((float) i * 0.56F, -0.52F + equipProgress * -0.6F, -0.72F);
    }

    private void applySwingOffset(@NotNull MatrixStack matrices, Arm arm, float swingProgress) {
        int i = arm == Arm.RIGHT ? 1 : -1;
        float f = MathHelper.sin(swingProgress * swingProgress * 3.1415927F);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) i * (45.0F + f * -20.0F)));
        float g = MathHelper.sin(MathHelper.sqrt(swingProgress) * 3.1415927F);
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float) i * g * -20.0F));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(g * -80.0F));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) i * -45.0F));
    }

    public void renderItem(LivingEntity entity, ItemStack stack, ItemDisplayContext renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        mc.getItemRenderer().renderItem(entity, stack, renderMode, leftHanded, matrices, vertexConsumers, entity.getWorld(), light, OverlayTexture.DEFAULT_UV, entity.getId());
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

        ViewModel viewModel = Mahiro.MODULES.getModule(ViewModel.class);
        matrices.translate(h * 0.6F * (float) i * viewModel.eatX.get(), h * -0.5F * viewModel.eatY.get(), h * 0.0F);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) i * h * 90.0F));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(h * 10.0F));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float) i * h * 30.0F));
    }

    private void translateToViewModel(MatrixStack matrices) {
        ViewModel viewModel = Mahiro.MODULES.getModule(ViewModel.class);
        if (viewModel.isEnabled())
            matrices.translate(viewModel.mainX.get(), viewModel.mainY.get(), viewModel.mainZ.get());
    }

    private void translateToViewModelOff(MatrixStack matrices) {
        ViewModel viewModel = Mahiro.MODULES.getModule(ViewModel.class);
        if (viewModel.isEnabled())
            matrices.translate(-viewModel.mainX.get(), viewModel.mainY.get(), viewModel.mainZ.get());
    }

    private void translateBack(MatrixStack matrices) {
        ViewModel viewModel = Mahiro.MODULES.getModule(ViewModel.class);
        if (viewModel.isEnabled())
            matrices.translate(-viewModel.mainX.get(), -viewModel.mainY.get(), -viewModel.mainZ.get());
    }

    private void translateBacklOff(MatrixStack matrices) {
        ViewModel viewModel = Mahiro.MODULES.getModule(ViewModel.class);
        if (viewModel.isEnabled())
            matrices.translate(viewModel.mainX.get(), -viewModel.mainY.get(), -viewModel.mainZ.get());
    }
}
