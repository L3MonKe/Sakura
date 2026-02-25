package dev.mzc.client.module.impl.player;

import dev.mzc.client.events.EventType;
import dev.mzc.client.events.packet.PacketEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;

public class PacketEat extends Module {
    public PacketEat() {
        super("PacketEat", "发包吃物品", Category.Player);
        this.setType(ModuleType.Hack);
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (event.getType() != EventType.SEND) return;
        if (event.getPacket() instanceof PlayerActionC2SPacket packet && packet.getAction() == PlayerActionC2SPacket.Action.RELEASE_USE_ITEM && mc.player.getActiveItem().get(DataComponentTypes.FOOD) != null) {
            event.cancel();
        }
    }
}