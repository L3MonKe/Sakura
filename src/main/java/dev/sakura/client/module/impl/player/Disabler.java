package dev.sakura.client.module.impl.player;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.mixin.accessor.IPlayerMoveC2SPacket;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.client.ChatUtil;
import dev.sakura.client.utils.player.PacketUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.MultiBoolValue;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

import java.util.List;

public class Disabler extends Module {
    public Disabler() {
        super("Disabler", "禁用器", Category.Player);
    }

    private final BoolValue disAim360 = new BoolValue("Aim 360", "Aim 360", true);
    private final BoolValue inventory = new BoolValue("Inventory", "背包", true);
    private final BoolValue logging = new BoolValue("Logging", "日志", false);
    private final MultiBoolValue selected = new MultiBoolValue("Select", "选择", List.of(new BoolValue("Aim360", "Aim360", false, disAim360::get), new BoolValue("Inventory", "背包", true, inventory::get)), logging::get);

    @EventHandler
    private void onPacket(PacketEvent event) {
        if (nullCheck()) return;
        if (event.getType() != EventType.SEND) return;

        if (disAim360.get()) {
            if (event.getPacket() instanceof PlayerMoveC2SPacket movePacket && movePacket.changesLook()) {
                IPlayerMoveC2SPacket accessor = (IPlayerMoveC2SPacket) movePacket;
                float yaw = accessor.getYaw();
                if (yaw < 360.0f && yaw > -360.0f) {
                    accessor.setYaw(yaw + 720.0f);
                    if (logging.get() && selected.isEnabled("Aim360")) {
                        log("Disabled aim 360");
                    }
                }
            }
        }

        if (inventory.get()) {
            if ((event.getPacket() instanceof ClickSlotC2SPacket || event.getPacket() instanceof CloseHandledScreenC2SPacket) && mc.player.isSprinting()) {
                event.setCancelled(true);
                mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.STOP_SPRINTING));
                PacketUtil.sendPacketNoEvent(event.getPacket());
                if (logging.get() && selected.isEnabled("Inventory")) {
                    log("Disabled screen action");
                }
                mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_SPRINTING));
            }
        }
    }

    private void log(String message) {
        if (logging.get()) {
            ChatUtil.clientMessage(message);
        }
    }
}
