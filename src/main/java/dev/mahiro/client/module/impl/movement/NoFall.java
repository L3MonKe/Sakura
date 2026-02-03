package dev.mahiro.client.module.impl.movement;

import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.events.packet.PacketEvent;
import dev.mahiro.client.events.type.EventType;
import dev.mahiro.client.mixin.accessor.IPlayerMoveC2SPacket;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.values.impl.EnumValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class NoFall extends Module {
    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Packet);

    public NoFall() {
        super("NoFall", "无摔落", Category.Movement);
    }

    @Override
    public String getSuffix() {
        return mode.get().name();
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;
        if (!isFalling()) return;

        if (mode.is(Mode.Grim)) {
            mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY() + 0.000000001, mc.player.getZ(), mc.player.getYaw(), mc.player.getPitch(), false, mc.player.horizontalCollision));
            mc.player.onLanding();
        }
    }

    @EventHandler
    public void onPacketSend(PacketEvent event) {
        if (mc.world == null || mc.player == null || event.getType() != EventType.SEND) return;

        for (EquipmentSlot slot : AttributeModifierSlot.ARMOR) {
            if (mc.player.getEquippedStack(slot).getItem() == Items.ELYTRA) {
                return;
            }
        }
        if (mode.is(Mode.Packet)) {
            if (event.getPacket() instanceof PlayerMoveC2SPacket packet && isFalling()) {
                ((IPlayerMoveC2SPacket) packet).setOnGround(true);
            }
        }
    }

    private boolean isFalling() {
        return mc.player.fallDistance > mc.player.getSafeFallDistance() && !mc.player.isOnGround();
    }

    private enum Mode {
        Grim,
        Packet
    }
}
