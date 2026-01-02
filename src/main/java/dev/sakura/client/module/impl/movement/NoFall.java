package dev.sakura.client.module.impl.movement;

import dev.sakura.client.events.EventType;
import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.events.packet.PacketEvent;
import dev.sakura.client.mixin.accessor.IPlayerMoveC2SPacket;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.EnumValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.ItemStack;
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
        if (mc.player == null || mc.world == null) return;
        if (!isFalling()) return;

        if (mode.is(Mode.Grim)) {
            mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY() + 0.000000001, mc.player.getZ(), mc.player.getYaw(), mc.player.getPitch(), false, mc.player.horizontalCollision));
            mc.player.onLanding();
        }
    }

    @EventHandler
    public void onPacketSend(PacketEvent event) {
        if (mc.world == null || mc.player == null || event.getType() != EventType.SEND) return;

        for (ItemStack is : mc.player.getArmorItems()) {
            if (is.getItem() == Items.ELYTRA) {
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
