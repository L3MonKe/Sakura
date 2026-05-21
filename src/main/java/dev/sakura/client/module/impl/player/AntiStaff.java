package dev.sakura.client.module.impl.player;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.packet.PacketEvent;
import dev.sakura.client.event.type.EventType;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.client.ChatUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;

public class AntiStaff extends Module {
    public static AntiStaff INSTANCE;

    private static final String STAFF_LIST_B64 = "QuermeaQnOaXoOmHj+Wfn+mbqizkuInlm73mnYAs56yZ5qmZLE1lbmdDaGVuMzg4NCxBbmRyZXdrcmlzdCxGaWE5LOaeq+iQp+ael+eEtiznu7/osYbkuYPjgZXjgpMs5oqW6Z+z5Li25bCP5YyqLOaKlumfs19hd2Hpqazljp8sTW5hbUxlb18s5Lit5LqM5bCR5bm0REws5p6V5LiK5Lmm5Li25aGR5pyb5pyILElhbU1vbGluY2VuXywsQ29GdV9fLOaWl+aImOiDnOS9myzlj6rnjqnmlqXlgJks5p6V5LiK5Lmm5Li26Zuq5aScLGFpeXVraSxDYW5keUFwb3N0bGUsY2h1bnlpMSzmtYHlvbHlj6rkvJrlmKTlmKTlmKQscXRlc2RmXzY3NCxxeHRtbGM5OSxTa3lmb3ks56We5Z2R5LmL6YCXLOWco+S4iuiNo+iAgDIzMyzlsI/lhpvlkJvkuLblpKnkvb/kuYvnv7ws5p6V5LiK5Lmm5Li25YKy5a+SLF93aW5uZXJfLFNreV9ZdWFueGlhbw==";

    public AntiStaff() {
        super("AntiStaff", "反管理员", Category.Player);
        INSTANCE = this;
    }

    @EventHandler
    public void onPacket(PacketEvent packetEvent) {
        if (packetEvent.getType() != EventType.RECEIVE) return;
        String decoded = new String(Base64.getDecoder().decode(STAFF_LIST_B64), StandardCharsets.UTF_8);
        List<String> list = Arrays.asList(decoded.split(","));
        Packet<?> packet = packetEvent.getPacket();
        if (packet instanceof PlayerListS2CPacket playerListS2CPacket) {
            if (playerListS2CPacket.getActions().contains(PlayerListS2CPacket.Action.ADD_PLAYER)) {
                for (PlayerListS2CPacket.Entry entry : playerListS2CPacket.getEntries()) {
                    if (entry.profile() != null) {
                        String name = entry.profile().name();
                        if (name != null && !name.isEmpty() && list.contains(name)) {
                            this.exitGame();
                        }
                    }
                    if (entry.displayName() != null) {
                        String display = entry.displayName().getString();
                        if (!display.isEmpty() && list.contains(display)) {
                            this.exitGame();
                        }
                    }
                }
            }
        } else if (packet instanceof EntitySpawnS2CPacket entitySpawnS2CPacket && entitySpawnS2CPacket.getEntityType() == EntityType.PLAYER) {
            if (entitySpawnS2CPacket.getUuid() != null && mc.world != null) {
                Entity entity = mc.world.getEntityById(entitySpawnS2CPacket.getEntityId());
                if (entity != null) {
                    String name = entity.getName().getString();
                    if (!name.isEmpty() && list.contains(name)) {
                        this.exitGame();
                    }
                }
            }
        }
    }

    private void exitGame() {
        ChatUtil.clientMessage("Staff detected!");
        if (mc.player != null && mc.player.networkHandler != null) {
            mc.player.networkHandler.sendChatCommand("hub");
        }
    }
}
