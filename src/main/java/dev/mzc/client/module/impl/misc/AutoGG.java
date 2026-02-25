package dev.mzc.client.module.impl.misc;

import dev.mzc.client.events.EventType;
import dev.mzc.client.events.client.ChatMessageEvent;
import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.events.packet.PacketEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.values.impl.StringValue;
import dev.mzc.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AutoGG extends Module {
    private final Set<String> triggers = new HashSet<>(Arrays.asList(
            "1st Killer -", "1st Place -", "Winner:", " - Damage Dealt -", "Winning Team -", "1st -",
            "Winners:", "Winner:", "Winning Team:", " won the game!", "Top Seeker:", "1st Place:",
            "Last team standing!", "Winner #1 (", "Top Survivors", "Winners -", "Sumo Duel -",
            "Most Wool Placed -", "Your Overall Winstreak:"
    ));

    private final NumberValue<Integer> delayTicks = new NumberValue<>("SendDelay", "发送延迟", 30, 1, 30, 1);
    private final StringValue message = new StringValue("Message", "消息", "GG");
    private boolean pending;
    private int ticksLeft;

    public AutoGG() {
        super("AutoGG", "自动GG", Category.Misc);
        this.setType(ModuleType.Safe);
    }

    @Override
    protected void onEnable() {
        pending = false;
        ticksLeft = 0;
    }

    private boolean match(String s) {
        if (s == null || s.isEmpty()) return false;
        String t = s.replace('\u00A7', '&'); // strip section for robustness if present
        for (String k : triggers) {
            if (t.contains(k)) return true;
        }
        return false;
    }

    private void sendMsg() {
        if (mc.getNetworkHandler() != null && mc.player != null) {
            mc.getNetworkHandler().sendChatMessage(message.get());
        }
    }

    private void scheduleSend() {
        if (pending) return;
        ticksLeft = Math.max(1, delayTicks.get());
        pending = true;
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;
        if (!pending) return;
        if (ticksLeft-- <= 0) {
            sendMsg();
            pending = false;
        }
    }

    @EventHandler
    private void onClientChat(ChatMessageEvent.Client event) {
        if (nullCheck()) return;
        if (mc.isInSingleplayer()) {
            if (match(event.getMessage())) {
                scheduleSend();
            }
        }
    }

    @EventHandler
    private void onPacket(PacketEvent event) {
        if (nullCheck()) return;
        if (event.getType() != EventType.RECEIVE) return;

        Packet<?> packet = event.getPacket();

        if (packet instanceof TitleS2CPacket(Text text)) {
            if (text != null && match(text.getString())) {
                scheduleSend();
                return;
            }
        }

        if (packet instanceof GameMessageS2CPacket(Text content, boolean overlay)) {
            if (content != null && match(content.getString())) {
                scheduleSend();
                return;
            }
        }
    }
}
