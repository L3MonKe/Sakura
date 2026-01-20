package dev.mahiro.client.manager.impl;

import dev.mahiro.client.LemonClient;
import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.utils.animations.ChatAnimationManager;
import meteordevelopment.orbit.EventHandler;

public class ChatAnimationUpdater {
    public ChatAnimationUpdater() {
        LemonClient.EVENT_BUS.subscribe(this);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        ChatAnimationManager.getInstance().update();
    }
}