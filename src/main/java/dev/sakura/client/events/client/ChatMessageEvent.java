package dev.sakura.client.events.client;

import dev.sakura.client.events.Cancellable;
import net.minecraft.util.StringHelper;
import org.apache.commons.lang3.StringUtils;

/**
 * 不能直接使用ChatMessageEvent，接子类。
 */

public class ChatMessageEvent extends Cancellable {
    private final String message;

    public ChatMessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return normalize(message);
    }

    private String normalize(String chatText) {
        return StringHelper.truncateChat(StringUtils.normalizeSpace(chatText.trim()));
    }

    public static class Client extends ChatMessageEvent {
        public Client(String message) {
            super(message);
        }
    }

    public static class Server extends ChatMessageEvent {
        public Server(String message) {
            super(message);
        }
    }
}
