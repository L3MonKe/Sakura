package dev.sakura.client.module.impl.render;

import dev.sakura.client.Sakura;
import dev.sakura.client.events.EventType;
import dev.sakura.client.events.entity.LimbAnimationEvent;
import dev.sakura.client.events.entity.SwingSpeedEvent;
import dev.sakura.client.events.entity.UpdateServerPositionEvent;
import dev.sakura.client.events.packet.PacketEvent;
import dev.sakura.client.events.render.item.EatTransformationEvent;
import dev.sakura.client.events.render.item.RenderSwingAnimationEvent;
import dev.sakura.client.events.render.item.UpdateHeldItemsEvent;
import dev.sakura.client.mixin.accessor.IAccessorBundlePacket;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.Value;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BundleS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;

import java.util.ArrayList;
import java.util.List;

public class SwingAnimation extends Module {
    private final Value<Boolean> noSwitchConfig = new BoolValue("NoSwitchAnimation", "无切换动画", false);
    private final Value<Boolean> oldSwingConfig = new BoolValue("OldSwingAnimation", "旧版挥手", false);
    private final Value<Boolean> swingSpeedConfig = new BoolValue("SwingSpeed", "挥手速度", false);
    private final Value<Integer> swingFactorConfig = new NumberValue<>("SwingFactor", "挥手因子", 6, 1, 20, 1, () -> swingSpeedConfig.get());
    private final Value<Boolean> selfOnlyConfig = new BoolValue("SelfOnly", "仅自己", true, () -> false);
    private final Value<Boolean> eatTransformConfig = new BoolValue("EatTransform", "食用变换", false);
    private final Value<Double> eatTransformFactorConfig = new NumberValue<>("EatTransform-Factor", "变换因子", 1.0, 0.0, 1.0, 0.1, () -> eatTransformConfig.get());
    private final Value<Boolean> limbSwing = new BoolValue("NoLimbSwing", "无肢体摆动", false);
    private final Value<Boolean> interpolationConfig = new BoolValue("NoInterpolation", "无插值", false, () -> limbSwing.get());

    public SwingAnimation() {
        super("SwingAnimation", "挥手动画", Category.Render);
    }

    @Override
    protected void onEnable() {
        Sakura.EVENT_BUS.subscribe(this);
    }

    @Override
    protected void onDisable() {
        Sakura.EVENT_BUS.unsubscribe(this);
    }

    @EventHandler
    public void onUpdateHeldItems(UpdateHeldItemsEvent event) {
        if (noSwitchConfig.get()) {
            event.setCancelled(true);
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
}
