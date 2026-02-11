package dev.sakura.client.event.impl.client;

import dev.sakura.client.event.Cancellable;

public class SendMessageEvent extends Cancellable {
    private final String message;

    public SendMessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
