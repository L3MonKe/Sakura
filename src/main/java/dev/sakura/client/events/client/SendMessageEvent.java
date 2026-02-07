package dev.sakura.client.events.client;

import dev.sakura.client.events.Cancellable;

public class SendMessageEvent extends Cancellable {
    private final String message;

    public SendMessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
