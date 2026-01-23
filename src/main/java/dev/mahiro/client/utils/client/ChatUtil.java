package dev.mahiro.client.utils.client;

import dev.mahiro.client.Mahiro;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;

import static dev.mahiro.client.Mahiro.mc;

public class ChatUtil {
    private static final String PREFIX = "§7[§5" + Mahiro.MOD_NAME + "§7] ";

    public static void component(Text component) {
        ChatHud chat = mc.inGameHud.getChatHud();
        chat.addMessage(component);
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