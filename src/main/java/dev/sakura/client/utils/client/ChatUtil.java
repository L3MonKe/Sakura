package dev.sakura.client.utils.client;

import dev.sakura.client.Sakura;
import net.minecraft.text.Text;

import static dev.sakura.client.Sakura.mc;

public class ChatUtil {
    public static final String GRADIENT_SYNC_CODE = "§)";
    private static final String CLIENT_PREFIX_TEXT = "[" + Sakura.MOD_NAME + "]";
    private static final String CLIENT_PREFIX = GRADIENT_SYNC_CODE + "§r" + CLIENT_PREFIX_TEXT + "§f ";

    public static void clientMessage(String message) {
        clientMessage(true, message);
    }

    public static void clientMessage(boolean prefix, String message) {
        if (mc.player == null || mc.world == null) return;
        
        // Ensure this runs on the main thread (Render thread)
        if (!mc.isOnThread()) {
            mc.execute(() -> clientMessage(prefix, message));
            return;
        }
        
        // Replace & with § for color codes support
        String formattedMessage = message.replace('&', '§');
        mc.inGameHud.getChatHud().addMessage(Text.literal((prefix ? CLIENT_PREFIX : "") + formattedMessage));
    }

    public static void serverMessage(String message) {
        mc.player.sendMessage(Text.literal(message.replace('&', '§')), false);
    }
}
