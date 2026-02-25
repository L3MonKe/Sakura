package dev.mzc.client.utils.client;

import dev.mzc.client.Sakura;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;

import static dev.mzc.client.Sakura.mc;

public class ChatUtil {
    private static final String PREFIX = "§7[§5" + Sakura.MOD_NAME + "§7] ";

    public static void component(Text component) {
        if (mc.inGameHud == null) return;
        ChatHud chat = mc.inGameHud.getChatHud();
        if (chat != null) chat.addMessage(component);
    }

    public static void addChatMessage(String message) {
        addChatMessage(true, message);
    }

    public static void addChatMessage(boolean prefix, String message) {
        component(Text.literal((prefix ? PREFIX : "") + message));
    }

    public static void sendMessage(String message) {
        if (mc.player != null) {
            Text component = Text.literal(message.replace('&', '§'));
            mc.player.sendMessage(component, false);
        }
    }
}